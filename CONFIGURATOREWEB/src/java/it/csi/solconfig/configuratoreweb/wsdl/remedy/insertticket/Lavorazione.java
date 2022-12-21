/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.wsdl.remedy.insertticket;

import it.csi.solconfig.configuratoreweb.wsdl.remedy.InfoNota;

import java.util.Date;
import java.util.List;

public class Lavorazione {
    private String operatorId;
    // private String nominativo;
    private String email;
    private String stato;
    private String statoEsteso;
    private Date updateDate;
    private String risoluzione;
    private Date closeDate;
    private String commento;
    private List<InfoNota> noteUtente;

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

//    public String getNominativo() {
//        return nominativo;
//    }
//
//    public void setNominativo(String nominativo) {
//        this.nominativo = nominativo;
//    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public String getStatoEsteso() {
        return statoEsteso;
    }

    public void setStatoEsteso(String statoEsteso) {
        this.statoEsteso = statoEsteso;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getRisoluzione() {
        return risoluzione;
    }

    public void setRisoluzione(String risoluzione) {
        this.risoluzione = risoluzione;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public String getCommento() {
        return commento;
    }

    public void setCommento(String commento) {
        this.commento = commento;
    }

    public List<InfoNota> getNoteUtente() {
        return noteUtente;
    }

    public void setNoteUtente(List<InfoNota> noteUtente) {
        this.noteUtente = noteUtente;
    }
}
