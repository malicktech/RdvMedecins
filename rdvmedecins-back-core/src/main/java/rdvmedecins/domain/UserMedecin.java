package rdvmedecins.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "medecins")
public class UserMedecin extends Personne implements Serializable {

	/*
	 * Serial Version UID
	 * =========================================================================
	 */
	
	private static final long serialVersionUID = 1L;

	/*
	 * Fields
	 * =========================================================================
	 */
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "id_specialisation", referencedColumnName = "id")
	private MedecinSpecialization specialization;

	@OneToMany(mappedBy = "medecin", fetch = FetchType.LAZY)
	private List<Creneau> creneaux = new ArrayList<>();

	/*
	 * constructors
	 * =========================================================================
	 */

	public UserMedecin() {
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
