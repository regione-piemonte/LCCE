/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao;

import it.csi.solconfig.configuratoreweb.business.dao.dto.TipoFunzionalitaDto;

public interface TipoFunzionalitaLowDao extends EntityBaseLowDao<TipoFunzionalitaDto, Long> {

	TipoFunzionalitaDto findByCodiceTipoFunzione(String cODICE_TIPO_FUNZIONALITA_APP);
	
}
