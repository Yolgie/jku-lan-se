package at.jku.oeh.lan.laganizer.controllers;

import at.jku.oeh.lan.laganizer.model.base.User;
import org.pac4j.core.context.J2EContext;
import org.pac4j.core.context.WebContext;
import org.pac4j.core.exception.RequiresHttpAction;
import org.pac4j.core.profile.ProfileManager;
import org.pac4j.core.profile.UserProfile;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class MainController {
    @PreAuthorize("permitAll()")
    @RequestMapping("/")
    public String index(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map)
            throws RequiresHttpAction {
        return index_html(request, response, map);
    }

    @RequestMapping("/index.html")
    public String index_html(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map)
            throws RequiresHttpAction {
        final WebContext context = new J2EContext(request, response);
        map.put("profile", getStringProfile(context));
        map.put("user", getCurrentUserString(context));
        map.put("firstName", getCurrentUserString(context));
        return "index";
    }

    @RequestMapping("/saml/index.html")
    public String saml(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        return protectedIndex(request, response, map);
    }

    @RequestMapping("/google/index.html")
    public String google(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        return protectedIndex(request, response, map);
    }

    @RequestMapping("/steam/index.html")
    public String steam(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        return protectedIndex(request, response, map);
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/users")
    @PreAuthorize("hasRole('USER')")
    public String users() {
        return "userlist";
    }

    private UserProfile getProfile(WebContext context) {
        @SuppressWarnings("rawtypes")
        final ProfileManager manager = new ProfileManager(context);
        return manager.get(true);
    }

    private String getCurrentUserString(WebContext context) {
        User user = (User) context.getSessionAttribute("user");
        return user == null ? "" : user.toString();
    }

    private String getStringProfile(WebContext context) {
        final UserProfile profile = getProfile(context);
        if (profile == null) {
            return "";
        } else {
            return profile.toString();
        }
    }

    protected String protectedIndex(HttpServletRequest request, HttpServletResponse response, Map<String, Object> map) {
        final WebContext context = new J2EContext(request, response);
        map.put("profile", getStringProfile(context));
        return "protectedIndex";
    }
}
