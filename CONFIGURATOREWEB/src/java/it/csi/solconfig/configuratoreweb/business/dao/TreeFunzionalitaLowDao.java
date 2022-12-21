/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao;

import java.util.List;

import it.csi.solconfig.configuratoreweb.business.dao.dto.TreeFunzionalitaDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.FunzionalitaDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.ApplicazioneDto;
import it.csi.solconfig.configuratoreweb.presentation.model.Nodo;

import java.util.Collection;

public interface TreeFunzionalitaLowDao extends EntityBaseLowDao<TreeFunzionalitaDto, Long> {
    Collection<TreeFunzionalitaDto> findByFunzionalitaDto(FunzionalitaDto funzionalitaDto);
    
    public List<TreeFunzionalitaDto> findByIdApplicazione(ApplicazioneDto applicazioneDto);

	public List<TreeFunzionalitaDto> findByIdPadre(Nodo albero);
	
	public List<TreeFunzionalitaDto> findByIdPadreFunzionalita(TreeFunzionalitaDto dto);

	List<TreeFunzionalitaDto> findByCodiceApplicazioneAndFunzionalitaTipo(String codiceTipoFunzione,
			String codiceApplicazione);

	List<TreeFunzionalitaDto> findByCodiceFunzioneAndApplicazione(String codiceFunzione, String codiceTipoFunzione,
			String codiceApplicazione);
	
	public List<TreeFunzionalitaDto> findByIdFunzione(Long idFunzione);

	List<TreeFunzionalitaDto> findFunzByIdApplicazione(ApplicazioneDto applicazioneDto);
}
