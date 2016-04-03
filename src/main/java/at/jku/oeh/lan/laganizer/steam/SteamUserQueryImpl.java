package at.jku.oeh.lan.laganizer.steam;

import com.github.koraktor.steamcondenser.exceptions.WebApiException;
import com.github.koraktor.steamcondenser.steam.community.WebApi;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SteamUserQueryImpl implements SteamUserQuery {
	private static final Log log = LogFactory.getLog(SteamUserQueryImpl.class);
	@Override
	public SteamUser getUser(long id) {
		Collection<SteamUser> out = getUsers(id);
		return (out.size() > 0) ? out.iterator().next() : null;
	}

	@Override
	public Collection<SteamUser> getUsers(Collection<Long> ids) {
		return createUsers(ids);
	}

	@Override
	public Collection<SteamUser> getUsers(long... ids) {
		List<Long> input = Arrays.asList(ArrayUtils.toObject(ids));
		return createUsers(input);
	}


	private Collection<SteamUser> createUsers(Collection<Long> ids) {
		Map<String,Object> params = new HashMap<>();
		List<SteamUser> out = new ArrayList<>();
		JSONArray steamIds = new JSONArray(ids);
		params.put("steamids", steamIds);
		try {
			JSONObject result = new JSONObject(WebApi.getJSON("ISteamUser", "GetPlayerSummaries", 2, params));
			log.trace("Steam API call result: "+result.toString(2));
			JSONArray players = result.getJSONObject("response").getJSONArray("players");
			for (int i=0; i<players.length();i++) {
				out.add(new SteamUser(players.getJSONObject(i)));
			}
		} catch (JSONException | WebApiException e) {
			log.error("Error querying Steam API", e);
		}
		return out;
	}

}
