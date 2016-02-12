package jkulan.software.configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import jkulan.software.model.User;

public class MyAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	private static Log log = LogFactory.getLog(MyAuthenticationSuccessHandler.class);
	
	@Override
	protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response) {
		String target = super.determineTargetUrl(request, response);
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		log.trace("SuccessHandler: "+user);
		if (user != null) {
			if (user.isComplete()) {
				return target;
			} else {
				log.info("User info incomplete, sending to signup");
				return "/signup";
			}
		} else {
			return target;
		}
	}
}
