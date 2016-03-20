package rdvmedecins.security;

import javax.persistence.Entity;
import javax.persistence.Table;

import rdvmedecins.entities.AbstractEntity;

@Entity
@Table(name = "USERS")
public class User extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	// propriétés
	private String identity;
	private String login;
	private String password;

	// constructeur
	public User() {
	}

	public User(String identity, String login, String password) {
		this.identity = identity;
		this.login = login;
		this.password = password;
	}

	// identité
	@Override
	public String toString() {
		return String.format("User[%s,%s,%s]", identity, login, password);
	}

	// getters et setters
	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
