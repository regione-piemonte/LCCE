/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.dto.configuratorews;

import java.io.Serializable;

public class ProfiloCollocazione implements Serializable{

	private CollocazioneUtente collocazione;
	private ProfiloFunzionalita profiloFunzionalita;
	public CollocazioneUtente getCollocazione() {
		return collocazione;
	}
	public void setCollocazione(CollocazioneUtente collocazione) {
		this.collocazione = collocazione;
	}
	public ProfiloFunzionalita getProfiloFunzionalita() {
		return profiloFunzionalita;
	}
	public void setProfiloFunzionalita(ProfiloFunzionalita profiloFunzionalita) {
		this.profiloFunzionalita = profiloFunzionalita;
	}
}
