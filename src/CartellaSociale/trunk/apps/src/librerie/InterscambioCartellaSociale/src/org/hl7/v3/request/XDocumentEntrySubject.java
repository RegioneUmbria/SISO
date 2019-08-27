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
 * <p>Java class for x_DocumentEntrySubject.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="x_DocumentEntrySubject">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="PAT"/>
 *     &lt;enumeration value="PRS"/>
 *     &lt;enumeration value="SPEC"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "x_DocumentEntrySubject")
@XmlEnum
public enum XDocumentEntrySubject {

    PAT,
    PRS,
    SPEC;

    public String value() {
        return name();
    }

    public static XDocumentEntrySubject fromValue(String v) {
        return valueOf(v);
    }

}