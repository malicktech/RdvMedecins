package rdvmedecins.web.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import rdvmedecins.web.models.ApplicationModel;

@Controller
public class RdvMedecinsCorsController {

	@Autowired
	private ApplicationModel application;

	// envoi des options au client
	public void sendOptions(String origin, HttpServletResponse response) {
		// Cors allowed ?
		if (!application.isCorsAllowed() || origin == null || !origin.startsWith("http://localhost")) {
			return;
		}
		// on fixe le header CORS
		response.addHeader("Access-Control-Allow-Origin", origin);
		// on autorise certains headers
		response.addHeader("Access-Control-Allow-Headers", "accept, authorization, content-type");
		// on autorise le GET
		response.addHeader("Access-Control-Allow-Methods", "GET, POST");
	}

	// liste des médecins
	@RequestMapping(value = "/getAllMedecins", method = RequestMethod.OPTIONS)
	public void getAllMedecins(@RequestHeader(value = "Origin", required = false) String origin,
			HttpServletResponse response) {
		sendOptions(origin, response);
	}

	// liste des clients
	@RequestMapping(value = "/getAllClients", method = RequestMethod.OPTIONS)
	public void getAllClients(@RequestHeader(value = "Origin", required = false) String origin,
			HttpServletResponse response) {
		sendOptions(origin, response);
	}

	// liste des créneaux
	@RequestMapping(value = "/getAllCreneaux/{idMedecin}", method = RequestMethod.OPTIONS)
	public void getAllCreneaux(@RequestHeader(value = "Origin", required = false) String origin,
			HttpServletResponse response) {
		sendOptions(origin, response);
	}

	// liste des Rv d'un médecin
	@RequestMapping(value = "/getRvMedecinJour/{idMedecin}/{jour}", method = RequestMethod.OPTIONS)
	public void getRvMedecinJour(@RequestHeader(value = "Origin", required = false) String origin,
			HttpServletResponse response) {
		sendOptions(origin, response);
	}

	@RequestMapping(value = "/getClientById/{id}", method = RequestMethod.OPTIONS)
	public void getClientById(@RequestHeader(value = "Origin", required = false) String origin,
			HttpServletResponse response) {
		sendOptions(origin, response);
	}

	@RequestMapping(value = "/getMedecinById/{id}", method = RequestMethod.OPTIONS)
	public void getMedecinById(@RequestHeader(value = "Origin", required = false) String origin,
			HttpServletResponse response) {
		sendOptions(origin, response);
	}

	@RequestMapping(value = "/getCreneauById/{id}", method = RequestMethod.OPTIONS)
	public void getCreneauById(@RequestHeader(value = "Origin", required = false) String origin,
			HttpServletResponse response) {
		sendOptions(origin, response);
	}

	@RequestMapping(value = "/getRvById/{id}", method = RequestMethod.OPTIONS)
	public void getRvById(@RequestHeader(value = "Origin", required = false) String origin, HttpServletResponse response) {
		sendOptions(origin, response);
	}

	// agenda du médecin
	@RequestMapping(value = "/getAgendaMedecinJour/{idMedecin}/{jour}", method = RequestMethod.OPTIONS)
	public void getAgendaMedecinJour(@RequestHeader(value = "Origin", required = false) String origin,
			HttpServletResponse response) {
		sendOptions(origin, response);
	}

	@RequestMapping(value = "/ajouterRv", method = RequestMethod.OPTIONS)
	public void ajouterRv(@RequestHeader("Origin") String origin, HttpServletResponse response) {
		sendOptions(origin, response);
	}

	@RequestMapping(value = "/supprimerRv", method = RequestMethod.OPTIONS)
	public void supprimerRv(@RequestHeader("Origin") String origin, HttpServletResponse response) {
		sendOptions(origin, response);
	}

	@RequestMapping(value = "/authenticate", method = RequestMethod.OPTIONS)
	public void authenticate(HttpServletResponse response, @RequestHeader(value = "Origin", required = false) String origin) {
		sendOptions(origin, response);
	}
}
