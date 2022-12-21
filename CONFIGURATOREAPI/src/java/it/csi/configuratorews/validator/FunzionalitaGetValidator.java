/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.validator;

import java.util.List;

import it.csi.configuratorews.exception.ErroreBuilder;

public interface FunzionalitaGetValidator {

	ErroreBuilder validate(String shibIdentitaCodiceFiscale, String xRequestID,
			 String xForwardedFor, String xCodiceServizio, String codiceFunzionalita) throws Exception;

}
