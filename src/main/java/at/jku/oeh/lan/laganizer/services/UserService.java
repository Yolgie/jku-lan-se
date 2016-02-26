package at.jku.oeh.lan.laganizer.services;

import at.jku.oeh.lan.laganizer.model.Clan;
import at.jku.oeh.lan.laganizer.model.ClanDAO;
import at.jku.oeh.lan.laganizer.model.User;
import at.jku.oeh.lan.laganizer.model.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


public class UserService implements ClanServiceInterface {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private ClanDAO clanDAO;

    //TODO Define UserServiceInterface
    public User createUser() {
        User user = new User();
        Clan clan = new Clan();
        clan.setName(user.getName());
        clan.addUser(user);

        user.setClan(clan);

        userDAO.save(user);
        clanDAO.save(clan);

        return user;
    }

    public Clan renameClan(Clan clan, String name) {
        if (validateClanName(name)) {
            clan.setName(name);
            clanDAO.save(clan);
        }
        return clan;
    }

    public void addUserToClan(User user, Clan clan) {
        Clan oldClan = userDAO.findOne(user.getId()).getClan();
        removeIfEmpty(oldClan);

        clan.addUser(user);
        user.setClan(clan);

        clanDAO.save(clan);
        userDAO.save(user);
        return;
    }

    public void removeUserFromClan(User user, Clan clan) {
        Clan oldClan = userDAO.findOne(user.getId()).getClan();
        if (!oldClan.equals(clan)) {
            return;
        }

        oldClan.removeUser(user);
        removeIfEmpty(oldClan);

        Clan newClan = new Clan();
        newClan.setName(user.getName());
        newClan.addUser(user);
        user.setClan(newClan);

        clanDAO.save(newClan);
        userDAO.save(user);
    }

    public Clan getClan(User user) {
        return userDAO.findOne(user.getId()).getClan();
    }

    public List<User> getMember(Clan clan) {
        return clanDAO.findOne(clan.getId()).getMembers();
    }

    /**
     * Checks, if a clan has members. Deletes clan from DB, if count < 1
     */
    private boolean removeIfEmpty(Clan clan) {
        if (clan.countMembers() > 0) {
            return true;
        }

        clanDAO.delete(clan);
        return false;
    }

    /**
     * Checks, if a clan name meets given constraints
     */
    private boolean validateClanName(String name) {
        //TODO validate complexity of clan names (RegEx?)
        return name.length() >= 3;
    }
}
