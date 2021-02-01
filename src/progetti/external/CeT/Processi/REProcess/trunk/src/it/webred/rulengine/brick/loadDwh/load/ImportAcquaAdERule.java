package it.webred.rulengine.brick.loadDwh.load;

import it.webred.rulengine.Command;
import it.webred.rulengine.Context;
import it.webred.rulengine.Rule;
import it.webred.rulengine.brick.bean.ApplicationAck;
import it.webred.rulengine.brick.bean.CommandAck;
import it.webred.rulengine.brick.bean.ErrorAck;
import it.webred.rulengine.brick.bean.NotFoundAck;
import it.webred.rulengine.brick.loadDwh.load.acqua.AdE.AcquaTipoRecordEnv;
import it.webred.rulengine.brick.loadDwh.load.acqua.AdE.AcquaTipoRecordFiles;
import it.webred.rulengine.brick.loadDwh.load.acqua.AdE.bean.Testata;
import it.webred.rulengine.db.model.RAbNormal;
import it.webred.rulengine.exception.CommandException;
import it.webred.rulengine.impl.bean.BeanCommand;
import it.webred.utils.GenericTuples.T2;

import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;



public class  ImportAcquaAdERule extends Command implements Rule    {

	private static final org.apache.log4j.Logger log = Logger.getLogger(ImportAcquaAdERule.class.getName());

		
	
	public ImportAcquaAdERule(BeanCommand bc, Properties jrulecfg) {
		super(bc, jrulecfg);
		System.setProperty("oracle.jdbc.V8Compatible", "true");
	}




	@SuppressWarnings("unchecked")
	public CommandAck run(Context ctx) throws CommandException {
		
		CommandAck retAck = null;
		
		try {
			AcquaTipoRecordEnv env = new AcquaTipoRecordEnv((String)ctx.get("connessione") , ctx);
			AcquaTipoRecordFiles<AcquaTipoRecordEnv<Testata>> acq = new AcquaTipoRecordFiles<AcquaTipoRecordEnv<Testata>>(env);
			T2<String, List<RAbNormal>> msg = acq.avviaImportazioneFiles();
			
			//se nn ci sono file da elaborare NotFoundAck
			if(msg.firstObj.equals(acq.RETURN_NO_FILE)) {
				retAck = new NotFoundAck(msg.firstObj);
			}
			else {
				retAck = new ApplicationAck(msg.firstObj);
			}
		} catch (Exception e) {
			log.error("Errore grave importando i dati", e);
			retAck = new ErrorAck(e);
		}
		
		return retAck;
	}






	
	

	

	
	
	
}
