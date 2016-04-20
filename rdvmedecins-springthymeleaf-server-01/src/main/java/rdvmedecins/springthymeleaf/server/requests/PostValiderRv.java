package rdvmedecins.springthymeleaf.server.requests;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import rdvmedecins.client.entities.User;

public class PostValiderRv extends PostUser {

	// data
	@NotNull
	private Long idCreneau;
	@NotNull
	private Long idClient;
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date jour;

	// constructeurs
	public PostValiderRv() {

	}

	public PostValiderRv(User user, String lang, Date jour, long idCreneau, long idClient) {
		super(user, lang);
		this.jour = jour;
		this.idCreneau = idCreneau;
		this.idClient = idClient;
	}

	// getters et setters

	public long getIdCreneau() {
		return idCreneau;
	}

	public void setIdCreneau(long idCreneau) {
		this.idCreneau = idCreneau;
	}

	public Date getJour() {
		return jour;
	}

	public void setJour(Date jour) {
		this.jour = jour;
	}

	public long getIdClient() {
		return idClient;
	}

	public void setIdClient(long idClient) {
		this.idClient = idClient;
	}

}
