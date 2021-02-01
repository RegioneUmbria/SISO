//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.10.24 at 04:01:25 PM CEST 
//


package org.hl7.v3.request;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ActStatusHeld.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ActStatusHeld">
 *   &lt;restriction base="{urn:hl7-org:v3}cs">
 *     &lt;enumeration value="held"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ActStatusHeld")
@XmlEnum
public enum ActStatusHeld {

    @XmlEnumValue("held")
    HELD("held");
    private final String value;

    ActStatusHeld(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ActStatusHeld fromValue(String v) {
        for (ActStatusHeld c: ActStatusHeld.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}
