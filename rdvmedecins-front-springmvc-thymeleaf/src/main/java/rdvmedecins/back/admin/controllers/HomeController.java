package rdvmedecins.back.admin.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import rdvmedecins.service.ClientService;
import rdvmedecins.service.CreneauRdvService;
import rdvmedecins.service.MedecinService;

@Controller
public class HomeController {

	private final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/*
	 * DEPENDENCY INJECTION
	 * =========================================================================
	 */
	
	@Autowired
	private ClientService clientService;
	
	@Autowired
	private MedecinService medecinService;
	
	@Autowired
	private CreneauRdvService creneauRdvService;

	/*
	 * LOCAL ATTRIBUTES
	 * =========================================================================
	 */
	
	private static final String LOGIN_VIEW_NAME = "login";
	private static final String HOME_VIEW_NAME = "home";

	/*
	 * CONTROLLERS METHODS
	 * =========================================================================
	 */

	/**
     * GET  /index -> get Indicators stats number -> return admin index view
     */
	@RequestMapping(value = { "/", "/home", "/index" }, method = RequestMethod.GET)
	public String home(Model model) {
		logger.info("IN: home, GET");
		
		model.addAttribute("totalNumberOfDoctors", medecinService.countAllMedecins());
		model.addAttribute("totalNumberOfPatients", clientService.countAllClients());
		model.addAttribute("totalNumberOfRegistredAppointment", creneauRdvService.countAllAppointments());
		return HOME_VIEW_NAME;
	}

	/**
     * GET  /login ->  return login page
     */
//	@RequestMapping(value = {"/login"}, method = RequestMethod.GET)
//	public String login() {
//		logger.info("IN: Login, GET");		
//		return LOGIN_VIEW_NAME;
//	}
	
	/**
     * GET  /logout ->  return login page
     */
//	@RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
//	public String logout() {
//		logger.info("IN: Logout, GET");		
//		return LOGIN_VIEW_NAME;
//	}


}