package rdvmedecins.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;

import rdvmedecins.enums.Civility;

@Entity
@Table(name = "person")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(
		name = "type_user", 
		discriminatorType = DiscriminatorType.STRING, length = 1)
@JsonTypeInfo(
		use=JsonTypeInfo.Id.NAME, 
		include=JsonTypeInfo.As.PROPERTY,
		property="type")
@JsonSubTypes({
		@Type(name="P", value=Client.class),
		@Type(name="D", value=Medecin.class),
		@Type(name="A", value=Admin.class)})

public class Personne extends AbstractEntity {

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

	@Column(name = "titre", length = 5, nullable = false)
	@NotNull
	@NotEmpty
	private String titre;

	@Enumerated(value = EnumType.STRING)
	private Civility civility;

	@Column(name = "nom", length = 25, nullable = false)
	@NotNull(message = "{error.medecin.lastname.null}")
	@NotEmpty(message = "{error.medecin.lastname.empty}")
	@Size(min = 2, max = 25, message = "{error.medecin.lastname.size}")
	private String nom;

	@Column(name = "prenom", length = 25, nullable = false)
	@NotNull(message = "{error.medecin.firstname.null}")
	@NotEmpty(message = "{error.medecin.firstname.empty}")
	@Size(min = 2, max = 25, message = "{error.medecin.firstname.size}")
	private String prenom;

	// @Temporal(TemporalType.DATE)
	// no need to add temporal annotation, converter with do that
	private LocalDate DateOfBirthday;

	/*
	 * constructors
	 * =========================================================================
	 */

	public Personne() {
	}

	public Personne(String titre, String nom, String prenom) {
		super();
		this.titre = titre;
		this.nom = nom;
		this.prenom = prenom;
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

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public LocalDate getDateOfBirthday() {
		return DateOfBirthday;
	}

	public void setDateOfBirthday(LocalDate dateOfBirthday) {
		DateOfBirthday = dateOfBirthday;
	}

	public Civility getCivility() {
		return civility;
	}

	public void setCivility(Civility civility) {
		this.civility = civility;
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
		return String.format("Personne[id=%s, version=%s, titre=%s, nom=%s, prenom=%s]", id, version, titre, nom,
				prenom);
	}

}