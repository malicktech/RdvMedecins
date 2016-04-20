package rdvmedecins.entities;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

@MappedSuperclass
public class AbstractEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;
	// version de chaque entité	, Ce n° sert à empêcher la mise à jour simultanée de l'entité par deux utilisateur différents	
	@Version
	protected Long version;

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (id != null ? id.hashCode() : 0);
		return hash;
	}

	// initialisation de  deux champs de [AbstractEntity]
	public AbstractEntity build(Long id, Long version) {
		this.id = id;
		this.version = version;
		return this;
	}

	// deux entités seront dites égales si elles ont le même nom de classe et le même identifiant id
	@Override
	public boolean equals(Object entity) {
		String class1 = this.getClass().getName();
		String class2 = entity.getClass().getName();
		if (!class2.equals(class1)) {
			return false;
		}
		AbstractEntity other = (AbstractEntity) entity;
		return this.id == other.id;
	}

	// getters et setters
	public Long getId() {
		return id;
	}

	public Long getVersion() {
		return version;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

}
