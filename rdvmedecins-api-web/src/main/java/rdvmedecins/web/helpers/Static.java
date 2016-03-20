package rdvmedecins.web.helpers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rdvmedecins.domain.AgendaMedecinJour;
import rdvmedecins.domain.CreneauMedecinJour;
import rdvmedecins.entities.Creneau;
import rdvmedecins.entities.Rv;

/**
 * regroupe l'ensemble des méthodes statiques utilitaires qui n'ont pas d'aspect " métier " ou " web "
 * Utilitaire de gestion des erreurs
 * Utilitaires de Mapage d'objets pour mieux sérialiser les réponses 
 *  
 * @author Malick
 *
 */
public class Static {

	public Static() {
	}

	// liste des messages d'erreur d'une exception
	public static List<String> getErreursForException(Exception exception) {
		// on récupère la liste des messages d'erreur de l'exception
		Throwable cause = exception;
		List<String> erreurs = new ArrayList<String>();
		while (cause != null) {
			erreurs.add(cause.getMessage());
			cause = cause.getCause();
		}
		return erreurs;
	}

	// mappers Object --> Map
	// --------------------------------------------------------

	// AgendaMedecinJour --> Map
	public static Map<String, Object> getMapForAgendaMedecinJour(AgendaMedecinJour agenda) {
		// qq chose à faire ?
		if (agenda == null) {
			return null;
		}
		// dictionnaire <String,Object>
		Map<String, Object> hash = new HashMap<String, Object>();
		hash.put("medecin", agenda.getMedecin());
		hash.put("jour", new SimpleDateFormat("yyyy-MM-dd").format(agenda.getJour()));
		List<Map<String, Object>> créneaux = new ArrayList<Map<String, Object>>();
		for (CreneauMedecinJour créneau : agenda.getCreneauxMedecinJour()) {
			créneaux.add(getMapForCreneauMedecinJour(créneau));
		}
		hash.put("creneauxMedecin", créneaux);
		// on rend le dictionnaire
		return hash;
	}

	// Creneau --> Map
	public static Map<String, Object> getMapForCreneau(Creneau créneau) {
		// qq chose à faire ?
		if (créneau == null) {
			return null;
		}
		// dictionnaire <String,Object>
		Map<String, Object> hash = new HashMap<String, Object>();
		hash.put("id", créneau.getId());
		hash.put("hDebut", créneau.getHdebut());
		hash.put("mDebut", créneau.getMdebut());
		hash.put("hFin", créneau.getHfin());
		hash.put("mFin", créneau.getMfin());
		// on rend le dictionnaire
		return hash;
	}

	// Rv --> Map
	public static Map<String, Object> getMapForRv(Rv rv) {
		// qq chose à faire ?
		if (rv == null) {
			return null;
		}
		// dictionnaire <String,Object>
		Map<String, Object> hash = new HashMap<String, Object>();
		hash.put("id", rv.getId());
		hash.put("client", rv.getClient());
		hash.put("creneau", getMapForCreneau(rv.getCreneau()));
		// on rend le dictionnaire
		return hash;
	}

	// Rv --> Map
	public static Map<String, Object> getMapForRv2(Rv rv) {
		// qq chose à faire ?
		if (rv == null) {
			return null;
		}
		// dictionnaire <String,Object>
		Map<String, Object> hash = new HashMap<String, Object>();
		hash.put("id", rv.getId());
		hash.put("idClient", rv.getIdClient());
		hash.put("idCreneau", rv.getIdCreneau());
		// on rend le dictionnaire
		return hash;
	}

	// CreneauMedecinJour --> map
	public static Map<String, Object> getMapForCreneauMedecinJour(CreneauMedecinJour créneau) {
		// qq chose à faire ?
		if (créneau == null) {
			return null;
		}
		// dictionnaire <String,Object>
		Map<String, Object> hash = new HashMap<String, Object>();
		hash.put("creneau", getMapForCreneau(créneau.getCreneau()));
		hash.put("rv", getMapForRv(créneau.getRv()));
		// on rend le dictionnaire
		return hash;
	}

	// List<Creneau> --> List<Map>
	public static List<Map<String, Object>> getListMapForCreneaux(List<Creneau> créneaux) {
		// liste de dictionnaires <String,Object>
		List<Map<String, Object>> liste = new ArrayList<Map<String, Object>>();
		for (Creneau créneau : créneaux) {
			liste.add(Static.getMapForCreneau(créneau));
		}
		// on rend la liste
		return liste;
	}

	// List<Rv> --> List<Map>
	public static List<Map<String, Object>> getListMapForRvs(List<Rv> rvs) {
		// liste de dictionnaires <String,Object>
		List<Map<String, Object>> liste = new ArrayList<Map<String, Object>>();
		for (Rv rv : rvs) {
			liste.add(Static.getMapForRv(rv));
		}
		// on rend la liste
		return liste;
	}

}
