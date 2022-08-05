package it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles;

import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImport;

import it.webred.rulengine.brick.loadDwh.load.superc.concrete.ConcreteImportFactory;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.bean.Testata;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImportFilesWithTipoRecord;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.exceptions.TroppiErroriImportFileException;
import it.webred.rulengine.brick.loadDwh.load.util.Util;
import it.webred.rulengine.exception.RulEngineException;


import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


public abstract class ImportFilesWithTipoRecord<T extends EnvImportFilesWithTipoRecord> extends ImportFiles<T> {

	public ImportFilesWithTipoRecord(T env) {
		super(env);
		// TODO Auto-generated constructor stub
	}

	
	public abstract  String getTipoRecordFromLine(String currentLine) throws RulEngineException;

	public abstract  Testata getTestata(String file) throws RulEngineException;

	protected void preProcesingFile(String file) throws RulEngineException {
		env.setTestata(getTestata(file));
	}

	protected void procesingFile(String file, String cartellaDati) throws RulEngineException {
		boolean gestisciTmp = false;
		boolean disabilitaStorico = false;
		if (env.getEnteSorgente().isInReplace())
			gestisciTmp = true;

		if (env.getEnteSorgente().isDisabilitaStorico())
			disabilitaStorico = true;

			
		LinkedHashMap<String, String> tabs = new LinkedHashMap<String, String>();
		ConcreteImport ci = ConcreteImportFactory.getConcreteImport(this);
		tabs = ci.getTabelleAndTipiRecord();


		ArrayList<String> tabeleREValorizzate = new ArrayList<String>();
		// METTO IL FILE NELLA TABELLA TEMPORANEA
		// ogni file lo tratto per tutti  i tipi record che ha
		for (String key : tabs.keySet())
		{
				String tr = tabs.get(key);
				log.info("CARICO " + file + " TipoRecord:" + (tr!=null?tr:""));
				try {
					boolean lettoqualcosa = leggiFile(file, cartellaDati, key, tr, getDataExport());
					if (lettoqualcosa && !tabeleREValorizzate.contains(key))
						tabeleREValorizzate.add(key);
				} catch (TroppiErroriImportFileException e) {
						throw e;
				} catch (Exception e) {
					throw new RulEngineException("Problema in lettura del file " + file + " tr=" + tr,e);
				}
		}

		ci.postLetturaFileAndFilter(cartellaDati, file, gestisciTmp);
		

		
		
		
		log.info("AVVIO NORMALIZZAZIONE " + file);
		boolean norm = ci.normalizza(super.ctx.getBelfiore());
		
		log.info("Aggiornamento contesto con info per eventuale normalizzazione e reverse dati");
		//mettere su ctx le tabelle DWH
		Map m = new HashMap();
		m.put("reverse.tabelleDWH", ci.getTabelleFinaliDWH());
		m.put("reverse.tabs", tabs);
		ctx.addReverseObjects(m);
		
		
		/*
		 * se la normalizzazione non avviene (false) 
		 * allora non faccio neanche tutto il giochino di 
		 * riversamento!!
		 */
		if (norm) {
			// RIVERSO DA TABELLA TMP A PRODUZIONE
			if (gestisciTmp) {
				try {
					ArrayList<String> tabelleDWH = ci.getTabelleFinaliDWH();
					Connection conn = ci.getConnection();
					Util.riversaSetDatiDaTmpADwh(tabelleDWH, conn,disabilitaStorico,env.getEnteSorgente().getInReplaceValue(),ci.getGestoreCorrelazioneVariazioni());
				} catch (Exception e) {
					throw new RulEngineException(e.getMessage(), e);
				}
			}
			
			
			// dopo la normalizzazione setto ad elaborati tutti i record che 
			// sono rimasti con flag a zero per via del fatto che 
			// non avevano chiave
			if (!gestisciTmp) {
				log.info("setReFlagElaboratoConChiaveNullaONoChiave");
				ci.setReFlagElaboratoConChiaveNullaONoChiave();
			}
			else {
				Connection conn = ci.getConnection();
				for (String key : tabs.keySet())
				{
					try {
						if (tabeleREValorizzate.contains(key)) {
							log.info("TRUNCATE TABELLA " + key);
							Util.truncateTable(key, conn);
						}
					} catch (Exception e) {
						log.error("ERRORE IN TRUNCATE TABELLA " + key );
						throw new RulEngineException("ERRORE IN TRUNCATE TABELLA " + key , e);
					}
				}
			}
		}
	}



}