package rdvmedecins.web.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import rdvmedecins.domain.AgendaMedecinJour;
import rdvmedecins.entities.Client;
import rdvmedecins.entities.Creneau;
import rdvmedecins.entities.Medecin;
import rdvmedecins.entities.Rv;
import rdvmedecins.web.helpers.Static;
import rdvmedecins.web.models.ApplicationModel;
import rdvmedecins.web.models.PostAjouterRv;
import rdvmedecins.web.models.PostSupprimerRv;
import rdvmedecins.web.models.Reponse;

@RestController
public class RdvMedecinsController {

	@Autowired
	private ApplicationModel application;

	@Autowired
	private RdvMedecinsCorsController rdvMedecinsCorsController;

	// liste de messages
	private List<String> messages;

	@PostConstruct
	// méthode à exécuter juste après l'instanciation de la classe
	public void init() {
		// messages d'erreur de l'application
		messages = application.getMessages();
	}

	// début méthodes locales
	// ---------------------------------------------------------------
	private Reponse getMedecin(long id) {
		// on récupère le médecin
		Medecin médecin = null;
		try {
			médecin = application.getMedecinById(id);
		} catch (Exception e1) {
			return new Reponse(1, Static.getErreursForException(e1));
		}
		// médecin existant ?
		if (médecin == null) {
			return new Reponse(2, null);
		}
		// ok
		return new Reponse(0, médecin);
	}

	private Reponse getClient(long id) {
		// on récupère le client
		Client client = null;
		try {
			client = application.getClientById(id);
		} catch (Exception e1) {
			return new Reponse(1, Static.getErreursForException(e1));
		}
		// client existant ?
		if (client == null) {
			return new Reponse(2, null);
		}
		// ok
		return new Reponse(0, client);
	}

	private Reponse getRv(long id) {
		// on récupère le Rv
		Rv rv = null;
		try {
			rv = application.getRvById(id);
		} catch (Exception e1) {
			return new Reponse(1, Static.getErreursForException(e1));
		}
		// Rv existant ?
		if (rv == null) {
			return new Reponse(2, null);
		}
		// ok
		return new Reponse(0, rv);
	}

	private Reponse getCreneau(long id) {
		// on récupère le créneau
		Creneau créneau = null;
		try {
			créneau = application.getCreneauById(id);
		} catch (Exception e1) {
			return new Reponse(1, Static.getErreursForException(e1));
		}
		// créneau existant ?
		if (créneau == null) {
			return new Reponse(2, null);
		}
		// ok
		return new Reponse(0, créneau);
	}

	/** 
	 * MEDECINS
	 * =========================================================================================== */
	
	/**
	 * Liste de tous les médecins du cabinet médical
	 */
	@RequestMapping(value = "/getAllMedecins", method = RequestMethod.GET)
	public Reponse getAllMedecins(HttpServletResponse response) {
		// entêtes CORS
		rdvMedecinsCorsController.getAllMedecins(response);
		// état de l'application 
		//determine si l'application s'est correctement initialisée
		if (messages != null) {
			return new Reponse(-1, messages);
		}
		// liste des médecins
		try {
			return new Reponse(0, application.getAllMedecins());
		} catch (Exception e) {
			return new Reponse(1, Static.getErreursForException(e));
		}
	}
	
	@RequestMapping(value = "/getMedecinById/{id}", method = RequestMethod.GET)
	public Reponse getMedecinById(@PathVariable("id") long id) {
		// état de l'application
		if (messages != null) {
			return new Reponse(-1, messages);
		}
		// on récupère le médecin
		return getMedecin(id);
	}
	
	/** 
	 * Liste des créneaux horaires d'un médecin 
	 */
	@RequestMapping(value = "/getAllCreneaux/{idMedecin}", method = RequestMethod.GET)
	public Reponse getAllCreneaux(@PathVariable("idMedecin") long idMedecin) {
		// état de l'application
		if (messages != null) {
			return new Reponse(-1, messages);
		}
		// on récupère le médecin
		Reponse réponse = getMedecin(idMedecin);
		if (réponse.getStatus() != 0) {
			return réponse;
		}
		Medecin médecin = (Medecin) réponse.getData();
		// créneaux du médecin
		List<Creneau> créneaux = null;
		try {
			créneaux = application.getAllCreneaux(médecin.getId());
		} catch (Exception e1) {
			return new Reponse(3, Static.getErreursForException(e1));
		}
		// on rend la réponse
		return new Reponse(0, Static.getListMapForCreneaux(créneaux));
	}
	
	/**
	 * Agenda d'un médecin : Liste des Rdv sur le jour données
	 */
	@RequestMapping(value = "/getAgendaMedecinJour/{idMedecin}/{jour}", method = RequestMethod.GET)
	public Reponse getAgendaMedecinJour(@PathVariable("idMedecin") long idMedecin, @PathVariable("jour") String jour, HttpServletResponse response) {
		// entêtes CORS
		rdvMedecinsCorsController.getAgendaMedecinJour(response);
		// état de l'application
		if (messages != null) {
			return new Reponse(-1, messages);
		}
		// on vérifie la date
		Date jourAgenda = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		try {
			jourAgenda = sdf.parse(jour);
		} catch (ParseException e) {
			return new Reponse(3, new String[] { String.format("jour [%s] invalide", jour) });
		}
		// on récupère le médecin
		Reponse réponse = getMedecin(idMedecin);
		if (réponse.getStatus() != 0) {
			return réponse;
		}
		Medecin médecin = (Medecin) réponse.getData();
		// on récupère son agenda
		AgendaMedecinJour agenda = null;
		try {
			agenda = application.getAgendaMedecinJour(médecin.getId(), jourAgenda);
		} catch (Exception e1) {
			return new Reponse(4, Static.getErreursForException(e1));
		}
		// ok
		return new Reponse(0, Static.getMapForAgendaMedecinJour(agenda));
	}

	/** 
	 * CLIENTS
	 * =========================================================================================== */
	
	/**
	 * Liste de tous les patients du cabinet médical
	 */
	@RequestMapping(value = "/getAllClients", method = RequestMethod.GET)
	public Reponse getAllClients(HttpServletResponse response) {
		// entêtes CORS
		rdvMedecinsCorsController.getAllClients(response);
		// état de l'application
		if (messages != null) {
			return new Reponse(-1, messages);
		}
		// liste des clients
		try {
			return new Reponse(0, application.getAllClients());
		} catch (Exception e) {
			return new Reponse(1, Static.getErreursForException(e));
		}
	}
	
	/**
	 * Le service web permet également de récupérer des entités via leur identifiant 
	 */
	@RequestMapping(value = "/getClientById/{id}", method = RequestMethod.GET)
	public Reponse getClientById(@PathVariable("id") long id) {
		// état de l'application
		if (messages != null) {
			return new Reponse(-1, messages);
		}
		// on récupère le client
		return getClient(id);
	}

	/** 
	 * RENDEZ-VOUS
	 * =========================================================================================== */

	/**
	 * Ajouter un rendez-vous
	 * 
	 * @param post
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/ajouterRv", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8")
	public Reponse ajouterRv(@RequestBody PostAjouterRv post, HttpServletResponse response) {
		// entêtes CORS
		rdvMedecinsCorsController.ajouterRv(response);
		// état de l'application
		if (messages != null) {
			return new Reponse(-1, messages);
		}
		// on récupère les valeurs postées
		String jour = post.getJour();
		long idCreneau = post.getIdCreneau();
		long idClient = post.getIdClient();
		// on vérifie la date
		Date jourAgenda = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		try {
			jourAgenda = sdf.parse(jour);
		} catch (ParseException e) {
			return new Reponse(6, null);
		}
		// on récupère le créneau
		Reponse réponse = getCreneau(idCreneau);
		if (réponse.getStatus() != 0) {
			return réponse;
		}
		Creneau créneau = (Creneau) réponse.getData();
		// on récupère le client
		réponse = getClient(idClient);
		if (réponse.getStatus() != 0) {
			réponse.incrStatusBy(2);
			return réponse;
		}
		Client client = (Client) réponse.getData();
		// on ajoute le Rv
		Rv rv = null;
		try {
			rv = application.ajouterRv(jourAgenda, créneau, client);
		} catch (Exception e1) {
			return new Reponse(5, Static.getErreursForException(e1));
		}
		// on rend la réponse
		return new Reponse(0, Static.getMapForRv(rv));
	}

	/**
	 * Supprrimer un rendez-vous
	 * 
	 * @param post
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/supprimerRv", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8")
	public Reponse supprimerRv(@RequestBody PostSupprimerRv post, HttpServletResponse response) {
		// entêtes CORS
		rdvMedecinsCorsController.supprimerRv(response);
		// état de l'application
		if (messages != null) {
			return new Reponse(-1, messages);
		}
		// on récupère les valeurs postées
		long idRv = post.getIdRv();
		// on récupère le rv
		Reponse réponse = getRv(idRv);
		if (réponse.getStatus() != 0) {
			return réponse;
		}
		// suppression du rv
		try {
			application.supprimerRv(idRv);
		} catch (Exception e1) {
			return new Reponse(3, Static.getErreursForException(e1));
		}
		// ok
		return new Reponse(0, null);
	}
	
	/**
	 * Liste des rendez-vous d'un médecin 
	 * @param idMedecin
	 * @param jour
	 * @return
	 */
	@RequestMapping(value = "/getRvMedecinJour/{idMedecin}/{jour}", method = RequestMethod.GET)
	public Reponse getRvMedecinJour(@PathVariable("idMedecin") long idMedecin, @PathVariable("jour") String jour) {
		// état de l'application
		if (messages != null) {
			return new Reponse(-1, messages);
		}
		// on vérifie la date
		Date jourAgenda = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		try {
			jourAgenda = sdf.parse(jour);
		} catch (ParseException e) {
			return new Reponse(3, null);
		}
		// on récupère le médecin
		Reponse réponse = getMedecin(idMedecin);
		if (réponse.getStatus() != 0) {
			return réponse;
		}
		Medecin médecin = (Medecin) réponse.getData();
		// liste de ses rendez-vous
		List<Rv> rvs = null;
		try {
			rvs = application.getRvMedecinJour(médecin.getId(), jourAgenda);
		} catch (Exception e1) {
			return new Reponse(4, Static.getErreursForException(e1));
		}
		// on rend la réponse
		return new Reponse(0, Static.getListMapForRvs(rvs));
	}

	@RequestMapping(value = "/getRvById/{id}", method = RequestMethod.GET)
	public Reponse getRvById(@PathVariable("id") long id) {
		// état de l'application
		if (messages != null) {
			return new Reponse(-1, messages);
		}
		// on récupère le rv
		Reponse réponse = getRv(id);
		if (réponse.getStatus() == 0) {
			réponse.setData(Static.getMapForRv2((Rv) réponse.getData()));
		}
		// résultat
		return réponse;
	}

	@RequestMapping(value = "/getCreneauById/{id}", method = RequestMethod.GET)
	public Reponse getCreneauById(@PathVariable("id") long id) {
		// état de l'application
		if (messages != null) {
			return new Reponse(-1, messages);
		}
		// on récupère le créneau
		Reponse réponse = getCreneau(id);
		if (réponse.getStatus() == 0) {
			réponse.setData(Static.getMapForCreneau((Creneau) réponse.getData()));
		}
		// résultat
		return réponse;
	}




}