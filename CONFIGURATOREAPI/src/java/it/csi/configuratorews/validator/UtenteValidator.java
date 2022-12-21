/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.validator;

import it.csi.configuratorews.exception.ErroreBuilder;

public interface UtenteValidator {

	ErroreBuilder validate(String shibIdentitaCodiceFiscale, String xRequestID,
                           String xForwardedFor, String xCodiceServizio) throws Exception;
	
	ErroreBuilder validateCodiceFiscale(String codiceFiscale) throws Exception;
}
