/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.service;

import java.util.List;

import it.csi.solconfig.configuratoreweb.business.dao.dto.ApplicazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.FunzionalitaDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.TreeFunzionalitaDto;
import it.csi.solconfig.configuratoreweb.presentation.model.InserisciProfiloModel;
import it.csi.solconfig.configuratoreweb.presentation.model.Nodo;

public interface InserisciProfiliService extends BaseService{
	
	public List<TreeFunzionalitaDto> findByApplicazioneTree(ApplicazioneDto applicazioneDtoList) throws Exception;
	
	public List<FunzionalitaDto> prelevaFunzionalitaApplicazione(List<ApplicazioneDto> applicazioneDtoList) throws Exception;
	
	public List<TreeFunzionalitaDto> prelevaFunzionalitaTreePadre(List<FunzionalitaDto> funzionalitaDtoList) throws Exception; 
	
	public boolean checkCodicePresente(FunzionalitaDto funzionalitaDto) throws Exception;
	
	public String inserimentoProfilo(InserisciProfiloModel inserisciProfiloModel, List<Nodo> listaNodiSelezionati, String codiceFiscaleUtente) throws Exception;

	Long getIdRadiceProfilo();
	
}
