package rdvmedecins.back.admin.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import rdvmedecins.back.admin.helpers.Static;
import rdvmedecins.back.admin.models.ApplicationModel;
import rdvmedecins.back.admin.models.Response;
import rdvmedecins.entities.Medecin;

@Controller
public class HomeController {

	private final Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private ApplicationModel application;

	private static final String INDEX_VIEW_NAME = "index";
	private static final String LOGIN_VIEW_NAME = "login";
	private static final String ERROR_VIEW_NAME = "error";
	private static final String HOME_VIEW_NAME = "home";
	private static final String HELLO_VIEW_NAME = "hello";

	/**
	 * Home Page
	 * 
	 * @return
	 */
	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public String savePage() {
		return HOME_VIEW_NAME;
	}

	/**
	 * Login form Page
	 * 
	 * @return
	 */
	@RequestMapping("/login")
	public String login() {
		return LOGIN_VIEW_NAME;
	}

	/**
	 * logout url
	 * 
	 * @return login form view
	 */
	@RequestMapping(value = "/logout")
	public String logout() {
		return LOGIN_VIEW_NAME;
	}

	@RequestMapping("/hello")
	public String hello() {
		return HELLO_VIEW_NAME;
	}

	/**
	 * Error page : DISABLED
	 * @param request
	 * @param model
	 * @return
	 */	
	// @RequestMapping("/error")
	public String error(HttpServletRequest request, Model model) {
		model.addAttribute("errorCode", request.getAttribute("javax.servlet.error.status_code"));
		Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
		String errorMessage = null;
		if (throwable != null) {
			errorMessage = throwable.getMessage();
		}
		model.addAttribute("errorMessage", errorMessage);
		return ERROR_VIEW_NAME;
	}

}