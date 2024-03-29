package it.webred.cs.csa.ejb.dao;

import it.webred.cs.csa.ejb.CarSocialeBaseDAO;
import it.webred.cs.csa.ejb.dto.listaCasi.UnitaOrganizzativaDTO;
import it.webred.cs.data.model.CsACaso;
import it.webred.cs.data.model.CsACasoAccessoFascicolo;
import it.webred.cs.data.model.CsACasoOpeTipoOpe;
import it.webred.cs.data.model.CsACasoOpeTipoOpe2;
import it.webred.cs.data.model.CsOOperatoreBASIC;
import it.webred.cs.data.model.CsOOperatoreSettore;
import it.webred.cs.data.model.CsOOperatoreTipoOperatore;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.view.VSsSchedeUdcDiff;
import it.webred.ct.support.datarouter.CeTBaseObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.Query;

@Named
public class CasoDAO extends CarSocialeBaseDAO implements Serializable {

	private static final long serialVersionUID = 1L;


	public CsACaso inizializzaNuovoCaso(String userId) {
		
		CsACaso newCaso = new CsACaso();
		
		newCaso.setUserIns(userId);
		newCaso.setDtIns(new Date());
		newCaso.setUsrMod(null);
		newCaso.setDtMod(null);
		
		setIdentificativoCaso(newCaso);
		
		return newCaso;
	}
	
	public CsACaso findCasoById(Long idCaso){
		CsACaso caso = null;
		if(idCaso!=null && idCaso>0)
			caso = em.find(CsACaso.class, idCaso);
		return caso;
	}
	
	public void setIdentificativoCaso(CsACaso caso){
		if(caso.getIdentificativo()==null || caso.getIdentificativo()==0){
			String sql =" SELECT SQ_CODICE_CS.nextval FROM DUAL";
			Query q = em.createNativeQuery(sql);
			Long ide = ((BigDecimal)q.getSingleResult()).longValue();
			logger.debug("Assegnazione identificativo "+ide);
			caso.setIdentificativo(ide);
		}
	}

	public void updateCaso(CsACaso caso){
		setIdentificativoCaso(caso);
		em.merge(caso);
		
	}

	public CsOOperatoreTipoOperatore findOperatoreTipoOperatoreByOpSettore(long idOperatore, long idSettore){
		Query q = em.createNamedQuery("CsOOperatoreTipoOperatore.findOpTipoOpByOpSettore");
		q.setParameter("operatoreId", idOperatore);
		q.setParameter("settoreId", idSettore);
		List<CsOOperatoreTipoOperatore> list = q.getResultList();
		if(!list.isEmpty())
			return list.get(0);
		return null;
	}
	
	public CsACasoOpeTipoOpe findCasoOpeResponsabile(long idCaso) {
		
		Query q = em.createNamedQuery("CsACasoOpeTipoOpe.findResponsabileByCaso");
		q.setParameter("casoId", idCaso);
		List<CsACasoOpeTipoOpe> list = (List<CsACasoOpeTipoOpe>) q.getResultList();
		if(!list.isEmpty())
			return list.get(0);
		return null;
		
	}
	
	public CsOOperatoreSettore findOpSettoreResponsabileCaso(long idCaso) {
		
		Query q = em.createNamedQuery("CsACasoOpeTipoOpe.findOpSettoreResponsabileByCaso");
		q.setParameter("casoId", idCaso);
		List<CsOOperatoreSettore> list = (List<CsOOperatoreSettore>) q.getResultList();
		if(!list.isEmpty())
			return list.get(0);
		return null;
		
	}
	
	
	
	public CsOOperatoreBASIC findResponsabileBASIC(Long idCaso) {
		if(idCaso!=null){
			Query q = em.createNamedQuery("CsOOperatoreBASIC.findResponsabileBASICByCaso");
			q.setParameter("casoId", idCaso);
			List<CsOOperatoreBASIC> list = (List<CsOOperatoreBASIC>) q.getResultList();
			if(!list.isEmpty())
				return list.get(0);
		}
		return null;
		
	}
	
	
	public List<CsACaso> findCasoByCognomeAndNome(String cognome, String nome){
		String query = "from CsACaso c where upper(c.csASoggetto.csAAnagrafica.cognome) = :cognome and upper(c.csASoggetto.csAAnagrafica.nome) = :nome";
		Query q = em.createQuery(query);
		q.setParameter("cognome", cognome.toUpperCase());
		q.setParameter("nome", nome.toUpperCase());
		return q.getResultList();
	}
	
	//nuovo operatore tipo 2
	public void salvaOperatoreTipoOp2Caso(CsACasoOpeTipoOpe2 oper){
		em.merge(oper);
	}
	
	public void eliminaOperatoreTipoOp2ByCasoId(Long obj) {
		Query q = em.createNamedQuery("CsACasoOpeTipoOpe2.deleteOpeByCasoId");
		q.setParameter("casoId", obj);
		q.executeUpdate();
		
	}
	//fine
	
	public void salvaOperatoreTipoOpCaso(CsACasoOpeTipoOpe oper){
		em.persist(oper);
	}
	
	//SISO-812
	public void salvaAccessoFascicolo(CsACasoAccessoFascicolo af){
		em.persist(af);
	}
	
	public void updateOperatoreTipoOpCaso(CsACasoOpeTipoOpe oper){
		em.merge(oper);
	}

	public void eliminaOperatoreTipoOpByCasoId(Long obj) {
		Query q = em.createNamedQuery("CsACasoOpeTipoOpe.deleteByCasoId");
		q.setParameter("casoId", obj);
		q.executeUpdate();
		
	}
	
	//SISO-812
	public void eliminaAccessoFascicoloByCasoId(Long id){
		Query q = em.createNamedQuery("CsACasoAccessoFascicolo.deleteByCasoId");
		q.setParameter("casoId", id);
		
		q.executeUpdate();
	}

	public List<CsACasoOpeTipoOpe> getListaOperatoreTipoOpByCasoId(Object obj) {
		Query q = em.createNamedQuery("CsACasoOpeTipoOpe.findByCasoId");
		q.setParameter("casoId", obj);
		return q.getResultList();
	}
	

	public List<CsACasoAccessoFascicolo> getListaAccessoFascicoloByCasoId(Object obj){
		Query q = em.createNamedQuery("CsACasoAccessoFascicolo.findByCasoId");
		q.setParameter("casoId", obj);
		return q.getResultList();
	}
	
	public List<UnitaOrganizzativaDTO> getListaUnitaOrganizzative(Long casoId){
		List<UnitaOrganizzativaDTO> out = new ArrayList<UnitaOrganizzativaDTO>();
		List<CsACasoAccessoFascicolo> in = getListaAccessoFascicoloByCasoId(casoId);
		for(CsACasoAccessoFascicolo a : in){
			UnitaOrganizzativaDTO uo = new UnitaOrganizzativaDTO();
			if(a.getCsOOrganizzazione()!=null)
				uo.setOrganizzazione(a.getCsOOrganizzazione().getNome());
			if(a.getCsOSettore()!=null){
				uo.setSettore(a.getCsOSettore().getNome());
				if(a.getCsOSettore().getCsTbSecondoLivello()!=null)
					uo.setSecondoLivello(a.getCsOSettore().getCsTbSecondoLivello().getDescrizione());
			}uo.setDataInizioApp(a.getDataInizioApp());
			uo.setDataFineApp(a.getDataFineApp());
			out.add(uo);
		}
		return out;
	}
	
	//SISO-812
	public List<CsACasoAccessoFascicolo>findAccessoFascicoloAttuali(Long idOrganizzazione, Long idSettore)
	{
		Query q = em.createNamedQuery("CsACasoAccessoFascicolo.findAttualiBySettoreOrganizzazione");
		q.setParameter("idOrganizzazione", idOrganizzazione);
		q.setParameter("idSettore", idSettore);
		return q.getResultList();
	}
	
	public List<CsACasoAccessoFascicolo> findAccessoFascicoloAttuali(Long idCaso, Long idOrganizzazione, Long idSettore){
		Query q = em.createNamedQuery("CsACasoAccessoFascicolo.findAttualiByCasoSettoreOrganizzazione");
		q.setParameter("idCaso", idCaso);
		q.setParameter("idOrganizzazione", idOrganizzazione);
		q.setParameter("idSettore", idSettore);
		return q.getResultList();
	}
	
	public List<CsACasoOpeTipoOpe2> getListaOperatoreTipoOp2ByCasoId(Object obj) {
		Query q = em.createNamedQuery("CsACasoOpeTipoOpe2.findOpeByCasoId");
		q.setParameter("casoId", obj);
		return q.getResultList();
	}
	
	
	public List<CsACasoOpeTipoOpe> findCasoOpeTipoOpeByCasoOpSettore(long idCaso, long idOpSettore){
		Query q = em.createNamedQuery("CsACasoOpeTipoOpe.findCasoOpeTipoOpeByCasoOpSettore");
		q.setParameter("casoId", idCaso);
		q.setParameter("opSettoreId", idOpSettore);
		return q.getResultList();
	}
	
	public Integer countCasiByResponsabileCatSociale(long idOperatore, long idCatSociale, boolean equals, String codRouting){
		String sql ="   select count(cs.caso_id) carico "+
                "from Cs_A_Caso_Ope_Tipo_Ope cs, Cs_A_Soggetto_Categoria_Soc scs, Cs_It_Step it,  "+
                "Cs_O_operatore_tipo_operatore tipoOp, Cs_O_operatore_Settore opSett, Cs_a_soggetto s, "+
                "Cs_o_settore sett, Cs_o_organizzazione o "+
                "where CS.OPERATORE_TIPO_OPERATORE_ID=tipoOp.id and TIPOOP.OPERATORE_SETTORE_ID=opSett.id  "+
                "and cs.flag_Responsabile = 1 "+
                "AND SCS.PREVALENTE = 1 "+
                "and cs.data_Fine_App > CURRENT_DATE "+
                "and scs.data_Fine_App > CURRENT_DATE "+
                "and CS.CASO_ID=s.caso_id and S.ANAGRAFICA_ID=SCS.SOGGETTO_ANAGRAFICA_ID "+
                "and  IT.ID = (SELECT MAX (it2.id) "+
                "      FROM CS_IT_STEP it2 "+
                "     WHERE IT2.CASO_ID = cs.caso_id) "+
                "and IT.CFG_IT_STATO_ID<>2 "+
                "and OPSETT.SETTORE_ID =sett.id and SETT.ORGANIZZAZIONE_ID =o.id "+
                "and OPSETT.OPERATORE_ID= :operatoreId "+
                "and SCS.CATEGORIA_SOCIALE_ID= :catSocialeId "+
				"and O.COD_ROUTING " + (equals ? "=" : "<>" ) + ":codRouting ";
		
		Query q = em.createNativeQuery(sql);
		q.setParameter("operatoreId", idOperatore);
		q.setParameter("catSocialeId", idCatSociale);
		q.setParameter("codRouting", codRouting);
		BigDecimal c = (BigDecimal) q.getSingleResult();
		return c.intValue();
	}

	public Integer countDatiStorici(Integer tipoDiario, Long casoId) {
		String sql = "select count(*) from cs_d_diario where tipo_diario_id= :tipoDiarioId and caso_id= :casoId";
		logger.debug("countDatiStorici SQL["+sql+"]");
		Query q = em.createNativeQuery(sql);
		q.setParameter("tipoDiarioId", tipoDiario);
		q.setParameter("casoId", casoId);
		BigDecimal count = (BigDecimal)q.getSingleResult();
		return count.intValue();
	}
		
	public List<VSsSchedeUdcDiff> controllaModificheSchedaCompletaUDC(){
		Query q = em.createNamedQuery("VSsSchedeUdcDiff.verificaAggiornamenti");
		List<VSsSchedeUdcDiff> result = q.getResultList();
		return result;
	}

}
