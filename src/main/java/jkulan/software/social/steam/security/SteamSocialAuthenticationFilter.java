package jkulan.software.social.steam.security;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.social.UserIdSource;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.security.SocialAuthenticationFilter;
import org.springframework.social.security.SocialAuthenticationServiceLocator;

public class SteamSocialAuthenticationFilter extends SocialAuthenticationFilter {
	private static final Log log = LogFactory.getLog(SteamSocialAuthenticationFilter.class);
	
	private static final List<String> EXPECTED_OPENID_PARAMS = Collections.unmodifiableList(Arrays.asList(
			"openid_identifier", "openid.identity"));
	
	public SteamSocialAuthenticationFilter(AuthenticationManager authManager,
			UserIdSource userIdSource,
			UsersConnectionRepository usersConnectionRepository,
			SocialAuthenticationServiceLocator authServiceLocator) {
		super(authManager, userIdSource, usersConnectionRepository, authServiceLocator);
	}

	@Override
	protected boolean detectRejection(HttpServletRequest request) {
		log.trace("detectRejection");
		if (!super.detectRejection(request)) {
			return false;
		} else {
			return !isOpenIdRequest(request);
		}
	}
	
	public static boolean isOpenIdRequest(final HttpServletRequest request) {
		final Set<?> parameterKeys = request.getParameterMap().keySet();
		log.trace("isOpenIdRequest");
		if (!parameterKeys.isEmpty()) {
			for (final String expected : EXPECTED_OPENID_PARAMS) {
				if (parameterKeys.contains(expected)) {
					log.debug("isOpenIdRequest=true");
					return true;
				}
			}
		}
		return false;
	}
}
