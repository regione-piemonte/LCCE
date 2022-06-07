/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.validation;

import java.util.List;

import javax.xml.ws.WebServiceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.csi.dma.dmaloginccebl.business.dao.UtenteLowDao;
import it.csi.dma.dmaloginccebl.business.dao.dto.ApplicazioneDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.UtenteDto;
import it.csi.dma.dmaloginccebl.business.dao.impl.ApplicazioneLowDaoImpl;
import it.csi.dma.dmaloginccebl.business.dao.util.CatalogoLog;
import it.csi.dma.dmaloginccebl.integration.LogGeneralDaoBean;
import it.csi.dma.dmaloginccebl.interfacews.msg.Errore;
import it.csi.dma.dmaloginccebl.interfacews.ruoliUtente.GetRuoliUtenteRequest;
import it.csi.dma.dmaloginccebl.interfacews.ruoliUtente.ValidateRuoliUtenteResponse;
import it.csi.dma.dmaloginccebl.util.Utils;

@Component
public class RuoliUtenteServiceValidator extends BaseServiceValidator{
	
	@Autowired
	private UtenteLowDao utenteLowDao;
	
	@Autowired
	private ApplicazioneLowDaoImpl applicazioneLowDao;
	
	public ValidateRuoliUtenteResponse validateCampi(GetRuoliUtenteRequest getRuoliUtenteRequest, WebServiceContext wsContext, List<Errore> errori, LogGeneralDaoBean logGeneralDaoBean) {
		
		ValidateRuoliUtenteResponse validateRuoliUtenteResponse=new ValidateRuoliUtenteResponse();
		if(getRuoliUtenteRequest.getRichiedente() == null) {
			errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.RICHIEDENTE_OBBLIGATORIO.getValue()));
			validateRuoliUtenteResponse.setListaErrori(errori);
		} else {
			validateRuoliUtenteResponse=validateCodiceFiscaleRichiedente(getRuoliUtenteRequest, wsContext, errori, logGeneralDaoBean, validateRuoliUtenteResponse);
			validateRuoliUtenteResponse=validateApplicazioneChiamante(getRuoliUtenteRequest, wsContext, errori, logGeneralDaoBean, validateRuoliUtenteResponse);
			validateRuoliUtenteResponse=validateIpClient(getRuoliUtenteRequest, wsContext, errori, logGeneralDaoBean, validateRuoliUtenteResponse);
		}
		return validateRuoliUtenteResponse;
	}
	
	
	public ValidateRuoliUtenteResponse validateCodiceFiscaleRichiedente(GetRuoliUtenteRequest getRuoliUtenteRequest, WebServiceContext wsContext, List<Errore> errori, LogGeneralDaoBean logGeneralDaoBean, ValidateRuoliUtenteResponse validateRuoliUtenteResponse) {
		
	    //VERIFICA OBBLIGATORIETA' - Codice Fiscale
	    if(getRuoliUtenteRequest.getRichiedente().getCodiceFiscaleRichiedente() == null || getRuoliUtenteRequest.getRichiedente().getCodiceFiscaleRichiedente().isEmpty()) {
	    	errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.CODICE_FISCALE_OBBLIGATORIO.getValue()));
	    } else {
	    	//Controllo se esiste un utente con il codice fiscale inserito
	    	UtenteDto utenteDto=Utils.getFirstRecord(utenteLowDao.findByCodiceFiscale(getRuoliUtenteRequest.getRichiedente().getCodiceFiscaleRichiedente()));
	    	if(utenteDto == null) {
	    		errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.CODICE_FISCALE_ERRATO.getValue()));
	    	} else {
	    		validateRuoliUtenteResponse.setUtenteDto(utenteDto);
	    	}
	    }
	    validateRuoliUtenteResponse.setListaErrori(errori);
		return validateRuoliUtenteResponse;
	}
	
	public ValidateRuoliUtenteResponse validateApplicazioneChiamante(GetRuoliUtenteRequest getRuoliUtenteRequest, WebServiceContext wsContext, List<Errore> errori, LogGeneralDaoBean logGeneralDaoBean, ValidateRuoliUtenteResponse validateRuoliUtenteResponse) {
		
		if(getRuoliUtenteRequest.getRichiedente().getApplicazioneChiamante() == null || getRuoliUtenteRequest.getRichiedente().getApplicazioneChiamante().isEmpty()) {
			errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.APPLICAZIONE_CHIAMANTE_OBBLIGATORIA.getValue()));
		} else {
			ApplicazioneDto applicazioneDto=new ApplicazioneDto();
			applicazioneDto=Utils.getFirstRecord(applicazioneLowDao.findByCodice(applicazioneDto, getRuoliUtenteRequest.getRichiedente().getApplicazioneChiamante()));
			if(applicazioneDto == null) {
				errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.APPLICAZIONE_CHIAMANTE_ERRATA.getValue()));
			} else {
				validateRuoliUtenteResponse.setApplicazioneDto(applicazioneDto);
			}
		}
		validateRuoliUtenteResponse.setListaErrori(errori);
		return validateRuoliUtenteResponse;
	}
	
	public ValidateRuoliUtenteResponse validateIpClient(GetRuoliUtenteRequest getRuoliUtenteRequest, WebServiceContext wsContext, List<Errore> errori, LogGeneralDaoBean logGeneralDaoBean, ValidateRuoliUtenteResponse validateRuoliUtenteResponse) {
        if (getRuoliUtenteRequest.getRichiedente().getIpChiamante() == null || getRuoliUtenteRequest.getRichiedente().getIpChiamante().isEmpty()) {
        	errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.IPCLIENT_OBBLIGATORIO.getValue()));
        	validateRuoliUtenteResponse.setListaErrori(errori);
        }
        return validateRuoliUtenteResponse;
    }
}
