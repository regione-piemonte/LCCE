/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.validator.impl;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.validator.FieldMandatoryValidator;

@Component
public class FieldMandatoryValidatorImpl extends BaseValidatorImpl implements FieldMandatoryValidator {

	@Override
	public ErroreBuilder validate(String field, String descrizioneCampo, String[] valoriAmmessi) throws Exception {
		if (field == null || field.isEmpty()) {
			return generateErrore(Constants.PARAMETRO_NON_VALORIZZATO, descrizioneCampo);
		}
		if(valoriAmmessi!=null) {
			if(!Arrays.stream(valoriAmmessi).anyMatch(field::equals)) {
				if(Constants.FUNZIONALITA_TIPO.equals(descrizioneCampo)) {
					return generateErrore(Constants.FUNZIONALITA_TIPO_ERRORE, descrizioneCampo);
				}
				return generateErrore(Constants.PARAMETRO_NON_VALIDO, descrizioneCampo);
			}
		}
		return null;
	}

}
