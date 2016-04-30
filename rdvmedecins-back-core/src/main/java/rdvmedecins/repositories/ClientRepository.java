package rdvmedecins.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import rdvmedecins.entities.Client;

/**
 * Spring Data JPA repository for the [Client] entity
 * 
 * @author Malick
 *
 */
public interface ClientRepository extends JpaRepository<Client, Long> {
	
}
