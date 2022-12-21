/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.validator;

import javax.ws.rs.core.HttpHeaders;

import it.csi.configuratorews.exception.ErroreBuilder;

public interface SistemiRichiedentiValidator {
	
	ErroreBuilder validate(String shibIdentitaCodiceFiscale, String xRequestID, String xForwardedFor,
			String xCodiceServizio, String codiceAzienda) throws Exception;
	
	ErroreBuilder validateJWT(HttpHeaders httpHeaders, String xCodiceServizio) throws Exception;
	
}
