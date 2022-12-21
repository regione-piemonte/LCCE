/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.validator.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.util.LogUtil;
import it.csi.configuratorews.validator.FunzionalitaIfProfiloValidator;

@Component
public class FunzionalitaIfProfiloValidatorImpl extends BaseValidatorImpl implements FunzionalitaIfProfiloValidator {

	protected LogUtil log = new LogUtil(this.getClass());
	
	@Override
	public ErroreBuilder validate(String shibIdentitaCodiceFiscale, String xRequestID, String xForwardedFor,
			String xCodiceServizio, String codiceFunzionalita, String codiceProfilo) throws Exception {
		ErroreBuilder erroreBuilder = validateHeader(shibIdentitaCodiceFiscale, xCodiceServizio, xRequestID, xForwardedFor);
		if(erroreBuilder != null) {
			return erroreBuilder;
		}
		if(StringUtils.isNotBlank(codiceFunzionalita)&& StringUtils.isBlank(codiceProfilo)){
			return	generateErrore(Constants.FUNZIONALITA_SENZA_PROFILO, Constants.CODICE_PROFILO);
		}

		return null;
	}





}
