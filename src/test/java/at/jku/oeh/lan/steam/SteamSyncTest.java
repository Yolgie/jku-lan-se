package at.jku.oeh.lan.steam;

import at.jku.oeh.lan.AbstractTestCase;
import at.jku.oeh.lan.laganizer.model.User;
import at.jku.oeh.lan.laganizer.model.dao.GameDAO;
import at.jku.oeh.lan.laganizer.model.dao.UserDAO;
import at.jku.oeh.lan.laganizer.steam.SteamSync;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;

@DirtiesContext(methodMode = DirtiesContext.MethodMode.BEFORE_METHOD)
public class SteamSyncTest extends AbstractTestCase {

	@Autowired
	private SteamSync sync;
	
	@Autowired
	private GameDAO gameDao;

	@Autowired
	private UserDAO userDao;


	@Test
	public void testSyncUserStates() {
		User fuero = new User();
		fuero.setEmail("fuerob@example.org");
		fuero.setSteamId(76561198048409050L);
		fuero.setName("Fuero");
		User robin = new User();
		robin.setEmail("robinb@example.org");
		robin.setSteamId(76561197960435530L);
		robin.setName("Robin");
		userDao.save(fuero);
		userDao.save(robin);
		sync.syncUserStates();
		for (User user : userDao.findAll()) {
			Assert.assertNotNull(user.getSteamAvatarUrl());
		}
	}
	
	@Test
	public void testSyncGames() {
		gameDao.deleteAll();
		sync.syncGames();
		Assert.assertTrue(gameDao.count() > 0);
	}
}
