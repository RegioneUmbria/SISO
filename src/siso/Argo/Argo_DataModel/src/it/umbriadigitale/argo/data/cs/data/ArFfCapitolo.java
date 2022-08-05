package it.umbriadigitale.argo.data.cs.data;

// Generated 26-ott-2015 13.12.17 by Hibernate Tools 4.0.0

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ArFfCapitolo generated by hbm2java
 */
@Entity
@Table(name = "AR_FF_CAPITOLO")
public class ArFfCapitolo implements java.io.Serializable {

	@Id
	private long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SERVIZIO_TERRIOTORIO_ID", nullable = false)
	private ArFfServizioTerriotorio arFfServizioTerriotorio;
	
	@Column(name = "USER_INS", nullable = false, length = 50)
	private String userIns;
	
	private Date dtIns;
	
	@Column(name = "USER_MOD", nullable = false, length = 50)
	private String usrMod;
	private Date dtMod;
	private String codCapitoloSpesa;
	private String desCapitoloSpesa;

	public ArFfCapitolo() {
	}

	public ArFfCapitolo(long id,
			ArFfServizioTerriotorio arFfServizioTerriotorio, String userIns,
			Date dtIns, String codCapitoloSpesa, String desCapitoloSpesa) {
		this.id = id;
		this.arFfServizioTerriotorio = arFfServizioTerriotorio;
		this.userIns = userIns;
		this.dtIns = dtIns;
		this.codCapitoloSpesa = codCapitoloSpesa;
		this.desCapitoloSpesa = desCapitoloSpesa;
	}

	public ArFfCapitolo(long id,
			ArFfServizioTerriotorio arFfServizioTerriotorio, String userIns,
			Date dtIns, String usrMod, Date dtMod, String codCapitoloSpesa,
			String desCapitoloSpesa) {
		this.id = id;
		this.arFfServizioTerriotorio = arFfServizioTerriotorio;
		this.userIns = userIns;
		this.dtIns = dtIns;
		this.usrMod = usrMod;
		this.dtMod = dtMod;
		this.codCapitoloSpesa = codCapitoloSpesa;
		this.desCapitoloSpesa = desCapitoloSpesa;
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	
	public ArFfServizioTerriotorio getArFfServizioTerriotorio() {
		return this.arFfServizioTerriotorio;
	}

	public void setArFfServizioTerriotorio(
			ArFfServizioTerriotorio arFfServizioTerriotorio) {
		this.arFfServizioTerriotorio = arFfServizioTerriotorio;
	}

	public String getUserIns() {
		return this.userIns;
	}

	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_INS", nullable = false, length = 7)
	public Date getDtIns() {
		return this.dtIns;
	}

	public void setDtIns(Date dtIns) {
		this.dtIns = dtIns;
	}

	@Column(name = "USR_MOD", length = 50)
	public String getUsrMod() {
		return this.usrMod;
	}

	public void setUsrMod(String usrMod) {
		this.usrMod = usrMod;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_MOD", length = 7)
	public Date getDtMod() {
		return this.dtMod;
	}

	public void setDtMod(Date dtMod) {
		this.dtMod = dtMod;
	}

	@Column(name = "COD_CAPITOLO_SPESA", nullable = false, length = 50)
	public String getCodCapitoloSpesa() {
		return this.codCapitoloSpesa;
	}

	public void setCodCapitoloSpesa(String codCapitoloSpesa) {
		this.codCapitoloSpesa = codCapitoloSpesa;
	}

	@Column(name = "DES_CAPITOLO_SPESA", nullable = false)
	public String getDesCapitoloSpesa() {
		return this.desCapitoloSpesa;
	}

	public void setDesCapitoloSpesa(String desCapitoloSpesa) {
		this.desCapitoloSpesa = desCapitoloSpesa;
	}

}