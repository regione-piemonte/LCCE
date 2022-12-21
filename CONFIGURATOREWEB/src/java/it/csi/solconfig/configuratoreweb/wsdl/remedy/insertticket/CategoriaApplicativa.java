/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.wsdl.remedy.insertticket;

public class CategoriaApplicativa {
    private String ente;
    private String alo;
    private String sa;
    private String applicativo;
    private String componente;
    private String codiceCfi;

    public String getEnte() {
        return ente;
    }

    public void setEnte(String ente) {
        this.ente = ente;
    }

    public String getAlo() {
        return alo;
    }

    public void setAlo(String alo) {
        this.alo = alo;
    }

    public String getSa() {
        return sa;
    }

    public void setSa(String sa) {
        this.sa = sa;
    }

    public String getApplicativo() {
        return applicativo;
    }

    public void setApplicativo(String applicativo) {
        this.applicativo = applicativo;
    }

    public String getComponente() {
        return componente;
    }

    public void setComponente(String componente) {
        this.componente = componente;
    }

    public String getCodiceCfi() {
        return codiceCfi;
    }

    public void setCodiceCfi(String codiceCfi) {
        this.codiceCfi = codiceCfi;
    }
}
