package rdvmedecins.service;

import java.util.List;

import rdvmedecins.domain.Medecin;

public interface MedecinService {
	
	public Medecin createMedecin(Medecin Medecin);
    public Medecin updateMedecin(Medecin Medecin);
    public void deleteMedecin(Long id);

    public Medecin findOneMedecin(Long empId);
    public List<Medecin> findAllMedecins();
    public List<Medecin> searchMedecins();
    
    public Long countAllMedecins();
    
}
