/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.token;

import it.csi.dma.dmaloginccebl.business.dao.*;
import it.csi.dma.dmaloginccebl.business.dao.dto.*;
import it.csi.dma.dmaloginccebl.business.dao.util.CatalogoLog;
import it.csi.dma.dmaloginccebl.business.dao.util.CatalogoLogAudit;
import it.csi.dma.dmaloginccebl.business.dao.util.Constants;
import it.csi.dma.dmaloginccebl.business.dao.util.Servizi;
import it.csi.dma.dmaloginccebl.integration.LogGeneralDaoBean;
import it.csi.dma.dmaloginccebl.integration.dao.LogAuditDao;
import it.csi.dma.dmaloginccebl.integration.dao.LogGeneralDao;
import it.csi.dma.dmaloginccebl.interfacews.msg.Errore;
import it.csi.dma.dmaloginccebl.interfacews.msg.ParametriLogin;
import it.csi.dma.dmaloginccebl.interfacews.msg.RisultatoCodice;
import it.csi.dma.dmaloginccebl.interfacews.token2.GetTokenInformation2Request;
import it.csi.dma.dmaloginccebl.interfacews.token2.GetTokenInformation2Response;
import it.csi.dma.dmaloginccebl.interfacews.token2.TokenInformation2DaoImpl;
import it.csi.dma.dmaloginccebl.interfacews.validation.TokenInformationServiceValidator;
import it.csi.dma.dmaloginccebl.util.Utils;
import org.apache.log4j.Logger;
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

/**
 */
@WebService(serviceName = "TokenInformationService", portName = "TokenInformationService", targetNamespace = "http://dmacc.csi.it/", endpointInterface = "it.csi.dma.dmaloginccebl.interfacews.token.TokenInformationService")
@Service(value = "tokenInformationService")
public class TokenInformationServiceImpl implements TokenInformationService {

    private final static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);
    
    private static final String ERRORE_ELABORAZIONE = "CC_ER_103";

	private static final Object GETTOKENINFORMATION = "GetTokenInformation";
    
    @Resource
    WebServiceContext wsContext;
    
    @Autowired
    LogGeneralDao logGeneralDao;

    @Autowired
    TokenInformationServiceValidator tokenInformationServiceValidator;
    
    @Autowired
    LoginDataLowDao loginDataLowDao;

    @Autowired
    LogAuditDao logAuditDao;
    
    @Autowired
    AbilitazioneLowDao abilitazioneLowDao;
    
    @Autowired
    ApplicazioneLowDao applicazioneLowDao;
    
    @Autowired
    UtenteLowDao utenteLowDao;
    
    @Autowired
    RuoloUtenteLowDao ruoloUtenteLowDao;
    
    @Autowired
    RuoloLowDao ruoloLowDao;
    
    @Autowired
    LoginParamLowDao loginParamLowDao;
    
    @Autowired
    private ServiziLowDao serviziLowDao;

    @Autowired
	private TokenInformation2DaoImpl tokenInformation2Dao;
    
    public String className = "TokenInformationServiceImpl";


    /**
     * Implementazione del metodo getTokenInformation del servizio
     */
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    @Override
    public GetTokenInformationResponse getTokenInformation(GetTokenInformationRequest getTokenInformationRequest) {

    	final String methodName = "getTokenInformation";
        List<Errore> errori = new ArrayList<>();
        GetTokenInformationResponse response = null;
        LogGeneralDaoBean logGeneralDaoBean = null;
        AbilitazioneDto abilitazioneDto = null;
        ApplicazioneDto applicazioneDto = null;
		
        try {
			// GENERAZIONE LOG
			logGeneralDaoBean = this.prepareLogBeanTokenInformation(getTokenInformationRequest);
			logGeneralDao.logStart(logGeneralDaoBean, Servizi.TOKEN.getValue());
			
        	// VERIFICA CREDENZIALI APPLICAZIONE ESTERNA
			errori=tokenInformationServiceValidator.validateApplicazione(getTokenInformationRequest, logGeneralDaoBean, errori);
			
			if (checkErrori(errori)) {
        		return response=new GetTokenInformationResponse(errori, RisultatoCodice.FALLIMENTO);
        	}
			
			// VERIFICA CREDENZIALI
        	errori = tokenInformationServiceValidator.validateCredenziali(Servizi.TOKEN, getTokenInformationRequest.getApplicazione(), wsContext, errori, logGeneralDaoBean);
        	
        	if (checkErrori(errori)) {
        		return response=new GetTokenInformationResponse(errori, RisultatoCodice.FALLIMENTO);
        	}
    	    
        	//3. VERIFICA PARAMETRI IN INPUT
        	errori = tokenInformationServiceValidator.verifyCampiObbligatori(getTokenInformationRequest, logGeneralDaoBean, errori);
        	if (checkErrori(errori)) {
        		return response=new GetTokenInformationResponse(errori, RisultatoCodice.FALLIMENTO);
        	}
        	
        	//4. VERIFICA ESISTENZA TOKEN IN ARCHIVIO
        	LoginDataDto loginDataDto = getLoginData(getTokenInformationRequest);
        	
        	errori = tokenInformationServiceValidator.verifyEsistenzaToken(getTokenInformationRequest, logGeneralDaoBean, errori, loginDataDto);
        	if (checkErrori(errori)) {
        		return response=new GetTokenInformationResponse(errori, RisultatoCodice.FALLIMENTO);
        	}
        		
    		//5. VERIFICA TOKEN NON UTILIZZATO
//        	errori = tokenInformationServiceValidator.verifyUtilizzoToken(getTokenInformationRequest, logGeneralDaoBean, errori, loginDataDto);
//        	if (checkErrori(errori)) {
//        		return response=new GetTokenInformationResponse(errori, RisultatoCodice.FALLIMENTO);
//        	}
    		
    		//6. VERIFICA CONGRUENZA IP IN INPUT CON IP IN ARCHIVIO (salvato da getAuthentication)
//        	errori = tokenInformationServiceValidator.verifyIp(getTokenInformationRequest, logGeneralDaoBean, errori, loginDataDto);
//        	if (checkErrori(errori)) {
//        		return response=new GetTokenInformationResponse(errori, RisultatoCodice.FALLIMENTO);
//        	}
    		
    		//7. VERIFICA APPLICAZIONE RICHIESTA
    		abilitazioneDto = getAbilitazione(loginDataDto, abilitazioneDto);
        	
        	if(abilitazioneDto == null) {
        		errori = tokenInformationServiceValidator.verifyApplicazione(getTokenInformationRequest, logGeneralDaoBean, applicazioneDto, errori);
        	}else {
				loginDataDto.setAbilitazioneDto(abilitazioneDto);
				if(abilitazioneDto.getApplicazioneDto() != null) {
					applicazioneDto = abilitazioneDto.getApplicazioneDto();
					errori = tokenInformationServiceValidator.verifyApplicazione(getTokenInformationRequest, logGeneralDaoBean, applicazioneDto, errori);
				}
        	}
        	if (checkErrori(errori)) {
        		return response=new GetTokenInformationResponse(errori, RisultatoCodice.FALLIMENTO);
        	}
    		
    		//8. VERIFICA VALIDIT� TEMPORALE DEL TOKEN
    		errori = tokenInformationServiceValidator.verifyValiditaTemporaleToken(logGeneralDaoBean, loginDataDto, errori);
        	if (checkErrori(errori)) {
        		return response=new GetTokenInformationResponse(errori, RisultatoCodice.FALLIMENTO);
        	}
    		        		
    		
    		//10. Aggiornamento del Log
    		/*
    		 * Il servizio registra nelle tabelle di log la risposta al chiamante tracciando il relativo xml.in lcce.auth_l_messaggi e 
    		 * traccia nella tabella di log l�eventuale errore intercettato.
    		 * Se il servizio intercetta un qualunque errore interno non riconducibile a quelli codificati, 
    		 * deve essere tracciato nel log e restituito in output�l�errore AUTH_ER_000: 
    		 * Errore interno del servizio non riconducibile ad altri errori codificati 
    		 * (l�errore � codificato in lcce.auth_d_catalogo_log).*/
    		UtenteDto utenteDto = getUtente(loginDataDto);
    		RuoloDto ruoloDto = getRuolo(abilitazioneDto);
    		Richiedente richiedente = setRichiedente(utenteDto, ruoloDto, abilitazioneDto, loginDataDto);    		
    		
    		
    		
    		logGeneralDaoBean.getMessaggiDto().setCfRichiedente(richiedente.getCodiceFiscale());
    		logGeneralDaoBean.getMessaggiDto().setRuoloRichiedente(richiedente.getRuolo());
    		/*
    		 * informazioni_tracciate = auth_d_catalogo_log_audit.**Descrizione** dell�azione relativa alla verifica e utilizzo del token -> descrizione o descrizioneCodice?
    		 * id_catalogo_log_audit = identificativo dell�azione relativa alla alla verifica e utilizzo del token censita in auth_d_catalogo_log_audit.
    		 */
    		LogAuditDto logAuditDto = setLogAudit(logGeneralDaoBean, loginDataDto);
    		
    		logGeneralDao.logAudit(logAuditDto, applicazioneDto, abilitazioneDto, utenteDto, ruoloDto, 
    				getTokenInformationRequest.getToken(), CatalogoLogAudit.LOG_SUCCESSO_TOKEN.getValue());
    		
    		//setto il cf assistito nel log
    		logGeneralDaoBean.getLogDto().setCfAssistito(loginDataDto.getCfAssistito());
    		logGeneralDaoBean.getMessaggiDto().setCfAssistito(loginDataDto.getCfAssistito());
    		
    		//11. Componi Response    		
    		response = new GetTokenInformationResponse(null, RisultatoCodice.SUCCESSO);

    		setResponse(response, loginDataDto, richiedente, abilitazioneDto);
    		
    		//9. CONFIGURAZIONE VALENZA TEMPORALE NEL DB
    		loginDataDto = updateLoginData(loginDataDto);

        } catch (Exception e) {
        	errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.ERRORE_INTERNO.getValue()));
            response = new GetTokenInformationResponse(errori, RisultatoCodice.FALLIMENTO);
        } finally {
            logGeneralDao.logEnd(logGeneralDaoBean, abilitazioneDto, response, getTokenInformationRequest.getToken(), null, GETTOKENINFORMATION, response.getEsito().getValue());
        }
        return response;
    }

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public GetTokenInformation2Response getTokenInformation2(GetTokenInformation2Request parameters) {
		return tokenInformation2Dao.getTokenInformation2(parameters, wsContext);
	}

	private boolean checkErrori(List<Errore> errori) {
        if (errori != null && !errori.isEmpty()) {
            return true;
        }
        return false;
    }
    
	private LoginDataDto getLoginData(GetTokenInformationRequest getTokenInformationRequest) throws Exception {
		
		LoginDataDto loginDataDto = new LoginDataDto();
		loginDataDto.setToken(getTokenInformationRequest.getToken());
		//devo inserire come filtro che il servizio che ha generato il token sia getAuthentication
		ServiziDto getAuth2 = new ServiziDto();
		getAuth2 = Utils.getFirstRecord(serviziLowDao.findByCodice(new ServiziDto(), Servizi.AUTHENTICATION.toString()));
		loginDataDto.setServiziDto(getAuth2);
    	LoginDataDto loginDataDtos = Utils.getFirstRecord(loginDataLowDao.findByFilter(loginDataDto));

		return loginDataDtos;
	}

	private LoginDataDto updateLoginData(LoginDataDto loginDataDto) {
		
		loginDataDto.setDataUtilizzoToken(Utils.sysdate());
		loginDataLowDao.update(loginDataDto);
		
		return loginDataDto;
	}
	
	private AbilitazioneDto getAbilitazione(LoginDataDto loginDataDto, AbilitazioneDto abilitazioneDto) {
		
		if(loginDataDto.getAbilitazioneDto() != null) {
			abilitazioneDto = abilitazioneLowDao.findByPrimaryId(loginDataDto.getAbilitazioneDto().getId());
		}		
		return abilitazioneDto;
	}

	private LogAuditDto setLogAudit(LogGeneralDaoBean logGeneralDaoBean, LoginDataDto loginDataDto) {
		
		LogAuditDto logAuditDto = logGeneralDaoBean.getLogAuditDto();
		logAuditDto.setCfAssistito(loginDataDto.getCfAssistito());
		logAuditDto.setMessaggiDto(logGeneralDaoBean.getMessaggiDto());
		logAuditDto.setCfRichiedente(loginDataDto.getCfRichiedente());
				
		return logAuditDto;
	}
	
	private void setResponse(GetTokenInformationResponse response, LoginDataDto loginDataDto, 
			Richiedente richiedente, AbilitazioneDto abilitazioneDto) throws Exception {
		
		response.setRichiedente(richiedente);
        response.setCodiceFiscaleAssistito(loginDataDto.getCfAssistito());
        
        Collection<LoginParamDto> loginParams = getLoginParams(loginDataDto);
        for(LoginParamDto loginParamDto : loginParams) {
        	ParametriLogin parametriLogin = new ParametriLogin();
        	parametriLogin.setCodice(loginParamDto.getCodice());
        	parametriLogin.setValore(loginParamDto.getValore());
        	response.setParametriLogin(parametriLogin);
        }
	}

	private Collection<LoginParamDto> getLoginParams(LoginDataDto loginDataDto) throws Exception {
		
		LoginParamDto loginParam = new LoginParamDto();
		LoginDataDto data = new LoginDataDto();
		data.setId(loginDataDto.getId());
		loginParam.setLoginDataDto(data);
//        loginParam.setLoginDataDto(loginDataDto);
        
        Collection<LoginParamDto> loginParams = loginParamLowDao.findByFilter(loginParam);
		return loginParams;
	}
	
	private Richiedente setRichiedente(UtenteDto utenteDto, RuoloDto ruoloDto, AbilitazioneDto abilitazioneDto, LoginDataDto loginDataDto) {
		
		Richiedente richiedente = new Richiedente();
		if(utenteDto != null) {
			richiedente.setNome(utenteDto.getNome());
	        richiedente.setCognome(utenteDto.getCognome());
		}
		richiedente.setCodiceFiscale(loginDataDto.getCfRichiedente());
		if(ruoloDto != null) {
			richiedente.setRuolo(ruoloDto.getCodice());
		}
		richiedente.setCodiceAbilitazione(abilitazioneDto.getCodiceAbilitazione());
		
		return richiedente;
	}

	private UtenteDto getUtente(LoginDataDto loginDataDto) throws Exception {
		UtenteDto utenteDto = new UtenteDto();
	    utenteDto.setCodiceFiscale(loginDataDto.getCfRichiedente());
	    utenteDto = Utils.getFirstRecord(utenteLowDao.findByFilter(utenteDto));
	    return utenteDto;
	}

	private RuoloDto getRuolo(AbilitazioneDto abilitazioneDto) {
		
		RuoloUtenteDto ruoloUtenteDto = ruoloUtenteLowDao.findByPrimaryId(abilitazioneDto.getRuoloUtenteDto().getId());
		RuoloDto ruoloDto = ruoloLowDao.findByPrimaryId(ruoloUtenteDto.getRuoloDto().getId());
		
		return ruoloDto;
	}
	
	private LogGeneralDaoBean prepareLogBeanTokenInformation(GetTokenInformationRequest getTokenInformationRequest) {
		
		ServiziDto tokenInformationService = Utils.getFirstRecord(serviziLowDao.findByCodice(new ServiziDto(), Servizi.TOKEN.getValue()));

		//Creo MessaggiDto
		MessaggiDto messaggiDto = createMessaggiDtoForTokenInformation(getTokenInformationRequest, tokenInformationService);
		
		//Creo LogDto
		LogDto logDto = createLogDtoForTokenInformation(getTokenInformationRequest, tokenInformationService);

		//Creo MessaggiXmlDto
		MessaggiXmlDto messaggiXmlDto = createMessaggiXmlDtoForTokenInformation(getTokenInformationRequest); 
		
		//Creo LogAuditDto
		LogAuditDto logAuditDto = createLogAuditDtoForTokenInformation(getTokenInformationRequest, tokenInformationService);
		
		return new LogGeneralDaoBean(logDto, logAuditDto, messaggiDto, messaggiXmlDto, null);
		
	}

	private MessaggiDto createMessaggiDtoForTokenInformation(GetTokenInformationRequest getTokenInformationRequest,
			ServiziDto tokenInformationService) {
		
		MessaggiDto messaggiDto = new MessaggiDto();
		messaggiDto.setServiziDto(tokenInformationService);
		messaggiDto.setClientIp(getTokenInformationRequest.getIpBrowser());
		messaggiDto.setApplicazione(getTokenInformationRequest.getApplicazione());
		messaggiDto.setToken(getTokenInformationRequest.getToken());
		messaggiDto.setDataRicezione(Utils.sysdate());
		
		return messaggiDto;
	}
	
	private LogDto createLogDtoForTokenInformation(GetTokenInformationRequest getTokenInformationRequest, 
			ServiziDto tokenInformationService) {
		
		LogDto logDto = new LogDto();
		logDto.setServiziDto(tokenInformationService);
		
		return logDto;
	}

	private MessaggiXmlDto createMessaggiXmlDtoForTokenInformation(GetTokenInformationRequest getTokenInformationRequest) {
		
		MessaggiXmlDto messaggiXmlDto = new MessaggiXmlDto();
		messaggiXmlDto.setId(Utils.getLXmlMessaggiIdFromInterceptor(wsContext));
		
		return messaggiXmlDto;
	}
	
	private LogAuditDto createLogAuditDtoForTokenInformation(GetTokenInformationRequest getTokenInformationRequest,
			ServiziDto tokenInformationService) {
		
		LogAuditDto logAuditDto = new LogAuditDto();
		logAuditDto.setToken(getTokenInformationRequest.getToken());
		logAuditDto.setIpRichiedente(getTokenInformationRequest.getIpBrowser());
		logAuditDto.setServiziDto(tokenInformationService);
		
		return logAuditDto;
	}
}
