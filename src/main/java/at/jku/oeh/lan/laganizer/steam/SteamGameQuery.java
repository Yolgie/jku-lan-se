package at.jku.oeh.lan.laganizer.steam;

import java.util.List;

import at.jku.oeh.lan.laganizer.model.Game;

public interface SteamGameQuery {
	public List<Game> getAllGames();
	public Game getGame(long gameId);
	public List<Game> getGames(long... gameIds);
}
