package rdvmedecins.repositories;


import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import rdvmedecins.entities.Rv;

/**
 * Spring Data JPA repository for the [Rv] entity
 * 
 * @author Malick
 *
 */
public interface RvRepository extends JpaRepository<Rv, Long> {
	
	/** 
	 * l'agenda d'un medecin pour un jour donnée
	 * les rv du médecin sur un jour, avec les info sur les crénaux et les clients
	 * 
	 * les champs de la classe Rv, de types [Client] et [Creneau] sont obtenus en mode [FetchType.LAZY]
	 * 'ils doivent être demandés explicitement pour être obtenus
	 * 
	 * De jointures sont faites explicitement pour ramener les champs [client] et [creneau]. 
	 * Par ailleurs à cause de la jointure [cr.medecin.id=?1 ], nous aurons également le médecin.
	 */
	@Query("select rv from Rv rv left join fetch rv.client c left join fetch rv.creneau cr where cr.medecin.id=?1 and rv.jour=?2")
	List<Rv> findAppointmentByDoctorByDay(long idMedecin, Date jour);
	
	
}
