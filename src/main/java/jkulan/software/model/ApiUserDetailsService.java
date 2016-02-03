package jkulan.software.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.schema.XSString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.openid.OpenIDAuthenticationToken;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;

public class ApiUserDetailsService implements UserDetailsService, AuthenticationUserDetailsService<OpenIDAuthenticationToken>,
	SAMLUserDetailsService {
	private static Log log = LogFactory.getLog(ApiUserDetailsService.class);
	
	@Autowired
	private PasswordEncoder encoder;
	
    @Value("${security.saml.adminRoleRegexp}")
    @NotNull
    private String adminRegexp;
	
	@Autowired
	private UserDAO dao;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
	    assert dao != null;
	    User user = dao.findByName(username);
	    if (user == null) {
	    	throw new UsernameNotFoundException("Unable to find user: "+username);
	    }
	    return user; 
	}
	
    @Override
    public UserDetails loadUserDetails(OpenIDAuthenticationToken token) throws UsernameNotFoundException {
    	assert dao != null;
        log.error("Hier muss entweder ein Nutzer angelegt, oder einer geladen werden");
        throw new UsernameNotFoundException("Aktuell wird kein UserDetails Object zurückgeliefert");
    }
    
    private Set<String> mapRoles(final SAMLCredential credential) {
    	final Set<String> roles = new HashSet<String>();
    	
    	assert adminRegexp != null;
    	Attribute entitlement = getAttributeByFriendlyName(credential, "eduPersonEntitlement");
    	roles.add("ROLE_USER");
    	
    	if (entitlement != null) {
	    	for (XMLObject obj : entitlement.getAttributeValues()) {
	    		if (obj instanceof XSString) {
	    			final String role = ((XSString) obj).getValue();
	    			log.trace(String.format("Matching role %s", role));
	        		if (role.matches(adminRegexp)) {
	        			log.trace("match");
	        			roles.add("ROLE_ADMIN");
	        		}
	    		}
	    	}
    	}
    	
    	return roles;
    }
    
    private User createFromCredential(final SAMLCredential credential) {
    	final User user = new User();
    	user.setName(getAttributeValue(getAttributeByFriendlyName(credential, "cn")));
    	user.setEmail(getAttributeValue(getAttributeByFriendlyName(credential, "mail")));
    	user.setAddress("N/A");
    	user.setPassword(encoder.encode(credential.getLocalEntityID() + "!" + credential.getRemoteEntityID() + "!" + credential.getNameID().getValue()));
    	user.setRoles(mapRoles(credential));
    	return user;
    }
    
    private Attribute getAttributeByFriendlyName(final SAMLCredential credential, final String name) {
    	for (Attribute attr : credential.getAttributes()) {
    		if (name.equals(attr.getFriendlyName())) {
    			return attr;
    		}
    	}
    	return null;
    }
    
    private String attributeValuesToString(final Attribute attr) {
    	final StringBuilder buf = new StringBuilder();
    	buf.append("[");
    	Iterator<XMLObject> iter = attr.getAttributeValues().iterator();
    	while (iter.hasNext()) {
    		XMLObject obj = iter.next();
    		if (obj instanceof XSString) {
	    		buf.append(((XSString) obj).getValue());
	    		if (iter.hasNext()) {
	    			buf.append(",");
	    		}
    		}
    	}
    	buf.append("]");
    	return buf.toString();
    }
    
    private List<String> getAttributeValues(final Attribute attr) {
    	final List<String> result = new ArrayList<String>();
    	final Iterator<XMLObject> iter = attr.getAttributeValues().iterator();
    	while (iter.hasNext()) {
    		XMLObject obj = iter.next();
    		if (obj instanceof XSString) {
	    		result.add(((XSString) obj).getValue());
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
    
	public Object loadUserBySAML(final SAMLCredential credential)
			throws UsernameNotFoundException {
		assert dao != null;
		// The method is supposed to identify local account of user referenced by
		// data in the SAML assertion and return UserDetails object describing the user.
		
		String userID = credential.getNameID().getValue();
		
		log.info(userID + " is logged in");

		if (log.isTraceEnabled()) {
			log.trace("User attributes");
			for (Attribute attr : credential.getAttributes()) {
				log.trace(String.format("%s: %s", attr.getFriendlyName(), attributeValuesToString(attr)));
			}
		}
		User user = dao.findByName(getAttributeValue( getAttributeByFriendlyName(credential, "cn") ));
		
		if (user == null) {
			log.info("Provisioning user...");
			user = createFromCredential(credential);
			log.trace(String.format("Newly minted user: %s", user));
			dao.save(user);
		}
		
		return user;
	}
}
