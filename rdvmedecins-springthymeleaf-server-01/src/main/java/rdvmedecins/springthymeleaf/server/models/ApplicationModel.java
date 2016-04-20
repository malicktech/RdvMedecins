package rdvmedecins.springthymeleaf.server.models;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rdvmedecins.client.dao.IDao;
import rdvmedecins.client.dao.RdvMedecinsException;
import rdvmedecins.client.entities.AgendaMedecinJour;
import rdvmedecins.client.entities.Client;
import rdvmedecins.client.entities.Creneau;
import rdvmedecins.client.entities.Medecin;
import rdvmedecins.client.entities.Rv;
import rdvmedecins.client.entities.User;
import rdvmedecins.springthymeleaf.server.config.AppConfig;

@Component
public class ApplicationModel implements IDao {

	// la couche [DAO]
	@Autowired
	private IDao dao;
	// la configuration
	@Autowired
	private AppConfig appConfig;

	// données provenant de la couche [DAO]
	private List<ClientItem> clientItems;
	private List<MedecinItem> medecinItems;
	// données de configuration
	private String userInit;
	private String mdpUserInit;
	private boolean corsAllowed;
	// exception
	private RdvMedecinsException rdvMedecinsException;

	// constructeur
	public ApplicationModel() {
	}

	@PostConstruct
	public void init() {
		// config
		userInit = appConfig.getUSER_INIT();
		mdpUserInit = appConfig.getMDP_USER_INIT();
		dao.setTimeout(appConfig.getTIMEOUT());
		dao.setUrlServiceWebJson(appConfig.getWEBJSON_ROOT());
		corsAllowed = appConfig.isCORS_ALLOWED();
		// on met en cache les médecins et les clients
		List<Medecin> medecins = null;
		List<Client> clients = null;
		try {
			medecins = dao.getAllMedecins(new User(userInit, mdpUserInit));
			clients = dao.getAllClients(new User(userInit, mdpUserInit));
		} catch (RdvMedecinsException ex) {
			rdvMedecinsException = ex;
		}
		if (rdvMedecinsException == null) {
			// on crée les éléments des listes déroulantes
			medecinItems = new ArrayList<MedecinItem>();
			for (Medecin médecin : medecins) {
				medecinItems.add(new MedecinItem(médecin));
			}
			clientItems = new ArrayList<ClientItem>();
			for (Client client : clients) {
				clientItems.add(new ClientItem(client));
			}
		}
	}

	// getters et setters
	public RdvMedecinsException getRdvMedecinsException() {
		return rdvMedecinsException;
	}

	public List<ClientItem> getClientItems() {
		return clientItems;
	}

	public void setClientItems(List<ClientItem> clientItems) {
		this.clientItems = clientItems;
	}

	public List<MedecinItem> getMedecinItems() {
		return medecinItems;
	}

	public void setMedecinItems(List<MedecinItem> medecinItems) {
		this.medecinItems = medecinItems;
	}

	public boolean isCorsAllowed() {
		return corsAllowed;
	}

	// implémentation interface [IDao]
	@Override
	public void setUrlServiceWebJson(String url) {
		dao.setUrlServiceWebJson(url);
	}

	@Override
	public void setTimeout(int timeout) {
		dao.setTimeout(timeout);
	}

	@Override
	public Rv ajouterRv(User user, String jour, long idCreneau, long idClient) {
		return dao.ajouterRv(user, jour, idCreneau, idClient);
	}

	@Override
	public List<Rv> getRvMedecinJour(User user, long idMedecin, String jour) {
		return dao.getRvMedecinJour(user, idMedecin, jour);
	}

	@Override
	public AgendaMedecinJour getAgendaMedecinJour(User user, long idMedecin, String jour) {
		return dao.getAgendaMedecinJour(user, idMedecin, jour);
	}

	@Override
	public List<Client> getAllClients(User user) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Medecin> getAllMedecins(User user) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Creneau> getAllCreneaux(User user, long idMedecin) {
		return dao.getAllCreneaux(user, idMedecin);
	}

	@Override
	public Client getClientById(User user, long id) {
		return dao.getClientById(user, id);
	}

	@Override
	public Medecin getMedecinById(User user, long id) {
		return dao.getMedecinById(user, id);
	}

	@Override
	public Rv getRvById(User user, long id) {
		return dao.getRvById(user, id);
	}

	@Override
	public Creneau getCreneauById(User user, long id) {
		return dao.getCreneauById(user, id);
	}

	@Override
	public void supprimerRv(User user, long idRv) {
		dao.supprimerRv(user, idRv);
	}

	@Override
	public void authenticate(User user) {
		dao.authenticate(user);
	}

}
