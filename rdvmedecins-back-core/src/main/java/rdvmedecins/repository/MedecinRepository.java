package rdvmedecins.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import rdvmedecins.domain.UserMedecin;

/**
 * Spring Data JPA repository for the [Medecin] entity 
 * 
 * @author Malick
 *
 */
public interface MedecinRepository extends JpaRepository<UserMedecin, Long> {
	
	
}
