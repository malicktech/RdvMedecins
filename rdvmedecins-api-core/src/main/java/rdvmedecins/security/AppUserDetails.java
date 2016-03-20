package rdvmedecins.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AppUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	// propriétés
	private User user;
	private UserRepository userRepository;

	// constructeurs
	public AppUserDetails() {
	}

	public AppUserDetails(User user, UserRepository userRepository) {
		this.user = user;
		this.userRepository = userRepository;
	}

	// -------------------------interface
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		for (Role role : userRepository.getRoles(user.getId())) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getLogin();
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

	// getters et setters
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
