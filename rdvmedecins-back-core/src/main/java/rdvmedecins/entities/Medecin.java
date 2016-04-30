package rdvmedecins.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "medecins")
public class Medecin extends Personne {

	private static final long serialVersionUID = 1L;

	/*
	 * constructors
	 * =========================================================================
	 */

	public Medecin() {
	}

	public Medecin(String titre, String nom, String prenom) {
		super(titre, nom, prenom);
	}

	/*
	 * toString
	 * =========================================================================
	 */

	public String toString() {
		return String.format("Medecin[%s]", super.toString());
	}

}
