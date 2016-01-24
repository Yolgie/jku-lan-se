package jkulan.software.authentication;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Handler called when authentication via OpenID Fails. Used to identify the kind of error encountered and
 * provide information to the user, about how to resolve the encountered issues.
 */
public class OpenIDAuthenticationFailurehandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        exception.printStackTrace();
        System.out.println(exception.toString());

        if(exception instanceof UsernameNotFoundException) {
            DefaultRedirectStrategy redirectStrategy = new DefaultRedirectStrategy();


        }
        super.onAuthenticationFailure(request, response, exception);
    }
}
