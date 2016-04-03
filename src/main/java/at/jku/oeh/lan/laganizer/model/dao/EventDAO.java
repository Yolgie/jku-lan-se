package at.jku.oeh.lan.laganizer.model.dao;

import at.jku.oeh.lan.laganizer.model.events.Event;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface EventDAO extends CrudRepository<Event, Long> {

}
