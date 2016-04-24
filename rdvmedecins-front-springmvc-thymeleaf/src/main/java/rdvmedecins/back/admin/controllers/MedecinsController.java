package rdvmedecins.back.admin.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import rdvmedecins.back.admin.models.ApplicationModel;
import rdvmedecins.entities.Medecin;

@Controller
@RequestMapping(value = "/dashbord")
public class MedecinsController {
	
    private final Logger logger = LoggerFactory.getLogger(MedecinsController.class);

    
	@Autowired
	private ApplicationModel application;

	private static final String INDEX_VIEW_NAME = "index";
	private static final String DASHBORD_HOME_VIEW_NAME = "index-dashbord";
	
	/**
	 * MEDECINS
	 * =========================================================================
	 * 
	 */

	/**
	 * Liste de tous les médecins du cabinet médical
	 */
	@RequestMapping(value = {"/", "/index", "/home"}, method = RequestMethod.GET)
	public String getAllMedecins(Model model) {
		logger.info("IN: dashbord/index , GET");
		
		List<Medecin> medecins = application.getAllMedecins();
		model.addAttribute("medecins", medecins );
		
		if (!model.containsAttribute("strategy")) {
            logger.info("Adding Strategy object to model");
            Medecin medecin = new Medecin();
            model.addAttribute("medecin", medecin);
        }
		
		return DASHBORD_HOME_VIEW_NAME;
	}

}
