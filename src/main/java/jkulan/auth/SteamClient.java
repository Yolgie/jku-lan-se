package jkulan.auth;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openid4java.message.AuthSuccess;
import org.openid4java.message.MessageException;
import org.openid4java.message.ax.FetchRequest;
import org.pac4j.core.client.BaseClient;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.openid.client.BaseOpenIdClient;
import org.pac4j.openid.credentials.OpenIdCredentials;

/**
 * Necessary to login via Steam, as pac4j provides no generic OpenID client.
 * @author fuero
 */
public class SteamClient extends BaseOpenIdClient<CommonProfile> {
	private static final Log logger = LogFactory.getLog(SteamClient.class);
	public static final String STEAM_OPENID_IDENTIFIER = "http://steamcommunity.com/openid";

	@Override
	protected String getUser(WebContext webContext) {
		return STEAM_OPENID_IDENTIFIER;
	}

	@Override
	protected FetchRequest getFetchRequest() throws MessageException {
		final FetchRequest fetchRequest = FetchRequest.createFetchRequest();
		logger.trace("Fetch Request generated");
		return fetchRequest;
	}

	@Override
	protected CommonProfile createProfile(AuthSuccess authSuccess) throws MessageException {
		CommonProfile profile = new CommonProfile();
		logger.trace("Authentication sucessful " + authSuccess);
		return profile;
	}

	@Override
	protected BaseClient<OpenIdCredentials, CommonProfile> newClient() {
		return super.clone();
	}
}
