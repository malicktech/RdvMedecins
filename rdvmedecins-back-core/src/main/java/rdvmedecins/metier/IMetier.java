package rdvmedecins.metier;

import java.util.Date;
import java.util.List;

import rdvmedecins.domain.AgendaMedecinJour;
import rdvmedecins.entities.Client;
import rdvmedecins.entities.Creneau;
import rdvmedecins.entities.Medecin;
import rdvmedecins.entities.Rv;

public interface IMetier {

	/**
	 * liste des Médecins
	 * @return
	 */
	public List<Medecin> getAllMedecins();
	
	/**
	 * trouver un client identifié par son id
	 * @param id
	 * @return
	 */
	public Medecin getMedecinById(long id);

	/**
	 * liste des créneaux horaires d'un médecin
	 * @param idMedecin
	 * @return
	 */
	public List<Creneau> getAllCreneaux(long idMedecin);

	/**
	 * liste des Rv d'un médecin pour un jour donné
	 * @param idMedecin
	 * @param jour
	 * @return
	 */
	public List<Rv> getRvMedecinJour(long idMedecin, Date jour);

	
	/**
	 * liste des clients
	 * @return
	 */
	public List<Client> getAllClients();
	
	/**
	 * trouver un client identifié par son id
	 * @param id
	 * @return
	 */
	public Client getClientById(long id);



	/**
	 * trouver un Rv identifié par son id
	 * @param id
	 * @return
	 */
	public Rv getRvById(long id);

	/**
	 * trouver un créneau horaire identifié par son id
	 * @param id
	 * @return
	 */
	public Creneau getCreneauById(long id);

	/**
	 * ajouter un RV
	 * @param jour
	 * @param créneau
	 * @param client
	 * @return
	 */
	public Rv ajouterRv(Date jour, Creneau créneau, Client client);

	/**
	 * supprimer un RV
	 * @param idRv
	 */
	public void supprimerRv(long idRv);

	/**
	 * metier
	 * @param idMedecin
	 * @param jour
	 * @return
	 */
	public AgendaMedecinJour getAgendaMedecinJour(long idMedecin, Date jour);

}