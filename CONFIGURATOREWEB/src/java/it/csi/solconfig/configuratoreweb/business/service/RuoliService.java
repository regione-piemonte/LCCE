/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.service;

import java.util.List;

import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloSelezionabileDto;
import it.csi.solconfig.configuratoreweb.presentation.model.Data;
import it.csi.solconfig.configuratoreweb.presentation.model.PaginaDTO;
import it.csi.solconfig.configuratoreweb.presentation.model.RicercaRuoloModel;
import it.csi.solconfig.configuratoreweb.presentation.model.RuoloModel;

public interface RuoliService extends BaseService {
	
	public PaginaDTO<RuoloDto> ricercaListaRuoli(RicercaRuoloModel ricercaRuoloModel, Boolean flagConfiguratore) throws Exception;
	
	public RuoloDto recuperaRuoloById(Long id) throws Exception;
	
	public void modificaRuolo (RuoloModel ruoloModel, String cfOperatore, Data data) throws Exception;

	List<RuoloSelezionabileDto> ricercaRuoliSelezionabiliBy(Long idRuoloOperatore);
	
}
