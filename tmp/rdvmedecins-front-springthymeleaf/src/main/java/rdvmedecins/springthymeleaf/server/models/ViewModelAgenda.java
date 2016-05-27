package rdvmedecins.springthymeleaf.server.models;


public class ViewModelAgenda {

	// propriétés
	private ViewModelCreneau[] creneaux;
	private String titre;

	// getters et setters
	public ViewModelCreneau[] getCreneaux() {
		return creneaux;
	}

	public void setCreneaux(ViewModelCreneau[] creneaux) {
		this.creneaux = creneaux;
	}

	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}
}
