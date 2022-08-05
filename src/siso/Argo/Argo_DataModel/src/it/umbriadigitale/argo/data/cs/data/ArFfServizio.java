package it.umbriadigitale.argo.data.cs.data;

// Generated 26-ott-2015 13.12.17 by Hibernate Tools 4.0.0

import java.math.BigDecimal;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ArFfServizio generated by hbm2java
 */
@Entity
@Table(name = "AR_FF_SERVIZIO")
public class ArFfServizio implements java.io.Serializable {

	private long id;
	private ArFfLineafin arFfLineafin;
	private String descrizione;
	private String userIns;
	private Date dtIns;
	private String usrMod;
	private Date dtMod;
	private BigDecimal importoDicui;
	private Set<ArFfServizioClasse> arFfServizioClasses = new HashSet<ArFfServizioClasse>(0);
	private Set<ArFfServizioAreat> arFfServizioAreats = new HashSet<ArFfServizioAreat>(0);
	private Set<ArFfServizioTerriotorio> arFfServizioTerriotorios = new HashSet<ArFfServizioTerriotorio>(0);

	public ArFfServizio() {
	}

	public ArFfServizio(long id, ArFfLineafin arFfLineafin, String descrizione,
			String userIns, Date dtIns, BigDecimal importoDicui) {
		this.id = id;
		this.arFfLineafin = arFfLineafin;
		this.descrizione = descrizione;
		this.userIns = userIns;
		this.dtIns = dtIns;
		this.importoDicui = importoDicui;
	}

	public ArFfServizio(long id, ArFfLineafin arFfLineafin, String descrizione,
			String userIns, Date dtIns, String usrMod, Date dtMod,
			BigDecimal importoDicui,
			Set<ArFfServizioClasse> arFfServizioClasses,
			Set<ArFfServizioAreat> arFfServizioAreats,
			Set<ArFfServizioTerriotorio> arFfServizioTerriotorios) {
		this.id = id;
		this.arFfLineafin = arFfLineafin;
		this.descrizione = descrizione;
		this.userIns = userIns;
		this.dtIns = dtIns;
		this.usrMod = usrMod;
		this.dtMod = dtMod;
		this.importoDicui = importoDicui;
		this.arFfServizioClasses = arFfServizioClasses;
		this.arFfServizioAreats = arFfServizioAreats;
		this.arFfServizioTerriotorios = arFfServizioTerriotorios;
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
	@JoinColumn(name = "LINEAFIN_ID", nullable = false)
	public ArFfLineafin getArFfLineafin() {
		return this.arFfLineafin;
	}

	public void setArFfLineafin(ArFfLineafin arFfLineafin) {
		this.arFfLineafin = arFfLineafin;
	}

	@Column(name = "DESCRIZIONE", nullable = false)
	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
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

	@Column(name = "IMPORTO_DICUI", nullable = false, precision = 20)
	public BigDecimal getImportoDicui() {
		return this.importoDicui;
	}

	public void setImportoDicui(BigDecimal importoDicui) {
		this.importoDicui = importoDicui;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "arFfServizio")
	public Set<ArFfServizioClasse> getArFfServizioClasses() {
		return this.arFfServizioClasses;
	}

	public void setArFfServizioClasses(
			Set<ArFfServizioClasse> arFfServizioClasses) {
		this.arFfServizioClasses = arFfServizioClasses;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "arFfServizio")
	public Set<ArFfServizioAreat> getArFfServizioAreats() {
		return this.arFfServizioAreats;
	}

	public void setArFfServizioAreats(Set<ArFfServizioAreat> arFfServizioAreats) {
		this.arFfServizioAreats = arFfServizioAreats;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "arFfServizio")
	public Set<ArFfServizioTerriotorio> getArFfServizioTerriotorios() {
		return this.arFfServizioTerriotorios;
	}

	public void setArFfServizioTerriotorios(
			Set<ArFfServizioTerriotorio> arFfServizioTerriotorios) {
		this.arFfServizioTerriotorios = arFfServizioTerriotorios;
	}

}
