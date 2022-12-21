/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.validator.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.configuratorews.business.dao.FunzionalitaLowDao;
import it.csi.configuratorews.business.dao.RuoloLowDao;
import it.csi.configuratorews.business.dto.FunzionalitaDto;
import it.csi.configuratorews.business.dto.RuoloDto;
import it.csi.configuratorews.dto.configuratorews.InserimentoProfiloFunzionalitaBody;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.util.LogUtil;
import it.csi.configuratorews.util.Utils;
import it.csi.configuratorews.validator.ProfiloGetValidator;

@Component
public class ProfiloGetValidatorImpl extends BaseValidatorImpl implements ProfiloGetValidator {

	protected LogUtil log = new LogUtil(this.getClass());

	@Autowired
	FunzionalitaLowDao funzionalitaLowDao;

	@Autowired
	RuoloLowDao ruoloLowDao;

	@Override
	public ErroreBuilder validate(String shibIdentitaCodiceFiscale, String xRequestID, String xForwardedFor,
			String xCodiceServizio, String codiceProfilo) throws Exception {
		ErroreBuilder erroreBuilder = validateHeader(shibIdentitaCodiceFiscale, xCodiceServizio, xRequestID,
				xForwardedFor);
		if (erroreBuilder != null) {
			return erroreBuilder;
		}
		if (codiceProfilo == null || codiceProfilo.isEmpty()) {
			return generateErrore(Constants.PARAMETRO_NON_VALORIZZATO, Constants.CODICE_PROFILO);
		} else {
			FunzionalitaDto funzionalitaDto = new FunzionalitaDto();
			funzionalitaDto = Utils.getFirstRecord(funzionalitaLowDao
					.findByCodiceFunzioneAndCodiceTipoFunzione(codiceProfilo, Constants.FUNZIONALITA_TIPO_PROFILO));
			if (funzionalitaDto == null) {
				return generateErrore(Constants.PROFILO_ERRATO, Constants.CODICE_PROFILO);
			}
		}

		return null;

	}

	@Override
	public ErroreBuilder validateNuovoProfilo(String codiceProfilo, String codiceApplicazione,
			InserimentoProfiloFunzionalitaBody body) throws Exception {

		if (codiceProfilo != null && !codiceProfilo.isEmpty()) {
			FunzionalitaDto funzionalitaDto = new FunzionalitaDto();
			funzionalitaDto = Utils.getFirstRecord(funzionalitaLowDao.findByCodiceAndCodiceTipoAndApplicazioneCodice(
					codiceProfilo, Constants.FUNZIONALITA_TIPO_PROFILO, codiceApplicazione));
			if (funzionalitaDto != null) {
				return generateErrore(Constants.PROFILO_GIA_PRESENTE_PER_APPLICATIVO, Constants.CODICE_PROFILO);
			}

			// controllo presenza listaRuoli
			if (body.getListaRuoli() == null || body.getListaRuoli().size() == 0) {
				return generateErrore(Constants.LISTA_CODICI_RUOLI_ASSENTE, Constants.CODICE_PROFILO);
			} else {
				// Se lista ruoli presente verificarne la validita'
				for (String codice : body.getListaRuoli()) {
					RuoloDto ruoloDto = Utils.getFirstRecord(ruoloLowDao.findByCodiceAndDataValidita(codice));
					if (ruoloDto == null) {
						return generateErrore(Constants.CODICI_RUOLO_NON_VALIDI, Constants.CODICE_PROFILO);
					}
				}
			}
		}

		return null;
	}

}
