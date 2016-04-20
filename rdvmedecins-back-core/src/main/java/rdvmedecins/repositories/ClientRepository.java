package rdvmedecins.repositories;

import org.springframework.data.repository.CrudRepository;

import rdvmedecins.entities.Client;

/**
 * donne accès aux entités JPA [Client]
 * @author Malick
 *
 */
public interface ClientRepository extends CrudRepository<Client, Long> {
}
