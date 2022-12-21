/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.model;

public class FunzionalitaDTO extends BaseDto {

    private String codice;

    private FunzionalitaDTO parent;

    public FunzionalitaDTO() {
        super();
    }

    public FunzionalitaDTO(Long id, String descrizione, String codice) {
        super(id, descrizione);
        this.codice = codice;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public FunzionalitaDTO getParent() {
        return parent;
    }

    public void setParent(FunzionalitaDTO parent) {
        this.parent = parent;
    }

}
