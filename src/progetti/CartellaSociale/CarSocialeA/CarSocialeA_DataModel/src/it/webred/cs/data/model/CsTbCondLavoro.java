package it.webred.cs.data.model;

import java.io.Serializable;

import javax.persistence.*;


@Entity
@Table(name="CS_TB_COND_LAVORO")
public class CsTbCondLavoro implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_TB_COND_LAVORO_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_TB_COND_LAVORO_ID_GENERATOR")
	private long id;
	
	private Boolean abilitato;

	private String descrizione;

	private String tooltip;
	
	@ManyToOne
	@JoinColumn(name="ING_MERCATO_FK")
	private CsTbIngMercato csTbIngMercato;
	
	public CsTbCondLavoro() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Boolean getAbilitato() {
		return abilitato;
	}

	public void setAbilitato(Boolean abilitato) {
		this.abilitato = abilitato;
	}

	public String getDescrizione() {
		return this.descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getTooltip() {
		return this.tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public CsTbIngMercato getCsTbIngMercato() {
		return csTbIngMercato;
	}

	public void setCsTbIngMercato(CsTbIngMercato csTbIngMercato) {
		this.csTbIngMercato = csTbIngMercato;
	}
	
}