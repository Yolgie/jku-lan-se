package at.jku.oeh.lan.laganizer.model.dao;

import org.springframework.data.repository.CrudRepository;

import at.jku.oeh.lan.laganizer.model.ArrivalLog;

import javax.transaction.Transactional;

@Transactional
public interface ArrivalLogDAO extends CrudRepository<ArrivalLog, Long> {

}