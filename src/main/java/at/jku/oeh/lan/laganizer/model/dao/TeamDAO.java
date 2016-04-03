package at.jku.oeh.lan.laganizer.model.dao;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import at.jku.oeh.lan.laganizer.model.Team;

@Transactional
public interface TeamDAO extends CrudRepository<Team, Long> {

}
