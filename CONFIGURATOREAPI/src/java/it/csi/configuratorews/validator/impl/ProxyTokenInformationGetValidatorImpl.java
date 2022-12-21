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
import it.csi.configuratorews.validator.ProxyRuoliUtenteGetValidator;
import it.csi.configuratorews.validator.ProxyTokenInformationGetValidator;

import org.springframework.stereotype.Component;


@Component
public class ProxyTokenInformationGetValidatorImpl extends BaseValidatorImpl implements ProxyTokenInformationGetValidator {

	protected LogUtil log = new LogUtil(this.getClass());

	@Override
	public ErroreBuilder validate(String shibIdentitaCodiceFiscale, String xRequestID,
						 String xForwardedFor, String xCodiceServizio, String token) throws Exception{

		ErroreBuilder erroreBuilder = validateHeader(shibIdentitaCodiceFiscale, xCodiceServizio, xRequestID, xForwardedFor);
		if(erroreBuilder != null) {
			return erroreBuilder;
		}
		
		if(token == null || token.isEmpty()) {
			return generateErrore(Constants.PARAMETRO_NON_VALORIZZATO, Constants.TOKEN);
		}
		
		return null;
	}
}
