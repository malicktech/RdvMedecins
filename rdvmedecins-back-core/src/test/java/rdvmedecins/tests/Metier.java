package rdvmedecins.tests;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import rdvmedecins.config.DomainAndPersistenceConfig;
import rdvmedecins.domain.AgendaMedecinJour;
import rdvmedecins.entities.Client;
import rdvmedecins.entities.Creneau;
import rdvmedecins.entities.Medecin;
import rdvmedecins.entities.Rv;
import rdvmedecins.metier.IMetier;

@SpringApplicationConfiguration(classes = DomainAndPersistenceConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class Metier {

	@Autowired
	private IMetier métier;


	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		System.out.println("La méthode annotée sera lancée avant le premier test.");
		// TODO use properties files
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		System.out.println("La méthode annotée sera lancée après le dernier test.");

	}

	/** 
	 * initialiser des variables et ressources communes à tous les tests et à les nettoyer à la fin
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		System.out.println("avant un test");
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("après un test");
	}
	
	@Test
	public void test1() {
		// affichage clients
		List<Client> clients = métier.getAllClients();
		display("Liste des clients :", clients);
		
		// affichage médecins
		List<Medecin> medecins = métier.getAllMedecins();
		display("Liste des médecins :", medecins);
		
		// affichage créneaux d'un médecin
		Medecin médecin = medecins.get(0);
		List<Creneau> creneaux = métier.getAllCreneaux(médecin.getId());
		display(String.format("Liste des créneaux du médecin %s", médecin), creneaux);
		
		// liste des Rv d'un médecin, un jour donné
		Date jour = new Date();
		display(String.format("Liste des rv du médecin %s, le [%s]", médecin, jour),
		    métier.getRvMedecinJour(médecin.getId(), jour));
		
		// ajouter un RV
		Rv rv = null;
		Creneau créneau = creneaux.get(2);
		Client client = clients.get(0);
		System.out.println(String.format("Ajout d'un Rv le [%s] dans le créneau %s pour le client %s", jour, créneau,
		    client));
		rv = métier.ajouterRv(jour, créneau, client);
		
		// vérification
		Rv rv2 = métier.getRvById(rv.getId());
		Assert.assertEquals(rv, rv2);
		display(String.format("Liste des Rv du médecin %s, le [%s]", médecin, jour),
		    métier.getRvMedecinJour(médecin.getId(), jour));
		
		// ajouter un RV dans le même créneau du même jour
		// doit provoquer une exception
		System.out.println(String.format("Ajout d'un Rv le [%s] dans le créneau %s pour le client %s", jour, créneau,
		    client));
		Boolean erreur = false;
		try {
			rv = métier.ajouterRv(jour, créneau, client);
			System.out.println("Rv ajouté");
		} catch (Exception ex) {
			Throwable th = ex;
			while (th != null) {
				System.out.println(ex.getMessage());
				th = th.getCause();
			}
			// on note l'erreur
			erreur = true;
		}
		
		// on vérifie qu'il y a eu une erreur
		Assert.assertTrue(erreur);
		
		// liste des RV
		display(String.format("Liste des Rv du médecin %s, le [%s]", médecin, jour),
		    métier.getRvMedecinJour(médecin.getId(), jour));
		
		// affichage agenda
		AgendaMedecinJour agenda = métier.getAgendaMedecinJour(médecin.getId(), jour);
		System.out.println(agenda);
		Assert.assertEquals(rv, agenda.getCreneauxMedecinJour()[2].getRv());
		
		// supprimer un RV
		System.out.println("Suppression du Rv ajouté");
		métier.supprimerRv(rv.getId());
		
		// vérification
		rv2 = métier.getRvById(rv.getId());
		Assert.assertNull(rv2);
		display(String.format("Liste des Rv du médecin %s, le [%s]", médecin, jour),
		    métier.getRvMedecinJour(médecin.getId(), jour));
	}
	
	@Test
	public void testAjoutRendezVous() {
	}
	
	@Test
	public void testSupressionRendezVous() {
	}
	
	@Test
	public void testAgenda() {
	}


	// méthode utilitaire - affiche les éléments d'une collection
	private void display(String message, Iterable<?> elements) {
		System.out.println(message);
		for (Object element : elements) {
			System.out.println(element);
		}
	}

}
