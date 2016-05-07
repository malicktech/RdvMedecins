package rdvmedecins.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import rdvmedecins.domain.Client;

/**
 * Spring Data JPA repository for the [Client] entity
 * 
 * @author Malick
 *
 */
public interface ClientRepository extends JpaRepository<Client, Long> {
	
}
