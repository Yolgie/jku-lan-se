package at.jku.oeh.lan.laganizer.steam;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import at.jku.oeh.lan.laganizer.model.User;
import at.jku.oeh.lan.laganizer.model.dao.GameDAO;
import at.jku.oeh.lan.laganizer.model.dao.TransactionalConsumer;
import at.jku.oeh.lan.laganizer.model.dao.UserDAO;

import com.github.koraktor.steamcondenser.steam.community.WebApi;

@Service
public class SteamSync implements InitializingBean {
	private static final Log log = LogFactory.getLog(SteamSync.class);
	
	@Value("${steam.apiKey}")
	@NotNull
	private String apiKey;
	
	@Autowired
	private UserDAO userDao;
	@Autowired
	private GameDAO gameDao;
	
	@Autowired
	private SteamGameQuery gameQuery;
	
	@Autowired
	private SteamUserQuery userQuery;
	
	
	public void syncUserStates() {
		userDao.iterateOverSteamUsers(new TransactionalConsumer<List<User>>() {
			@Override
			public void accept(List<User> users) {
				List<Long> ids = users.stream().map(u -> u.getSteamId()).collect(Collectors.toList());
				List<SteamUser> players = userQuery.getUsers(ids);
				if (players.size() == users.size()) { 
					for (User u : users) {
						SteamUser player = players.stream().filter(p -> p.getId() == u.getSteamId()).findFirst().get();
						log.debug("Steam player data: "+player);
						u.setSteamAvatarUrl(player.getAvatarFullUrl());
						u.setSteamVisible(player.getCommunityVisibilityState() >= 3);
					}
				}
			}
		});
	}
	
	public void syncGames() {
		gameDao.save(gameQuery.getAllGames());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		WebApi.setApiKey(apiKey);
	}
}
