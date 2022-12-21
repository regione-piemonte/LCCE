/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao;

import it.csi.solconfig.configuratoreweb.business.dao.dto.ApplicazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.FunzionalitaDto;
import it.csi.solconfig.configuratoreweb.presentation.model.FunzionalitaDTO;
import it.csi.solconfig.configuratoreweb.presentation.model.FunzionalitaTreeDTO;
import it.csi.solconfig.configuratoreweb.presentation.model.ProfiloDTO;

import java.util.List;

public interface FunzionalitaLowDao extends EntityBaseLowDao<FunzionalitaDto, Long> {

    List<ProfiloDTO> findProfiliByIdApplicazione(Long idApplicazione, boolean isOPListaProfiliCompleta, boolean superUser);

    List<FunzionalitaTreeDTO> findAllFunzionalitaByIdProfilo(Long idProfilo);

    FunzionalitaDTO findFunzionalitaById(Long idFunzionalita);

    List<FunzionalitaDto> findProfili(FunzionalitaDto funzionalitaDto, Boolean flag);

	List<FunzionalitaDto> findByIdApplicazione(ApplicazioneDto applicazioneDto);

	List<FunzionalitaDto> findFunzionalita(FunzionalitaDto funzionalitaDto);
	
	List<FunzionalitaDto> findFunzionalitaApplicazione(ApplicazioneDto applicazioneDto);
	
	List<ProfiloDTO> findProfiliByIdApplicazioneAndRuolo(Long idApplicazione, boolean isOPListaProfiliCompleta,Long idRuolo, boolean superUser);
	
	List<ProfiloDTO> findProfiliByIdApplicazioneAndCollocazione(Long idApplicazione, boolean isOPListaProfiliCompleta,Long idCollocazione, boolean superUser);

	FunzionalitaDTO findFunzionalitaByCodice(String codice);
	
}
