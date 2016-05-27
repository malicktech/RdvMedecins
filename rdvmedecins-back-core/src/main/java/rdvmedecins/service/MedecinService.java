package rdvmedecins.service;

import java.util.List;

import rdvmedecins.domain.UserMedecin;

public interface MedecinService {
	
	public UserMedecin createMedecin(UserMedecin Medecin);
    public UserMedecin updateMedecin(UserMedecin Medecin);
    public void deleteMedecin(Long id);

    public UserMedecin findOneMedecin(Long empId);
    public List<UserMedecin> findAllMedecins();
    public List<UserMedecin> searchMedecins();
    
    public Long countAllMedecins();
    
}
