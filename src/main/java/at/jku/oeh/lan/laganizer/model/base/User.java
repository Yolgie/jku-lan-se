package at.jku.oeh.lan.laganizer.model.base;

import at.jku.oeh.lan.laganizer.model.BaseEntity;
import at.jku.oeh.lan.laganizer.model.events.tournament.team.Team;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Entity
public class User extends BaseEntity implements UserDetails, Serializable {
    @Column(length = 80, nullable = false)
    private String uuid;

    @NotNull
    private boolean active;

    @ManyToMany
    @ElementCollection
    private Set<Team> teams;

    @NotNull
    private String name;

    private String email;

    @Column(nullable=true)
    private Long steamId;

    private boolean steamVisible;

    @Column(length = 255, nullable = true)
    private String steamAvatarUrl;

    @Column(length = 80, nullable = true)
    private String googleId;

    @Column(length = 80, nullable = true)
    private String saml2Id;

    @ElementCollection(fetch = FetchType.LAZY)
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

        authorities.addAll(roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));

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

    /// Getter ///

    public String getUuid() {
        return uuid;
    }

    public boolean isActive() {
        return active;
    }

    public String getName() {
        return name;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Long getSteamId() {
        return steamId;
    }

    public void setSteamId(Long steamId) {
        this.steamId = steamId;

    public String getEmail() {
        return email;
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

    /// Setter ///
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public void setSteamId(String steamId) {
        this.steamId = steamId;
    }

    public void setSaml2Id(String saml2Id) {
        this.saml2Id = saml2Id;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((uuid == null) ? 0 : uuid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (uuid == null) {
			if (other.uuid != null)
				return false;
		} else if (!uuid.equals(other.uuid))
			return false;
		return true;
	}
}
