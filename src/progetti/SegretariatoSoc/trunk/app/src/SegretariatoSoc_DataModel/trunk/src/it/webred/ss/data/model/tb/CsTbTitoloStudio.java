package it.webred.ss.data.model.tb;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the CS_TB_TITOLO_STUDIO database table.
 * 
 */
@Entity
@Table(name="CS_TB_TITOLO_STUDIO")
@NamedQueries({
	@NamedQuery(name="CsTbTitoloStudio.findAll", query="SELECT c FROM CsTbTitoloStudio c"),
	@NamedQuery(name="CsTbTitoloStudio.findAllAbilit", query="SELECT c FROM CsTbTitoloStudio c where c.abilitato = '1'")
,
}) 
public class CsTbTitoloStudio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="CS_TB_TITOLO_STUDIO_ID_GENERATOR", sequenceName="SQ_ID",allocationSize=1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="CS_TB_TITOLO_STUDIO_ID_GENERATOR")
	private long id;

	private String abilitato;

	private String descrizione;

	private String tooltip;

	public CsTbTitoloStudio() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getAbilitato() {
		return this.abilitato;
	}

	public void setAbilitato(String abilitato) {
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
}