package rdvmedecins.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "clients")
public class Client extends Personne {

	/*
	 * Serial Version UID
	 * =========================================================================
	 */
	
	private static final long serialVersionUID = 1L;
	
	/*
	 * Fields
	 * =========================================================================
	 */
		
	@OneToMany(mappedBy = "client" , fetch=FetchType.LAZY)   
    private List<Rv> rv = new ArrayList<>();
	
	@OneToMany(mappedBy="person", cascade=CascadeType.ALL)
    private List<Address> addresses;

	/*
	 * constructors
	 * =========================================================================
	 */

	public Client() {
	}
	
	/*
	 * Equals , hashCode, toString
	 * =========================================================================
	 */
	
	public String toString() {
		return String.format("Client[%s]", super.toString());
	}

}
