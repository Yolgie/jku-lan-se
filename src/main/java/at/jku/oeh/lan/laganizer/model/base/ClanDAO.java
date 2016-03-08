package at.jku.oeh.lan.laganizer.model.base;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public interface ClanDAO extends CrudRepository<Clan, Long> {
    Clan findClanByName(String name);

    Clan findClanById(long id);
}
