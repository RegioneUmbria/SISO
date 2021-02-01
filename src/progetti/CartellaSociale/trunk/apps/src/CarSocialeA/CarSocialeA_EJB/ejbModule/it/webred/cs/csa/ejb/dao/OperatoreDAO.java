package it.webred.cs.csa.ejb.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.inject.Named;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.apache.commons.lang.StringUtils;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.csa.ejb.dto.OperatoriSearchCriteria;
import it.webred.cs.data.model.CsODecodificaRuoli;
import it.webred.cs.data.model.CsOOperatore;
import it.webred.cs.data.model.CsOOperatoreAnagrafica;
import it.webred.cs.data.model.CsOOperatoreBASIC;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOperatoreTipoOperatore;
import it.webred.cs.data.model.CsOOpsettoreAlertConfig;
import it.webred.cs.data.model.CsOOrganizzazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsOSettoreBASIC;
import it.webred.cs.data.model.CsOZonaSoc;
import it.webred.cs.data.model.view.CsAmAnagraficaOperatore;
import it.webred.ct.support.validation.annotation.AuditSaltaValidazioneSessionID;

/**
 * @author Andrea
 *
 */
@Named
public class OperatoreDAO extends CarSocialeBaseDAO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	public CsOOrganizzazione findOrganizzazioneById( long id ) throws Exception	{
		CsOOrganizzazione organizzazione = em.find(CsOOrganizzazione.class,  id);
		return organizzazione;
	}
	
	public CsOSettore findSettoreById(long id) throws Exception {
			CsOSettore settore = em.find(CsOSettore.class, id);
			return settore;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CsOOperatoreTipoOperatore> getOperatoriByTipoId(Long id) {
		Query q = em.createNamedQuery("CsOOperatore.findOperatoriByTipoId");
		q.setParameter("id", id);
		return q.getResultList();	
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CsOOperatoreTipoOperatore> getOperatoriByTipoIdSettore(Long idTipo, Long idSettore) {
		Query q = em.createNamedQuery("CsOOperatore.findOperatoriByTipoIdSettore");
		q.setParameter("idTipo", idTipo);
		q.setParameter("idSettore", idSettore);
		return q.getResultList();
			
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CsOOperatore> getOperatoriByTipoDescrizione(String descrizione) {
			
		Query q = em.createNamedQuery("CsOOperatore.findOperatoriByTipoDescrizione");
		q.setParameter("descrizione", descrizione);
		List result = q.getResultList();
		if( result != null && result.size() > 0 )
			return result;
		
		return null;

	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CsOOperatore> getOperatoriAll() {
			
		Query q = em.createNamedQuery("CsOOperatore.findAll");
		return q.getResultList();

	}
	
	@SuppressWarnings("rawtypes")
	public CsOOperatoreTipoOperatore getTipoByOperatoreSettore(Long idOpSet, Date datFinApp) {
			
		Query q = em.createNamedQuery("CsOOperatore.findTipoByOperatoreSettore");
		q.setParameter("idOpSet", idOpSet);
		q.setParameter("dataFineApp",  datFinApp);
		List result = q.getResultList();
		if( result != null && result.size() > 0 )
			return (CsOOperatoreTipoOperatore)result.get(0);
		
		return null;

	}	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CsOOperatore> getOperatoriByCatSocialeOrg(Long idCatSociale, String codRouting) {
			
		Query q = em.createNamedQuery("CsOOperatore.findOperatoriByCatSocialeOrg");
		q.setParameter("idCatSociale", idCatSociale);
		q.setParameter("codRouting", codRouting);
		List result = q.getResultList();
		if( result != null && result.size() > 0 )
			return result;
		
		return null;

	}
	
	@SuppressWarnings("rawtypes")
	public CsOOperatoreSettore findOperatoreSettoreById(long idOp, long idSettore, Date date) throws Exception {
		Query q = em.createNamedQuery("CsOOperatoreSettore.getOperatoreSettoreById").setParameter("idOp", idOp).setParameter("date", date).setParameter("idSettore", idSettore);
		List result = q.getResultList();
		
		if( result != null && result.size() > 0 )
			return (CsOOperatoreSettore)result.get(0);
		
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public CsOOperatoreSettore findOperatoreSettoreById(long idOpSettore)  {
		
		CsOOperatoreSettore op = em.find(CsOOperatoreSettore.class, idOpSettore); 
		return op;
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<CsOOperatoreSettore> findOperatoreSettoreByOperatore(long idOp, Date date) throws Exception {
		Query q = em.createNamedQuery("CsOOperatoreSettore.findOperatoreSettoreByOperatore").setParameter("idOp", idOp).setParameter("date", date);
		List results = q.getResultList();
		return results;
	}
	
	@SuppressWarnings({"unchecked", "rawtypes"})
	public List<CsOOperatoreSettore> findOperatoreSettoreByUsername(String idOp, Date date) throws Exception {
		Query q = em.createNamedQuery("CsOOperatoreSettore.findOperatoreSettoreByUsername").setParameter("username", idOp).setParameter("date", date);
		List results = q.getResultList();
		return results;
	}

	@SuppressWarnings("rawtypes")
	@AuditSaltaValidazioneSessionID
	public CsOOperatore findOperatoreByUsername(String username) throws Exception {
		CsOOperatore o = null;
		if( StringUtils.isEmpty( username ) )
			return null;
		
		try{
			Query q = em.createNamedQuery("CsOOperatore.getOperatoreByUsername");
			q.setParameter("username", username);
			List result = q.getResultList();
			if( result != null && result.size() > 0 ){
				o = (CsOOperatore)result.get(0);
				o.getCsOOperatoreSettores().size();
			}
			
		}catch(Exception t){
			logger.error(t.getMessage(),t);
			throw(t);
		}
		return o;
	}
	
	public CsOOperatore findOperatoreById(long idOp) throws Exception {
		
		Query op = em.createNamedQuery("CsOOperatore.getOperatoreById")
				.setParameter("idOp", idOp);

		return (CsOOperatore) op.getSingleResult();
	}
	
	public CsOOperatoreBASIC findOperatoreBASICById(long idOp) throws Exception {
		
		CsOOperatoreBASIC operatore = em.find(CsOOperatoreBASIC.class, idOp);
		return operatore;
		
	}
	
	public CsOOperatoreBASIC findOperatoreBASICByUserName(String userName) throws Exception {

		Query op = em.createNamedQuery("CsOOperatoreBASIC.getOperatoreBASICByUserName")
				.setParameter("username", userName);

		return (CsOOperatoreBASIC) op.getSingleResult();
	}
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<CsOOperatoreSettore> findOperatoreSettori() throws Exception {
		Query q = em.createNamedQuery("CsOOperatoreSettore.findAll");
		List results = q.getResultList();
		return results;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CsOOperatoreSettore> findOperatoreSettoreBySettore(long idSettore) throws Exception {
		Query q = em.createNamedQuery("CsOOperatoreSettore.findOperatoreSettoreBySettore").setParameter("idSettore", idSettore);
		List results = q.getResultList();
		return results;
	}
	
	//frida
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CsOOperatoreAnagrafica> findAllOperatoreAnagrafica() throws Exception {
		Query q = em.createNamedQuery("CsOOperatoreAnagrafica.findAllOperatoreAnagrafica");
		List results = q.getResultList();
		return results;
	}
		
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public CsOZonaSoc findZonaSocAbilitata() {
		try{
		Query q = em.createNamedQuery("CsOZonaSoc.findAllAbil");
		List results = q.getResultList();
		if(results!=null && results.size()>0)
			return (CsOZonaSoc)results.get(0);
		}catch(Exception e){
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CsOSettoreBASIC> findSettoreBASICByOrganizzazione(long idOrganizzazione) throws Exception {
		Query q = em.createNamedQuery("CsOSettore.findSettoreBASICByOrganizzazione").setParameter("idOrganizzazione", idOrganizzazione);
		List results = q.getResultList();
		return results;
	}
	
	//SISO-812
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CsOSettoreBASIC> findSettoreBASICSecondoLivelloByOrganizzazione(long idOrganizzazione) throws Exception {
		Query q = em.createNamedQuery("CsOSettore.findSettoreBASICSecondoLivelloByOrganizzazione").setParameter("idOrganizzazione", idOrganizzazione);
		List results = q.getResultList();
		return results;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CsOSettoreBASIC> findSettoreBASICSenzaSecondoLivelloByOrganizzazione(long idOrganizzazione) throws Exception {
		Query q = em.createNamedQuery("CsOSettore.findSettoreBASICSenzaSecondoLivelloByOrganizzazione").setParameter("idOrganizzazione", idOrganizzazione);
		List results = q.getResultList();
		return results;
	}
	
	public CsOOperatoreTipoOperatore findCsOOperatoreTipoOpById(Long obj) {
		return em.find(CsOOperatoreTipoOperatore.class, obj);
	}
	
	public CsOOperatore insertOrUpdateOperatore(CsOOperatore op) throws Exception {
		op = em.merge(op);
		return op;
	}
	
	public CsOOperatoreTipoOperatore insertOrUpdateTipoOperatore(CsOOperatoreTipoOperatore tipoOp) throws Exception {
		tipoOp = em.merge(tipoOp);
		return tipoOp;
	}
	
	public CsOOperatoreSettore insertOrUpdateOperatoreSettore(CsOOperatoreSettore opSet) throws Exception {
		opSet = em.merge(opSet);
		return opSet;
	}
	
	public void deleteTipoOperatore(CsOOperatoreTipoOperatore tipoOp) throws Exception {
		Query q = em.createNamedQuery("CsOOperatoreTipoOperatore.eliminaTipoOperatoreById");
		q.setParameter("id", tipoOp.getId());
		q.executeUpdate();
	}
	
	public void deleteOperatoreSettore(CsOOperatoreSettore opSet) throws Exception {
		try{
		
			Query q = em.createNamedQuery("CsACasoOpeTipoOpe.eliminaCasoOpeTipoOpeByIdOperatoreSettore");
			q.setParameter("idOpSet", opSet.getId());
			q.executeUpdate();
			
			q = em.createNamedQuery("CsOOperatoreTipoOperatore.eliminaTipoOperatoreByIdOperatoreSettore");
			q.setParameter("idOpSet", opSet.getId());
			q.executeUpdate();
			
			deleteConfigAlertOpSettore(opSet.getId());
			
			q = em.createNamedQuery("CsOOperatoreSettore.eliminaOperatoreSettoreById");
			q.setParameter("id", opSet.getId());
			q.executeUpdate();
			
		}catch(Exception e){
			logger.error(e);
			throw(e);
		}
	}

	public void resetFirmaTuttiRespSettore(String amGroup, Long settoreId) {
		String s = "UPDATE CS_O_OPERATORE_SETTORE SET FIRMA=0 WHERE AM_GROUP like '%"+amGroup+"%' and SETTORE_ID=?";
		Query q = em.createNativeQuery(s);
		q.setParameter(1, settoreId);
		q.executeUpdate();
	}

	public CsOSettoreBASIC findSettoreBASICById(Long idSettore) {
			CsOSettoreBASIC settore = em.find(CsOSettoreBASIC.class, idSettore);
			return settore;
	}

	public void salvaConfigurazioneAlert(CsOOpsettoreAlertConfig c) {
		em.merge(c);
	}

	public void deleteConfigAlertOpSettore(Long opSettoreId) throws Exception {
		try{
			
			Query q = em.createNamedQuery("CsOOpsettoreAlertConfig.eliminaByIdOperatoreSettore");
			q.setParameter("idOpSettore", opSettoreId);
			q.executeUpdate();
			
		}catch(Exception e){
			logger.error(e);
			throw(e);
		}
		
	}
	public List<CsOOperatoreTipoOperatore> findTipiOperatore(Long idOperatoreSettore) throws Exception {
		try {
			TypedQuery<CsOOperatoreTipoOperatore> q = em.createNamedQuery("CsOOperatoreSettore.findTipiOperatoreByOperatoreSettore", CsOOperatoreTipoOperatore.class);
			q.setParameter("idOperatoreSettore", idOperatoreSettore);
			return q.getResultList();
		} catch (Exception e) {
			logger.error(e);
			throw (e);
		}
	}

	public String findLabelRuolo(String obj) {
		CsODecodificaRuoli d = em.find(CsODecodificaRuoli.class, obj);
		return d!=null ? d.getLabelRuolo() : null;
	}

	public LinkedHashMap<String, String> getDecodificaRuoli(String belfiore) {
		LinkedHashMap<String, String> hmRuoli = new LinkedHashMap<String, String>();
		Query q = em.createNamedQuery("CsODecodificaRuoli.findAll");
		List<CsODecodificaRuoli> ruoli = (List<CsODecodificaRuoli>) q.getResultList();
		if(belfiore!=null && !ruoli.isEmpty()){
			for(CsODecodificaRuoli ruolo : ruoli)
				hmRuoli.put(ruolo.getPrefissoGruppo() + belfiore, ruolo.getLabelRuolo());
		}
		
		return hmRuoli;
	}

		
	public List<CsAmAnagraficaOperatore> searchUtentiAmPerCs(OperatoriSearchCriteria cet, boolean count) {
		try{
			
			String s = "SELECT c FROM CsAmAnagraficaOperatore c WHERE 1=1 ";
			
			if(!StringUtils.isBlank(cet.getCognome())) 
				s+= "AND UPPER(c.cognome) LIKE '%"+cet.getCognome().toUpperCase()+"%'";
			if(!StringUtils.isBlank(cet.getNome())) 
				s+= "AND UPPER(c.nome) LIKE '%"+cet.getNome().toUpperCase()+"%'";
			if(!StringUtils.isBlank(cet.getCodiceFiscale())) 
				s+= "AND UPPER(c.codiceFiscale) LIKE '%"+cet.getCodiceFiscale().toUpperCase()+"%'";
			if(!StringUtils.isBlank(cet.getUsername())) 
				s+= "AND UPPER(c.username) LIKE '%"+cet.getUsername().toUpperCase()+"%'";
			if(cet.getAbilitato()!=null){ 
				s+= "AND (c.operatore IS "+(cet.getAbilitato().booleanValue() ? " NOT NULL AND " : " NULL OR ");
				s+= "c.operatore.abilitato="+cet.getAbilitato().booleanValue();
				s+= ")";
			}if(cet.getEnti()!=null && !cet.getEnti().isEmpty()){
				s+="AND (";
				String entis = "";
				for(String ente : cet.getEnti())
					entis+=" OR c.enti like '%"+ente+"%'";
				entis = entis.replaceFirst("OR", "");
				s+=entis+") ";
			}
				   s+= "ORDER BY cognome, nome ";
			
			Query q = em.createQuery(s);
			
			if(!count){
				q.setFirstResult(cet.getFirst());
				q.setMaxResults(cet.getPageSize());
			}
			
			return q.getResultList();
		} catch (Exception e) {
			logger.error(e);
		}
		return null;
	
	}
	
}
