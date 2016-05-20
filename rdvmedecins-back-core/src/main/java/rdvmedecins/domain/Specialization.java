package rdvmedecins.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "specialisation")
public class Specialization extends AbstractAuditingEntity {

	/*
	 * Serial Version UID
	 * =========================================================================
	 */

	private static final long serialVersionUID = 8421372668281286597L;

	/*
	 * Fields
	 * =========================================================================
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String name;

	/*
	 * Constructors
	 * =========================================================================
	 */
	public Specialization() {
	}
	
	/*
	 * getters et setters
	 * =========================================================================
	 */

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
