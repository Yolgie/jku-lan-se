package at.jku.oeh.lan.laganizer.steam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import at.jku.oeh.lan.laganizer.model.Game;

import com.github.koraktor.steamcondenser.steam.community.WebApi;

/**
 * Looks up Steam games via Steam Condenser.
 * 
 * Steam doesn't support querying for a single (or several) AppIDs,
 * so we have to get *all* (ISteamApps.GetAppList)
 * 
 * ISteamUserStats.GetSchemaForGame looks promising, but
 * only supports games with achievements and yields a way larger response.
 * 
 * @author fuero
 */
@Component
public class SteamGameQueryImpl implements SteamGameQuery {
	private static final Log log = LogFactory.getLog(SteamGameQueryImpl.class); 

	@Override
	public List<Game> getAllGames() {
		List<Game> games = new ArrayList<>();
		JSONObject result = null;
		try {
			result = new JSONObject(WebApi.getJSON("ISteamApps", "GetAppList", 2));
			log.trace(result.toString(2));
			JSONArray jsonGames = result.getJSONObject("applist").getJSONArray("apps");
			
			for (int i = 0; i < jsonGames.length(); i++) {
				Game game = new Game();
				JSONObject jsonGame = jsonGames.getJSONObject(i);
				game.setSteamId(jsonGame.getLong("appid"));
				game.setName(jsonGame.getString("name"));
				games.add(game);
			}
		} catch(Exception e) {
			log.error("Unable to query Steam Games", e);
		}
		return games;
	}
	
	@Override
	public Game getGame(long gameId) {
		return getAllGames().stream().filter(g -> g.getSteamId() == gameId).findFirst().get();
	}
	
	private boolean contains(long[] a, long n) {
		for (int i = 0; i < a.length; i++) {
			if (a[i] == n) {
				log.debug("FOUND: "+n);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public List<Game> getGames(long... gameIds) {
		return getAllGames().stream().filter(g -> contains(gameIds, g.getSteamId())).collect(Collectors.toList());
	}
}
