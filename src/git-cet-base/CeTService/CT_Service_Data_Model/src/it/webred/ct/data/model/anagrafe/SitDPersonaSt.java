package it.webred.ct.data.model.anagrafe;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the SIT_D_PERSONA_ST database table.
 * 
 */
@Entity
@Table(name="SIT_D_PERSONA_ST")
@NamedQuery(name="SitDPersonaSt.findAll", query="SELECT s FROM SitDPersonaSt s")
public class SitDPersonaSt implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private SitDPersonaStPK id;

	private String codfisc;

	private String cognome;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_EMI")
	private Date dataEmi;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_IMM")
	private Date dataImm;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_INIZIO_RESIDENZA")
	private Date dataInizioResidenza;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_MOR")
	private Date dataMor;

	@Temporal(TemporalType.DATE)
	@Column(name="DATA_NASCITA")
	private Date dataNascita;

	@Column(name="ID_EXT_COMUNE_EMI")
	private String idExtComuneEmi;

	@Column(name="ID_EXT_COMUNE_IMM")
	private String idExtComuneImm;

	@Column(name="ID_EXT_COMUNE_MOR")
	private String idExtComuneMor;

	@Column(name="ID_EXT_COMUNE_NASCITA")
	private String idExtComuneNascita;

	@Column(name="ID_EXT_PROVINCIA_EMI")
	private String idExtProvinciaEmi;

	@Column(name="ID_EXT_PROVINCIA_IMM")
	private String idExtProvinciaImm;

	@Column(name="ID_EXT_PROVINCIA_MOR")
	private String idExtProvinciaMor;

	@Column(name="ID_EXT_PROVINCIA_NASCITA")
	private String idExtProvinciaNascita;

	@Column(name="ID_EXT_STATO")
	private String idExtStato;

	@Column(name="INDIRIZZO_EMI")
	private String indirizzoEmi;

	private String nome;

	private String processid;

	private String sesso;

	@Column(name="STATO_CIVILE")
	private String statoCivile;

	public SitDPersonaSt() {
	}

	public SitDPersonaStPK getId() {
		return this.id;
	}

	public void setId(SitDPersonaStPK id) {
		this.id = id;
	}

	public String getCodfisc() {
		return this.codfisc;
	}

	public void setCodfisc(String codfisc) {
		this.codfisc = codfisc;
	}

	public String getCognome() {
		return this.cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public Date getDataEmi() {
		return this.dataEmi;
	}

	public void setDataEmi(Date dataEmi) {
		this.dataEmi = dataEmi;
	}

	public Date getDataImm() {
		return this.dataImm;
	}

	public void setDataImm(Date dataImm) {
		this.dataImm = dataImm;
	}

	public Date getDataInizioResidenza() {
		return this.dataInizioResidenza;
	}

	public void setDataInizioResidenza(Date dataInizioResidenza) {
		this.dataInizioResidenza = dataInizioResidenza;
	}

	public Date getDataMor() {
		return this.dataMor;
	}

	public void setDataMor(Date dataMor) {
		this.dataMor = dataMor;
	}

	public Date getDataNascita() {
		return this.dataNascita;
	}

	public void setDataNascita(Date dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getIdExtComuneEmi() {
		return this.idExtComuneEmi;
	}

	public void setIdExtComuneEmi(String idExtComuneEmi) {
		this.idExtComuneEmi = idExtComuneEmi;
	}

	public String getIdExtComuneImm() {
		return this.idExtComuneImm;
	}

	public void setIdExtComuneImm(String idExtComuneImm) {
		this.idExtComuneImm = idExtComuneImm;
	}

	public String getIdExtComuneMor() {
		return this.idExtComuneMor;
	}

	public void setIdExtComuneMor(String idExtComuneMor) {
		this.idExtComuneMor = idExtComuneMor;
	}

	public String getIdExtComuneNascita() {
		return this.idExtComuneNascita;
	}

	public void setIdExtComuneNascita(String idExtComuneNascita) {
		this.idExtComuneNascita = idExtComuneNascita;
	}

	public String getIdExtProvinciaEmi() {
		return this.idExtProvinciaEmi;
	}

	public void setIdExtProvinciaEmi(String idExtProvinciaEmi) {
		this.idExtProvinciaEmi = idExtProvinciaEmi;
	}

	public String getIdExtProvinciaImm() {
		return this.idExtProvinciaImm;
	}

	public void setIdExtProvinciaImm(String idExtProvinciaImm) {
		this.idExtProvinciaImm = idExtProvinciaImm;
	}

	public String getIdExtProvinciaMor() {
		return this.idExtProvinciaMor;
	}

	public void setIdExtProvinciaMor(String idExtProvinciaMor) {
		this.idExtProvinciaMor = idExtProvinciaMor;
	}

	public String getIdExtProvinciaNascita() {
		return this.idExtProvinciaNascita;
	}

	public void setIdExtProvinciaNascita(String idExtProvinciaNascita) {
		this.idExtProvinciaNascita = idExtProvinciaNascita;
	}

	public String getIdExtStato() {
		return this.idExtStato;
	}

	public void setIdExtStato(String idExtStato) {
		this.idExtStato = idExtStato;
	}

	public String getIndirizzoEmi() {
		return this.indirizzoEmi;
	}

	public void setIndirizzoEmi(String indirizzoEmi) {
		this.indirizzoEmi = indirizzoEmi;
	}

	public String getNome() {
		return this.nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getProcessid() {
		return this.processid;
	}

	public void setProcessid(String processid) {
		this.processid = processid;
	}

	public String getSesso() {
		return this.sesso;
	}

	public void setSesso(String sesso) {
		this.sesso = sesso;
	}

	public String getStatoCivile() {
		return this.statoCivile;
	}

	public void setStatoCivile(String statoCivile) {
		this.statoCivile = statoCivile;
	}

}