/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao;

import it.csi.solconfig.configuratoreweb.business.dao.dto.ApplicazioneCollocazioneDto;

import java.util.List;

public interface ApplicazioneCollocazioneLowDao extends EntityBaseLowDao<ApplicazioneCollocazioneDto, Long> {

    List<Long> findIdCollocazioniByIdApplicazione(Long idApplicazione);

    List<ApplicazioneCollocazioneDto> findByIdCollocazioneAndDateValidita(Long colId);

	void insert(Long idApplicazione, long idCollocazione);

	void deleteById(long idapp, List<String> collocazioniToDel);
}
