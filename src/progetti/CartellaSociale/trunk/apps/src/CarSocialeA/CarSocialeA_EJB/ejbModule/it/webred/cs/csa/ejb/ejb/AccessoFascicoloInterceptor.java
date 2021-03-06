package it.webred.cs.csa.ejb.ejb;

import it.webred.cs.csa.ejb.CarSocialeBaseSessionBean;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.RelazioneDTO;
import it.webred.cs.csa.ejb.dto.erogazioni.ErogazioneMasterDTO;
import it.webred.cs.data.model.CsDColloquioBASIC;
import it.webred.cs.data.model.CsDDiario;
import it.webred.cs.data.model.CsDDiarioBASIC;
import it.webred.cs.data.model.CsDDocIndividuale;
import it.webred.cs.data.model.CsDIsee;
import it.webred.cs.data.model.CsDPai;
import it.webred.cs.data.model.CsDScuola;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.data.model.CsIIntervento;

import java.util.Iterator;
import java.util.List;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

@SuppressWarnings("serial")
public class AccessoFascicoloInterceptor extends CarSocialeBaseSessionBean {

	@AroundInvoke
	public <T> Object intercetta(InvocationContext context){
		Object result = null;
		Object input = context.getParameters()[0];
		Long idSettore = null;
		Boolean nascondiInfo = null;
	    if(input instanceof BaseDTO){
	    	BaseDTO b = (BaseDTO)input ;
	    	idSettore = b.getSettoreId();
	    	nascondiInfo = b.getNascondiInfoPerSettore()!=null ? b.getNascondiInfoPerSettore() : false;
	    }
			
		try {
			result = context.proceed();
			
			List<T> listaRisultati = (List<T>) result;	
			
			if(idSettore != null && listaRisultati != null && listaRisultati.size() > 0){
				T tipoObj = listaRisultati.get(0);
				
				//interventi programmati
				if(tipoObj instanceof CsIIntervento){
					List<CsIIntervento> lip = (List<CsIIntervento> ) result;
					filtraInterventiProgrammati(lip, idSettore, nascondiInfo);
				}
				
				//EROGAZIONI
				if(tipoObj instanceof ErogazioneMasterDTO){
					List<ErogazioneMasterDTO> le = (List<ErogazioneMasterDTO> ) result;
					//le eorgazioni vengono gia filtrate per settore.
					//vanno filtrate solo eventualmente per secondo livello
					filtraErogazioni(le, idSettore);
				}
				
				
				//COLLOQUI (diario)
				if(tipoObj instanceof CsDColloquioBASIC){
					List<CsDColloquioBASIC> lcb = (List<CsDColloquioBASIC> ) result;
					filtraColloqui(lcb, idSettore, nascondiInfo);
				}
				
				//ATTIVITA' PROFESSIONALI
				if(tipoObj instanceof RelazioneDTO){
					List<RelazioneDTO> lr = (List<RelazioneDTO> ) result;
					filtraRelazioni(lr, idSettore, nascondiInfo);
				}
				
				//PAI
				if(tipoObj instanceof CsDPai){
					List<CsDPai> lp = (List<CsDPai> ) result;
					filtraPai(lp, idSettore, nascondiInfo);
				}
				
				//ISEE
				if(tipoObj instanceof CsDIsee){
					List<CsDIsee> li = (List<CsDIsee> ) result;
					filtraIsee(li, idSettore, nascondiInfo);
				}
		
				//DOCUMENTI INDIVIDUALI
				if(tipoObj instanceof CsDDocIndividuale){
					List<CsDDocIndividuale> ldi = (List<CsDDocIndividuale> ) result;
					filtraDocIndividuali(ldi, idSettore, nascondiInfo);
				}
				
				//VAL.SINBA and PROVVEDIMENTI-TRIBUNALE
				if(tipoObj instanceof CsDValutazione){
					List<CsDValutazione> lv = (List<CsDValutazione> ) result;
					filtraValutazioni(lv, idSettore, nascondiInfo);
				}
				
				
				//SCUOLA
				if(tipoObj instanceof CsDScuola){
					List<CsDScuola> ls = (List<CsDScuola> ) result;
					filtraScuole(ls, idSettore, nascondiInfo);
				}
			}
		} catch (Exception e) {
			logger.error("Errore fascicolo interceptor: "+e.getMessage(), e);
		}
			
		return result;
	}
	
	private boolean filtraSecondoLivelloBASIC(CsDDiarioBASIC diario, Long idSettore, Boolean nascondiInfo){
		boolean rimuovi = false;
		Boolean isSecondoLivelloInfo = diario.getVisSecondoLivello() == null ? false : true;
		rimuovi = (nascondiInfo || isSecondoLivelloInfo) && !idSettore.equals(diario.getCsOOperatoreSettore().getCsOSettore().getId());
		return rimuovi;
	}
	
	private boolean filtraSecondoLivello(CsDDiario diario, Long idSettore, Boolean nascondiInfo){
		boolean rimuovi = false;
		Boolean isSecondoLivelloInfo = diario.getVisSecondoLivello() == null ? false : true;
		rimuovi = (nascondiInfo || isSecondoLivelloInfo) && !idSettore.equals(diario.getCsOOperatoreSettore().getCsOSettore().getId());
		return rimuovi;
	}
	
	private void filtraColloqui(List<CsDColloquioBASIC> lcb, Long idSettore, Boolean nascondiInfo){
		Iterator<CsDColloquioBASIC> it = lcb.iterator();
		while(it.hasNext()){
			CsDColloquioBASIC c = it.next();
			if(filtraSecondoLivelloBASIC(c.getCsDDiario(),idSettore, nascondiInfo))
			   it.remove();
		}
		
	}
	
	private void filtraDocIndividuali(List<CsDDocIndividuale> ldi, Long idSettore, Boolean nascondiInfo){
		Iterator<CsDDocIndividuale> it = ldi.iterator();
		while(it.hasNext()){
			CsDDocIndividuale c = it.next();
			if(filtraSecondoLivello(c.getCsDDiario(),idSettore, nascondiInfo))
				   it.remove();
		}	
	}
	
	private void filtraIsee(List<CsDIsee> li, Long idSettore, Boolean nascondiInfo){
		Iterator<CsDIsee> it = li.iterator();
		while(it.hasNext()){
			CsDIsee c = it.next();
			if(filtraSecondoLivello(c.getCsDDiario(),idSettore, nascondiInfo))
				   it.remove();
		}	
	}
	
	private void filtraScuole(List<CsDScuola> ls, Long idSettore, Boolean nascondiInfo){
		Iterator<CsDScuola> it = ls.iterator();
		while(it.hasNext()){
			CsDScuola c = it.next();
			if(filtraSecondoLivello(c.getCsDDiario(),idSettore, nascondiInfo))
				   it.remove();
		}	
	}
	
	private void filtraRelazioni(List<RelazioneDTO> lr, Long idSettore, Boolean nascondiInfo){
		Iterator<RelazioneDTO> it = lr.iterator();
		while(it.hasNext()){
			RelazioneDTO cs = it.next();
			if(filtraSecondoLivello(cs.getRelazione().getCsDDiario(),idSettore, nascondiInfo))
				   it.remove();	
		}	
	}
	
	private void filtraPai(List<CsDPai> lp, Long idSettore, Boolean nascondiInfo){
		Iterator<CsDPai> it = lp.iterator();
		while(it.hasNext()){
			CsDPai c = it.next();
			if(filtraSecondoLivello(c.getCsDDiario(),idSettore, nascondiInfo))
				   it.remove();
		}	
	}
	
	private void filtraValutazioni(List<CsDValutazione> lv, Long idSettore, Boolean nascondiInfo){
		Iterator<CsDValutazione> it = lv.iterator();
		while(it.hasNext()){
			CsDValutazione c = it.next();
			if(filtraSecondoLivello(c.getCsDDiario(),idSettore, nascondiInfo))
				   it.remove();
		}	
	}
	
    private void filtraInterventiProgrammati(List<CsIIntervento> lip, Long idSettore, Boolean nascondiInfo){
		Iterator<CsIIntervento> it = lip.iterator();
		while(it.hasNext()){
			CsIIntervento cs = it.next();
            if(filtraSecondoLivello(cs.getCsFlgInterventos().iterator().next().getCsDDiario(),idSettore, nascondiInfo))
			   it.remove();
		}	
	}
    
    private void filtraErogazioni(List<ErogazioneMasterDTO> lem, Long idSettore){
    	Iterator<ErogazioneMasterDTO> it = lem.iterator();
    	while(it.hasNext()){
    		ErogazioneMasterDTO em = it.next();
    		if(em.getSettoreSecondoLivello() != null && !em.getSettoreSecondoLivello().equals(idSettore)){
    			it.remove();
    		}
    	}
    }
	
	
}
