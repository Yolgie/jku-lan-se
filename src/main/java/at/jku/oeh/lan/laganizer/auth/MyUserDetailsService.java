package at.jku.oeh.lan.laganizer.auth;

import at.jku.oeh.lan.laganizer.model.base.User;
import at.jku.oeh.lan.laganizer.model.base.UserNotFoundException;
import at.jku.oeh.lan.laganizer.model.base.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.schema.XSString;
import org.opensaml.saml.saml2.core.Attribute;
import org.pac4j.oidc.profile.OidcProfile;
import org.pac4j.saml.credentials.SAML2Credentials;
import org.pac4j.springframework.security.authentication.ClientAuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyUserDetailsService implements AuthenticationUserDetailsService<ClientAuthenticationToken> {

    @Autowired
    private UserService userService;

//    @Value("${saml.adminRoleRegexp}")
//    @NotNull
//    private String adminRegexp;

    private static final Log log = LogFactory.getLog(MyUserDetailsService.class);


    @Override
    public UserDetails loadUserDetails(ClientAuthenticationToken token) throws UsernameNotFoundException {
        log.info("Tried to load user Details with token " + token);

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
        try {
            return userService.findUserBySteamId(profile.steamId);
        }catch(UserNotFoundException e) {
            User user = userService.createUser(Long.toString(profile.steamId));
            user.setSteamId(profile.steamId);
            return user;
        }
    }

    private UserDetails getGoogleUserDetails(OidcProfile profile) {
        try{
            return userService.findUserByGoogleID(profile.getId());
        }catch( UserNotFoundException e) {

            User user = userService.createUser((String) profile.getAttribute("name"));
            user.setGoogleId(profile.getId());
            user.setEmail(profile.getEmail());
            return user;
        }
    }

    private UserDetails getSamlClientDetails(ClientAuthenticationToken token) {
        SAML2Credentials credentials = (SAML2Credentials) token.getCredentials();
        String userID = credentials.getNameId().getValue();

        try{
            return userService.findUserBySaml2ID(userID);
        }catch( UserNotFoundException e) {
            User user = userService.createUser(getAttributeValue(getAttributeByFriendlyName(credentials, "cn")));
            user.setSaml2Id(userID);
            user.setEmail(getAttributeValue(getAttributeByFriendlyName(credentials, "mail")));
            return user;
        }
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


    //TODO: We could automatically assign roles based on SAML2 with this function. Not in use for first versions.
//    private Set<String> mapRoles(final SAML2Credentials credential) {
//        final Set<String> roles = new HashSet<>();
//
//        assert adminRegexp != null;
//        Attribute entitlement = getAttributeByFriendlyName(credential, "eduPersonEntitlement");
//        roles.add("ROLE_USER");
//
//        if (entitlement != null) {
//            Pattern PATTERN_ADMIN = Pattern.compile(adminRegexp);
//            for (XMLObject obj : entitlement.getAttributeValues()) {
//                if (obj instanceof XSString) {
//                    final String role = ((XSString) obj).getValue();
//                    log.trace(String.format("Matching role %s", role));
//
//                    if (PATTERN_ADMIN.matcher(role).matches()) {
//                        log.trace("match");
//                        roles.add("ROLE_ADMIN");
//                    }
//                }
//            }
//        }
//
//        return roles;
//    }

    /*
     * /OpenSAML helper methods
     */

}
