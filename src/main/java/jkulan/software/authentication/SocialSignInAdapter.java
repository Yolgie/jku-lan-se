package jkulan.software.authentication;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;

public class SocialSignInAdapter implements SignInAdapter {
	private static Log log = LogFactory.getLog(SocialSignInAdapter.class);
	
	@Override
	public String signIn(String userId, Connection<?> connection, NativeWebRequest request) {
		log.debug("User: "+userId);
		SecurityContextHolder.getContext().setAuthentication(
	            new UsernamePasswordAuthenticationToken(userId, null, null));
		return null;
	}

}
