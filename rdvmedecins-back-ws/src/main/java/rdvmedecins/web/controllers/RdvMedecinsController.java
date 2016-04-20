package rdvmedecins.web.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import rdvmedecins.domain.AgendaMedecinJour;
import rdvmedecins.entities.Client;
import rdvmedecins.entities.Creneau;
import rdvmedecins.entities.Medecin;
import rdvmedecins.entities.Rv;
import rdvmedecins.web.helpers.Static;
import rdvmedecins.web.models.ApplicationModel;
import rdvmedecins.web.models.PostAjouterRv;
import rdvmedecins.web.models.PostSupprimerRv;
import rdvmedecins.web.models.Response;

@RestController
public class RdvMedecinsController {

	@Autowired
	private ApplicationModel application;

	@Autowired
	private RdvMedecinsCorsController rdvMedecinsCorsController;

	@Autowired
	private MappingJackson2HttpMessageConverter converter;

	// liste de messages
	private List<String> messages;
	// mapper JSON pour contrôler la sérialisation des objets rendus par le
	// service [web / jSON]
	private ObjectMapper mapper;

	@PostConstruct
	// méthode à exécuter juste après l'instanciation de la classe
	public void init() {
		// Stocke les messages d'erreur de l'application
		// Permet aux méthodes de savoir si l'application s'est initialisée
		// correctement.
		messages = application.getMessages();
		// mapper JSON
		mapper = converter.getObjectMapper();
	}

	/*
	 * Méthodes locales
	 * =========================================================================
	 * ==================
	 */

	private Response<Medecin> getMedecin(long id) {
		// on récupère le médecin
		Medecin médecin = null;
		try {
			médecin = application.getMedecinById(id);
		} catch (Exception e1) {
			return new Response<Medecin>(1, Static.getErreursForException(e1), null);
		}
		// médecin existant ?
		if (médecin == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("Le médecin d'id [%s] n'existe pas", id));
			return new Response<Medecin>(2, messages, null);
		}
		// ok
		return new Response<Medecin>(0, null, médecin);
	}

	private Response<Client> getClient(long id) {
		// on récupère le client
		Client client = null;
		try {
			client = application.getClientById(id);
		} catch (Exception e1) {
			return new Response<Client>(1, Static.getErreursForException(e1), null);
		}
		// client existant ?
		if (client == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("Le client d'id [%s] n'existe pas", id));
			return new Response<Client>(2, messages, null);
		}
		// ok
		return new Response<Client>(0, null, client);
	}

	private Response<Rv> getRv(long id) {
		// on récupère le Rv
		Rv rv = null;
		try {
			rv = application.getRvById(id);
		} catch (Exception e1) {
			return new Response<Rv>(1, Static.getErreursForException(e1), null);
		}
		// Rv existant ?
		if (rv == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("Le rendez-vous d'id [%s] n'existe pas", id));
			return new Response<Rv>(2, messages, null);
		}
		// ok
		return new Response<Rv>(0, null, rv);
	}

	private Response<Creneau> getCreneau(long id) {
		// on récupère le créneau
		Creneau créneau = null;
		try {
			créneau = application.getCreneauById(id);
		} catch (Exception e1) {
			return new Response<Creneau>(1, Static.getErreursForException(e1), null);
		}
		// créneau existant ?
		if (créneau == null) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("Le créneau d'id [%s] n'existe pas", id));
			return new Response<Creneau>(2, messages, null);
		}
		// ok
		return new Response<Creneau>(0, null, créneau);
	}

	/**
	 * MEDECINS
	 * =========================================================================
	 * ==================
	 */

	/**
	 * Liste de tous les médecins du cabinet médical
	 */
	@RequestMapping(value = "/getAllMedecins", method = RequestMethod.GET)
	public Response<List<Medecin>> getAllMedecins(HttpServletResponse response,
			@RequestHeader(value = "Origin", required = false) String origin) {
		// entêtes CORS
		rdvMedecinsCorsController.sendOptions(origin, response);
		// état de l'application
		if (messages != null) {
			return new Response<List<Medecin>>(-1, messages, null);
		}
		// liste des médecins
		try {
			return new Response<List<Medecin>>(0, null, application.getAllMedecins());
		} catch (Exception e) {
			return new Response<List<Medecin>>(1, Static.getErreursForException(e), null);
		}
	}

	/**
	 * Get Medecin By ID
	 * 
	 * @param id
	 * @param response
	 * @param origin
	 * @return
	 */
	@RequestMapping(value = "/getMedecinById/{id}", method = RequestMethod.GET)
	public Response<Medecin> getMedecinById(@PathVariable("id") long id, HttpServletResponse response,
			@RequestHeader(value = "Origin", required = false) String origin) {
		// entêtes CORS
		rdvMedecinsCorsController.sendOptions(origin, response);
		// état de l'application
		if (messages != null) {
			return new Response<Medecin>(-1, messages, null);
		}
		// on récupère le médecin
		return getMedecin(id);
	}

	/**
	 * liste des créneaux d'un médecin
	 * 
	 * @param idMedecin
	 * @param response
	 * @param origin
	 * @return
	 */
	@RequestMapping(value = "/getAllCreneaux/{idMedecin}", method = RequestMethod.GET)
	public Response<List<Creneau>> getAllCreneaux(@PathVariable("idMedecin") long idMedecin,
			HttpServletResponse response, @RequestHeader(value = "Origin", required = false) String origin) {
		// entêtes CORS
		rdvMedecinsCorsController.sendOptions(origin, response);
		// état de l'application
		if (messages != null) {
			return new Response<List<Creneau>>(-1, messages, null);
		}
		// on récupère le médecin
		Response<Medecin> responseMedecin = getMedecin(idMedecin);
		if (responseMedecin.getStatus() != 0) {
			return new Response<List<Creneau>>(responseMedecin.getStatus(), responseMedecin.getMessages(), null);
		}
		Medecin médecin = responseMedecin.getBody();
		// créneaux du médecin
		List<Creneau> créneaux = null;
		try {
			créneaux = application.getAllCreneaux(médecin.getId());
		} catch (Exception e1) {
			return new Response<List<Creneau>>(3, Static.getErreursForException(e1), null);
		}
		// on rend la réponse
		SimpleBeanPropertyFilter creneauFilter = SimpleBeanPropertyFilter.serializeAllExcept("medecin");
		mapper.setFilterProvider(new SimpleFilterProvider().addFilter("creneauFilter", creneauFilter));
		return new Response<List<Creneau>>(0, null, créneaux);
	}

	/**
	 * liste des rendez-vous d'un médecin
	 * 
	 * @param idMedecin
	 * @param jour
	 * @param response
	 * @param origin
	 * @return
	 */
	@RequestMapping(value = "/getRvMedecinJour/{idMedecin}/{jour}", method = RequestMethod.GET)
	public Response<List<Rv>> getRvMedecinJour(@PathVariable("idMedecin") long idMedecin,
			@PathVariable("jour") String jour, HttpServletResponse response,
			@RequestHeader(value = "Origin", required = false) String origin) {
		// entêtes CORS
		rdvMedecinsCorsController.sendOptions(origin, response);
		// état de l'application
		if (messages != null) {
			return new Response<List<Rv>>(-1, messages, null);
		}
		// on vérifie la date
		Date jourAgenda = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		try {
			jourAgenda = sdf.parse(jour);
		} catch (ParseException e) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("La date [%s] est invalide", jour));
			return new Response<List<Rv>>(3, messages, null);
		}
		// on récupère le médecin
		Response<Medecin> responseMedecin = getMedecin(idMedecin);
		if (responseMedecin.getStatus() != 0) {
			return new Response<List<Rv>>(responseMedecin.getStatus(), responseMedecin.getMessages(), null);
		}
		Medecin médecin = responseMedecin.getBody();
		// liste de ses rendez-vous
		List<Rv> rvs = null;
		try {
			rvs = application.getRvMedecinJour(médecin.getId(), jourAgenda);
		} catch (Exception e1) {
			return new Response<List<Rv>>(4, Static.getErreursForException(e1), null);
		}
		// on rend la réponse
		SimpleBeanPropertyFilter rvFilter = SimpleBeanPropertyFilter.serializeAllExcept();
		SimpleBeanPropertyFilter creneauFilter = SimpleBeanPropertyFilter.serializeAllExcept("medecin");
		mapper.setFilterProvider(
				new SimpleFilterProvider().addFilter("rvFilter", rvFilter).addFilter("creneauFilter", creneauFilter));
		return new Response<List<Rv>>(0, null, rvs);
	}

	/**
	 * CLIENTS
	 * =========================================================================
	 * ==================
	 */

	/**
	 * liste des clients
	 * 
	 * @param response
	 * @param origin
	 * @return
	 */
	@RequestMapping(value = "/getAllClients", method = RequestMethod.GET)
	public Response<List<Client>> getAllClients(HttpServletResponse response,
			@RequestHeader(value = "Origin", required = false) String origin) {
		// entêtes CORS
		rdvMedecinsCorsController.sendOptions(origin, response);
		// état de l'application
		if (messages != null) {
			return new Response<List<Client>>(-1, messages, null);
		}
		// liste des clients
		try {
			return new Response<List<Client>>(0, null, application.getAllClients());
		} catch (Exception e) {
			return new Response<List<Client>>(1, Static.getErreursForException(e), null);
		}
	}

	/**
	 * Get client by id
	 * 
	 * @param id
	 * @param response
	 * @param origin
	 * @return
	 */
	@RequestMapping(value = "/getClientById/{id}", method = RequestMethod.GET)
	public Response<Client> getClientById(@PathVariable("id") long id, HttpServletResponse response,
			@RequestHeader(value = "Origin", required = false) String origin) {
		// entêtes CORS
		rdvMedecinsCorsController.sendOptions(origin, response);
		// état de l'application
		if (messages != null) {
			return new Response<Client>(-1, messages, null);
		}
		// on récupère le client
		return getClient(id);
	}

	/**
	 * Get RDV By ID
	 * 
	 * @param id
	 * @param response
	 * @param origin
	 * @return
	 */

	@RequestMapping(value = "/getRvById/{id}", method = RequestMethod.GET)
	public Response<Rv> getRvById(@PathVariable("id") long id, HttpServletResponse response,
			@RequestHeader(value = "Origin", required = false) String origin) {
		// entêtes CORS
		rdvMedecinsCorsController.sendOptions(origin, response);
		// état de l'application
		if (messages != null) {
			return new Response<Rv>(-1, messages, null);
		}
		// on rend le rv en empêchant la sérialisation du champ [medecin] de la classe [Rv]
		SimpleBeanPropertyFilter rvFilter = SimpleBeanPropertyFilter.serializeAllExcept("client", "creneau");
		mapper.setFilterProvider(new SimpleFilterProvider().addFilter("rvFilter", rvFilter));
		return getRv(id);
	}

	/**
	 * Get Creneaux by ID
	 * 
	 * @param id
	 * @param response
	 * @param origin
	 * @return
	 */
	@RequestMapping(value = "/getCreneauById/{id}", method = RequestMethod.GET)
	public Response<Creneau> getCreneauById(@PathVariable("id") long id, HttpServletResponse response,
			@RequestHeader(value = "Origin", required = false) String origin) {
		// entêtes CORS
		rdvMedecinsCorsController.sendOptions(origin, response);
		// état de l'application
		if (messages != null) {
			return new Response<Creneau>(-1, messages, null);
		}
		// on rend le créneau
		SimpleBeanPropertyFilter creneauFilter = SimpleBeanPropertyFilter.serializeAllExcept("medecin");
		mapper.setFilterProvider(new SimpleFilterProvider().addFilter("creneauFilter", creneauFilter));
		return getCreneau(id);
	}

	/**
	 * Add RDV 
	 * 
	 * @param post
	 * @param response
	 * @param origin
	 * @return
	 */
	@RequestMapping(value = "/ajouterRv", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8")
	public Response<Rv> ajouterRv(@RequestBody PostAjouterRv post, HttpServletResponse response,
			@RequestHeader(value = "Origin", required = false) String origin) {
		// entêtes CORS
		rdvMedecinsCorsController.sendOptions(origin, response);
		// état de l'application
		if (messages != null) {
			return new Response<Rv>(-1, messages, null);
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
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("La date [%s] est invalide", jour));
			return new Response<Rv>(6, messages, null);
		}
		// on récupère le créneau
		Response<Creneau> responseCréneau = getCreneau(idCreneau);
		if (responseCréneau.getStatus() != 0) {
			return new Response<Rv>(responseCréneau.getStatus(), responseCréneau.getMessages(), null);
		}
		Creneau créneau = responseCréneau.getBody();
		// on récupère le client
		Response<Client> responseClient = getClient(idClient);
		if (responseClient.getStatus() != 0) {
			return new Response<Rv>(responseClient.getStatus() + 2, responseClient.getMessages(), null);
		}
		Client client = responseClient.getBody();
		// on ajoute le Rv
		Rv rv = null;
		try {
			rv = application.ajouterRv(jourAgenda, créneau, client);
		} catch (Exception e1) {
			return new Response<Rv>(5, Static.getErreursForException(e1), null);
		}
		// on rend la réponse
		SimpleBeanPropertyFilter rvFilter = SimpleBeanPropertyFilter.serializeAllExcept();
		SimpleBeanPropertyFilter creneauFilter = SimpleBeanPropertyFilter.serializeAllExcept("medecin");
		mapper.setFilterProvider(
				new SimpleFilterProvider().addFilter("rvFilter", rvFilter).addFilter("creneauFilter", creneauFilter));
		return new Response<Rv>(0, null, rv);
	}

	/**
	 * DELETE A RV
	 * 
	 * @param post
	 * @param response
	 * @param origin
	 * @return
	 */
	@RequestMapping(value = "/supprimerRv", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8")
	public Response<Void> supprimerRv(@RequestBody PostSupprimerRv post, HttpServletResponse response,
			@RequestHeader(value = "Origin", required = false) String origin) {
		// entêtes CORS
		rdvMedecinsCorsController.sendOptions(origin, response);
		// état de l'application
		if (messages != null) {
			return new Response<Void>(-1, messages, null);
		}
		// on récupère les valeurs postées
		long idRv = post.getIdRv();
		// on récupère le rv
		Response<Rv> responseRv = getRv(idRv);
		if (responseRv.getStatus() != 0) {
			return new Response<Void>(responseRv.getStatus(), responseRv.getMessages(), null);
		}
		// suppression du rv
		try {
			application.supprimerRv(idRv);
		} catch (Exception e1) {
			return new Response<Void>(3, Static.getErreursForException(e1), null);
		}
		// ok
		return new Response<Void>(0, null, null);
	}

	/**
	 * Agenda d'un médecin : Liste des Rdv sur un jour donnée
	 * 
	 * @param idMedecin
	 * @param jour
	 * @param response
	 * @param origin
	 * @return
	 */
	@RequestMapping(value = "/getAgendaMedecinJour/{idMedecin}/{jour}", method = RequestMethod.GET)
	public Response<AgendaMedecinJour> getAgendaMedecinJour(@PathVariable("idMedecin") long idMedecin,
			@PathVariable("jour") String jour, HttpServletResponse response,
			@RequestHeader(value = "Origin", required = false) String origin) {
		// entêtes CORS
		rdvMedecinsCorsController.sendOptions(origin, response);
		// état de l'application
		if (messages != null) {
			return new Response<AgendaMedecinJour>(-1, messages, null);
		}
		// on vérifie la date
		Date jourAgenda = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		try {
			jourAgenda = sdf.parse(jour);
		} catch (ParseException e) {
			List<String> messages = new ArrayList<String>();
			messages.add(String.format("La date [%s] est invalide", jour));
			return new Response<AgendaMedecinJour>(3, messages, null);
		}
		// on récupère le médecin
		Response<Medecin> responseMedecin = getMedecin(idMedecin);
		if (responseMedecin.getStatus() != 0) {
			return new Response<AgendaMedecinJour>(responseMedecin.getStatus(), responseMedecin.getMessages(), null);
		}
		Medecin médecin = responseMedecin.getBody();
		// on récupère son agenda
		AgendaMedecinJour agenda = null;
		try {
			agenda = application.getAgendaMedecinJour(médecin.getId(), jourAgenda);
		} catch (Exception e1) {
			return new Response<AgendaMedecinJour>(4, Static.getErreursForException(e1), agenda);
		}
		// on rend l'agenda
		SimpleBeanPropertyFilter rvFilter = SimpleBeanPropertyFilter.serializeAllExcept();
		SimpleBeanPropertyFilter creneauFilter = SimpleBeanPropertyFilter.serializeAllExcept("medecin");
		mapper.setFilterProvider(
				new SimpleFilterProvider().addFilter("rvFilter", rvFilter).addFilter("creneauFilter", creneauFilter));
		return new Response<AgendaMedecinJour>(0, null, agenda);
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.GET)
	public Response<Void> authenticate(HttpServletResponse response,
			@RequestHeader(value = "Origin", required = false) String origin) {
		// entêtes CORS
		rdvMedecinsCorsController.sendOptions(origin, response);
		// réponse
		return new Response<Void>(0, null, null);
	}
}