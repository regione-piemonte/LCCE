/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao;

import it.csi.configuratorews.business.dto.ApplicazioneCollocazioneDto;

import java.util.List;

public interface ApplicazioneCollocazioneLowDao extends EntityBaseLowDao<ApplicazioneCollocazioneDto, Long> {

	List<Long> findIdCollocazioniByIdApplicazione(Long idApplicazione);

	List<ApplicazioneCollocazioneDto> findByCodiceApplicazione(String codiceApplicazione, Integer limit, Integer offset,
			String codiceAzienda);

	public Long countByCodiceApplicazione(String codiceApplicazione);

	List<ApplicazioneCollocazioneDto> findByApplicazioneAndAzienda(String codiceApplicazione, String codiceAzienda);

}
