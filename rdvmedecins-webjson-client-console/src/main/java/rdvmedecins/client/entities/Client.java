package rdvmedecins.client.entities;


public class Client extends Personne {

	private static final long serialVersionUID = 1L;

	// constructeur par défaut
	public Client() {
	}

	// constructeur avec paramètres
	public Client(String titre, String nom, String prenom) {
		super(titre, nom, prenom);
	}

	// identité
	public String toString() {
		return String.format("Client[%s]", super.toString());
	}

}
