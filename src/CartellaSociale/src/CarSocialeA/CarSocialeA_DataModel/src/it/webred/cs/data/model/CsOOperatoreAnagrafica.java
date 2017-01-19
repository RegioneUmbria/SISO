package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="CS_O_OPERATORE_ANAGRAFICA")
public class CsOOperatoreAnagrafica implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;
	private String abilitato;

//INIZIO MOD-RL
	@Column(name="CODICE_FISCALE")
	private String codiceFiscale;
//FINE MOD-RL
		
	@Basic
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_FINE_VAL")
	private Date dataFineVal;

	@Basic
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_INIZIO_VAL")
	private Date dataInizioVal;

	@Basic
	private String tooltip;

	@Basic
	private String username;
	
	@Basic
	private String cognome;
	
	@Basic
	private String nome;
	

	public CsOOperatoreAnagrafica() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

//INIZIO MOD-RL
	public String getCodiceFiscale() {
		return codiceFiscale;
	}  

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	} 
//FINE MOD-RL
	public String getAbilitato() {
		return this.abilitato;
	}

	public void setAbilitato(String abilitato) {
		this.abilitato = abilitato;
	}

	public Date getDataFineVal() {
		return this.dataFineVal;
	}

	public void setDataFineVal(Date dataFineVal) {
		this.dataFineVal = dataFineVal;
	}

	public Date getDataInizioVal() {
		return this.dataInizioVal;
	}

	public void setDataInizioVal(Date dataInizioVal) {
		this.dataInizioVal = dataInizioVal;
	}

	public String getTooltip() {
		return this.tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}