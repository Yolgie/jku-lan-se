package at.jku.oeh.lan.laganizer.repository;

import at.jku.oeh.lan.laganizer.model.Game;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Custom Repository written to persist a Game. To my mind, this should have the same functionality as
 * the GameDAO object. But for some reason the CrudRepository runs into race conditions that are avoided by this
 * implementation.
 *
 * This is an ugly and possible very bad performing solution, but it is working.
 * TODO: Move this back to the GameDAO as soon as a way to do so has been found.
 */
@Repository
public class GameRepo {

    @PersistenceContext
    EntityManager entityManager;

    protected Session getCurrentSession() {
        return entityManager.unwrap(Session.class);
    }

    public void save(Game g) {
        Session session = getCurrentSession();
        session.saveOrUpdate(g);
    }

    public void save(Iterable<Game> games) {
        Session session = getCurrentSession();
        for(Game g: games) {
            session.saveOrUpdate(g);
        }
    }
}
