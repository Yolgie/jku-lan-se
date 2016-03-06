package at.jku.oeh.lan.laganizer.model.events;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
@Transactional
public interface TeamDAO extends CrudRepository<Team, Long> {

    Team findTeamById(long id);

    Team findTeamByName(String name);
}
