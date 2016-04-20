package rdvmedecins.client.entities;

public class User {

	// data
	private String login;
	private String passwd;

	// constructeurs
	public User() {
	}

	public User(String login, String passwd) {
		this.login = login;
		this.passwd = passwd;
	}

	// getters et setters
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
}
