/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.service;

import it.csi.configuratorews.dto.configuratorews.ApplicazioneProfili;
import it.csi.configuratorews.dto.configuratorews.Pagination;
import it.csi.configuratorews.dto.configuratorews.UtenteApplicazioni;
import it.csi.configuratorews.dto.configuratorews.UtenteProfiloFunzionalita;

public interface GetUtentiProfiliFunzionalitaService {

	Pagination<UtenteProfiloFunzionalita> getUtentiProfiliFunzionalita(String ruolo, String collocazione,
			String codiceAzienda, Integer limit, Integer offset);

	Pagination<UtenteApplicazioni> getUtentiApplicazione(String ruolo, String collocazione, String codiceAzienda,
			Integer limit, Integer offset);

	Pagination<ApplicazioneProfili> getUtentiAbilitazioni(String utente, String ruolo, String collocazione,
			String codiceAzienda, Integer limit, Integer offset);

}
