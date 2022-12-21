/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.service;

import it.csi.configuratorews.dto.configuratorews.ModelUtente;
import it.csi.configuratorews.dto.configuratorews.Pagination;

public interface GetUtentiService {
	Pagination<ModelUtente> getUtentiSol(String applicazione, String azienda,Integer limit,Integer offset);
}
