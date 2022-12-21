/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.model;

import java.util.ArrayList;
import java.util.List;

public class ApplicazioneModel {
	private long id;
	private String codice;
	private String descrizione;
	private String pinRichiesto;
	private String urlverifyloginconditions;
	private String descrizioneWebapp;
	private String pathImmagine;
	private String redirectUrl;
	private String bottone;

	private String oscurato;

	private List<String> collocazioni;

	public List<String> getCollocazioni() {
		if (collocazioni == null)
			collocazioni = new ArrayList<>();
		collocazioni.removeIf(String::isEmpty);
		return collocazioni;
	}

	public void setCollocazioni(List<String> collocazioni) {
		this.collocazioni = collocazioni;
	}

	@Override
	public String toString() {
		return "ApplicazioneModel [codice=" + codice + ", descrizione=" + descrizione + ", pinRichiesto=" + pinRichiesto
				+ ", urlverifyloginconditions=" + urlverifyloginconditions + ", descrizioneWebapp=" + descrizioneWebapp
				+ ", pathImmagine=" + pathImmagine + ", redirectUrl=" + redirectUrl + ", bottone=" + bottone + "]";
	}

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getPinRichiesto() {
		return pinRichiesto;
	}

	public void setPinRichiesto(String pinRichiesto) {
		this.pinRichiesto = pinRichiesto;
	}

	public String getUrlverifyloginconditions() {
		return urlverifyloginconditions;
	}

	public void setUrlverifyloginconditions(String urlverifyloginconditions) {
		this.urlverifyloginconditions = urlverifyloginconditions;
	}

	public String getDescrizioneWebapp() {
		return descrizioneWebapp;
	}

	public void setDescrizioneWebapp(String descrizioneWebapp) {
		this.descrizioneWebapp = descrizioneWebapp;
	}

	public String getPathImmagine() {
		return pathImmagine;
	}

	public void setPathImmagine(String pathImmagine) {
		this.pathImmagine = pathImmagine;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getBottone() {
		return bottone;
	}

	public void setBottone(String bottone) {
		this.bottone = bottone;
	}

	public String getOscurato() {
		return oscurato;
	}

	public void setOscurato(String oscurato) {
		this.oscurato = oscurato;
	}

	public boolean isFlagPIN() {
		return "S".equalsIgnoreCase(pinRichiesto);
	}

	public void setFlagPIN(boolean flagPIN) {
		this.pinRichiesto = (flagPIN ? "S" : "N");
	}

	public boolean isFlagBottone() {
		return "S".equalsIgnoreCase(bottone);
	}

	public void setFlagBottone(boolean flagBottone) {
		this.bottone = (flagBottone ? "S" : "N");
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isFlagOscurato() {
		return "S".equalsIgnoreCase(oscurato);

	}

	public void setFlagOscurato(boolean flagOscurato) {
		this.oscurato = (flagOscurato ? "S" : "N");
	}

}
