/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.validator.impl;

import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.util.LogUtil;
import it.csi.configuratorews.validator.ProxyAbilitazioniGetValidator;
import it.csi.configuratorews.validator.ProxyCollocazioneGetValidator;

import org.springframework.stereotype.Component;


@Component
public class ProxyCollocazioneGetValidatorImpl extends BaseValidatorImpl implements ProxyCollocazioneGetValidator {

	protected LogUtil log = new LogUtil(this.getClass());

	@Override
	public ErroreBuilder validate(String shibIdentitaCodiceFiscale, String xRequestID,
						 String xForwardedFor, String xCodiceServizio, String codiceRuolo) throws Exception{

		ErroreBuilder erroreBuilder = validateHeader(shibIdentitaCodiceFiscale, xCodiceServizio, xRequestID, xForwardedFor);
		if(erroreBuilder != null) {
			return erroreBuilder;
		}
		
		if(codiceRuolo == null || codiceRuolo.isEmpty()){
			return generateErrore(Constants.PARAMETRO_NON_VALORIZZATO, Constants.CODICE_RUOLO);
		}
		return null;
	}
}
