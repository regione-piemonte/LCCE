/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.service;

import it.csi.configuratorews.dto.configuratorews.CollocazioneUtente;
import it.csi.configuratorews.dto.configuratorews.Pagination;

public interface GetCollocazioniAziendaService {

	public Pagination<CollocazioneUtente> getCollocazioniAzienda(String codiceAzienda,String codiceStruttura, Integer limit,Integer offset);
}
