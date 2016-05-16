package rdvmedecins.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "ADDRESS")
public class Address extends AbstractEntity {
	
    private static final long serialVersionUID = 1L;
   

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;
	
    @Column(name = "STREET_NAME", nullable = false,length = 50)
    @Size(max = 50)
    @NotEmpty
    private String streetName;

    @Column(name = "CITY", nullable = false, length = 25)
    @Size(max = 25)
    @NotEmpty
    private String city;

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
    
    
    

}
