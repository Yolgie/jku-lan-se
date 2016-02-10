package jkulan.software.configuration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurer;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ProviderSignInController;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SocialAuthenticationServiceLocator;
import org.springframework.social.security.SocialAuthenticationServiceRegistry;
import org.springframework.social.security.SocialUserDetailsService;

import jkulan.software.authentication.SocialSignInAdapter;
import jkulan.software.social.steam.SteamConnectionFactory;
import jkulan.software.social.steam.security.DefaultRealmMapper;
import jkulan.software.social.steam.security.SteamAuthenticationService;
import jkulan.software.social.steam.security.SteamSocialAuthenticationFilter;

@Configuration
@EnableSocial
public class SocialConfiguration implements SocialConfigurer {
	private static final Log log = LogFactory.getLog(SocialConfiguration.class); 
	
	@Inject
	private Environment env;
	
	@Inject
	private DataSource dataSource;
	
	@Inject
	private TextEncryptor textEncryptor;
	
	@Inject 
	private SocialUserDetailsService userDetails;
	
	@Inject
	private AuthenticationManager authenticationManager;
    
    @Bean
    public SocialAuthenticationServiceLocator socialAuthenticationServiceLocator() {
    	SocialAuthenticationServiceRegistry registry = new SocialAuthenticationServiceRegistry();
    	
//    	registry.addAuthenticationService(openIdAuthenticationService());
    	registry.addAuthenticationService(steamAuthenticationService());
    	
    	return registry;
    }
    
    @Bean
    @Scope(value="session", proxyMode=ScopedProxyMode.INTERFACES)
    public ConnectionRepository connectionRepository() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new IllegalStateException("Unable to get a ConnectionRepository: no user signed in");
        }
        log.trace("Authentication: "+authentication);
        return usersConnectionRepository().createConnectionRepository(authentication.getName());
    }
    @Bean
    public SteamAuthenticationService steamAuthenticationService() {
    	SteamAuthenticationService sa = new SteamAuthenticationService(steamConnectionFactory());
    	sa.setRealmMapper(new DefaultRealmMapper());
    	return sa;
    }
    @Bean
    SteamConnectionFactory steamConnectionFactory() {
    	return new SteamConnectionFactory(env.getProperty("steam.apiKey"));
    }
//    @Bean
//    public OpenIdAuthenticationService openIdAuthenticationService() {
//    	return new OpenIdAuthenticationService(openIdConnectionFactory());
//    }
//    @Bean
//    OpenIdConnectionFactory openIdConnectionFactory() {
//    	return new OpenIdConnectionFactory();
//    }
    
    @Bean
    public UsersConnectionRepository usersConnectionRepository() {
        JdbcUsersConnectionRepository repo = new JdbcUsersConnectionRepository(dataSource, socialAuthenticationServiceLocator(), 
            textEncryptor);
        return repo;
    }
    
    @Bean
    public ConnectController connectController(Environment env) {
    	ConnectController controller = new ConnectController(socialAuthenticationServiceLocator(), connectionRepository());
        controller.setApplicationUrl(env.getProperty("application.url"));
        return controller;
    }
    @Bean
    public ProviderSignInController signInController(Environment env) {
    	ProviderSignInController signin = new ProviderSignInController(socialAuthenticationServiceLocator(),
    			usersConnectionRepository(), new SocialSignInAdapter());
        return signin;
    }
    @Bean
    public UserIdSource userIdSource() {
    	return new AuthenticationNameUserIdSource();
    }
    
    @Bean
    public TextEncryptor textEncryptor() {
    	return Encryptors.delux(env.getProperty("steam.apiKey"), env.getProperty("security.passwordSalt"));
    }
    
    @Bean
    public SteamSocialAuthenticationFilter steamSocialAuthenticationFilter() throws Exception {
    	return new SteamSocialAuthenticationFilter(authenticationManager, userIdSource(), usersConnectionRepository(),
    			socialAuthenticationServiceLocator());
    }

//    @Bean
//    public OpenIdSocialAuthenticationFilter openIdSocialAuthenticationFilter() throws Exception {
//    	return new OpenIdSocialAuthenticationFilter(authenticationManager, userIdSource(), usersConnectionRepository(),
//    			socialAuthenticationServiceLocator());
//    }

	@Override
	public UserIdSource getUserIdSource() {
		return userIdSource();
	}

	@Override
	public UsersConnectionRepository getUsersConnectionRepository(
			ConnectionFactoryLocator connectionFactoryLocator) {
		return usersConnectionRepository();
	}

	@Override
	public void addConnectionFactories(
			ConnectionFactoryConfigurer connectionFactoryConfigurer,
			Environment environment) {
		
		try {
			Method m = connectionFactoryConfigurer.getClass().getMethod("getConnectionFactoryLocator", (Class[]) null);
			m.setAccessible(true);
			SocialAuthenticationServiceRegistry reg = (SocialAuthenticationServiceRegistry) m.invoke(connectionFactoryConfigurer, (Object[]) null);
			reg.addAuthenticationService(steamAuthenticationService());
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			log.error("Error calling method via reflection", e);
		}
		
	}
}
