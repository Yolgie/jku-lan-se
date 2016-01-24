//package jkulan.software.configuration;
//
//import jkulan.software.model.UserDAO;
//import jkulan.software.services.AccountConnectionSignupService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.security.crypto.encrypt.Encryptors;
//import org.springframework.social.UserIdSource;
//import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
//import org.springframework.social.config.annotation.EnableSocial;
//import org.springframework.social.config.annotation.SocialConfigurer;
//import org.springframework.social.connect.ConnectionFactoryLocator;
//import org.springframework.social.connect.UsersConnectionRepository;
//import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
//import org.springframework.social.security.AuthenticationNameUserIdSource;
//
//import javax.sql.DataSource;
//
///**
// * Created by tth on 23.01.16.
// */
//@Configuration
//@EnableSocial
//public class SocialConfig implements SocialConfigurer {
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Autowired
//    private UserDAO usersDao;
//
//    @Override
//    public void addConnectionFactories(ConnectionFactoryConfigurer  connectionFactoryConfigurer, Environment environment) {
//
//    }
//
//    @Override
//    public UserIdSource getUserIdSource() {
//        return new AuthenticationNameUserIdSource();
//    }
//
//    @Override
//    public UsersConnectionRepository getUsersConnectionRepository(ConnectionFactoryLocator connectionFactoryLocator) {
//        JdbcUsersConnectionRepository repository = new JdbcUsersConnectionRepository(dataSource,connectionFactoryLocator, Encryptors.noOpText());
//        repository.setConnectionSignUp(new AccountConnectionSignupService(usersDao));
//        return repository;
//    }
//}
