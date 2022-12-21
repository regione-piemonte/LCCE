/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.configuratorews.log.service;

import javax.servlet.http.HttpServletRequest;

import it.csi.configuratorews.business.configuratorews.log.util.Operazione;
import it.csi.configuratorews.business.dto.LogAuditDto;

public interface LogAuditService {

	boolean insertLogAudit(HttpServletRequest httpRequest, String codiceLog, String shibIdentitaCodiceFiscale, String codSer,
			String xForwardedFor, String xRequestID, String xCodiceServizio);
	
}
