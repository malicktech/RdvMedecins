package rdvmedecins.springthymeleaf.server.requests;

import javax.validation.constraints.NotNull;

import rdvmedecins.client.entities.User;

public class PostSupprimerRv extends PostUser {

	// data
	@NotNull
	private Long idRv;

	// constructeurs
	public PostSupprimerRv() {

	}

	public PostSupprimerRv(User user, String lang, long idRv) {
		super(user, lang);
		this.idRv = idRv;
	}

	// getters et setters
	public long getIdRv() {
		return idRv;
	}

	public void setIdRv(long idRv) {
		this.idRv = idRv;
	}

}
