package at.jku.oeh.lan.laganizer.auth;

import at.jku.oeh.lan.laganizer.model.User;
import at.jku.oeh.lan.laganizer.model.dao.UserDAO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.schema.XSString;
import org.opensaml.saml.saml2.core.Attribute;
import org.pac4j.oidc.profile.OidcProfile;
import org.pac4j.saml.credentials.SAML2Credentials;
import org.pac4j.springframework.security.authentication.ClientAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Component
public class MyUserDetailsService implements AuthenticationUserDetailsService<ClientAuthenticationToken> {

    @Autowired
    UserDAO userDAO;

    @Value("${saml.adminRoleRegexp}")
    @NotNull
    private String adminRegexp;


    private static final Log log = LogFactory.getLog(MyUserDetailsService.class);


    @Override
    public UserDetails loadUserDetails(ClientAuthenticationToken token) throws UsernameNotFoundException {
        log.info("Tryed to load user Details with token " + token);

        switch (token.getClientName()) {
            case "googleClient":
                return getGoogleUserDetails((OidcProfile) token.getUserProfile());
            case "SteamClient":
                return getSteamUserDetails((SteamProfile) token.getUserProfile());
            case "SAML2Client":
                return getSamlClientDetails(token);
            default:
                throw new UsernameNotFoundException("Unknown Client cannot be used to find user");
        }

    }

    private UserDetails getSteamUserDetails(SteamProfile profile) {
        User user = userDAO.findBySteamId(profile.steamId);

        if (user == null) {
            user = new User();
            user.setSteamId(profile.steamId);
            user.setName(Long.toString(profile.steamId));
            user.getRoles().add("USER");
            userDAO.save(user);
        }
        return user;
    }

    private UserDetails getGoogleUserDetails(OidcProfile profile) {
        User user = userDAO.findByGoogleId(profile.getId());

        if (user == null) {
            user = new User();
            user.setGoogleId(profile.getId());
            user.setEmail(profile.getEmail());
            user.setName(profile.getUsername());
            user.getRoles().add("USER");
            userDAO.save(user);
        }
        return user;
    }

    private UserDetails getSamlClientDetails(ClientAuthenticationToken token) {
        SAML2Credentials credentials = (SAML2Credentials) token.getCredentials();
        String userID = credentials.getNameId().getValue();
        User user = userDAO.findBySaml2Id(userID);
        if (user == null) {
            user = createFromCredential(credentials);
        }
        return user;
    }

    /*
     * OpenSAML helper methods
     */
    private User createFromCredential(final SAML2Credentials credential) {
        final User user = new User();
        user.setName(getAttributeValue(getAttributeByFriendlyName(credential, "cn")));
        user.setEmail(getAttributeValue(getAttributeByFriendlyName(credential, "mail")));
        user.setRoles(mapRoles(credential));
        return user;
    }


    private Attribute getAttributeByFriendlyName(final SAML2Credentials credential, final String name) {
        for (Attribute attr : credential.getAttributes()) {
            if (name.equals(attr.getFriendlyName())) {
                return attr;
            }
        }
        return null;
    }

    private List<String> getAttributeValues(final Attribute attr) {
        final List<String> result = new ArrayList<>();
        for (XMLObject o : attr.getAttributeValues()) {
            if (o instanceof XSString) {
                result.add(((XSString) o).getValue());
            }
        }
        return result;
    }

    private String getAttributeValue(final Attribute attr) {
        final List<String> result = getAttributeValues(attr);
        if (result.size() >= 1) {
            return result.get(0);
        }
        return null;
    }

    private Set<String> mapRoles(final SAML2Credentials credential) {
        final Set<String> roles = new HashSet<>();

        assert adminRegexp != null;
        Attribute entitlement = getAttributeByFriendlyName(credential, "eduPersonEntitlement");
        roles.add("ROLE_USER");

        if (entitlement != null) {
            Pattern PATTERN_ADMIN = Pattern.compile(adminRegexp);
            for (XMLObject obj : entitlement.getAttributeValues()) {
                if (obj instanceof XSString) {
                    final String role = ((XSString) obj).getValue();
                    log.trace(String.format("Matching role %s", role));

                    if (PATTERN_ADMIN.matcher(role).matches()) {
                        log.trace("match");
                        roles.add("ROLE_ADMIN");
                    }
                }
            }
        }

        return roles;
    }

    /*
     * /OpenSAML helper methods
     */

}
