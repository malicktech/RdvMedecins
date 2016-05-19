package rdvmedecins.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "admin")
public class Admin extends Personne {

	/*
	 * Serial Version UID
	 * =========================================================================
	 */

	private static final long serialVersionUID = 3937603522096809440L;

	/*
	 * Fields
	 * =========================================================================
	 */

	/*
	 * constructors
	 * =========================================================================
	 */

	public Admin() {
	}

	/*
	 * getters et setters
	 * =========================================================================
	 */

	/*
	 * Equals , hashCode, toString
	 * =========================================================================
	 */
}
