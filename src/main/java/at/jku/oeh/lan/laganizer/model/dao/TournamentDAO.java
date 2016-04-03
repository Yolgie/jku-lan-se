package at.jku.oeh.lan.laganizer.model.dao;

import at.jku.oeh.lan.laganizer.model.events.tournament.Tournament;
import org.springframework.data.repository.CrudRepository;

public interface TournamentDAO extends CrudRepository<Tournament, Long> {

    Tournament findByGame(String game);
}
