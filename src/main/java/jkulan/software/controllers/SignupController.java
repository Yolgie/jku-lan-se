package jkulan.software.controllers;

import javax.inject.Inject;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jkulan.software.model.ApiUserDetailsService;
import jkulan.software.model.User;
import jkulan.software.model.UserDAO;

@Controller
public class SignupController {
	private static Log log = LogFactory.getLog(SignupController.class);
	private static String COOKIE_NAME = "jku-lan-uuid";
	@Inject
	private ApiUserDetailsService userDetailsService;
	@Inject
	private UserDAO dao;
	
	@PreAuthorize("hasRole('ROLE_PRE_AUTH')")
	@RequestMapping("/signup")
	public String signup(@AuthenticationPrincipal User user, HttpServletRequest request, HttpServletResponse response) {
		User existing = null;
		for (Cookie c : request.getCookies()) {
			if (COOKIE_NAME.equals(c.getName())) {
				existing = userDetailsService.loadUserByUUID(c.getValue());
			}
		}
		if (existing != null && !existing.isComplete()) {
			log.trace("Existing user: "+existing);
			log.trace("Current user: "+user);
			existing.completeUser(user);
			if (existing.isComplete()) {
				log.info("Sign up done, logging out user");
				dao.save(existing);
				dao.delete(user);
				return "redirect:/logout?signup-done";
			} else {
				log.info("Sign up failed");
				return "redirect:/signup?failed";
			}
			
		}
		if (!user.isComplete()) {
			Cookie c = new Cookie(COOKIE_NAME, user.getUuid());
			c.setMaxAge(300);
			response.addCookie(c);
			return "signup";
		}
		return "redirect:/";
	}
}
