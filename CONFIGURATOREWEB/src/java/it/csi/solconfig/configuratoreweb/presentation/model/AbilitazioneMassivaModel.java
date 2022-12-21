/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.model;

import java.util.List;

public class AbilitazioneMassivaModel extends RicercaModel {
	
	private String azienda;
	private String collocazione;
	private String collocazioneLabel;
	private String ruolo;
	private String sol;
	private List<String> selected;
	private boolean allSelected;
	private String solSelezionabili;
	private String profili;
	private boolean disabilitazione;
	private String dataFineValidita;
	
	
	public String getAzienda() {
		return azienda;
	}
	public void setAzienda(String azienda) {
		this.azienda = azienda;
	}
	public String getCollocazione() {
		return collocazione;
	}
	public void setCollocazione(String collocazione) {
		this.collocazione = collocazione;
	}
	public String getCollocazioneLabel() {
		return collocazioneLabel;
	}
	public void setCollocazioneLabel(String collocazioneLabel) {
		this.collocazioneLabel = collocazioneLabel;
	}
	public String getRuolo() {
		return ruolo;
	}
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	public String getSol() {
		return sol;
	}
	public void setSol(String sol) {
		this.sol = sol;
	}
	
	public List<String> getSelected() {
		return selected;
	}
	public void setSelected(List<String> selected) {
		this.selected = selected;
	}
	public boolean isAllSelected() {
		return allSelected;
	}
	public void setAllSelected(boolean allSelected) {
		this.allSelected = allSelected;
	}
	public String getSolSelezionabili() {
		return solSelezionabili;
	}
	public void setSolSelezionabili(String solSelezionabili) {
		this.solSelezionabili = solSelezionabili;
	}
	public String getProfili() {
		return profili;
	}
	public void setProfili(String profili) {
		this.profili = profili;
	}
	public boolean isDisabilitazione() {
		return disabilitazione;
	}
	public void setDisabilitazione(boolean disabilitazione) {
		this.disabilitazione = disabilitazione;
	}
	public String getDataFineValidita() {
		return dataFineValidita;
	}
	public void setDataFineValidita(String dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}
	
	
	
}
