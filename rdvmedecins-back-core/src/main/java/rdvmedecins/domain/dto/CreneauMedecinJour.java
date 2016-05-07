package rdvmedecins.domain.dto;

import java.io.Serializable;

import rdvmedecins.domain.Creneau;
import rdvmedecins.domain.Rv;

/**
 * L'entité [CreneauMedecinJour] est une entité métier 
 * il associe un créneau horaire et le rendez-vous éventuel pris dans ce créneau :
 * 
 * @author Malick
 *
 */
public class CreneauMedecinJour implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// champs
	private Creneau creneau;
	private Rv rv;

	// constructeurs
	public CreneauMedecinJour() {

	}

	public CreneauMedecinJour(Creneau creneau, Rv rv) {
		this.creneau = creneau;
		this.rv = rv;
	}

	// toString
	@Override
	public String toString() {
		return String.format("[%s %s]", creneau, rv);
	}

	// getters et setters

	public Creneau getCreneau() {
		return creneau;
	}

	public void setCreneau(Creneau creneau) {
		this.creneau = creneau;
	}

	public Rv getRv() {
		return rv;
	}

	public void setRv(Rv rv) {
		this.rv = rv;
	}

}
