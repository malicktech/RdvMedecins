package rdvmedecins.springthymeleaf.server.models;

public class ViewModelCreneau {

	// constantes;
	public static final int ACTION_SUPPRIMER = 2;
	public static final int ACTION_RESERVER = 1;

	// propriétés
	private Long id;
	private String creneauHoraire;
	private String client;
	private String commande;
	private int action;
	private long idRv;

	// getters et setters

	public String getCreneauHoraire() {
		return creneauHoraire;
	}

	public void setCreneauHoraire(String creneauHoraire) {
		this.creneauHoraire = creneauHoraire;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getCommande() {
		return commande;
	}

	public void setCommande(String commande) {
		this.commande = commande;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getAction() {
		return action;
	}

	public void setAction(int action) {
		this.action = action;
	}

	public long getIdRv() {
		return idRv;
	}

	public void setIdRv(long idRv) {
		this.idRv = idRv;
	}

}
