/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.model;

import java.util.List;

public class ProfiloDTO extends BaseDto {

    private String codice;

    private List<FunzionalitaDTO> funzionalita;
    
    private List<RuoloDTO> ruoli;

    public ProfiloDTO() {
        super();
    }

    public ProfiloDTO(Long id, String descrizione, String codice, List<FunzionalitaDTO> funzionalita) {
        super(id, descrizione);
        this.codice = codice;
        this.funzionalita = funzionalita;
    }

    public ProfiloDTO(Long id, String descrizione, String codice) {
        super(id, descrizione);
        this.codice = codice;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public List<FunzionalitaDTO> getFunzionalita() {
        return funzionalita;
    }

    public void setFunzionalita(List<FunzionalitaDTO> funzionalita) {
        this.funzionalita = funzionalita;
    }

	public List<RuoloDTO> getRuoli() {
		return ruoli;
	}

	public void setRuoli(List<RuoloDTO> ruoli) {
		this.ruoli = ruoli;
	}
    
}
