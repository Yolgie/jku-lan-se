package at.jku.oeh.lan.laganizer.auth;

import at.jku.oeh.lan.laganizer.model.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pac4j.core.context.Pac4jConstants;
import org.pac4j.springframework.security.authentication.ClientAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private static Log log = LogFactory.getLog(MyAuthenticationSuccessHandler.class);


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        log.info("Reached on AuthenticationSuccess");
        HttpSession session = request.getSession();
        User user = (User) authentication.getPrincipal();
        session.setAttribute("username", user.getUsername());
        session.setAttribute("authorities", authentication.getAuthorities());
        ClientAuthenticationToken clientAuthenticationToken = (ClientAuthenticationToken) authentication;
        session.setAttribute(Pac4jConstants.USER_PROFILE, clientAuthenticationToken.getUserProfile());
        session.setAttribute("user", user);
        response.sendRedirect("/");

    }
}

