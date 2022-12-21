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
import it.csi.configuratorews.validator.HeaderLimitOffsetValidator;

@Component
public class HeaderLimitOffsetValidatorImpl extends BaseValidatorImpl implements HeaderLimitOffsetValidator {

	@Autowired
	CollocazioneLowDao collocazioneLowDao;
	
	@Override
	public ErroreBuilder validate(String shibIdentitaCodiceFiscale, String xRequestID, String xForwardedFor,
			String xCodiceServizio, Integer limit, Integer offset) throws Exception {
		
		ErroreBuilder erroreBuilder = validateHeader(shibIdentitaCodiceFiscale, xCodiceServizio, xRequestID,
				xForwardedFor);
		if (erroreBuilder != null) {
			return erroreBuilder;
		}
		erroreBuilder = checkIntegerPositiveNotNull(limit, Constants.LIMIT);
		if (erroreBuilder != null) {
			return erroreBuilder;
		}

		erroreBuilder = checkIntegerPositiveNotNull(offset, Constants.OFFSET);
		if (erroreBuilder != null) {
			return erroreBuilder;
		}

		return null;
	}

	public ErroreBuilder checkIntegerPositiveNotNull(Integer testInteger, String fieldName) throws Exception {
		if (testInteger == null) {
			return generateErrore(Constants.PARAMETRO_NON_VALORIZZATO, fieldName);
		} else if (testInteger < 0) {
			return generateErrore(Constants.PARAMETRO_NON_VALIDO, fieldName);
		}
		return null;
	}

	@Override
	public ErroreBuilder validate(String shibIdentitaCodiceFiscale, String xRequestID, String xForwardedFor,
			String xCodiceServizio, String limit, String offset) throws Exception {

		ErroreBuilder erroreBuilder = validateHeader(shibIdentitaCodiceFiscale, xCodiceServizio, xRequestID,
				xForwardedFor);
		if (erroreBuilder != null) {
			return erroreBuilder;
		}
		erroreBuilder = checkIntegerPositiveNotNull(limit, Constants.LIMIT);
		if (erroreBuilder != null) {
			return erroreBuilder;
		}

		erroreBuilder = checkIntegerPositiveNotNull(offset, Constants.OFFSET);
		if (erroreBuilder != null) {
			return erroreBuilder;
		}

		return null;
	}

	public ErroreBuilder checkIntegerPositiveNotNull(String testInteger, String fieldName) throws Exception {

		if (testInteger == null) {
			return generateErrore(Constants.PARAMETRO_NON_VALORIZZATO, fieldName);
		} else {
			try {
				Integer number = Integer.parseInt(testInteger);
				if (number < 0) {
					return generateErrore(Constants.PARAMETRO_NON_VALIDO, fieldName);
				}
			} catch (NumberFormatException ex) {
				return generateErrore(Constants.PARAMETRO_NON_VALIDO, fieldName);
			}			
		}

		return null;
	}

}
