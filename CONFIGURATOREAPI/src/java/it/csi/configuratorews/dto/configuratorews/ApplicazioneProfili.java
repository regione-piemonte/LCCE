/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.dto.configuratorews;

import java.io.Serializable;
import java.util.Set;

public class ApplicazioneProfili extends Applicazione implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5893830529237821030L;
	private Set<ProfiloCollocazione> profiloCollocazione;
	public Set<ProfiloCollocazione> getProfiloCollocazione() {
		return profiloCollocazione;
	}
	public void setProfiloCollocazione(Set<ProfiloCollocazione> profiloCollocazione) {
		this.profiloCollocazione = profiloCollocazione;
	}
}
