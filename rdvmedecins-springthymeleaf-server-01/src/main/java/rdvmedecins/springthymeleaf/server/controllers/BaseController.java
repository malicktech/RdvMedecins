package rdvmedecins.springthymeleaf.server.controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring4.SpringTemplateEngine;

import rdvmedecins.client.dao.RdvMedecinsException;
import rdvmedecins.client.entities.AgendaMedecinJour;
import rdvmedecins.client.entities.Client;
import rdvmedecins.client.entities.Creneau;
import rdvmedecins.client.entities.CreneauMedecinJour;
import rdvmedecins.client.entities.Medecin;
import rdvmedecins.client.entities.Rv;
import rdvmedecins.springthymeleaf.server.models.ApplicationModel;
import rdvmedecins.springthymeleaf.server.models.ViewModelAgenda;
import rdvmedecins.springthymeleaf.server.models.ViewModelCreneau;
import rdvmedecins.springthymeleaf.server.requests.ActionContext;
import rdvmedecins.springthymeleaf.server.requests.PostGetAgenda;
import rdvmedecins.springthymeleaf.server.responses.Reponse;

public class BaseController {
	// ------------------------------------------ infos partagées
	@Autowired
	protected ApplicationModel application;
	@Autowired
	protected SpringTemplateEngine engine;

	// ------------------------------------------ méthodes partagées

	// flux [login]
	protected String getPartialViewLogin(WebContext thymeleafContext) {
		return engine.process("login", thymeleafContext);
	}

	// flux [accueil]
	protected String getPartialViewAccueil(WebContext thymeleafContext) {
		// modèle
		PostGetAgenda postAfficherAgenda = new PostGetAgenda();
		postAfficherAgenda.setJour(new Date());
		thymeleafContext.setVariable("rdvmedecins", application);
		thymeleafContext.setVariable("postChoixMedecinJour", postAfficherAgenda);
		// fusion de [accueil] avec son modèle
		return engine.process("accueil", thymeleafContext);
	}

	// flux [agenda]
	protected String getPartialViewAgenda(ActionContext actionContext, AgendaMedecinJour agenda, Locale locale) {
		// contextes
		WebContext thymeleafContext = actionContext.getThymeleafContext();
		WebApplicationContext springContext = actionContext.getSpringContext();
		// on construit le modèle de la page [agenda]
		ViewModelAgenda modelAgenda = setModelforAgenda(agenda, springContext, locale);
		// l'agenda avec son modèle
		thymeleafContext.setVariable("agenda", modelAgenda);
		// resa et son modèle
		thymeleafContext.setVariable("clientItems", application.getClientItems());
		return engine.process("agenda", thymeleafContext);
	}

	// modèle de la page [Agenda]
	private ViewModelAgenda setModelforAgenda(AgendaMedecinJour agenda, WebApplicationContext springContext, Locale locale) {
		// le titre de la page
		String dateFormat = springContext.getMessage("date.format", null, locale);
		Medecin médecin = agenda.getMedecin();
		String titre = springContext.getMessage("agenda.titre", new String[] { médecin.getTitre(), médecin.getPrenom(),
				médecin.getNom(), new SimpleDateFormat(dateFormat).format(agenda.getJour()) }, locale);
		// les créneaux de réservation
		ViewModelCreneau[] modelCréneaux = new ViewModelCreneau[agenda.getCreneauxMedecinJour().length];
		int i = 0;
		for (CreneauMedecinJour creneauMedecinJour : agenda.getCreneauxMedecinJour()) {
			// créneau du médecin
			Creneau créneau = creneauMedecinJour.getCreneau();
			ViewModelCreneau modelCréneau = new ViewModelCreneau();
			modelCréneaux[i] = modelCréneau;
			// id
			modelCréneau.setId(créneau.getId());
			// créneau horaire
			modelCréneau.setCreneauHoraire(String.format("%02dh%02d-%02dh%02d", créneau.getHdebut(), créneau.getMdebut(),
					créneau.getHfin(), créneau.getMfin()));
			Rv rv = creneauMedecinJour.getRv();
			// client et commande
			String commande;
			if (rv == null) {
				modelCréneau.setClient("");
				commande = springContext.getMessage("agenda.reserver", null, locale);
				modelCréneau.setCommande(commande);
				modelCréneau.setAction(ViewModelCreneau.ACTION_RESERVER);

			} else {
				Client client = rv.getClient();
				modelCréneau.setClient(String.format("%s %s %s", client.getTitre(), client.getPrenom(), client.getNom()));
				commande = springContext.getMessage("agenda.supprimer", null, locale);
				modelCréneau.setCommande(commande);
				modelCréneau.setIdRv(rv.getId());
				modelCréneau.setAction(ViewModelCreneau.ACTION_SUPPRIMER);
			}
			// créneau suivant
			i++;
		}
		// on rend le modèle de l'agenda
		ViewModelAgenda modelAgenda = new ViewModelAgenda();
		modelAgenda.setTitre(titre);
		modelAgenda.setCreneaux(modelCréneaux);
		return modelAgenda;
	}

	// vue [erreurs]
	protected Reponse getViewErreurs(WebContext thymeleafContext, List<String> erreurs) {
		Reponse reponse = new Reponse();
		reponse.setStatus(2);
		thymeleafContext.setVariable("erreurs", erreurs);
		reponse.setContent(engine.process("erreurs", thymeleafContext));
		return reponse;
	}

	// contexte d'une action
	protected ActionContext getActionContext(String lang, String origin, HttpServletRequest request,
			HttpServletResponse response, BindingResult result, RdvMedecinsCorsController rdvMedecinsCorsController) {
		// langue ?
		if (lang == null) {
			lang = "fr";
		}
		// locale
		Locale locale = null;
		if (lang.trim().toLowerCase().equals("fr")) {
			// français
			locale = new Locale("fr", "FR");
		} else {
			// tout le reste en anglais
			locale = new Locale("en", "US");
		}
		// entêtes CORS
		rdvMedecinsCorsController.sendOptions(origin, response);
		// ActionContext
		ActionContext actionContext = new ActionContext(new WebContext(request, response, request.getServletContext(),
				locale), WebApplicationContextUtils.getWebApplicationContext(request.getServletContext()), locale, null);
		// erreurs d'initialisation
		RdvMedecinsException e = application.getRdvMedecinsException();
		if (e != null) {
			actionContext.setErreurs(e.getMessages());
			return actionContext;
		}
		// erreurs de POST ?
		if (result != null && result.hasErrors()) {
			actionContext.setErreurs(getErreursForModel(result, locale, actionContext.getSpringContext()));
			return actionContext;
		}
		// pas d'erreurs
		return actionContext;
	}

	// liste des messages d'erreur liés à un modèle invalide
	protected List<String> getErreursForModel(BindingResult result, Locale locale, WebApplicationContext ctx) {
		StringBuffer buffer = new StringBuffer();
		for (FieldError error : result.getFieldErrors()) {
			StringBuffer bufferCodes = new StringBuffer("(");
			for (String code : error.getCodes()) {
				bufferCodes.append(String.format("%s ", code));
			}
			bufferCodes.append(")");
			buffer.append(String.format("[%s:%s:%s:%s]", error.getField(), error.getRejectedValue(), bufferCodes,
					ctx.getMessage(error.getCode(), null, locale)));
		}
		List<String> erreurs = new ArrayList<String>();
		erreurs.add(buffer.toString());
		return erreurs;
	}

	// liste des messages d'erreur d'une exception
	protected List<String> getErreursForException(Exception exception) {
		// on récupère la liste des messages d'erreur de l'exception
		Throwable cause = exception;
		List<String> erreurs = new ArrayList<String>();
		while (cause != null) {
			// on récupère le message seulement s'il est !=null et non blanc
			String message = cause.getMessage();
			if (message != null) {
				message = message.trim();
				if (message.length() != 0) {
					erreurs.add(message);
				}
			}
			// cause suivante
			cause = cause.getCause();
		}
		return erreurs;
	}

}
