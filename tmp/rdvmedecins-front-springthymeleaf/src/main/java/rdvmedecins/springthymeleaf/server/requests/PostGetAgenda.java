package rdvmedecins.springthymeleaf.server.requests;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import rdvmedecins.client.entities.User;

public class PostGetAgenda extends PostUser {

	// données
	@NotNull
	private Long idMedecin;
	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date jour;

	// constructeurs
	public PostGetAgenda() {
	}

	public PostGetAgenda(User user, String lang, Long idMedecin, Date jour) {
		super(user,lang);
		this.idMedecin = idMedecin;
		this.jour = jour;
	}

	// getters et setters
	public Long getIdMedecin() {
		return idMedecin;
	}

	public void setIdMedecin(Long idMedecin) {
		this.idMedecin = idMedecin;
	}

	public Date getJour() {
		return jour;
	}

	public void setJour(Date jour) {
		this.jour = jour;
	}
}
