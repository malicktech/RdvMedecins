package rdvmedecins.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "clients")
public class Client extends Personne {

	private static final long serialVersionUID = 1L;
	
	/*
	 * Fields
	 * =========================================================================
	 */
	@Column(name="email", length = 50, unique=true, nullable = false , updatable = false, insertable = false)
	@Email
	@NotEmpty
	@NotNull
	private String email;
	
	
	@OneToMany(mappedBy = "client" , fetch=FetchType.LAZY)   
    private List<Rv> rv = new ArrayList<>();

	/*
	 * constructors
	 * =========================================================================
	 */

	public Client() {
	}

	public Client(String titre, String nom, String prenom) {
		super(titre, nom, prenom);
	}
	
	/*
	 * Equals , hashCode, toString
	 * =========================================================================
	 */
	
	public String toString() {
		return String.format("Client[%s]", super.toString());
	}

}
