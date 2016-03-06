package at.jku.oeh.lan.laganizer.model.base;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@Transactional
public interface UserDAO extends CrudRepository<User, Long> {
    User findByName(String name);

    User findById(long id);

    User findByEmail(String email);

    User findBySteamId(String steamId);

    User findByGoogleId(String googleId);

    User findBySaml2Id(String saml2Id);
}
