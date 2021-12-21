package it.webred.ss.web.bean.lista.inviate;

import it.webred.ejb.utility.ClientUtility;
import it.webred.ss.data.model.SsAnagrafica;
import it.webred.ss.data.model.SsSchedaSegnalato;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;
import it.webred.ss.ejb.dto.BaseDTO;
import it.webred.ss.ejb.dto.DatiSchedaListDTO;
import it.webred.ss.ejb.dto.SsSearchCriteria;
import it.webred.ss.web.bean.SegretariatoSocBaseBean;
import it.webred.ss.web.bean.lista.LazySchedeUdcDataModel;
import it.webred.ss.web.bean.lista.Scheda;
import it.webred.ss.web.bean.util.PreselPuntoContatto;
import it.webred.ss.web.bean.util.UserBean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

public class LazySchedeInviateTableDataModel extends LazySchedeUdcDataModel {
	
	private static final long serialVersionUID = 1L;
	
	private Long ufficioId;
	private Long organizzazioneId;
	private Long soggettoId;
	
	  @Override
	    public List<Scheda> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map filters) {
		  SegretariatoSocBaseBean.logger.debug("*** Load List<Scheda> " + new Date());
			PreselPuntoContatto pContMan = (PreselPuntoContatto)SegretariatoSocBaseBean.getBeanReference("preselPuntoContatto");
	        List<Scheda> schede = new ArrayList<Scheda>();
	    	
		    SsSearchCriteria criteria = new SsSearchCriteria();
		    SegretariatoSocBaseBean.fillEnte(criteria);
		    criteria = this.getFilterCondition(filters, criteria);
		    criteria.setZonaSociale(UserBean.getBeanIstance().getZonaSociale());
	        if(this.ufficioId!=null){
	        	criteria.setUfficioId(ufficioId);
	        	criteria.setOrganizzazioneId(pContMan.getPuntoContatto().getOrganizzazione().getId()); //Uno stesso ufficio può essere in più organizzazioni
	        	schede = this.populateSchedeFromUfficio(first, pageSize, sortField, sortOrder, criteria);
	        }else if(this.organizzazioneId!=null && this.organizzazioneId>-1){
	        	criteria.setOrganizzazioneId(organizzazioneId);
	        	schede = this.populateSchedeFromOrganizzazione(first, pageSize, sortField, sortOrder, criteria);
	        }
	        
	        return schede;
	  }
	  
	  
	  
	  private List<Scheda> populateSchedeFromOrganizzazione(int first, int pageSize, String sortField, SortOrder sortOrder, SsSearchCriteria criteria) {  
			List<Scheda> schede = new ArrayList<Scheda>();
	    	try {
	    		SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
	    			"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
				
				criteria.setFirst(first);
				criteria.setPageSize(pageSize);
				
	        	Long size = schedaService.countSchedeInviateEnte(criteria);
				this.setRowCount(size.intValue());
				
				List<DatiSchedaListDTO> results = schedaService.searchSchedeInviateEnte(criteria);
	        	
	        	BaseDTO dto = new BaseDTO();
	        	SegretariatoSocBaseBean.fillEnte(dto);
	        	for(DatiSchedaListDTO row: results)
	        		schede.add(new Scheda(row));
	    
	    	} catch(Exception e) {
	    		SegretariatoSocBaseBean.logger.error(e.getMessage(), e);
	    		SegretariatoSocBaseBean.addError("lettura.error");
	    		
			}
			 		
	    	return schede;
		}
			
	  
	  
	  private List<Scheda> populateSchedeFromUfficio(int first, int pageSize, String sortField, SortOrder sortOrder, SsSearchCriteria criteria) {  
		List<Scheda> schede = new ArrayList<Scheda>();
    	try {
    		SsSchedaSessionBeanRemote schedaService = (SsSchedaSessionBeanRemote) ClientUtility.getEjbInterface(
    			"SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
			

			criteria.setFirst(first);
			criteria.setPageSize(pageSize);
			
        	Long size = schedaService.countSchedeInviateUfficio(criteria);
			this.setRowCount(size.intValue());
			
			List<DatiSchedaListDTO> results = schedaService.searchSchedeInviateUfficio(criteria);
        	
        	BaseDTO dto = new BaseDTO();
        	SegretariatoSocBaseBean.fillEnte(dto);
        	for(DatiSchedaListDTO row: results)
        		schede.add(new Scheda(row));
    
    	} catch(Exception e) {
    		SegretariatoSocBaseBean.logger.error(e.getMessage(), e);
    		SegretariatoSocBaseBean.addError("lettura.error");
    		
		}
		 		
    	return schede;
	}
		 	
	

	public Long getUfficioId() {
		return ufficioId;
	}

	public Long getSoggettoId() {
		return soggettoId;
	}

	public void setUfficioId(Long ufficioId) {
		this.ufficioId = ufficioId;
	}

	public void setSoggettoId(Long soggettoId) {
		this.soggettoId = soggettoId;
	}



	public Long getOrganizzazioneId() {
		return organizzazioneId;
	}



	public void setOrganizzazioneId(Long organizzazioneId) {
		this.organizzazioneId = organizzazioneId;
	}
	
	@Override
	protected SsSearchCriteria getFilterCondition(Map filters, SsSearchCriteria ss){
		  
		String fIntervento = (String) filters.get("intervento");
		String fSegnalato = (String)  filters.get("segnalato");
		String fOperatore = (String)  filters.get("operatore");
		String fPuntoContatto = (String)  filters.get("puntoContatto");
		
		if(fIntervento!=null)
			ss.setTipoSchedaDescr(fIntervento);
		
		if(fSegnalato!=null)
			ss.setSegnalato(fSegnalato);
		
		if(fOperatore!=null)
			ss.setOperatoreUserName(fOperatore);
		
		if(fPuntoContatto!=null)
			ss.setPuntoContattoDescr(fPuntoContatto);
		
	  return ss;
  }

	
}