/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.configuratorews.rest.service;

import java.util.ArrayList;
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
import it.csi.configuratorews.business.dto.CsiLogAuditDto;
import it.csi.configuratorews.business.dto.PreferenzaDto;
import it.csi.configuratorews.dto.configuratorews.FilterPreferences;
import it.csi.configuratorews.dto.configuratorews.UserPreference;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.exception.RESTException;
import it.csi.configuratorews.util.Constants;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class PreferencesService extends SOLRESTBaseService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	LogAuditService logAuditService;
	@Autowired
	PreferenzaLowDao preferenzaLowDao;
	
	private FilterPreferences input;

	public static final String url = "/preferences/utenti";
	
	private static final String CODICE_LOG_AUDIT = "AUTH_LOG_162";
	private static final String CODICE_SERVIZIO = "POSTGETUTENTI";
	
	public PreferencesService(String shibIdentitaCodiceFiscale, String xRequestID,
            String xForwardedFor, String xCodiceServizio,
            SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request, FilterPreferences input) {
		super(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request);
		this.input = input;
	}
	
	@Override
	protected Response execute() {
		String METHOD_NAME = "execute";
		log.info(METHOD_NAME, "PreferencesService versione 1.0.0");
		String response = "error";
		try {
			if(input.getCodiceApplicazione()==null && input.getCodiceCollocazione()==null && input.getCodiceRuolo()==null && input.getCodiciFiscale()==null
					&& input.getCodiceAzienda()==null) {
				Response returnResponse = ErroreBuilder.from(Response.Status.BAD_REQUEST).response();
				return returnResponse;
			}
			if(input.getNomeFruitore()==null) {
				Response returnResponse = ErroreBuilder.from(Response.Status.BAD_REQUEST).response();
				return returnResponse;
			}
			List<UserPreference> listaPreferenze = new ArrayList<UserPreference>();
			List<PreferenzaDto> preferenze = preferenzaLowDao.findByFilter(this.input);
			UserPreference userPreference = null;
			for(PreferenzaDto preferenza: preferenze) {
				boolean sms = false;
				boolean email = false;
				boolean push = false;
				if(preferenza.getPreferenzaSalvata()!=null && preferenza.getPreferenzaSalvata().size()>0 &&
						preferenza.getPreferenzaSalvata().get(0).getPush() && preferenza.getUtente().getTokenPush()!=null) {
					push = true;
				}
				if(preferenza.getPreferenzaSalvata()!=null && preferenza.getPreferenzaSalvata().size()>0 &&
						preferenza.getPreferenzaSalvata().get(0).getSms() && preferenza.getUtente().getTelefonoPreferenze()!=null) {
					sms = true;
				}
				if(preferenza.getPreferenzaSalvata()!=null && preferenza.getPreferenzaSalvata().size()>0 &&
						preferenza.getPreferenzaSalvata().get(0).getEmail() && preferenza.getUtente().getEmailPreferenze()!=null) {
					email = true;
				}
				if(sms || email || push) {
					userPreference = new UserPreference();
					userPreference.setCodiceFiscale(preferenza.getUtente().getCodiceFiscale());
					if(preferenza.getPreferenzaSalvata()!=null && preferenza.getPreferenzaSalvata().size()>0 &&
							preferenza.getPreferenzaSalvata().get(0).getSms() && StringUtils.isNotEmpty(preferenza.getUtente().getTelefonoPreferenze())) {
						userPreference.setNumeroDiTelefono(preferenza.getUtente().getTelefonoPreferenze());
					}
					if(preferenza.getPreferenzaSalvata()!=null && preferenza.getPreferenzaSalvata().size()>0 &&
							preferenza.getPreferenzaSalvata().get(0).getEmail() && StringUtils.isNotEmpty(preferenza.getUtente().getEmailPreferenze())) {
						userPreference.setEmail(preferenza.getUtente().getEmailPreferenze());
					}
					if(preferenza.getPreferenzaSalvata()!=null && preferenza.getPreferenzaSalvata().size()>0 &&
							preferenza.getPreferenzaSalvata().get(0).getPush() && StringUtils.isNotEmpty(preferenza.getUtente().getTokenPush())) {
						userPreference.setPush(preferenza.getUtente().getTokenPush());
					}
					listaPreferenze.add(userPreference);
				}
			}
			ObjectMapper objectMapper = new ObjectMapper();
			response = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(listaPreferenze);
			return Response.ok(listaPreferenze).build();
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
					log.info(METHOD_NAME, "log Audit in PreferencesService inserito correttamente");
				} else {
					throw new Exception("Errore inserimento log Audit in PreferencesService");
				}
			} catch (Exception e) {
				log.error("PreferencesService", "log audit: ", e);
				throw ErroreBuilder.from(Response.Status.INTERNAL_SERVER_ERROR).exception();
			}
        }
	}

}
