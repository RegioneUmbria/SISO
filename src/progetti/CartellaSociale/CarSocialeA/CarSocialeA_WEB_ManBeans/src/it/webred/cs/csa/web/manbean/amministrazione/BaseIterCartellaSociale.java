package it.webred.cs.csa.web.manbean.amministrazione;

import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableIterStepSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableOperatoreSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.IterDTO;
import it.webred.cs.csa.ejb.dto.KeyValueDTO;
import it.webred.cs.csa.ejb.dto.OperatoreDTO;
import it.webred.cs.data.model.CsASoggettoCategoriaSoc;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsItStep;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsOSettoreBASIC;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.util.ArrayList;
import java.util.List;

import javax.faces.model.SelectItem;

public abstract class BaseIterCartellaSociale extends CsUiCompBaseBean {
	protected AccessTableIterStepSessionBeanRemote iterService =
		(AccessTableIterStepSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableIterStepSessionBean");

	protected AccessTableConfigurazioneSessionBeanRemote configurazioneService = (AccessTableConfigurazioneSessionBeanRemote) 
			getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
	
	protected AccessTableOperatoreSessionBeanRemote operatoreService = (AccessTableOperatoreSessionBeanRemote) 
			getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableOperatoreSessionBean");
	
	protected AccessTableSoggettoSessionBeanRemote  soggettoService = (AccessTableSoggettoSessionBeanRemote) 
			getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableSoggettoSessionBean");
	
 	public AccessTableConfigurazioneSessionBeanRemote getConfigurazioneService() {
		return configurazioneService;
	}
 	 public List<CsASoggettoCategoriaSoc> getSoggettoCategorieAttuali(BaseDTO bDto){
 		 return soggettoService.getSoggettoCategorieAttualiBySoggetto(bDto);
 	 }
 	
 	
	public void setConfigurazioneService(
			AccessTableConfigurazioneSessionBeanRemote configurazioneService) {
		this.configurazioneService = configurazioneService;
	}

	public List<SelectItem> getListaIterStati(CeTBaseObject cet) {
		return convertiLista(iterService.getListaIterStati(cet));
	}
	public boolean addIterStep(IterDTO dto) throws Exception{
		return this.iterService.addIterStep(dto);
	}
	public CsItStep getLastIterStepByCaso(IterDTO dto) {
		try {
			return this.iterService.getLastIterStepByCaso(dto); 
		}catch (Exception ex){
			return null;
		}
	}
	
	public List<SelectItem> findOperatoriSettore(OperatoreDTO dto) {
		List<SelectItem> operatores = new ArrayList<SelectItem>();
		try{
			List<KeyValueDTO> result = operatoreService.findListaOperatoreSettoreBySettore(dto);
			for (KeyValueDTO it : result)
				operatores.add(new SelectItem( it.getCodice(), it.getDescrizione()));	
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}	
		return operatores;
	}
	
	public CsOOperatoreSettore getOperatoreSettore(OperatoreDTO dto) {
		try {
			return operatoreService.findOperatoreSettoreById(dto);
		} catch (Exception e) {
			return null;
		}
	}
	public CsASoggettoLAZY getSoggettoByCF(BaseDTO dto){
		 
			return this.soggettoService.getSoggettoByCF(dto);
	 
	}
	
	public List<CsASoggettoLAZY> getSoggettiByCF(BaseDTO dto){
		 
		return this.soggettoService.getSoggettiByCF(dto);
 
    }
	public List<CsOSettoreBASIC> findSettoreBASICByOrganizzazione(BaseDTO dto){
		return this.configurazioneService.findSettoreBASICByOrganizzazione(dto);
	}
	public List<CsOSettore> getListaSettori(CeTBaseObject cet) {
		return this.configurazioneService.getSettoreAll(cet);
	}
	public List<CsOOrganizzazione> getOrganizzazione(CeTBaseObject cet) {
		return this.configurazioneService.getOrganizzazioniAll(cet);
	}
	public List<CsOOperatore> getOperatore(CeTBaseObject cet) {
		return null;
	}
	
	public AccessTableIterStepSessionBeanRemote getIterService() {
		return iterService;
	}

	public void setIterService(AccessTableIterStepSessionBeanRemote iterService) {
		this.iterService = iterService;
	}
	
  
}
