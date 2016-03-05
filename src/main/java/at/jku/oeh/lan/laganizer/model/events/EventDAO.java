package at.jku.oeh.lan.laganizer.model.events;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.transaction.Transactional;

@Component
@Transactional
public interface EventDAO extends CrudRepository<Event, Long> {

}
