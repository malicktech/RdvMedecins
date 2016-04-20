package rdvmedecins.clients.console;

import java.io.IOException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import rdvmedecins.client.config.DaoConfig;
import rdvmedecins.client.dao.IDao;
import rdvmedecins.client.dao.RdvMedecinsException;
import rdvmedecins.client.entities.Rv;
import rdvmedecins.client.entities.User;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {

	// sérialiseur jSON
	static private ObjectMapper mapper = new ObjectMapper();
	// timeout des connexions en millisecondes
	static private int TIMEOUT = 1000;

	public static void main(String[] args) throws IOException {
		// on récupère une référence sur la couche [DAO]
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoConfig.class);
		IDao dao = context.getBean(IDao.class);
		// on fixe l'URL du service web / json
		dao.setUrlServiceWebJson("http://localhost:8080");
		// on fixe les timeout en millisecondes
		dao.setTimeout(TIMEOUT);

		// Authentification
		String message = "/authenticate [admin,admin]";
		try {
			dao.authenticate(new User("admin", "admin"));
			System.out.println(String.format("%s : OK", message));
		} catch (RdvMedecinsException e) {
			showException(message, e);
		}

		message = "/authenticate [user,user]";
		try {
			dao.authenticate(new User("user", "user"));
			System.out.println(String.format("%s : OK", message));
		} catch (RdvMedecinsException e) {
			showException(message, e);
		}

		message = "/authenticate [user,x]";
		try {
			dao.authenticate(new User("user", "x"));
			System.out.println(String.format("%s : OK", message));
		} catch (RdvMedecinsException e) {
			showException(message, e);
		}

		message = "/authenticate [x,x]";
		try {
			dao.authenticate(new User("x", "x"));
			System.out.println(String.format("%s : OK", message));
		} catch (RdvMedecinsException e) {
			showException(message, e);
		}

		message = "/authenticate [admin,x]";
		try {
			dao.authenticate(new User("admin", "x"));
			System.out.println(String.format("URL %s : OK", message));
		} catch (RdvMedecinsException e) {
			showException(message, e);
		}

		// liste des clients
		message = "/getAllClients";
		try {
			showResponse(message, dao.getAllClients(new User("admin", "admin")));
		} catch (RdvMedecinsException e) {
			showException(message, e);
		}

		// liste des médecins
		message = "/getAllMedecins";
		try {
			showResponse(message, dao.getAllMedecins(new User("admin", "admin")));
		} catch (RdvMedecinsException e) {
			showException(message, e);
		}

		// liste des créneaux du médecin 2
		message = "/getAllCreneaux/2";
		try {
			showResponse(message, dao.getAllCreneaux(new User("admin", "admin"), 2L));
		} catch (RdvMedecinsException e) {
			showException(message, e);
		}

		// client n° 1
		message = "/getClientById/1";
		try {
			showResponse(message, dao.getClientById(new User("admin", "admin"), 1L));
		} catch (RdvMedecinsException e) {
			showException(message, e);
		}

		// médecin n° 2
		message = "/getMedecinById/2";
		try {
			showResponse(message, dao.getMedecinById(new User("admin", "admin"), 2L));
		} catch (RdvMedecinsException e) {
			showException(message, e);
		}

		// créneau n° 3
		message = "/getCreneauById/3";
		try {
			showResponse(message, dao.getCreneauById(new User("admin", "admin"), 3L));
		} catch (RdvMedecinsException e) {
			showException(message, e);
		}

		// rv n° 4
		message = "/getRvById/4";
		try {
			showResponse(message, dao.getRvById(new User("admin", "admin"), 4L));
		} catch (RdvMedecinsException e) {
			showException(message, e);
		}

		// ajout d'un rv
		message = "/ajouterRv [idClient=5,idCreneau=8,jour=2015-01-08]";
		long idRv = 0;
		try {
			Rv response = dao.ajouterRv(new User("admin", "admin"), "2015-01-08", 8L, 3L);
			idRv = response.getId();
			showResponse(message, response);
		} catch (RdvMedecinsException e) {
			showException(message, e);
		}

		// liste des rv du médecin 1 le 2015-01-08
		message = "/getRvMedecinJour/1/2015-01-08";
		try {
			showResponse(message, dao.getRvMedecinJour(new User("admin", "admin"), 1L, "2015-01-08"));
		} catch (RdvMedecinsException e) {
			showException(message, e);
		}

		// agenda du médecin 1 le 2015-01-08
		message = "/getAgendaMedecinJour/1/2015-01-08";
		try {
			showResponse(message, dao.getAgendaMedecinJour(new User("admin", "admin"), 1L, "2015-01-08"));
		} catch (RdvMedecinsException e) {
			showException(message, e);
		}
		// suppression du rv ajouté
		message = String.format("/supprimerRv [idRv=%s]", idRv);
		try {
			dao.supprimerRv(new User("admin", "admin"), idRv);
		} catch (RdvMedecinsException e) {
			showException(message, e);
		}

		// liste des rv du médecin 1 le 2015-01-08
		message = "/getRvMedecinJour/1/2015-01-08";
		try {
			showResponse(message, dao.getRvMedecinJour(new User("admin", "admin"), 1L, "2015-01-08"));
		} catch (RdvMedecinsException e) {
			showException(message, e);
		}
		// fermeture contexte
		context.close();
	}

	private static void showException(String message, RdvMedecinsException e) {
		System.out.println(String.format("URL [%s]", message));
		System.out.println(String.format("L'erreur n° [%s] s'est produite :", e.getStatus()));
		for (String msg : e.getMessages()) {
			System.out.println(msg);
		}
	}

	private static <T> void showResponse(String message, T response) throws JsonProcessingException {
		System.out.println(String.format("URL [%s]", message));
		System.out.println(mapper.writeValueAsString(response));
	}
}
