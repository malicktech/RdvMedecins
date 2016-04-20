package rdvmedecins.client.entities;


public class Personne extends AbstractEntity {
	private static final long serialVersionUID = 1L;
	// attributs d'une persinne
	private String titre;
	private String nom;
	private String prenom;

	// constructeur par défaut
	public Personne() {
	}

	// constructeur avec paramètres
	public Personne(String titre, String nom, String prenom) {
		this.titre = titre;
		this.nom = nom;
		this.prenom = prenom;
	}

	// toString
	public String toString() {
		return String.format("Personne[%s, %s, %s, %s, %s]", id, version, titre, nom, prenom);
	}

	// getters et setters
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