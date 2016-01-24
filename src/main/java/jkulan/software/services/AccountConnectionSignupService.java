//package jkulan.software.services;
//
//import jkulan.software.model.User;
//import jkulan.software.model.UserDAO;
//import org.springframework.social.connect.Connection;
//import org.springframework.social.connect.ConnectionSignUp;
//import org.springframework.social.connect.UserProfile;
//
///**
// *
// */
//public class AccountConnectionSignupService implements ConnectionSignUp {
//
//    private final UserDAO userDAO;
//
//    public AccountConnectionSignupService(UserDAO userDAO) {
//        this.userDAO = userDAO;
//    }
//
//    public String execute(Connection<?> connection) {
//        UserProfile profile = connection.fetchUserProfile();
//        userDAO.save(new User(profile.getEmail(), profile.getName()));
//        return profile.getUsername();
//    }
//}
