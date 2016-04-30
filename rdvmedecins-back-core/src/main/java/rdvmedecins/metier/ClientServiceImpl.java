package rdvmedecins.metier;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rdvmedecins.entities.Client;
import rdvmedecins.entities.Medecin;
import rdvmedecins.repositories.ClientRepository;

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
	public Boolean deleteClient(Long id) {
		Client temp = clientRepository.findOne(id);
		if(temp!=null){
			 clientRepository.delete(temp);
			 return true;
		}
		return false;
	}

	@Override
	public Client updateClient(Client client) {
		return clientRepository.save(client);
	}

	@Override
	public List<Client> getAllClients() {
		List<Client> clients = clientRepository.findAll();
		return clients;
	}

	@Override
	public Client findOneClient(Long id) {
		return clientRepository.findOne(id);
	}

	@Override
	public List<Medecin> searchMedecins() {
		// TODO Add elastic search repository
		return null;
	}
	

}
