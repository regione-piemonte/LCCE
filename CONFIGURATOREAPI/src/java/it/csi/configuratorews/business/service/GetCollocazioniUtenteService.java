/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.service;

import it.csi.configuratorews.dto.configuratorews.CollocazioneUtente;
import it.csi.configuratorews.dto.configuratorews.Pagination;

public interface GetCollocazioniUtenteService {

	public Pagination<CollocazioneUtente> getCollocazioniUtente(String codiceFiscale, String ruoloCodice, Integer offset,Integer limit, String codiceAzienda);
}
