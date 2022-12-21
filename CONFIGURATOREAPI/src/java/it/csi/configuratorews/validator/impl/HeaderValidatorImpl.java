/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.validator.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.configuratorews.business.dao.CollocazioneLowDao;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.validator.HeaderValidator;

@Component
public class HeaderValidatorImpl extends BaseValidatorImpl implements HeaderValidator {
	
	@Autowired
	CollocazioneLowDao collocazioneLowDao;

	@Override
	public ErroreBuilder validate(String shibIdentitaCodiceFiscale, String xRequestID, String xForwardedFor,
			String xCodiceServizio) throws Exception {

		ErroreBuilder erroreBuilder = validateHeader(shibIdentitaCodiceFiscale, xCodiceServizio, xRequestID,
				xForwardedFor);
		if (erroreBuilder != null) {
			return erroreBuilder;
		}

		return null;
	}
	
	
	public  ErroreBuilder checkIntegerPositiveNotNull(Integer testInteger,String fieldName) throws Exception {
		if(testInteger==null) {
			return generateErrore(Constants.PARAMETRO_NON_VALORIZZATO, fieldName);
		}else if( testInteger<0) {
			return generateErrore(Constants.PARAMETRO_NON_VALIDO, fieldName);
		}
		return null;
	}
}
