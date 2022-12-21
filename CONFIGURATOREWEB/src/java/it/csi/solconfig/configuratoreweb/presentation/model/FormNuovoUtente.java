/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.model;

import java.util.List;

public class FormNuovoUtente extends FormDatiUtente {

    private List<String> ruoli;

    private List<String> collocazioni;

    private List<String> profiliSol;
    
    private List<String> ruoliFromOpessan;
    
    private List<String> collocazioniFromOpessan;

    private Long idAura;
    
    private boolean isMaildaInviare= false ;
    
    
   

    public List<String> getRuoli() {
        return ruoli;
    }

    public void setRuoli(List<String> ruoli) {
        this.ruoli = ruoli;
    }

    public List<String> getCollocazioni() {
        return collocazioni;
    }

    public void setCollocazioni(List<String> collocazioni) {
        this.collocazioni = collocazioni;
    }

    public List<String> getProfiliSol() {
        return profiliSol;
    }

    public void setProfiliSol(List<String> profiliSol) {
        this.profiliSol = profiliSol;
    }

    public void setIdAura(Long idAura) {
        this.idAura = idAura;
    }

    public Long getIdAura() {
        return idAura;
    }

	public List<String> getCollocazioniFromOpessan() {
		return collocazioniFromOpessan;
	}

	public void setCollocazioniFromOpessan(List<String> collocazioniFromOpessan) {
		this.collocazioniFromOpessan = collocazioniFromOpessan;
	}

	public List<String> getRuoliFromOpessan() {
		return ruoliFromOpessan;
	}

	public void setRuoliFromOpessan(List<String> ruoliFromOpessan) {
		this.ruoliFromOpessan = ruoliFromOpessan;
	}

	public boolean isMaildaInviare() {
		return isMaildaInviare;
	}

	public void setMaildaInviare(boolean isMaildaInviare) {
		this.isMaildaInviare = isMaildaInviare;
	}

	
	
}
