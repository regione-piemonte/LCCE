/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao;

import java.util.List;

import it.csi.configuratorews.business.dto.ApplicazioneDto;

public interface ApplicazioneLowDao extends CatalogoBaseLowDao<ApplicazioneDto, Long> {

	List<ApplicazioneDto> findApplicazioniAbilitate(String codiceRuolo, String codiceCollocazione, String codiceAzienda,
			String cfUtente);

	List<ApplicazioneDto> findAll(Integer limit, Integer offset, String codiceAzienda);
	
	List<ApplicazioneDto> findByCodiceValidator(ApplicazioneDto applicazionedto);

	Long countApplicazioni();

	Long countApplicazioniByCodAzienda(String codiceAzienda);
	
	List<ApplicazioneDto> findByUtenteAndCollocazioneOrAzienda(Long idUtente, String codiceCollocazione, String codiceAzienda);
	
	List<ApplicazioneDto> findByCodiceAndBloccoModificaS(String applicazione);
	
}
