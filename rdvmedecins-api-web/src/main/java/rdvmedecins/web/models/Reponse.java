package rdvmedecins.web.models;

/**
 * Classe encapsulant la forme de tous les réponses de notre service web
 * @author Malick
 *
 */
public class Reponse {

	// ----------------- propriétés
	// statut de l'opération // code d'erreur de la réponse 0: OK, autre chose : KO 
	private int status;
	// la réponse JSON
	private Object data;

	// ---------------constructeurs
	public Reponse() {
	}

	public Reponse(int status, Object data) {
		this.status = status;
		this.data = data;
	}

	// méthodes
	public void incrStatusBy(int increment) {
		status += increment;
	}

	// ----------------------getters et setters

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
