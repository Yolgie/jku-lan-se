package at.jku.oeh.lan.laganizer.model;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

@Autowired
private UserDAO userDAO;
@Autowired
private ClanDAO clanDAO;

public class ClanService {

    public boolean isUserInClan(User user, Clan clan) {
        return clanDAO.findClan().contains(user);
    }


    @Override
    public void addUser(long userID) {
        members.add(userID);
    }

    @Override
    public void removeUser(long userID) {
        members.remove(userID);
        user.setClan(new Clan(user));
        if (members.isEmpty()) {
        }
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String getSteamId() {
        return steamId;
    }

    public void setSteamId(String steamId) {
        this.steamId = steamId;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getSaml2Id() {
        return saml2Id;
    }

    public void setSaml2Id(String saml2Id) {
        this.saml2Id = saml2Id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString() {
        return "User: " + getName() + " with E-Mail: " + getEmail() + " and Roles: " + roles.toString();
    }


}
