//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.10.24 at 04:01:25 PM CEST 
//


package org.hl7.v3.request;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ProcessingID.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ProcessingID">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="D"/>
 *     &lt;enumeration value="P"/>
 *     &lt;enumeration value="T"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ProcessingID")
@XmlEnum
public enum ProcessingID {

    D,
    P,
    T;

    public String value() {
        return name();
    }

    public static ProcessingID fromValue(String v) {
        return valueOf(v);
    }

}
