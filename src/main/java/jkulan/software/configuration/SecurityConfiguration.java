package jkulan.software.configuration;

import javax.inject.Inject;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.openid.OpenIDAuthenticationProvider;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

import jkulan.software.authentication.OpenIDAuthenticationFailurehandler;
import jkulan.software.model.ApiUserDetailsService;

/**
 * Spring Security Configuration File.
 */
@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Inject
	private PasswordEncoder encoder;
    
//    @Bean
//    Filter csrfHeaderFilter() {
//        return new OncePerRequestFilter() {
//            @Override
//            protected void doFilterInternal(HttpServletRequest request,
//                                            HttpServletResponse response, FilterChain filterChain)
//                throws ServletException, IOException {
//                CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class
//                    .getName());
//                if (csrf != null) {
//                    Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
//                    String token = csrf.getToken();
//                    if (cookie == null || token != null
//                        && !token.equals(cookie.getValue())) {
//                        cookie = new Cookie("XSRF-TOKEN", token);
//                        cookie.setPath("/");
//                        response.addCookie(cookie);
//                    }
//                }
//                filterChain.doFilter(request, response);
//            }
//        };
//    }

    @Bean
    CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setHeaderName("X-XSRF-TOKEN");
        return repository;
    }
    
    @Bean
    AuthenticationProvider formAuthenticationProvider() {
    	DaoAuthenticationProvider prov = new DaoAuthenticationProvider();
    	prov.setUserDetailsService(apiUserDetailsService());
    	prov.setPasswordEncoder(encoder);
    	return prov;
    }
    @Bean
    OpenIDAuthenticationProvider openIDAuthenticationProvider() {
    	OpenIDAuthenticationProvider prov = new OpenIDAuthenticationProvider();
    	
    	prov.setUserDetailsService(apiUserDetailsService());
    	return prov;
    }
    
    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
    	auth.authenticationProvider(formAuthenticationProvider());
    	auth.authenticationProvider(openIDAuthenticationProvider());
    }

    @Bean
    ApiUserDetailsService apiUserDetailsService() {
    	return new ApiUserDetailsService();
    }
    
	@Override
    protected void configure(final HttpSecurity http) throws Exception {
        http
			.authorizeRequests()
				.antMatchers("/js/**", "/css/**", "/webjars/**", "/wro/**", 
						"/favion.ico", "/login/j_spring_openid_security_check")
					.permitAll()
				.antMatchers("/saml/**")
					.permitAll()
	        .and()
	        	.formLogin()
	                .loginPage("/login")
	                .loginProcessingUrl("/login/authenticate")
	                .failureUrl("/login?error=bad_credentials")
//	                .defaultSuccessUrl("/signup")
	                .permitAll()
	        .and()
                .csrf()
                	.disable();
                //.csrfTokenRepository(csrfTokenRepository())
        http
            	.logout()
            		.logoutSuccessUrl("/login?logout");
        http.openidLogin()
            .loginPage("/login")
            .loginProcessingUrl("/login/j_spring_openid_security_check")
//            .defaultSuccessUrl("/signup")
            .failureUrl("/login?error=bad_credentials")
            .failureHandler(new OpenIDAuthenticationFailurehandler())
            .authenticationUserDetailsService(apiUserDetailsService())
            .permitAll()
            .attributeExchange(".*")
            	.attribute("mail")
            		.type("http://schema.openid.net/contact/email")
            	.and()
            	.attribute("name")
            		.type("http://schema.openid.net/namePerson/friendly");
    }
}
