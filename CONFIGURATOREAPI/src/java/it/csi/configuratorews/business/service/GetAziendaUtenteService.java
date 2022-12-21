/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.service;

public interface GetAziendaUtenteService {

	public String getAzienda(String codiceFiscale, String codiceCollocazione, String codiceRuolo,
			String codiceApplicazione);

}
