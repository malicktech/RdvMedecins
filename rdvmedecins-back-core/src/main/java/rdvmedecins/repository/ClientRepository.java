package rdvmedecins.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import rdvmedecins.domain.UserClient;

/**
 * Spring Data JPA repository for the [Client] entity
 * 
 * @author Malick
 *
 */
public interface ClientRepository extends JpaRepository<UserClient, Long> {
	
}
