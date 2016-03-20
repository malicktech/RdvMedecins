package rdvmedecins.web.models;

public class PostAjouterRv {

	// données du post
	private String jour;
	private long idClient;
	private long idCreneau;

	// getters et setters
	public String getJour() {
		return jour;
	}

	public void setJour(String jour) {
		this.jour = jour;
	}

	public long getIdClient() {
		return idClient;
	}

	public void setIdClient(long idClient) {
		this.idClient = idClient;
	}

	public long getIdCreneau() {
		return idCreneau;
	}

	public void setIdCreneau(long idCreneau) {
		this.idCreneau = idCreneau;
	}

}
