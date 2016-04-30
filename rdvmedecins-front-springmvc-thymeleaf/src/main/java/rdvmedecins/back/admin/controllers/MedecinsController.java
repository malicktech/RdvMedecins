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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import rdvmedecins.back.admin.models.ApplicationModel;
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

	private static final String MEDECIN_LIST_VIEW_NAME = "medecin-list";
	private static final String MEDECIN_EDIT_VIEW_NAME = "medecin-edit";
	private static final String MEDECIN_DELETE_VIEW_NAME = "medecin-delete";

	/*
	 * CONTROLLERS METHODS
	 * =========================================================================
	 */

	/**
	 * Liste de tous les médecins du cabinet médical
	 */
	@RequestMapping(value = { "/", "/index", "/list" }, method = RequestMethod.GET)
	public String getAllMedecins(Model model) {
		logger.info("IN: dashbord/index , GET");

		List<Medecin> medecins = medecinService.findAllMedecins();
		model.addAttribute("medecins", medecins);

		if (!model.containsAttribute("Medecin")) {
			logger.info("Adding Medecin object to model");
			Medecin medecin = new Medecin();
			model.addAttribute("medecin", medecin);
		}

		return MEDECIN_LIST_VIEW_NAME;
	}

	/**
	 * Add Medecins
	 * 
	 * @param Medecin
	 * @param result
	 * @param redirectAttrs
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@Valid @ModelAttribute Medecin medecin, BindingResult result, RedirectAttributes redirectAttrs) {
		logger.info("IN: Medecins/add-POST");

		if (result.hasErrors()) {
			logger.info("Medecin-add error: " + result.toString());
			redirectAttrs.addFlashAttribute("org.springframework.validation.BindingResult.Medecin", result);
			redirectAttrs.addFlashAttribute("medecin", medecin);
			return "redirect:/admin/medecins/list";
		} else {
			medecinService.createMedecin(medecin);
			String message = "Medecin " + medecin.getId() + " was successfully added";
			redirectAttrs.addFlashAttribute("message", message);
			return "redirect:/admin/medecins/list";
		}
	}

	/**
	 * UPDATE
	 * 
	 * @param Medecin
	 * @param result
	 * @param redirectAttrs
	 * @param action
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String editStrategyPage(@RequestParam(value = "id", required = true) Long id, Model model) {
		logger.info("IN: Strategy/edit-GET:  ID to query = " + id);

		if (!model.containsAttribute("strategy")) {
			logger.info("Adding Strategy object to model");
			Medecin medecin = application.getMedecinById(id);
			logger.info("Medecins/edit-GET:  " + medecin.toString());
			model.addAttribute("medecin", medecin);
		}
		return MEDECIN_EDIT_VIEW_NAME;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public String updateMedecin(@Valid @ModelAttribute Medecin medecin, BindingResult result,
			RedirectAttributes redirectAttrs, @RequestParam(value = "action", required = true) String action,
			Locale locale) {
		logger.info("IN: Medecins/update-POST: " + action);

		if (action.equals(messageSource.getMessage("button.action.cancel", null, locale))) {
			String message = "Medecin " + medecin.getId() + " update cancelled";
			redirectAttrs.addFlashAttribute("message", message);
		} else if (result.hasErrors()) {
			logger.info("Medecin-edit error: " + result.toString());
			redirectAttrs.addFlashAttribute("org.springframework.validation.BindingResult.Medecin", result);
			redirectAttrs.addFlashAttribute("Medecin", medecin);
			return "redirect:/admin/medecins/edit?id=" + medecin.getId();
		} else if (action.equals(messageSource.getMessage("button.action.save", null, locale))) {
			logger.info("Medecin/edit-POST:  " + medecin.toString());
			medecinService.updateMedecin(medecin);
			String message = "Medecin " + medecin.getId() + " was successfully edited";
			redirectAttrs.addFlashAttribute("message", message);
		}

		return "redirect:/admin/medecins/list";
	}

	/**
	 * DELETE
	 * 
	 * @param id
	 * @param phase
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public String deleteMedecinPage(@RequestParam(value = "id", required = true) Long id,
			@RequestParam(value = "phase", required = true) String phase, Model model, Locale locale) {

		Medecin medecin = application.getMedecinById(id);
		logger.info("IN: Medecin/delete-GET | id = " + id + " | phase = " + phase + " | " + medecin.toString());

		if (phase.equals(messageSource.getMessage("button.action.cancel", null, locale))) {
			String message = "Medecin delete was cancelled.";
			model.addAttribute("message", message);
			return "redirect:/admin/medecins/list";
		} else if (phase.equals(messageSource.getMessage("button.action.stage", null, locale))) {
			String message = "Medecin " + medecin.getId() + " queued for display.";
			model.addAttribute("medecin", medecin);
			model.addAttribute("message", message);
			return MEDECIN_DELETE_VIEW_NAME;
		} else if (phase.equals(messageSource.getMessage("button.action.delete", null, locale))) {
			medecinService.deleteMedecin(id);
			String message = "Medecin " + medecin.getId() + " was successfully deleted";
			model.addAttribute("message", message);
			return "redirect:/medecin/list";
		}

		return "redirect:/admin/medecins/list";
	}
}
