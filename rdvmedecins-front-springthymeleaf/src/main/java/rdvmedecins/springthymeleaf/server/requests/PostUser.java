package rdvmedecins.springthymeleaf.server.requests;

import javax.validation.constraints.NotNull;

import rdvmedecins.client.entities.User;

public class PostUser extends PostLang {
	// data
	@NotNull
	private User user;

	// constructeurs
	public PostUser() {

	}

	public PostUser(User user, String lang) {
		super(lang);
		this.user = user;
	}

	// getters et setters
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
