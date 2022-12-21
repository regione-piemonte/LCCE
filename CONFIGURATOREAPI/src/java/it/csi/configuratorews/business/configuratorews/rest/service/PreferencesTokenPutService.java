/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.configuratorews.rest.service;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

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
import it.csi.configuratorews.business.configuratorews.log.util.Operazione;
import it.csi.configuratorews.business.configuratorews.rest.SOLRESTBaseService;
import it.csi.configuratorews.business.dao.ApplicazioneLowDao;
import it.csi.configuratorews.business.dao.CollocazioneLowDao;
import it.csi.configuratorews.business.dao.PreferenzaFruitoreLowDao;
import it.csi.configuratorews.business.dao.PreferenzaLowDao;
import it.csi.configuratorews.business.dao.PreferenzaSalvataLowDao;
import it.csi.configuratorews.business.dao.RuoloLowDao;
import it.csi.configuratorews.business.dto.ApplicazioneDto;
import it.csi.configuratorews.business.dto.CollocazioneDto;
import it.csi.configuratorews.business.dto.CsiLogAuditDto;
import it.csi.configuratorews.business.dto.PreferenzaDto;
import it.csi.configuratorews.business.dto.PreferenzaFruitoreDto;
import it.csi.configuratorews.business.dto.PreferenzaSalvataDto;
import it.csi.configuratorews.business.dto.RuoloDto;
import it.csi.configuratorews.dto.configuratorews.PreferenzeFruitore;
import it.csi.configuratorews.dto.configuratorews.TokenConfiguration;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.exception.RESTException;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.util.Utils;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class PreferencesTokenPutService extends SOLRESTBaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3630936896399035950L;

	private TokenConfiguration tokenConfiguration;

	private static final String CODICE_LOG_AUDIT = "AUTH_LOG_163";
	private static final String CODICE_SERVIZIO = "PUTTOKEN";
	@Autowired
	LogAuditService logAuditService;
	@Autowired
	ApplicazioneLowDao applicazioneLowDao;
	@Autowired
	CollocazioneLowDao collocazioneLowDao;
	@Autowired
	RuoloLowDao ruoloLowDao;
	@Autowired
	PreferenzaFruitoreLowDao preferenzaFruitoreLowDao;
	@Autowired
	PreferenzaLowDao preferenzaLowDao;
	@Autowired
	PreferenzaSalvataLowDao preferenzaSalvataLowDao;
	
	public static final String url = "/preferences/token/configuration";
	
	public PreferencesTokenPutService(String shibIdentitaCodiceFiscale, String xRequestID, String xForwardedFor, String xCodiceServizio,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request, TokenConfiguration input) {
		super(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request);
		this.tokenConfiguration = input;
	}

	@Override
	protected Response execute() {
		String METHOD_NAME = "execute";
		log.info(METHOD_NAME, "PreferencesTokenPutService versione 1.0.0");
		String response = "error";
		try {
			if ((tokenConfiguration.getPreferenzeFruitoreDaAggiungere() == null || tokenConfiguration.getPreferenzeFruitoreDaAggiungere().isEmpty())
					&& (tokenConfiguration.getPreferenzeFruitoreDaCancellare() == null || tokenConfiguration.getPreferenzeFruitoreDaCancellare().isEmpty())) {
				Response returnResponse = ErroreBuilder.from(Response.Status.BAD_REQUEST).response();
				return returnResponse;
			}
			if(tokenConfiguration.getPreferenzeFruitoreDaAggiungere() != null ) {
				for (PreferenzeFruitore preferenze : tokenConfiguration.getPreferenzeFruitoreDaAggiungere()) {
					List<PreferenzaFruitoreDto> preferenzeFruitori = preferenzaFruitoreLowDao.findByName(preferenze.getNomeFruitore(),preferenze.getApplicazione(), preferenze.getCodiceRuolo(), preferenze.getCodiceCollocazione());
					if(preferenzeFruitori==null||preferenzeFruitori.size()==0) {
						PreferenzaFruitoreDto toSave = new PreferenzaFruitoreDto();
						if(StringUtils.isNotEmpty(preferenze.getApplicazione())){
							ApplicazioneDto filterApplicazione = new ApplicazioneDto();
							filterApplicazione.setCodice(preferenze.getApplicazione());
							Collection<ApplicazioneDto> applicazione = applicazioneLowDao.findByCodice(filterApplicazione);
							if(applicazione.size()>0) {
								toSave.setApplicazioneDto(applicazione.iterator().next() );
							}
						}
						if(StringUtils.isNotEmpty(preferenze.getCodiceCollocazione())){
							CollocazioneDto filterCollocazione = new CollocazioneDto();
							filterCollocazione.setColCodice(preferenze.getCodiceCollocazione());
							Collection<CollocazioneDto> collocazione = collocazioneLowDao.findByFilter(filterCollocazione);
							if(collocazione.size()>0) {
								toSave.setCollocazioneDto(collocazione.iterator().next());
							}
						}
						if(StringUtils.isNotEmpty(preferenze.getCodiceRuolo())){
							RuoloDto filterRuolo = new RuoloDto();
							filterRuolo.setCodice(preferenze.getCodiceRuolo());
							Collection<RuoloDto> ruolo = ruoloLowDao.findByCodice(filterRuolo);
							if(ruolo.size()>0) {
								toSave.setRuoloDto(ruolo.iterator().next());
							}
						}
						toSave.setDescrizioneFruitore(preferenze.getDescrizioneFruitore());
						toSave.setEmail(preferenze.getPreferenza().isEmailSelectable());
						toSave.setNomeFruitore(preferenze.getNomeFruitore());
						toSave.setPush(preferenze.getPreferenza().isPushSelectable());
						toSave.setSms(preferenze.getPreferenza().isSmsSelectable());
						preferenzaFruitoreLowDao.save(toSave);
					}else {
						if(preferenzeFruitori.size()==1) {
							PreferenzaFruitoreDto toSave = preferenzeFruitori.get(0);
							toSave.setDescrizioneFruitore(preferenze.getDescrizioneFruitore());
							toSave.setEmail(preferenze.getPreferenza().isEmailSelectable());
							toSave.setNomeFruitore(preferenze.getNomeFruitore());
							toSave.setPush(preferenze.getPreferenza().isPushSelectable());
							toSave.setSms(preferenze.getPreferenza().isSmsSelectable());
							preferenzaFruitoreLowDao.merge(toSave);
						} else {
							log.error("PreferencesTokenPutService", "not a possible case");
						}
					}
				}
			}
			if(tokenConfiguration.getPreferenzeFruitoreDaCancellare() != null ) {
				for (PreferenzeFruitore preferenze : tokenConfiguration.getPreferenzeFruitoreDaCancellare()) {
					List<PreferenzaFruitoreDto> preferenzeFruitori = preferenzaFruitoreLowDao.findByName(preferenze.getNomeFruitore(),preferenze.getApplicazione(), preferenze.getCodiceRuolo(), preferenze.getCodiceCollocazione());
					for (PreferenzaFruitoreDto preferenzaFruitoreDto : preferenzeFruitori) {
						//cancella preferenze salvate per quel fruitore
						List<PreferenzaSalvataDto> preferenzeSalvate= preferenzaSalvataLowDao.findByIdFruitore(preferenzaFruitoreDto.getId());
						for(PreferenzaSalvataDto preferenzaSalvata:preferenzeSalvate) {
							preferenzaSalvata.setSms(false);
							preferenzaSalvata.setEmail(false);
							preferenzaSalvata.setPush(false);
							preferenzaSalvata.setDataAggiornamento(Utils.sysdate());
							preferenzaSalvata.setDataCancellazione(Utils.sysdate());
							preferenzaSalvataLowDao.merge(preferenzaSalvata);
						}
						preferenzaFruitoreDto.setDataCancellazione( Utils.sysdate());
						preferenzaFruitoreLowDao.merge(preferenzaFruitoreDto);
					}
					
				}
			}
			ObjectMapper objectMapper = new ObjectMapper();
			response = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString("OK");
			return Response.ok("OK").build();
		} catch (RESTException e) {
			log.error("PreferencesTokenPutService", "Errore rest: ", e);
			throw e;
		} catch (Exception e) {
			log.error("PreferencesTokenPutService", "Errore rest: ", e);
			throw ErroreBuilder.from(Response.Status.INTERNAL_SERVER_ERROR).exception();
		} finally {
			/* Update logAudit */
			try {
				String xForwadedForInHeader = extractXForwadedFor(xForwardedFor);
				if (logAuditService.insertLogAudit(request, CODICE_LOG_AUDIT, shibIdentitaCodiceFiscale, CODICE_SERVIZIO, xForwadedForInHeader, xRequestID, xCodiceServizio)) {
					log.info(METHOD_NAME, "log Audit in PreferencesTokenPutService inserito correttamente");
				} else {
					throw new Exception("Errore inserimento log Audit in PreferencesTokenPutService");
				}
			} catch (Exception e) {
				log.error("PreferencesTokenPutService", "log audit: ", e);
				throw ErroreBuilder.from(Response.Status.INTERNAL_SERVER_ERROR).exception();
			}
		}
	}
}
