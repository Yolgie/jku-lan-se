package at.jku.oeh.lan.laganizer.model.dao;

import at.jku.oeh.lan.laganizer.model.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameDAO extends CrudRepository<Game, Long> {

}
