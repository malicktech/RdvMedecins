package rdvmedecins.domain.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import rdvmedecins.domain.UserMedecin;

/**
 * L'entité [AgendaMedecinJour] est un entité métier il est l'agenda d'un
 * médecin pour un jour donné, ç-à-d la liste de ses rendez-vous
 * 
 * @author Malick
 *
 */
public class AgendaMedecinJour implements Serializable {

	private static final long serialVersionUID = 1L;

	// champs
	private UserMedecin medecin;
	private Date jour;
	private CreneauMedecinJour[] creneauxMedecinJour;

	// constructeurs
	public AgendaMedecinJour() {

	}

	public AgendaMedecinJour(UserMedecin medecin, Date jour, CreneauMedecinJour[] creneauxMedecinJour) {
		this.medecin = medecin;
		this.jour = jour;
		this.creneauxMedecinJour = creneauxMedecinJour;
	}

	public String toString() {
		StringBuffer str = new StringBuffer("");
		for (CreneauMedecinJour cr : creneauxMedecinJour) {
			str.append(" ");
			str.append(cr.toString());
		}
		return String.format("Agenda[%s,%s,%s]", medecin, new SimpleDateFormat("dd/MM/yyyy").format(jour),
				str.toString());
	}

	// getters et setters

	public CreneauMedecinJour[] getCreneauxMedecinJour() {
		return creneauxMedecinJour;
	}

	public void setCreneauxMedecinJour(CreneauMedecinJour[] creneauxMedecinJour) {
		this.creneauxMedecinJour = creneauxMedecinJour;
	}

	public Date getJour() {
		return jour;
	}

	public void setJour(Date jour) {
		this.jour = jour;
	}

	public UserMedecin getMedecin() {
		return medecin;
	}

	public void setMedecin(UserMedecin medecin) {
		this.medecin = medecin;
	}

}
