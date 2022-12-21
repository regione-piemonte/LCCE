/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.dto.configuratorews;

import java.io.Serializable;

public class AutorizzazioneUtente implements Serializable {
	/**
		 * 
		 */
	private static final long serialVersionUID = -2270440358574083187L;
	private Boolean abilitato;

	public Boolean getAbilitato() {
		return abilitato;
	}

	public void setAbilitato(Boolean abilitato) {
		this.abilitato = abilitato;
	}

}
