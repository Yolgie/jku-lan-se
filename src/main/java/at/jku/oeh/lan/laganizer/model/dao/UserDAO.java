package at.jku.oeh.lan.laganizer.model.dao;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import at.jku.oeh.lan.laganizer.model.User;

@Component
@Transactional
public interface UserDAO extends CrudRepository<User, Long> {
    User findBySteamId(long steamId);

    User findByGoogleId(String googleId);

    User findBySaml2Id(String saml2Id);

    User findUserByName(String name);
}
