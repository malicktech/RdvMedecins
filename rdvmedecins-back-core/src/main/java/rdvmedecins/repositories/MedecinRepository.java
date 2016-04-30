package rdvmedecins.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import rdvmedecins.entities.Medecin;

/**
 * Spring Data JPA repository for the [Medecin] entity 
 * 
 * @author Malick
 *
 */
public interface MedecinRepository extends JpaRepository<Medecin, Long> {
	
	
}
