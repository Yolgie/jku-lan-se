package at.jku.oeh.lan.model;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import at.jku.oeh.lan.AbstractTestCase;
import at.jku.oeh.lan.laganizer.model.Game;
import at.jku.oeh.lan.laganizer.model.User;
import at.jku.oeh.lan.laganizer.model.UserState;
import at.jku.oeh.lan.laganizer.model.dao.UserDAO;

public class UserTest extends AbstractTestCase {
	private User test;
	@Autowired
	private UserDAO dao;
	
	@Before
	public void setUp() {
		Game game = new Game(0L, "Test Game");
		
		test = new User();
		test.setEmail("test@example.org");
		test.setName("Tester");
		UserState state = new UserState();
		state.setGame(game);
		state.setGameServer("127.0.0.1");
		test.setState(state);
	}
	
	@Test
	public void testGetAndSet() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Set<String> set = (Set<String>) PropertyUtils.describe(test).keySet();
		for (String name : set) {
			Object expected = PropertyUtils.getSimpleProperty(test, name);
			try {
				PropertyUtils.setSimpleProperty(test, name, expected);
				Object actual = PropertyUtils.getSimpleProperty(test, name);
				Assert.assertEquals(expected, actual);
			} catch (NoSuchMethodException ex) {
			}
		}
	}
	
	@Test
	public void saveAndLoad() {
		dao.save(test);
		Assert.assertNotEquals(0L, test.getId());
		final User actual = dao.findOne(test.getId());
		Assert.assertEquals(test, actual);
		final UserState state = test.getState();
		Assert.assertNotNull(state);
		Assert.assertEquals(state.getGame(), actual.getState().getGame());
		Assert.assertEquals(state.getGameServer(), actual.getState().getGameServer());
	}
}
