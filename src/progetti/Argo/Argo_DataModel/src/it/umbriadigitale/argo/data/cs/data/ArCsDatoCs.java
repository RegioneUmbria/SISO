package it.umbriadigitale.argo.data.cs.data;

// Generated 26-ott-2015 13.12.17 by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * ArCsDatoCs generated by hbm2java
 */
@Entity
@Table(name = "AR_CS_DATO_CS")
public class ArCsDatoCs implements java.io.Serializable {

	private long id;
	private ArCsSoggettoCs arCsSoggettoCs;
	private ArDato arDato;
	private Set<ArCsSoggettoCs> arCsSoggettoCses = new HashSet<ArCsSoggettoCs>(
			0);
	private Set<ArCsTrasfFondi> arCsTrasfFondis = new HashSet<ArCsTrasfFondi>(0);
	private Set<ArCsConsuntivoServ> arCsConsuntivoServs = new HashSet<ArCsConsuntivoServ>(
			0);

	public ArCsDatoCs() {
	}

	public ArCsDatoCs(long id, ArDato arDato) {
		this.id = id;
		this.arDato = arDato;
	}

	public ArCsDatoCs(long id, ArCsSoggettoCs arCsSoggettoCs, ArDato arDato,
			Set<ArCsSoggettoCs> arCsSoggettoCses,
			Set<ArCsTrasfFondi> arCsTrasfFondis,
			Set<ArCsConsuntivoServ> arCsConsuntivoServs) {
		this.id = id;
		this.arCsSoggettoCs = arCsSoggettoCs;
		this.arDato = arDato;
		this.arCsSoggettoCses = arCsSoggettoCses;
		this.arCsTrasfFondis = arCsTrasfFondis;
		this.arCsConsuntivoServs = arCsConsuntivoServs;
	}

	@Id
	@Column(name = "ID", unique = true, nullable = false, precision = 10, scale = 0)
	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SOGGETTO_CS_ID")
	public ArCsSoggettoCs getArCsSoggettoCs() {
		return this.arCsSoggettoCs;
	}

	public void setArCsSoggettoCs(ArCsSoggettoCs arCsSoggettoCs) {
		this.arCsSoggettoCs = arCsSoggettoCs;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DATO_ID", nullable = false)
	public ArDato getArDato() {
		return this.arDato;
	}

	public void setArDato(ArDato arDato) {
		this.arDato = arDato;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "arCsDatoCs")
	public Set<ArCsSoggettoCs> getArCsSoggettoCses() {
		return this.arCsSoggettoCses;
	}

	public void setArCsSoggettoCses(Set<ArCsSoggettoCs> arCsSoggettoCses) {
		this.arCsSoggettoCses = arCsSoggettoCses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "arCsDatoCs")
	public Set<ArCsTrasfFondi> getArCsTrasfFondis() {
		return this.arCsTrasfFondis;
	}

	public void setArCsTrasfFondis(Set<ArCsTrasfFondi> arCsTrasfFondis) {
		this.arCsTrasfFondis = arCsTrasfFondis;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "arCsDatoCs")
	public Set<ArCsConsuntivoServ> getArCsConsuntivoServs() {
		return this.arCsConsuntivoServs;
	}

	public void setArCsConsuntivoServs(
			Set<ArCsConsuntivoServ> arCsConsuntivoServs) {
		this.arCsConsuntivoServs = arCsConsuntivoServs;
	}

}
