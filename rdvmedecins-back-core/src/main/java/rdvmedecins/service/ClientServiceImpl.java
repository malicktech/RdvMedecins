package rdvmedecins.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rdvmedecins.domain.Client;
import rdvmedecins.domain.Medecin;
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
	public Client createClient(Client client) {
		return clientRepository.save(client);
	}

	@Override
	public void deleteClient(Long id) {
		clientRepository.delete(id);
	}

	@Override
	public Client updateClient(Client client) {
		return clientRepository.save(client);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Client> getAllClients() {
		List<Client> clients = clientRepository.findAll();
		return clients;
	}

	@Override
	@Transactional(readOnly = true)
	public Client findOneClient(Long id) {
		return clientRepository.findOne(id);
	}

	@Override
	public List<Medecin> searchClients() {
		// TODO Add elastic search repository
		return null;
	}



	@Override
	public Long countAllClients() {
		return clientRepository.count();
	}
	

}
