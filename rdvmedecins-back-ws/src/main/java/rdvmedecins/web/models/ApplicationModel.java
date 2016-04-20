package rdvmedecins.web.models;

import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rdvmedecins.domain.AgendaMedecinJour;
import rdvmedecins.entities.Client;
import rdvmedecins.entities.Creneau;
import rdvmedecins.entities.Medecin;
import rdvmedecins.entities.Rv;
import rdvmedecins.metier.IMetier;
import rdvmedecins.web.helpers.Static;

/**
 * classe [ApplicationModel] un composant Spring. un seul objet de ce type sera instancié (singleton)
 * Classe singleton qui sert de : 
 * cache pour stocker les listes de médecins et de patients (clients) et d'interface unique pour les contrôleurs ;
 * Cette stratégie amène de la souplesse quant à la gestion du cache
 * @author Malick
 *
 */
@Component
public class ApplicationModel implements IMetier {

	// la couche [métier]
	@Autowired
	private IMetier métier;

	// données provenant de la couche [métier]
	private List<Medecin> médecins;
	private List<Client> clients;
	private List<String> messages;
	// données de configuration
	private boolean corsAllowed = true;
	private boolean secured = true;
	
	@PostConstruct
	public void init() {
		// on récupère les médecins et les clients
		try {
			médecins = métier.getAllMedecins();
			clients = métier.getAllClients();
		} catch (Exception ex) {
			messages = Static.getErreursForException(ex);
		}
	}

	// getter
	public List<String> getMessages() {
		return messages;
	}

	// ------------------------- interface couche [métier]
	@Override
	public List<Client> getAllClients() {
		return clients;
	}

	@Override
	public List<Medecin> getAllMedecins() {
		return médecins;
	}

	@Override
	public List<Creneau> getAllCreneaux(long idMedecin) {
		return métier.getAllCreneaux(idMedecin);
	}

	@Override
	public List<Rv> getRvMedecinJour(long idMedecin, Date jour) {
		return métier.getRvMedecinJour(idMedecin, jour);
	}

	@Override
	public Client getClientById(long id) {
		return métier.getClientById(id);
	}

	@Override
	public Medecin getMedecinById(long id) {
		return métier.getMedecinById(id);
	}

	@Override
	public Rv getRvById(long id) {
		return métier.getRvById(id);
	}

	@Override
	public Creneau getCreneauById(long id) {
		return métier.getCreneauById(id);
	}

	@Override
	public Rv ajouterRv(Date jour, Creneau creneau, Client client) {
		return métier.ajouterRv(jour, creneau, client);
	}

	@Override
	public void supprimerRv(long idRv) {
		métier.supprimerRv(idRv);
	}

	@Override
	public AgendaMedecinJour getAgendaMedecinJour(long idMedecin, Date jour) {
		return métier.getAgendaMedecinJour(idMedecin, jour);
	}

	public boolean isCorsAllowed() {
		return corsAllowed;
	}

	public boolean isSecured() {
		return secured;
	}
	
}
