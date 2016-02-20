package jkulan.software.model;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

@Transactional
public interface UserDAO extends CrudRepository<User, Long> {

}
