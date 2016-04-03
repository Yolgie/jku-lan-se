package at.jku.oeh.lan.laganizer.steam;

import java.util.Collection;

public interface SteamUserQuery {
	SteamUser getUser(long id);
	Collection<SteamUser> getUsers(long... ids);
	Collection<SteamUser> getUsers(Collection<Long> ids);
}
