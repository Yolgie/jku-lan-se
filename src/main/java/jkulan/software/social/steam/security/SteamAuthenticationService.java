package jkulan.software.social.steam.security;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openid4java.consumer.ConsumerException;
import org.openid4java.consumer.ConsumerManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.openid.OpenID4JavaConsumer;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.security.openid.OpenIDConsumer;
import org.springframework.security.openid.OpenIDConsumerException;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.security.SocialAuthenticationRedirectException;
import org.springframework.social.security.SocialAuthenticationToken;
import org.springframework.social.security.provider.AbstractSocialAuthenticationService;
import org.springframework.util.StringUtils;

import jkulan.software.social.steam.SteamConnectionFactory;
import jkulan.software.social.steam.api.Steam;

public class SteamAuthenticationService extends AbstractSocialAuthenticationService<Steam> {

	private static final Log log = LogFactory.getLog(SteamAuthenticationService.class);

	public static final String DEFAULT_CLAIMED_IDENTITY_FIELD = "openid_identifier";

	private SteamConnectionFactory connectionFactory;

	private OpenIDConsumer consumer;
	private String claimedIdentityFieldName = DEFAULT_CLAIMED_IDENTITY_FIELD;
	private IRealmMapper realmMapper = null;
	private Set<String> returnToUrlParameters = Collections.emptySet();

	public SteamAuthenticationService() {
		super();
	}

	public SteamAuthenticationService(SteamConnectionFactory connectionFactory) {
		this();
		setConnectionFactory(connectionFactory);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		log.trace("afterPropertiesSet");
		super.afterPropertiesSet();
        if (consumer == null) {
            try {
            	ConsumerManager manager = new ConsumerManager();
            	manager.setMaxAssocAttempts(0);
                consumer = new OpenID4JavaConsumer();
            } catch (ConsumerException e) {
                throw new IllegalArgumentException("Failed to initialize OpenID", e);
            }
        }
	}

	public void setConnectionFactory(SteamConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

	@Override
	public SteamConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	@Override
	public SocialAuthenticationToken getAuthToken(HttpServletRequest request,
			HttpServletResponse response) throws SocialAuthenticationRedirectException {

		String identity = request.getParameter("openid.identity");
		log.trace("getAuthToken");
		if (!StringUtils.hasText(identity)) {
			String claimedIdentity = obtainUsername(request);
			log.trace("claimedIdentity: "+claimedIdentity);
			try {
				String returnToUrl = buildReturnToUrl(request);
				String realm = lookupRealm(returnToUrl);
				String openIdUrl = consumer.beginConsumption(request, claimedIdentity, returnToUrl, realm);
				if (log.isDebugEnabled()) {
					log.debug("return_to is '" + returnToUrl + "', realm is '" + realm + "'");
					log.debug("Redirecting to " + openIdUrl);
				}
				throw new SocialAuthenticationRedirectException(openIdUrl);
			} catch (OpenIDConsumerException e) {
				log.debug("Failed to consume claimedIdentity: " + claimedIdentity, e);
				throw new AuthenticationServiceException("Unable to process claimed identity '" + claimedIdentity + "'");
			}
		}

		if (log.isDebugEnabled()) {
			log.debug("Supplied OpenID identity is " + identity);
		}

		try {
			OpenIDAuthenticationToken token = consumer.endConsumption(request);

			String verifiedId = (String) token.getPrincipal();
			ConnectionData data = new ConnectionData(connectionFactory.getProviderId(), verifiedId, null, null, null,
					null, null, null, null);

			return new SocialAuthenticationToken(connectionFactory.createConnection(data), obtainAccountData(token));
		} catch (OpenIDConsumerException oice) {
			throw new AuthenticationServiceException("Consumer error", oice);
		}
	}

	protected Map<String, String> obtainAccountData(OpenIDAuthenticationToken token) {
		return Collections.emptyMap();
	}

	protected String lookupRealm(String returnToUrl) {
		log.trace("lookupRealm");
		String mapping = null;

		IRealmMapper realmMapper = getRealmMapper();
		if (realmMapper != null) {
			mapping = realmMapper.getMapping(returnToUrl);
		}


		if (mapping == null) {
			try {
				URL url = new URL(returnToUrl);
				int port = url.getPort();

				StringBuilder realmBuffer = new StringBuilder(returnToUrl.length()).append(url.getProtocol())
						.append("://").append(url.getHost());
				if (port > 0) {
					realmBuffer.append(":").append(port);
				}
				realmBuffer.append("/");
				mapping = realmBuffer.toString();
			} catch (MalformedURLException e) {
				log.warn("returnToUrl was not a valid URL: [" + returnToUrl + "]", e);
			}
		}

		return mapping;
	}

	/**
	 * Builds the <tt>return_to</tt> URL that will be sent to the OpenID service
	 * provider. By default returns the URL of the current request.
	 *
	 * @param request
	 *            the current request which is being processed by this filter
	 * @return The <tt>return_to</tt> URL.
	 */
	protected String buildReturnToUrl(HttpServletRequest request) {
		StringBuffer sb = request.getRequestURL();

		Iterator<String> iterator = returnToUrlParameters.iterator();
		boolean isFirst = true;

		while (iterator.hasNext()) {
			String name = iterator.next();
			// Assume for simplicity that there is only one value
			String value = request.getParameter(name);

			if (value == null) {
				continue;
			}

			if (isFirst) {
				sb.append("?");
				isFirst = false;
			}
			sb.append(name).append("=").append(value);

			if (iterator.hasNext()) {
				sb.append("&");
			}
		}

		return sb.toString();
	}

	/**
	 * Reads the <tt>claimedIdentityFieldName</tt> from the submitted request.
	 */
	protected String obtainUsername(HttpServletRequest req) {
		String claimedIdentity = req.getParameter(claimedIdentityFieldName);

		if (!StringUtils.hasText(claimedIdentity)) {
			log.error("No claimed identity supplied in authentication request");
			return "";
		}

		return claimedIdentity.trim();
	}

	/**
	 * The name of the request parameter containing the OpenID identity, as
	 * submitted from the initial login form.
	 *
	 * @param claimedIdentityFieldName
	 *            defaults to "openid_identifier"
	 */
	public void setClaimedIdentityFieldName(String claimedIdentityFieldName) {
		this.claimedIdentityFieldName = claimedIdentityFieldName;
	}

	public void setConsumer(OpenIDConsumer consumer) {
		this.consumer = consumer;
	}

	public IRealmMapper getRealmMapper() {
		return realmMapper;
	}

	public void setRealmMapper(IRealmMapper realmMapper) {
		this.realmMapper = realmMapper;
	}

	/**
	 * Specifies any extra parameters submitted along with the identity field
	 * which should be appended to the {@code return_to} URL which is assembled
	 * by {@link #buildReturnToUrl}.
	 *
	 * @param returnToUrlParameters
	 *            the set of parameter names. If not set, it will default to the
	 *            parameter name used by the {@code RememberMeServices} obtained
	 *            from the parent class (if one is set).
	 */
	public void setReturnToUrlParameters(Set<String> returnToUrlParameters) {
		assert returnToUrlParameters != null;
		this.returnToUrlParameters = returnToUrlParameters;
	}

}