package it.webred.mui.model;
// Generated 15-lug-2008 10.19.11 by Hibernate Tools 3.1.0.beta4

import java.util.HashSet;
import java.util.Set;


/**
 * MiDupSoggettiBase generated by hbm2java
 */

public class MiDupSoggettiBase  implements java.io.Serializable {


    // Fields    

     private long iid;
     private MiDupFornitura miDupFornitura;
     private String idNota;
     private MiDupNotaTras miDupNotaTras;
     private String idSoggettoNota;
     private String idSoggettoCatastale;
     private String tipoSoggetto;
     private String cognome;
     private String nome;
     private String sesso;
     private String dataNascita;
     private String luogoNascita;
     private String codiceFiscale;
     private String denominazione;
     private String sede;
     private String codiceFiscaleG;
     private Set<MiDupTitolarita> miDupTitolaritas = new HashSet<MiDupTitolarita>(0);
     private Set<MiDupIndirizziSog> miDupIndirizziSogs = new HashSet<MiDupIndirizziSog>(0);
     private Set<MiConsComunicazione> miConsComunicaziones = new HashSet<MiConsComunicazione>(0);


    // Constructors

    /** default constructor */
    public MiDupSoggettiBase() {
    }

	/** minimal constructor */
    public MiDupSoggettiBase(MiDupFornitura miDupFornitura) {
        this.miDupFornitura = miDupFornitura;
    }
    
    /** full constructor */
    public MiDupSoggettiBase(MiDupFornitura miDupFornitura, String idNota, MiDupNotaTras miDupNotaTras, String idSoggettoNota, String idSoggettoCatastale, String tipoSoggetto, String cognome, String nome, String sesso, String dataNascita, String luogoNascita, String codiceFiscale, String denominazione, String sede, String codiceFiscaleG, Set<MiDupTitolarita> miDupTitolaritas, Set<MiDupIndirizziSog> miDupIndirizziSogs, Set<MiConsComunicazione> miConsComunicaziones) {
        this.miDupFornitura = miDupFornitura;
        this.idNota = idNota;
        this.miDupNotaTras = miDupNotaTras;
        this.idSoggettoNota = idSoggettoNota;
        this.idSoggettoCatastale = idSoggettoCatastale;
        this.tipoSoggetto = tipoSoggetto;
        this.cognome = cognome;
        this.nome = nome;
        this.sesso = sesso;
        this.dataNascita = dataNascita;
        this.luogoNascita = luogoNascita;
        this.codiceFiscale = codiceFiscale;
        this.denominazione = denominazione;
        this.sede = sede;
        this.codiceFiscaleG = codiceFiscaleG;
        this.miDupTitolaritas = miDupTitolaritas;
        this.miDupIndirizziSogs = miDupIndirizziSogs;
        this.miConsComunicaziones = miConsComunicaziones;
    }
    

   
    // Property accessors

    public long getIid() {
        return this.iid;
    }
    
    public void setIid(long iid) {
        this.iid = iid;
    }

    public MiDupFornitura getMiDupFornitura() {
        return this.miDupFornitura;
    }
    
    public void setMiDupFornitura(MiDupFornitura miDupFornitura) {
        this.miDupFornitura = miDupFornitura;
    }

    public String getIdNota() {
        return this.idNota;
    }
    
    public void setIdNota(String idNota) {
        this.idNota = idNota;
    }

    public MiDupNotaTras getMiDupNotaTras() {
        return this.miDupNotaTras;
    }
    
    public void setMiDupNotaTras(MiDupNotaTras miDupNotaTras) {
        this.miDupNotaTras = miDupNotaTras;
    }

    public String getIdSoggettoNota() {
        return this.idSoggettoNota;
    }
    
    public void setIdSoggettoNota(String idSoggettoNota) {
        this.idSoggettoNota = idSoggettoNota;
    }

    public String getIdSoggettoCatastale() {
        return this.idSoggettoCatastale;
    }
    
    public void setIdSoggettoCatastale(String idSoggettoCatastale) {
        this.idSoggettoCatastale = idSoggettoCatastale;
    }

    public String getTipoSoggetto() {
        return this.tipoSoggetto;
    }
    
    public void setTipoSoggetto(String tipoSoggetto) {
        this.tipoSoggetto = tipoSoggetto;
    }

    public String getCognome() {
        return this.cognome;
    }
    
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return this.nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSesso() {
        return this.sesso;
    }
    
    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public String getDataNascita() {
        return this.dataNascita;
    }
    
    public void setDataNascita(String dataNascita) {
        this.dataNascita = dataNascita;
    }

    public String getLuogoNascita() {
        return this.luogoNascita;
    }
    
    public void setLuogoNascita(String luogoNascita) {
        this.luogoNascita = luogoNascita;
    }

    public String getCodiceFiscale() {
        return this.codiceFiscale;
    }
    
    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getDenominazione() {
        return this.denominazione;
    }
    
    public void setDenominazione(String denominazione) {
        this.denominazione = denominazione;
    }

    public String getSede() {
        return this.sede;
    }
    
    public void setSede(String sede) {
        this.sede = sede;
    }

    public String getCodiceFiscaleG() {
        return this.codiceFiscaleG;
    }
    
    public void setCodiceFiscaleG(String codiceFiscaleG) {
        this.codiceFiscaleG = codiceFiscaleG;
    }

    public Set<MiDupTitolarita> getMiDupTitolaritas() {
        return this.miDupTitolaritas;
    }
    
    public void setMiDupTitolaritas(Set<MiDupTitolarita> miDupTitolaritas) {
        this.miDupTitolaritas = miDupTitolaritas;
    }

    public Set<MiDupIndirizziSog> getMiDupIndirizziSogs() {
        return this.miDupIndirizziSogs;
    }
    
    public void setMiDupIndirizziSogs(Set<MiDupIndirizziSog> miDupIndirizziSogs) {
        this.miDupIndirizziSogs = miDupIndirizziSogs;
    }

    public Set<MiConsComunicazione> getMiConsComunicaziones() {
        return this.miConsComunicaziones;
    }
    
    public void setMiConsComunicaziones(Set<MiConsComunicazione> miConsComunicaziones) {
        this.miConsComunicaziones = miConsComunicaziones;
    }
   








}