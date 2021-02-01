package it.webred.cs.csa.web.manbean.fascicolo.schedeSegr;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.StreamedContent;

import it.webred.cs.csa.ejb.client.AccessTableConfigurazioneSessionBeanRemote;
import it.webred.cs.csa.ejb.client.AccessTableSchedaSegrSessionBeanRemote;
import it.webred.cs.csa.ejb.dto.BaseDTO;
import it.webred.cs.csa.web.manbean.report.ReportBean;
import it.webred.cs.data.DataModelCostanti.PermessiSchedeSegr;
import it.webred.cs.data.DataModelCostanti.Scheda;
import it.webred.cs.data.DataModelCostanti.TabUDC;
import it.webred.cs.data.DataModelCostanti.TipoDiario;
import it.webred.cs.data.model.CsDValutazione;
import it.webred.cs.data.model.CsOSettore;
import it.webred.cs.data.model.CsSsSchedaSegr;
import it.webred.cs.data.model.CsTbCittadinanzaAcq;
import it.webred.cs.data.model.CsTbStatoCivile;
import it.webred.cs.data.model.CsTbTipoRapportoCon;
import it.webred.cs.data.model.CsTbTipologiaFamiliare;
import it.webred.cs.jsf.interfaces.ISchedaSegr;
import it.webred.cs.jsf.manbean.ConsensoPrivacyMan;
import it.webred.cs.jsf.manbean.FormazioneLavoroMan;
import it.webred.cs.jsf.manbean.superc.CsUiCompBaseBean;
import it.webred.cs.json.ISchedaValutazione;
import it.webred.cs.json.OrientamentoLavoro.IOrientamentoLavoro;
import it.webred.cs.json.OrientamentoLavoro.OrientamentoLavoroManBaseBean;
import it.webred.cs.json.abitazione.IAbitazione;
import it.webred.cs.json.familiariConviventi.IFamConviventi;
import it.webred.cs.json.intermediazione.IIntermediazioneAb;
import it.webred.cs.json.intermediazione.IntermediazioneManBaseBean;
import it.webred.cs.json.mediazioneculturale.IMediazioneCult;
import it.webred.cs.json.mediazioneculturale.MediazioneCultManBaseBean;
import it.webred.cs.json.orientamentoistruzione.IOrientamentoIstruzione;
import it.webred.cs.json.orientamentoistruzione.OrientamentoIstruzioneManBaseBean;
import it.webred.cs.json.serviziorichiestocustom.IServizioRichiestoCustom;
import it.webred.cs.json.serviziorichiestocustom.ServizioRichiestoCustomManBaseBean;
import it.webred.cs.json.stranieri.IStranieri;
import it.webred.ct.config.luoghi.LuoghiService;
import it.webred.ct.config.model.AmTabComuni;
import it.webred.ejb.utility.ClientUtility;
import it.webred.ss.data.model.SsAnagrafica;
import it.webred.ss.data.model.SsDiario;
import it.webred.ss.data.model.SsInterventiSchede;
import it.webred.ss.data.model.SsInterventoEconomico;
import it.webred.ss.data.model.SsMotivazioniSchede;
import it.webred.ss.data.model.SsScheda;
import it.webred.ss.data.model.SsSchedaRiferimento;
import it.webred.ss.data.model.SsSchedaSegnalato;
import it.webred.ss.ejb.client.SsSchedaSessionBeanRemote;
import it.webred.ss.ejb.dto.report.DatiPrivacyPdfDTO;

@ManagedBean
@SessionScoped
public class SchedaSegrBean extends CsUiCompBaseBean implements ISchedaSegr {
	
	protected HashMap<String, String> mappaLabelUDC;
	private SsScheda ssScheda;
	private SsSchedaSegnalato ssSchedaSegnalato;
	private FormazioneLavoroMan formLavoroSegnalato;
	private IStranieri stranieriMan;
	private IAbitazione abitazioneMan;
	private IFamConviventi famConviventiMan;
	private ConsensoPrivacyMan consensoPrivacyMan;

	private List<ISchedaValutazione> lstServiziRichiesti;// SISO-438-Possibilità di allegare documenti in UdC
	
	private List<SsDiario> notediario;
	
	//private CsTbTipologiaFamiliare tipoFamigliaSegnalato;
	private CsTbCittadinanzaAcq cittadinanzaAcq;
	
	private AmTabComuni comuneSegnalante;
	private List<SsMotivazioniSchede> listaMotivazioni;
	private List<SsDiario> listaDiari;
	private List<SsInterventiSchede> listaInterventi;
	private List<SsInterventoEconomico> listaInterventiEcon;
	private List <SsSchedaRiferimento> listaRiferimenti;//SISO-947
	
	private SsSchedaSessionBeanRemote ssSchedaSegrService = (SsSchedaSessionBeanRemote) getEjb("SegretariatoSoc", "SegretariatoSoc_EJB", "SsSchedaSessionBean");
	private	AccessTableSchedaSegrSessionBeanRemote schedaSegrService = (AccessTableSchedaSegrSessionBeanRemote) getEjb("CarSocialeA", "CarSocialeA_EJB", "AccessTableSchedaSegrSessionBean");
	private LuoghiService luoghiService = (LuoghiService) getEjb("CT_Service", "CT_Config_Manager", "LuoghiServiceBean");

	public void initialize(Long sId) {
		
		logger.debug("*** INIZIO chiamata SchedaSegrBean.initialize");
		mappaLabelUDC = CsUiCompBaseBean.getMappaLabelUDC();
		ssScheda = null;
		ssSchedaSegnalato = null;
		comuneSegnalante = new AmTabComuni();
		
/*		lavoroSegnalato = new CsTbCondLavoro();
		professioneSegnalato = new CsTbProfessione();
		tipoFamigliaSegnalato = new CsTbTipologiaFamiliare();*/

		//Serve per la stampa
		if(sId!=null){
			BaseDTO dto = new BaseDTO();
			fillEnte(dto);
			dto.setObj(sId);
			CsSsSchedaSegr csSsSchedaSegr = schedaSegrService.findSchedaSegrCreataByIdAnagrafica(dto);
			if(csSsSchedaSegr != null) 
				caricaDettagliSchedaSegr(csSsSchedaSegr.getId());
		}else{
			logger.warn("SchedaSegrBean- initialize() - IdAnagrafica non valorizzato.");
		}
		
		logger.debug("*** FINE chiamata SchedaSegrBean.initialize");
	}
	
	public void caricaDettagliSchedaSegr(Long idSchedaSegr) {
		
		try {
			it.webred.ss.ejb.dto.BaseDTO bDto = new it.webred.ss.ejb.dto.BaseDTO();
			fillEnte(bDto);
			bDto.setObj(idSchedaSegr);
			ssScheda = ssSchedaSegrService.readScheda(bDto);

			if (ssScheda != null) {
				bDto.setObj(ssScheda.getSegnalato());
				ssSchedaSegnalato = ssSchedaSegrService.readSegnalatoById(bDto);

				if (ssScheda.getSegnalante() != null && ssScheda.getSegnalante().getComune() != null) {
					comuneSegnalante = luoghiService.getComuneItaByIstat(ssScheda.getSegnalante().getComune());
				}

				if (ssScheda.getMotivazione() != null) {
					bDto.setObj(ssScheda.getMotivazione());
					listaMotivazioni = ssSchedaSegrService.readMotivazioniScheda(bDto);
				}

				if (ssSchedaSegnalato != null && ssSchedaSegnalato.getAnagrafica() != null) {
					bDto.setObj(ssSchedaSegnalato.getAnagrafica());
					bDto.setObj2(ssScheda.getAccesso().getSsRelUffPcontOrg().getSsOOrganizzazione().getId());
					listaDiari = ssSchedaSegrService.readDiarioSoggettoEnte(bDto);
					listaInterventiEcon = ssSchedaSegrService.readInterventiEconomici(bDto);
					
					formLavoroSegnalato = new FormazioneLavoroMan();
					formLavoroSegnalato.setIdCondLavorativa(ssSchedaSegnalato.getLavoro()!=null ? new BigDecimal(ssSchedaSegnalato.getLavoro()) : null);
					formLavoroSegnalato.setIdProfessione(ssSchedaSegnalato.getProfessione()!=null ? new BigDecimal(ssSchedaSegnalato.getProfessione()) : null);
					formLavoroSegnalato.setIdTitoloStudio(ssSchedaSegnalato.getTitoloStudioId());
					formLavoroSegnalato.setIdSettoreImpiego(ssSchedaSegnalato.getSettImpiegoId());
					
					stranieriMan = super.getSchedaJsonStranieri(ssScheda.getId());
					abitazioneMan = super.getSchedaJsonAbitazione(ssScheda.getId());
					famConviventiMan = super.getSchedaJsonFamConviventi(ssScheda.getId());
							
					List<CsDValutazione> res = getSchedeJsonInterventiCustom(ssScheda);
					lstServiziRichiesti = new ArrayList<ISchedaValutazione>();
					for(CsDValutazione val : res){

						if(TipoDiario.ABITAZIONE_ID==val.getCsDDiario().getCsTbTipoDiario().getId()){
							IIntermediazioneAb intermediazioneAbMan = (IIntermediazioneAb)IntermediazioneManBaseBean.initByModel(val);
							if (intermediazioneAbMan!=null) {
								lstServiziRichiesti.add(intermediazioneAbMan);
							}
						}
						if(TipoDiario.ORIENTAMENTO_LAVORO_ID==val.getCsDDiario().getCsTbTipoDiario().getId()){
							IOrientamentoLavoro orientamentoLavMan = (IOrientamentoLavoro)OrientamentoLavoroManBaseBean.initByModel(val);
							if (orientamentoLavMan!=null) {
								lstServiziRichiesti.add(orientamentoLavMan);
							}
						}
						if(TipoDiario.MEDIAZIONE_CULT_ID==val.getCsDDiario().getCsTbTipoDiario().getId()){
							IMediazioneCult mediazioneCultMan = MediazioneCultManBaseBean.initByModel(val);
							if (mediazioneCultMan!=null) {
								lstServiziRichiesti.add(mediazioneCultMan);
							} 
						}
						if(TipoDiario.ORIENTAMENTO_ISTRUZIONE_ID==val.getCsDDiario().getCsTbTipoDiario().getId()){
							IOrientamentoIstruzione orientamentoIstruzioneMan = (IOrientamentoIstruzione) OrientamentoIstruzioneManBaseBean.initByModel(val);
							if (orientamentoIstruzioneMan!=null) {
								lstServiziRichiesti.add(orientamentoIstruzioneMan);
							}
						}
						if(TipoDiario.RICHIESTA_SERVIZIO_ID==val.getCsDDiario().getCsTbTipoDiario().getId()){
							IServizioRichiestoCustom iServizioRichiestoCustom = 
									(IServizioRichiestoCustom) ServizioRichiestoCustomManBaseBean.initByModel(val, null);
							lstServiziRichiesti.add(iServizioRichiestoCustom);
						}
					}
					
			       // tipoFamigliaSegnalato = this.valorizzaTipoFamiglia(ssSchedaSegnalato.getTipologia_familiare());

			        cittadinanzaAcq = this.valorizzaCittadinanzaAcq(ssSchedaSegnalato.getAnagrafica().getCittadinanzaAcq());
				}
			
				//SISO-947 dati riferimenti
					
				listaRiferimenti = new ArrayList<SsSchedaRiferimento>();
				listaRiferimenti.add(ssScheda.getRiferimento());
				listaRiferimenti.add( ssScheda.getRiferimento2());
			    listaRiferimenti.add(ssScheda.getRiferimento3());
								
				if(ssScheda.getInterventi() != null) {
					bDto.setObj(ssScheda.getInterventi());
					listaInterventi = ssSchedaSegrService.readInterventiScheda(bDto);
				}
				
				Long orgAccessoId = ssScheda.getAccesso().getSsRelUffPcontOrg().getId().getOrganizzazioneId();
				boolean beneficiarioRdC = this.verificaBeneficiarioRdC(ssSchedaSegnalato.getAnagrafica().getCf());
				consensoPrivacyMan = new ConsensoPrivacyMan(ssSchedaSegnalato.getAnagrafica().getCf(), orgAccessoId, ssSchedaSegnalato.getAnagrafica().isAnonimo(), beneficiarioRdC);
				consensoPrivacyMan.setSchedaUdcId(ssScheda.getId());
				
				//FINE SISO 961
				// dati diario sociale
	        	fillEnte(bDto);
	        	bDto.setObj(ssSchedaSegnalato.getAnagrafica().getCf());
	        	List<SsAnagrafica> anagrafiche = ssSchedaSegrService.readAnagraficheByCf(bDto);
	        	
	        	notediario = new ArrayList<SsDiario>();
	        	for(SsAnagrafica ana: anagrafiche){
	        		bDto.setObj(ana);
	        		bDto.setObj2(ssScheda.getAccesso().getSsRelUffPcontOrg().getSsOOrganizzazione().getId());
	        		List<SsDiario> lstDiari = ssSchedaSegrService.readDiarioSoggettoEnte(bDto);
	        		for(SsDiario diario : lstDiari){
	        			if(!canReadNotaDiario(diario, ssScheda.getAccesso().getOperatore()))
	        				diario.setNota(" ** L'operatore corrente non è autorizzato a leggere il contenuto della nota **");
	        			notediario.add(diario); 
	        		}
	        	}
	        	
			}else{
				logger.warn("Nessuna Scheda UdC con id:"+idSchedaSegr);
			}
		} catch (Exception e) {
			addErrorFromProperties("caricamento.error");
			logger.error(e.getMessage(),e);
		}
	}
		
	private boolean canReadNotaDiario(SsDiario nota, String operatoreAccesso){
		return canReadNotaDiario(nota, operatoreAccesso, getCurrentOpSettore().getCsOSettore().getCsOOrganizzazione().getId());
	}
	
	
	private boolean canReadNotaDiario(SsDiario nota, String operatoreAccesso, Long organizzazioneId){
		String opCorrente = getCurrentOpSettore().getCsOOperatore().getUsername();
		if(nota.getPubblica()) //la nota è pubblica
			return true;
		
		//responsabile dell'organizzazione in cui è stata inserita la nota
		if(isResponsabileSsEnte(nota.getEnte().getCodRouting()))
			return true;
		
		//l'utente che ha scritto la nota è l'operatore corrente
		if(nota.getAutore().equals(opCorrente))
			return true;
		
		//l'operatore che risulta registrato in SS_SCHEDA_ACCESSO è l'utente corrente
		if(operatoreAccesso.equals(opCorrente)) 
			return true;

		//l'operatore possiede il permesso di leggere i DIARI in UDC e si è loggato con la stessa organizzazione di creazione della nota
		if(canReadDiario() && nota.getEnte().getId()== organizzazioneId)
			return true;
		
		return false;
	}
	
	public boolean canReadDiario() {
		return checkPermessoSS("segrsoc-readDiario");
	}
	
	public boolean isRenderSchedaSegr() {
		return checkPermesso(PermessiSchedeSegr.ITEM, PermessiSchedeSegr.VISUALIZZA_SCHEDE_SEGR) && ssScheda != null;
	}

	@Override
	public SsScheda getSsScheda() {
		return ssScheda;
	}

	public void setSsScheda(SsScheda ssScheda) {
		this.ssScheda = ssScheda;
	}

	@Override
	public SsSchedaSegnalato getSsSchedaSegnalato() {
		return ssSchedaSegnalato;
	}

	public void setSsSchedaSegnalato(SsSchedaSegnalato ssSchedaSegnalato) {
		this.ssSchedaSegnalato = ssSchedaSegnalato;
	}

	@Override
	public AmTabComuni getComuneSegnalante() {
		return comuneSegnalante;
	}

	public void setComuneSegnalante(AmTabComuni comuneSegnalante) {
		this.comuneSegnalante = comuneSegnalante;
	}

	@Override
	public List<SsMotivazioniSchede> getListaMotivazioni() {
		return listaMotivazioni;
	}

	public void setListaMotivazioni(List<SsMotivazioniSchede> listaMotivazioni) {
		this.listaMotivazioni = listaMotivazioni;
	}

	@Override
	public List<SsDiario> getListaDiari() {
		return listaDiari;
	}

	public void setListaDiari(List<SsDiario> listaDiari) {
		this.listaDiari = listaDiari;
	}

	@Override
	public List<SsInterventiSchede> getListaInterventi() {
		return listaInterventi;
	}

	public void setListaInterventi(List<SsInterventiSchede> listaInterventi) {
		this.listaInterventi = listaInterventi;
	}

	@Override
	public List<SsInterventoEconomico> getListaInterventiEcon() {
		return listaInterventiEcon;
	}

	public void setListaInterventiEcon(List<SsInterventoEconomico> listaInterventiEcon) {
		this.listaInterventiEcon = listaInterventiEcon;
	}

/*	public CsTbTipologiaFamiliare getTipoFamigliaSegnalato() {
		return tipoFamigliaSegnalato;
	}

	public void setTipoFamigliaSegnalato(
			CsTbTipologiaFamiliare tipoFamigliaSegnalato) {
		this.tipoFamigliaSegnalato = tipoFamigliaSegnalato;
	}*/
	
		
	private CsTbTipologiaFamiliare valorizzaTipoFamiglia(String  codfam){
    	try{
    		AccessTableConfigurazioneSessionBeanRemote configService = (AccessTableConfigurazioneSessionBeanRemote)
    				ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
    	if(codfam!=null){
    		BaseDTO d = new BaseDTO();
    		CsUiCompBaseBean.fillEnte(d);
    		d.setObj(new Long(codfam));
    		return configService.getTipologiaFamiliareById(d);
    	}
		
    	} catch(Exception e) {
    		CsUiCompBaseBean.logger.error(e.getMessage(), e);
		}
    	return new CsTbTipologiaFamiliare();
    }
		
	private CsTbCittadinanzaAcq valorizzaCittadinanzaAcq(Long  id){
    	try{
    		AccessTableConfigurazioneSessionBeanRemote configService = (AccessTableConfigurazioneSessionBeanRemote)
    				ClientUtility.getEjbInterface("CarSocialeA", "CarSocialeA_EJB", "AccessTableConfigurazioneSessionBean");
    	if(id!=null){
    		BaseDTO d = new BaseDTO();
    		CsUiCompBaseBean.fillEnte(d);
    		d.setObj(id);
    		return configService.getCittadinanzaAcqById(d);
    	}
		
    	} catch(Exception e) {
    		CsUiCompBaseBean.logger.error(e.getMessage(), e);
		}
    	return new CsTbCittadinanzaAcq();
    }
	
	public CsTbCittadinanzaAcq getCittadinanzaAcq() {
		return cittadinanzaAcq;
	}

	public void setCittadinanzaAcq(CsTbCittadinanzaAcq cittadinanzaAcq) {
		this.cittadinanzaAcq = cittadinanzaAcq;
	}

	public FormazioneLavoroMan getFormLavoroSegnalato() {
		return formLavoroSegnalato;
	}

	public void setFormLavoroSegnalato(FormazioneLavoroMan formLavoroSegnalato) {
		this.formLavoroSegnalato = formLavoroSegnalato;
	}

	public IStranieri getStranieriMan() {
		return stranieriMan;
	}

	public void setStranieriMan(IStranieri stranieriMan) {
		this.stranieriMan = stranieriMan;
	}

	public IAbitazione getAbitazioneMan() {
		return abitazioneMan;
	}

	public void setAbitazioneMan(IAbitazione abitazioneMan) {
		this.abitazioneMan = abitazioneMan;
	}

	public IFamConviventi getFamConviventiMan() {
		return famConviventiMan;
	}

	public void setFamConviventiMan(IFamConviventi famConviventiMan) {
		this.famConviventiMan = famConviventiMan;
	}
    
	@Override
	public String getStatoCivileSegnalante() {
		return getDescrizioneStatoCivile(ssScheda.getSegnalante().getCodStatoCivile());
	}

	@Override
	public String getStatoCivileRiferimento() {
		return getDescrizioneStatoCivile(ssScheda.getRiferimento().getCodStatoCivile());
	}
	
	@Override
	public String getRelazioneSegnalante() {
		return getDescrizioneRelazione(ssScheda.getSegnalante().getRelazioneId());
	}

	@Override
	public String getRelazioneRiferimento() {
		return getDescrizioneRelazione(ssScheda.getRiferimento().getRelazioneId());
	}

	@Override
	public String getInviatoDaAccesso(){
		return getSettore(ssScheda!=null ? ssScheda.getAccesso().getInviato_da() : null);
	}

	private String getDescrizioneStatoCivile(String codStatoCivile) {
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		if(codStatoCivile!=null){
			dto.setObj(codStatoCivile);
			CsTbStatoCivile csTbStatoCivile = confService.getStatoCivileByCodice(dto);
			return csTbStatoCivile!=null ? csTbStatoCivile.getDescrizione() : "";
		}else return "";
	}
	
	private String getSettore(String codice) {
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		if(codice!=null){
			dto.setObj(new Long(codice));
			CsOSettore tb = confService.getSettoreById(dto);
			return tb!=null ? tb.getNome() : "";
		}
		return null;
	}
	
	private String getDescrizioneRelazione(Long codice) {
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		if(codice!=null){
			dto.setObj(codice);
			CsTbTipoRapportoCon tb = confService.getTipoRapportoConByCodice(dto);
			return tb!=null ? tb.getDescrizione() : "";
		}else return "";
	}
	
	private CsOSettore getDescrizioneSettore(Long codice) {
		BaseDTO dto = new BaseDTO();
		fillEnte(dto);
		if(codice!=null){
			dto.setObj(codice);
			return confService.getSettoreById(dto);
		}
		return null;
	}

	public List<SsDiario> getNotediario() {
		return notediario;
	}

	public void setNotediario(List<SsDiario> notediario) {
		this.notediario = notediario;
	}
	
	public String getMSG_INFO_NOTA_PRIVATA() {
		String msg = "Le note private possono essere visualizzare dall'utente corrente solo se: ";
		msg +="<ul>";
		msg+="<li>é il responsabile dell'organizzazione in cui è stata inserita la nota</li>";
		msg+="<li>ha creato la scheda</li>";
		msg+="<li>ha inserito la nota</li>";
		msg+="<li>posside il permesso di leggere i diari ed è attualmente autenticato nella stessa oganizzazione in cui è stata inserita la nota.</li>";
		msg+="</ul>";
		return msg;
		
	}

	@Override
	public String getEnteSegnalante() {
		CsOSettore s = getDescrizioneSettore(ssScheda.getSegnalante().getCsOSettoreId());
		return s!=null ? s.getNome() : ssScheda.getSegnalante().getEnte_servizio();
	}

	@Override
	public boolean isHideSegnalante() {
		return ssScheda!=null && Scheda.Interlocutori.UTENTE.equalsIgnoreCase(ssScheda.getAccesso().getInterlocutore()) && ssScheda.getSegnalante()==null;
	}
    //SISO-947
	@Override
	public List<SsSchedaRiferimento> getListaRiferimenti() {
		return listaRiferimenti;
	}

	public void setListaRiferimenti(List<SsSchedaRiferimento> listaRiferimenti) {
		this.listaRiferimenti = listaRiferimenti;
	}

	public String titoloTabRiferimento(SsSchedaRiferimento riferimento, Integer numRiferimento){
		String titolo = "Riferimento " + Integer.toString(numRiferimento);
					
		if (riferimento != null) {
			if (riferimento.getNome() != null
					&& !riferimento.getNome().trim().equals("")
					&& riferimento.getCognome() != null
					&& !riferimento.getCognome().trim().equals("")) {

				titolo = riferimento.getCognome() + " " + riferimento.getNome();
			}
		}
		return titolo;
		
	}
	
	public String relazioneRif(SsSchedaRiferimento riferimento) {
		String relazione = "";
		if (riferimento != null) {
			relazione = getDescrizioneRelazione(riferimento.getRelazioneId());
		}
		return relazione;
	}
	
	public String statoCivileRif(SsSchedaRiferimento riferimento) {
		String statoCivile = "";
		if (riferimento != null) {
			statoCivile = getDescrizioneStatoCivile(riferimento.getCodStatoCivile());
		}
		return statoCivile;
	}
	
	@Override
	public String getLabelAccesso(){
		if(mappaLabelUDC==null) mappaLabelUDC = CsUiCompBaseBean.getMappaLabelUDC();
		return this.mappaLabelUDC.get(TabUDC.ACCESSO_TAB);
	}
	@Override
	public String getLabelSegnalante(){
		if(mappaLabelUDC==null)mappaLabelUDC = CsUiCompBaseBean.getMappaLabelUDC();
		return this.mappaLabelUDC.get(TabUDC.SEGNALANTE_TAB);
	}
	@Override
	public String getLabelSegnalato(){
		if(mappaLabelUDC==null)mappaLabelUDC = CsUiCompBaseBean.getMappaLabelUDC();
		return this.mappaLabelUDC.get(TabUDC.SEGNALATO_TAB);
	}
	@Override
	public String getLabelRiferimento(){
		if(mappaLabelUDC==null)mappaLabelUDC = CsUiCompBaseBean.getMappaLabelUDC();
		return this.mappaLabelUDC.get(TabUDC.RIFERIMENTO_TAB);
	}
	@Override
	public String getLabelMotivazione(){
		if(mappaLabelUDC==null)mappaLabelUDC = CsUiCompBaseBean.getMappaLabelUDC();
		return this.mappaLabelUDC.get(TabUDC.MOTIVAZIONE_TAB);
	}
	@Override
	public String getLabelInterventi(){
		if(mappaLabelUDC==null)mappaLabelUDC = CsUiCompBaseBean.getMappaLabelUDC();
		return this.mappaLabelUDC.get(TabUDC.INTERVENTI_TAB);
	}
	@Override
	public String getLabelChiusura(){
		if(mappaLabelUDC==null)mappaLabelUDC = CsUiCompBaseBean.getMappaLabelUDC();
		return this.mappaLabelUDC.get(TabUDC.CHIUSURA_TAB);
	}

	public ConsensoPrivacyMan getConsensoPrivacyMan() {
		return consensoPrivacyMan;
	}

	public void setConsensoPrivacyMan(ConsensoPrivacyMan consensoPrivacyMan) {
		this.consensoPrivacyMan = consensoPrivacyMan;
	}
	
	public StreamedContent getFilePrivacy(){
		DatiPrivacyPdfDTO dati = null;
		if(this.ssScheda!=null) {
			it.webred.ss.ejb.dto.BaseDTO dto = new it.webred.ss.ejb.dto.BaseDTO();
			fillEnte(dto);
			dto.setObj(this.getSsScheda().getId());
			dati = this.ssSchedaSegrService.getDatiReportPrivacy(dto);
		}
		ReportBean bean = (ReportBean)CsUiCompBaseBean.getReferencedBean("ReportBean");
		if(bean==null) bean = new ReportBean(); 
		
		return bean.getStampaPrivacy(dati, this.getLabelSegnalante(), this.getLabelSegnalato(), this.getLabelRiferimento());
	}

	public List<ISchedaValutazione> getLstServiziRichiesti() {
		return lstServiziRichiesti;
	}

	public void setLstServiziRichiesti(List<ISchedaValutazione> lstServiziRichiesti) {
		this.lstServiziRichiesti = lstServiziRichiesti;
	}
	
	@Override
	public boolean servizioRendered(ISchedaValutazione schedaValutazione, String tipo){
		if (tipo.equals("IIntermediazioneAb") && schedaValutazione instanceof IIntermediazioneAb ) {
			return true;
		}
		if (tipo.equals("IOrientamentoLavoro") && schedaValutazione instanceof IOrientamentoLavoro ) {
			return true;
		}
		if (tipo.equals("IMediazioneCult") && schedaValutazione instanceof IMediazioneCult ) {
			return true;
		}
		if (tipo.equals("IOrientamentoIstruzione") && schedaValutazione instanceof IOrientamentoIstruzione ) {
			return true;
		}
		if (tipo.equals("IServizioRichiestoCustom") && schedaValutazione instanceof IServizioRichiestoCustom) {
			return true;
		}
		return false; 
	} 
}
