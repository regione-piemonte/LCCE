/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.service;

import it.csi.configuratorews.dto.configuratorews.InserimentoProfiloFunzionalitaBody;

public interface PostProfiloFunzionalitaService {

	void insertNuovaFunzionalita(String codiceFunzionalita,
			String descrizioneFunzionalita, String codiceApplicazione, InserimentoProfiloFunzionalitaBody body,
			String codiceAzienda, String codiceFiscale);

	void insertNuovoProfilo(String codiceProfilo, String descrizioneProfilo, String codiceApplicazione,
			String shibIdentitaCodiceFiscale, InserimentoProfiloFunzionalitaBody body);
}
