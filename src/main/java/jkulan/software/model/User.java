package jkulan.software.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;

@Entity
public class User implements UserDetails, Serializable {
    @Id
    @GeneratedValue
    private long id;

    @Column(length = 80, nullable = false)
    private String uuid;


    @NotNull
    private String name;

    @Column(length = 80, nullable = true)
    private String steamId;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles = new HashSet<>();

    public User() {
        this.uuid = UUID.randomUUID().toString();
    }

    public String getSteamId() {
        return steamId;
    }

    public void setSteamId(String steamId) {
        this.steamId = steamId;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        final Set<GrantedAuthority> authorities = new HashSet<>();
        final Set<String> roles = this.getRoles();

//        if (!isComplete()) {
//            // We're missing required info
//            authorities.add(new SimpleGrantedAuthority("ROLE_PRE_AUTH"));
//            return authorities;
//        }

        if (roles == null) {
            return Collections.emptyList();
        }

        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        return authorities;
    }


    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public String toString() {
        return getName();
    }

}
