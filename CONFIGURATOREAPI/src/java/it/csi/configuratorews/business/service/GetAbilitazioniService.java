/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.service;

import it.csi.configuratorews.dto.configuratorews.AutorizzazioneUtente;

public interface GetAbilitazioniService {

	public AutorizzazioneUtente getAbilitazioneUtente(String codiceFiscale, 
	 String ruoloCodice, String applicazioneCodice, String collocazioneCodice,
	String profiloCodice, String funzionalitaCodice, String codiceAzienda);
}
