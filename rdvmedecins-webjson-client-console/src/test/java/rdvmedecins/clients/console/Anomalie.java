package rdvmedecins.clients.console;

import java.io.IOException;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import rdvmedecins.client.config.DaoConfig;
import rdvmedecins.client.dao.IDao;
import rdvmedecins.client.dao.RdvMedecinsException;
import rdvmedecins.client.entities.User;

public class Anomalie {

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

		// Authentification
		message = "/authenticate [admin,x]";
		try {
			dao.authenticate(new User("admin", "x"));
			System.out.println(String.format("%s : OK", message));
		} catch (RdvMedecinsException e) {
			showException(message, e);
		}

		// Authentification
		message = "/authenticate [user,user]";
		try {
			dao.authenticate(new User("user", "user"));
			System.out.println(String.format("%s : OK", message));
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
}
