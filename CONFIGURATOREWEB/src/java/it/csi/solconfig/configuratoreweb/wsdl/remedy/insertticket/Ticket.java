/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.wsdl.remedy.insertticket;

import java.util.Date;

public class Ticket {
    private String ticketId;
    private Date insertDate;
    private String riepilogo;
    private String dettaglio;
    private String impatto;
    private String urgenza;
    private String tipologia;
    private Richiedente richiedente;
    private Categorizzazione categorizzazione;
    private Lavorazione lavorazione;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public Date getInsertDate() {
        return insertDate;
    }

    public void setInsertDate(Date insertDate) {
        this.insertDate = insertDate;
    }

    public String getRiepilogo() {
        return riepilogo;
    }

    public void setRiepilogo(String riepilogo) {
        this.riepilogo = riepilogo;
    }

    public String getDettaglio() {
        return dettaglio;
    }

    public void setDettaglio(String dettaglio) {
        this.dettaglio = dettaglio;
    }

    public String getImpatto() {
        return impatto;
    }

    public void setImpatto(String impatto) {
        this.impatto = impatto;
    }

    public String getUrgenza() {
        return urgenza;
    }

    public void setUrgenza(String urgenza) {
        this.urgenza = urgenza;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public Richiedente getRichiedente() {
        return richiedente;
    }

    public void setRichiedente(Richiedente richiedente) {
        this.richiedente = richiedente;
    }

    public Categorizzazione getCategorizzazione() {
        return categorizzazione;
    }

    public void setCategorizzazione(Categorizzazione categorizzazione) {
        this.categorizzazione = categorizzazione;
    }

    public Lavorazione getLavorazione() {
        return lavorazione;
    }

    public void setLavorazione(Lavorazione lavorazione) {
        this.lavorazione = lavorazione;
    }
}
