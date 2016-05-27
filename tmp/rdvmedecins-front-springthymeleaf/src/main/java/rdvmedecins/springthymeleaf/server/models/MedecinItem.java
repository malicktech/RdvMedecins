package rdvmedecins.springthymeleaf.server.models;

import rdvmedecins.client.entities.Medecin;


public class MedecinItem extends PersonneItem{
	// constructeurs
	public MedecinItem(){
		
	}
	public MedecinItem(Medecin medecin){
		super(medecin);
	}
}
