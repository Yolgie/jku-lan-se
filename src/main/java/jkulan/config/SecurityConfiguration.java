package jkulan.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
@Order(1)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
           		.antMatchers("/resources/**");
    }

	@Override
    protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		
		http.formLogin()
            .loginPage("/login")
            .defaultSuccessUrl("/success")
            	.and()
        	.logout()
        	.logoutUrl("/logout");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
             .inMemoryAuthentication()
              		.withUser("user")
                  		.password("password")
                  		.roles("USER").and()
              		.withUser("helper")
                  		.password("password")
                   		.roles("USER", "HELPER").and()
               		.withUser("admin")
                   		.password("password")
                   		.roles("USER", "ADMIN");
    }
}