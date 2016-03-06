package at.jku.oeh.lan;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import at.jku.oeh.lan.model.UserTest;
import at.jku.oeh.lan.steam.SteamGameTest;
import at.jku.oeh.lan.steam.SteamSyncTest;
import at.jku.oeh.lan.steam.SteamUserTest;

@RunWith(Suite.class)	
@SuiteClasses({SteamGameTest.class, SteamUserTest.class, SteamSyncTest.class, UserTest.class})
public class TestSuite {

}
