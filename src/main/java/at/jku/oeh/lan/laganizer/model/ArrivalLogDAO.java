package at.jku.oeh.lan.laganizer.model;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface ArrivalLogDAO extends CrudRepository<ArrivalLog, Long> {

}
