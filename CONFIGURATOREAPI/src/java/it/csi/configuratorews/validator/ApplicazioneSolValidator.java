/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.validator;

import it.csi.configuratorews.exception.ErroreBuilder;

public interface ApplicazioneSolValidator {

	ErroreBuilder validate(String shibIdentitaCodiceFiscale, String xRequestID, String xForwardedFor,
			String xCodiceServizio, String applicazione) throws Exception;

	ErroreBuilder validateCoerenzaAzienda(String applicazione, String azienda) throws Exception;
	
	ErroreBuilder validateFlagBloccoModifica(String applicazione) throws Exception;
	
}
