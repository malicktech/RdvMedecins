package rdvmedecins.service;

import java.util.List;

import rdvmedecins.domain.UserClient;
import rdvmedecins.domain.UserMedecin;

public interface ClientService {

	public UserClient createClient(UserClient emp);
	public UserClient updateClient(UserClient emp);
	public void deleteClient(Long empId);
	
	public UserClient findOneClient(Long empId);
	public List<UserClient> getAllClients();
	public List<UserMedecin> searchClients();
	
	public Long countAllClients();
}
