package at.jku.oeh.lan.laganizer.model.actionlog;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public interface ArrivalLogDAO extends CrudRepository<ArrivalLog, Long> {

}
