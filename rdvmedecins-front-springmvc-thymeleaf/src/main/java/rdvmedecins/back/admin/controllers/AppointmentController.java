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
import rdvmedecins.domain.Rv;
import rdvmedecins.domain.UserClient;
import rdvmedecins.domain.UserMedecin;
import rdvmedecins.domain.dto.AgendaMedecinJour;
import rdvmedecins.domain.dto.CreneauMedecinJour;
import rdvmedecins.service.ClientService;
import rdvmedecins.service.CreneauRdvService;
import rdvmedecins.service.MedecinService;

@Controller
@RequestMapping(value = "/admin/medecins")
public class AppointmentController {

	private final Logger logger = LoggerFactory.getLogger(AppointmentController.class);

	/*
	 * DEPENDENCY INJECTION
	 * =========================================================================
	 */

	@Autowired
	private MedecinService medecinService;

	@Autowired
	private ClientService clientService;

	@Autowired
	private CreneauRdvService creneauRdvService;

	/*
	 * LOCAL ATTRIBUTES
	 * =========================================================================
	 */

	private static final String REDIRECT_MEDECIN_APPOINTMENT = "doctor-appointment";

	/*
	 * CONTROLLERS METHODS
	 * =========================================================================
	 */

	/**
	 * GET /admin/medecins/appointment/:id ->
	 */
	@RequestMapping(value = "/appointment/{id}", method = RequestMethod.GET)
	public String getdoctorAppointment(@PathVariable("id") Long id, final RedirectAttributes redirectAttributes,
			Model model) {
		logger.info("GET -> /medecins/appointment/" + id);

		return REDIRECT_MEDECIN_APPOINTMENT;
	}

	@RequestMapping(value = "/getAgendaMedecinJour/{idMedecin}/{jour}", method = RequestMethod.GET)
	public String getAgendaMedecinJour(@PathVariable("idMedecin") Long idMedecin, @PathVariable("jour") String jour,
			Model model) {
		logger.debug("REST request to get getAgendaMedecinJour/{}/{}", idMedecin, jour);
		// check date format
		Date jourAgenda = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		try {
			jourAgenda = sdf.parse(jour);
		} catch (ParseException e) {
			logger.debug("La date {} est invalide", jour);
		}
		// get doctor and his timeslots
		UserMedecin medecin = medecinService.findOneMedecin(idMedecin);
		AgendaMedecinJour agenda = creneauRdvService.getAgendaMedecinJour(medecin.getId(), jourAgenda);

		Map<Creneau, Rv> appointmentMap = new HashMap<>();
		CreneauMedecinJour[] cMJs = agenda.getCreneauxMedecinJour();
		for (CreneauMedecinJour cmj : cMJs) {
			// appointmentMap.put(getTextForCreneau(cmj.getCreneau()),
			// cmj.getRv());
			appointmentMap.put(cmj.getCreneau(), cmj.getRv());
		}

		model.addAttribute("medecin", medecin);
		model.addAttribute("today", Calendar.getInstance());
		model.addAttribute("timeslotMap", appointmentMap);

		// list client for form in modal
		List<UserClient> clients = clientService.getAllClients();
		model.addAttribute("clients", clients);

		// model bean for form in modal
		if (!model.containsAttribute("addRvDto")) {
			PostAjouterRv addRvDto = new PostAjouterRv();
			model.addAttribute("addRvDto", addRvDto);
		}

		return ConstantsViewName.DOCTOR_VIEW;
	}

	/** mise en forme du libellé d'un créneau horaire */
	public String getTextForCreneau(Creneau creneau) {
		return getTextFor(creneau.getHdebut()) + "h" + getTextFor(creneau.getMdebut()) + ":"
				+ getTextFor(creneau.getHfin()) + "h" + getTextFor(creneau.getMfin());
	}

	/** met un 0 devant le chiffre si moins de deux chiffres */
	public String getTextFor(Integer number) {
		return number < 10 ? "0" + number : number.toString();
	}

	/**
	 * POST /admin/medecins/ajouterRv
	 */
	@RequestMapping(value = "/{idMedecin}/rv/add", method = RequestMethod.POST)
	public String ajouterRv(@PathVariable("idMedecin") Long idMedecin,
			@Valid @ModelAttribute("addRvDto") PostAjouterRv addRvDto, final RedirectAttributes redirectAttributes,
			Model model, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			redirectAttributes.addFlashAttribute("addRvDto", addRvDto);
			return "redirect:/admin/medecins/" + idMedecin;
		}

		// on récupère les valeurs postées
		String jour = addRvDto.getJour();
		long idCreneau = addRvDto.getIdCreneau();
		long idClient = addRvDto.getIdClient();
		System.out.println(jour + idCreneau + idClient);
		logger.debug(
				"REST Post addRvDto[(jour)({})-(idCreneau)({})-(idClient)({})])request to /admin/medecins/{}/rv/add",
				jour, idCreneau, idClient, idMedecin);

		// on vérifie la date
		Date jourAgenda = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			jourAgenda = sdf.parse(jour);
		} catch (ParseException e) {
			logger.debug("La date {} est invalide", jour);
		}
		// get doctor and his timeslots
		Creneau creneau = creneauRdvService.findTimeslotById(idCreneau);
		UserClient client = clientService.findOneClient(idClient);
		// Add Rv
		Rv rv = new Rv();
		rv.setClient(client);
		rv.setCreneau(creneau);
		rv.setJour(jourAgenda);
		Rv addedRv = creneauRdvService.createRv(rv);

		if (addedRv != null) {
			String message = "Rv " + rv.getId() + " was successfully added";
			redirectAttributes.addFlashAttribute("message", message);
			redirectAttributes.addFlashAttribute("registrationTask", "success");
		} else {
			redirectAttributes.addFlashAttribute("registrationTask", "unsuccess");
		}
		return "redirect:/admin/medecins/getAgendaMedecinJour/" + idMedecin + "/" + jour;

	}

	/**
	 * DELETE admin/medecin/:idMedecin/rv/delete/:idAppointment/:jour : delete
	 * the "id" location.
	 */
	@RequestMapping(value = "/{idMedecin}/rv/delete/{idAppointment}/{jour}")
	public String supprimerRv(@PathVariable("idMedecin") Long idMedecin,
			@PathVariable("idAppointment") Long idAppointment, @PathVariable("jour") String jour, Model model) {
		logger.debug("ADMIN request to delete Appointment : {}", idAppointment);

		Rv rv = creneauRdvService.findRvById(idAppointment);
		creneauRdvService.deleteRv(idAppointment);

		return "redirect:/admin/medecins/getAgendaMedecinJour/" + idMedecin + "/" + jour;
	}
}
