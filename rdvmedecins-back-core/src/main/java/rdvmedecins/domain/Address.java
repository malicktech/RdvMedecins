package rdvmedecins.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "address")
public class Address extends AbstractAuditingEntity implements Serializable{
	
	/*
	 * Serial Version UID
	 * =========================================================================
	 */
	
    private static final long serialVersionUID = 1L;
   
    /*
	 * Fields
	 * =========================================================================
	 */
    
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;
	
    @Column(name = "street_name", nullable = false,length = 50)
    @Size(max = 50)
    @NotEmpty
    private String streetName;

    @Column(name = "city", nullable = false, length = 25)
    @Size(max = 25)
    @NotEmpty
    private String city;
    
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_client", insertable = false, nullable = false, updatable = false)
	private UserClient client;

    /*
	 * constructors
	 * =========================================================================
	 */
    public Address() {
	}

    /*
	 * getters et setters
	 * =========================================================================
	 */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStreetName() {
		return streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	/*
	 * Equals , hashCode, toString
	 * =========================================================================
	 */
    
    

}
