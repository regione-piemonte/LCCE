/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao;


import it.csi.configuratorews.business.dto.ApplicazioneDto;
import it.csi.configuratorews.business.dto.FunzionalitaDto;
import it.csi.configuratorews.business.dto.TreeFunzionalitaDto;

import java.util.Collection;
import java.util.List;

public interface TreeFunzionalitaLowDao extends EntityBaseLowDao<TreeFunzionalitaDto, Long> {
    Collection<TreeFunzionalitaDto> findByFunzionalitaDto(FunzionalitaDto funzionalitaDto);
    
    public List<TreeFunzionalitaDto> findByIdApplicazione(ApplicazioneDto applicazioneDto, String codiceTipoFunzione);
    public List<TreeFunzionalitaDto> findByCodiceFunzioneAndApplicazione(String codiceFunzione, String codiceTipoFunzione, String codiceApplicazione); 

	
	public List<TreeFunzionalitaDto> findByIdPadreFunzionalita(TreeFunzionalitaDto dto);
	
	public List<TreeFunzionalitaDto> findFunzionalitaByIdPadreProfilo(Long idPadreProfilo);
	public List<TreeFunzionalitaDto> findFunzionalitaById(Long idProfilo);
	
	public List<TreeFunzionalitaDto> findByCodiceApplicazioneAndFunzionalitaTipo(String codiceTipoFunzione, String codiceApplicazione);

	public List<TreeFunzionalitaDto> findIdTreeByFnzId(Long idFunzionalita);
	
}
