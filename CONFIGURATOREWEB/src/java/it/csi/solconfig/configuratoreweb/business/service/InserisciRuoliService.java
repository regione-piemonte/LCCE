/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.service;

import java.util.List;

import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloSelCollTipo;

public interface InserisciRuoliService extends BaseService {
	
	public List<RuoloDto> findByCodice(RuoloDto ruoloDto) throws Exception;
	
	public boolean checkCodicePresente(RuoloDto ruoloDto) throws Exception;
	
	public RuoloDto insertRuolo(RuoloDto ruolo, String cfUtente, Boolean flag, List<String> newRuoliComp ,List<RuoloSelCollTipo> newRuoliSel) throws Exception;
	
}
