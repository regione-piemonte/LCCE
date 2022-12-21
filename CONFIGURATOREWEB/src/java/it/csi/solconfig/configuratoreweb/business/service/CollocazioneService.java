/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.service;

import it.csi.solconfig.configuratoreweb.business.dao.dto.CollocazioneTipoDto;
import it.csi.solconfig.configuratoreweb.presentation.model.CollocazioneDTO;
import it.csi.solconfig.configuratoreweb.presentation.model.Data;

import java.util.Collection;
import java.util.List;

public interface CollocazioneService extends BaseService {

    List<CollocazioneDTO> getAllAziende(Data data);

    List<CollocazioneDTO> getCollocazioneByCodiceOrDescrizione(String azienda, String codice, String descrizione, Data data);

    List<CollocazioneDTO> getCollocazioneById(List<Long> ids, Data data);

    List<CollocazioneDTO> ricercaCollocazioniByIdUtente(Long idUtente, Data data);

    List<CollocazioneDTO> getCollocazioniSolByCodiceOrDescrizione(List<Long> collocazioni,Data data);

	Collection<CollocazioneTipoDto> getCollocazioniTipo();
}
