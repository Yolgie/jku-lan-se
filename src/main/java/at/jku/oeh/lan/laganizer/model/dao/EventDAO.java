package at.jku.oeh.lan.laganizer.model.dao;

import org.springframework.data.repository.CrudRepository;

import at.jku.oeh.lan.laganizer.model.Event;

import javax.transaction.Transactional;

@Transactional
public interface EventDAO extends CrudRepository<Event, Long> {

}
