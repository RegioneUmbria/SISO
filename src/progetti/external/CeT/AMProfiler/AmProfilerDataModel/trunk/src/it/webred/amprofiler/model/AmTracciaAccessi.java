package it.webred.amprofiler.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the AM_USER database table.
 * 
 */
@Entity
@Table(name = "AM_TRACCIA_ACCESSI")
public class AmTracciaAccessi implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private BigDecimal id;

	@Column(name = "USER_NAME")
	private String userName;
	
	@Column(name = "RAGIONE_ACCESSO")
	private String ragioneAccesso;
	
	@Column(name="FK_AM_ITEM")
	private String fkAmItem;
	
	private String pratica;
	private String ente;
	private String pubk;
	private String prik;
	private String usata;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "TIME_ACCESSO")
	private Date timeAccesso;
	
	@Column(name="SESSION_ID")
	private String sessionId;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getUserName() {
		return userName;
	}

	public String getRagioneAccesso() {
		return ragioneAccesso;
	}

	public String getFkAmItem() {
		return fkAmItem;
	}

	public String getPratica() {
		return pratica;
	}

	public String getEnte() {
		return ente;
	}

	public String getPubk() {
		return pubk;
	}

	public String getPrik() {
		return prik;
	}

	public String getUsata() {
		return usata;
	}

	public Date getTimeAccesso() {
		return timeAccesso;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setRagioneAccesso(String ragioneAccesso) {
		this.ragioneAccesso = ragioneAccesso;
	}

	public void setFkAmItem(String fkAmItem) {
		this.fkAmItem = fkAmItem;
	}

	public void setPratica(String pratica) {
		this.pratica = pratica;
	}

	public void setEnte(String ente) {
		this.ente = ente;
	}

	public void setPubk(String pubk) {
		this.pubk = pubk;
	}

	public void setPrik(String prik) {
		this.prik = prik;
	}

	public void setUsata(String usata) {
		this.usata = usata;
	}

	public void setTimeAccesso(Date timeAccesso) {
		this.timeAccesso = timeAccesso;
	}

	public BigDecimal getId() {
		return id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

}