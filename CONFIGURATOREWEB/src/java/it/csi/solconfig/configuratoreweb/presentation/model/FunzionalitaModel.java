/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.model;

public class FunzionalitaModel {
	private long id;
	private String codice;
	private String descrizione;
	private String applicazione;
	private String idApplicazione;
	private String datiAnagrafici;
	private String datiPrescrittivi;
	private String datiClinici;
	private String datiConsenso;
	private String datiAmministrativi;
	private boolean funzionalitaAttivo;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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

	public String getDatiAnagrafici() {
		return datiAnagrafici;
	}

	public void setDatiAnagrafici(String datiAnagrafici) {
		this.datiAnagrafici = datiAnagrafici;
	}

	public String getDatiPrescrittivi() {
		return datiPrescrittivi;
	}

	public void setDatiPrescrittivi(String datiPrescrittivi) {
		this.datiPrescrittivi = datiPrescrittivi;
	}

	public String getDatiClinici() {
		return datiClinici;
	}

	public void setDatiClinici(String datiClinici) {
		this.datiClinici = datiClinici;
	}

	public String getDatiConsenso() {
		return datiConsenso;
	}

	public void setDatiConsenso(String datiConsenso) {
		this.datiConsenso = datiConsenso;
	}

	public String getDatiAmministrativi() {
		return datiAmministrativi;
	}

	public void setDatiAmministrativi(String datiAmministrativi) {
		this.datiAmministrativi = datiAmministrativi;
	}

	public String getApplicazione() {
		return applicazione;
	}

	public void setApplicazione(String applicazione) {
		this.applicazione = applicazione;
	}

	public boolean isFunzionalitaAttivo() {
		return funzionalitaAttivo;
	}

	public void setFunzionalitaAttivo(boolean funzionalitaAttivo) {
		this.funzionalitaAttivo = funzionalitaAttivo;
	}

	public String getIdApplicazione() {
		return idApplicazione;
	}

	public void setIdApplicazione(String idApplicazione) {
		this.idApplicazione = idApplicazione;
	}

}
