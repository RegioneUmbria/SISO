package it.webred.rulengine.brick.loadDwh.load.imf;



import it.webred.rulengine.brick.loadDwh.load.imf.bean.Testata;


import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.ImportFilesWithTipoRecord;
import it.webred.rulengine.exception.RulEngineException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.ibm.icu.util.Calendar;

public class ImpiantiFognariTipoRecordFiles<T extends ImpiantiFognariTipoRecordEnv<Testata>> extends ImportFilesWithTipoRecord<T> {
	
	public ImpiantiFognariTipoRecordFiles(T env) {
		super(env);
	}//-------------------------------------------------------------------------
	
	public String getTipoRecordFromLine(String currentLine)	throws RulEngineException {
		return "";
	}//-------------------------------------------------------------------------

	@Override
	public List<String> getValoriFromLine(String tipoRecord,String currentLine) throws RulEngineException {		
		/*
		while (currentLine.indexOf(",,") > -1) {
			currentLine = currentLine.replace(",,", ",\"\",");
		}
		if (currentLine.endsWith(",")) {
			currentLine += "\"\"";
		}
		*/
		/*
		 * il -1 sotto all'interno dello split server per generare gli item all'interno dell'array anche se vuoti nel file di testo (ad es. ###)
		 */
		String[] values = currentLine.split(";", -1);
		String[] ret = new String[values.length];
		int idx = 0;
		for (String value : values) {
			/*
			if (idx == 0 && value.startsWith("\"")) {
				value = value.substring(1);
			}
			
			if (idx == values.length - 1 && value.endsWith("\"")) {
				value = value.substring(0, value.length() - 1);
			}
			*/
			ret[idx] = value;
			idx++;
		}
		//ret[ret.length - 1] = env.getTestata().getData();
		return Arrays.asList(ret);		
	}//-------------------------------------------------------------------------

	@Override
	public Timestamp getDataExport() throws RulEngineException {
		String dt = env.getTestata().getData();
		Date t = null;
		try {
			t = it.webred.utils.DateFormat.stringToDate(dt, "yyyyMMdd");
		} catch (Exception e) {
			t = new Date(Calendar.getInstance().getTime().getTime());
		}
		return new Timestamp(t.getTime());
	}//-------------------------------------------------------------------------


	@Override
	public void preProcesing(Connection con) throws RulEngineException {
		 
		Statement st = null;
		try {
			st = con.createStatement();
			st.execute(env.createTableUNO);
		} catch (SQLException e1) {
			log.warn("Tabella esiste gia : OK, BENE");
		}
		finally {
			try {
				if (st!=null)
					st.close();
			} catch (SQLException e1) {
			}			
		}
		try {
			st = con.createStatement();
			st.execute(env.RE_IMPIANTI_FOGNARI_IDX);
		} catch (SQLException e1) {
			log.warn("INDICE esiste gia : OK, BENE");
		}
		finally {
			try {
				if (st!=null)
					st.close();
			} catch (SQLException e1) {
			}			
		}
		
	}

	@Override
	public it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.bean.Testata getTestata(String file) throws RulEngineException {			
		Testata t = new Testata();
		try {
			t.setData(file.substring(0, 8));
			return t;	
		} catch (Exception e) {
			log.error("Errore cercando di leggere la testata del file",e);
			throw new RulEngineException("Errore cercando di leggere la testata del file",e);
		}
	}

	@Override
	public void sortFiles(List<String> files) throws RulEngineException {
		Collections.sort(files);		
	}

	@Override
	public String getProvenienzaDefault() throws RulEngineException {
		return "IMF";
	}

	@Override
	public boolean isIntestazioneSuPrimaRiga() throws RulEngineException {
		return true;
	}

	@Override
	public void tracciaFornitura(String file, String cartellaDati, String line)	throws RulEngineException {
		// TODO Auto-generated method stub		
	}
	
}
