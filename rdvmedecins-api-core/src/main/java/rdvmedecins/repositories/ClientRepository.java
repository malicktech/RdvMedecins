package rdvmedecins.repositories;

import org.springframework.data.repository.CrudRepository;

import rdvmedecins.entities.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {
}
