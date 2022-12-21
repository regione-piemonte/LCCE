/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao;


import it.csi.configuratorews.business.dto.LoginDataDto;

import java.util.List;

public interface LoginDataLowDao extends EntityBaseLowDao<LoginDataDto, Long> {

	List<LoginDataDto> findByToken(LoginDataDto loginDataDto);
	
	public void update(LoginDataDto loginDataDto);
	
}
