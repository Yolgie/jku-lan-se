package at.jku.oeh.lan.laganizer.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public interface ClanDAO extends CrudRepository<User, Long> {
    User[] findUsersByClan(String cid);

    Clan findClanByUser(String uuid);

    Clan findClanByName(String name);
}
