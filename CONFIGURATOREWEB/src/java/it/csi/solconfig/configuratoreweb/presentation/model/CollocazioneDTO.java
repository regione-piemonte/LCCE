/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.model;

public class CollocazioneDTO extends BaseDto {

    private String codice;

    private String azienda;

    private boolean modificabile;

    private boolean aziendaSanitaria;

    public CollocazioneDTO() {
        super();
    }

    public CollocazioneDTO(Long id, String descrizione, String codice) {
        super(id, descrizione);
        this.codice = codice;
    }
    
     public CollocazioneDTO(Long id, String descrizione, String codice, String azienda) {
        super(id, descrizione);
        this.codice = codice;
        this.azienda = azienda;
    }

    public CollocazioneDTO(Long id, String descrizione, String codice, String azienda, boolean modificabile) {
        super(id, descrizione);
        this.codice = codice;
        this.azienda = azienda;
        this.modificabile = modificabile;
    }

    public CollocazioneDTO(Long id, String descrizione, String codice, boolean aziendaSanitaria) {
        super(id, descrizione);
        this.codice = codice;
        this.aziendaSanitaria = aziendaSanitaria;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getAzienda() {
        return azienda;
    }

    public void setAzienda(String azienda) {
        this.azienda = azienda;
    }

    public void setModificabile(boolean modificabile) {
        this.modificabile = modificabile;
    }

    public boolean isModificabile() {
        return modificabile;
    }

    public boolean getAziendaSanitaria() {
        return aziendaSanitaria;
    }

    public void setAziendaSanitaria(boolean aziendaSanitaria) {
        this.aziendaSanitaria = aziendaSanitaria;
    }
}
