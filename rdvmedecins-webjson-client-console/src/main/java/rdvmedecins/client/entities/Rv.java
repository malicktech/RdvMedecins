package rdvmedecins.client.entities;

import java.util.Date;

public class Rv extends AbstractEntity {
	private static final long serialVersionUID = 1L;

	// caractéristiques d'un Rv
	private Date jour;

	// un rv est lié à un client
	private Client client;

	// un rv est lié à un créneau
	private Creneau creneau;

	// clés étrangères
	private long idClient;
	private long idCreneau;

	// constructeur par défaut
	public Rv() {
	}

	// avec paramètres
	public Rv(Date jour, Client client, Creneau creneau) {
		this.jour = jour;
		this.client = client;
		this.creneau = creneau;
	}

	// toString
	public String toString() {
		return String.format("Rv[%d, %s, %d, %d]", id, jour, client.id, creneau.id);
	}

	// clés étrangères
	public long getIdCreneau() {
		return idCreneau;
	}

	public long getIdClient() {
		return idClient;
	}

	// getters et setters

	public Client getClient() {
		return client;
	}

	public Date getJour() {
		return jour;
	}

	public void setJour(Date jour) {
		this.jour = jour;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Creneau getCreneau() {
		return creneau;
	}

	public void setCreneau(Creneau creneau) {
		this.creneau = creneau;
	}

}