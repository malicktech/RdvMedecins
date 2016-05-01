package rdvmedecins.back.admin.controllers;

import java.util.List;
import java.util.Locale;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import rdvmedecins.back.admin.models.ApplicationModel;
import rdvmedecins.entities.Client;
import rdvmedecins.entities.Medecin;
import rdvmedecins.metier.MedecinService;

@Controller
@RequestMapping(value = "/admin/medecins")
public class MedecinsController {

	private final Logger logger = LoggerFactory.getLogger(MedecinsController.class);

	/*
	 * DEPENDENCY INJECTION
	 * =========================================================================
	 */

	@Autowired
	private ApplicationModel application;

	@Autowired
	private MedecinService medecinService;

	@Autowired
	private MessageSource messageSource;

	/*
	 * LOCAL ATTRIBUTES
	 * =========================================================================
	 */

	private static final String MEDECIN_LIST_VIEW_NAME = "doctor-list";
	private static final String MEDECIN_EDIT_VIEW_NAME = "doctor-edit";
	private static final String REDIRECT_MEDECIN_LIST_PATH = "redirect:/admin/medecins/list";

	/*
	 * CONTROLLERS METHODS
	 * =========================================================================
	 */

	/**
     * GET  /index -> get all registered doctor -> return doctor-list view
     */
	@RequestMapping(value = { "/", "/index", "/list" }, method = RequestMethod.GET)
	public String getAllMedecins(Model model) {
		logger.info("IN: dashbord/index , GET");

		List<Medecin> medecins = medecinService.findAllMedecins();
		model.addAttribute("medecins", medecins);

		if (!model.containsAttribute("Medecin")) {
			Medecin medecin = new Medecin();
			model.addAttribute("medecin", medecin);
		}
		return MEDECIN_LIST_VIEW_NAME;
	}

	/**
     * POST  /admin/medecins/add -> Create a new Doctor
     */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@Valid @ModelAttribute("client") Medecin medecin, BindingResult bindingResult, final RedirectAttributes redirectAttributes) {
		logger.info("IN: Medecins/add-POST");

		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("medecin", medecin);
			return REDIRECT_MEDECIN_LIST_PATH;
		} 
		Medecin registeredMedecin = medecinService.createMedecin(medecin);
		if (registeredMedecin != null) {
			String message = "Medecin " + registeredMedecin.getId() + " was successfully added";
			redirectAttributes.addFlashAttribute("message", message);
			redirectAttributes.addFlashAttribute("registrationTask", "success");
		} else {
			redirectAttributes.addFlashAttribute("registrationTask", "unsuccess");
		}
		return REDIRECT_MEDECIN_LIST_PATH;
	}
	
	/**
     * GET  /operation/:id	-> DELETE or EDIT Dcotor
     */
	@RequestMapping(value = "/{operation}/{id}", method = RequestMethod.GET)
	public String editRemoveClient(@PathVariable("operation") String operation, @PathVariable("id") Long id,
			final RedirectAttributes redirectAttributes, Model model) {
		logger.info("GET -> /medecins/"+ operation +"/"+ id );
		
		if (operation.equals("delete")) {
			try {
				medecinService.deleteMedecin(id);
				redirectAttributes.addFlashAttribute("deletion", "success");
			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("deletion", "unsuccess");
				redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
			}
		} else if (operation.equals("edit")) {
			Medecin medecinToEdit = medecinService.findOneMedecin(id);
			if (medecinToEdit != null) {
				logger.info("Medecins to edit :  " + medecinToEdit.toString());
				model.addAttribute("medecinToEdit", medecinToEdit);
				return MEDECIN_EDIT_VIEW_NAME;
			} else {
				redirectAttributes.addFlashAttribute("status", "notfound");
			}
		}
		return REDIRECT_MEDECIN_LIST_PATH;
	}
	
	/**
     * POST  /update -> Updates an existing Doctor.
     */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateClient(@ModelAttribute("medecinToEdit") @Valid Medecin medecinToEdit, BindingResult bindingResult,
			final RedirectAttributes redirectAttributes, Model model) {		
		logger.info("GET -> /medecins/update/ -> : " + medecinToEdit.toString() );
		
		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("editMedecin", medecinToEdit);
			return MEDECIN_EDIT_VIEW_NAME;
		}		
		if (medecinService.updateMedecin(medecinToEdit) != null) {
			redirectAttributes.addFlashAttribute("edit", "success");
		} else {
			redirectAttributes.addFlashAttribute("edit", "unsuccess");
		}
		return REDIRECT_MEDECIN_LIST_PATH;
	}
}
