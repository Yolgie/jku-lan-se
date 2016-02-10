package jkulan.software.social.steam;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionValues;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.UserProfileBuilder;

import jkulan.software.social.steam.api.Steam;

public class SteamAdapter implements ApiAdapter<Steam> {

	@Override
	public boolean test(Steam api) {
		return true;
	}

	@Override
	public void setConnectionValues(Steam api, ConnectionValues values) {
		values.setProviderUserId(api.getVerifiedSteamId());
	}

	@Override
	public UserProfile fetchUserProfile(Steam api) {
		return new UserProfileBuilder().setUsername(api.getVerifiedSteamId()).build();
	}

	@Override
	public void updateStatus(Steam api, String message) {
	}

}
