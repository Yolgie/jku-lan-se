package jkulan.software.configuration;

import javax.inject.Inject;
import javax.servlet.Filter;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableOAuth2Client
@Order(30)
public class OAuth2Configuration extends WebSecurityConfigurerAdapter {
	@Inject
	private OAuth2ClientContext oauth2ClientContext;
	
	@Bean
	@ConfigurationProperties("google.client")
	OAuth2ProtectedResourceDetails google() {
		return new AuthorizationCodeResourceDetails();
	}
    
    @Bean
    FilterRegistrationBean oauth2ClientFilterRegistration(
        OAuth2ClientContextFilter filter) {
      FilterRegistrationBean registration = new FilterRegistrationBean();
      registration.setFilter(filter);
      registration.setOrder(-100);
      return registration;
    }
    
	@Bean
	@ConfigurationProperties("google.resource")
	ResourceServerProperties googleResource() {
		return new ResourceServerProperties();
	}
	
	@Bean
	Filter googleFilter() {
		OAuth2ClientAuthenticationProcessingFilter googleFilter = new OAuth2ClientAuthenticationProcessingFilter(
				"/login/google");
		OAuth2RestTemplate googleTemplate = new OAuth2RestTemplate(google(), oauth2ClientContext);
		googleFilter.setRestTemplate(googleTemplate);
		googleFilter.setTokenServices(
				new UserInfoTokenServices(googleResource().getUserInfoUri(), google().getClientId()));
		return googleFilter;
	}
	@Bean
    LoginUrlAuthenticationEntryPoint oAuth2AuthenticationEntryPoint() {
      return new LoginUrlAuthenticationEntryPoint("/login/google");
    }
	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
			.antMatchers("/login/google")
			.permitAll();
		http.addFilterBefore(googleFilter(), BasicAuthenticationFilter.class);
	}
}
