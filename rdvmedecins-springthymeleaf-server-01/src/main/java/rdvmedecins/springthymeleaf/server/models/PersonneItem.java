package rdvmedecins.springthymeleaf.server.models;

import rdvmedecins.client.entities.Personne;

public class PersonneItem {

	// élément d'une liste
	private Long id;
	private String texte;

	// constructeur
	public PersonneItem() {

	}

	public PersonneItem(Personne personne) {
		id = personne.getId();
		texte = String.format("%s %s %s", personne.getTitre(), personne.getPrenom(), personne.getNom());
	}

	// getters et setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTexte() {
		return texte;
	}

	public void setTexte(String texte) {
		this.texte = texte;
	}

}
