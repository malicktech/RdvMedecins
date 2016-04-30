package rdvmedecins.back.admin.controllers;

import java.util.ArrayList;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import rdvmedecins.entities.Client;
import rdvmedecins.metier.ClientService;

/**
 * Controller for managing Client View.
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

	/*
	 * LOCAL ATTRIBUTES
	 * =========================================================================
	 */

	private static final String INDEX_VIEW_NAME = "index";
	private static final String CLIENT_LIST_VIEW_NAME = "client-list";
	private static final String CLIENT_EDIT_VIEW_NAME = "client-edit";
	
	private static final String REDIRECT_CLIENT_LIST_PATH = "redirect:/admin/clients/list";
	
	/*
	 * CONTROLLERS METHODS
	 * =========================================================================
	 */

	@RequestMapping(value = { "/", "index", "/list", "/savepage" }, method = RequestMethod.GET)
	public String savePage(Model model) {
		model.addAttribute("client", new Client());
		model.addAttribute("allClients", (ArrayList<Client>) clientService.getAllClients());
		return CLIENT_LIST_VIEW_NAME;
	}

	/**
	 * Save Client
	 * 
	 * @param client
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = { "/save" }, method = RequestMethod.POST)
	public String saveClient(@ModelAttribute("client") @Valid Client client, BindingResult bindingResult,
			final RedirectAttributes redirectAttributes, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("allClients", (ArrayList<Client>) clientService.getAllClients());
			return CLIENT_LIST_VIEW_NAME;
		}
		if (clientService.createClient(client) != null) {
			redirectAttributes.addFlashAttribute("saveClient", "success");
		} else {
			redirectAttributes.addFlashAttribute("saveClient", "unsuccess");
		}

		return REDIRECT_CLIENT_LIST_PATH;
	}

	/**
	 * DELETE or EDIT client
	 * 
	 * @param operation
	 * @param id
	 * @param redirectAttributes
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{operation}/{id}", method = RequestMethod.GET)
	public String editRemoveClient(@PathVariable("operation") String operation, @PathVariable("id") Long id,
			final RedirectAttributes redirectAttributes, Model model) {
		if (operation.equals("delete")) {
			if (clientService.deleteClient(id)) {
				redirectAttributes.addFlashAttribute("deletion", "success");
			} else {
				redirectAttributes.addFlashAttribute("deletion", "unsuccess");
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
	 * Update Client
	 * 
	 * @param editClient
	 * @param redirectAttributes
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateClient(@ModelAttribute("editClient") Client editClient,
			final RedirectAttributes redirectAttributes) {
		
		logger.info(editClient.toString());
		
		if (clientService.updateClient(editClient) != null) {
			redirectAttributes.addFlashAttribute("edit", "success");
		} else {
			redirectAttributes.addFlashAttribute("edit", "unsuccess");
		}
		return REDIRECT_CLIENT_LIST_PATH;
	}
}
