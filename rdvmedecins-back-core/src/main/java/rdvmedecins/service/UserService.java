package rdvmedecins.service;

import java.util.Optional;

import rdvmedecins.domain.User;
import rdvmedecins.domain.dto.ManagedUserDTO;

public interface UserService {

	public Optional<User> activateRegistration(String key);

	public Optional<User> completePasswordReset(String newPassword, String key);

	public Optional<User> requestPasswordReset(String mail);

	public User createUserInformation(String login, String password, String firstName, String lastName, String email,
			String langKey);

	public User createUser(ManagedUserDTO managedUserDTO);

	public void updateUserInformation(String firstName, String lastName, String email, String langKey);

	public void deleteUserInformation(String login);

	public void changePassword(String password);

	public Optional<User> getUserWithAuthoritiesByLogin(String login);

	public User getUserWithAuthorities(Long id);

	public User getUserWithAuthorities();

	void removeOldPersistentTokens();

	void removeNotActivatedUsers();

}
