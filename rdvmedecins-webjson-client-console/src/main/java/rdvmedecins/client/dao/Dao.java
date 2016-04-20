package rdvmedecins.client.dao;

import java.net.URI;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import rdvmedecins.client.entities.AgendaMedecinJour;
import rdvmedecins.client.entities.Client;
import rdvmedecins.client.entities.Creneau;
import rdvmedecins.client.entities.Medecin;
import rdvmedecins.client.entities.Rv;
import rdvmedecins.client.entities.User;
import rdvmedecins.client.requests.PostAjouterRv;
import rdvmedecins.client.requests.PostSupprimerRv;
import rdvmedecins.client.responses.Response;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class Dao implements IDao {

	// data
	@Autowired
	private RestTemplate restTemplate;
	private String urlServiceWebJson;
	// sérialiseur jSON
	static private ObjectMapper mapper = new ObjectMapper();

	// URL service web / jSON
	public void setUrlServiceWebJson(String url) {
		this.urlServiceWebJson = url;
	}

	private String getBase64(User user) {
		// on encode en base 64 l'utilisateur et son mot de passe - nécessite java 8
		String chaîne = String.format("%s:%s", user.getLogin(), user.getPasswd());
		return String.format("Basic %s", new String(Base64.getEncoder().encode(chaîne.getBytes())));
	}

	public void setTimeout(int timeout) {
		// on fixe le timeout des requêtes du client web
		HttpComponentsClientHttpRequestFactory factory = (HttpComponentsClientHttpRequestFactory) restTemplate
				.getRequestFactory();
		factory.setConnectTimeout(timeout);
		factory.setReadTimeout(timeout);
	}

	public List<Client> getAllClients(User user) {
		// la liste des clients List<LinkedHashMap>
		Object map = this.<List<Client>, Void> getResponse(user, "/getAllClients", HttpMethod.GET, 100, null);
		try {
			// la liste des clients List<Client>
			return mapper.readValue(mapper.writeValueAsString(map), new TypeReference<List<Client>>() {
			});
		} catch (Exception e) {
			throw new RdvMedecinsException(300, getMessagesForException(e));
		}
	}

	public List<Medecin> getAllMedecins(User user) {
		// la liste des médecins List<LinkedHashMap>
		Object map = this.<List<Medecin>, Void> getResponse(user, "/getAllMedecins", HttpMethod.GET, 101, null);
		try {
			// la liste des médecins List<Medecin>
			return mapper.readValue(mapper.writeValueAsString(map), new TypeReference<List<Medecin>>() {
			});
		} catch (Exception e) {
			throw new RdvMedecinsException(301, getMessagesForException(e));
		}
	}

	public List<Creneau> getAllCreneaux(User user, long idMedecin) {
		// la liste des créneaux List<LinkedHashMap>
		Object map = this.<List<Creneau>, Void> getResponse(user, String.format("%s/%s", "/getAllCreneaux", idMedecin),
				HttpMethod.GET, 102, (Void) null);
		try {
			// la liste des créneaux List<Creneau>
			return mapper.readValue(mapper.writeValueAsString(map), new TypeReference<List<Creneau>>() {
			});
		} catch (Exception e) {
			throw new RdvMedecinsException(302, getMessagesForException(e));
		}
	}

	public List<Rv> getRvMedecinJour(User user, long idMedecin, String jour) {
		// la liste des Rv List<LinkedHashMap>
		Object map = this.<List<Rv>, Void> getResponse(user,
				String.format("%s/%s/%s", "/getRvMedecinJour", idMedecin, jour), HttpMethod.GET, 103, (Void) null);
		try {
			// la liste des Rv List<Rv>
			return mapper.readValue(mapper.writeValueAsString(map), new TypeReference<List<Rv>>() {
			});
		} catch (Exception e) {
			throw new RdvMedecinsException(303, getMessagesForException(e));
		}
	}

	public Client getClientById(User user, long id) {
		// le client LinkHashedMap
		Object map = this.<Client, Void> getResponse(user, String.format("%s/%s", "/getClientById", id), HttpMethod.GET,
				104, (Void) null);
		try {
			// le client Client
			return mapper.readValue(mapper.writeValueAsString(map), new TypeReference<Client>() {
			});
		} catch (Exception e) {
			throw new RdvMedecinsException(304, getMessagesForException(e));
		}
	}

	public Medecin getMedecinById(User user, long id) {
		// le médecin LinkHashedMap
		Object map = this.<Medecin, Void> getResponse(user, String.format("%s/%s", "/getMedecinById", id), HttpMethod.GET,
				105, (Void) null);
		try {
			// le médecin Medecin
			return mapper.readValue(mapper.writeValueAsString(map), new TypeReference<Medecin>() {
			});
		} catch (Exception e) {
			throw new RdvMedecinsException(305, getMessagesForException(e));
		}
	}

	public Rv getRvById(User user, long id) {
		// le Rv LinkHashedMap
		Object map = this.<Rv, Void> getResponse(user, String.format("%s/%s", "/getRvById", id), HttpMethod.GET, 106,
				(Void) null);
		try {
			// le Rv Rv
			return mapper.readValue(mapper.writeValueAsString(map), new TypeReference<Rv>() {
			});
		} catch (Exception e) {
			throw new RdvMedecinsException(306, getMessagesForException(e));
		}
	}

	public Creneau getCreneauById(User user, long id) {
		// le créneau LinkHashedMap
		Object map = this.<Creneau, Void> getResponse(user, String.format("%s/%s", "/getCreneauById", id), HttpMethod.GET,
				107, (Void) null);
		try {
			// le créneau Creneau
			return mapper.readValue(mapper.writeValueAsString(map), new TypeReference<Creneau>() {
			});
		} catch (Exception e) {
			throw new RdvMedecinsException(307, getMessagesForException(e));
		}
	}

	public Rv ajouterRv(User user, String jour, long idCreneau, long idClient) {
		// le Rv LinkHashedMap
		Object map = this.<Rv, PostAjouterRv> getResponse(user, "/ajouterRv", HttpMethod.POST, 108, new PostAjouterRv(
				idClient, idCreneau, jour));
		try {
			// le Rv Rv
			return mapper.readValue(mapper.writeValueAsString(map), new TypeReference<Rv>() {
			});
		} catch (Exception e) {
			throw new RdvMedecinsException(308, getMessagesForException(e));
		}
	}

	public void supprimerRv(User user, long idRv) {
		this.<Void, PostSupprimerRv> getResponse(user, "/supprimerRv", HttpMethod.POST, 109, new PostSupprimerRv(idRv));
	}

	public AgendaMedecinJour getAgendaMedecinJour(User user, long idMedecin, String jour) {
		// l'agenda LinkHashedMap
		Object map = this.<AgendaMedecinJour, Void> getResponse(user,
				String.format("%s/%s/%s", "/getAgendaMedecinJour", idMedecin, jour), HttpMethod.GET, 110, (Void) null);
		try {
			// l'agenda AgendaMedecinJour
			return mapper.readValue(mapper.writeValueAsString(map), new TypeReference<AgendaMedecinJour>() {
			});
		} catch (Exception e) {
			throw new RdvMedecinsException(310, getMessagesForException(e));
		}
	}

	// vérification [user,mdp]
	public void authenticate(User user) {
		this.<Void, Void> getResponse(user, "/authenticate", HttpMethod.GET, 111, (Void) null);
	}

	// requête générique
	private <T1, T2> T1 getResponse(User user, String url, HttpMethod method, int errStatus, T2 body) {
		// la réponse du serveur
		ResponseEntity<Response<T1>> response;
		try {
			// on prépare la requête
			RequestEntity<?> request = null;
			if (method == HttpMethod.GET) {
				request = RequestEntity.get(new URI(String.format("%s%s", urlServiceWebJson, url)))
						.header("Authorization", getBase64(user)).accept(MediaType.APPLICATION_JSON).build();
			}
			if (method == HttpMethod.POST) {
				request = RequestEntity.post(new URI(String.format("%s%s", urlServiceWebJson, url)))
						.header("Authorization", getBase64(user)).header("Content-Type", "application/json")
						.accept(MediaType.APPLICATION_JSON).body(body);
			}
			// on exécute la requête
			response = restTemplate.exchange(request, new ParameterizedTypeReference<Response<T1>>() {
			});
		} catch (Exception e) {
			// on encapsule l'exception
			throw new RdvMedecinsException(errStatus, getMessagesForException(e));
		}
		// on récupère le corps de la réponse
		Response<T1> entity = response.getBody();
		int status = entity.getStatus();
		// des erreurs côté serveur ?
		if (status != 0) {
			// on crée une exception
			throw new RdvMedecinsException(status, entity.getMessages());
		} else {
			// c'est bon
			return entity.getBody();
		}
	}

	// liste des messages d'erreur d'une exception
	private static List<String> getMessagesForException(Exception exception) {
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
