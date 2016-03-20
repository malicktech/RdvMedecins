package rdvmedecins.security;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import rdvmedecins.entities.AbstractEntity;

@Entity
@Table(name = "USERS_ROLES")
public class UserRole extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	// un UserRole référence un User
	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User user;
	
	// un UserRole référence un Role
	@ManyToOne
	@JoinColumn(name = "ROLE_ID")
	private Role role;

	// constructeurs
	public UserRole() {

	}

	public UserRole(User user, Role role) {
		this.user = user;
		this.role = role;
	}

	// getters et setters

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
