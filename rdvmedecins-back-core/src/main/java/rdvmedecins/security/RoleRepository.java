package rdvmedecins.security;

import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {

	// recherche d'un rôle via son nom
	Role findRoleByName(String name);

}
