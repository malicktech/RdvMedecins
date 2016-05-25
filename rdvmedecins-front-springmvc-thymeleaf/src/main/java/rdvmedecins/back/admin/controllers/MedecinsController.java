package rdvmedecins.back.admin.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import rdvmedecins.back.admin.web.dto.PostAjouterRv;
import rdvmedecins.back.admin.web.dto.PostSupprimerRv;
import rdvmedecins.domain.Client;
import rdvmedecins.domain.Creneau;
import rdvmedecins.domain.Medecin;
import rdvmedecins.domain.Rv;
import rdvmedecins.domain.dto.AgendaMedecinJour;
import rdvmedecins.domain.dto.CreneauMedecinJour;
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

	private static final String MEDECIN_LIST_VIEW_NAME = "doctor-list";
	private static final String MEDECIN_EDIT_VIEW_NAME = "doctor-edit";
	private static final String REDIRECT_MEDECIN_LIST_PATH = "redirect:/admin/medecins/list";
	private static final String REDIRECT_MEDECIN_APPOINTMENT = "doctor-appointment";
	private static final String MEDECIN_DETAIL_VIEW_NAME = "doctor-view";

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

		List<Medecin> medecins = medecinService.findAllMedecins();
		model.addAttribute("medecins", medecins);

		if (!model.containsAttribute("Medecin")) {
			Medecin medecin = new Medecin();
			model.addAttribute("medecin", medecin);
		}
		return MEDECIN_LIST_VIEW_NAME;
	}

	/**
	 * GET /Medecins/:id -> get the "id" doctor.
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getMedecin(@PathVariable Long id, Model model) {
		logger.debug("REST request to get Medecins Detail : {}", id);
		// on récupère le médecin
		Medecin medecin = medecinService.findOneMedecin(id);
		// créneaux du médecin sur une journée
		List<Creneau> creneaux = creneauRdvService.findAllTimeslotOfDoctor(medecin.getId());

		model.addAttribute("creneaux", creneaux);
		model.addAttribute("medecin", medecin);
		model.addAttribute("today", Calendar.getInstance());

		return MEDECIN_DETAIL_VIEW_NAME;
	}

	@RequestMapping(value = "/getAgendaMedecinJour/{idMedecin}/{jour}", method = RequestMethod.GET)
	public String getAgendaMedecinJour(@PathVariable("idMedecin") Long idMedecin, @PathVariable("jour") String jour,
			Model model) {
		logger.debug("REST request to get getAgendaMedecinJour/{}/{}", idMedecin, jour);
		// on vérifie la date
		Date jourAgenda = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		try {
			jourAgenda = sdf.parse(jour);
		} catch (ParseException e) {
			logger.debug("La date {} est invalide", jour);
		}
		// on récupère le médecin
		Medecin medecin = medecinService.findOneMedecin(idMedecin);
		// on récupère son agenda
		AgendaMedecinJour agenda = creneauRdvService.getAgendaMedecinJour(medecin.getId(), jourAgenda);

		Map<String, Rv> appointmentMap = new HashMap<>();
		CreneauMedecinJour[] cMJs = agenda.getCreneauxMedecinJour();
		/*
		 * for (int i = 0; i < cMJs.length - 1; i++) {
		 * appointmentMap.put(getTextForCreneau(cMJs[i].getCreneau()),
		 * cMJs[i].getRv()); }
		 */
		for (CreneauMedecinJour cmj : cMJs) {
			appointmentMap.put(getTextForCreneau(cmj.getCreneau()), cmj.getRv());
			// System.out.println("rv : " + cmj.getRv().getId() +
			// cmj.getRv().getClient().getFirstName());
		}

		model.addAttribute("medecin", medecin);
		model.addAttribute("today", Calendar.getInstance());
		model.addAttribute("timeslotMap", appointmentMap);

		return MEDECIN_DETAIL_VIEW_NAME;
	}

	// mise en forme du libellé d'un créneau horaire
	public String getTextForCreneau(Creneau creneau) {
		return getTextFor(creneau.getHdebut()) + "h" + getTextFor(creneau.getMdebut()) + ":"
				+ getTextFor(creneau.getHfin()) + "h" + getTextFor(creneau.getMfin());
	}

	// met un 0 devant le chiffre si moins de deux chiffres
	public String getTextFor(Integer number) {
		return number < 10 ? "0" + number : number.toString();
	}

	/**
	 * POST /admin/medecins/add -> Create a new Doctor
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String add(@Valid @ModelAttribute("client") Medecin medecin, BindingResult bindingResult,
			final RedirectAttributes redirectAttributes) {
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
	 * POST /update -> Updates an existing Doctor.
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateClient(@ModelAttribute("medecinToEdit") @Valid Medecin medecinToEdit,
			BindingResult bindingResult, final RedirectAttributes redirectAttributes, Model model) {
		logger.info("GET -> /medecins/update/ -> : " + medecinToEdit.toString());

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

	/**
	 * GET /operation/:id -> DELETE or EDIT Dcotor
	 */
	@RequestMapping(value = "/appointment/{id}", method = RequestMethod.GET)
	public String getdoctorAppointment(@PathVariable("id") Long id, final RedirectAttributes redirectAttributes,
			Model model) {
		logger.info("GET -> /medecins/appointment/" + id);

		return REDIRECT_MEDECIN_APPOINTMENT;
	}

	/**
	 * POST /ajouterRv
	 */
	@RequestMapping(value = "/rv/add", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8")
	public String ajouterRv(@Valid @ModelAttribute("post") PostAjouterRv post,
			final RedirectAttributes redirectAttributes, Model model) {

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
			logger.debug("La date {} est invalide", jour);
		}
		// on récupère le créneau
		Creneau creneau = creneauRdvService.findTimeslotById(idCreneau);
		// on récupère le client
		Client client = clientService.findOneClient(idClient);
		// on ajoute le Rv
		Rv rv = null;

		rv = creneauRdvService.createRv(jourAgenda, creneau, client);

		return MEDECIN_DETAIL_VIEW_NAME;
	}
	
	/**
	 * POST /supprimerRv
	 */
	@RequestMapping(value = "/rv/delete", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8")
	public String supprimerRv(@Valid @ModelAttribute("post") PostSupprimerRv post,
			final RedirectAttributes redirectAttributes, Model model) {

		// on récupère les valeurs postées
				long idRv = post.getIdRv();

				// on récupère le rv
		Rv rv = creneauRdvService.findRvById(idRv);
		// suppression du rv
		creneauRdvService.deleteRv(idRv);

		return MEDECIN_DETAIL_VIEW_NAME;
	}
}
