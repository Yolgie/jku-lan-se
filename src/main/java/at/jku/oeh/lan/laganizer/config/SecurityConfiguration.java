package at.jku.oeh.lan.laganizer.config;

import at.jku.oeh.lan.laganizer.auth.MyAuthenticationSuccessHandler;
import at.jku.oeh.lan.laganizer.auth.MyUserDetailsService;
import at.jku.oeh.lan.laganizer.auth.SteamClient;
import org.pac4j.core.config.Config;
import org.pac4j.oidc.client.OidcClient;
import org.pac4j.saml.client.SAML2Client;
import org.pac4j.springframework.security.authentication.ClientAuthenticationProvider;
import org.pac4j.springframework.security.web.ClientAuthenticationEntryPoint;
import org.pac4j.springframework.security.web.ClientAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring Security Main config.
 * Since all authentication work is offloaded to pac4j, this configuration is
 * mainly responsible for calling the right pac4j clients, defined in the
 * {@link Pac4JConfiguration}, according to the login method chosen by the user.
 *
 * @author fuero
 */
@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    @Qualifier("pac4j-config")
    private Config config;

    @Autowired
    private SAML2Client saml2Client;

    @Autowired
    private SteamClient steamClient;

    @Autowired
    @Qualifier("googleClient")
    private OidcClient googleClient;

    @Autowired
    MyUserDetailsService myUserDetailsService;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**");
    }

    @Bean
    @Qualifier("samlEntryPoint")
    public ClientAuthenticationEntryPoint samlEntryPoint() {
        ClientAuthenticationEntryPoint ep = new ClientAuthenticationEntryPoint();
        ep.setClient(saml2Client);
        return ep;
    }

    @Bean
    @Qualifier("steamEntryPoint")
    public ClientAuthenticationEntryPoint steamEntryPoint() {
        ClientAuthenticationEntryPoint ep = new ClientAuthenticationEntryPoint();
        ep.setClient(steamClient);
        return ep;
    }

    @Bean
    @Qualifier("googleEntryPoint")
    public ClientAuthenticationEntryPoint googleEntryPoint() {
        ClientAuthenticationEntryPoint ep = new ClientAuthenticationEntryPoint();
        ep.setClient(googleClient);
        return ep;
    }

    /**
     * Defines and authorizes all requests to the available login methods,
     * represented by their AuthenticationEntryPoints.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Forces the SecurityInterceptor into the entrypoints
        http.csrf().disable();
        http.formLogin()
                //Does this generate a penetrable Default Login Form ?
                .loginPage("/login");
        http.authorizeRequests()
                .antMatchers("/saml/**").authenticated()
                .antMatchers("/steam/**").authenticated()
                .antMatchers("/google/**").authenticated();
        // Assigns the entrypoints to URLs
        http.exceptionHandling().defaultAuthenticationEntryPointFor(samlEntryPoint(), new AntPathRequestMatcher("/saml/**"));
        http.exceptionHandling().defaultAuthenticationEntryPointFor(steamEntryPoint(), new AntPathRequestMatcher("/steam/**"));
        http.exceptionHandling().defaultAuthenticationEntryPointFor(googleEntryPoint(), new AntPathRequestMatcher("/google/**"));
        //http.addFilterAfter(clientFilter(authenticationManager()), BasicAuthenticationFilter.class);
        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .logoutSuccessUrl("/");

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(clientProvider());
    }

    @Bean
    public ClientAuthenticationProvider clientProvider() {
        ClientAuthenticationProvider prov = new ClientAuthenticationProvider();
        prov.setClients(config.getClients());
        prov.setUserDetailsService(myUserDetailsService);
        return prov;
    }

    @Bean
    public MyAuthenticationSuccessHandler myAuthenticationSuccessHandler() {
        return new MyAuthenticationSuccessHandler();
    }

    /**
     * *BEWARE* Spring Boot auto-magically loads this with {@link org.springframework.boot.context.embedded.FilterRegistrationBean}
     *
     * @param auth Auto-Wiring
     * @return the Filter
     */
    @Bean
    protected FilterRegistrationBean clientFilter(AuthenticationManager auth) throws Exception {
        ClientAuthenticationFilter filter = new ClientAuthenticationFilter("/callback");
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(filter);
        filter.setClients(config.getClients());
        filter.setAuthenticationManager(super.authenticationManager());
        filter.setAuthenticationSuccessHandler(myAuthenticationSuccessHandler());
        //filter.setAuthenticationFailureHandler(new MyAuthenticationFailureHandler());
        List<String> patterns = new ArrayList<>();
        patterns.add("/callback");
        filterRegistrationBean.setUrlPatterns(patterns);
        return filterRegistrationBean;
    }
}