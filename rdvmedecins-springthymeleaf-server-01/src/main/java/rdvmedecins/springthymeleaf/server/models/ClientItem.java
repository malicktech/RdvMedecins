package rdvmedecins.springthymeleaf.server.models;

import rdvmedecins.client.entities.Client;


public class ClientItem extends PersonneItem{
// constructeurs
	public ClientItem(){
		
	}
	public ClientItem(Client client){
		super(client);
	}
}
