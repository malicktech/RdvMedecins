package rdvmedecins.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFilter;

@Entity
@Table(name = "creneaux")
@JsonFilter("creneauFilter")
public class Creneau extends AbstractAuditingEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Fields
	 * =========================================================================
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	protected Long id;

	private int hdebut;
	private int mdebut;
	private int hfin;
	private int mfin;

	/** a timeslot is linked to one medecin */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_medecin")
	private UserMedecin medecin;

	@OneToMany(mappedBy = "creneau")
	private List<Rv> rdVs = new ArrayList<>();

	/*
	 * foreign keys
	 * =========================================================================
	 * used to add object directly : Creneau is not owner of releationship
	 */
	
	@Column(name = "id_medecin", nullable = false, insertable = false, updatable = false)
	private long idMedecin;

	public long getIdMedecin() {
		return idMedecin;
	}

	/*
	 * constructors
	 * =========================================================================
	 */

	public Creneau() {
	}

	public Creneau(UserMedecin medecin, int hdebut, int mdebut, int hfin, int mfin) {
		this.medecin = medecin;
		this.hdebut = hdebut;
		this.mdebut = mdebut;
		this.hfin = hfin;
		this.mfin = mfin;
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

	public int getHdebut() {
		return hdebut;
	}

	public void setHdebut(int hdebut) {
		this.hdebut = hdebut;
	}

	public int getMdebut() {
		return mdebut;
	}

	public void setMdebut(int mdebut) {
		this.mdebut = mdebut;
	}

	public int getHfin() {
		return hfin;
	}

	public void setHfin(int hfin) {
		this.hfin = hfin;
	}

	public int getMfin() {
		return mfin;
	}

	public void setMfin(int mfin) {
		this.mfin = mfin;
	}

	public UserMedecin getMedecin() {
		return medecin;
	}

	public void setMedecin(UserMedecin medecin) {
		this.medecin = medecin;
	}

	public List<Rv> getRdVs() {
		return rdVs;
	}

	public void setRdVs(List<Rv> rdVs) {
		this.rdVs = rdVs;
	}

	/*
	 * Equals , hashCode, toString
	 * =========================================================================
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Creneau other = (Creneau) o;
		if (other.id == null || id == null) {
			return false;
		}
		return Objects.equals(id, other.id);
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	public String toString() {
		return String.format("Créneau[%d, %d, %d:%d, %d:%d]", id, version, hdebut, mdebut, hfin, mfin);
	}

}