/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.service;

public interface PutAbilitazioniProfiloPreferenzeService {

	void updateAbilitazioniProfiloPreferenze(String codiceFunzionalita, String codiceApplicazione, String cfOperatore,
			String codiceAzienda);

	void updateFunzionalita(String codiceFunzionalita, String codiceApplicazione, String cfOperatore,
			String codiceAzienda);

}
