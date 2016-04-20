package rdvmedecins.web.jsf.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import rdvmedecins.entities.Client;
import rdvmedecins.entities.Medecin;
import rdvmedecins.metier.IMetier;

@Component
//@ApplicationScoped
public class ApplicationInit implements Serializable{


	private static final long serialVersionUID = 1L;

	// la couche [métier]
	@Autowired
	private IMetier metier;
  
  // cache
  private List<Medecin> medecins;
  private List<Client> clients;
  private Map<Long, Medecin> hMedecins = new HashMap<Long, Medecin>();
  private Map<Long, Client> hClients = new HashMap<Long, Client>();
  // erreurs
  private List<Erreur> erreurs = new ArrayList<>();
  private Boolean erreur = false;

  public ApplicationInit() {
  }

  @PostConstruct
  public void init() {
    try {
      // on met les médecins et les clients en cache
      medecins = metier.getAllMedecins();
      clients = metier.getAllClients();
    } catch (Throwable th) {
      // on note l'erreur
      erreur = true;
      erreurs.add(new Erreur(th.getClass().getName(), th.getMessage()));
      while (th.getCause() != null) {
        th = th.getCause();
        erreurs.add(new Erreur(th.getClass().getName(), th.getMessage()));
      }
      return;
    }
    // vérification des listes
    if (medecins.size() == 0) {
      // on note l'erreur
      erreur = true;
      erreurs.add(new Erreur("", "La liste des médecins est vide"));
    }
    if (clients.size() == 0) {
      // on note l'erreur
      erreur = true;
      erreurs.add(new Erreur("", "La liste des clients est vide"));
    }
    // erreur ?
    if (erreur) {
      return;
    }

    // les dictionnaires
    for (Medecin m : medecins) {
      hMedecins.put(m.getId(), m);
    }
    for (Client c : clients) {
      hClients.put(c.getId(), c);
    }
  }

  // getters et setters
  public List<Client> getClients() {
    return clients;
  }

  public List<Medecin> getMedecins() {
    return medecins;
  }

  public IMetier getMetier() {
    return metier;
  }

  public Map<Long, Medecin> gethMedecins() {
    return hMedecins;
  }

  public Map<Long, Client> gethClients() {
    return hClients;
  }

  public Boolean getErreur() {
    return erreur;
  }

  public List<Erreur> getErreurs() {
    return erreurs;
  }
}
