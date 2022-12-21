/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.service;

import it.csi.configuratorews.dto.configuratorews.Pagination;
import it.csi.configuratorews.interfacews.client.ruoliUtente.Ruolo;

public interface GetRuoliUtenteService {

	public Pagination<Ruolo> getRuoliUtente(String codiceFiscale, Integer offset,Integer limit);
}
