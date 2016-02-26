package at.jku.oeh.lan.laganizer.services;

import java.util.*;

import at.jku.oeh.lan.laganizer.model.Clan;
import at.jku.oeh.lan.laganizer.model.User;

public interface ClanServiceInterface {
    public void addUserToClan(User User, Clan clan);

    public void removeUserFromClan(User user, Clan clan);

    public Clan getClan(User user);

    public Clan renameClan(Clan clan, String name);

    public List<User> getMember(Clan clan);
}
