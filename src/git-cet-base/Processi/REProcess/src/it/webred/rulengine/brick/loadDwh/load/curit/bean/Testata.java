package it.webred.rulengine.brick.loadDwh.load.curit.bean;

public class Testata implements it.webred.rulengine.brick.loadDwh.load.superc.genericImportFiles.bean.Testata {

	private String data;
	
	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getProvenienza() {
		return "CURIT";
	}
	
}