package rdvmedecins.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "clients")
public class UserClient extends Personne implements Serializable{

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
	
	@OneToMany(mappedBy="client", fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    private List<Address> addresses;

	/*
	 * constructors
	 * =========================================================================
	 */

	public UserClient() {
	}
	
	/*
	 * Equals , hashCode, toString
	 * =========================================================================
	 */
	
	public String toString() {
		return String.format("Client[%s]", super.toString());
	}

}
