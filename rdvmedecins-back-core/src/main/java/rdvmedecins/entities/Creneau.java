package rdvmedecins.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFilter;

@Entity
@Table(name = "creneaux")
@JsonFilter("creneauFilter")
public class Creneau extends AbstractEntity {

	private static final long serialVersionUID = 1L;
	
	/*
	 * Fields
	 * =========================================================================
	 */
	private int hdebut;
	private int mdebut;
	private int hfin;
	private int mfin;

	/** a timeslot is linked to one medecin */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "id_medecin")
	private Medecin medecin;
	
	@OneToMany(mappedBy = "creneau" , fetch=FetchType.LAZY)   
    private List<Rv> rdVs = new ArrayList<>();

	/*
	 * constructors
	 * =========================================================================
	 */
	
	public Creneau() {
	}

	public Creneau(Medecin medecin, int hdebut, int mdebut, int hfin, int mfin) {
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

	public Medecin getMedecin() {
		return medecin;
	}

	public void setMedecin(Medecin medecin) {
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
		return String.format("Créneau[%d, %d, %d, %d:%d, %d:%d]", id, version, hdebut, mdebut, hfin, mfin);
	}
}