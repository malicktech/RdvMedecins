package rdvmedecins.springthymeleaf.server.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.thymeleaf.context.WebContext;

import rdvmedecins.client.dao.RdvMedecinsException;
import rdvmedecins.client.entities.AgendaMedecinJour;
import rdvmedecins.client.entities.Creneau;
import rdvmedecins.client.entities.Rv;
import rdvmedecins.client.entities.User;
import rdvmedecins.springthymeleaf.server.requests.ActionContext;
import rdvmedecins.springthymeleaf.server.requests.PostGetAgenda;
import rdvmedecins.springthymeleaf.server.requests.PostLang;
import rdvmedecins.springthymeleaf.server.requests.PostSupprimerRv;
import rdvmedecins.springthymeleaf.server.requests.PostUser;
import rdvmedecins.springthymeleaf.server.requests.PostValiderRv;
import rdvmedecins.springthymeleaf.server.responses.Reponse;
import rdvmedecins.web.validators.PostGetAgendaValidator;

@Controller
public class RdvMedecinsController extends BaseController {

	@Autowired
	private RdvMedecinsCorsController rdvMedecinsCorsController;

	// affichage de l'agenda d'un médecin pour un jour donné
	@RequestMapping(value = "/getAgenda", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8")
	@ResponseBody
	public Reponse getAgenda(@RequestBody @Valid PostGetAgenda postGetAgenda, BindingResult result,
			HttpServletRequest request, HttpServletResponse response,
			@RequestHeader(value = "Origin", required = false) String origin) {
		// contextes de l'action
		ActionContext actionContext = getActionContext(postGetAgenda.getLang(), origin, request, response, result,
				rdvMedecinsCorsController);
		WebContext thymeleafContext = actionContext.getThymeleafContext();
		WebApplicationContext springContext = actionContext.getSpringContext();
		Locale locale = actionContext.getLocale();
		// erreurs ?
		List<String> erreurs = actionContext.getErreurs();
		if (erreurs != null) {
			return getViewErreurs(thymeleafContext, erreurs);
		}
		// on vérifie la validité du post
		if (result != null) {
			new PostGetAgendaValidator().validate(postGetAgenda, result);
			if (result.hasErrors()) {
				// on retourne la vue [erreurs]
				return getViewErreurs(thymeleafContext, getErreursForModel(result, locale, springContext));
			}
		}
		// action
		try {
			// agenda du médecin
			AgendaMedecinJour agenda = application.getAgendaMedecinJour(postGetAgenda.getUser(),
					postGetAgenda.getIdMedecin(), new SimpleDateFormat("yyyy-MM-dd").format(postGetAgenda.getJour()));
			// réponse
			Reponse reponse = new Reponse();
			reponse.setStatus(1);
			reponse.setAgenda(getPartialViewAgenda(actionContext, agenda, locale));
			return reponse;
		} catch (RdvMedecinsException e1) {
			// on retourne la vue [erreurs]
			return getViewErreurs(thymeleafContext, e1.getMessages());
		} catch (Exception e2) {
			// on retourne la vue [erreurs]
			return getViewErreurs(thymeleafContext, getErreursForException(e2));
		}
	}

	// navbar+ jumbotron + accueil
	@RequestMapping(value = "/getNavbarRunJumbotronAccueil", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8")
	@ResponseBody
	public Reponse getNavbarRunJumbotronAccueil(@Valid @RequestBody PostUser postUser, BindingResult result,
			HttpServletRequest request, HttpServletResponse response,
			@RequestHeader(value = "Origin", required = false) String origin) {
		// contextes de l'action
		ActionContext actionContext = getActionContext(postUser.getLang(), origin, request, response, result,
				rdvMedecinsCorsController);
		WebContext thymeleafContext = actionContext.getThymeleafContext();
		// erreurs ?
		List<String> erreurs = actionContext.getErreurs();
		if (erreurs != null) {
			return getViewErreurs(thymeleafContext, erreurs);
		}
		// la vue [accueil] est protégée
		try {
			// utilisateur
			User user = postUser.getUser();
			// on vérifie les identifiants [userName, password]
			application.authenticate(user);
		} catch (RdvMedecinsException e) {
			// on renvoie une erreur
			return getViewErreurs(thymeleafContext, e.getMessages());
		}
		// on envoie la réponse
		Reponse reponse = new Reponse();
		reponse.setStatus(1);
		reponse.setNavbar(engine.process("navbar-run", thymeleafContext));
		reponse.setJumbotron(engine.process("jumbotron", thymeleafContext));
		reponse.setContent(getPartialViewAccueil(thymeleafContext));
		return reponse;
	}

	// nabar + jumbotron + accueil + agenda
	@RequestMapping(value = "/getNavbarRunJumbotronAccueilAgenda", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8")
	@ResponseBody
	public Reponse getNavbarRunJumbotronAccueilAgenda(@Valid @RequestBody PostGetAgenda post, BindingResult result,
			HttpServletRequest request, HttpServletResponse response,
			@RequestHeader(value = "Origin", required = false) String origin) {
		// contextes de l'action
		ActionContext actionContext = getActionContext(post.getLang(), origin, request, response, result,
				rdvMedecinsCorsController);
		WebContext thymeleafContext = actionContext.getThymeleafContext();
		// erreurs ?
		List<String> erreurs = actionContext.getErreurs();
		if (erreurs != null) {
			return getViewErreurs(thymeleafContext, erreurs);
		}
		// agenda
		Reponse agenda = getAgenda(post, result, request, response, null);
		if (agenda.getStatus() != 1) {
			return agenda;
		}
		// on envoie la réponse
		Reponse reponse = new Reponse();
		reponse.setStatus(1);
		reponse.setNavbar(engine.process("navbar-run", thymeleafContext));
		reponse.setJumbotron(engine.process("jumbotron", thymeleafContext));
		reponse.setContent(getPartialViewAccueil(thymeleafContext));
		reponse.setAgenda(agenda.getAgenda());
		return reponse;
	}

	// page de login
	@RequestMapping(value = "/getLogin", method = RequestMethod.POST)
	@ResponseBody
	public Reponse getLogin(@Valid @RequestBody PostLang postLang, BindingResult result, HttpServletRequest request,
			HttpServletResponse response, @RequestHeader(value = "Origin", required = false) String origin) {
		// contextes de l'action
		ActionContext actionContext = getActionContext(postLang.getLang(), origin, request, response, result,
				rdvMedecinsCorsController);
		WebContext thymeleafContext = actionContext.getThymeleafContext();
		// erreurs ?
		List<String> erreurs = actionContext.getErreurs();
		if (erreurs != null) {
			return getViewErreurs(thymeleafContext, erreurs);
		}
		// on renvoie la vue [login]
		Reponse reponse = new Reponse();
		reponse.setStatus(1);
		reponse.setJumbotron(engine.process("jumbotron", thymeleafContext));
		reponse.setNavbar(engine.process("navbar-start", thymeleafContext));
		reponse.setContent(getPartialViewLogin(thymeleafContext));
		return reponse;
	}

	// jumbotron
	@RequestMapping(value = "/getJumbotron", method = RequestMethod.POST)
	@ResponseBody
	public Reponse getJumbotron(@Valid @RequestBody PostLang postLang, BindingResult result, HttpServletRequest request,
			HttpServletResponse response, @RequestHeader(value = "Origin", required = false) String origin) {
		// contextes de l'action
		ActionContext actionContext = getActionContext(postLang.getLang(), origin, request, response, result,
				rdvMedecinsCorsController);
		WebContext thymeleafContext = actionContext.getThymeleafContext();
		// erreurs ?
		List<String> erreurs = actionContext.getErreurs();
		if (erreurs != null) {
			return getViewErreurs(thymeleafContext, erreurs);
		}
		// on renvoie la vue [jumbotron]
		Reponse reponse = new Reponse();
		reponse.setStatus(1);
		reponse.setJumbotron(engine.process("jumbotron", thymeleafContext));
		return reponse;
	}

	// accueil
	@RequestMapping(value = "/getAccueil", method = RequestMethod.POST)
	@ResponseBody
	public Reponse getAccueil(@Valid @RequestBody PostUser postUser, BindingResult result, HttpServletRequest request,
			HttpServletResponse response, @RequestHeader(value = "Origin", required = false) String origin) {
		// contextes de l'action
		ActionContext actionContext = getActionContext(postUser.getLang(), origin, request, response, result,
				rdvMedecinsCorsController);
		WebContext thymeleafContext = actionContext.getThymeleafContext();
		// erreurs ?
		List<String> erreurs = actionContext.getErreurs();
		if (erreurs != null) {
			return getViewErreurs(thymeleafContext, erreurs);
		}
		// la vue [accueil] est protégée
		try {
			// utilisateur
			User user = postUser.getUser();
			// on vérifie les identifiants [userName, password]
			application.authenticate(user);
		} catch (RdvMedecinsException e) {
			// on renvoie une erreur
			return getViewErreurs(thymeleafContext, e.getMessages());
		}
		// on renvoie la vue [accueil]
		Reponse reponse = new Reponse();
		reponse.setStatus(1);
		reponse.setContent(getPartialViewAccueil(thymeleafContext));
		return reponse;
	}

	// navbar-start
	@RequestMapping(value = "/getNavbarStart", method = RequestMethod.POST)
	@ResponseBody
	public Reponse getNavbarStart(@Valid @RequestBody PostLang postLang, BindingResult result,
			HttpServletRequest request, HttpServletResponse response,
			@RequestHeader(value = "Origin", required = false) String origin) {
		// contextes de l'action
		ActionContext actionContext = getActionContext(postLang.getLang(), origin, request, response, result,
				rdvMedecinsCorsController);
		WebContext thymeleafContext = actionContext.getThymeleafContext();
		// erreurs ?
		List<String> erreurs = actionContext.getErreurs();
		if (erreurs != null) {
			return getViewErreurs(thymeleafContext, erreurs);
		}
		// on renvoie la vue [navbar-start]
		Reponse reponse = new Reponse();
		reponse.setStatus(1);
		reponse.setNavbar(engine.process("navbar-start", thymeleafContext));
		return reponse;
	}

	// navbar-run
	@RequestMapping(value = "/getNavbarRun", method = RequestMethod.POST)
	@ResponseBody
	public Reponse getNavbarRun(@Valid @RequestBody PostLang postLang, BindingResult result, HttpServletRequest request,
			HttpServletResponse response, @RequestHeader(value = "Origin", required = false) String origin) {
		// contextes de l'action
		ActionContext actionContext = getActionContext(postLang.getLang(), origin, request, response, result,
				rdvMedecinsCorsController);
		WebContext thymeleafContext = actionContext.getThymeleafContext();
		// erreurs ?
		List<String> erreurs = actionContext.getErreurs();
		if (erreurs != null) {
			return getViewErreurs(thymeleafContext, erreurs);
		}
		// on renvoie la vue [navbar-run]
		Reponse reponse = new Reponse();
		reponse.setStatus(1);
		reponse.setNavbar(engine.process("navbar-run", thymeleafContext));
		return reponse;
	}

	// suppression d'un rendez-vous
	@RequestMapping(value = "/supprimerRv", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8")
	@ResponseBody
	public Reponse supprimerRv(@Valid @RequestBody PostSupprimerRv postSupprimerRv, BindingResult result,
			HttpServletRequest request, HttpServletResponse response,
			@RequestHeader(value = "Origin", required = false) String origin) {
		// contextes de l'action
		ActionContext actionContext = getActionContext(postSupprimerRv.getLang(), origin, request, response, result,
				rdvMedecinsCorsController);
		WebContext thymeleafContext = actionContext.getThymeleafContext();
		Locale locale = actionContext.getLocale();
		// erreurs ?
		List<String> erreurs = actionContext.getErreurs();
		if (erreurs != null) {
			return getViewErreurs(thymeleafContext, erreurs);
		}
		// valeurs postées
		User user = postSupprimerRv.getUser();
		long idRv = postSupprimerRv.getIdRv();
		// on supprime le Rdv
		AgendaMedecinJour agenda = null;
		try {
			// on le récupère
			Rv rv = application.getRvById(user, idRv);
			Creneau creneau = application.getCreneauById(user, rv.getIdCreneau());
			long idMedecin = creneau.getIdMedecin();
			Date jour = rv.getJour();
			// on supprime le rv associé
			application.supprimerRv(user, idRv);
			// on régénère l'agenda du médecin
			agenda = application.getAgendaMedecinJour(user, idMedecin, new SimpleDateFormat("yyyy-MM-dd").format(jour));
			// on rend le nouvel agenda
			Reponse reponse = new Reponse();
			reponse.setStatus(1);
			reponse.setAgenda(getPartialViewAgenda(actionContext, agenda, locale));
			return reponse;
		} catch (RdvMedecinsException ex) {
			// on retourne la vue [erreurs]
			return getViewErreurs(thymeleafContext, ex.getMessages());
		} catch (Exception e2) {
			// on retourne la vue [erreurs]
			return getViewErreurs(thymeleafContext, getErreursForException(e2));
		}
	}

	// validation d'un rendez-vous
	@RequestMapping(value = "/validerRv", method = RequestMethod.POST, consumes = "application/json; charset=UTF-8")
	@ResponseBody
	public Reponse validerRv(@RequestBody PostValiderRv postValiderRv, BindingResult result, HttpServletRequest request,
			HttpServletResponse response, @RequestHeader(value = "Origin", required = false) String origin) {
		// contextes de l'action
		ActionContext actionContext = getActionContext(postValiderRv.getLang(), origin, request, response, result,
				rdvMedecinsCorsController);
		WebApplicationContext springContext = actionContext.getSpringContext();
		WebContext thymeleafContext = actionContext.getThymeleafContext();
		Locale locale = actionContext.getLocale();
		// erreurs ?
		List<String> erreurs = actionContext.getErreurs();
		if (erreurs != null) {
			return getViewErreurs(thymeleafContext, erreurs);
		}
		// on vérifie la validité du post
		if (result != null) {
			new PostGetAgendaValidator().validate(postValiderRv, result);
			if (result.hasErrors()) {
				// on retourne la vue [erreurs]
				return getViewErreurs(thymeleafContext, getErreursForModel(result, locale, springContext));
			}
		}
		// valeurs postées
		User user = postValiderRv.getUser();
		long idClient = postValiderRv.getIdClient();
		long idCreneau = postValiderRv.getIdCreneau();
		Date jour = postValiderRv.getJour();
		// action
		try {
			// on récupère des infos sur le créneau
			Creneau créneau = application.getCreneauById(user, idCreneau);
			long idMedecin = créneau.getIdMedecin();
			// on ajoute le Rv
			application.ajouterRv(postValiderRv.getUser(), new SimpleDateFormat("yyyy-MM-dd").format(jour), idCreneau,
					idClient);
			// on régénère l'agenda
			AgendaMedecinJour agenda = application.getAgendaMedecinJour(user, idMedecin,
					new SimpleDateFormat("yyyy-MM-dd").format(jour));
			// on rend le nouvel agenda
			Reponse reponse = new Reponse();
			reponse.setStatus(1);
			reponse.setAgenda(getPartialViewAgenda(actionContext, agenda, locale));
			return reponse;
		} catch (RdvMedecinsException ex) {
			// on retourne la vue [erreurs]
			return getViewErreurs(thymeleafContext, ex.getMessages());
		} catch (Exception e2) {
			// on retourne la vue [erreurs]
			return getViewErreurs(thymeleafContext, getErreursForException(e2));
		}
	}
}