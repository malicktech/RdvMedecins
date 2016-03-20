package rdvmedecins.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Spring Security impose l'existence d'une classe implémentant l'interface [AppUserDetailsService]
 * 
 * @author Malick
 *
 */
@Service
public class AppUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		// on cherche l'utilisateur via son login
		User user = userRepository.findUserByLogin(login);
		// trouvé ?
		if (user == null) {
			throw new UsernameNotFoundException(String.format("login [%s] inexistant", login));
		}
		// on rend les détails de l'utilsateur
		return new AppUserDetails(user, userRepository);
	}

}
