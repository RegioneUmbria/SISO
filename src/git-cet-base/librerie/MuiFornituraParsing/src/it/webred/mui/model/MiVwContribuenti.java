package it.webred.mui.model;
// Generated 15-lug-2008 10.19.07 by Hibernate Tools 3.1.0.beta4



/**
 * MiVwContribuenti generated by hbm2java
 */

public class MiVwContribuenti  implements java.io.Serializable {


    // Fields    

     private long iid;
     private String codiceFiscale;
     private String nome;
     private String cognome;
     private String sesso;
     private String dataNascita;
     private String comuneNascita;
     private String indirizzo;


    // Constructors

    /** default constructor */
    public MiVwContribuenti() {
    }

	/** minimal constructor */
    public MiVwContribuenti(long iid) {
        this.iid = iid;
    }
    
    /** full constructor */
    public MiVwContribuenti(long iid, String codiceFiscale, String nome, String cognome, String sesso, String dataNascita, String comuneNascita, String indirizzo) {
        this.iid = iid;
        this.codiceFiscale = codiceFiscale;
        this.nome = nome;
        this.cognome = cognome;
        this.sesso = sesso;
        this.dataNascita = dataNascita;
        this.comuneNascita = comuneNascita;
        this.indirizzo = indirizzo;
    }
    

   
    // Property accessors

    public long getIid() {
        return this.iid;
    }
    
    public void setIid(long iid) {
        this.iid = iid;
    }

    public String getCodiceFiscale() {
        return this.codiceFiscale;
    }
    
    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getNome() {
        return this.nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return this.cognome;
    }
    
    public void setCognome(String cognome) {
        this.cognome = cognome;
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

    public String getComuneNascita() {
        return this.comuneNascita;
    }
    
    public void setComuneNascita(String comuneNascita) {
        this.comuneNascita = comuneNascita;
    }

    public String getIndirizzo() {
        return this.indirizzo;
    }
    
    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }
   








}