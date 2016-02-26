package at.jku.oeh.lan.laganizer.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class User extends BaseEntity implements UserDetails, Serializable {
    @Column(length = 80, nullable = false)
    private String uuid;

    @ManyToMany
    @ElementCollection
    private Set<Team> teams;

    @NotNull
    private String name;

    private String email;

    private long steamId;
    private boolean steamVisible;
    private String steamAvatarUrl;

    @Column(length = 80, nullable = true)
    private String googleId;

    @Column(length = 80, nullable = true)
    private String saml2Id;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> roles = new HashSet<>();

    public User() {
        this.uuid = UUID.randomUUID().toString();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
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

    public long getSteamId() {
        return steamId;
    }

    public void setSteamId(long steamId) {
        this.steamId = steamId;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public String getSaml2Id() {
        return saml2Id;
    }

    public void setSaml2Id(String saml2Id) {
        this.saml2Id = saml2Id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString() {
        return "User: " + getName() + " with E-Mail: " + getEmail() + " and Roles: " + roles.toString();
    }


	public boolean isSteamVisible() {
		return steamVisible;
	}


	public void setSteamVisible(boolean steamVisible) {
		this.steamVisible = steamVisible;
	}


	public String getSteamAvatarUrl() {
		return steamAvatarUrl;
	}


	public void setSteamAvatarUrl(String steamAvatarUrl) {
		this.steamAvatarUrl = steamAvatarUrl;
	}
}
