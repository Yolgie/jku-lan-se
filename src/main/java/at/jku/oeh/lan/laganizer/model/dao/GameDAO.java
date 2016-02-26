package at.jku.oeh.lan.laganizer.model.dao;

import org.springframework.data.repository.CrudRepository;

import at.jku.oeh.lan.laganizer.model.Game;

public interface GameDAO extends CrudRepository<Game, Long> {

}
