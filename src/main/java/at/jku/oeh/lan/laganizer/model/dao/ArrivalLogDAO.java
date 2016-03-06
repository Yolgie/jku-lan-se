package at.jku.oeh.lan.laganizer.model.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import at.jku.oeh.lan.laganizer.model.actionlog.ArrivalLog;

@Transactional
public interface ArrivalLogDAO extends CrudRepository<ArrivalLog, Long> {
}