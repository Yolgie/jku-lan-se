package at.jku.oeh.lan.laganizer.steam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;
import org.springframework.stereotype.Component;

import com.github.koraktor.steamcondenser.exceptions.WebApiException;
import com.github.koraktor.steamcondenser.steam.community.WebApi;

@Component
public class SteamUserQueryImpl implements SteamUserQuery {
	private static final Log log = LogFactory.getLog(SteamUserQueryImpl.class);
	@Override
	public SteamUser getUser(long id) {
		List<SteamUser> out = getUsers(id);
		return (out.size() > 0) ? out.get(0) : null;
	}

	@Override
	public List<SteamUser> getUsers(List<Long> ids) {
		long[] aids = new long[ids.size()];
		for (int i = 0; i < aids.length; i++) {
			aids[i] = ids.get(i);
		}
		return getUsers(aids);

	}

	@Override
	public List<SteamUser> getUsers(long... ids) {
		Map<String,Object> params = new HashMap<>();
		List<SteamUser> out = new ArrayList<>();
		JSONArray steamIds = new JSONArray(ids);
		params.put("steamids", steamIds);
		try {
			JSONObject result = new JSONObject(WebApi.getJSON("ISteamUser", "GetPlayerSummaries", 2, params));
			log.trace("Steam API call result: "+result.toString(2));
			JSONArray players = result.getJSONObject("response").getJSONArray("players");
			Assert.assertEquals(ids.length, players.length());
			for (int i=0; i<players.length();i++) {
				out.add(new SteamUser(players.getJSONObject(i)));
			}
		} catch (JSONException | WebApiException e) {
			log.error("Error querying Steam API", e);
		}
		return out;
	}

}
