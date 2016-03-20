package rdvmedecins.repositories;

import org.springframework.data.repository.CrudRepository;

import rdvmedecins.entities.Medecin;

/**
 * donne accès aux entités JPA [Client]
 * @author Malick
 *
 */
public interface MedecinRepository extends CrudRepository<Medecin, Long> {
}
