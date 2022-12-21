/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.service;

import it.csi.configuratorews.dto.configuratorews.ModelCollocazione;
import it.csi.configuratorews.dto.configuratorews.Pagination;

public interface GetCollocazioniSOLService {


	Pagination<ModelCollocazione> getCollocazioniSol(String applicazione,Integer limit, Integer offset, String codiceAzienda);
}
