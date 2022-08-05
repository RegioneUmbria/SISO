package it.webred.ct.service.indice.web.soggetto;

import it.webred.ct.data.access.basic.indice.IndiceService;
import it.webred.ct.service.indice.web.IndiceBaseBean;

import javax.ejb.EJB;

public class SoggettoBaseBean extends IndiceBaseBean {

	protected IndiceService indiceService = (IndiceService) getEjb(
			"CT_Service", "CT_Service_Data_Access", "SoggettoServiceBean");

}