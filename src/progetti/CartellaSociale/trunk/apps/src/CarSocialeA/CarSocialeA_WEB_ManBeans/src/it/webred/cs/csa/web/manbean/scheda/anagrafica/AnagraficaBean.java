package it.webred.cs.csa.web.manbean.scheda.anagrafica;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.context.RequestContext;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.webred.cs.csa.ejb.client.AccessTableAnagraficaLogSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableCasoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableIndirizzoSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableIterStepSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableNazioniSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTablePersonaCiviciSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSoggettoSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.ejb.dto.cartella.DatiAggCasoGitDTO;
import it.webred.cs.csa.ejb.dto.cartella.DatiAnagraficaCasoDTO;
import it.webred.cs.csa.web.manbean.scheda.SchedaBean;
import it.webred.cs.csa.web.manbean.scheda.SchedaUtils;
import it.webred.cs.csa.web.manbean.scheda.parenti.ComuneResidenzaBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.TipoAggiornamentoAnagrafica;
import it.webred.cs.data.DataModelCostanti.TipoRicercaSoggetto;
import it.webred.cs.data.model.CsAAnaIndirizzo;
import it.webred.cs.data.model.CsAAnagrafica;
import it.webred.cs.data.model.CsAAnagraficaLog;
import it.webred.cs.data.model.CsAComponenteAnagCasoGit;
import it.webred.cs.data.model.CsAIndirizzo;
import it.webred.cs.data.model.CsASoggettoLAZY;
import it.webred.cs.data.model.CsASoggettoMedico;
import it.webred.cs.data.model.CsASoggettoStatoCivile;
import it.webred.cs.data.model.CsASoggettoStatus;
import it.webred.cs.data.model.CsCCategoriaSocialeBASIC;
import it.webred.cs.data.model.CsCMedico;
import it.webred.cs.data.model.CsMobileStaging;
import it.webred.cs.data.model.CsTbCittadinanzaAcq;
import it.webred.cs.data.model.CsTbStatoCivile;
import it.webred.cs.jsf.bean.DatiAnaBean;
import it.webred.cs.jsf.bean.ValiditaCompBaseBean;
import it.webred.cs.jsf.interfaces.IDatiAna;
import it.webred.cs.jsf.manbean.ComuneNazioneNascitaMan;
import it.webred.cs.jsf.manbean.ComuneNazioneResidenzaMan;
import it.webred.cs.jsf.manbean.ComuneResidenzaMan;
import it.webred.cs.jsf.manbean.IndirizzoMan;
import it.webred.cs.jsf.manbean.ResidenzaCsaMan;
import it.webred.cs.jsf.manbean.comuneNazione.ComuneNazioneGenericMan;
import it.webred.cs.jsf.manbean.exception.CsUiCompException;
import it.webred.cs.jsf.manbean.superc.ComuneMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.ct.config.model.AmTabNazioni;
import it.webred.ct.support.datarouter.CeTBaseObject;
import it.webred.ejb.utility.ClientUtility;
import it.webred.jsf.bean.ComuneBean;
import it.webred.jsf.bean.SessoBean;
import it.webred.siso.ws.ricerca.dto.FamiliareDettaglio;
import it.webred.siso.ws.ricerca.dto.PersonaDettaglio;
import it.webred.siso.ws.ricerca.dto.RicercaAnagraficaParams;
import it.webred.siso.ws.ricerca.dto.RicercaAnagraficaResult;
import it.webred.ss.data.model.SsScheda;
import it.webred.ss.ejb.dto.report.DatiPrivacyPdfDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;
import javax.naming.NamingException;

import org.primefaces.context.RequestContext;


@ManagedBean
@SessionScoped
public class AnagraficaBean extends SchedaUtils implements IDatiAna {
	
	private String nomeTab = "Anagrafica";
	
	protected AccessTableSoggettoSessionBeanRemote soggettoService = (AccessTableSoggettoSessionBeanRemote) getCarSocialeEjb ("AccessTableSoggettoSessionBean");
	
	protected AccessTableCasoSessionBeanRemote casoService = (AccessTableCasoSessionBeanRemote) getCarSocialeEjb ("AccessTableCasoSessionBean");

	protected AccessTableIndirizzoSessionBeanRemote indirizzoService = (AccessTableIndirizzoSessionBeanRemote) getCarSocialeEjb ("AccessTableIndirizzoSessionBean");

	protected AccessTableIterStepSessionBeanRemote iterStepService = (AccessTableIterStepSessionBeanRemote) getCarSocialeEjb ("AccessTableIterStepSessionBean");
	
	protected AccessTableConfigurazioneSessionBeanRemote confService = (AccessTableConfigurazioneSessionBeanRemote) getCarSocialeEjb ("AccessTableConfigurazioneSessionBean");
	
	protected AccessTableAnagraficaLogSessionBeanRemote csAnagraficaLogService = (AccessTableAnagraficaLogSessionBeanRemote) getCarSocialeEjb ("AccessTableAnagraficaLogSessionBean");
   	
	protected Long soggettoId = (Long) getSession().getAttribute("soggettoId");
	
	private DatiAnaBean datiAnaBean;
	
	private ComuneNazioneNascitaMan comuneNazioneNascitaMan;

	private ResidenzaCsaMan residenzaCsaMan;
	
	private MediciGestBean mediciGestBean;
	
	private StatusGestBean statusGestBean;
	
	private StatoCivileGestBean statoCivileGestBean;
	
	private SoggCatSocialeBean soggCatSocialeBean;
	
	private CsASoggettoLAZY soggetto;
	
	private List<SelectItem> lstCittadinanze;
	private List<SelectItem> lstCittadinanzeAcq;
	private List<CsAAnagraficaLog> lstStoricoForzature;
	private boolean visDataAperturaFascicoloFam;
	private String accessoModifica;
	//private HashMap<String, String> aggiornamentiAnagraficiCasoGit;
	private CsAComponenteAnagCasoGit  anagraficaAggiornata;
	private List<DatiAggCasoGitDTO> lstAggiornamentiAnagraficaCasoGit;
	
	
	public void initialize(Long sId) {                                           
		
        logger.debug("*** INIZIO AnagraficaBean.initialize ....");
		visDataAperturaFascicoloFam = CsUiCompBaseBean.isVisDataAperturaFascFam();
		
		soggettoId = sId;
		
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(soggettoId);

		datiAnaBean = new DatiAnaBean();
		comuneNazioneNascitaMan = new ComuneNazioneNascitaMan();
		residenzaCsaMan = new ResidenzaCsaMan();
		mediciGestBean = new MediciGestBean();
		statusGestBean = new StatusGestBean();
		statoCivileGestBean = new StatoCivileGestBean();
		if(soggCatSocialeBean == null)
			soggCatSocialeBean = new SoggCatSocialeBean();
		
		soggCatSocialeBean.setRichiediCategoriaSociale(false);
		lstStoricoForzature = new ArrayList<CsAAnagraficaLog>();
		anagraficaAggiornata = null;
		lstAggiornamentiAnagraficaCasoGit = new ArrayList<DatiAggCasoGitDTO>();
		if(soggettoId != null){
			
			//carico dati anagrafici
			soggetto = soggettoService.getSoggettoById(dto);
			CsAAnagrafica csAna = soggetto.getCsAAnagrafica();
			datiAnaBean.setCognome(csAna.getCognome());
			datiAnaBean.setNome(csAna.getNome());
			datiAnaBean.setDataNascita(csAna.getDataNascita());
			datiAnaBean.setCodiceFiscale(csAna.getCf());
			datiAnaBean.setIdOrigWs(csAna.getIdOrigWs());
			if(csAna.getComuneNascitaCod() != null) {
				ComuneBean comuneBean = new ComuneBean(csAna.getComuneNascitaCod(), csAna.getComuneNascitaDes(), csAna.getProvNascitaCod());
				comuneNazioneNascitaMan.getComuneNascitaMan().setComune(comuneBean);
			}
			if(csAna.getStatoNascitaCod() != null) {
				AmTabNazioni nazione = CsUiCompBaseBean.getNazioneByIstat(csAna.getStatoNascitaCod(), csAna.getStatoNascitaDes());
				comuneNazioneNascitaMan.getNazioneNascitaMan().setNazione(nazione);
				comuneNazioneNascitaMan.setNazioneValue();
			}
			SessoBean sb = new SessoBean(csAna.getSesso());
			datiAnaBean.setDatiSesso(sb);
			datiAnaBean.setCittadinanza(csAna.getCittadinanza());
			datiAnaBean.setCittadinanzaAcq(csAna.getCittadinanzaAcq());
			datiAnaBean.setCittadinanza2(csAna.getCittadinanza2());
			datiAnaBean.setTelefono(csAna.getTel());
			datiAnaBean.setTitTelefono(csAna.getTitolareTelefono());
			
			datiAnaBean.setCellulare(csAna.getCell());
			datiAnaBean.setTitCellulare(csAna.getTitolareCellulare());
			
			datiAnaBean.setEmail(csAna.getEmail());
			datiAnaBean.setTitEmail(csAna.getTitolareEmail());
			datiAnaBean.setDataAperturaFascFam(csAna.getDataAperturaFascFam());
			
			//carico indirizzi
			List<CsAIndirizzo> listaIndirizzi = indirizzoService.getIndirizziBySoggetto(dto);
			residenzaCsaMan.setLstIndirizziOld(listaIndirizzi);
			residenzaCsaMan.setLstIndirizzi(residenzaCsaMan.resetListeIndirizzi(listaIndirizzi));
			//verificaResidenzaDaAnagrafe();
			
			//carico medici
			List<ValiditaCompBaseBean> lstMedici = new ArrayList<ValiditaCompBaseBean>();
			List<CsASoggettoMedico> lstSoggMedico = soggettoService.getSoggettoMedicoBySoggetto(dto);
			for(CsASoggettoMedico csSoggComp: lstSoggMedico) {
				ValiditaCompBaseBean comp = new ValiditaCompBaseBean();
				comp.setDataFine(csSoggComp.getId().getDataFineApp());
				comp.setDataInizio(csSoggComp.getDataInizioApp());
				String denominazione = (csSoggComp.getCsCMedico().getCognome() == null ? "" : csSoggComp.getCsCMedico().getCognome()).trim();
				String nome = (csSoggComp.getCsCMedico().getNome() == null ? "" : csSoggComp.getCsCMedico().getNome()).trim();
				if (!nome.equals("")) {
					if (!denominazione.equals("")) {
						denominazione += " ";
					}
					denominazione += nome;
				}	
				comp.setDescrizione(denominazione);
				comp.setId(csSoggComp.getId().getMedicoId());
				mediciGestBean.addDettagliSelezione(comp);
				lstMedici.add(comp);
			}
			mediciGestBean.setLstComponents(lstMedici);		
			mediciGestBean.setLstComponentsActive(mediciGestBean.getActiveList());
			
			//carico status
			List<ValiditaCompBaseBean> lstStatus = new ArrayList<ValiditaCompBaseBean>();
			List<CsASoggettoStatus> lstSoggStatus = soggettoService.getSoggettoStatusBySoggetto(dto);
			for(CsASoggettoStatus csSoggComp: lstSoggStatus) {
				ValiditaCompBaseBean comp = new ValiditaCompBaseBean();
				comp.setDataFine(csSoggComp.getId().getDataFineApp());
				comp.setDataInizio(csSoggComp.getDataInizioApp());
				comp.setDescrizione(csSoggComp.getCsTbStatus().getDescrizione());
				comp.setId(csSoggComp.getId().getStatusId());
				lstStatus.add(comp);
			}
			statusGestBean.setLstComponents(lstStatus);
			statusGestBean.setLstComponentsActive(statusGestBean.getActiveList());
			
			//carico stato civile
			List<ValiditaCompBaseBean> lstStatoCivile = new ArrayList<ValiditaCompBaseBean>();
			List<CsASoggettoStatoCivile> lstSoggStatoCiv = soggettoService.getSoggettoStatoCivileBySoggetto(dto);
			for(CsASoggettoStatoCivile csSoggComp: lstSoggStatoCiv) {
				ValiditaCompBaseBean comp = new ValiditaCompBaseBean();
				comp.setDataFine(csSoggComp.getId().getDataFineApp());
				comp.setDataInizio(csSoggComp.getDataInizioApp());
				comp.setDescrizione(csSoggComp.getCsTbStatoCivile().getDescrizione());
				comp.setId(new Long(csSoggComp.getId().getStatoCivileCod()));
				lstStatoCivile.add(comp);
			}
			statoCivileGestBean.setLstComponents(lstStatoCivile);
			statoCivileGestBean.setLstComponentsActive(statoCivileGestBean.getActiveList());
			
			List<CsCCategoriaSocialeBASIC> lstCatSociali = soggettoService.getCatSocAttualiBySoggetto(dto);
			if(lstCatSociali.isEmpty()){
				soggCatSocialeBean.setDisableEsci(true);
				soggCatSocialeBean.setRichiediCategoriaSociale(true);
			}
			
			//carico lo storico delle variazioni anagrafiche
			this.loadListaForzatureAnagrafiche();
			this.setAccessoModifica("ACCESSO");
			
			//SISO-1127 Inizio
			verificaAggiornamentiSoggettoCaso();
			//SISO-1127 Fine
		} else {
			//resetto la gestione soggetto/cat sociale solo per una nuova cartella
			soggCatSocialeBean.setDisableEsci(true);
		}
		
		logger.debug("*** FINE AnagraficaBean.initialize ....");

		
	}
	//SISO-1127 Inizio
	
	private void verificaAggiornamentiSoggettoCaso() {
	
		//Caricamento dati variati rispetto a quelli presenti in cartella (se presenti)
		BaseDTO bDto = new BaseDTO();
	 	fillEnte(bDto);
	 	bDto.setObj(this.soggettoId);
	 	bDto.setObj2(this.getSoggetto().getDtMod() == null ? this.getSoggetto().getDtIns() : this.getSoggetto().getDtMod());
    	anagraficaAggiornata = soggettoService.getAnagraficaCasoGitAggiornataBySoggettoId(bDto);
    	this.estraiAggiornamentoAnag();
		 
	}
	private LinkedHashMap<String,String> decodeAggiornamentoAnagrafica(String jsonEncode) {
		ObjectMapper mapper = new ObjectMapper();
		TypeReference<LinkedHashMap<String, String>> typeRef = new TypeReference<LinkedHashMap <String, String>>() {};
		try {
			return mapper.readValue(jsonEncode, typeRef);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
 


	public CsAComponenteAnagCasoGit getAnagraficaAggiornata() {
		return anagraficaAggiornata;
	}


	public void setAnagraficaAggiornata(CsAComponenteAnagCasoGit anagraficaAggiornata) {
		this.anagraficaAggiornata = anagraficaAggiornata;
	}

	private DatiAggCasoGitDTO getGruppoSegnalazioni(LinkedHashMap<String, String> arraySegnalazioni, String filtro ){
		DatiAggCasoGitDTO  aggCasoGit = new DatiAggCasoGitDTO();
		
		LinkedHashMap<String, String> gruppoSegnalazioni = new LinkedHashMap<String, String>();
		if(arraySegnalazioni == null ||arraySegnalazioni.size() == 0)
			return null;
		for (Entry<String, String> entry : arraySegnalazioni.entrySet()) {
			
			if(entry.getKey().indexOf(filtro) > -1) {
				gruppoSegnalazioni.put(entry.getKey().replaceAll(filtro,""), entry.getValue());
			}
		}
		if(gruppoSegnalazioni.size() > 0) {
			aggCasoGit.setElementiVariati(gruppoSegnalazioni);
			aggCasoGit.setTipologiaVariazione(filtro);
			aggCasoGit.setElementiOriginali(getElementiOriginali(filtro));
			
		}
		else if(gruppoSegnalazioni.size() == 0 )
			return null;
		return aggCasoGit;
	}
	private LinkedHashMap<String, String> getElementiOriginali(String filtro ){
		BaseDTO bDto = new BaseDTO();
	 	fillEnte(bDto);
	 	bDto.setObj(this.soggettoId);
	 	LinkedHashMap<String, String> gruppoElementiOriginali = new LinkedHashMap<String, String>();
		CsAAnaIndirizzo indirizzoTmp = null;
		if(filtro.equals(TipoAggiornamentoAnagrafica.DOMICILIO)) {
			indirizzoTmp =  indirizzoService.getIndirizzoDomicilio(bDto);
			if(indirizzoTmp == null)
				gruppoElementiOriginali.put(TipoAggiornamentoAnagrafica.DOMICILIO, TipoAggiornamentoAnagrafica.ASSENTE);
			else {
				gruppoElementiOriginali.put(TipoAggiornamentoAnagrafica.CODICE_COMUNE_DOMICILIO.replaceAll(filtro,""), indirizzoTmp.getComCod());
				gruppoElementiOriginali.put(TipoAggiornamentoAnagrafica.COMUNE_DOMICILIO.replaceAll(filtro,""), indirizzoTmp.getComDes());
				gruppoElementiOriginali.put(TipoAggiornamentoAnagrafica.INDIRIZZO_DOMICILIO.replaceAll(filtro,""), indirizzoTmp.getIndirizzo());
				gruppoElementiOriginali.put(TipoAggiornamentoAnagrafica.NUM_CIVICO_DOMICILIO.replaceAll(filtro,""), indirizzoTmp.getCivicoNumero());
				gruppoElementiOriginali.put(TipoAggiornamentoAnagrafica.PROVINCIA_DI_DOMICILIO.replaceAll(filtro,""), indirizzoTmp.getProv());
			}
		}else if(filtro.equals(TipoAggiornamentoAnagrafica.RESIDENZA)) {
				indirizzoTmp =  indirizzoService.getIndirizzoResidenza(bDto);
				if(indirizzoTmp == null)
					gruppoElementiOriginali.put(TipoAggiornamentoAnagrafica.RESIDENZA, TipoAggiornamentoAnagrafica.ASSENTE);
				else {
					gruppoElementiOriginali.put(TipoAggiornamentoAnagrafica.COD_COM_RESIDENZA.replaceAll(filtro,""), indirizzoTmp.getComCod());
					gruppoElementiOriginali.put(TipoAggiornamentoAnagrafica.COMUNE_DI_RESIDENZA.replaceAll(filtro,""), indirizzoTmp.getComDes());
					gruppoElementiOriginali.put(TipoAggiornamentoAnagrafica.INDIRIZZO_RESIDENZA.replaceAll(filtro,""), indirizzoTmp.getIndirizzo());
					gruppoElementiOriginali.put(TipoAggiornamentoAnagrafica.NUM_CIVICO_RESIDENZA.replaceAll(filtro,""), indirizzoTmp.getCivicoNumero());
					gruppoElementiOriginali.put(TipoAggiornamentoAnagrafica.PROVINCIA_DI_RESIDENZA.replaceAll(filtro,""), indirizzoTmp.getProv());
				}
			}
		else if(filtro.equals(TipoAggiornamentoAnagrafica.MEDICO_REVOCATO) || filtro.equals(TipoAggiornamentoAnagrafica.NUOVO_MEDICO)) {
			ValiditaCompBaseBean medico = this.mediciGestBean.getComponenteAttivo();
			boolean medicoAttivo = medico!=null;
			if(medicoAttivo){
					String denom =  medico.getDescrizione();
					String dtInizio = medico.getDataInizio()!=null ? ddMMyyyy.format(medico.getDataInizio()) : "";
					gruppoElementiOriginali.put(TipoAggiornamentoAnagrafica.DENOMINAZIONE_MEDICO_REVOCATO.replaceAll(filtro,""), denom);
					gruppoElementiOriginali.put(TipoAggiornamentoAnagrafica.DATA_SCELTA_MEDICO_REVOCATO.replaceAll(filtro,""), dtInizio);
					//gruppoElementiOriginali.put(TipoAggiornamentoAnagrafica.DATA_REVOCA_MEDICO_REVOCATO.replaceAll(filtro,""), dtInizio);
			}else gruppoElementiOriginali.put(TipoAggiornamentoAnagrafica.MEDICO, TipoAggiornamentoAnagrafica.ASSENTE);
		}
		else if(filtro.equals(TipoAggiornamentoAnagrafica.ANAGRAFICA)) {
			List<ValiditaCompBaseBean> listStatoCivile = statoCivileGestBean.getLstComponentsActive();
			for(ValiditaCompBaseBean statoCivile : listStatoCivile) {
				Date dtFin =  statoCivile.getDataFine();
				if(dtFin == null) { //statoCivile attualmente attivo
					String denom =  statoCivile.getDescrizione();
					Date dtInizio = statoCivile.getDataInizio();
					gruppoElementiOriginali.put(TipoAggiornamentoAnagrafica.ANAGRAFICA_STATO_CIVILE.replaceAll(filtro,""), denom);
			 	}
				if(this.datiAnaBean.getCellulare() != null)
					gruppoElementiOriginali.put(TipoAggiornamentoAnagrafica.ANAGRAFICA_TELEFONO.replaceAll(filtro,""), this.datiAnaBean.getCellulare());
			}
		}
			return gruppoElementiOriginali;
	}
	@Override
	public void estraiAggiornamentoAnag() {
		// TODO Auto-generated method stub
		String END_MSG_RESULT = "<font color='black'>, </font>";
		if(this.anagraficaAggiornata == null || this.anagraficaAggiornata.getSegnalazione() == null)
			return;
		else {
			 LinkedHashMap<String,String> hashSegnalazione = decodeAggiornamentoAnagrafica(anagraficaAggiornata.getSegnalazione());
			 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
			 String strDate= formatter.format(this.anagraficaAggiornata.getDtMod());
			 
			String msgResult ="Alla data " + strDate + (hashSegnalazione.size() == 1 ? " risulta variato" : " risultano variate") + " le seguenti informazioni:<br/>";
			
			//			for (Entry<String, String> entry : hashSegnalazione.entrySet()) {
//				  
//				msgResult = msgResult.concat("<font color='black'>" + entry.getKey() + ":</font>" + " " + "<font color='gray'>" +   entry.getValue() + "</font>");
//				msgResult = msgResult.concat(END_MSG_RESULT);
//			}
//			if(msgResult.endsWith(END_MSG_RESULT)) {
//				int lastIndexOf = msgResult.lastIndexOf(END_MSG_RESULT);
//				msgResult = msgResult.substring(0, lastIndexOf);
//			}
			//return msgResult;
			this.lstAggiornamentiAnagraficaCasoGit = new ArrayList<DatiAggCasoGitDTO>();
			lstAggiornamentiAnagraficaCasoGit.add(getGruppoSegnalazioni(hashSegnalazione, TipoAggiornamentoAnagrafica.ANAGRAFICA));
			lstAggiornamentiAnagraficaCasoGit.add(getGruppoSegnalazioni(hashSegnalazione, TipoAggiornamentoAnagrafica.DOMICILIO));
			lstAggiornamentiAnagraficaCasoGit.add(getGruppoSegnalazioni(hashSegnalazione, TipoAggiornamentoAnagrafica.RESIDENZA));
			lstAggiornamentiAnagraficaCasoGit.add(getGruppoSegnalazioni(hashSegnalazione, TipoAggiornamentoAnagrafica.MEDICO_REVOCATO));
			lstAggiornamentiAnagraficaCasoGit.add(getGruppoSegnalazioni(hashSegnalazione, TipoAggiornamentoAnagrafica.NUOVO_MEDICO));
			lstAggiornamentiAnagraficaCasoGit.removeAll(Collections.singleton(null));
			
			 
		}
		verificaResidenzaDaAnagrafe();
			
	}
	
	//SISO-1127 Fine
	
	public List<DatiAggCasoGitDTO> getLstAggiornamentiAnagraficaCasoGit() {
		return lstAggiornamentiAnagraficaCasoGit;
	}


	public void setLstAggiornamentiAnagraficaCasoGit(List<DatiAggCasoGitDTO> lstAggiornamentiAnagraficaCasoGit) {
		this.lstAggiornamentiAnagraficaCasoGit = lstAggiornamentiAnagraficaCasoGit;
	}


	private void verificaResidenzaDaAnagrafe() {
		try{
		if(datiAnaBean.getCodiceFiscale()!=null){
			AccessTablePersonaCiviciSessionBeanRemote personaCiviciService = 
					(AccessTablePersonaCiviciSessionBeanRemote) getCarSocialeEjb("AccessTablePersonaCiviciSessionBean");
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(datiAnaBean.getCodiceFiscale());
			CsAIndirizzo anaRes = personaCiviciService.getIndirizzoResidenzaByCodFisc(dto);
			
			if(anaRes!=null){
				
				CsAIndirizzo residenza = residenzaCsaMan.getIndirizzoResidenzaAttivo();
				if(residenza!=null){
					//Confornto i dati
					if(!residenza.getCsAAnaIndirizzo().getIndirizzo().equalsIgnoreCase(anaRes.getCsAAnaIndirizzo().getIndirizzo()) ||
					   !residenza.getCsAAnaIndirizzo().getCivicoNumero().equalsIgnoreCase(anaRes.getCsAAnaIndirizzo().getCivicoNumero()) ||
					   !residenza.getCsAAnaIndirizzo().getComCod().equalsIgnoreCase(anaRes.getCsAAnaIndirizzo().getComCod()) ||
					   !residenza.getCsAAnaIndirizzo().getComDes().equalsIgnoreCase(anaRes.getCsAAnaIndirizzo().getComDes()) ||
					   !residenza.getCsAAnaIndirizzo().getProv().equalsIgnoreCase(anaRes.getCsAAnaIndirizzo().getProv())
					)
						residenzaCsaMan.setAddressMessage(anaRes.getCsAAnaIndirizzo().getLabelIndirizzoCompleto());
				}else
					residenzaCsaMan.setAddressMessage(anaRes.getCsAAnaIndirizzo().getLabelIndirizzoCompleto());
			}
		}
		}catch(Exception e){
			logger.error("verificaResidenzaDaAnagrafe -"+e.getMessage(), e);
		}
	}
	
	public boolean salva() {
		boolean salvato = true;
		
		try{
			
			if(!validaAnagrafica())
				return false;

			String username = getCurrentUsername();
			boolean nuovoInserimento = soggettoId==null;
			
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);	
			dto.setObj(soggettoId);
			
			//dati anagrafici
			soggetto = new CsASoggettoLAZY();
			if(nuovoInserimento) {
				//IterDTO itDto = new IterDTO();
				//fillEnte(itDto);
				soggetto.setCsAAnagrafica(new CsAAnagrafica());
				//soggetto.setCsACaso(casoService.newCaso(itDto));
			} else 
				soggetto = soggettoService.getSoggettoById(dto);
			CsAAnagrafica csAna = soggetto.getCsAAnagrafica();
			
			
			csAna.setNome(datiAnaBean.getNome());
			csAna.setCognome(datiAnaBean.getCognome());
			csAna.setDataNascita(datiAnaBean.getDataNascita());
			csAna.setCf(datiAnaBean.getCodiceFiscale());
			csAna.setIdOrigWs(datiAnaBean.getIdOrigWs());
			
			if(comuneNazioneNascitaMan.isComune() && 
			   comuneNazioneNascitaMan.getComuneNascitaMan().getComune() != null) {
				csAna.setProvNascitaCod(comuneNazioneNascitaMan.getComuneNascitaMan().getComune().getSiglaProv());
				csAna.setComuneNascitaCod(comuneNazioneNascitaMan.getComuneNascitaMan().getComune().getCodIstatComune());
				csAna.setComuneNascitaDes(comuneNazioneNascitaMan.getComuneNascitaMan().getComune().getDenominazione());
			}
			if(comuneNazioneNascitaMan.isNazione() && 
			   comuneNazioneNascitaMan.getNazioneNascitaMan().getNazione() != null) {
				csAna.setStatoNascitaCod(comuneNazioneNascitaMan.getNazioneNascitaMan().getNazione().getCodIstatNazione());
				csAna.setStatoNascitaDes(comuneNazioneNascitaMan.getNazioneNascitaMan().getNazione().getNazione());
			}
			csAna.setSesso(datiAnaBean.getDatiSesso().getSesso());
			csAna.setCittadinanza(datiAnaBean.getCittadinanza());
			csAna.setCittadinanzaAcq((datiAnaBean.getCittadinanzaAcq()!=null && datiAnaBean.getCittadinanzaAcq()>0) ? datiAnaBean.getCittadinanzaAcq() : null);
			csAna.setCittadinanza2(datiAnaBean.getCittadinanza2());
			csAna.setTel(datiAnaBean.getTelefono());
			csAna.setTitolareTelefono(datiAnaBean.getTitTelefono());
			csAna.setCell(datiAnaBean.getCellulare());
			csAna.setTitolareCellulare(datiAnaBean.getTitCellulare());
			csAna.setEmail(datiAnaBean.getEmail());
			csAna.setTitolareEmail(datiAnaBean.getTitEmail());
			csAna.setDataAperturaFascFam(datiAnaBean.getDataAperturaFascFam());
			
			//SISO-1127
			if(this.anagraficaAggiornata != null && StringUtils.isBlank(csAna.getIdOrigWs()))
				csAna.setIdOrigWs(anagraficaAggiornata.getIdOrigWs());
			
			//SISO-1127 Fine
			DatiAnagraficaCasoDTO casoDTO = new DatiAnagraficaCasoDTO();
			fillEnte(casoDTO);
			casoDTO.setSoggetto(soggetto);
			casoDTO.setNuovoInserimento(nuovoInserimento);
			casoDTO.setUsernameOperatore(username);
			casoDTO.setCurrentOpSettore(getCurrentOpSettore());
			casoDTO.setListaIndirizzi(residenzaCsaMan.getLstIndirizzi());

		
			//medici
			casoDTO.setListaMedici(mediciGestBean.riversaLista());
			
			//status
			casoDTO.setListaStatus(statusGestBean.riversaLista());
			
			//stato civile
			casoDTO.setListaStatoCivile(statoCivileGestBean.riversaLista());
			
			if(nuovoInserimento) {
				//creo la famiglia GIT
				casoDTO.setListaFamiliari(getListaFamilari(soggetto));
			}
			
			
			
			SchedaBean schedaBean = (SchedaBean) getSession().getAttribute("schedaBean");
			SsScheda ssScheda = schedaBean!=null ? schedaBean.getSchedaSegrBean().getSsScheda() : null;
			//aggancio la scheda segretariato se sono partito da quella
			if(ssScheda != null) {
				//verifico che i soggetti corrispondano per CF
				if(schedaBean.getSchedaSegrBean().getSsSchedaSegnalato().getAnagrafica().getCf().equalsIgnoreCase(datiAnaBean.getCodiceFiscale())){
					casoDTO.setSsSchedaId(ssScheda.getId());
					soggCatSocialeBean.setSchedaSegr(ssScheda);
				}
			}
			
			soggettoId = soggettoService.salvaAnagraficaCaso(casoDTO);
			
			if(nuovoInserimento){
				dto.setObj(soggettoId);
				soggetto = soggettoService.getSoggettoById(dto);
			}
			
			List<CsCCategoriaSocialeBASIC> lstCatSociali = soggettoService.getCatSocAttualiBySoggetto(dto);
			if(lstCatSociali.isEmpty())
				soggCatSocialeBean.carica(soggetto);
						
		} catch(Exception e) {
			salvato = false;
			addErrorFromProperties("salva.errorA");
			logger.error(e.getMessage(),e);
		}
		
		return salvato;
	}
	
	
	
	
	/*public boolean salva() {
		boolean salvato = true;
		
		try{
			
			if(!validaAnagrafica())
				return false;

			String username = getCurrentUsername();
			boolean nuovoInserimento = soggettoId==null;
			
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);	
			dto.setObj(soggettoId);
			
			//dati anagrafici
			soggetto = new CsASoggettoLAZY();
			if(nuovoInserimento) {
				IterDTO itDto = new IterDTO();
				fillEnte(itDto);
				soggetto.setCsAAnagrafica(new CsAAnagrafica());
				soggetto.setCsACaso(casoService.newCaso(itDto));
			} else 
				soggetto = soggettoService.getSoggettoById(dto);
			CsAAnagrafica csAna = soggetto.getCsAAnagrafica();
			CsACaso csCaso = soggetto.getCsACaso();
			
			csAna.setNome(datiAnaBean.getNome());
			csAna.setCognome(datiAnaBean.getCognome());
			csAna.setDataNascita(datiAnaBean.getDataNascita());
			csAna.setCf(datiAnaBean.getCodiceFiscale());
			csAna.setIdOrigWs(datiAnaBean.getIdOrigWs());
			
			if(comuneNazioneNascitaMan.isComune() && 
			   comuneNazioneNascitaMan.getComuneNascitaMan().getComune() != null) {
				csAna.setProvNascitaCod(comuneNazioneNascitaMan.getComuneNascitaMan().getComune().getSiglaProv());
				csAna.setComuneNascitaCod(comuneNazioneNascitaMan.getComuneNascitaMan().getComune().getCodIstatComune());
				csAna.setComuneNascitaDes(comuneNazioneNascitaMan.getComuneNascitaMan().getComune().getDenominazione());
			}
			if(comuneNazioneNascitaMan.isNazione() && 
			   comuneNazioneNascitaMan.getNazioneNascitaMan().getNazione() != null) {
				csAna.setStatoNascitaCod(comuneNazioneNascitaMan.getNazioneNascitaMan().getNazione().getCodIstatNazione());
				csAna.setStatoNascitaDes(comuneNazioneNascitaMan.getNazioneNascitaMan().getNazione().getNazione());
			}
			csAna.setSesso(datiAnaBean.getDatiSesso().getSesso());
			csAna.setCittadinanza(datiAnaBean.getCittadinanza());
			csAna.setCittadinanzaAcq((datiAnaBean.getCittadinanzaAcq()!=null && datiAnaBean.getCittadinanzaAcq()>0) ? datiAnaBean.getCittadinanzaAcq() : null);
			csAna.setCittadinanza2(datiAnaBean.getCittadinanza2());
			csAna.setTel(datiAnaBean.getTelefono());
			csAna.setTitolareTelefono(datiAnaBean.getTitTelefono());
			csAna.setCell(datiAnaBean.getCellulare());
			csAna.setTitolareCellulare(datiAnaBean.getTitCellulare());
			csAna.setEmail(datiAnaBean.getEmail());
			csAna.setTitolareEmail(datiAnaBean.getTitEmail());
			csAna.setDataAperturaFascFam(datiAnaBean.getDataAperturaFascFam());
			
			dto.setObj(soggetto);
			dto.setObj2(username);
			
			if(nuovoInserimento) soggetto = soggettoService.saveSoggetto(dto);
			else 							soggettoService.updateSoggetto(dto); 
			
			//iterstep
			if(nuovoInserimento) {
				IterDTO itDto = new IterDTO();
				fillEnte(itDto);
				itDto.setNomeOperatore(username);			
				itDto.setIdCaso(csCaso.getId());
				CsOOperatoreSettore opSettore = getCurrentOpSettore();
				itDto.setIdSettore(opSettore.getCsOSettore().getId());
				itDto.setAlertUrl("");
				itDto.setNotificaSettoreSegnalante(true);
				itDto.setDataInserimento(new Date());
				iterStepService.newIter(itDto);
			}
			
			//indirizzi
			if(!nuovoInserimento) {
				dto.setObj(soggettoId);
				indirizzoService.eliminaIndirizziBySoggetto(dto);
			}
			for(CsAIndirizzo csInd: residenzaCsaMan.getLstIndirizzi()) {
				
				dto.setObj(csInd);
				csInd.setAnaIndirizzoId(null);
				csInd.getCsAAnaIndirizzo().setId(null);
				csInd.setDtIns(new Date());
				csInd.setUserIns(username);
				csInd.setDataInizioSys(new Date());
				csInd.setCsASoggetto(soggetto);
				if(csInd.getDataFineApp() != null && !csInd.getDataFineApp().equals(DataModelCostanti.END_DATE))
					csInd.setDataFineSys(new Date());
				indirizzoService.saveIndirizzo(dto);	
			}
			
			//medici
			if(!nuovoInserimento) {
				dto.setObj(soggettoId);
				soggettoService.eliminaSoggettoMedicoBySoggetto(dto);
			}
			for(ValiditaCompBaseBean comp: mediciGestBean.getLstComponents()) {
				CsASoggettoMedico cs = new CsASoggettoMedico();
				CsASoggettoMedicoPK csPK = new CsASoggettoMedicoPK();
				cs.setCsASoggetto(soggetto);
				cs.setDataInizioApp(comp.getDataInizio());
				cs.setDataInizioSys(new Date());
				cs.setDtIns(new Date());
				cs.setUserIns(username);
				csPK.setMedicoId(comp.getId());
				csPK.setDataFineApp(comp.getDataFine());
				csPK.setSoggettoAnagraficaId(soggetto.getAnagraficaId());
				cs.setId(csPK);
				
				dto.setObj(cs);
				soggettoService.saveSoggettoMedico(dto);
			}
			
			//status
			if(!nuovoInserimento) {
				dto.setObj(soggettoId);
				soggettoService.eliminaSoggettoStatusBySoggetto(dto);
			}
			for(ValiditaCompBaseBean comp: statusGestBean.getLstComponents()) {
				CsASoggettoStatus cs = new CsASoggettoStatus();
				CsASoggettoStatusPK csPK = new CsASoggettoStatusPK();
				cs.setCsASoggetto(soggetto);
				cs.setDataInizioApp(comp.getDataInizio());
				cs.setDataInizioSys(new Date());
				cs.setDtIns(new Date());
				cs.setUserIns(username);
				csPK.setStatusId(comp.getId());
				csPK.setDataFineApp(comp.getDataFine());
				csPK.setSoggettoAnagraficaId(soggetto.getAnagraficaId());
				cs.setId(csPK);
				
				dto.setObj(cs);
				soggettoService.saveSoggettoStatus(dto);
			}
			
			//stato civile
			if(!nuovoInserimento) {
				dto.setObj(soggettoId);
				soggettoService.eliminaSoggettoStatoCivileBySoggetto(dto);
			}
			for(ValiditaCompBaseBean comp: statoCivileGestBean.getLstComponents()) {
				CsASoggettoStatoCivile cs = new CsASoggettoStatoCivile();
				CsASoggettoStatoCivilePK csPK = new CsASoggettoStatoCivilePK();
				cs.setCsASoggetto(soggetto);
				cs.setDataInizioApp(comp.getDataInizio());
				cs.setDataInizioSys(new Date());
				cs.setDtIns(new Date());
				cs.setUserIns(username);
				
				csPK.setStatoCivileCod(comp.getId().toString());
				csPK.setDataFineApp(comp.getDataFine());
				csPK.setSoggettoAnagraficaId(soggetto.getAnagraficaId());
				cs.setId(csPK);
				
				dto.setObj(cs);
				soggettoService.saveSoggettoStatoCivile(dto);
			}
			
			if(nuovoInserimento) {
				//creo la famiglia GIT
				dto.setObj(soggetto);
				dto.setObj2(getListaFamilari(soggetto));
				parentiService.createFamigliaGruppoGit(dto);
			}
			
			SchedaBean schedaBean = (SchedaBean) getSession().getAttribute("schedaBean");
			SsScheda ssScheda = schedaBean!=null ? schedaBean.getSchedaSegrBean().getSsScheda() : null;
			//aggancio la scheda segretariato se sono partito da quella
			if(ssScheda != null) {
				//verifico che i soggetti corrispondano per CF
				if(schedaBean.getSchedaSegrBean().getSsSchedaSegnalato().getAnagrafica().getCf().equalsIgnoreCase(datiAnaBean.getCodiceFiscale())){
				
					soggCatSocialeBean.setSchedaSegr(ssScheda);
					
					if(soggettoId == null){ //Se è la creazione aggiorno la CS_SS
						AccessTableSchedaSegrSessionBeanRemote schedaSegrService = 
								(AccessTableSchedaSegrSessionBeanRemote) getCarSocialeEjb ("AccessTableSchedaSegrSessionBean");
						dto.setObj(ssScheda.getId());
						dto.setObj2(soggetto);
						schedaSegrService.agganciaCartellaASchedaUDC(dto);
					}
				}
			}
			
			if(nuovoInserimento){
				dto.setObj(soggetto.getAnagraficaId());
				soggetto = soggettoService.getSoggettoById(dto);
			}
			
			dto.setObj(soggetto.getAnagraficaId());
			List<CsCCategoriaSocialeBASIC> lstCatSociali = soggettoService.getCatSocAttualiBySoggetto(dto);
			if(lstCatSociali.isEmpty())
				soggCatSocialeBean.carica(soggetto);
			
			//soggetto=csSogg;
						
		} catch(Exception e) {
			salvato = false;
			addErrorFromProperties("salva.errorA");
			logger.error(e.getMessage(),e);
		}
		
		return salvato;
	}
	*/
	public boolean salvaAnagraficaModificata() {
		boolean salvato = true;
		this.setAccessoModifica("MODIFICA");//M = MODIFICA Dei dati anagrafici base
		try{
			//Devo salvare la modifica ai dati anagrafici base e tracciare la modifica
			//Salvo il Log
			String username = getCurrentUsername();
		
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);	
			dto.setObj(soggettoId);
			
			//dati anagrafici
			soggetto = soggettoService.getSoggettoById(dto);
			if(validaAnagrafica())
			{
					
				CsAAnagrafica csAAnagraficaMod = soggetto.getCsAAnagrafica();

				csAAnagraficaMod.setNome(datiAnaBean.getNome());
				csAAnagraficaMod.setCognome(datiAnaBean.getCognome());
				csAAnagraficaMod.setDataNascita(datiAnaBean.getDataNascita());
				if (comuneNazioneNascitaMan.isComune() && comuneNazioneNascitaMan.getComuneNascitaMan().getComune() != null) 
				{
					csAAnagraficaMod.setProvNascitaCod(comuneNazioneNascitaMan.getComuneNascitaMan().getComune().getSiglaProv());
					csAAnagraficaMod.setComuneNascitaCod(comuneNazioneNascitaMan.getComuneNascitaMan().getComune().getCodIstatComune());
					csAAnagraficaMod.setComuneNascitaDes(comuneNazioneNascitaMan.getComuneNascitaMan().getComune().getDenominazione());
				}
				if (comuneNazioneNascitaMan.isNazione()	&& comuneNazioneNascitaMan.getNazioneNascitaMan().getNazione() != null) {
					csAAnagraficaMod.setStatoNascitaCod(comuneNazioneNascitaMan.getNazioneNascitaMan().getNazione().getCodIstatNazione());
					csAAnagraficaMod.setStatoNascitaDes(comuneNazioneNascitaMan.getNazioneNascitaMan().getNazione().getNazione());
				}
				csAAnagraficaMod.setSesso(datiAnaBean.getDatiSesso().getSesso());
				csAAnagraficaMod.setCittadinanza(datiAnaBean.getCittadinanza());

				dto.setObj(csAAnagraficaMod);
				soggettoService.saveAnagrafica(dto);
			}
			else
			{
			   
				CsAAnagrafica csAna = soggetto.getCsAAnagrafica();
				datiAnaBean.setCognome(csAna.getCognome());
				datiAnaBean.setNome(csAna.getNome());
				datiAnaBean.setDataNascita(csAna.getDataNascita());
				if(csAna.getComuneNascitaCod() != null) {
					ComuneBean comuneBean = new ComuneBean(csAna.getComuneNascitaCod(), csAna.getComuneNascitaDes(), csAna.getProvNascitaCod());
					comuneNazioneNascitaMan.getComuneNascitaMan().setComune(comuneBean);
				}
				if(csAna.getStatoNascitaCod() != null) {
					AmTabNazioni nazione = CsUiCompBaseBean.getNazioneByIstat(csAna.getStatoNascitaCod(), csAna.getStatoNascitaDes());
					comuneNazioneNascitaMan.getNazioneNascitaMan().setNazione(nazione);
					comuneNazioneNascitaMan.setNazioneValue();
				}
				SessoBean sb = new SessoBean(csAna.getSesso());
				datiAnaBean.setDatiSesso(sb);
				datiAnaBean.setCittadinanza(csAna.getCittadinanza());
			}
			
			
			this.salvaAnagraficaLog();
		
		RequestContext.getCurrentInstance().addCallbackParam("saved", salvato);	
		} catch(Exception e) {
			salvato = false;
			addErrorFromProperties("salva.errorA");
			logger.error(e.getMessage(),e);
		}
		
		return salvato;
	}
	
	public boolean salvaAnagraficaLog() {
		boolean salvato = true;

			//A = ACCESSO AL PANNELLO DI MODIFICA Dei dati anagrafici base	ma ancora non ho salvato	
			try{
				//Devo salvare la modifica ai dati anagrafici base e tracciare la modifica
				//Salvo il Log
				String username = getCurrentUsername();
//			
			    BaseDTO dto = new BaseDTO();
				fillEnte(dto);	
				dto.setObj(soggettoId);
				
				//dati anagrafici
				soggetto = new CsASoggettoLAZY();
				soggetto = soggettoService.getSoggettoById(dto);
				
				CsAAnagrafica csAna = soggetto!=null ?  soggetto.getCsAAnagrafica() : null;
				
//				
//				//dati anagrafici
//				soggetto = soggettoService.getSoggettoById(dto);
				CsAAnagraficaLog anagraficaLog = new CsAAnagraficaLog();
//				fillEnte(anagraficaLog);
				
			
				anagraficaLog.setNome(datiAnaBean.getNome());
				anagraficaLog.setCognome(datiAnaBean.getCognome());
				anagraficaLog.setDataNascita(datiAnaBean.getDataNascita());
				if(comuneNazioneNascitaMan.isComune() &&  comuneNazioneNascitaMan.getComuneNascitaMan().getComune() != null) {
					anagraficaLog.setProvNascitaCod(comuneNazioneNascitaMan.getComuneNascitaMan().getComune().getSiglaProv());
					anagraficaLog.setComuneNascitaCod(comuneNazioneNascitaMan.getComuneNascitaMan().getComune().getCodIstatComune());
					anagraficaLog.setComuneNascitaDes(comuneNazioneNascitaMan.getComuneNascitaMan().getComune().getDenominazione());
		         }
			       if(comuneNazioneNascitaMan.isNazione() && comuneNazioneNascitaMan.getNazioneNascitaMan().getNazione() != null) {
							anagraficaLog.setStatoNascitaCod(comuneNazioneNascitaMan.getNazioneNascitaMan().getNazione().getCodIstatNazione());
							anagraficaLog.setStatoNascitaDes(comuneNazioneNascitaMan.getNazioneNascitaMan().getNazione().getNazione());
						}
				anagraficaLog.setSesso(datiAnaBean.getDatiSesso().getSesso());
				anagraficaLog.setCittadinanza(datiAnaBean.getCittadinanza());		
				anagraficaLog.setCsAAnagrafica(csAna);  //setId(id);(csAna.getId());
				anagraficaLog.setDataModifica(new Date());
				anagraficaLog.setOperatore(username);
			    anagraficaLog.setTipoAzione(getAccessoModifica());
						
			BaseDTO dto2 = new BaseDTO();
			fillEnte(dto2);	
			dto2.setObj(anagraficaLog);
				
			csAnagraficaLogService.saveAnagraficaLog(dto2) ;
			loadListaForzatureAnagrafiche();
			
			this.setAccessoModifica("ACCESSO");
		
		RequestContext.getCurrentInstance().addCallbackParam("saved", salvato);	
		} catch(Exception e) {
			salvato = false;
			addErrorFromProperties("salva.errorA");
			logger.error(e.getMessage(),e);
		}
		
		return salvato;
	}

/**
 * Aggiornamento dei dati del soggetto in base ad aggiornamento GIT
 * **/	
//SISO-1127 Inizio
	public boolean salvaAnagraficaGit() {
		boolean salvato = true;
		 
		CsAAnaIndirizzo  indirizzoResAttivo = null;
		CsASoggettoMedico soggettoMedico = null;
			//A = ACCESSO AL PANNELLO DI MODIFICA Dei dati anagrafici base	ma ancora non ho salvato	
			try{
				//Devo salvare la modifica ai dati anagrafici base e tracciare la modifica
				//Salvo il Log
				String username = getCurrentUsername();
//			
			    BaseDTO dto = new BaseDTO();
				fillEnte(dto);	
				dto.setObj(soggettoId);
				
				//dati anagrafici
				soggetto = new CsASoggettoLAZY();
				soggetto = soggettoService.getSoggettoById(dto);
				CsAAnagrafica csAna = soggetto!=null ?  soggetto.getCsAAnagrafica() : null;
				ComuneNazioneResidenzaMan comuneNazioneResidenzaMan = null;
				ComuneBean comuneBean = null;

				
 				for(DatiAggCasoGitDTO datoAggiornato : lstAggiornamentiAnagraficaCasoGit) {
 					
					if(datoAggiornato.getTipologiaVariazione().equals(TipoAggiornamentoAnagrafica.ANAGRAFICA)) {
						for(String key	: datoAggiornato.getElementiVariati().keySet()) {
							if(TipoAggiornamentoAnagrafica.ANAGRAFICA_CITTADINANZA.indexOf(key) > -1) {
									datiAnaBean.setCittadinanza(datoAggiornato.getElementiVariati().get(key));
							}
							if(TipoAggiornamentoAnagrafica.ANAGRAFICA_CODICE_FISCALE.indexOf(key) > -1) {
								datiAnaBean.setCodiceFiscale(datoAggiornato.getElementiVariati().get(key));
							}
							if(TipoAggiornamentoAnagrafica.ANAGRAFICA_COGNOME.indexOf(key) > -1) {
								datiAnaBean.setCognome(datoAggiornato.getElementiVariati().get(key));
							}
							if(TipoAggiornamentoAnagrafica.ANAGRAFICA_DATA_DECESSO.indexOf(key) > -1) {
//								datiAnaBean.setDataDecesso(stringToDate(datoAggiornato.getElementiVariati().get(key)));
								//Al momento non viene effettuata alcuna operazione!
							}
							if(TipoAggiornamentoAnagrafica.ANAGRAFICA_NOME.indexOf(key) > -1) {
								csAna.setNome(datoAggiornato.getElementiVariati().get(key));
							}
							if(TipoAggiornamentoAnagrafica.ANAGRAFICA_SESSO.indexOf(key) > -1) {
								SessoBean sb = new SessoBean(datoAggiornato.getElementiVariati().get(key));
								datiAnaBean.setDatiSesso(sb);
							}
							if(TipoAggiornamentoAnagrafica.ANAGRAFICA_TELEFONO.indexOf(key) > -1) {
								 datiAnaBean.setTelefono(datoAggiornato.getElementiVariati().get(key));
							}
							if(TipoAggiornamentoAnagrafica.ANAGRAFICA_CELLULARE.indexOf(key) > -1) {
								 datiAnaBean.setCellulare(datoAggiornato.getElementiVariati().get(key));
							}
							if(TipoAggiornamentoAnagrafica.ANAGRAFICA_STATO_CIVILE.indexOf(key) > -1) {
								BaseDTO dtoSC = new BaseDTO();
								fillEnte(dtoSC);	
								dtoSC.setObj(datoAggiornato.getElementiVariati().get(key));
								CsTbStatoCivile  tbStatoCivileNew = confService.getStatoCivileByDescrizione(dtoSC);
								
								ValiditaCompBaseBean compAttivo =  statoCivileGestBean.getComponenteAttivo();
								if(compAttivo != null) {
									statoCivileGestBean.setCompSelezionato(compAttivo);
									statoCivileGestBean.setIndexSelezionato(statoCivileGestBean.getIndexComponenteAttivo());
									statoCivileGestBean.chiudiSelezionato();
									statoCivileGestBean.salva();
									//Attenzone non può essere chiuso due volte nella stessa stata lo stesso codice stato ticivile per lo stesso soggetto
									//perché è in chiave
								}
								
								statoCivileGestBean.setItemSelezionato(tbStatoCivileNew.getCod() + "|" + tbStatoCivileNew.getDescrizione());
								statoCivileGestBean.aggiungiSelezionato();
								statoCivileGestBean.setMaxActiveComponents(1);
								statoCivileGestBean.salva();
								
								
							} 
						}
					}
					
					////// INIZIO NUOVA GESTIONE RESIDENZA
					
					if(datoAggiornato.getTipologiaVariazione().equals(TipoAggiornamentoAnagrafica.RESIDENZA)) {
						
						CsAIndirizzo oldIndirizzoResAttivo = residenzaCsaMan.getIndirizzoResidenzaAttivo();
						if(oldIndirizzoResAttivo != null) {
							residenzaCsaMan.setIndirizzoSelezionato(oldIndirizzoResAttivo);
							residenzaCsaMan.setDataAnnullamento(new Date());
							residenzaCsaMan.annullaIndirizzo();
								
						}
						CsAAnaIndirizzo indirizzoAna = new CsAAnaIndirizzo();

						residenzaCsaMan.setTipoIndirizzo(String.valueOf(residenzaCsaMan.getTipoIndirizzoResidenza().getId()));
						
						for(String key	: datoAggiornato.getElementiVariati().keySet()) {
							if(TipoAggiornamentoAnagrafica.COD_COM_RESIDENZA.indexOf(key) > -1) { 
								indirizzoAna.setComCod(datoAggiornato.getElementiVariati().get(key));
							}
							if(TipoAggiornamentoAnagrafica.COMUNE_DI_RESIDENZA.indexOf(key) > -1) {
								indirizzoAna.setComDes(datoAggiornato.getElementiVariati().get(key));
							}
							if(TipoAggiornamentoAnagrafica.INDIRIZZO_RESIDENZA.indexOf(key) > -1) {
								indirizzoAna.setIndirizzo(datoAggiornato.getElementiVariati().get(key));
							}
							if(TipoAggiornamentoAnagrafica.NUM_CIVICO_RESIDENZA.indexOf(key) > -1) {
								indirizzoAna.setCivicoNumero(datoAggiornato.getElementiVariati().get(key));
							}
							if(TipoAggiornamentoAnagrafica.PROVINCIA_DI_RESIDENZA.indexOf(key) > -1) {
								indirizzoAna.setProv(datoAggiornato.getElementiVariati().get(key));
						   }
						
						}
						//Se indirizzo non ha provincia, significa che non � variata e recupero la precedente per non � veriata!!!
						//Stesso controllo anche per il comune/nazione.
						if(indirizzoAna.getProv() == null || indirizzoAna.getComDes() == null || indirizzoAna.getComCod() == null || indirizzoAna.getIndirizzo() == null) {
							List<CsAIndirizzo>listIndirizziOld = residenzaCsaMan.getLstIndirizziOld();
							
							if(listIndirizziOld !=null & listIndirizziOld.size() > 0) {
								for(CsAIndirizzo csAIndirizzoOld : listIndirizziOld) {
								   
								if(csAIndirizzoOld.getDataFineApp() == null && csAIndirizzoOld.getCsTbTipoIndirizzo().getDescrizione().equalsIgnoreCase("Residenza"))
									indirizzoAna.setProv(indirizzoAna.getProv() == null ? csAIndirizzoOld.getCsAAnaIndirizzo().getProv() : indirizzoAna.getProv());
									indirizzoAna.setComDes(indirizzoAna.getComDes() == null ? csAIndirizzoOld.getCsAAnaIndirizzo().getProv() : indirizzoAna.getComDes());
									indirizzoAna.setComCod(indirizzoAna.getComCod() == null ? csAIndirizzoOld.getCsAAnaIndirizzo().getProv() : indirizzoAna.getComCod());
									indirizzoAna.setIndirizzo(indirizzoAna.getIndirizzo() == null ? csAIndirizzoOld.getCsAAnaIndirizzo().getIndirizzo() : indirizzoAna.getIndirizzo());
									
								}
							}
								
						}
						 if(indirizzoAna.getProv().equals(residenzaCsaMan.getEnteSiglaProv())) {
							 residenzaCsaMan.setTipoComune(residenzaCsaMan.getEnteValue());
							 IndirizzoMan indirizzoMan = new IndirizzoMan();
							 indirizzoMan.setSelectedCivico(indirizzoAna.getCivicoNumero());
							 indirizzoMan.setSelectedIndirizzo(indirizzoAna.getIndirizzo());
							 residenzaCsaMan.setIndirizzoMan(indirizzoMan);
						 }else {
							 residenzaCsaMan.setTipoComune(residenzaCsaMan.getAltroValue());
							 
							 comuneBean = new ComuneBean();  
							 comuneNazioneResidenzaMan = new ComuneNazioneResidenzaMan();
							 comuneBean.setAttivo(true);
							 comuneBean.setDenominazione(indirizzoAna.getComDes());
							 comuneBean.setCap(indirizzoAna.getComCod());
							 comuneBean.setSiglaProv(indirizzoAna.getProv());
							
							 ComuneResidenzaMan comuneResidenzaMan = new ComuneResidenzaMan();
							 comuneResidenzaMan.setComune(comuneBean);
							 comuneNazioneResidenzaMan.setComuneResidenzaMan(comuneResidenzaMan);
							 comuneNazioneResidenzaMan.setComuneValue();
							 residenzaCsaMan.setComuneNazioneResidenzaMan(comuneNazioneResidenzaMan);
							 
							 residenzaCsaMan.setIndirizzo(indirizzoAna.getIndirizzo());
							 residenzaCsaMan.setCivicoNumero(indirizzoAna.getCivicoNumero());
							 residenzaCsaMan.setDataInizioApp(new Date());
						 }
						 if(indirizzoAna.getIndirizzo() == null)
								residenzaCsaMan.setIndirizzo(TipoAggiornamentoAnagrafica.ASSENTE);
						 residenzaCsaMan.aggiungiIndirizzo();
					}
					
					////// FINE NUOVA GESTIONE RESIDENZA
					
				
					///NUOVA VERSIONE INDIRIZZO DOMICILIO
					if(datoAggiornato.getTipologiaVariazione().equals(TipoAggiornamentoAnagrafica.DOMICILIO)) {
						
						 
					    CsAIndirizzo indirizzoDomAttivo =	 residenzaCsaMan.getIndirizzoDomicilioAttivo();
						if(indirizzoDomAttivo != null && indirizzoDomAttivo.getAnaIndirizzoId() != null) {
							residenzaCsaMan.setIndirizzoSelezionato(indirizzoDomAttivo);
							 residenzaCsaMan.setDataAnnullamento(new Date());
								residenzaCsaMan.annullaIndirizzo();
								
						}
					   
						CsAAnaIndirizzo indirizzoAnaDom = new CsAAnaIndirizzo();
						 
					
						residenzaCsaMan.setTipoComune(residenzaCsaMan.getEnteValue());
						residenzaCsaMan.setDataInizioApp(new Date());
						residenzaCsaMan.setTipoIndirizzo(String.valueOf(residenzaCsaMan.getTipoIndirizzoDomicilio().getId()));
						
						
						
						
						for(String key	: datoAggiornato.getElementiVariati().keySet()) {
							if(TipoAggiornamentoAnagrafica.CODICE_COMUNE_DOMICILIO.indexOf(key) > -1) {
								indirizzoAnaDom.setComCod(datoAggiornato.getElementiVariati().get(key));
							}
							if(TipoAggiornamentoAnagrafica.COMUNE_DOMICILIO.indexOf(key) > -1) {
								indirizzoAnaDom.setComDes(datoAggiornato.getElementiVariati().get(key));
							}
							if(TipoAggiornamentoAnagrafica.INDIRIZZO_DOMICILIO.indexOf(key) > -1) {
								indirizzoAnaDom.setIndirizzo(datoAggiornato.getElementiVariati().get(key));
							}
							if(TipoAggiornamentoAnagrafica.NUM_CIVICO_DOMICILIO.indexOf(key) > -1) {
								indirizzoAnaDom.setCivicoNumero(datoAggiornato.getElementiVariati().get(key));
							}
							if(TipoAggiornamentoAnagrafica.PROVINCIA_DI_DOMICILIO.indexOf(key) > -1) {
								indirizzoAnaDom.setProv(datoAggiornato.getElementiVariati().get(key));
									
							 }
						 }
						//Se indirizzo non ha provincia, significa che non � variata e recupero la precedente per non � veriata!!!
						//Stesso controllo anche per il comune/nazione.
						if(indirizzoAnaDom.getProv() == null || indirizzoAnaDom.getComDes() == null || indirizzoAnaDom.getComCod() == null || indirizzoAnaDom.getIndirizzo() == null) {
							List<CsAIndirizzo>listIndirizziOld = residenzaCsaMan.getLstIndirizziOld();
							
							if(listIndirizziOld !=null & listIndirizziOld.size() > 0) {
								for(CsAIndirizzo csAIndirizzoOld : listIndirizziOld) {
								   
								if(csAIndirizzoOld.getDataFineApp() == null && csAIndirizzoOld.getCsTbTipoIndirizzo().getDescrizione().equalsIgnoreCase("Domicilio"))
									indirizzoAnaDom.setProv(indirizzoAnaDom.getProv() == null ? csAIndirizzoOld.getCsAAnaIndirizzo().getProv() : indirizzoAnaDom.getProv());
									indirizzoAnaDom.setComDes(indirizzoAnaDom.getComDes() == null ? csAIndirizzoOld.getCsAAnaIndirizzo().getProv() : indirizzoAnaDom.getComDes());
									indirizzoAnaDom.setComCod(indirizzoAnaDom.getComCod() == null ? csAIndirizzoOld.getCsAAnaIndirizzo().getProv() : indirizzoAnaDom.getComCod());
									indirizzoAnaDom.setIndirizzo(indirizzoAnaDom.getIndirizzo() == null ? csAIndirizzoOld.getCsAAnaIndirizzo().getIndirizzo() : indirizzoAnaDom.getIndirizzo());
									
								}
							}
								
						}
						 if(indirizzoAnaDom.getProv()!=null && 
							indirizzoAnaDom.getProv().equals(residenzaCsaMan.getEnteSiglaProv())) {
							 residenzaCsaMan.setTipoComune(residenzaCsaMan.getEnteValue());
							 IndirizzoMan indirizzoMan = new IndirizzoMan();
							 indirizzoMan.setSelectedCivico(indirizzoAnaDom.getCivicoNumero());
							 indirizzoMan.setSelectedIndirizzo(indirizzoAnaDom.getIndirizzo());
							 residenzaCsaMan.setIndirizzoMan(indirizzoMan);
						 }else {
							 residenzaCsaMan.setTipoComune(residenzaCsaMan.getAltroValue());

							 comuneBean = new ComuneBean();  
							 comuneNazioneResidenzaMan = new ComuneNazioneResidenzaMan();
							 comuneBean.setAttivo(true);
							 comuneBean.setDenominazione(indirizzoAnaDom.getComDes());
							 comuneBean.setCap(indirizzoAnaDom.getComCod());
							 comuneBean.setSiglaProv(indirizzoAnaDom.getProv());
							
							 ComuneResidenzaMan comuneResidenzaMan = new ComuneResidenzaMan();
							 comuneResidenzaMan.setComune(comuneBean);
							 comuneNazioneResidenzaMan.setComuneResidenzaMan(comuneResidenzaMan);
							 comuneNazioneResidenzaMan.setComuneValue();
							 residenzaCsaMan.setComuneNazioneResidenzaMan(comuneNazioneResidenzaMan);
							 residenzaCsaMan.setIndirizzo(indirizzoAnaDom.getIndirizzo());
							 residenzaCsaMan.setCivicoNumero(indirizzoAnaDom.getCivicoNumero());
							 
						 }
						
						 residenzaCsaMan.setDataInizioApp(new Date());
						//Metto un carattere Jolly nel caso in cui fosse assente l'indirizzo del domicilio
						if(indirizzoAnaDom.getIndirizzo() == null)
							residenzaCsaMan.setIndirizzo(TipoAggiornamentoAnagrafica.ASSENTE);
						
						residenzaCsaMan.aggiungiIndirizzo();
						
						
					}
					///FINE NUOVA VERSIONE
					
					if(datoAggiornato.getTipologiaVariazione().equals(TipoAggiornamentoAnagrafica.MEDICO_REVOCATO)
							|| datoAggiornato.getTipologiaVariazione().equals(TipoAggiornamentoAnagrafica.NUOVO_MEDICO)) {
					 
						soggettoMedico = new CsASoggettoMedico();
						CsCMedico medico = new CsCMedico();
						Date dataScelta = null;
						Date dataRevoca = null;
					for(String key	: datoAggiornato.getElementiVariati().keySet()) {
						if(TipoAggiornamentoAnagrafica.COGNOME_MEDICO_REVOCATO.indexOf(key) > -1) {
							medico.setCognome(datoAggiornato.getElementiVariati().get(key));
						}
						if(TipoAggiornamentoAnagrafica.COGNOME_NUOVO_MEDICO.indexOf(key) > -1) {
							medico.setCognome(datoAggiornato.getElementiVariati().get(key));
						}
						if(TipoAggiornamentoAnagrafica.NOME_MEDICO_REVOCATO.indexOf(key) > -1) {
							medico.setNome(datoAggiornato.getElementiVariati().get(key));
						}
						if(TipoAggiornamentoAnagrafica.NOME_NUOVO_MEDICO.indexOf(key) > -1) {
							medico.setCognome(datoAggiornato.getElementiVariati().get(key));
						}
						if(TipoAggiornamentoAnagrafica.COD_REGIONALE_MEDICO_REVOCATO.indexOf(key) > -1) {
							medico.setCodiceRegionale(datoAggiornato.getElementiVariati().get(key));
						}else if(TipoAggiornamentoAnagrafica.CODICE_FISCALE_MEDICO_REVOCATO.indexOf(key) > -1) {
							medico.setCodiceRegionale(datoAggiornato.getElementiVariati().get(key));
						}
						if(TipoAggiornamentoAnagrafica.COD_REGIONALE_NUOVO_MEDICO.indexOf(key) > -1) {
							medico.setCodiceRegionale(datoAggiornato.getElementiVariati().get(key));
						}else if(TipoAggiornamentoAnagrafica.CODICE_FISCALE_NUOVO_MEDICO.indexOf(key) > -1) {
							medico.setCodiceRegionale(datoAggiornato.getElementiVariati().get(key));
						}
						if(TipoAggiornamentoAnagrafica.DATA_SCELTA_MEDICO_REVOCATO.indexOf(key) > -1) {
							dataScelta = ddMMyyyy.parse(datoAggiornato.getElementiVariati().get(key));
						}
						if(TipoAggiornamentoAnagrafica.DATA_SCELTA_NUOVO_MEDICO.indexOf(key) > -1) {
							dataScelta = ddMMyyyy.parse(datoAggiornato.getElementiVariati().get(key));
						}
						if(TipoAggiornamentoAnagrafica.DATA_REVOCA_MEDICO_REVOCATO.indexOf(key) > -1) {
							dataScelta = ddMMyyyy.parse(datoAggiornato.getElementiVariati().get(key));
						}
						if(TipoAggiornamentoAnagrafica.DATA_REVOCA_NUOVO_MEDICO.indexOf(key) > -1) {
							dataScelta = ddMMyyyy.parse(datoAggiornato.getElementiVariati().get(key));
						}
					  }
					  
					
					  if(!StringUtils.isBlank(anagraficaAggiornata.getIdOrigWs())){
						  String[] idSplit = anagraficaAggiornata.getIdOrigWs().split("@");
						  String tipo = idSplit[0];
						  verificaAggiungiMedico(tipo, medico, dataScelta, dataRevoca);
					  }
					  
					}
					
 				}
 				//Salva tutto
				salva();
				loadListaForzatureAnagrafiche();
				//Aggiorna la Segnalazione a '-'
				this.anagraficaAggiornata.setSegnalazione("-");
				this.anagraficaAggiornata.setDtMod(new Date());
				dto = new BaseDTO();
				fillEnte(dto);	
				dto.setObj(anagraficaAggiornata);
				soggettoService.updateAnagraficaComponenteCaso(dto);
				verificaAggiornamentiSoggettoCaso();
				RequestContext.getCurrentInstance().addCallbackParam("saved", salvato);	
		} catch(Exception e) {
			salvato = false;
			addErrorFromProperties("salva.errorA");
			logger.error(e.getMessage(),e);
		}
		
		return salvato;
	}
	
	private void verificaAggiungiMedico(String tipoRicerca, CsCMedico medicoGit, Date dataScelta, Date dataRevoca) throws CsUiCompException  {
		boolean isNewMedico = false;
		it.webred.cs.csa.ejb.dto.BaseDTO dto  = new it.webred.cs.csa.ejb.dto.BaseDTO();   
		fillEnte(dto);
		CsCMedico medico = null;
		  dto.setObj(medicoGit.getCodiceRegionale());
			medico  = mediciSessionBean.getMedicoByCodReg(dto);
			if(medico==null){
				if(TipoRicercaSoggetto.ANAG_SANITARIA_MARCHE.equalsIgnoreCase(tipoRicerca))
					medico = aggiungiMedicoMarche(medicoGit.getCodiceRegionale(), medicoGit.getCognome(), medicoGit.getNome());
				if(TipoRicercaSoggetto.ANAG_SANITARIA_UMBRIA.equalsIgnoreCase(tipoRicerca))
					medico = aggiungiMedicoUmbria(medicoGit.getCodiceRegionale());
				
				isNewMedico = true;
			}
			if(medico != null) {
				//Verifico se il medico è già associato al soggetto
				ValiditaCompBaseBean vCompSel = null; 
				vCompSel = getMediciGestBean().getComponenteAttivo();
			
				if(vCompSel != null){
					if(vCompSel.getId().equals(medico.getId())) {
						//Se il medico esistente coincide con il nuovo, aggiorno le date di validità
						vCompSel.setDataInizio(dataScelta!=null ? dataScelta : new Date());
						vCompSel.setDataFine(dataRevoca!=null ? dataRevoca : DataModelCostanti.END_DATE);
					}else{
						//Annullo il precedente e aggiungo il nuovo
						vCompSel.setDataFine(dataScelta != null ? dataScelta : new Date());
					}
					getMediciGestBean().setCompSelezionato(vCompSel);
					getMediciGestBean().setIndexSelezionato(getMediciGestBean().getIndexComponenteAttivo());
					//getMediciGestBean().chiudiSelezionato(); lo chiudo prima con la data di inizio del nuovo medico
					getMediciGestBean().salva();
				}
				
				addToListaMediciSoggetto(medico, dataScelta, dataRevoca);
		}
	}
	 
	
//SISO-1127 Fine	
	
	private List<FamiliareDettaglio> getListaFamilari(CsASoggettoLAZY soggetto){
    	RicercaAnagraficaParams params;
    	String idWs = soggetto.getCsAAnagrafica().getIdOrigWs();
    	String tipoRicerca = soggetto.getCsAAnagrafica().getIdOrigWsTipo()==null ? 
    			DataModelCostanti.TipoRicercaSoggetto.DEFAULT : soggetto.getCsAAnagrafica().getIdOrigWsTipo();
    	String identificativo = soggetto.getCsAAnagrafica().getIdOrigWsId(); //Valido per WS esterno
    	String cf = soggetto.getCsAAnagrafica().getCf();
		
    	List<FamiliareDettaglio> lista = new ArrayList<FamiliareDettaglio>();
    	if(isAnagrafeAbilitata(tipoRicerca)){
			params = new RicercaAnagraficaParams(tipoRicerca, true);
			fillEnte(params);
			params.setCf(cf);
			params.setIdentificativo(identificativo);
	    	
			RicercaAnagraficaResult result = getComposizioneFamiliare(params);
			
			if(result!=null){
				if(result.getMessaggio()==null && !result.getElencoFamiliari().isEmpty())
					lista = result.getElencoFamiliari();
				else if(result.getMessaggio()!=null)
					logger.error("Errore ricerca componenti familiari per il soggetto["+cf+"]["+idWs+"] "+tipoRicerca+" CODICE["+result.getCodice()+"]"+ result.getMessaggio(), result.getEccezione());
			}
		}else 
			logger.error("Ricerca componenti familiari per il soggetto["+cf+"]["+idWs+"] "+tipoRicerca+" NON ABILITATA");

		return lista;
	}
	
	private boolean validaAnagrafica() {
		boolean ok = true;
		
		if(datiAnaBean.getCognome() == null || "".equals(datiAnaBean.getCognome().trim())) {
			this.addErrorCampiObbligatori(nomeTab,"Cognome");
			ok = false;
		}
		
		if(datiAnaBean.getNome() == null || "".equals(datiAnaBean.getNome().trim())) {
			this.addErrorCampiObbligatori(nomeTab,"Nome");
			ok = false;
		}
		
		if(datiAnaBean.getDataNascita() == null) {
			this.addErrorCampiObbligatori(nomeTab,"Data di nascita");
			ok = false;
		}
		
		if(datiAnaBean.getDatiSesso().getSesso() == null || "".equals(datiAnaBean.getDatiSesso().getSesso().trim())) {
			this.addErrorCampiObbligatori(nomeTab,"Sesso");
			ok = false;
		}
		
		if(datiAnaBean.getCodiceFiscale() == null || "".equals(datiAnaBean.getCodiceFiscale().trim())) {
			this.addErrorCampiObbligatori(nomeTab,"Cod.fiscale");
			ok = false;
		}
		
		if(datiAnaBean.getCittadinanza() == null || "".equals(datiAnaBean.getCittadinanza().trim())) {
			this.addErrorCampiObbligatori(nomeTab,"Cittadinanza");
			ok = false;
		}
		
		if(comuneNazioneNascitaMan.getComuneNascitaMan().getComune() == null && comuneNazioneNascitaMan.getNazioneNascitaMan().getNazione() == null) {
			this.addErrorCampiObbligatori(nomeTab,"Comune/Stato estero di nascita");
			ok = false;
		} 
		
		if(residenzaCsaMan.getIndirizzoResidenzaAttivo() == null || residenzaCsaMan.getIndirizzoResidenzaAttivo().getCsAAnaIndirizzo() == null) {
			this.addErrorCampiObbligatori(nomeTab,"Residenza");
			ok = false;
		}
		
		if((datiAnaBean.getCellulare() == null || "".equals(datiAnaBean.getCellulare().trim())) && (datiAnaBean.getTelefono()==null || "".equals(datiAnaBean.getTelefono()))){
			addError(nomeTab+": Inserire almeno un recapito telefonico","");
			ok = false;
		}
			
		
		return ok;
	}
	
	public DatiAnaBean getDatiAnaBean() {
		return datiAnaBean;
	}
	
	public void setDatiAnaBean(DatiAnaBean datiAnaBean) {
		this.datiAnaBean = datiAnaBean;
	}

	@Override
	public ComuneNazioneNascitaMan getComuneNazioneNascitaMan() {
		return comuneNazioneNascitaMan;
	}

	public void setComuneNazioneNascitaMan(ComuneNazioneNascitaMan comuneNazioneNascitaMan) {
		this.comuneNazioneNascitaMan = comuneNazioneNascitaMan;
	}

	public ResidenzaCsaMan getResidenzaCsaMan() {
		return residenzaCsaMan;
	}

	public void setResidenzaCsaMan(ResidenzaCsaMan residenzaCsaMan) {
		this.residenzaCsaMan = residenzaCsaMan;
	}
	
	@Override
	public List<SelectItem> getLstCittadinanze() {
		if (lstCittadinanze == null) {
			lstCittadinanze = new ArrayList<SelectItem>();
			lstCittadinanze.add(new SelectItem(null, "- seleziona -"));
			try {
				AccessTableNazioniSessionBeanRemote bean = (AccessTableNazioniSessionBeanRemote) ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableNazioniSessionBean");
				List<String> beanLstCittadinanze = bean.getCittadinanze();
				if (beanLstCittadinanze != null) {
					for (String cittadinanza : beanLstCittadinanze) {
						//in AM_TAB_NAZIONI il campo NAZIONALITA ha lunghezza 500, in CS_A_SOGGETTO il campo CITTADINANZA ha lunghezza 255
						if (cittadinanza.length() > 255) {
							cittadinanza = cittadinanza.substring(0, 252) + "...";
						}
						lstCittadinanze.add(new SelectItem(cittadinanza, cittadinanza));
					}
				}
			} catch (NamingException e) {
				addErrorFromProperties("caricamento.error");
				logger.error(e.getMessage(),e);
			}
		}
		return lstCittadinanze;
	}
	
	@Override
	public List<SelectItem> getLstCittadinanzeAcq() {
		if (lstCittadinanzeAcq == null) {
			lstCittadinanzeAcq = new ArrayList<SelectItem>();
			lstCittadinanzeAcq.add(new SelectItem("", "- seleziona -"));
			try {
				CeTBaseObject cet = new CeTBaseObject();
				fillEnte(cet);
				List<CsTbCittadinanzaAcq> lst = confService.getCittadinanzeAcquisite(cet);
				   for(CsTbCittadinanzaAcq p : lst){
					   SelectItem si = new SelectItem(p.getId(),p.getDescrizione());
					   si.setDisabled(!p.getAbilitato());
					   lstCittadinanzeAcq.add(si);
				}
			} catch (Exception e) {
				addErrorFromProperties("caricamento.error");
				logger.error(e.getMessage(),e);
			}
		}
		return lstCittadinanzeAcq;
	}
	
	@Override
	public Long getSoggettoId() {
		return soggettoId;
	}

	public void setSoggettoId(Long soggettoId) {
		this.soggettoId = soggettoId;
	}
	
	public MediciGestBean getMediciGestBean() {
		return mediciGestBean;
	}

	public void setMediciGestBean(MediciGestBean mediciGestBean) {
		this.mediciGestBean = mediciGestBean;
	}

	public StatusGestBean getStatusGestBean() {
		return statusGestBean;
	}

	public void setStatusGestBean(StatusGestBean statusGestBean) {
		this.statusGestBean = statusGestBean;
	}

	public StatoCivileGestBean getStatoCivileGestBean() {
		return statoCivileGestBean;
	}

	public void setStatoCivileGestBean(StatoCivileGestBean statoCivileGestBean) {
		this.statoCivileGestBean = statoCivileGestBean;
	}

	public SoggCatSocialeBean getSoggCatSocialeBean() {
		return soggCatSocialeBean;
	}

	public void setSoggCatSocialeBean(SoggCatSocialeBean soggCatSocialeBean) {
		this.soggCatSocialeBean = soggCatSocialeBean;
	}

	public CsASoggettoLAZY getSoggetto() {
		Long anagId = soggetto.getAnagraficaId();
		CsASoggettoLAZY csSogg = soggetto;
		if(anagId!=null){
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);	
			dto.setObj(anagId);
		    csSogg = soggettoService.getSoggettoById(dto);
		    csSogg = csSogg!=null ? csSogg : soggetto;
	   }
		return csSogg;
	}
	
	/*public CsASoggettoLAZY getSoggettoLAZYSalvato() {
		Long anagId = soggetto.getAnagraficaId();
		CsASoggettoLAZY csSogg = soggetto;
		if(anagId!=null){
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);	
			dto.setObj(anagId);
		    csSogg = soggettoService.getSoggettoById(dto);
		    csSogg = csSogg!=null ? csSogg : soggetto;
	   }
		return csSogg;
	}*/

	public boolean isVisDataAperturaFascicoloFam() {
		return visDataAperturaFascicoloFam;
	}

	public void caricaSoggCatSocialeBean(){
		this.soggCatSocialeBean.carica(soggetto);
	}
	public String getWidgetVar() {
		return "dlgAnagraficaMod";
	}
	//SISO-1127
	public String getWidgetVarGit() {
		return "dlgAnagraficaModGit";
	}
	
	public String getAccessoModifica() {
		return accessoModifica;
	}

	public void setAccessoModifica(String accessoModifica) {
		this.accessoModifica = accessoModifica;
	}

	private void loadListaForzatureAnagrafiche(){
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		dto.setObj(soggettoId);
		this.lstStoricoForzature = csAnagraficaLogService.findStoricoBySoggetto(dto);
	}

	public List<CsAAnagraficaLog> getLstStoricoForzature() {
		return lstStoricoForzature;
	}

	public DatiPrivacyPdfDTO getDatiReportPrivacy() {
		DatiPrivacyPdfDTO dati = new DatiPrivacyPdfDTO();
		dati.setAnonimo(getDatiAnaBean().isAnonimo());
		dati.setAccessoComune(getCurrentOpSettore().getCsOSettore().getCsOOrganizzazione().getNome());
		if(!this.getDatiAnaBean().isAnonimo()){
			dati.setCognome(this.getDatiAnaBean().getCognome());
			dati.setNome(this.getDatiAnaBean().getNome());
			dati.setCf(this.getDatiAnaBean().getCodiceFiscale());
			dati.setData_nascita(ddMMyyyy.format(getDatiAnaBean().getDataNascita()));
			dati.setLuogo_nascita(this.getComuneNazioneNascitaMan().getDescrizioneLuogoDiNascita());
			dati.setSesso(this.getDatiAnaBean().getDatiSesso().getSesso());
			dati.setTel(this.getDatiAnaBean().getTelefono()!=null ? this.getDatiAnaBean().getTelefono() : "");
			dati.setCel(this.getDatiAnaBean().getCellulare()!=null ? this.getDatiAnaBean().getCellulare() : "");
			dati.setEmail(this.getDatiAnaBean().getEmail()!=null ? this.getDatiAnaBean().getEmail() : "");
			
			CsAIndirizzo resi = this.getResidenzaCsaMan().getIndirizzoResidenzaAttivo();
			CsAAnaIndirizzo resana = resi!=null ? resi.getCsAAnaIndirizzo() : null;
			dati.setResidenza(resana!=null ? resana.getLabelIndirizzoCompleto() : "");
			
			ValiditaCompBaseBean med = this.getMediciGestBean().getComponenteAttivo();
			dati.setMedico(med!=null ? med.getDescrizione() : null);
		}
		return dati;
	}
	
	//SISO-1127
	private Date stringToDate(String dateValue) {
		Date parsed;
		try {
		    SimpleDateFormat format =
		        new SimpleDateFormat("dd-MM-yyyy");
		    parsed = format.parse(dateValue);
		}
		catch(ParseException pe) {
		    throw new IllegalArgumentException(pe);
		}
		return parsed;
	}
	
	public void impostaMedicoPersonaUmbria(String codRegMedico, Date dataScelta, Date dataRevoca) throws CsUiCompException {
		CsCMedico medico = aggiungiMedicoUmbria(codRegMedico);
		addToListaMediciSoggetto(medico, dataScelta, dataRevoca);
	}
	
	private CsCMedico aggiungiMedicoUmbria(String codRegMedico) throws CsUiCompException{
		CsCMedico medico = null;
		if (codRegMedico != null && !codRegMedico.trim().isEmpty()) {
			medico = getMedicoByCodRegionale(codRegMedico);
			if(medico==null){
				BaseDTO dto2 = new BaseDTO();
				fillEnte(dto2);
				dto2.setObj(codRegMedico);
				medico = mediciSessionBean.addNewMedicoUmbria(dto2);
			}
		}
		return medico;
	}
	
	public void impostaMedicoPersonaMarche(PersonaDettaglio p) throws CsUiCompException {
		if (p.getCodfisc() != null && !p.getCodfisc().trim().isEmpty()) {
			this.aggiungiMedicoMarche(p.getMedicoCodiceFiscale(), p.getMedicoCognome(), p.getMedicoNome());
			CsCMedico medico = getMedicoByCodRegionale(p.getMedicoCodiceFiscale());
			addToListaMediciSoggetto(medico, p.getMedicoDataScelta(), p.getMedicoDataRevoca()); //formato dd/MM/yyyy
		}
	}


	private CsCMedico aggiungiMedicoMarche(String cf, String cognome, String nome) throws CsUiCompException {
		CsCMedico medico = null;
		if (!StringUtils.isBlank(cf)) {
			medico = getMedicoByCodRegionale(cf);
			if (medico == null) {
				it.webred.cs.csa.ejb.dto.BaseDTO dto2 = new it.webred.cs.csa.ejb.dto.BaseDTO();
				fillEnte(dto2);
				dto2.setObj(cf);
				dto2.setObj2(cognome);
				dto2.setObj3(nome);
				medico = mediciSessionBean.addNewMedico(dto2);
			}
		}
		return medico;
	}
	
	public void addToListaMediciSoggetto(CsCMedico medico, Date dataInizio, Date dataFine) {

		if (medico != null) {// se ho trovato il medico sulla vista e l'ho
								// aggiunto a CsCMedici, lo setto sulla scheda
			getMediciGestBean().getLstItems();
			String denominazione = (medico.getCognome() == null ? "" : medico.getCognome()).trim();
			String nome = (medico.getNome() == null ? "" : medico.getNome()).trim();
			if (!nome.equals("")) {
				if (!denominazione.equals("")) {
					denominazione += " ";
				}
				denominazione += nome;
			}
			
			String item = String.valueOf(medico.getId()) + "|" + denominazione;
			item+= dataInizio!=null ? "|" + ddMMyyyy.format(dataInizio) : "";
			item+= dataFine!=null   ? "|" + ddMMyyyy.format(dataFine) : "";
			
			getMediciGestBean().setItemSelezionato(item);
			getMediciGestBean().aggiungiSelezionato();
			getMediciGestBean().salva();
		}
	}
}
