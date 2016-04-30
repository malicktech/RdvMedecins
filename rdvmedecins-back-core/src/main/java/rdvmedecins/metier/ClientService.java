package rdvmedecins.metier;

import java.util.List;

import rdvmedecins.entities.Client;
import rdvmedecins.entities.Medecin;

public interface ClientService {

	public Client createClient(Client emp);
	public Client updateClient(Client emp);
	public Boolean deleteClient(Long empId);
	
	public Client findOneClient(Long empId);
	public List<Client> getAllClients();
	public List<Medecin> searchMedecins();
}
