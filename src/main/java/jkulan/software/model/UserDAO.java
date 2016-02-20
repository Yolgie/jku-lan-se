package jkulan.software.model;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

/**
 * A DAO for the entity User is simply created by extending the CrudRepository
 * interface provided by spring. The following methods are some of the ones
 * available from such interface: save, delete, deleteAll, findOne and findAll.
 * The magic is that such methods must not be implemented, and moreover it is
 * possible create new query methods working only by defining their signature!
 *
 * @author netgloo
 */
@Transactional
public interface UserDAO extends CrudRepository<User, Long> {

    /**
     * Return the jkulan.software.model having the passed address or null if no jkulan.software.model is found.
     *
     * @param address the jkulan.software.model address.
     */
    public User findByAddress(String address);

} // class UserDao
