/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao.impl;

import it.csi.configuratorews.business.dao.LogLowDao;
import it.csi.configuratorews.business.dto.LogDto;
import org.springframework.stereotype.Component;

@Component
public class LogLowDaoImpl extends EntityBaseLowDaoImpl<LogDto, Long> implements LogLowDao {
	
	
}