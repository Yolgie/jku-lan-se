package jkulan.software.controllers;

import java.util.Arrays;
import java.util.HashSet;

import javax.inject.Inject;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jkulan.software.model.User;
import jkulan.software.model.UserDAO;

@Controller
public class DebugController {
	@Inject
	private UserDAO dao;
	
	@Inject
	private PasswordEncoder encoder;
	
	@RequestMapping("/fuero")
	@PreAuthorize("permitAll()")
	public String createUser() {
		assert dao != null;
		if (dao.findByName("fuero") == null) {
			User user = new User();
			user.setName("fuero");
			user.setPassword(encoder.encode("test123"));
			user.setAddress("Byteweg 255");
			user.setEmail("fuerob@gmail.com");
			user.setRoles(new HashSet<String>(Arrays.asList("ROLE_USER", "ROLE_ADMIN")));
			dao.save(user);
		}
		return "index";
	}
	
	
//	@Bean
//	public ServletRegistrationBean h2servletRegistration() {
//	    ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
//	    registration.addUrlMappings("/console/*");
//	    return registration;
//	}
}
