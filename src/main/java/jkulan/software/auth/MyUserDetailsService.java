package jkulan.software.auth;

import jkulan.software.model.User;
import jkulan.software.model.UserDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pac4j.springframework.security.authentication.ClientAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class MyUserDetailsService implements AuthenticationUserDetailsService<ClientAuthenticationToken> {

    @Autowired
    UserDAO userDAO;

    private static final Log log = LogFactory.getLog(MyUserDetailsService.class);

    @Override
    public UserDetails loadUserDetails(ClientAuthenticationToken token) throws UsernameNotFoundException {
        log.info("Tryed to load user Details with token "+token);
        SteamProfile profile = (SteamProfile) token.getUserProfile();
        User user = userDAO.findBySteamId(profile.steamId);

        if(user == null) {
            user = new User();
            user.setSteamId(profile.steamId);
            user.setName(profile.steamId);
            user.getRoles().add("USER");
            userDAO.save(user);
        }

        return user;
    }
}
