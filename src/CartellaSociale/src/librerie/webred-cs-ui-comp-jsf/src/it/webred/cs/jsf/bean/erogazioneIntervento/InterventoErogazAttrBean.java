package it.webred.cs.jsf.bean.erogazioneIntervento;

import it.webred.cs.csa.ejb.dto.erogazioni.IntEsegAttrBean;
import it.webred.cs.data.DataModelCostanti;
import it.webred.cs.data.DataModelCostanti.TipoAttributo;
import it.webred.cs.data.model.CsCfgAttr;
import it.webred.cs.data.model.CsCfgAttrOption;
import it.webred.cs.data.model.CsCfgAttrUnitaMisura;
import it.webred.cs.data.model.CsIInterventoEsegValore;
import it.webred.cs.data.model.CsTbUnitaMisura;
import it.webred.utilities.CommonUtils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.commons.lang.StringUtils;

public class InterventoErogazAttrBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean required;
	private String requiredMessage;
	private String Label;
	private Object value;
	private String tooltip;
	//private String clientId;
	
	private TipoAttributo.Enum eType;
	protected List<SelectItem> cmbxValues;
	private List<CsTbUnitaMisura> cmbxUmeasure;
	private String unitaMisura;
	private Long selectedUnitaMisura;
	private boolean multipleUnitaMisura;
	private boolean singleUnitaMisura;
	private boolean abilitato;

	protected boolean persist;
	private List<CsCfgAttrUnitaMisura> listaAttrUnitaMisura;
	protected IntEsegAttrBean attr;
	
	public InterventoErogazAttrBean(InterventoErogazAttrBean source)
	{
		this.attr = source.getAttr();
		this.persist = source.persist;
		this.cmbxUmeasure = source.cmbxUmeasure;
		this.cmbxValues = source.cmbxValues;
		this.multipleUnitaMisura = source.multipleUnitaMisura;
		this.singleUnitaMisura = source.singleUnitaMisura;
		this.Label = source.Label;
		this.value = source.value;
		this.listaAttrUnitaMisura = source.listaAttrUnitaMisura;
		this.eType = source.eType;
		this.requiredMessage = source.requiredMessage;
		this.required = source.required;
		this.unitaMisura = source.unitaMisura;
		this.selectedUnitaMisura = source.selectedUnitaMisura;
		this.tooltip = source.tooltip;
		this.abilitato = source.isAbilitato();
	}
	public InterventoErogazAttrBean( IntEsegAttrBean attr ) {
		this.attr = attr;
		this.listaAttrUnitaMisura = attr.getAttrUnitaMisuras();
		
		this.Label = attr.getCsCfgAttr().getLabel();
		this.value = attr.getCsCfgAttr().getValoreDefault();
		
		this.persist = true;
		this.eType = DataModelCostanti.TipoAttributo.ToEnum(attr.getCsCfgAttr().getTipoAttr());
		this.requiredMessage = attr.getCsCfgAttr().getMessaggioObbligatorio();
		this.required = StringUtils.isNotEmpty(requiredMessage);
		
		this.cmbxValues = new ArrayList<SelectItem>();
		if (isList())
			for (CsCfgAttrOption itOption : attr.getCsCfgAttr().getCsCfgAttrOptions())
				cmbxValues.add(new SelectItem(itOption.getId(), itOption.getValore()));

		createTooltip();
		loadUnitaMisura();
		this.abilitato=attr.getAbilitato()!=null ? attr.getAbilitato().booleanValue() : false;
	}

	public InterventoErogazAttrBean( CsIInterventoEsegValore valore, boolean calcTotale ) {

		attr = new IntEsegAttrBean(valore.getCsAttributoUnitaMisura(), calcTotale);
		
		Label = attr.getCsCfgAttr().getLabel();
		value = (String)valore.getValore();
		eType = TipoAttributo.ToEnum(attr.getCsCfgAttr().getTipoAttr());

		persist = valore != null;

		createTooltip();
		loadUnitaMisura();
	}
	
	public InterventoErogazAttrBean( TipoAttributo.Enum eType, Object value) {
		this.persist = false;
		this.eType = eType;
		this.value = value;
	}

	public void loadUnitaMisura() {
		this.multipleUnitaMisura = false;
		this.singleUnitaMisura = false;
		this.unitaMisura = "";
		this.cmbxUmeasure = new ArrayList<CsTbUnitaMisura>();
		
		if (attr.getUnitaMisuras() != null && attr.getUnitaMisuras().size() > 0){
			if (attr.getUnitaMisuras().size() == 1) {
				if( attr.getUnitaMisuras().get(0) != null )
				{
					this.unitaMisura = attr.getUnitaMisuras().get(0).getValore();
					this.selectedUnitaMisura = attr.getUnitaMisuras().get(0).getId();
					singleUnitaMisura = true;
				}
			}
			else {
				cmbxUmeasure.addAll(attr.getUnitaMisuras());
				multipleUnitaMisura = true;
			}
		}
	}
	
	public String getClientId() {
		return java.util.UUID.randomUUID().toString().replace("-", "");
	}
	
	public String getUnitaMisuraSelezionataValore(){
		CsTbUnitaMisura um = getUnitaMisuraSelezionata();
		if( um != null )
			return um.getValore();
		return null;
	}
	
	protected CsTbUnitaMisura getUnitaMisuraSelezionata(){
		if( multipleUnitaMisura && selectedUnitaMisura != null ){
			for( CsTbUnitaMisura it : cmbxUmeasure ){
				if( it.getId() == selectedUnitaMisura )
					return it;
			}
		}
		else if( singleUnitaMisura )
		{
			return attr.getUnitaMisuras().get(0);
		}
		
		return null;
	}
	protected CsCfgAttrUnitaMisura getAttrUnitaMisuraSelezionata()
	{
		CsCfgAttr csAttr = attr.getCsCfgAttr();
		CsTbUnitaMisura um = getUnitaMisuraSelezionata();
		
		for (CsCfgAttrUnitaMisura attrUm : listaAttrUnitaMisura) {
			if ( attrUm.getCsCfgAttributo() == csAttr ){
				if( attrUm.getCsTbUnitaMisura() == um )
					return attrUm;
			}
		}
		
		throw new Error("Qui non ci deve arrivare mai");
	}
	public void forceSingolaUnitaMisura(Long selectedUnitaMisura) {
		if( isMultipleUnitaMisura() && selectedUnitaMisura != null ) {
			multipleUnitaMisura = false;
			singleUnitaMisura = true;
			unitaMisura = getUnitaMisuraSelezionata().getValore();
		}
	}

	protected void createTooltip() {
	
		try{
			tooltip = attr.getCsCfgAttr().getTooltip();
			if (isList()) {
				List<String> list = new LinkedList<String>();
				for (CsCfgAttrOption itOption : attr.getCsCfgAttr().getCsCfgAttrOptions()) {
					if (itOption.getTooltip() != null && !"".equals(itOption.getTooltip()))
						list.add("<strong>" + itOption.getValore() + "</strong> " + itOption.getTooltip());
				}
				if (!list.isEmpty())
					tooltip = CommonUtils.Join("<br/>", list.toArray());
		}
		}catch(Exception e){
			e.printStackTrace();
		}

	}

	
	public void reset2default() {
		this.value = attr.getCsCfgAttr().getValoreDefault();
	}

	public String getLabel() {
		return Label;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public TipoAttributo.Enum geteValueType() {
		return eType;
	}

	public List<CsTbUnitaMisura> getCmbxUmeasure() {
		return cmbxUmeasure;
	}

	public boolean getHasDescription() {
		return StringUtils.isNotEmpty(tooltip);
	}

	public String getUnitaMisura() {
		return unitaMisura;
	}

	public String getRequiredMessage() {
		return requiredMessage;
	}

	public List<SelectItem> getCmbxValues() {
		return cmbxValues;
	}

	public String getTooltip() {
		return tooltip;
	}

	public Long getSelectedUnitaMisura() {
		return selectedUnitaMisura;
	}
	public void setSelectedUnitaMisura(Long idUMeasure) {
		this.selectedUnitaMisura = idUMeasure;
	}

	public boolean isMultipleUnitaMisura() {
		return multipleUnitaMisura;
	}

	public boolean isSingleUnitaMisura() {
		return singleUnitaMisura;
	}

	public boolean isBool() {
		return eType == TipoAttributo.Enum.BOOLEAN;
	}

	public boolean isString() {
		return eType == TipoAttributo.Enum.STRING;
	}

	public boolean isInteger() {
		return eType == TipoAttributo.Enum.INTEGER;
	}

	public boolean isDouble() {
		return eType == TipoAttributo.Enum.DOUBLE;
	}

	public boolean isTime() {
		return eType == TipoAttributo.Enum.TIME;
	}

	public boolean isDate() {
		return eType == TipoAttributo.Enum.DATE;
	}

	public boolean isList() {
		return eType == TipoAttributo.Enum.LIST;
	}

	public List<CsCfgAttrUnitaMisura> getListaAttrUnitaMisura() {
		return listaAttrUnitaMisura;
	}

	public IntEsegAttrBean getAttr() {
		return attr;
	}

	public String getStringValue() {
		String res = value.toString();
		
		if ( isBool() ) {
			return Boolean.parseBoolean(value.toString()) ? "SI" : "NO";
		}
		else if ( isDate()) {
			SimpleDateFormat dateformatyyyyMMdd = new SimpleDateFormat("dd/MM/yyyy");
			String stringData = dateformatyyyyMMdd.format((Date) value);
			return stringData;
		}else if(isList()){
			String data = "TODO: Implementare descrittore lista";
			return data;
		}

		if( StringUtils.isEmpty(res))
			res = "-";
		
		return res;
	}

	public String getPrefixClientId() {
		if( isTime() ) return "attrErogTime";
		if( isInteger() ) return "attrErogInteger";
		if( isDouble() ) return "attrErogDouble";
        if( isString() ) return "attrErogString"; 
		
        if( isBool() ) return "attrErogBool"; 
        if( isDate() ) return "attrErogDate"; 
        if( isList() ) return "attrErogList";
        
        throw new Error("Prefix Client Id not supported");
	}
	
	public boolean isRequired() {
		return required;
	}

	public boolean isPersist() {
		return persist;
	}

	public boolean isValid() {
		boolean bValid = true;
		if( isRequired() ) {
			bValid = !( getValue() == null || StringUtils.isEmpty( getValue().toString() ));
		}

		return bValid;
	}

	public void toString(StringBuilder sb) {

		String formatAttribute = "label=" + getLabel() + "\n" + "Value=" + getValue() + "\n" + "tooltip=" + getTooltip() + "\n" + "Type" + "\n" + "\t isBool=" + isBool() + "\n" + "\t isDate=" + isDate() + "\n" + "\t isInteger=" + isInteger() + "\n" + "\t isList=" + isList() + "\n" + "\t isString="
				+ isString() + "\n" + "\t Unita Misura\n" + "\t isuMeasureList=" + this.multipleUnitaMisura + "\n" + "Value: ";
		if (this.multipleUnitaMisura) {
			for (CsTbUnitaMisura um : getCmbxUmeasure()) {
				formatAttribute += "\t" + um.getValore() + " / ";
			}
		}
		else
			formatAttribute += "Selected: " + getUnitaMisura() + "\n";

		sb.append(formatAttribute);
		sb.append("\n********************************\n");
	}
	public boolean isAbilitato() {
		return abilitato;
	}
	public void setAbilitato(boolean abilitato) {
		this.abilitato = abilitato;
	}
	
	
}
