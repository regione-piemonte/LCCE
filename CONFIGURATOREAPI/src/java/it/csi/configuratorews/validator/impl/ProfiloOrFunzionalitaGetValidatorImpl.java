/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.validator.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.configuratorews.business.dao.FunzionalitaLowDao;
import it.csi.configuratorews.business.dto.FunzionalitaDto;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.util.LogUtil;
import it.csi.configuratorews.util.Utils;
import it.csi.configuratorews.validator.FunzionalitaIfProfiloValidator;
import it.csi.configuratorews.validator.ProfiloOrFunzionalitaGetValidator;

@Component
public class ProfiloOrFunzionalitaGetValidatorImpl extends BaseValidatorImpl implements ProfiloOrFunzionalitaGetValidator {

	protected LogUtil log = new LogUtil(this.getClass());
	
	@Autowired
	private FunzionalitaLowDao funzionalitaLowDao;

	@Override
	public ErroreBuilder validate(String shibIdentitaCodiceFiscale, String xRequestID, String xForwardedFor,
			String xCodiceServizio, String codiceFunzionalita, String codiceTipo, String codiceApplicazione)
			throws Exception {
		ErroreBuilder erroreBuilder = validateHeader(shibIdentitaCodiceFiscale, xCodiceServizio, xRequestID, xForwardedFor);
		if(erroreBuilder != null) {
			return erroreBuilder;
		}
		if(StringUtils.isBlank(codiceFunzionalita)){
			return	generateErrore(Constants.PARAMETRO_NON_VALORIZZATO, Constants.CODICE_FUNZIONALITA);
		}
		if(StringUtils.isBlank(codiceTipo)){
			return	generateErrore(Constants.PARAMETRO_NON_VALORIZZATO, Constants.FUNZIONALITA_TIPO);
		}
		if(StringUtils.isBlank(codiceApplicazione)){
			return	generateErrore(Constants.PARAMETRO_NON_VALORIZZATO, Constants.CODICE_APPLICAZIONE);
		}
		
		FunzionalitaDto funzionalitaDto = new FunzionalitaDto();
		funzionalitaDto = Utils.getFirstRecord(funzionalitaLowDao.findByCodiceAndCodiceTipoAndApplicazioneCodice(codiceFunzionalita,  codiceTipo,  codiceApplicazione));
			if(funzionalitaDto == null){
				return generateErrore(Constants.PROFILO_O_FUNZIONALITA_ASSENTE, Constants.CODICE_PROF_OR_FUNZ);
			}
		

		return null;
	}





}
