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

	/*
	 * Fields
	 * =========================================================================
	 */
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;

	/**
	 * specify that property as its optimistic lock value.
	 * 
	 * version de chaque entité , 
	 * Prevent concurrent changes in updating entity during transaction
	 * 
	 * rows count which increment on every update statement execution
	 */
	@Version
	protected Long version;
	
	/*
	 * constructors
	 * =========================================================================
	 */
	
	public AbstractEntity() {
	}

	public AbstractEntity build(Long id, Long version) {
		this.id = id;
		this.version = version;
		return this;
	}
	
	/*
	 * getters et setters
	 * =========================================================================
	 */

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
	
	/*
	 * Equals , hashCode, toString
	 * =========================================================================
	 */

}
