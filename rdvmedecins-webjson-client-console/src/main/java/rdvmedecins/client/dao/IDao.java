package rdvmedecins.client.dao;

import java.util.List;

import rdvmedecins.client.entities.AgendaMedecinJour;
import rdvmedecins.client.entities.Client;
import rdvmedecins.client.entities.Creneau;
import rdvmedecins.client.entities.Medecin;
import rdvmedecins.client.entities.Rv;
import rdvmedecins.client.entities.User;

public interface IDao {
	// Url du service web
	public void setUrlServiceWebJson(String url);

	// timeout
	public void setTimeout(int timeout);

	// authentification
	public void authenticate(User user);

	// liste des clients
	public List<Client> getAllClients(User user);

	// liste des Médecins
	public List<Medecin> getAllMedecins(User user);

	// liste des créneaux horaires d'un médecin
	public List<Creneau> getAllCreneaux(User user, long idMedecin);

	// trouver un client identifié par son id
	public Client getClientById(User user, long id);

	// trouver un client identifié par son id
	public Medecin getMedecinById(User user, long id);

	// trouver un Rv identifié par son id
	public Rv getRvById(User user, long id);

	// trouver un créneau horaire identifié par son id
	public Creneau getCreneauById(User user, long id);

	// ajouter un RV
	public Rv ajouterRv(User user, String jour, long idCreneau, long idClient);

	// supprimer un RV
	public void supprimerRv(User user, long idRv);

	// liste des Rv d'un médecin, un jour donné
	public List<Rv> getRvMedecinJour(User user, long idMedecin, String jour);

	// agenda
	public AgendaMedecinJour getAgendaMedecinJour(User user, long idMedecin, String jour);

}
