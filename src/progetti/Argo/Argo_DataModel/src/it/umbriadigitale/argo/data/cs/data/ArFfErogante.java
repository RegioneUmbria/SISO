package it.umbriadigitale.argo.data.cs.data;

// Generated 26-ott-2015 13.12.17 by Hibernate Tools 4.0.0

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * ArFfErogante generated by hbm2java
 */
@Entity
@Table(name = "AR_FF_EROGANTE")
public class ArFfErogante implements java.io.Serializable {

	private ArFfEroganteId id;
	private ArFfServizioTerriotorio arFfServizioTerriotorio;
	private ArOOrganizzazione arOOrganizzazione;

	public ArFfErogante() {
	}

	public ArFfErogante(ArFfEroganteId id,
			ArFfServizioTerriotorio arFfServizioTerriotorio,
			ArOOrganizzazione arOOrganizzazione) {
		this.id = id;
		this.arFfServizioTerriotorio = arFfServizioTerriotorio;
		this.arOOrganizzazione = arOOrganizzazione;
	}

	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "servizioTerriotorioId", column = @Column(name = "SERVIZIO_TERRIOTORIO_ID", nullable = false, precision = 10, scale = 0)),
			@AttributeOverride(name = "organizzazioneId", column = @Column(name = "ORGANIZZAZIONE_ID", nullable = false, precision = 10, scale = 0)) })
	public ArFfEroganteId getId() {
		return this.id;
	}

	public void setId(ArFfEroganteId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SERVIZIO_TERRIOTORIO_ID", nullable = false, insertable = false, updatable = false)
	public ArFfServizioTerriotorio getArFfServizioTerriotorio() {
		return this.arFfServizioTerriotorio;
	}

	public void setArFfServizioTerriotorio(
			ArFfServizioTerriotorio arFfServizioTerriotorio) {
		this.arFfServizioTerriotorio = arFfServizioTerriotorio;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ORGANIZZAZIONE_ID", nullable = false, insertable = false, updatable = false)
	public ArOOrganizzazione getArOOrganizzazione() {
		return this.arOOrganizzazione;
	}

	public void setArOOrganizzazione(ArOOrganizzazione arOOrganizzazione) {
		this.arOOrganizzazione = arOOrganizzazione;
	}

}
