/**
 * ClientAnagrafeRomaPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package it.roma.comune.servizi.test;

public interface ClientAnagrafeRomaPortType extends java.rmi.Remote {
    public it.roma.comune.servizi.dto.xsd.RicercaResult eseguiRicercaAnagraficaEstesa(java.lang.String wsURL, java.lang.String cognome, java.lang.String nome, java.lang.String sesso, java.lang.String annoIniziale, java.lang.String annoFinale) throws java.rmi.RemoteException;
    public it.roma.comune.servizi.dto.xsd.RicercaResult verificaDatiAnagrafici(java.lang.String URL, java.lang.String cognome, java.lang.String nome, java.lang.String annoNascita, java.lang.String meseNascita, java.lang.String giornoNascita, java.lang.String codiceFiscale) throws java.rmi.RemoteException;
    public it.roma.comune.servizi.dto.xsd.RicercaResult ricercaPerCodiceIndividuale(java.lang.String wsURL, java.lang.String codiceIndividuale) throws java.rmi.RemoteException;
}
