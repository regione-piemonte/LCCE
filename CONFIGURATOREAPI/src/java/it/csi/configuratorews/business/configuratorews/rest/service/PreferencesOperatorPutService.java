/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.configuratorews.rest.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.csi.configuratorews.business.configuratorews.log.service.LogAuditService;
import it.csi.configuratorews.business.configuratorews.log.service.LogService;
import it.csi.configuratorews.business.configuratorews.rest.SOLRESTBaseService;
import it.csi.configuratorews.business.dao.PreferenzaFruitoreLowDao;
import it.csi.configuratorews.business.dao.PreferenzaLowDao;
import it.csi.configuratorews.business.dao.PreferenzaSalvataLowDao;
import it.csi.configuratorews.business.dto.PreferenzaDto;
import it.csi.configuratorews.business.dto.PreferenzaFruitoreDto;
import it.csi.configuratorews.business.dto.PreferenzaSalvataDto;
import it.csi.configuratorews.dto.configuratorews.Applicazione;
import it.csi.configuratorews.dto.configuratorews.FilterPreferences;
import it.csi.configuratorews.dto.configuratorews.ItemPreference;
import it.csi.configuratorews.dto.configuratorews.Preferences;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.exception.RESTException;
import it.csi.configuratorews.util.Utils;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class PreferencesOperatorPutService extends SOLRESTBaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2724712825510591375L;

	public static final String url = "/preferences/${utente}/save";

	private String cf;
	private Preferences preferences;
	private static final String CODICE_LOG_AUDIT = "AUTH_LOG_161";
	private static final String CODICE_SERVIZIO = "PUTPREFERENCESOPER";
	@Autowired
	LogAuditService logAuditService;
	@Autowired
	PreferenzaLowDao preferenzaLowDao;
	@Autowired
	PreferenzaSalvataLowDao preferenzaSalvataLowDao;
	@Autowired
	PreferenzaFruitoreLowDao preferenzaFruitoreLowDao;

	public PreferencesOperatorPutService(String shibIdentitaCodiceFiscale, String xRequestID, String xForwardedFor, String xCodiceServizio,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request, String utente, Preferences input) {
		super(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request);
		this.cf = utente;
		this.preferences = input;
	}

	@Override
	protected Response execute() {

		String METHOD_NAME = "execute";
		log.info(METHOD_NAME, "PreferencesOperatorGetService versione 1.0.0");
		log.info(METHOD_NAME, "Preferences: " + preferences.toString());
		String response = "error";
		try {
			if (StringUtils.isEmpty(cf)) {
				Response returnResponse = ErroreBuilder.from(Response.Status.BAD_REQUEST).response();
				return returnResponse;
			}

			FilterPreferences filter = new FilterPreferences();
			List<String> codiciFiscali = new ArrayList<String>();
			codiciFiscali.add(cf);
			filter.setCodiciFiscale(codiciFiscali);
			List<PreferenzaDto> preferenzeDb = preferenzaLowDao.findLeftByFilter(filter);
			for (ItemPreference itemPreferenceToSave : preferences.getItemPreference()) {
				if (preferenzeDb != null) {
					for (PreferenzaDto preferenzaDb : preferenzeDb) {
						PreferenzaSalvataDto preferenzaPresenteDb = searchPreferenzaSalvata(preferenzaDb, itemPreferenceToSave);
						if (preferenzaPresenteDb == null && 
								(
										preferenzaDb.getApplicazioneDto() != null
										&& itemPreferenceToSave.getApplicazione() != null
										&& preferenzaDb.getApplicazioneDto().getCodice().equals(itemPreferenceToSave.getApplicazione().getCodice())
										
								|| (
										preferenzaDb.getCollocazioneDto() == null 
										&& itemPreferenceToSave.getCollocazione()==null
										&& itemPreferenceToSave.getRuolo() != null && 
										preferenzaDb.getRuoloDto() != null
										&& preferenzaDb.getRuoloDto().getCodice().equals(itemPreferenceToSave.getRuolo())
										)
								
								|| (
										preferenzaDb.getCollocazioneDto() != null 
										&& itemPreferenceToSave.getRuolo() != null
										&& itemPreferenceToSave.getCollocazione() != null 
										&& preferenzaDb.getRuoloDto() != null
										&& preferenzaDb.getRuoloDto().getCodice().equals(itemPreferenceToSave.getRuolo())
										&& preferenzaDb.getCollocazioneDto().getColCodice().equals(itemPreferenceToSave.getCollocazione())
										
										)

						)) {
							log.info(METHOD_NAME, "preferenza non trovata su db: " + itemPreferenceToSave.toString());
							preferenzaPresenteDb = new PreferenzaSalvataDto();
							preferenzaPresenteDb.setPreferenzaDto(preferenzaDb);
							Applicazione applicazione = itemPreferenceToSave.getApplicazione();
							List<PreferenzaFruitoreDto> fruitoreDb = preferenzaFruitoreLowDao.findByCode(itemPreferenceToSave.getFruitore().getCodice(),
									applicazione == null ? null : applicazione.getCodice(), itemPreferenceToSave.getRuolo(),
									itemPreferenceToSave.getCollocazione());
							if (fruitoreDb.size() == 0) {
								Response returnResponse = ErroreBuilder.from(Response.Status.BAD_REQUEST).response();
								return returnResponse;
							}
							preferenzaPresenteDb.setPreferenzaFruitoreDto(fruitoreDb.get(0));
							preferenzaPresenteDb.setEmail(itemPreferenceToSave.getPreferenza().isEmail());
							preferenzaPresenteDb.setPush(itemPreferenceToSave.getPreferenza().isPush());
							preferenzaPresenteDb.setSms(itemPreferenceToSave.getPreferenza().isSms());
							preferenzaPresenteDb.setDataAggiornamento(Utils.sysdate());
							preferenzaSalvataLowDao.save(preferenzaPresenteDb);
						}
						if (preferenzaPresenteDb != null) {
							log.info(METHOD_NAME, "preferenza trovata su db: " + itemPreferenceToSave.toString());
							preferenzaPresenteDb.setEmail(itemPreferenceToSave.getPreferenza().isEmail());
							preferenzaPresenteDb.setPush(itemPreferenceToSave.getPreferenza().isPush());
							preferenzaPresenteDb.setSms(itemPreferenceToSave.getPreferenza().isSms());
							preferenzaPresenteDb.setDataAggiornamento(Utils.sysdate());
							preferenzaPresenteDb.setDataCancellazione(null);
							preferenzaSalvataLowDao.merge(preferenzaPresenteDb);
						}
					}
				}
			}
			log.info(METHOD_NAME, "applicazioni update concluso per cf: " + cf);

			ObjectMapper objectMapper = new ObjectMapper();
			response = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString("OK");
			return Response.ok("OK").build();
		} catch (RESTException e) {
			log.error("PreferenceGet", "Errore rest: ", e);
			throw e;
		} catch (Exception e) {
			log.error("PreferenceGet", "Errore rest: ", e);
			throw ErroreBuilder.from(Response.Status.INTERNAL_SERVER_ERROR).exception();
		} finally {
			/* Update logAudit */
			try {
				String xForwadedForInHeader = extractXForwadedFor(xForwardedFor);
				if (logAuditService.insertLogAudit(request, CODICE_LOG_AUDIT, shibIdentitaCodiceFiscale, CODICE_SERVIZIO, xForwadedForInHeader, xRequestID, xCodiceServizio)) {
					log.info(METHOD_NAME, "log Audit in PreferencesOperatorPutService inserito correttamente");
				} else {
					throw new Exception("Errore inserimento log Audit in PreferencesOperatorPutService");
				}
			} catch (Exception e) {
				log.error("PreferencesOperatorPutService", "log audit: ", e);
				throw ErroreBuilder.from(Response.Status.INTERNAL_SERVER_ERROR).exception();
			}
		}
	}

	private PreferenzaSalvataDto searchPreferenzaSalvata(PreferenzaDto preferenzaDb, ItemPreference itemPreferenceToSave) {

		if (itemPreferenceToSave.getTipoPreferenza().equals(ItemPreference.RUOLO)) {
			if (preferenzaDb.getRuoloDto() != null 
					&& preferenzaDb.getCollocazioneDto() == null
					&& itemPreferenceToSave.getCollocazione()==null
					&& preferenzaDb.getRuoloDto().getCodice().equals(itemPreferenceToSave.getRuolo())) {
				if (preferenzaDb.getPreferenzaSalvata() != null) {
					for (PreferenzaSalvataDto preferenzaSalvata : preferenzaDb.getPreferenzaSalvata()) {
						if (preferenzaSalvata.getPreferenzaFruitoreDto().getNomeFruitore().equals(itemPreferenceToSave.getFruitore().getCodice())) {
							return preferenzaSalvata;
						}
					}

				}
			}
		}

		if (itemPreferenceToSave.getTipoPreferenza().equals(ItemPreference.RUOLO_COLLOCAZIONE)) {
			if (preferenzaDb.getRuoloDto() != null && preferenzaDb.getRuoloDto().getCodice().equals(itemPreferenceToSave.getRuolo())
					&& preferenzaDb.getCollocazioneDto() != null
					&& preferenzaDb.getCollocazioneDto().getColCodice().equals(itemPreferenceToSave.getCollocazione())) {
				if (preferenzaDb.getPreferenzaSalvata() != null) {
					for (PreferenzaSalvataDto preferenzaSalvata : preferenzaDb.getPreferenzaSalvata()) {
						if (preferenzaSalvata.getPreferenzaFruitoreDto().getNomeFruitore().equals(itemPreferenceToSave.getFruitore().getCodice())) {
							return preferenzaSalvata;
						}
					}

				}
			}
		}

		if (itemPreferenceToSave.getTipoPreferenza().equals(ItemPreference.APPLICAZIONE)) {
			if (preferenzaDb.getApplicazioneDto() != null && itemPreferenceToSave.getApplicazione() != null
					&& preferenzaDb.getApplicazioneDto().getCodice().equals(itemPreferenceToSave.getApplicazione().getCodice())) {
				if (preferenzaDb.getPreferenzaSalvata() != null) {
					for (PreferenzaSalvataDto preferenzaSalvata : preferenzaDb.getPreferenzaSalvata()) {
						if (preferenzaSalvata.getPreferenzaFruitoreDto().getNomeFruitore().equals(itemPreferenceToSave.getFruitore().getCodice())) {
							return preferenzaSalvata;
						}
					}

				}
			}
		}

		return null;
	}
}
