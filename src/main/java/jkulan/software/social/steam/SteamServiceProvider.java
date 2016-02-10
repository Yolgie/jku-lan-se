package jkulan.software.social.steam;

import org.springframework.social.ServiceProvider;

import jkulan.software.social.steam.api.Steam;
import jkulan.software.social.steam.api.impl.SteamTemplate;

public class SteamServiceProvider implements ServiceProvider<Steam> {
	
	private String apiKey;
	
	public SteamServiceProvider(String apiKey) {
		this.apiKey = apiKey;
	}

	public Steam getApi(final String verifiedSteamId) {
		return new SteamTemplate(apiKey, verifiedSteamId);
	}
}
