package rdvmedecins.repositories;

import org.springframework.data.repository.CrudRepository;

import rdvmedecins.entities.Medecin;

public interface MedecinRepository extends CrudRepository<Medecin, Long> {
}
