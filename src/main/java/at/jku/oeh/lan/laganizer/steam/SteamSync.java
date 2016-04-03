package at.jku.oeh.lan.laganizer.steam;

import at.jku.oeh.lan.laganizer.model.Game;
import at.jku.oeh.lan.laganizer.model.base.User;
import at.jku.oeh.lan.laganizer.model.base.UserService;
import at.jku.oeh.lan.laganizer.model.dao.GameDAO;
import at.jku.oeh.lan.laganizer.model.dao.UserDAO;
import at.jku.oeh.lan.laganizer.repository.GameRepo;
import com.github.koraktor.steamcondenser.steam.community.WebApi;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SteamSync implements InitializingBean {
	private static final Log log = LogFactory.getLog(SteamSync.class);
	
	@Value("${steam.apiKey}")
	@NotNull
	private String apiKey;
	
	@Autowired
	private UserDAO userDao;

	@Autowired
	private UserService userService;

	@Autowired
	private GameDAO gameDao;
	
	@Autowired
	private SteamGameQuery gameQuery;
	
	@Autowired
	private SteamUserQuery userQuery;

    @Autowired
    private GameRepo gameRepo;

//	TODO:TTH: Do not see a real need for this construct as of now. Removed it from this branch.
//	/**
//	 * Ugly, but @Transational isn't inherited by concrete classes. So we need a helper
//	 * class to utilize it.
//	 * @author fuero
//	 */
//	@Component
//	@Scope("prototype")
//	private class SteamUserConsumer implements Consumer<List<User>> {
//		@Autowired
//		public SteamUserConsumer() {}
//		@Override
//		@Transactional
//		public void accept(List<User> users) {
//			List<Long> ids = users.stream().map(u -> u.getSteamId()).collect(Collectors.toList());
//			List<SteamUser> players = userQuery.getUsers(ids);
//			if (players.size() == users.size()) {
//				for (User u : users) {
//					SteamUser player = players.stream().filter(p -> p.getId() == u.getSteamId()).findFirst().get();
//					log.debug("Steam player data: "+player);
//					u.setSteamAvatarUrl(player.getAvatarFullUrl());
//					u.setSteamVisible(player.getCommunityVisibilityState() >= 3);
//				}
//			}
//		}
//
//	}
	
	/**
	 * Run every 10 minutes
	 */
	@Scheduled(fixedRate=600000)
	public void syncUserStates() {
		Set<User> steamUsers = userService.findAllSteamUsers();
		List<Long> ids = steamUsers.stream().map(u -> u.getSteamId()).collect(Collectors.toList());
		Collection<SteamUser> players = userQuery.getUsers(ids);
		if(players.size() == steamUsers.size()) {
			for(User u: steamUsers) {
				SteamUser player = players.stream().filter(p -> p.getId() == u.getSteamId()).findFirst().get();
				log.debug("Steam player data: "+player);
				u.setSteamAvatarUrl(player.getAvatarFullUrl());
				u.setSteamVisible(player.getCommunityVisibilityState() >= 3);
			}
		}
	}
	
	/**
	 * Run every 24 hours
	 */
	@Scheduled(fixedRate=86400000)
	public void syncGames() {
		List<Game> allGames = gameQuery.getAllGames();
		gameRepo.save(allGames);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		WebApi.setApiKey(apiKey);
	}
}
