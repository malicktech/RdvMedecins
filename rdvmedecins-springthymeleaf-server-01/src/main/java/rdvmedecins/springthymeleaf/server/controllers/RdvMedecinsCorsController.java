package rdvmedecins.springthymeleaf.server.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class RdvMedecinsCorsController extends BaseController {

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

	// login
	@RequestMapping(value = "/getLogin", method = RequestMethod.OPTIONS)
	public void getLogin(@RequestHeader(value = "Origin", required = false) String origin, HttpServletResponse response) {
		sendOptions(origin, response);
	}

	// agenda
	@RequestMapping(value = "/getAgenda", method = RequestMethod.OPTIONS)
	public void getAgenda(@RequestHeader(value = "Origin", required = false) String origin, HttpServletResponse response) {
		sendOptions(origin, response);
	}

	// page d'accueil
	@RequestMapping(value = "/checkUser", method = RequestMethod.OPTIONS)
	public void checkUser(@RequestHeader(value = "Origin", required = false) String origin, HttpServletResponse response) {
		sendOptions(origin, response);
	}

	// navbar+ jumbotron + accueil
	@RequestMapping(value = "/getNavbarRunJumbotronAccueil", method = RequestMethod.OPTIONS)
	public void getNavbarRunJumbotronAccueil(@RequestHeader(value = "Origin", required = false) String origin,
			HttpServletResponse response) {
		sendOptions(origin, response);
	}

	// nabar + jumbotron + accueil + agenda
	@RequestMapping(value = "/getNavbarRunJumbotronAccueilAgenda", method = RequestMethod.OPTIONS)
	public void getNavbarRunJumbotronAccueilAgenda(@RequestHeader(value = "Origin", required = false) String origin,
			HttpServletResponse response) {
		sendOptions(origin, response);
	}

	// jumbotron
	@RequestMapping(value = "/getJumbotron", method = RequestMethod.OPTIONS)
	public void getJumbotron(@RequestHeader(value = "Origin", required = false) String origin,
			HttpServletResponse response) {
		sendOptions(origin, response);
	}

	// accueil
	@RequestMapping(value = "/getAccueil", method = RequestMethod.OPTIONS)
	public void getAccueil(@RequestHeader(value = "Origin", required = false) String origin, HttpServletResponse response) {
		sendOptions(origin, response);
	}

	// navbar-start
	@RequestMapping(value = "/getNavbarStart", method = RequestMethod.OPTIONS)
	public void getNavbarStart(@RequestHeader(value = "Origin", required = false) String origin,
			HttpServletResponse response) {
		sendOptions(origin, response);
	}

	// navbar-run
	@RequestMapping(value = "/getNavbarRun", method = RequestMethod.OPTIONS)
	public void getNavbarRun(@RequestHeader(value = "Origin", required = false) String origin,
			HttpServletResponse response) {
		sendOptions(origin, response);
	}

	// suppression d'un rendez-vous
	@RequestMapping(value = "/supprimerRv", method = RequestMethod.OPTIONS)
	public void supprimerRv(@RequestHeader(value = "Origin", required = false) String origin, HttpServletResponse response) {
		sendOptions(origin, response);
	}

	// validation d'un rendez-vous
	@RequestMapping(value = "/validerRv", method = RequestMethod.OPTIONS)
	public void validerRv(@RequestHeader("Origin") String origin, HttpServletResponse response) {
		sendOptions(origin, response);
	}

}
