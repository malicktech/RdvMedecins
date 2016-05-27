package rdvmedecins.back.admin.controllers;

import java.util.Calendar;
import java.util.List;

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

import rdvmedecins.back.admin.config.ConstantsViewName;
import rdvmedecins.back.admin.web.dto.PostAjouterRv;
import rdvmedecins.domain.Creneau;
import rdvmedecins.domain.UserMedecin;
import rdvmedecins.service.ClientService;
import rdvmedecins.service.CreneauRdvService;
import rdvmedecins.service.MedecinService;

@Controller
@RequestMapping(value = "/admin/medecins")
public class MedecinsController {

	private final Logger logger = LoggerFactory.getLogger(MedecinsController.class);

	/*
	 * DEPENDENCY INJECTION
	 * =========================================================================
	 */

	@Autowired
	private MedecinService medecinService;

	@Autowired
	private ClientService clientService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private CreneauRdvService creneauRdvService;

	/*
	 * LOCAL ATTRIBUTES
	 * =========================================================================
	 */



	/*
	 * CONTROLLERS METHODS
	 * =========================================================================
	 */

	/**
	 * GET /index -> get all registered doctor -> return doctor-list view
	 */
	@RequestMapping(value = { "/", "/index", "/list" }, method = RequestMethod.GET)
	public String getAllMedecins(Model model) {
		logger.info("IN: dashbord/index , GET");

		List<UserMedecin> medecins = medecinService.findAllMedecins();
		model.addAttribute("medecins", medecins);

		if (!model.containsAttribute("Medecin")) {
			UserMedecin medecin = new UserMedecin();
			model.addAttribute("medecin", medecin);
		}
		return ConstantsViewName.MEDECIN_LIST_VIEW_NAME;
	}

	/**
	 * GET /Medecins/:id -> get the "id" doctor.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getMedecin(@PathVariable Long id, Model model) {
		logger.debug("REST request to get Medecins Detail : {}", id);
		// on récupère le médecin
		UserMedecin medecin = medecinService.findOneMedecin(id);
		// créneaux du médecin sur une journée
		List<Creneau> creneaux = creneauRdvService.findAllTimeslotOfDoctor(medecin.getId());

		model.addAttribute("creneaux", creneaux);
		model.addAttribute("medecin", medecin);
		model.addAttribute("today", Calendar.getInstance());

		// model bean for form in modal
		if (!model.containsAttribute("addRvDto")) {
			PostAjouterRv addRvDto = new PostAjouterRv();
			model.addAttribute("addRvDto", addRvDto);
		}

		return ConstantsViewName.DOCTOR_VIEW;
	}

	/**
	 * POST /admin/medecins/add -> Create a new Doctor
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@Valid @ModelAttribute("medecin") UserMedecin medecin, BindingResult bindingResult,
			final RedirectAttributes redirectAttributes) {
		logger.info("IN: Medecins/add-POST");

		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("medecin", medecin);
			return ConstantsViewName.REDIRECT_MEDECIN_LIST_PATH;
		}
		UserMedecin registeredMedecin = medecinService.createMedecin(medecin);
		if (registeredMedecin != null) {
			String message = "Medecin " + registeredMedecin.getId() + " was successfully added";
			redirectAttributes.addFlashAttribute("message", message);
			redirectAttributes.addFlashAttribute("registrationTask", "success");
		} else {
			redirectAttributes.addFlashAttribute("registrationTask", "unsuccess");
		}
		return ConstantsViewName.REDIRECT_MEDECIN_LIST_PATH;
	}

	/**
	 * GET /operation/:id -> DELETE or EDIT Dcotor
	 */
	@RequestMapping(value = "/{operation}/{id}", method = RequestMethod.GET)
	public String editRemoveClient(@PathVariable("operation") String operation, @PathVariable("id") Long id,
			final RedirectAttributes redirectAttributes, Model model) {
		logger.info("GET -> /medecins/" + operation + "/" + id);

		if (operation.equals("delete")) {
			try {
				medecinService.deleteMedecin(id);
				redirectAttributes.addFlashAttribute("deletion", "success");
			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("deletion", "unsuccess");
				redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
			}
		} else if (operation.equals("edit")) {
			UserMedecin medecinToEdit = medecinService.findOneMedecin(id);
			if (medecinToEdit != null) {
				logger.info("Medecins to edit :  " + medecinToEdit.toString());
				model.addAttribute("medecinToEdit", medecinToEdit);
				return ConstantsViewName.MEDECIN_EDIT_VIEW_NAME;
			} else {
				redirectAttributes.addFlashAttribute("status", "notfound");
			}
		}
		return ConstantsViewName.REDIRECT_MEDECIN_LIST_PATH;
	}

	/**
	 * POST /admin/medecins/update -> Updates an existing Doctor.
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateClient(@ModelAttribute("medecinToEdit") @Valid UserMedecin medecinToEdit,
			BindingResult bindingResult, final RedirectAttributes redirectAttributes, Model model) {
		logger.info("GET -> /medecins/update/ -> : " + medecinToEdit.toString());

		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("editMedecin", medecinToEdit);
			return ConstantsViewName.MEDECIN_EDIT_VIEW_NAME;
		}
		if (medecinService.updateMedecin(medecinToEdit) != null) {
			redirectAttributes.addFlashAttribute("edit", "success");
		} else {
			redirectAttributes.addFlashAttribute("edit", "unsuccess");
		}
		return ConstantsViewName.REDIRECT_MEDECIN_LIST_PATH;
	}



	
}
