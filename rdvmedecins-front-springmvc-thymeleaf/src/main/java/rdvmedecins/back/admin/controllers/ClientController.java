package rdvmedecins.back.admin.controllers;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import rdvmedecins.domain.Client;
import rdvmedecins.service.ClientService;

/**
 * Controller for managing Patient Admin View.
 */

@Controller
@RequestMapping(value = "/admin/clients")
public class ClientController {

	private final Logger logger = LoggerFactory.getLogger(ClientController.class);

	/*
	 * DEPENDENCY INJECTION
	 * =========================================================================
	 */

	@Autowired
	private ClientService clientService;
	
	@Autowired
	private MessageSource messageSource;

	/*
	 * LOCAL ATTRIBUTES
	 * =========================================================================
	 */

	private static final String CLIENT_LIST_VIEW_NAME = "patient-list";
	private static final String CLIENT_EDIT_VIEW_NAME = "patient-edit";
	
	private static final String REDIRECT_CLIENT_LIST_PATH = "redirect:/admin/clients/list";
	
	/*
	 * CONTROLLERS METHODS
	 * =========================================================================
	 */

	/**
     * GET  /index -> get all the patients -> return patient-list view
     */
	@RequestMapping(value = { "/", "index", "/list", "/savepage" }, method = RequestMethod.GET)
	public String savePage(Model model, Locale locale, HttpServletRequest request) {
		model.addAttribute("client", new Client());
		
		List<Client> clients = clientService.getAllClients();
		model.addAttribute("allClients", clients);
			
		// TODO to delete for test
		model.addAttribute("clientId", 1);
		Long testId = 1L;
		model.addAttribute("longId", testId);
		
		// disable automatic show of modal register 
    	model.addAttribute("isRegisterModalShowOnLoad", false);
    	
		setModel(model, locale, null);
		
		// check error, check whether flash attributes exists in previous request
		Map<String,?> inputFlashMap  = RequestContextUtils.getInputFlashMap(request); 
        if (inputFlashMap != null) {
        	System.out.println("GET  /index -> inputFlashMap attributes : " + inputFlashMap.keySet().toString());	
        }
		
		return CLIENT_LIST_VIEW_NAME;
	}
	
	/**
	 * préparation du modèle de la vue
	 */
	private void setModel(Model model, Locale locale, String message) {
		// on ne gère que les locales fr-FR, en-US
		String language = locale.getLanguage();
		String country = null;
		if (language.equals("fr")) {
			country = "FR";
		}
		if (language.equals("en")) {
			country = "US";
		}
		model.addAttribute("locale", String.format("%s-%s", language, country));
		// le message éventuel
		if (message != null) {
			model.addAttribute("message", message);
		}
	}

	/**
     * POST  /admin/medecins/save -> Create a new Patient
     */
	@RequestMapping(value = { "/save" }, method = RequestMethod.POST)
	public String saveClient(@ModelAttribute("client") @Valid Client client, BindingResult bindingResult,
			final RedirectAttributes redirectAttributes, Model model, Locale locale) {
		logger.info("POST -> /clients/save/ -> : " + client.toString() );

		if (bindingResult.hasErrors()) {
			model.addAttribute("allClients", clientService.getAllClients());
			model.addAttribute("isRegisterModalShowOnLoad", true);
			return CLIENT_LIST_VIEW_NAME;
		}
		Client registeredClient = clientService.createClient(client);
		if ( registeredClient != null) {			
			String message = "Patient " + registeredClient.getId() + messageSource.getMessage("allert.message.action.add.result.success", null, locale);
			redirectAttributes.addFlashAttribute("message", message);
			redirectAttributes.addFlashAttribute("registrationTask", "success");
		} else {
			redirectAttributes.addFlashAttribute("registrationTask", "unsuccess");
		}
		return REDIRECT_CLIENT_LIST_PATH;
	}

	/**
     * GET  /operation/:id	-> DELETE or EDIT Patient
     */
	@RequestMapping(value = "/{operation}/{id}", method = RequestMethod.GET)
	public String editRemoveClient(@PathVariable("operation") String operation, @PathVariable("id") Long id,
			final RedirectAttributes redirectAttributes, Model model) {

		if (operation.equals("delete")) {
			try {
				clientService.deleteClient(id);
				redirectAttributes.addFlashAttribute("deletionTask", "success");				
			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("deletionTask", "unsuccess");
				redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
			}
		} else if (operation.equals("edit")) {
			Client editClient = clientService.findOneClient(id);
			if (editClient != null) {
				model.addAttribute("editClient", editClient);
				return CLIENT_EDIT_VIEW_NAME;
			} else {
				redirectAttributes.addFlashAttribute("status", "notfound");
			}
		}
		return REDIRECT_CLIENT_LIST_PATH;
	}

	/**
     * POST  /update -> Updates an existing Patient.
     */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateClient(@ModelAttribute("client") @Valid Client client, BindingResult bindingResult,
			final RedirectAttributes redirectAttributes, Model model, Locale locale) {
		
		logger.info(client.toString());
		
		if (bindingResult.hasErrors()) {
			return CLIENT_EDIT_VIEW_NAME;
		}
		Client editedClient = clientService.updateClient(client);
		if ( editedClient != null) {
			String message = "Patient " + editedClient.getId() + messageSource.getMessage("allert.message.action.edit.result.success", null, locale);
			redirectAttributes.addFlashAttribute("message", message);
			redirectAttributes.addFlashAttribute("modificationTask", "success");
		} else {
			redirectAttributes.addFlashAttribute("modificationTask", "unsuccess");
		}
		return REDIRECT_CLIENT_LIST_PATH;
	}
	
	/*
	 * LOCAL METHODS
	 * =========================================================================
	 */
}
