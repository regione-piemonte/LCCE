/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.validation;

import it.csi.dma.dmaloginccebl.business.dao.ApplicazioneLowDao;
import it.csi.dma.dmaloginccebl.business.dao.ConfigurazioneLowDao;
import it.csi.dma.dmaloginccebl.business.dao.CredenzialiServiziLowDao;
import it.csi.dma.dmaloginccebl.business.dao.ServiziLowDao;
import it.csi.dma.dmaloginccebl.business.dao.dto.ApplicazioneDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.ConfigurazioneDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.LoginDataDto;
import it.csi.dma.dmaloginccebl.business.dao.util.CatalogoLog;
import it.csi.dma.dmaloginccebl.integration.LogGeneralDaoBean;
import it.csi.dma.dmaloginccebl.interfacews.msg.Errore;
import it.csi.dma.dmaloginccebl.interfacews.token2.GetTokenInformation2Request;
import it.csi.dma.dmaloginccebl.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TokenInformation2ServiceValidator extends BaseServiceValidator{

	@Autowired
	ServiziLowDao serviziLowDao;
	
	@Autowired
	CredenzialiServiziLowDao credenzialiServiziLowDao;
	
	@Autowired
    ApplicazioneLowDao applicazioneLowDao;
    
    @Autowired
    ConfigurazioneLowDao configurazioneLowDao;
    
    private final String TIME_TOK = "TIME_TOK2";
    
    public List<Errore> validateApplicazione(GetTokenInformation2Request getTokenInformation2Request, LogGeneralDaoBean logGeneralDaoBean, List<Errore> errori) {
    	
    	if(getTokenInformation2Request.getApplicazione().isEmpty()) {
	    	errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.APPLICAZIONE_OBBLIGATORIA.getValue()));
	    	
	    } else {
	    	ApplicazioneDto applicazioneDto = new ApplicazioneDto();
		    applicazioneDto = Utils.getFirstRecord(applicazioneLowDao.findByCodice(applicazioneDto, getTokenInformation2Request.getApplicazione()));
		    
		    if(applicazioneDto == null) {
		    	errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.APPLICAZIONE_ERRATA.getValue()));
		    }
	    }
    	return errori;
    }
    	
    
	public List<Errore> verifyCampiObbligatori(GetTokenInformation2Request getTokenInformation2Request, LogGeneralDaoBean logGeneralDaoBean, List<Errore> errori){

		//token
    	verificaCampoObbligatorio(logGeneralDaoBean, errori, getTokenInformation2Request.getToken(), CatalogoLog.TOKEN_OBBLIGATORIO);
    	//applicazione
    	verificaCampoObbligatorio(logGeneralDaoBean, errori, getTokenInformation2Request.getApplicazione(), CatalogoLog.APPLICAZIONE_OBBLIGATORIA);
    	//ipBrowser
    	verificaCampoObbligatorio(logGeneralDaoBean, errori, getTokenInformation2Request.getIpBrowser(), CatalogoLog.IPBROWSER_OBBLIGATORIO);
        
        return errori;
    }
	
	public List<Errore> verifyEsistenzaToken(LogGeneralDaoBean logGeneralDaoBean, List<Errore> errori, LoginDataDto loginDataDto) {
		
		if(loginDataDto == null) {
			errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.TOKEN_ERRATO_SCADUTO.getValue()));		
		}
		
		return errori;
	}

	public List<Errore> verifyUtilizzoToken(GetTokenInformation2Request getTokenInformation2Request,
			LogGeneralDaoBean logGeneralDaoBean, List<Errore> errori, LoginDataDto loginDataDto) {
		
		if(loginDataDto.getDataUtilizzoToken() != null) {	
			errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.TOKEN_ERRATO_SCADUTO.getValue()));
		}
		
		return errori;
	}

	public List<Errore> verifyIp(GetTokenInformation2Request getTokenInformation2Request,
			LogGeneralDaoBean logGeneralDaoBean, List<Errore> errori, LoginDataDto loginDataDto) {
		
		if(Utils.isNotEmpty(loginDataDto.getClientIp())){
			
			if(!getTokenInformation2Request.getIpBrowser().equals(loginDataDto.getClientIp())) {
				errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.IPADDRESS_ERRATO.getValue()));
			}
		}
		return errori;
	}

	public List<Errore> verificaApplicazione(GetTokenInformation2Request getTokenInformation2Request,
								 LogGeneralDaoBean logGeneralDaoBean, List<Errore> errori, LoginDataDto loginDataDto) {

		if(Utils.isNotEmpty(loginDataDto.getApplicazioneRichiesta())){

			if(!getTokenInformation2Request.getApplicazione().equals(loginDataDto.getApplicazioneRichiesta())) {
				errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.APPLICAZIONE_ERRATA.getValue()));
			}
		}
		return errori;
	}
	
	public List<Errore> verifyValiditaTemporaleToken(LogGeneralDaoBean logGeneralDaoBean,
			LoginDataDto loginDataDto, List<Errore> errori) throws Exception {
		
		long inizioValiditaInMills = loginDataDto.getDataRichiestaToken().getTime();
		long timeTok = 0;
		
		ConfigurazioneDto configurazioneDto = getConfigurazioneTimeToken();
		 
		if(configurazioneDto != null) {
			timeTok = Utils.toLong(configurazioneDto.getValore());
		}
		
		long intervalloValidita = (Utils.sysdate().getTime()-inizioValiditaInMills)/1000;
		
		long timeOut = intervalloValidita-timeTok;
		
		if(timeOut >0) {
			errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.TOKEN_ERRATO_SCADUTO.getValue()));
    	}
		
		return errori;
	}

	private ConfigurazioneDto getConfigurazioneTimeToken() throws Exception {
		ConfigurazioneDto configurazioneDto = new ConfigurazioneDto();
		
		configurazioneDto.setChiave(TIME_TOK);
		configurazioneDto = Utils.getFirstRecord(configurazioneLowDao.findByFilter(configurazioneDto));
		
	   return configurazioneDto;
	}

}
