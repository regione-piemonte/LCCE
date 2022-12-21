/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao.impl;

import it.csi.configuratorews.business.dao.RichiestaCredenzialiLowDao;
import it.csi.configuratorews.business.dto.RichiestaCredenzialiDto;
import org.springframework.stereotype.Component;


@Component
public class RichiestaCredenzialiLowDaoImpl extends EntityBaseLowDaoImpl<RichiestaCredenzialiDto, Long> implements RichiestaCredenzialiLowDao {

	 	
	 	 public String getTabName(Object className) {
		        return className.getClass().getSimpleName();
	 	 }
}
