/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.validator.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.configuratorews.business.dao.ApplicazioneLowDao;
import it.csi.configuratorews.business.dao.CollocazioneProfiloLowDao;
import it.csi.configuratorews.business.dao.FunzionalitaLowDao;
import it.csi.configuratorews.business.dao.PermessoLowDao;
import it.csi.configuratorews.business.dao.TipologiaDatoLowDao;
import it.csi.configuratorews.business.dto.FunzionalitaDto;
import it.csi.configuratorews.business.dto.TipologiaDatoDto;
import it.csi.configuratorews.dto.configuratorews.InserimentoProfiloFunzionalitaBody;
import it.csi.configuratorews.dto.configuratorews.Permesso;
import it.csi.configuratorews.dto.configuratorews.PermessoValido;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.util.LogUtil;
import it.csi.configuratorews.validator.ProfiliFunzionalitaPostValidator;

@Component
public class ProfiliFunzionalitaPostValidatorImpl extends BaseValidatorImpl
		implements ProfiliFunzionalitaPostValidator {

	protected LogUtil log = new LogUtil(this.getClass());

	@Autowired
	ApplicazioneLowDao applicazioneLowDao;

	@Autowired
	FunzionalitaLowDao funzionalitaLowDao;

	@Autowired
	TipologiaDatoLowDao tipologiaDatoLowDao;

	@Autowired
	PermessoLowDao permessoLowDao;

	@Autowired
	CollocazioneProfiloLowDao collocazioneProfiloLowDao;

	@Override
	public ErroreBuilder validateNuovaFunzionalita(String codiceFunzionalita, String codiceApplicazione,
			String codiceAzienda, InserimentoProfiloFunzionalitaBody body) throws Exception {

		if (codiceFunzionalita != null && !codiceFunzionalita.isEmpty()) {
			// controllo che sia valida la funzionalita' richiesta
			FunzionalitaDto funz = funzionalitaLowDao.findByFunzionalitaApplicazioneAndTipo(codiceFunzionalita,
					codiceApplicazione, Constants.FUNZIONALITA_TIPO_FUNZ);
			if (funz != null) {
				return generateErrore(Constants.FUNZIONALITA_GIA_PRESENTE_PER_APPLICATIVO,
						Constants.CODICE_FUNZIONALITA);
			}

			// controllo presenza listaProfili
			if (body.getListaProfili() == null || body.getListaProfili().size() == 0) {
				return generateErrore(Constants.LISTA_CODICI_PROFILO_ASSENTE, Constants.CODICE_PROFILO);
			} else {
				// Se lista profili presenti verificarne l'esistenza in funzione di codice
				// applicazione e codice azienda
				for (String codice : body.getListaProfili()) {
					if (collocazioneProfiloLowDao.existsApplicazioneByAziendaAndFunzionalita(codiceAzienda,
							codiceApplicazione)) {
						// il profilo deve essere tra quelli presenti in AUTH_R_COLAZIENDA_PROFILO
						if (!collocazioneProfiloLowDao.existsProfiloByAzienda(codiceAzienda, codice)) {
							return generateErrore(Constants.PROFILO_NON_PRESENTE_PER_APPLICATIVO,
									Constants.CODICE_PROFILO);
						}
					} else {
						// il profilo deve essere tra quelli presenti in AUTH_T_FUNZIONALITA
						if (!funzionalitaLowDao.existsProfiloByApplicazione(codice, codiceApplicazione)) {
							return generateErrore(Constants.PROFILO_NON_PRESENTE_PER_APPLICATIVO,
									Constants.CODICE_PROFILO);
						}
					}
				}
			}

			if (body.getTipologiaDatiPermessi() == null || body.getTipologiaDatiPermessi().size() == 0) {
				return generateErrore(Constants.LISTA_TIPOLOGIA_DATI_PERMESSI_ASSENTE, Constants.CODICE_PERMESSO);
			} else {
				// controllo tipologia dato
				if (body.getTipologiaDatiPermessi() != null && body.getTipologiaDatiPermessi().size() > 0) {
					for (Permesso p : body.getTipologiaDatiPermessi()) {
						if (!tipologiaDatoLowDao.existsByCodice(p.getCodiceTipologiaDato()))
							return generateErrore(Constants.TIPOLOGIA_DATO_NON_VALIDO, Constants.CODICE_TIPOLOGIA_DATO);
					}
				}

				// controllo permesso
				if (body.getTipologiaDatiPermessi() != null && body.getTipologiaDatiPermessi().size() > 0) {
					for (Permesso p : body.getTipologiaDatiPermessi()) {
						if (!permessoLowDao.existsByCodice(p.getCodicePermesso())) {
							return generateErrore(Constants.PERMESSO_NON_VALIDO, Constants.CODICE_PERMESSO);
						}

					}
				}

				// Verifica che tutte le tipologie di dati siano state fornite
				if (body.getTipologiaDatiPermessi() != null && body.getTipologiaDatiPermessi().size() > 0) {
					List<TipologiaDatoDto> tipologie = tipologiaDatoLowDao.findAllValide();

					if (tipologie.size() != body.getTipologiaDatiPermessi().size()) {
						return generateErrore(Constants.TIPOLOGIE_DATO_MANCANTI, Constants.CODICE_PERMESSO);
					}
				}
			}
		}

		return null;

	}

	@Override
	public ErroreBuilder validateCampiObbligatori(String codiceFunzionalita, String codiceProfilo,
			String descrizioneProfilo, String descrizioneFunzionalita) throws Exception {

		if ((codiceFunzionalita == null || codiceFunzionalita.isEmpty() || descrizioneFunzionalita == null
				|| descrizioneFunzionalita.isEmpty())
				&& (codiceProfilo == null || codiceProfilo.isEmpty() || descrizioneProfilo == null
						|| descrizioneProfilo.isEmpty())) {
			return generateErrore(Constants.VALORIZZAZIONE_PROFILO_O_FUNZIONALITA, Constants.CODICE_PROFILO);
		}

		return null;
	}

}
