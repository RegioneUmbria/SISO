package it.webred.rulengine.brick.loadDwh.load.bucapafm;

import it.webred.rulengine.Context;
import it.webred.rulengine.brick.loadDwh.load.bucapafm.bean.Testata;
import it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.env.EnvImportFilesWithTipoRecord;
import it.webred.rulengine.exception.RulEngineException;


public class BucapAfmTipoRecordEnv<T extends Testata> extends EnvImportFilesWithTipoRecord<T> {

	public String RE_BUCAP_UNO = getProperty("tableUNO.name");
	public String RE_BUCAP_IDX = getProperty("tableUNO.idx");
	
	protected String createTableUNO = getProperty("tableUNO.create_table");

	protected String dirFiles = getConfigProperty("dir.files",super.getCtx().getBelfiore(),super.getCtx().getIdFonte());
	
	public BucapAfmTipoRecordEnv(String connectionName, Context ctx) throws RulEngineException {
		super("52",connectionName, ctx);
	}
	
	@Override
	public String getPercorsoFiles() {
		return dirFiles;
	}

	
}
