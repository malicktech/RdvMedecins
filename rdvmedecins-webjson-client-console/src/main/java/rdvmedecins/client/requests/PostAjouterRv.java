package rdvmedecins.client.requests;

public class PostAjouterRv {

	// données du post
	private String jour;
	private long idClient;
	private long idCreneau;

	// constructeurs
	public PostAjouterRv() {

	}

	public PostAjouterRv(long idClient, long idCreneau, String jour) {
		this.idClient = idClient;
		this.idCreneau = idCreneau;
		this.jour = jour;
	}

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
