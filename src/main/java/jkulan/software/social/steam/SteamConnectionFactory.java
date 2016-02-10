package jkulan.software.social.steam;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.ConnectionFactory;

import jkulan.software.social.steam.api.Steam;

public class SteamConnectionFactory extends ConnectionFactory<Steam> {
	
	public SteamConnectionFactory(String apiKey) {
		super("steam", new SteamServiceProvider(apiKey), new SteamAdapter());
	}
	
	@Override
	public Connection<Steam> createConnection(ConnectionData data) {
		return new SteamConnection(data, (SteamServiceProvider) getServiceProvider(), getApiAdapter());
	}
}