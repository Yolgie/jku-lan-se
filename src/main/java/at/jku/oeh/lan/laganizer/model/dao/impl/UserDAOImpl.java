package at.jku.oeh.lan.laganizer.model.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.jku.oeh.lan.laganizer.model.User;
import at.jku.oeh.lan.laganizer.model.dao.SteamUserDAO;
import at.jku.oeh.lan.laganizer.model.dao.TransactionalConsumer;

@Service
public class UserDAOImpl implements SteamUserDAO {
	@Autowired
	private EntityManager em;
	
	@Override	
    public List<User> findAllSteamUsers() {
    	TypedQuery<User> q = em.createQuery("from User u where u.steamId != null", User.class);
    	return q.getResultList();
    }
	
	private List<User> getAllUsersIterable(int offset, int max)
    {
        return em.createQuery("from User u", User.class).setFirstResult(offset).setMaxResults(max).getResultList();
    }
	
	@Override
	public void iterateOverSteamUsers(TransactionalConsumer<List<User>> action) {
		int offset = 0;

	    List<User> users;
	    while ((users = getAllUsersIterable(offset, 100)).size() > 0)
	    {
        	action.accept(users);
	        offset += users.size();
	    }
	}
}
