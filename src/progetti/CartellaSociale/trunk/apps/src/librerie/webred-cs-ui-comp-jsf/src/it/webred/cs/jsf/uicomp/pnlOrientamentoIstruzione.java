package it.webred.cs.jsf.uicomp;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;

import javax.faces.component.FacesComponent;
import javax.faces.component.UINamingContainer;
import javax.faces.context.FacesContext;

@FacesComponent(value = "pnlOrientamentoIstruzione")
public class pnlOrientamentoIstruzione extends UINamingContainer implements Serializable {

	private static final long serialVersionUID = -421326517994151L;

	@Override
	public void encodeBegin(FacesContext context) throws IOException {

		Map<String, Object> value = getAttributes();
		super.encodeBegin(context);
	}

	@Override
	public String getId() {
		String id = super.getId();
		return id;
	}

}