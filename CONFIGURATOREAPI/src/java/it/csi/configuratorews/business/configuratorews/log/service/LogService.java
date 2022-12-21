/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.configuratorews.log.service;

import it.csi.configuratorews.business.configuratorews.log.util.Operazione;
import it.csi.configuratorews.business.dto.CsiLogAuditDto;

public interface LogService {

	CsiLogAuditDto logAttivazione(Operazione operazione, String key_operation, String uUid, String xForwardedFor,
								  String shibIdentitaCodiceFiscale, String xRequestID, String xCodiceServizio, String request);
	
	void updateLog(CsiLogAuditDto csiLogAuditDto, String response);

}
