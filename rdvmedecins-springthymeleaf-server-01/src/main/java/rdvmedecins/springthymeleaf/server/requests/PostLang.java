package rdvmedecins.springthymeleaf.server.requests;

import javax.validation.constraints.NotNull;

public class PostLang {

	// data
	@NotNull
	private String lang;

	// constructeurs
	public PostLang() {

	}

	public PostLang(String lang) {
		this.lang = lang;
	}

	// getters et setters
	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}
	
}
