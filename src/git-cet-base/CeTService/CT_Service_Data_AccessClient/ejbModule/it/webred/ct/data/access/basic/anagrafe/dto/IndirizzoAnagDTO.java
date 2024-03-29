package it.webred.ct.data.access.basic.anagrafe.dto;

import it.webred.ct.data.access.basic.common.utils.StringUtils;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

public class IndirizzoAnagDTO implements Serializable {

	private String civicoAltro;
	private String civicoNumero;
	private String codiceVia;

	private String comCod;
	private String comDes;

	private String indirizzo;
	private String prov;

	private String statoCod;
	private String statoDes;
	
	private Date dataInizioApp;

	public String getCivicoAltro() {
		return civicoAltro;
	}

	public String getCivicoNumero() {
		return civicoNumero;
	}

	public String getCodiceVia() {
		return codiceVia;
	}

	public String getComCod() {
		return comCod;
	}

	public String getComDes() {
		return comDes;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public String getProv() {
		return prov;
	}

	public String getStatoCod() {
		return statoCod;
	}

	public String getStatoDes() {
		return statoDes;
	}

	public void setCivicoAltro(String civicoAltro) {
		this.civicoAltro = civicoAltro;
	}

	public void setCivicoNumero(String civicoNumero) {
		this.civicoNumero = civicoNumero;
	}

	public void setCodiceVia(String codiceVia) {
		this.codiceVia = codiceVia;
	}

	public void setComCod(String comCod) {
		this.comCod = comCod;
	}

	public void setComDes(String comDes) {
		this.comDes = comDes;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public void setProv(String prov) {
		this.prov = prov;
	}

	public void setStatoCod(String statoCod) {
		this.statoCod = statoCod;
	}

	public void setStatoDes(String statoDes) {
		this.statoDes = statoDes;
	}

	public Date getDataInizioApp() {
		return dataInizioApp;
	}

	public void setDataInizioApp(Date dataInizioApp) {
		this.dataInizioApp = dataInizioApp;
	}
	
	public String getIndirizzoCompleto() {
		String ind="";
		if (indirizzo != null && !indirizzo.equals(""))
			ind +=indirizzo;
		if (civicoNumero != null && !civicoNumero.equals(""))
			ind += (ind.equals(""))? "" : ", " + StringUtils.removeLeadingZero(civicoNumero);
		if (civicoAltro != null && !civicoAltro.equals(""))
			ind += (ind.equals(""))? "" : "/" + StringUtils.removeLeadingZero(civicoAltro);
		return ind;
	}

	public String getIndirizzoCompletoComune(){
		String s = this.getIndirizzoCompleto();
		
		if(this.comDes!=null && !this.comDes.isEmpty())
			s+= " - "+comDes;
		if(this.prov!=null && !this.prov.isEmpty())
			s+= " ("+prov+")";
		
		return s;
	}
}