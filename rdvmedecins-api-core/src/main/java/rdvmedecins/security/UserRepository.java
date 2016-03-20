package rdvmedecins.security;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

	// liste des rôles d'un utilisateur identifié par son id
	@Query("select ur.role from UserRole ur where ur.user.id=?1")
	Iterable<Role> getRoles(long id);

	// liste des rôles d'un utilisateur identifié par son login et son mot de passe
	@Query("select ur.role from UserRole ur where ur.user.login=?1 and ur.user.password=?2")
	Iterable<Role> getRoles(String login, String password);

	// recherche d'un utilisateur via son login
	User findUserByLogin(String login);
}
