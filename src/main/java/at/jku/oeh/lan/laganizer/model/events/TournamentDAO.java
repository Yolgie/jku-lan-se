package at.jku.oeh.lan.laganizer.model.events;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface TournamentDAO extends CrudRepository<Tournament, Long> {

}
