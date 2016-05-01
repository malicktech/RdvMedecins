package rdvmedecins.metier;

import java.util.Date;
import java.util.List;

import rdvmedecins.domain.AgendaMedecinJour;
import rdvmedecins.entities.Client;
import rdvmedecins.entities.Creneau;
import rdvmedecins.entities.Rv;

public interface CreneauRdvService {

	public Rv createRv(Date jour, Creneau créneau, Client client);
	public Rv updateRv(Rv rv);	
	public void deleteRv(Long idRv);
	
	public Rv findRvById(Long idRv);
	public List<Rv> findAppointmentsByDoctorByDay(long idDcotor, Date day);
	
	public Long countAllAppointments();
	
	// TODO get rv/appointment of the day
	// TODO get rv/appointment of client (date min : today, or status true)
	// TODO get rv/appointment of doctor
	
	public Creneau createTimeslot(Date jour, Creneau créneau, Client client);
	public Creneau updateTimeslot(Creneau timeslot);	
	public void deleteTimeslot(Long id);
		
	public Creneau findTimeslotById(Long id);
	
	public List<Creneau> findAllTimeslotOfDoctor(long idDoctor);
	
	// TODO get calendar/schedule of doctor
	
	public AgendaMedecinJour getAgendaMedecinJour(long idMedecin, Date jour);
}
