package at.jku.oeh.lan.laganizer.model.dao;

import at.jku.oeh.lan.laganizer.model.events.tournament.team.Team;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface TeamDAO extends CrudRepository<Team, Long> {

    Team findTeamById(long id);

    Team findTeamByName(String name);
}
