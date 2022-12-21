/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.service;

import it.csi.configuratorews.dto.configuratorews.Applicazione;
import it.csi.configuratorews.dto.configuratorews.Pagination;

public interface GetApplicazioniService {

	public Pagination<Applicazione> getApplicazioni(Integer limit, Integer offset, String codiceAzienda);

}
