package at.jku.oeh.lan.laganizer.steam;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import at.jku.oeh.lan.laganizer.model.Game;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.github.koraktor.steamcondenser.steam.community.WebApi;

import at.jku.oeh.lan.laganizer.model.User;
import at.jku.oeh.lan.laganizer.model.dao.GameDAO;
import at.jku.oeh.lan.laganizer.model.dao.UserDAO;

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
	
	@Autowired
	private SteamUserConsumer consumer;
	
	/**
	 * Ugly, but @Transational isn't inherited by concrete classes. So we need a helper
	 * class to utilize it.
	 * @author fuero
	 */
	@Component
	@Scope("prototype")
	private class SteamUserConsumer implements Consumer<List<User>> {
		@Autowired
		public SteamUserConsumer() {}
		@Override
		@Transactional
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
		
	}
	
	/**
	 * Run every 10 minutes
	 */
//	@Async
	@Scheduled(fixedRate=600000)
	public void syncUserStates() {
		userDao.iterateOverSteamUsers(consumer);
	}
	
	/**
	 * Run every 24 hours
	 */
//	@Async
	@Scheduled(fixedRate=86400000)
	public void syncGames() {
		gameDao.save(gameQuery.getAllGames());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		WebApi.setApiKey(apiKey);
	}
}
