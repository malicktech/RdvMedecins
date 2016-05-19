package rdvmedecins.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import rdvmedecins.enums.Civility;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
//public abstract class Personne extends AbstractEntity {
public abstract class Personne implements Serializable {
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

	@Version
	@NotNull
	protected Long version;
	
	@Column(name = "civility", nullable = false)
	@Enumerated(value = EnumType.STRING)
	@NotNull
	private Civility civility;

	@Column(name = "last_name", length = 50, nullable = false)
	@NotNull(message = "{error.medecin.lastname.null}")
	@NotEmpty(message = "{error.medecin.lastname.empty}")
	@Size(min = 2, max = 50, message = "{error.medecin.lastname.size}")
	@Pattern(regexp = "[a-zA-Z]+")
	private String lastName;

	@Column(name = "first_name", length = 50, nullable = false)
	@NotNull(message = "{error.medecin.firstname.null}")
	@NotEmpty(message = "{error.medecin.firstname.empty}")
	@Size(min = 2, max = 50, message = "{error.medecin.firstname.size}")
	private String firstName;

	private LocalDate Birthday;
	
    @Pattern(regexp = "[0-9]{10}")
	private String mobilePhoneNumber;
	
	

	/*
	 * constructors
	 * =========================================================================
	 */

	public Personne() {
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


	public Long getVersion() {
		return version;
	}


	public void setVersion(Long version) {
		this.version = version;
	}


	public Civility getCivility() {
		return civility;
	}


	public void setCivility(Civility civility) {
		this.civility = civility;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public LocalDate getBirthday() {
		return Birthday;
	}


	public void setBirthday(LocalDate birthday) {
		Birthday = birthday;
	}


	public String getMobilePhoneNumber() {
		return mobilePhoneNumber;
	}


	public void setMobilePhoneNumber(String mobilePhoneNumber) {
		this.mobilePhoneNumber = mobilePhoneNumber;
	}

	/*
	 * Equals , hashCode, toString
	 * =========================================================================
	 */

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Personne personne = (Personne) o;
		if (personne.id == null || id == null) {
			return false;
		}
		return Objects.equals(id, personne.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public String toString() {
		return String.format("Personne[id=%s, version=%s, titre=%s, lastName=%s, firstName=%s]", id, version, civility, lastName,
				firstName);
	}

}