package jkulan.software.social.steam.api.impl;

import jkulan.software.social.steam.api.Steam;

public class SteamTemplate implements Steam {
	private final String verifiedSteamId;
	private final String apiKey;

	public SteamTemplate(String apiKey, String verifiedOpenId) {
		this.verifiedSteamId = verifiedOpenId;
		this.apiKey = apiKey;
	}
	
	@Override
	public boolean isAuthorized() {
		return verifiedSteamId != null;
	}

	@Override
	public String getVerifiedSteamId() {
		return verifiedSteamId;
	}

}
