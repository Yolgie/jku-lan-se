//package jkulan.software.services;
//
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.social.security.SocialUser;
//import org.springframework.social.security.SocialUserDetails;
//import org.springframework.social.security.SocialUserDetailsService;
//
///**
// * Created by tth on 23.01.16.
// */
//public class SimpleSocialUsersService implements SocialUserDetailsService {
//
//    private UserDetailsService userDetailsService;
//
//    public SimpleSocialUsersService(UserDetailsService userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }
//
//    @Override
//    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
//        UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
//        return new SocialUser(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
//    }
//}
