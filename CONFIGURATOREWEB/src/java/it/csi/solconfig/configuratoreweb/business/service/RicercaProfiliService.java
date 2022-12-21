/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.service;

import java.util.Collection;

import it.csi.solconfig.configuratoreweb.business.dao.dto.ApplicazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.FunzionalitaDto;
import it.csi.solconfig.configuratoreweb.presentation.model.Data;
import it.csi.solconfig.configuratoreweb.presentation.model.PaginaDTO;
import it.csi.solconfig.configuratoreweb.presentation.model.RicercaProfiloModel;

public interface RicercaProfiliService extends BaseService{

	Collection<ApplicazioneDto> ricercaListaApplicazioni(Data data) throws Exception;

	PaginaDTO<FunzionalitaDto> ricercaListaProfili(RicercaProfiloModel ricercaProfiloModel, Boolean flag, ApplicazioneDto applicazioneDto) throws Exception;
	
}
