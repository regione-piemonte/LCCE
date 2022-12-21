/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.model;

public class RuoloDTO extends BaseDto {

    private boolean modificabile;

    public RuoloDTO(Long id, String descrizione) {
        super(id, descrizione);
    }

    public RuoloDTO(Long id, String descrizione, boolean modificabile) {
        super(id, descrizione);
        this.modificabile = modificabile;
    }

    public RuoloDTO() {
		// TODO Auto-generated constructor stub
	}

	public boolean isModificabile() {
        return modificabile;
    }

    public void setModificabile(boolean modificabile) {
        this.modificabile = modificabile;
    }
}
