/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao;

import it.csi.solconfig.configuratoreweb.business.dao.dto.AbilitazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.UtenteCollocazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.UtenteDto;

import java.util.List;

public interface UtenteCollocazioneLowDao extends EntityBaseLowDao<UtenteCollocazioneDto, Long>{

    List<UtenteCollocazioneDto> findByUtenteDto(UtenteDto utenteDto);

	List<UtenteCollocazioneDto> findByIdRuoloAndIdAzienda(Long idRuolo, Long idAzienda);

	List<UtenteCollocazioneDto> findByIdRuoloAndIdCollocazione(Long idRuolo, Long idCollocazione);

	List<UtenteCollocazioneDto> ricercaUtenteCollocazioneAbilitazioneMassiva(Long idRuolo, Long idAzienda,
			Long idCollocazione, Long idSol, Integer numeroPagina, Integer numeroElementi);

	Long countRicercaUtenteCollocazioneAbilitazioneMassiva(Long idRuolo, Long idAzienda, Long idCollocazione, Long idSol);
	
	List<AbilitazioneDto> ricercaUtenteCollocazioneDisabilitazioneMassiva(Long idRuolo, Long idAzienda,
			Long idCollocazione, Long idSol, Integer numeroPagina, Integer numeroElementi);
	
	Long countRicercaUtenteCollocazioneDisabilitazioneMassiva(Long idRuolo, Long idAzienda, Long idCollocazione, Long idSol);
	
	List<Long> ricercaIDUtenteCollocazioneAbilitazioneMassiva(Long idRuolo, Long idAzienda,
			Long idCollocazione, Long idSol);
	
	List<Long> ricercaIDUtenteCollocazioneDisabilitazioneMassiva(Long idRuolo, Long idAzienda,
			Long idCollocazione, Long idSol);
	
	
}