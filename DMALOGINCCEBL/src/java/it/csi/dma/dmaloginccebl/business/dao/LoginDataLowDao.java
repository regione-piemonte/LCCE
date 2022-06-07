/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.business.dao;

import java.util.List;

import it.csi.dma.dmaloginccebl.business.dao.dto.LoginDataDto;

public interface LoginDataLowDao extends EntityBaseLowDao<LoginDataDto, Long> {

	List<LoginDataDto> findByToken(LoginDataDto loginDataDto);
	
	public void update(LoginDataDto loginDataDto);
	
}
