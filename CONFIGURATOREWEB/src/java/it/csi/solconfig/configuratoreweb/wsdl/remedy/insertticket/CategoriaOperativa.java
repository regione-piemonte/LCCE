/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.wsdl.remedy.insertticket;

public class CategoriaOperativa {
    private String ente;
    private String livello1;
    private String livello2;
    private String livello3;
    private String tipologia;

    public String getEnte() {
        return ente;
    }

    public void setEnte(String ente) {
        this.ente = ente;
    }

    public String getLivello1() {
        return livello1;
    }

    public void setLivello1(String livello1) {
        this.livello1 = livello1;
    }

    public String getLivello2() {
        return livello2;
    }

    public void setLivello2(String livello2) {
        this.livello2 = livello2;
    }

    public String getLivello3() {
        return livello3;
    }

    public void setLivello3(String livello3) {
        this.livello3 = livello3;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }
}
