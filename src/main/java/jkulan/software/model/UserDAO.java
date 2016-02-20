package jkulan.software.model;

import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface UserDAO extends CrudRepository<User, Long> {

	public User findUserByName(String name);
}
