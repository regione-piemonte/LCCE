/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.ruoliUtente;

import it.csi.dma.dmaloginccebl.business.dao.RuoloLowDao;
import it.csi.dma.dmaloginccebl.business.dao.ServiziLowDao;
import it.csi.dma.dmaloginccebl.business.dao.dto.*;
import it.csi.dma.dmaloginccebl.business.dao.util.CatalogoLog;
import it.csi.dma.dmaloginccebl.business.dao.util.CatalogoLogAudit;
import it.csi.dma.dmaloginccebl.business.dao.util.Constants;
import it.csi.dma.dmaloginccebl.business.dao.util.Servizi;
import it.csi.dma.dmaloginccebl.integration.LogGeneralDaoBean;
import it.csi.dma.dmaloginccebl.integration.dao.LogGeneralDao;
import it.csi.dma.dmaloginccebl.interfacews.msg.Errore;
import it.csi.dma.dmaloginccebl.interfacews.msg.RisultatoCodice;
import it.csi.dma.dmaloginccebl.interfacews.validation.RuoliUtenteServiceValidator;
import it.csi.dma.dmaloginccebl.util.Credenziali;
import it.csi.dma.dmaloginccebl.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@WebService(serviceName="RuoliUtenteService", portName="RuoliUtenteService", targetNamespace = "http://dmacc.csi.it/", endpointInterface = "it.csi.dma.dmaloginccebl.interfacews.ruoliUtente.RuoliUtenteService")
@Service(value = "ruoliUtenteService")
public class RuoliUtenteServiceImpl implements RuoliUtenteService {
	private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(Constants.APPLICATION_CODE);
	
	@Resource
    private WebServiceContext wsContext;
	
	@Autowired
	private LogGeneralDao logGeneralDao;
	
	@Autowired
	private RuoliUtenteServiceValidator ruoliUtenteServiceValidator;

	@Autowired
	private RuoloLowDao ruoloLowDao;
	
	@Autowired
	private ServiziLowDao serviziLowDao;
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public GetRuoliUtenteResponse getRuoliUtente(GetRuoliUtenteRequest getRuoliUtenteRequest) {
		GetRuoliUtenteResponse response=null;
        LogGeneralDaoBean logGeneralDaoBean = null;
        AbilitazioneDto abilitazioneDto = null;
		List<Errore> errori = new ArrayList<>();
		ValidateRuoliUtenteResponse validateRuoliUtenteResponse=new ValidateRuoliUtenteResponse();
		
		try {
			//1:Log
			logGeneralDaoBean = this.prepareLogBeanRuoliUtente(getRuoliUtenteRequest, wsContext);
			logGeneralDao.logStart(logGeneralDaoBean, Servizi.RUOLI_UTENTE.getValue());
			
			//2: Verifica Credenziali
			errori=ruoliUtenteServiceValidator.validateCredenziali(Servizi.RUOLI_UTENTE, null, wsContext, errori, logGeneralDaoBean);
			
			if(checkErrori(errori)) {
				return response=new GetRuoliUtenteResponse(errori, RisultatoCodice.FALLIMENTO);
			}
			
			//3-4: Verifica Obbligatorieta e Correttezza Parametri
			validateRuoliUtenteResponse=ruoliUtenteServiceValidator.validateCampi(getRuoliUtenteRequest, wsContext, errori, logGeneralDaoBean);

			if(checkErrori(validateRuoliUtenteResponse.getListaErrori())) {
				return response=new GetRuoliUtenteResponse(errori, RisultatoCodice.FALLIMENTO);
			}
			
			//5: Reperimento Ruoli-Utente Change COnfiguratore - aggiunto controllo campo visibilitaConf (deve essere NULL o S)
			Collection<RuoloDto> ruoli=ruoloLowDao.findByUtenteCodiceFiscale(getRuoliUtenteRequest.getRichiedente().getCodiceFiscaleRichiedente());
			if(ruoli != null && !ruoli.isEmpty()) {
				response=new GetRuoliUtenteResponse(RisultatoCodice.SUCCESSO);
				response.setListaRuoli(new ArrayList<Ruolo>());
				for(RuoloDto ruolo: ruoli) {
					Ruolo ruoloResponse=new Ruolo();
					ruoloResponse.setCodice(ruolo.getCodice());
					ruoloResponse.setDescrizione(ruolo.getDescrizione());
					response.getListaRuoli().add(ruoloResponse);
				}
			} else {
				errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.LISTA_RUOLI_VUOTA.getValue()));
				return response=new GetRuoliUtenteResponse(errori, RisultatoCodice.FALLIMENTO);
			}
			logGeneralDao.logAudit(logGeneralDaoBean.getLogAuditDto(), validateRuoliUtenteResponse.getApplicazioneDto(), abilitazioneDto, validateRuoliUtenteResponse.getUtenteDto(), null, null, CatalogoLogAudit.LOG_SUCCESSO_RUOLI_UTENTE.getValue());
			
		} catch (Exception e) {
			log.error("getRuoliUtente", e);
			errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.ERRORE_INTERNO.getValue()));
            response = new GetRuoliUtenteResponse(errori, RisultatoCodice.FALLIMENTO);
		} finally {
			String xmlOut = Utils.xmlMessageFromObject(response);
			logGeneralDao.logEnd(logGeneralDaoBean, abilitazioneDto, response, null, xmlOut, "GetRuoliUtente", response.getEsito().getValue());
		}
		return response;
	}
	
	private boolean checkErrori(List<Errore> errori) {
        if (errori != null && !errori.isEmpty()) {
            return true;
        }
        return false;
    }
	
	private LogGeneralDaoBean prepareLogBeanRuoliUtente(GetRuoliUtenteRequest getRuoliUtenteRequest, WebServiceContext wsContext) {
		
		ServiziDto ruoliUtenteService = Utils.getFirstRecord(serviziLowDao.findByCodice(new ServiziDto(), Servizi.RUOLI_UTENTE.getValue()));

		//Creo MessaggiDto
		MessaggiDto messaggiDto=new MessaggiDto();
		
		//Set Certificato con username= ???
		Credenziali credenzialiFromHeader=ruoliUtenteServiceValidator.getCredentialsFromHeader(wsContext);
		if(credenzialiFromHeader != null) {
			messaggiDto.setCertificato(ruoliUtenteServiceValidator.getCredentialsFromHeader(wsContext).getUsername());
		}
		messaggiDto.setServiziDto(ruoliUtenteService);
		messaggiDto.setClientIp(getRuoliUtenteRequest.getRichiedente().getIpChiamante());
		messaggiDto.setCfRichiedente(getRuoliUtenteRequest.getRichiedente().getCodiceFiscaleRichiedente());
		messaggiDto.setApplicazione(getRuoliUtenteRequest.getRichiedente().getApplicazioneChiamante());
		messaggiDto.setDataRicezione(Utils.sysdate());
		
		//Creo LogDto
		LogDto logDto = new LogDto();
		logDto.setServiziDto(ruoliUtenteService);
		logDto.setMessaggiDto(messaggiDto);
		
		//Creo LogAuditDto
		LogAuditDto logAuditDto = new LogAuditDto();
		logAuditDto.setCfRichiedente(getRuoliUtenteRequest.getRichiedente().getCodiceFiscaleRichiedente());
		logAuditDto.setServiziDto(ruoliUtenteService);
		logAuditDto.setIpRichiedente(getRuoliUtenteRequest.getRichiedente().getIpChiamante());
		logAuditDto.setCodiceLog(CatalogoLogAudit.LOG_SUCCESSO_RUOLI_UTENTE.getValue());
		logDto.setMessaggiDto(messaggiDto);
		
		//Creo MessaggiXmlDto
		MessaggiXmlDto messaggiXmlDto = new MessaggiXmlDto();
		String xmlIn = Utils.xmlMessageFromObject(getRuoliUtenteRequest);
		messaggiXmlDto.setXmlIn(xmlIn != null ? xmlIn.toString().getBytes() : null);
		logDto.setMessaggiDto(messaggiDto);

		return new LogGeneralDaoBean(logDto, logAuditDto, messaggiDto, messaggiXmlDto, null);
	}
}
