/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.configuratorews.rest.service;

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
import it.csi.configuratorews.business.dao.PreferenzaLowDao;
import it.csi.configuratorews.business.dao.PreferenzaSalvataLowDao;
import it.csi.configuratorews.business.dao.UtenteLowDao;
import it.csi.configuratorews.business.dto.CsiLogAuditDto;
import it.csi.configuratorews.business.dto.PreferenzaDto;
import it.csi.configuratorews.business.dto.PreferenzaSalvataDto;
import it.csi.configuratorews.business.dto.UtenteDto;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.exception.RESTException;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.util.Utils;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class PreferencesUserPostService extends SOLRESTBaseService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String CODICE_LOG_AUDIT = "AUTH_LOG_165";
	private static final String CODICE_SERVIZIO = "POSTDELETEPREF";
	@Autowired
	LogAuditService logAuditService;
	@Autowired
	UtenteLowDao utenteDao;
	@Autowired
	PreferenzaLowDao preferenzaLowDao;
	@Autowired
	PreferenzaSalvataLowDao preferenzaSalvataLowDao;
	

	private String cf;

	public static final String url = "/preferencesUsers/{utente}/elimina";

	public PreferencesUserPostService(String shibIdentitaCodiceFiscale, String xRequestID, String xForwardedFor, String xCodiceServizio,
			SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request, String codiceFiscale) {
		super(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request);
		this.cf = codiceFiscale;
	}

	@Override
	protected Response execute() {
		String METHOD_NAME = "execute";
		log.info(METHOD_NAME, "PreferencesUserPostService versione 1.0.0");
		String response = "error";
		try {
			if(StringUtils.isEmpty(cf)) {
				Response returnResponse = ErroreBuilder.from(Response.Status.BAD_REQUEST).response();
				return returnResponse;
			}
			Collection<UtenteDto> utenteDTO = utenteDao.findByCodiceFiscaleValido(cf);
			if(utenteDTO != null && utenteDTO.size()==1){
				for(UtenteDto utente: utenteDTO) {
					utente.setEmailPreferenze(null);
					utente.setTelefonoPreferenze(null);
					utente.setTokenPush(null);
					utenteDao.merge(utente);
				}
			}else {
				Response returnResponse = ErroreBuilder.from(Response.Status.BAD_REQUEST).response();
				return returnResponse;
			}
			log.info(METHOD_NAME, "cancellato contatti cf: "+cf);
			List<PreferenzaDto> resultQueryApplicazione = preferenzaLowDao.findApplicazionePreferenzeByCFAndApplicazioni(this.cf, null,false);
			for (PreferenzaDto preferenzaDto : resultQueryApplicazione) {
				if(preferenzaDto.getPreferenzaSalvata()!=null) {
					for(PreferenzaSalvataDto preferenzaSalvata:preferenzaDto.getPreferenzaSalvata()) {
						preferenzaSalvata.setSms(false);
						preferenzaSalvata.setEmail(false);
						preferenzaSalvata.setPush(false);
						preferenzaSalvata.setDataAggiornamento(Utils.sysdate());
						preferenzaSalvata.setDataCancellazione(Utils.sysdate());
						preferenzaSalvataLowDao.merge(preferenzaSalvata);
					}
				}
			}
			log.info(METHOD_NAME, "cancellato preferenze applicazione cf: "+cf);
			List<PreferenzaDto> resultQueryRuoloCollocazione = preferenzaLowDao.findRuoloCollocazioniPreferenzeByCFAndRuoloCollocazioni(this.cf, null,false);
			for (PreferenzaDto preferenzaDto : resultQueryRuoloCollocazione) {
				if(preferenzaDto.getPreferenzaSalvata()!=null) {
					for(PreferenzaSalvataDto preferenzaSalvata:preferenzaDto.getPreferenzaSalvata()) {
						preferenzaSalvata.setSms(false);
						preferenzaSalvata.setEmail(false);
						preferenzaSalvata.setPush(false);
						preferenzaSalvata.setDataAggiornamento(Utils.sysdate());
						preferenzaSalvata.setDataCancellazione(Utils.sysdate());
						preferenzaSalvataLowDao.merge(preferenzaSalvata);
					}
				}
			}
			log.info(METHOD_NAME, "cancellato preferenze ruolo collocazione cf: "+cf);
			List<PreferenzaDto> resultQueryRuolo = preferenzaLowDao.findRuoloPreferenzeByCFAndRuolo(this.cf, null,false);
			for (PreferenzaDto preferenzaDto : resultQueryRuolo) {
				if(preferenzaDto.getPreferenzaSalvata()!=null) {
					for(PreferenzaSalvataDto preferenzaSalvata:preferenzaDto.getPreferenzaSalvata()) {
						preferenzaSalvata.setSms(false);
						preferenzaSalvata.setEmail(false);
						preferenzaSalvata.setPush(false);
						preferenzaSalvata.setDataAggiornamento(Utils.sysdate());
						preferenzaSalvata.setDataCancellazione(Utils.sysdate());
						preferenzaSalvataLowDao.merge(preferenzaSalvata);
					}
				}
			}
			log.info(METHOD_NAME, "cancellato preferenze ruolo  cf: "+cf);
			ObjectMapper objectMapper = new ObjectMapper();
			response = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString("OK");
			return Response.ok("OK").build();
		}  catch(RESTException e){
        	log.error("PreferenceGet", "Errore rest: ", e);
			throw e;
		}catch(Exception e){
			log.error("PreferenceGet", "Errore rest: ", e);
			throw ErroreBuilder.from(Response.Status.INTERNAL_SERVER_ERROR).exception();
        }finally{
        	/* Update logAudit */
			try {
				String xForwadedForInHeader = extractXForwadedFor(xForwardedFor);
				if (logAuditService.insertLogAudit(request, CODICE_LOG_AUDIT, shibIdentitaCodiceFiscale, CODICE_SERVIZIO, xForwadedForInHeader, xRequestID, xCodiceServizio)) {
					log.info(METHOD_NAME, "log Audit in PreferencesUserPostService inserito correttamente");
				} else {
					throw new Exception("Errore inserimento log Audit in PreferencesUserPostService");
				}
			} catch (Exception e) {
				log.error("PreferencesUserPostService", "log audit: ", e);
				throw ErroreBuilder.from(Response.Status.INTERNAL_SERVER_ERROR).exception();
			}
        }
	}

}
