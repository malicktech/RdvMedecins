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
		// set Timeslot and appointment in Map list
		Map<Creneau, Rv> appointmentMap = new HashMap<>();
		CreneauMedecinJour[] cMJs = agenda.getCreneauxMedecinJour();
		for (CreneauMedecinJour cmj : cMJs) {
			appointmentMap.put(cmj.getCreneau(), cmj.getRv());
		}
		model.addAttribute("medecin", medecin);
		model.addAttribute("today", Calendar.getInstance());
		model.addAttribute("timeslotMap", appointmentMap);

		// list client for the form in modal
		List<UserClient> clients = clientService.getAllClients();
		model.addAttribute("clients", clients);

		// model bean for the form in modal
		if (!model.containsAttribute("addRvDto")) {
			PostAjouterRv addRvDto = new PostAjouterRv();
			model.addAttribute("addRvDto", addRvDto);
		}

		return ConstantsViewName.DOCTOR_VIEW;
	}

	/**
	 * POST /admin/medecins/ajouterRv
	 */
	@RequestMapping(value = "/{idMedecin}/rv/add", method = RequestMethod.POST)
	public String ajouterRv(@PathVariable("idMedecin") Long idMedecin,
			@Valid @ModelAttribute("addRvDto") PostAjouterRv addRvDto, final RedirectAttributes redirectAttributes,
			Model model) {

		// get posted values
		String jour = addRvDto.getJour();
		long idCreneau = addRvDto.getIdCreneau();
		long idClient = addRvDto.getIdClient();
		
		logger.debug(
				"Admin Post request | /admin/medecins/{}/rv/add | addRvDto[(jour)({})-(idCreneau)({})-(idClient)({})])",
				jour, idCreneau, idClient, idMedecin);

		// check date formaat
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

		creneauRdvService.deleteRv(idAppointment);

		return "redirect:/admin/medecins/getAgendaMedecinJour/" + idMedecin + "/" + jour;
	}
}
