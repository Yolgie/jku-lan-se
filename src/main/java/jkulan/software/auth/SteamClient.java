package jkulan.software.auth;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openid4java.message.AuthSuccess;
import org.openid4java.message.MessageException;
import org.openid4java.message.Parameter;
import org.openid4java.message.ax.FetchRequest;
import org.pac4j.core.client.BaseClient;
import org.pac4j.core.context.WebContext;
import org.pac4j.openid.client.BaseOpenIdClient;
import org.pac4j.openid.credentials.OpenIdCredentials;

/**
 * Necessary to login via Steam, as pac4j provides no generic OpenID client.
 * @author fuero
 */
public class SteamClient extends BaseOpenIdClient<SteamProfile> {
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
	protected SteamProfile createProfile(AuthSuccess authSuccess) throws MessageException {
		SteamProfile profile = new SteamProfile();
		logger.trace("Authentication sucessful " + authSuccess);
		return profile;
	}

	@Override
	protected BaseClient<OpenIdCredentials, SteamProfile> newClient() {
		return super.clone();
	}


	@Override
	protected SteamProfile retrieveUserProfile(OpenIdCredentials credentials, WebContext context) {
		if(credentials.getClientName().equals("SteamClient")) {
			SteamProfile steamProfile = new SteamProfile();
			String steamId = getSteamId(credentials);
			steamProfile.setSteamId(steamId);
			return steamProfile;
		}
		else return null;
	}

	private String getSteamId(OpenIdCredentials credentials) {
		Parameter identity = credentials.getParameterList().getParameter("openid.identity");
		String identityString = identity.getValue();
		return identityString.replace("http://steamcommunity.com/openid/id/", "");
	}
}
