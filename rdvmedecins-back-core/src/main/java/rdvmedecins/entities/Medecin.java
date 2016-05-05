package rdvmedecins.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "medecins")
public class Medecin extends Personne {

	private static final long serialVersionUID = 1L;
	
	/*
	 * Fields
	 * =========================================================================
	 */
	
	@OneToMany(mappedBy = "medecin" , fetch=FetchType.LAZY)   
    private List<Creneau> creneaux = new ArrayList<>();

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
	 * getters et setters
	 * =========================================================================
	 */
	
	public List<Creneau> getCreneaux() {
		return creneaux;
	}

	public void setCreneaux(List<Creneau> creneaux) {
		this.creneaux = creneaux;
	}

	/*
	 * Equals , hashCode, toString
	 * =========================================================================
	 */

	public String toString() {
		return String.format("Medecin[%s]", super.toString());
	}



}
