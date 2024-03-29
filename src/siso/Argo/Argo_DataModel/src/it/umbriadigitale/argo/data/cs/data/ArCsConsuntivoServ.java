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
 * ArCsConsuntivoServ generated by hbm2java
 */
@Entity
@Table(name = "AR_CS_CONSUNTIVO_SERV")
public class ArCsConsuntivoServ implements java.io.Serializable {

	private long id;
	private ArCsConsuntivoServ arCsConsuntivoServ;
	private ArQClasse arQClasse;
	private ArCsDatoCs arCsDatoCs;
	private String codice;
	private String descrizione;
	private boolean flagPresenzaServizio;
	private long utenti;
	private BigDecimal spesaEuro;
	private BigDecimal spesaDiretta;
	private BigDecimal compartUtenti;
	private BigDecimal compartSsn;
	private boolean flagVerificaSituazEco;
	private String userIns;
	private Date dtIns;
	private String usrMod;
	private Date dtMod;
	private Set<ArCsConsuntivoServ> arCsConsuntivoServs = new HashSet<ArCsConsuntivoServ>(
			0);

	public ArCsConsuntivoServ() {
	}

	public ArCsConsuntivoServ(long id, ArQClasse arQClasse,
			ArCsDatoCs arCsDatoCs, String codice, String descrizione,
			boolean flagPresenzaServizio, long utenti, BigDecimal spesaEuro,
			BigDecimal spesaDiretta, BigDecimal compartUtenti,
			BigDecimal compartSsn, boolean flagVerificaSituazEco,
			String userIns, Date dtIns) {
		this.id = id;
		this.arQClasse = arQClasse;
		this.arCsDatoCs = arCsDatoCs;
		this.codice = codice;
		this.descrizione = descrizione;
		this.flagPresenzaServizio = flagPresenzaServizio;
		this.utenti = utenti;
		this.spesaEuro = spesaEuro;
		this.spesaDiretta = spesaDiretta;
		this.compartUtenti = compartUtenti;
		this.compartSsn = compartSsn;
		this.flagVerificaSituazEco = flagVerificaSituazEco;
		this.userIns = userIns;
		this.dtIns = dtIns;
	}

	public ArCsConsuntivoServ(long id, ArCsConsuntivoServ arCsConsuntivoServ,
			ArQClasse arQClasse, ArCsDatoCs arCsDatoCs, String codice,
			String descrizione, boolean flagPresenzaServizio, long utenti,
			BigDecimal spesaEuro, BigDecimal spesaDiretta,
			BigDecimal compartUtenti, BigDecimal compartSsn,
			boolean flagVerificaSituazEco, String userIns, Date dtIns,
			String usrMod, Date dtMod,
			Set<ArCsConsuntivoServ> arCsConsuntivoServs) {
		this.id = id;
		this.arCsConsuntivoServ = arCsConsuntivoServ;
		this.arQClasse = arQClasse;
		this.arCsDatoCs = arCsDatoCs;
		this.codice = codice;
		this.descrizione = descrizione;
		this.flagPresenzaServizio = flagPresenzaServizio;
		this.utenti = utenti;
		this.spesaEuro = spesaEuro;
		this.spesaDiretta = spesaDiretta;
		this.compartUtenti = compartUtenti;
		this.compartSsn = compartSsn;
		this.flagVerificaSituazEco = flagVerificaSituazEco;
		this.userIns = userIns;
		this.dtIns = dtIns;
		this.usrMod = usrMod;
		this.dtMod = dtMod;
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
	@JoinColumn(name = "CONSUNTIVO_SERV_ID")
	public ArCsConsuntivoServ getArCsConsuntivoServ() {
		return this.arCsConsuntivoServ;
	}

	public void setArCsConsuntivoServ(ArCsConsuntivoServ arCsConsuntivoServ) {
		this.arCsConsuntivoServ = arCsConsuntivoServ;
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
	@JoinColumn(name = "DATO_CS_ID", nullable = false)
	public ArCsDatoCs getArCsDatoCs() {
		return this.arCsDatoCs;
	}

	public void setArCsDatoCs(ArCsDatoCs arCsDatoCs) {
		this.arCsDatoCs = arCsDatoCs;
	}

	@Column(name = "CODICE", nullable = false, length = 20)
	public String getCodice() {
		return this.codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	@Column(name = "DESCRIZIONE", nullable = false)
	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Column(name = "FLAG_PRESENZA_SERVIZIO", nullable = false, precision = 1, scale = 0)
	public boolean isFlagPresenzaServizio() {
		return this.flagPresenzaServizio;
	}

	public void setFlagPresenzaServizio(boolean flagPresenzaServizio) {
		this.flagPresenzaServizio = flagPresenzaServizio;
	}

	@Column(name = "UTENTI", nullable = false, precision = 10, scale = 0)
	public long getUtenti() {
		return this.utenti;
	}

	public void setUtenti(long utenti) {
		this.utenti = utenti;
	}

	@Column(name = "SPESA_EURO", nullable = false, precision = 10)
	public BigDecimal getSpesaEuro() {
		return this.spesaEuro;
	}

	public void setSpesaEuro(BigDecimal spesaEuro) {
		this.spesaEuro = spesaEuro;
	}

	@Column(name = "SPESA_DIRETTA", nullable = false, precision = 10)
	public BigDecimal getSpesaDiretta() {
		return this.spesaDiretta;
	}

	public void setSpesaDiretta(BigDecimal spesaDiretta) {
		this.spesaDiretta = spesaDiretta;
	}

	@Column(name = "COMPART_UTENTI", nullable = false, precision = 10)
	public BigDecimal getCompartUtenti() {
		return this.compartUtenti;
	}

	public void setCompartUtenti(BigDecimal compartUtenti) {
		this.compartUtenti = compartUtenti;
	}

	@Column(name = "COMPART_SSN", nullable = false, precision = 10)
	public BigDecimal getCompartSsn() {
		return this.compartSsn;
	}

	public void setCompartSsn(BigDecimal compartSsn) {
		this.compartSsn = compartSsn;
	}

	@Column(name = "FLAG_VERIFICA_SITUAZ_ECO", nullable = false, precision = 1, scale = 0)
	public boolean isFlagVerificaSituazEco() {
		return this.flagVerificaSituazEco;
	}

	public void setFlagVerificaSituazEco(boolean flagVerificaSituazEco) {
		this.flagVerificaSituazEco = flagVerificaSituazEco;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "arCsConsuntivoServ")
	public Set<ArCsConsuntivoServ> getArCsConsuntivoServs() {
		return this.arCsConsuntivoServs;
	}

	public void setArCsConsuntivoServs(
			Set<ArCsConsuntivoServ> arCsConsuntivoServs) {
		this.arCsConsuntivoServs = arCsConsuntivoServs;
	}

}
