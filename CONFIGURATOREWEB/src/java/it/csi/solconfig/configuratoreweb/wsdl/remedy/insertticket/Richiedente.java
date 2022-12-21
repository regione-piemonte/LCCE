/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.wsdl.remedy.insertticket;

public class Richiedente {
    private String personId;
    private String nome;
    private String cognome;
    private String email;
    // private String postazione;
    // private String ente;  // company

    public String getPersonId() {
        return personId;
    }

    public void setPersonId(String personId) {
        this.personId = personId;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // public String getPostazione() {
//        return postazione;
//    }
//
//    public void setPostazione(String postazione) {
//        this.postazione = postazione;
//    }
//
//    public String getEnte() {
//        return ente;
//    }

//    public void setEnte(String ente) {
//        this.ente = ente;
//    }
}
