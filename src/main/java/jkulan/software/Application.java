package jkulan.software;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.h2.server.web.WebServlet;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Main Class, used to start the Application.
 * 
 * https://github.com/philipsorst/angular-rest-springsecurity/tree/master/src/main/java/net/dontdrinkandroot/example/angularrestspringsecurity/rest
 */
@SpringBootApplication
@RestController
public class Application {
	
	@RequestMapping("/resource")
	public Map<String, Object> home() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("id", UUID.randomUUID().toString());
		model.put("content", "Hello World");
		return model;
	}
	
	@Bean
	public ServletRegistrationBean h2servletRegistration() {
	    ServletRegistrationBean registration = new ServletRegistrationBean(new WebServlet());
	    registration.addUrlMappings("/console/*");
	    return registration;
	}
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
