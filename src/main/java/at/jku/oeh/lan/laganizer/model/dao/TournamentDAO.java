package at.jku.oeh.lan.laganizer.model.dao;

import org.springframework.data.repository.CrudRepository;

import at.jku.oeh.lan.laganizer.model.Tournament;

public interface TournamentDAO extends CrudRepository<Tournament, Long> {

}
