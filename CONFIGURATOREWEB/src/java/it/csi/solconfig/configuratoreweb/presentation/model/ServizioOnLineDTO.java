/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.model;

import java.util.List;

public class ServizioOnLineDTO extends BaseDto {

    private String codice;

    private List<Long> collocazioni;

    private List<ProfiloDTO> profili;

    public ServizioOnLineDTO() {
        super();
    }

    public ServizioOnLineDTO(Long id, String descrizione, String codice) {
        super(id, descrizione);
        this.codice = codice;
    }

    public ServizioOnLineDTO(Long id, String descrizione, String codice, List<Long> collocazioni, List<ProfiloDTO> profili) {
        super(id, descrizione);
        this.codice = codice;
        this.collocazioni = collocazioni;
        this.profili = profili;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public List<Long> getCollocazioni() {
        return collocazioni;
    }

    public void setCollocazioni(List<Long> collocazioni) {
        this.collocazioni = collocazioni;
    }

    public List<ProfiloDTO> getProfili() {
        return profili;
    }

    public void setProfili(List<ProfiloDTO> profili) {
        this.profili = profili;
    }
}
