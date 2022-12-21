/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.validator;

import it.csi.configuratorews.exception.ErroreBuilder;

public interface HeaderLimitOffsetValidator {

	ErroreBuilder validate(String shibIdentitaCodiceFiscale, String xRequestID, String xForwardedFor,
			String xCodiceServizio, Integer limit, Integer offset) throws Exception;
	
	ErroreBuilder validate(String shibIdentitaCodiceFiscale, String xRequestID, String xForwardedFor,
			String xCodiceServizio, String limit, String offset) throws Exception;
	
}
