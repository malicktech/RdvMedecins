package rdvmedecins.web.jsf.beans;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rdvmedecins.domain.AgendaMedecinJour;
import rdvmedecins.domain.CreneauMedecinJour;
import rdvmedecins.entities.Client;
import rdvmedecins.entities.Creneau;
import rdvmedecins.entities.Medecin;
import rdvmedecins.web.jsf.utils.Messages;


@Component
@ManagedBean(name="form")
@SessionScoped //@RequestScoped - @ApplicationScoped
public class Form implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	// inject application by spring 
  @Autowired
	private ApplicationInit application;
  
  public Form() {
  }
  
  // modèle
  private Long idMedecin;
  private Date jour = new Date();
  private Boolean form1Rendered = true;
  private Boolean form2Rendered = false;
  private Boolean form3Rendered = false;
  private Boolean erreurRendered = false;
  private String form2Titre;
  private String form3Titre;
  private AgendaMedecinJour agendaMedecinJour;
  private Long idCreneau;
  private Medecin medecin;
  private Client client;
  private Long idClient;
  private CreneauMedecinJour creneauChoisi;
  private List<Erreur> erreurs;

  @PostConstruct
  private void init() {
    // l'initialisation s'est-elle bien passée ?
    if (application.getErreur()) {
      // on récupère la liste des erreurs
      erreurs = application.getErreurs();
      // la vue des erreurs est affichée
      setForms(false, false, false, true);
    }
  }

  // liste des médecins
  public List<Medecin> getMedecins() {
    return application.getMedecins();
  }

  // liste des clients
  public List<Client> getClients() {
    return application.getClients();
  }

  // agenda
  public void getAgenda() {
    try {
      // on récupère le médecin
      medecin = application.gethMedecins().get(idMedecin);
      // titre formulaire 2
      form2Titre = Messages.getMessage(null, "form2.titre", new Object[]{medecin.getTitre(), medecin.getPrenom(), medecin.getNom(), new SimpleDateFormat("dd MMM yyyy").format(jour)}).getSummary();
      // l'agenda du médecin pour un jour donné
      agendaMedecinJour = application.getMetier().getAgendaMedecinJour(medecin.getId(), jour);
      // on affiche le formulaire 2
      setForms(false, true, false, false);
    } catch (Throwable th) {
      // vue des erreurs
      prepareVueErreur(th);
    }
  }

  // action sur RV
  public void action() {
    // on recherche le créneau dans l'agenda
    int i = 0;
    Boolean trouvé = false;
    while (!trouvé && i < agendaMedecinJour.getCreneauxMedecinJour().length) {
      if (agendaMedecinJour.getCreneauxMedecinJour()[i].getCreneau().getId() == idCreneau) {
        trouvé = true;
      } else {
        i++;
      }
    }
    // a-t-on trouvé ?
    if (!trouvé) {
      // c'est bizarre - on réaffiche form2
      setForms(false, true, false, false);
      return;
    }
    // on a trouvé
    creneauChoisi = agendaMedecinJour.getCreneauxMedecinJour()[i];
    // selon l'action désirée
    if (creneauChoisi.getRv() == null) {
      reserver();
    } else {
      supprimer();
    }
  }
  // réservation

  public void reserver() {
    try {
      // titre formulaire 3
      form3Titre = Messages.getMessage(null, "form3.titre", new Object[]{medecin.getTitre(), medecin.getPrenom(), medecin.getNom(), new SimpleDateFormat("dd MMM yyyy").format(jour),
                creneauChoisi.getCreneau().getHdebut(), creneauChoisi.getCreneau().getMdebut(), creneauChoisi.getCreneau().getHfin(), creneauChoisi.getCreneau().getMfin()}).getSummary();
      // client sélectionné dans le combo
      idClient=null;
      // on affiche le formulaire 3
      setForms(false, false, true, false);
    } catch (Throwable th) {
      // vue erreurs
      prepareVueErreur(th);
    }
  }

  public void supprimer() {
    try {
      // suppression d'un Rdv
      application.getMetier().supprimerRv(creneauChoisi.getRv().getId());
      // on remet à jour l'agenda
      agendaMedecinJour = application.getMetier().getAgendaMedecinJour(medecin.getId(), jour);
      // on affiche form2
      setForms(false, true, false, false);
    } catch (Throwable th) {
      // vue erreurs
      prepareVueErreur(th);
    }
  }

  // validation Rv
  public void validerRv() {
    try {
      // on récupère une instance du créneau horaire choisi
      Creneau creneau = application.getMetier().getCreneauById(idCreneau);
      // on ajoute le Rv
      application.getMetier().ajouterRv(jour, creneau, application.gethClients().get(idClient));
      // on remet à jour l'agenda
      agendaMedecinJour = application.getMetier().getAgendaMedecinJour(medecin.getId(), jour);
      // on affiche form2
      setForms(false, true, false, false);
    } catch (Throwable th) {
      // vue erreurs
      prepareVueErreur(th);
    }
  }

  // annulation prise de Rdv
  public void annulerRv() {
    // on affiche form2
    setForms(false, true, false, false);
  }

  public void accueil() {
    // on affiche la page d'accueil
    setForms(true, false, false, false);
  }

  // affichage vue
  private void setForms(Boolean form1Rendered, Boolean form2Rendered, Boolean form3Rendered, Boolean erreurRendered) {
    this.form1Rendered = form1Rendered;
    this.form2Rendered = form2Rendered;
    this.form3Rendered = form3Rendered;
    this.erreurRendered = erreurRendered;
  }

  // préparation vueErreur
  private void prepareVueErreur(Throwable th) {
    // on crée la liste des erreurs
    erreurs = new ArrayList<Erreur>();
    erreurs.add(new Erreur(th.getClass().getName(), th.getMessage()));
    while (th.getCause() != null) {
      th = th.getCause();
      erreurs.add(new Erreur(th.getClass().getName(), th.getMessage()));
    }
// la vue des erreurs est affichée
    setForms(false, false, false, true);
  }

  // getters et setters
  public Date getJour() {
    return jour;
  }

  public void setJour(Date jour) {
    this.jour = jour;
  }

  public Long getIdMedecin() {
    return idMedecin;
  }

  public void setIdMedecin(Long idMedecin) {
    this.idMedecin = idMedecin;
  }

  public Boolean getForm1Rendered() {
    return form1Rendered;
  }

  public Boolean getForm2Rendered() {
    return form2Rendered;
  }

  public Boolean getForm3Rendered() {
    return form3Rendered;
  }

  public String getForm2Titre() {
    return form2Titre;
  }

  public AgendaMedecinJour getAgendaMedecinJour() {
    return agendaMedecinJour;
  }

  public void setIdCreneau(Long idCreneau) {
    this.idCreneau = idCreneau;
  }

  public String getForm3Titre() {
    return form3Titre;
  }

  public Long getIdClient() {
    return idClient;
  }

  public void setIdClient(Long idClient) {
    this.idClient = idClient;
  }

  public Boolean getErreurRendered() {
    return erreurRendered;
  }

  public List<Erreur> getErreurs() {
    return erreurs;
  }

  public void setApplication(ApplicationInit application) {
    this.application = application;
  }
  
}