package rdvmedecins.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "admin")
public class UserAdmin extends Personne implements Serializable{

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

	public UserAdmin() {
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
