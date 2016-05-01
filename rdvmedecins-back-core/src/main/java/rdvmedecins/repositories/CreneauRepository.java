package rdvmedecins.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import rdvmedecins.entities.Creneau;

/**
 * Spring Data JPA repository for the [Creneau] entity 
 * 
 * @author Malick
 *
 */
public interface CreneauRepository extends JpaRepository<Creneau, Long> {
	
	
	/**
	 * Find Timeslot list of given doctor
	 */
	@Query("select c from Creneau c where c.medecin.id=?1")
	List<Creneau> findAllTimeslotbyDoctor(long idDoctor);
}
