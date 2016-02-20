package jkulan.software.model;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface ArrivalLogDAO extends CrudRepository<ArrivalLog, Long> {

}