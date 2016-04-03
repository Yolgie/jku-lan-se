package at.jku.oeh.lan.laganizer.model.dao;

import at.jku.oeh.lan.laganizer.model.base.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public interface UserDAO extends CrudRepository<User, Long>,SteamUserDAO {
    User findBySteamId(long steamId);

    User findByGoogleId(String googleId);

    User findBySaml2Id(String saml2Id);

    User findByName(String name);

    User findById(long id);

    User findByEmail(String email);
}
