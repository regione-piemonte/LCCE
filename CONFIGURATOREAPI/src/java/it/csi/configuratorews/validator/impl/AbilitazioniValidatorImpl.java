/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.validator.impl;

import it.csi.configuratorews.business.dao.AbilitazioneLowDao;
import it.csi.configuratorews.business.dao.CollocazioneLowDao;
import it.csi.configuratorews.business.dto.AbilitazioneDto;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.util.LogUtil;
import it.csi.configuratorews.validator.AbilitazioniValidator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AbilitazioniValidatorImpl extends BaseValidatorImpl implements AbilitazioniValidator {

	protected LogUtil log = new LogUtil(this.getClass());

	@Autowired
	AbilitazioneLowDao abilitazioneLowDao;

	@Autowired
	CollocazioneLowDao collocazioneLowDao;

	@Override
	public ErroreBuilder checkCampiObbligatori(String shibIdentitaCodiceFiscale, String xRequestID,
			String xForwardedFor, String xCodiceServizio, String codiceRuolo, String codiceCollocazione,
			String codiceAzienda) throws Exception {

		ErroreBuilder erroreBuilder = validateHeader(shibIdentitaCodiceFiscale, xCodiceServizio, xRequestID,
				xForwardedFor);
		if (erroreBuilder != null) {
			return erroreBuilder;
		}

		if (codiceRuolo == null || codiceRuolo.isEmpty()) {
			return generateErrore(Constants.PARAMETRO_NON_VALORIZZATO, Constants.CODICE_RUOLO);
		}

		if (codiceCollocazione == null || codiceCollocazione.isEmpty()) {
			return generateErrore(Constants.PARAMETRO_NON_VALORIZZATO, Constants.CODICE_COLLOCAZIONE);
		}

		if (codiceAzienda == null || codiceAzienda.isEmpty()) {
			return generateErrore(Constants.PARAMETRO_NON_VALORIZZATO, Constants.CODICE_AZIENDA);
		}

		return null;

	}

	@Override
	public ErroreBuilder verificaAbilitazioniAttive(String codiceProfilo, String codiceApplicazione) throws Exception {

		List<AbilitazioneDto> abilitazioniAttive = abilitazioneLowDao.findByCodiceProfiloAndApplicazione(codiceProfilo,
				codiceApplicazione);

		if (abilitazioniAttive.size() > 0) {
			return generateErrore(Constants.ABILITAZIONI_ATTIVE_PER_PROFILO, Constants.CODICE_PROFILO);
		}

		return null;
	}

	@Override
	public ErroreBuilder verificaAbilitazioneUtente(String codiceFiscale, String codiceCollocazione, String codiceRuolo,
			String codiceApplicazione) throws Exception {

		String codiceAzienda = collocazioneLowDao.getCodAziendaByRuoloCollAndCF(codiceFiscale, codiceCollocazione,
				codiceRuolo, codiceApplicazione);

		if (codiceAzienda == null) {
			return generateErrore(Constants.ABILITAZIONE_NON_ATTIVA, Constants.CODICE_AZIENDA);
		}

		return null;
	}
}
