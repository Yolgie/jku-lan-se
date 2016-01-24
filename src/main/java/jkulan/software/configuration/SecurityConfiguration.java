package jkulan.software.configuration;

import jkulan.software.authentication.CustomOpenIDAuthenticationUserDetailsService;
import jkulan.software.authentication.OpenIDAuthenticationFailurehandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

/**
 * Spring Security Configuraiton File.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
//                .formLogin()
//                .loginPage("/login")
//                .loginProcessingUrl("/login/authenticate")
//                .failureUrl("/login?params.error=bad_credentials")
//                .permitAll()
//                .and()
//                .authorizeRequests()
//                .antMatchers("/favicon.ico", "/static-resources/**").permitAll()
//                .antMatchers("/**").authenticated()
//                .and()
//                .rememberMe();
                    .openidLogin()
                        .loginPage("/login")
                        .loginProcessingUrl("/login/j_spring_openid_security_check")
                        .defaultSuccessUrl("/")
                        .failureUrl("/login?params.error=bad_credentials")
                        .failureHandler(new OpenIDAuthenticationFailurehandler())
                        .authenticationUserDetailsService(new CustomOpenIDAuthenticationUserDetailsService())
                        .permitAll()
                    .and()
                    .authorizeRequests()
                        .antMatchers("/favion.ico", "/static-resources/**").permitAll()
                        .antMatchers("/**").authenticated()
                    .and()
                    .csrf().disable();

    }
}
