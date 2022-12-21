/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.model;

public class RisultatiRicercaAbilitazioneMassivaDTO {
	
	private String id;

    private String nome;

    private String cognome;

    private String codiceFiscale;

    private String collocazione;
    
    private String ruolo;
    
    private String profilo;

    public RisultatiRicercaAbilitazioneMassivaDTO() {}

    public RisultatiRicercaAbilitazioneMassivaDTO(String id, String nome, String cognome, String codiceFiscale, String collocazione) {
        this.id = id;
    	this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.collocazione = collocazione;
    }
    
    public RisultatiRicercaAbilitazioneMassivaDTO(String id, String nome, String cognome, String codiceFiscale, String collocazione,String ruolo) {
        this.id = id;
    	this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.collocazione = collocazione;
        this.ruolo=ruolo;
    }
    
    
    
    public RisultatiRicercaAbilitazioneMassivaDTO(String id, String nome, String cognome, String codiceFiscale,
			String collocazione, String ruolo, String profilo) {
		
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.codiceFiscale = codiceFiscale;
		this.collocazione = collocazione;
		this.ruolo = ruolo;
		this.profilo = profilo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public void setCodiceFiscale(String codiceFiscale) {
        this.codiceFiscale = codiceFiscale;
    }

    public String getCollocazione() {
        return collocazione;
    }

    public void setCollocazione(String collocazione) {
        this.collocazione = collocazione;
    }

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public String getProfilo() {
		return profilo;
	}

	public void setProfilo(String profilo) {
		this.profilo = profilo;
	}
    
    

}
