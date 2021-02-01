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
 * ArFfServizioClasse generated by hbm2java
 */
@Entity
@Table(name = "AR_FF_SERVIZIO_CLASSE")
public class ArFfServizioClasse implements java.io.Serializable {

	private long id;
	private ArQClasse arQClasse;
	private ArFfServizio arFfServizio;
	private String userIns;
	private Date dtIns;
	private String usrMod;
	private Date dtMod;

	public ArFfServizioClasse() {
	}

	public ArFfServizioClasse(long id, ArQClasse arQClasse,
			ArFfServizio arFfServizio, String userIns, Date dtIns) {
		this.id = id;
		this.arQClasse = arQClasse;
		this.arFfServizio = arFfServizio;
		this.userIns = userIns;
		this.dtIns = dtIns;
	}

	public ArFfServizioClasse(long id, ArQClasse arQClasse,
			ArFfServizio arFfServizio, String userIns, Date dtIns,
			String usrMod, Date dtMod) {
		this.id = id;
		this.arQClasse = arQClasse;
		this.arFfServizio = arFfServizio;
		this.userIns = userIns;
		this.dtIns = dtIns;
		this.usrMod = usrMod;
		this.dtMod = dtMod;
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
	@JoinColumn(name = "CLASSE_ID", nullable = false)
	public ArQClasse getArQClasse() {
		return this.arQClasse;
	}

	public void setArQClasse(ArQClasse arQClasse) {
		this.arQClasse = arQClasse;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SERVIZIO_ID", nullable = false)
	public ArFfServizio getArFfServizio() {
		return this.arFfServizio;
	}

	public void setArFfServizio(ArFfServizio arFfServizio) {
		this.arFfServizio = arFfServizio;
	}

	@Column(name = "USER_INS", nullable = false, length = 50)
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

}
