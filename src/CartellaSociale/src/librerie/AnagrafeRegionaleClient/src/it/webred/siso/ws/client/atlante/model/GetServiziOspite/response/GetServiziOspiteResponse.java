//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href='http://java.sun.com/xml/jaxb'>http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.09.14 at 03:12:17 PM CEST 
//


package it.webred.siso.ws.client.atlante.model.GetServiziOspite.response;

import java.io.ByteArrayInputStream;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Source;
import javax.xml.transform.sax.SAXSource;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base='{http://www.w3.org/2001/XMLSchema}anyType'>
 *       &lt;choice minOccurs='0'>
 *         &lt;element name='GetServiziOspiteResult' minOccurs='0'>
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base='{http://www.w3.org/2001/XMLSchema}anyType'>
 *                 &lt;sequence>
 *                   &lt;element ref='{http://schemas.datacontract.org/2004/07/AtlanteWebServices.ObjectModel.Messages.GestioneServizi}ServiziOspite' minOccurs='0'/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/choice>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "getServiziOspiteResult"
})
@XmlRootElement(name = "GetServiziOspiteResponse")
public class GetServiziOspiteResponse {

    @XmlElement(name = "GetServiziOspiteResult")
    protected GetServiziOspiteResponse.GetServiziOspiteResult getServiziOspiteResult;

    /**
     * Gets the value of the getServiziOspiteResult property.
     * 
     * @return
     *     possible object is
     *     {@link GetServiziOspiteResponse.GetServiziOspiteResult }
     *     
     */
    public GetServiziOspiteResponse.GetServiziOspiteResult getGetServiziOspiteResult() {
        return getServiziOspiteResult;
    }

    /**
     * Sets the value of the getServiziOspiteResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetServiziOspiteResponse.GetServiziOspiteResult }
     *     
     */
    public void setGetServiziOspiteResult(GetServiziOspiteResponse.GetServiziOspiteResult value) {
        this.getServiziOspiteResult = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base='{http://www.w3.org/2001/XMLSchema}anyType'>
     *       &lt;sequence>
     *         &lt;element ref='{http://schemas.datacontract.org/2004/07/AtlanteWebServices.ObjectModel.Messages.GestioneServizi}ServiziOspite' minOccurs='0'/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "serviziOspite"
    })
    public static class GetServiziOspiteResult {

        @XmlElement(name = "ServiziOspite")
        protected ServiziOspite serviziOspite;

        /**
         * Gets the value of the serviziOspite property.
         * 
         * @return
         *     possible object is
         *     {@link ServiziOspite }
         *     
         */
        public ServiziOspite getServiziOspite() {
            return serviziOspite;
        }

        /**
         * Sets the value of the serviziOspite property.
         * 
         * @param value
         *     allowed object is
         *     {@link ServiziOspite }
         *     
         */
        public void setServiziOspite(ServiziOspite value) {
            this.serviziOspite = value;
        }

    }
    
    
	public static void main(String[] args) {
		String ritorno = " <GetServiziOspiteResponse xmlns='http://sistematlante.it/gestioneServizi'><GetServiziOspiteResult xmlns:i='http://www.w3.org/2001/XMLSchema-instance' xmlns:a='http://schemas.datacontract.org/2004/07/AtlanteWebServices.ObjectModel.Messages.GestioneServizi'><a:ServiziOspite><a:ServizioOspiteResponse><a:DataFine i:nil='true'></a:DataFine><a:DataInizio>2008-10-11T00:00:00</a:DataInizio><a:IGUCausFineServ i:nil='true'></a:IGUCausFineServ><a:IdEntitaDes>PES Alviano</a:IdEntitaDes><a:IdEntitaProp>98</a:IdEntitaProp><a:IdMessaggio>2854_1</a:IdMessaggio></a:ServizioOspiteResponse></a:ServiziOspite></GetServiziOspiteResult></GetServiziOspiteResponse>";
		
		JAXBContext cont = null;
		try {
			cont = JAXBContext.newInstance(GetServiziOspiteResponse.class);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Unmarshaller um=null;
		try {
			um = cont.createUnmarshaller();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		  final SAXParserFactory sax = SAXParserFactory.newInstance();
		  sax.setNamespaceAware(true);
		  XMLReader reader = null;
		try {
			reader = sax.newSAXParser().getXMLReader();
		} catch (SAXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ParserConfigurationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		InputStream stream = new ByteArrayInputStream(ritorno.getBytes(StandardCharsets.UTF_8));
		  final Source er = new SAXSource(reader, new InputSource(stream));
		
		try {
			GetServiziOspiteResponse getServizioOspoteResponse = (GetServiziOspiteResponse) um.unmarshal(er);
			getServizioOspoteResponse.getGetServiziOspiteResult().getServiziOspite();
			
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	
		
	}
    
    
    

}
