package it.webred.cs.jsf.interfaces;

import java.util.List;

import javax.faces.model.SelectItem;

import it.webred.cs.data.model.CsDDocIndividuale;
import it.webred.cs.data.model.CsTbSottocartellaDoc;
import it.webred.cs.jsf.manbean.DiarioDocsMan;
import it.webred.cs.jsf.manbean.DocIndividualeBean;



public interface IDocIndividuali {
	
	public void initializeData();
	public void nuovo();
	public void caricaPubblico();
	public void caricaPrivato();
	public void indicaLettura();
	public void salva();
	public void elimina();
	public Long getIdCaso();
	public int getIdxSelected();
	public CsDDocIndividuale getDocIndividuale();
	public DiarioDocsMan getDiarioDocsMan();
	public String getModalHeader();
	public List<DocIndividualeBean> getListaDocIndividualiPubblica();
	public List<DocIndividualeBean> getListaDocIndividualiPrivata();
	public boolean isDisableUpload();
	public List<SelectItem> getListaSottocartelleItem();

}