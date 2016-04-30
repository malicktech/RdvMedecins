package rdvmedecins.entities;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

@MappedSuperclass
public class Personne extends AbstractEntity {

	private static final long serialVersionUID = 1L;

	/*
	 * Fields
	 * =========================================================================
	 */

	@Column(length = 5)
	private String titre;
	
	@NotNull(message = "{error.medecin.lastname.null}")
    @NotEmpty(message = "{error.medecin.lastname.empty}")
    @Size(max = 20, message = "{error.medecin.lastname.max}")
	@Column(length = 20)
	private String nom;
	
	@NotNull(message = "{error.medecin.firstname.null}")
    @NotEmpty(message = "{error.medecin.firstname.empty}")
    @Size(max = 20, message = "{error.medecin.firstname.max}")
	@Column(length = 20)
	private String prenom;

	/*
	 * constructors
	 * =========================================================================
	 */

	public Personne() {
	}

	public Personne(String titre, String nom, String prenom) {
		this.titre = titre;
		this.nom = nom;
		this.prenom = prenom;
	}

	/*
	 * Equals ,hashcode, toString
	 * =========================================================================
	 */

	public String toString() {
		return String.format("Personne[%s, %s, %s, %s, %s]", id, version, titre, nom, prenom);
	}

	/*
	 * getters et setters
	 * =========================================================================
	 */
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

}