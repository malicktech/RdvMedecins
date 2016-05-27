package rdvmedecins.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import rdvmedecins.domain.UserClient;
import rdvmedecins.domain.UserMedecin;
import rdvmedecins.repository.MedecinRepository;

@Service
@Transactional
public class MedecinServiceImpl implements MedecinService {

	/*
	 * DEPENDENCY INJECTION
	 * =========================================================================
	 */

	@Autowired
	private MedecinRepository medecinRepository;

	/*
	 * SERVICES METHODS
	 * =========================================================================
	 */

	@Override
	public UserMedecin createMedecin(UserMedecin medecin) {
		return medecinRepository.save(medecin);
	}

	@Override
	public UserMedecin updateMedecin(UserMedecin medecin) {
		return medecinRepository.save(medecin);
	}

	@Override
	public void deleteMedecin(Long id) {
		medecinRepository.delete (id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<UserMedecin> findAllMedecins() {
		List<UserMedecin> medecins = medecinRepository.findAll();
		return medecins;
	}
	
	@Override
	@Transactional(readOnly = true)
	public UserMedecin findOneMedecin(Long id) {
		return medecinRepository.findOne(id);
	}

	@Override
	public List<UserMedecin> searchMedecins() {
		// TODO implement elastic search repository
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public Long countAllMedecins() {		
		return medecinRepository.count();
	}

}
