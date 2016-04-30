package rdvmedecins.metier;

import java.util.List;

import rdvmedecins.entities.Medecin;

public interface MedecinService {
	
	public Medecin createMedecin(Medecin Medecin);
    public Medecin updateMedecin(Medecin Medecin);
    public void deleteMedecin(Long id);

    public List<Medecin> findAllMedecins();
    public List<Medecin> searchMedecins();
    
}
