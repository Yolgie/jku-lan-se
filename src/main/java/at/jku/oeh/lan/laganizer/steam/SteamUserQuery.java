package at.jku.oeh.lan.laganizer.steam;

import java.util.List;

public interface SteamUserQuery {
	SteamUser getUser(long id);
	List<SteamUser> getUsers(long... ids);
	List<SteamUser> getUsers(List<Long> ids);
}
