package jkulan.software.auth;

import org.pac4j.core.profile.CommonProfile;


public class SteamProfile extends CommonProfile {

    String steamId;


    public String getSteamId() {
        return steamId;
    }

    public void setSteamId(String steamId) {
        this.steamId = steamId;
    }
}
