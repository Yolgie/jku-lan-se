package jkulan.software.social.steam;

import org.springframework.social.connect.ApiAdapter;
import org.springframework.social.connect.ConnectionData;
import org.springframework.social.connect.support.AbstractConnection;

import jkulan.software.social.steam.api.Steam;

public class SteamConnection extends AbstractConnection<Steam> {
	private static final long serialVersionUID = -1305542626089910908L;
	
	private final SteamServiceProvider serviceProvider;
	private final String verifiedSteam;
	private transient Steam api;

	public SteamConnection(String verifiedSteam, SteamServiceProvider serviceProvider, ApiAdapter<Steam> apiAdapter) {
		super(apiAdapter);
		this.serviceProvider = serviceProvider;
		this.verifiedSteam = verifiedSteam;
		this.api = initApi();
	}

	public SteamConnection(ConnectionData data, SteamServiceProvider serviceProvider, ApiAdapter<Steam> apiAdapter) {
		super(data, apiAdapter);
		this.serviceProvider = serviceProvider;
		this.verifiedSteam = data.getProviderUserId();
		this.api = initApi();
	}

	private Steam initApi() {
		return serviceProvider.getApi(verifiedSteam);
	}
	
	@Override
	public Steam getApi() {
		synchronized (getMonitor()) {
			return api;
		}
	}

	@Override
	public ConnectionData createData() {
		synchronized (getMonitor()) {
			return new ConnectionData(getKey().getProviderId(), getKey().getProviderUserId(), getDisplayName(), getProfileUrl(), getImageUrl(), null, null, null, null);
		}
	}

	@Override
	public void refresh() {
		api = initApi();
	}
}
