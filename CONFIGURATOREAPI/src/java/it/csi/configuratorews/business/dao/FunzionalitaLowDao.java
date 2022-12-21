/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao;

import it.csi.configuratorews.business.dto.ApplicazioneDto;
import it.csi.configuratorews.business.dto.FunzionalitaDto;

import java.util.List;

public interface FunzionalitaLowDao extends EntityBaseLowDao<FunzionalitaDto, Long> {

	List<FunzionalitaDto> findProfili(FunzionalitaDto funzionalitaDto, Boolean flag);

	List<FunzionalitaDto> findByIdApplicazione(ApplicazioneDto funzionalitaDto);

	List<FunzionalitaDto> findFunzionalitaApplicazione(ApplicazioneDto applicazioneDto, String codiceTipoFunzione);

	List<FunzionalitaDto> findFunzionalitaByCodiceApplicazione(String codiceApplicazione, Integer limit, Integer offset,
			String codiceAzienda);

	Long countFunzionalitaByCodiceApplicazione(String codiceApplicazione, String codiceAzienda);

	FunzionalitaDto findByFunzionalitaApplicazioneAndTipo(String codiceFunzionalita, String codiceApplicazione,
			String tipoFunzionalita, String codiceAzienda);

	FunzionalitaDto findByFunzionalitaApplicazioneAndTipo(String codiceFunzionalita, String codiceApplicazione,
			String tipoFunzionalita);

	List<FunzionalitaDto> findByCodiceFunzioneAndCodiceTipoFunzione(String codiceFunzione, String codiceTipoFunzione);

	List<FunzionalitaDto> findByCodiceAndCodiceTipoAndApplicazioneCodice(String codiceFunzionalita, String codiceTipo,
			String codiceApplicazione);

	Boolean existsProfiloByApplicazione(String codiceProfilo, String codiceApplicazione);

	List<FunzionalitaDto> findFunzionalitaByCodiceApp(String codiceApp, String codiceAzienda, String codiceTipoFunzione,
			Integer limit, Integer offset);

	Long countFunzionalitaByCodiceApp(String codiceApplicazione, String codiceAzienda, String codiceTipoFunzione);

}
