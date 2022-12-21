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
import it.csi.solconfig.configuratoreweb.presentation.model.Data;
import it.csi.solconfig.configuratoreweb.presentation.model.ModificaProfiloModel;
import it.csi.solconfig.configuratoreweb.presentation.model.Nodo;

public interface ModificaProfiliService extends BaseService{
	
	public List<Long> organizzaAlberoFunzionalitaCheckate(TreeFunzionalitaDto treeFunzionalitaDto, List<Long> idTreeFunzionalitaCheckate, Nodo albero) throws Exception;
	
	public List<TreeFunzionalitaDto> findByApplicazioneTree(ApplicazioneDto applicazioneDto) throws Exception;

	public FunzionalitaDto prelevaFunzionalita(FunzionalitaDto funzionalitaDto) throws Exception;

	public TreeFunzionalitaDto prelevaTreeFunzionalita(FunzionalitaDto funzionalitaDto) throws Exception;

	public void modificaProfilo(ModificaProfiloModel modificaProfiloModel, List<Nodo> listaNodiSelezionati, List<Nodo> listaNodiOldFunzionalita,
			TreeFunzionalitaDto treeFunzionalitaProfiloDto, String codiceFiscale, Data data) throws Exception;
		
}
