package rdvmedecins.client.requests;

public class PostSupprimerRv {

	// données du post
	private long idRv;

	// constructeurs
	public PostSupprimerRv() {

	}

	public PostSupprimerRv(long idRv) {
		this.idRv = idRv;
	}

	// getters et setters
	public long getIdRv() {
		return idRv;
	}

	public void setIdRv(long idRv) {
		this.idRv = idRv;
	}
}
