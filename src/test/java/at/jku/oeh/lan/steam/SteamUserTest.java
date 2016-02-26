package at.jku.oeh.lan.steam;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import at.jku.oeh.lan.AbstractTestCase;
import at.jku.oeh.lan.laganizer.steam.SteamUser;
import at.jku.oeh.lan.laganizer.steam.SteamUserQuery;

import com.github.koraktor.steamcondenser.exceptions.WebApiException;
import com.github.koraktor.steamcondenser.steam.community.WebApi;

public class SteamUserTest extends AbstractTestCase {
	private SteamUser robin = new SteamUser(
		76561197960435530L,
		(short) 3,
		(short) 1,
		"Robin",
		new Date(1456476397),
		"http://steamcommunity.com/id/robinwalker/",
		"https://steamcdn-a.akamaihd.net/steamcommunity/public/images/avatars/f1/f1dd60a188883caf82d0cbfccfe6aba0af1732d4.jpg",
		"https://steamcdn-a.akamaihd.net/steamcommunity/public/images/avatars/f1/f1dd60a188883caf82d0cbfccfe6aba0af1732d4_medium.jpg",
		"https://steamcdn-a.akamaihd.net/steamcommunity/public/images/avatars/f1/f1dd60a188883caf82d0cbfccfe6aba0af1732d4_full.jpg",
		(short) 0,
		"Robin Walker",
		"103582791429521412",
		new Date(1063407589),
		(short) 0,
		"US",
		"WA",
		3961L
	);
	
	@Value("${steam.apiKey}")
	private String apiKey = "dummy";
	
	@Autowired
	private SteamUserQuery query;
	
	@Before
	public void setApiKey() throws WebApiException {
		Assert.assertNotEquals("dummy", apiKey);
		WebApi.setApiKey(apiKey);
		WebApi.setSecure(true);
	}
	
	@Test
	public void testGetAndSet() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Set<String> set = (Set<String>) PropertyUtils.describe(robin).keySet();
		for (String name : set) {
			Object expected = PropertyUtils.getSimpleProperty(robin, name);
			try {
				PropertyUtils.setSimpleProperty(robin, name, expected);
				Object actual = PropertyUtils.getSimpleProperty(robin, name);
				Assert.assertEquals(expected, actual);
			} catch (NoSuchMethodException ex) {
			}
		}
	}
	
	@Test
	public void queryUser() {
		SteamUser actual = query.getUser(robin.getId());
		Assert.assertEquals(robin, actual);
	}
	@Test
	public void queryUsers() {
		long[] ids = { 
			robin.getId(), 		
			76561198048409050L // fuero
		};
		List<SteamUser> users = query.getUsers(ids);
		Assert.assertNotNull(users);
		Assert.assertEquals(2, users.size());
		for (long id : ids) {
			Assert.assertTrue(users.stream().anyMatch(u -> u.getId() == id));
		}
	}
}
