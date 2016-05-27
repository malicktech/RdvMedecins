package rdvmedecins.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rdvmedecins.domain.UserClient;
import rdvmedecins.domain.UserMedecin;
import rdvmedecins.repository.ClientRepository;

@Service
@Transactional
public class ClientServiceImpl implements ClientService{

	/*
	 * DEPENDENCY INJECTION
	 * =========================================================================
	 */

	@Autowired
	protected ClientRepository clientRepository;


	/*
	 * SERVICES METHODS 
	 * =========================================================================
	 */

	@Override
	public UserClient createClient(UserClient client) {
		return clientRepository.save(client);
	}

	@Override
	public void deleteClient(Long id) {
		clientRepository.delete(id);
	}

	@Override
	public UserClient updateClient(UserClient client) {
		return clientRepository.save(client);
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserClient> getAllClients() {
		List<UserClient> clients = clientRepository.findAll();
		return clients;
	}

	@Override
	@Transactional(readOnly = true)
	public UserClient findOneClient(Long id) {
		return clientRepository.findOne(id);
	}

	@Override
	public List<UserMedecin> searchClients() {
		// TODO Add elastic search repository
		return null;
	}



	@Override
	public Long countAllClients() {
		return clientRepository.count();
	}
	

}
