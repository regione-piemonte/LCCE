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

import it.csi.dma.dmaloginccebl.business.dao.ApplicazioneLowDao;
import it.csi.dma.dmaloginccebl.business.dao.RuoloLowDao;
import it.csi.dma.dmaloginccebl.business.dao.RuoloUtenteLowDao;
import it.csi.dma.dmaloginccebl.business.dao.UtenteLowDao;
import it.csi.dma.dmaloginccebl.business.dao.dto.ApplicazioneDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.RuoloDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.RuoloUtenteDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.UtenteDto;
import it.csi.dma.dmaloginccebl.business.dao.util.CatalogoLog;
import it.csi.dma.dmaloginccebl.integration.LogGeneralDaoBean;
import it.csi.dma.dmaloginccebl.interfacews.collocazioni.GetCollocazioniRequest;
import it.csi.dma.dmaloginccebl.interfacews.collocazioni.ValidateCollocazioniResponse;
import it.csi.dma.dmaloginccebl.interfacews.msg.Errore;
import it.csi.dma.dmaloginccebl.util.Utils;

@Component
public class CollocazioniServiceValidator extends BaseServiceValidator {

	@Autowired
	private UtenteLowDao utenteLowDao;
	
	@Autowired
	private ApplicazioneLowDao applicazioneLowDao;
	
	@Autowired
	private RuoloLowDao ruoloLowDao;
	
	@Autowired
	private RuoloUtenteLowDao ruoloUtenteLowDao;
	
	public ValidateCollocazioniResponse validateCampi(GetCollocazioniRequest getCollocazioniRequest, WebServiceContext wsContext, List<Errore> errori, LogGeneralDaoBean logGeneralDaoBean) {
		ValidateCollocazioniResponse validateCollocazioniResponse=new ValidateCollocazioniResponse();
		validateCollocazioniResponse.setListaErrori(errori);
		if(getCollocazioniRequest.getRichiedente() == null) {
			validateCollocazioniResponse.getListaErrori().add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.RICHIEDENTE_OBBLIGATORIO.getValue()));
		} else {
			validateCollocazioniResponse=validateCodiceFiscaleRichiedente(getCollocazioniRequest, wsContext, errori, logGeneralDaoBean, validateCollocazioniResponse);
			validateCollocazioniResponse=validateApplicazioneChiamante(getCollocazioniRequest, wsContext, errori, logGeneralDaoBean, validateCollocazioniResponse);
			validateCollocazioniResponse=validateCodiceRuoloRichiedente(getCollocazioniRequest, wsContext, errori, logGeneralDaoBean, validateCollocazioniResponse);
			validateCollocazioniResponse=validateIpClient(getCollocazioniRequest, wsContext, errori, logGeneralDaoBean, validateCollocazioniResponse);
		}
		return validateCollocazioniResponse;
	}
	
	public ValidateCollocazioniResponse validateCodiceFiscaleRichiedente(GetCollocazioniRequest getRuoliUtenteRequest, WebServiceContext wsContext, List<Errore> errori, LogGeneralDaoBean logGeneralDaoBean, ValidateCollocazioniResponse validateCollocazioniResponse) {
		
	    //VERIFICA OBBLIGATORIETA' - Codice Fiscale
	    if(getRuoliUtenteRequest.getRichiedente().getCodiceFiscaleRichiedente() == null || getRuoliUtenteRequest.getRichiedente().getCodiceFiscaleRichiedente().isEmpty()) {
	    	validateCollocazioniResponse.getListaErrori().add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.CODICE_FISCALE_OBBLIGATORIO.getValue()));
	    } else {
	    	//Controllo se esiste un utente con il codice fiscale inserito
	    	UtenteDto utenteDto=Utils.getFirstRecord(utenteLowDao.findByCodiceFiscale(getRuoliUtenteRequest.getRichiedente().getCodiceFiscaleRichiedente()));
	    	if(utenteDto == null) {
	    		validateCollocazioniResponse.getListaErrori().add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.CODICE_FISCALE_ERRATO.getValue()));
	    	} else {
	    		validateCollocazioniResponse.setUtenteDto(utenteDto);
	    	}
	    }
		return validateCollocazioniResponse;
	}
	
	public ValidateCollocazioniResponse validateApplicazioneChiamante(GetCollocazioniRequest getRuoliUtenteRequest, WebServiceContext wsContext, List<Errore> errori, LogGeneralDaoBean logGeneralDaoBean, ValidateCollocazioniResponse validateCollocazioniResponse) {
		
		if(getRuoliUtenteRequest.getRichiedente().getApplicazioneChiamante() == null || getRuoliUtenteRequest.getRichiedente().getApplicazioneChiamante().isEmpty()) {
			validateCollocazioniResponse.getListaErrori().add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.APPLICAZIONE_CHIAMANTE_OBBLIGATORIA.getValue()));
		} else {
			ApplicazioneDto applicazioneDto=new ApplicazioneDto();
			applicazioneDto=Utils.getFirstRecord(applicazioneLowDao.findByCodice(applicazioneDto, getRuoliUtenteRequest.getRichiedente().getApplicazioneChiamante()));
			if(applicazioneDto == null) {
				validateCollocazioniResponse.getListaErrori().add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.APPLICAZIONE_CHIAMANTE_ERRATA.getValue()));
			} else {
				validateCollocazioniResponse.setApplicazioneDto(applicazioneDto);
			}
		}
		return validateCollocazioniResponse;
	}
	
	public ValidateCollocazioniResponse validateCodiceRuoloRichiedente(GetCollocazioniRequest getRuoliUtenteRequest, WebServiceContext wsContext, List<Errore> errori, LogGeneralDaoBean logGeneralDaoBean, ValidateCollocazioniResponse validateCollocazioniResponse) {
		
		if(getRuoliUtenteRequest.getRichiedente().getCodiceRuoloRichiedente() == null || getRuoliUtenteRequest.getRichiedente().getCodiceRuoloRichiedente().isEmpty()) {
			validateCollocazioniResponse.getListaErrori().add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.RUOLO_OBBLIGATORIO.getValue()));
		} else {
			RuoloDto ruoloDto=new RuoloDto();
			ruoloDto=Utils.getFirstRecord(ruoloLowDao.findByCodice(ruoloDto, getRuoliUtenteRequest.getRichiedente().getCodiceRuoloRichiedente()));
			if(ruoloDto == null) {
				validateCollocazioniResponse.getListaErrori().add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.RUOLO_ERRATO.getValue()));
			} else {
				validateCollocazioniResponse.setRuoloDto(ruoloDto);
			}
		}
		return validateCollocazioniResponse;
	}
	
	public ValidateCollocazioniResponse validateIpClient(GetCollocazioniRequest getRuoliUtenteRequest, WebServiceContext wsContext, List<Errore> errori, LogGeneralDaoBean logGeneralDaoBean, ValidateCollocazioniResponse validateCollocazioniResponse) {
        if (getRuoliUtenteRequest.getRichiedente().getIpChiamante() == null || getRuoliUtenteRequest.getRichiedente().getIpChiamante().isEmpty()) {
        	validateCollocazioniResponse.getListaErrori().add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.IPCLIENT_OBBLIGATORIO.getValue()));
        }
        return validateCollocazioniResponse;
    }
	
	public ValidateCollocazioniResponse validateParametriInterdipendenti(WebServiceContext wsContext, List<Errore> errori, LogGeneralDaoBean logGeneralDaoBean, ValidateCollocazioniResponse validateCollocazioniResponse) {
		
		RuoloUtenteDto ruoloUtenteDto=new RuoloUtenteDto();
		ruoloUtenteDto.setRuoloDto(validateCollocazioniResponse.getRuoloDto());
		ruoloUtenteDto.setUtenteDto(validateCollocazioniResponse.getUtenteDto());
		ruoloUtenteDto=Utils.getFirstRecord(ruoloUtenteLowDao.findByUtenteRuoloAndData(ruoloUtenteDto));
		if(ruoloUtenteDto == null) {
			validateCollocazioniResponse.getListaErrori().add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.INCONGRUENZA_CODICE_FISCALE_UTENTE.getValue()));
		} else {
			validateCollocazioniResponse.setRuoloUtenteDto(ruoloUtenteDto);
		}
		return validateCollocazioniResponse;
	}
}
