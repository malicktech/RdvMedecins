package rdvmedecins.service;

import java.util.List;

import rdvmedecins.domain.Client;
import rdvmedecins.domain.Medecin;

public interface ClientService {

	public Client createClient(Client emp);
	public Client updateClient(Client emp);
	public void deleteClient(Long empId);
	
	public Client findOneClient(Long empId);
	public List<Client> getAllClients();
	public List<Medecin> searchClients();
	
	public Long countAllClients();
}
