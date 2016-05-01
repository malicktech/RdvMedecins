package rdvmedecins.metier;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import rdvmedecins.domain.AgendaMedecinJour;
import rdvmedecins.domain.CreneauMedecinJour;
import rdvmedecins.entities.Client;
import rdvmedecins.entities.Creneau;
import rdvmedecins.entities.Medecin;
import rdvmedecins.entities.Rv;
import rdvmedecins.repositories.ClientRepository;
import rdvmedecins.repositories.CreneauRepository;
import rdvmedecins.repositories.MedecinRepository;
import rdvmedecins.repositories.RvRepository;

@Service
public class Metier implements IMetier {

	// répositories
	@Autowired
	private MedecinRepository medecinRepository;
	@Autowired
	private ClientRepository clientRepository;
	@Autowired
	private CreneauRepository creneauRepository;
	@Autowired
	private RvRepository rvRepository;

	// TODO DELETE
	
	@Override
	public List<Client> getAllClients() {
		return clientRepository.findAll();
	}

	// TODO DELETE
	@Override
	public List<Medecin> getAllMedecins() {
		return medecinRepository.findAll();
	}

	@Override
	public List<Rv> getRvMedecinJour(long idMedecin, Date jour) {
		return Lists.newArrayList(rvRepository.findAppointmentByDoctorByDay(idMedecin, jour));
	}

	// TODO DELETE
	@Override
	public Client getClientById(long id) {
		return clientRepository.findOne(id);
	}

	@Override
	public Medecin getMedecinById(long id) {
		return medecinRepository.findOne(id);
	}

	// TODO DELETE
	@Override
	public Rv getRvById(long id) {
		return rvRepository.findOne(id);
	}

	@Override
	public Creneau getCreneauById(long id) {
		return creneauRepository.findOne(id);
	}

	// TODO DELETE
	@Override
	public Rv ajouterRv(Date jour, Creneau créneau, Client client) {
		return rvRepository.save(new Rv(jour, client, créneau));
	}

	// TODO DELETE
	@Override
	public void supprimerRv(long idRv) {
		rvRepository.delete(idRv);
	}
	
	@Override
	public List<Creneau> getAllCreneaux(long idDoctor) {
		return creneauRepository.findAllTimeslotbyDoctor(idDoctor);
	}

	public AgendaMedecinJour getAgendaMedecinJour(long idMedecin, Date jour) {
		// liste des créneaux horaires du médecin
		List<Creneau> creneauxHoraires = getAllCreneaux(idMedecin);
		// liste des réservations de ce même médecin pour ce même jour
		List<Rv> reservations = getRvMedecinJour(idMedecin, jour);
		
		// on crée un dictionnaire à partir des Rv pris
		Map<Long, Rv> hReservations = new Hashtable<Long, Rv>();
		for (Rv resa : reservations) {
			hReservations.put(resa.getCreneau().getId(), resa);
		}
		// on crée l'agenda pour le jour demandé
		AgendaMedecinJour agenda = new AgendaMedecinJour();
		// le médecin
		agenda.setMedecin(getMedecinById(idMedecin));
		// le jour
		agenda.setJour(jour);
		// les créneaux de réservation
		CreneauMedecinJour[] creneauxMedecinJour = new CreneauMedecinJour[creneauxHoraires.size()];
		agenda.setCreneauxMedecinJour(creneauxMedecinJour);
		// remplissage des créneaux de réservation
		for (int i = 0; i < creneauxHoraires.size(); i++) {
			// ligne i agenda
			creneauxMedecinJour[i] = new CreneauMedecinJour();
			// créneau horaire
			Creneau créneau = creneauxHoraires.get(i);
			long idCreneau = créneau.getId();
			creneauxMedecinJour[i].setCreneau(créneau);
			// le créneau est-il libre ou réservé ?
			if (hReservations.containsKey(idCreneau)) {
				// le créneau est occupé - on note la résa
				Rv resa = hReservations.get(idCreneau);
				creneauxMedecinJour[i].setRv(resa);
			}
		}
		// on rend le résultat
		return agenda;
	}

}
