/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.validator;

import it.csi.configuratorews.dto.configuratorews.ParametriAutenticazione;
import it.csi.configuratorews.exception.ErroreBuilder;

public interface ProxyTokenAuthenticationGetValidator {


	ErroreBuilder validate(String shibIdentitaCodiceFiscale, String xRequestID,
				  String xForwardedFor, String xCodiceServizio, ParametriAutenticazione parametriAutenticazione) throws Exception;
}
