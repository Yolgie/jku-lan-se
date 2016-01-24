package jkulan.software.authentication;

import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.openid.OpenIDAuthenticationToken;

/**
 * Custom UserDetailsService specifically for OpenID authorization. Maybe it will be necessary at a later stage, to
 * split this even further and provide a custom @AuthenticationUserDetailsService for individual OpenID Providers,
 * depending on how much information they provide to endusers.
 */
public class CustomOpenIDAuthenticationUserDetailsService implements AuthenticationUserDetailsService<OpenIDAuthenticationToken> {

    @Override
    public UserDetails loadUserDetails(OpenIDAuthenticationToken token) throws UsernameNotFoundException {

        System.out.println("Hier muss entweder ein Nutzer angelegt, oder einer geladen werden");
        throw new UsernameNotFoundException("Aktuell wird kein UserDetails Object zurückgeliefert");
    }
}
