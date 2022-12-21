/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.validator.impl;

import it.csi.configuratorews.dto.configuratorews.ParametriAutenticazione;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.util.LogUtil;
import it.csi.configuratorews.validator.ProxyAbilitazioniGetValidator;
import it.csi.configuratorews.validator.ProxyCollocazioneGetValidator;
import it.csi.configuratorews.validator.ProxyRuoliUtenteGetValidator;
import it.csi.configuratorews.validator.ProxyTokenAuthenticationGetValidator;

import org.springframework.stereotype.Component;


@Component
public class ProxyTokenAuthenticatonGetValidatorImpl extends BaseValidatorImpl implements ProxyTokenAuthenticationGetValidator {

	protected LogUtil log = new LogUtil(this.getClass());

	@Override
	public ErroreBuilder validate(String shibIdentitaCodiceFiscale, String xRequestID,
						 String xForwardedFor, String xCodiceServizio,  ParametriAutenticazione parametriAutenticazione) throws Exception{

		ErroreBuilder erroreBuilder = validateHeader(shibIdentitaCodiceFiscale, xCodiceServizio, xRequestID, xForwardedFor);
		
		if(erroreBuilder != null) {
			return erroreBuilder;
		}
		
		if(parametriAutenticazione != null) {
			if(parametriAutenticazione.getApplicazione() == null || parametriAutenticazione.getApplicazione().isEmpty()) {
				return	generateErrore(Constants.PARAMETRO_NON_VALORIZZATO, Constants.APPLICAZIONE);
			}
			if(parametriAutenticazione.getAzienda() == null || parametriAutenticazione.getAzienda().isEmpty()) {
				return	generateErrore(Constants.PARAMETRO_NON_VALORIZZATO, Constants.AZIENDA);
			}
			if(parametriAutenticazione.getCollocazione() == null || parametriAutenticazione.getCollocazione().isEmpty()) {
				return	generateErrore(Constants.PARAMETRO_NON_VALORIZZATO, Constants.CODICE_COLLOCAZIONE);
			}
			if(parametriAutenticazione.getRuolo() == null || parametriAutenticazione.getRuolo().isEmpty()) {
				return	generateErrore(Constants.PARAMETRO_NON_VALORIZZATO, Constants.CODICE_RUOLO);
			}
		} else {
			return	generateErrore(Constants.PARAMETRO_NON_VALORIZZATO, Constants.PARAMETRI_AUTENTICAZIONE);
		}
		
		return null;
	}
}
