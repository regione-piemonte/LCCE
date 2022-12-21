/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.dto.configuratorews;

import java.io.Serializable;
import java.util.List;

public class ItemPreference implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3025667722286199100L;
	
	public static final String APPLICAZIONE="APPLICAZIONE";
	public static final String RUOLO="RUOLO";
	public static final String RUOLO_COLLOCAZIONE="RUOLO_COLLOCAZIONE";
	
	private Fruitore fruitore;
	private String collocazione;
	private String descrizioneCollocazione;
	private Applicazione applicazione;
	private String ruolo;
	private String descrizioneRuolo;
	private String tipoPreferenza;
	private Preference preferenza;

	public Applicazione getApplicazione() {
		return applicazione;
	}

	public void setApplicazione(Applicazione applicazione) {
		this.applicazione = applicazione;
	}
	
	public Preference getPreferenza() {
		return preferenza;
	}

	public void setPreferenza(Preference preferenza) {
		this.preferenza = preferenza;
	}


	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public String getTipoPreferenza() {
		return tipoPreferenza;
	}

	public void setTipoPreferenza(String tipoPreferenza) {
		this.tipoPreferenza = tipoPreferenza;
	}

	public String getCollocazione() {
		return collocazione;
	}

	public void setCollocazione(String collocazione) {
		this.collocazione = collocazione;
	}

	public Fruitore getFruitore() {
		return fruitore;
	}

	public void setFruitore(Fruitore fruitore) {
		this.fruitore = fruitore;
	}



	@Override
	public String toString() {
		return "ItemPreference [fruitore=" + fruitore + ", collocazione=" + collocazione + ", descrizioneCollocazione=" + descrizioneCollocazione
				+ ", applicazione=" + applicazione + ", ruolo=" + ruolo + ", descrizioneRuolo=" + descrizioneRuolo + ", tipoPreferenza=" + tipoPreferenza
				+ ", preferenza=" + preferenza + "]";
	}

	public String getDescrizioneCollocazione() {
		return descrizioneCollocazione;
	}

	public void setDescrizioneCollocazione(String descrizioneCollocazione) {
		this.descrizioneCollocazione = descrizioneCollocazione;
	}

	public String getDescrizioneRuolo() {
		return descrizioneRuolo;
	}

	public void setDescrizioneRuolo(String descrizioneRuolo) {
		this.descrizioneRuolo = descrizioneRuolo;
	}

}
