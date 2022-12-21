/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.model;

public class RisultatiRicercaUtenteDTO {

    private String nome;

    private String cognome;

    private String codiceFiscale;

    private String stato;

    private String ruolo;

    private String servizioOnLine;

    private String collocazione;

    private String inseritoDalConfiguratore;

    private String profili;

    private String solFlagConfiguratore;

    private String codiceRuolo;

    public RisultatiRicercaUtenteDTO(String nome, String cognome, String codiceFiscale,
                                     String ruolo, String collocazione, String inseritoDalConfiguratore,
                                     String servizioOnLine, String stato, String profili, String solFlagConfiguratore,
                                     String codiceRuolo) {
        this.nome = nome;
        this.cognome = cognome;
        this.codiceFiscale = codiceFiscale;
        this.stato = stato;
        this.ruolo = ruolo;
        this.servizioOnLine = servizioOnLine;
        this.collocazione = collocazione;
        this.inseritoDalConfiguratore = inseritoDalConfiguratore;
        this.profili = profili;
        this.solFlagConfiguratore = solFlagConfiguratore;
        this.codiceRuolo = codiceRuolo;
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

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    public String getServizioOnLine() {
        return servizioOnLine;
    }

    public void setServizioOnLine(String servizioOnLine) {
        this.servizioOnLine = servizioOnLine;
    }

    public String getCollocazione() {
        return collocazione;
    }

    public void setCollocazione(String collocazione) {
        this.collocazione = collocazione;
    }

    public String getInseritoDalConfiguratore() {
        return inseritoDalConfiguratore;
    }

    public void setInseritoDalConfiguratore(String inseritoDalConfiguratore) {
        this.inseritoDalConfiguratore = inseritoDalConfiguratore;
    }

    public String getProfili() {
        return profili;
    }

    public void setProfili(String profili) {
        this.profili = profili;
    }

    public String getSolFlagConfiguratore() {
        return solFlagConfiguratore;
    }

    public void setSolFlagConfiguratore(String solFlagConfiguratore) {
        this.solFlagConfiguratore = solFlagConfiguratore;
    }

    public String getCodiceRuolo() {
        return codiceRuolo;
    }

    public void setCodiceRuolo(String codiceRuolo) {
        this.codiceRuolo = codiceRuolo;
    }
}
