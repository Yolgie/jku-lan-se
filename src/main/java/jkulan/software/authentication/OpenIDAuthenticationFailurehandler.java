package jkulan.software.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

/**
 * Handler called when authentication via OpenID Fails. Used to identify the kind of error encountered and
 * provide information to the user, about how to resolve the encountered issues.
 */
public class OpenIDAuthenticationFailurehandler extends SimpleUrlAuthenticationFailureHandler {
	private static Log log = LogFactory.getLog(OpenIDAuthenticationFailurehandler.class);
	
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
    	log.error("Unable to authenticate user via OpenID, redirecting to login page", exception);
        if(exception instanceof UsernameNotFoundException) {
            DefaultRedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
            redirectStrategy.sendRedirect(request, response, "/login?error=user-not-found");
        }
        super.onAuthenticationFailure(request, response, exception);
    }
}
