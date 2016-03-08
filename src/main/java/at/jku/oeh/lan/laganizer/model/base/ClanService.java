package at.jku.oeh.lan.laganizer.model.base;

import at.jku.oeh.lan.laganizer.model.util.Converter;
import at.jku.oeh.lan.laganizer.model.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
class ClanService {
    @Autowired
    private ClanDAO clanDAO;

    public Clan findClanById(long id) throws ClanNotFoundException {
        Clan clan = clanDAO.findClanById(id);
        if (clan == null) {
            throw new ClanNotFoundException("Clan with ID " + id + " can't be found");
        }
        return clan;
    }

    public Clan findClanByName(String name) throws ClanNotFoundException {
        Clan clan = clanDAO.findClanByName(name);
        if (clan == null) {
            throw new ClanNotFoundException("Clan '" + name + "' can't be found");
        }
        return clan;
    }

    public Set<Clan> listClans() {
        return Converter.iterableToSet(clanDAO.findAll());
    }

    public Clan renameClan(long id, String name) throws InvalidClanNameException, ClanNotFoundException {
        if (Validator.isValidClanName(name)) {
            Clan clan = findClanById(id);
            clan.setName(name);
            clanDAO.save(clan);
            return clan;
        } else {
            throw new InvalidClanNameException(name);
        }
    }

    Clan createClan(User user) {
        Clan clan = new Clan();
        clan.setName("<" + user.getName() + ">");
        clan.addUser(user);
        clanDAO.save(clan);
        return clan;
    }

    void addUser(Clan clan, User user) {
        clan.addUser(user);
        clanDAO.save(clan);
    }

    void removeUser(Clan clan, User user) {
        clan.removeUser(user);
        if (clan.getUsers().size() > 0) {
            clanDAO.save(clan);
        } else {
            clanDAO.delete(clan);
        }
    }
}
