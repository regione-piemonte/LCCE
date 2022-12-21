/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import it.csi.solconfig.configuratoreweb.business.dao.dto.ApplicazioneDto;
import it.csi.solconfig.configuratoreweb.presentation.model.ApplicazioneModel;
import it.csi.solconfig.configuratoreweb.presentation.model.PaginaDTO;
import it.csi.solconfig.configuratoreweb.presentation.model.RicercaApplicazioneModel;

public interface ApplicazioniService extends BaseService {

	PaginaDTO<ApplicazioneDto> ricercaApplicazioni(RicercaApplicazioneModel ricercaApplicazioneModel);

	ApplicazioneDto inserisciApplicazione(ApplicazioneModel applicazioneModel, String cfOperatore);

	ApplicazioneDto aggiornaApplicazione(ApplicazioneModel applicazioneModel, String cfOperatore);

	ApplicazioneDto findApplicazioneById(long id);

	Map<String, String> getIcons();

	String getDefaultPath();

	List<String> getCollocazioni(ApplicazioneDto app);

	List<String> getCollocazioniByIdApp(Long id);

	Collection<ApplicazioneDto> findApplicazioneByCodice(String codice);
}
