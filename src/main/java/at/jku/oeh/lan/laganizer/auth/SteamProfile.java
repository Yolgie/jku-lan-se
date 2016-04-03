package at.jku.oeh.lan.laganizer.auth;

import org.pac4j.core.profile.CommonProfile;


public class SteamProfile extends CommonProfile {

    long steamId;


    public long getSteamId() {
        return steamId;
    }

    public void setSteamId(long steamId) {
        this.steamId = steamId;
    }
}
