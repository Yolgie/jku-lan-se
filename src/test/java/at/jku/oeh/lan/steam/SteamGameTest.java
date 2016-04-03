package at.jku.oeh.lan.steam;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.github.koraktor.steamcondenser.exceptions.WebApiException;
import com.github.koraktor.steamcondenser.steam.community.WebApi;

import at.jku.oeh.lan.AbstractTestCase;
import at.jku.oeh.lan.laganizer.model.Game;
import at.jku.oeh.lan.laganizer.model.dao.GameDAO;
import at.jku.oeh.lan.laganizer.steam.SteamGameQuery;

public class SteamGameTest extends AbstractTestCase {
	@Value("${steam.apiKey}")
	private String apiKey = "dummy";
	
	@Autowired
	private SteamGameQuery query;
	
	@Autowired
	private GameDAO dao;
	
	@Before
	public void setApiKey() throws WebApiException {
		Assert.assertNotEquals("dummy", apiKey);
		WebApi.setApiKey(apiKey);
		WebApi.setSecure(true);
	}
	
	@Test
	public void testGetAndSet() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Game game = new Game(236110L, "Dungeon Defenders II");
		Set<String> set = (Set<String>) PropertyUtils.describe(game).keySet();
		for (String name : set) {
			Object expected = PropertyUtils.getSimpleProperty(game, name);
			try {
				PropertyUtils.setSimpleProperty(game, name, expected);
				Object actual = PropertyUtils.getSimpleProperty(game, name);
				Assert.assertEquals(expected, actual);
			} catch (NoSuchMethodException ex) {
			}
		}
	}
	
	@Test
	public void lookupAllGames() {
		List<Game> games = query.getAllGames();
		Assert.assertNotNull(games);
		Assert.assertTrue(games.size() >= 1000);
	}
	
	@Test
	public void lookupOneGame() {
		Game game = query.getGame(98800);
		Assert.assertNotNull(game);
		Assert.assertEquals("Dungeons of Dredmor", game.getName());
	}

	@Test
	public void lookupMultipleGames() {
		long[] ids = { 
			3820, // BloodRayne 2
			236110 // Dungeon Defenders II
		};
		List<Game> games = query.getGames(ids);
		Assert.assertNotNull(games);
		Assert.assertEquals(2, games.size());
		for (long id : ids) {
			Assert.assertTrue(games.stream().anyMatch(g -> g.getSteamId() == id));
		}
	}
	
	@Test
	public void saveGame() {
		Game game = query.getGame(98800);
		Assert.assertNotNull(game);
		Assert.assertNotNull(query);
		dao.save(game);
		Game load = dao.findOne(98800L);
		Assert.assertEquals(game, load);
	}
}
