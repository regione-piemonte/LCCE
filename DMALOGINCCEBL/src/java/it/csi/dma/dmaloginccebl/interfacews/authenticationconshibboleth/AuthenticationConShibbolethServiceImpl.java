/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.authenticationconshibboleth;

import it.csi.dma.dmaloginccebl.business.dao.LoginDataLowDao;
import it.csi.dma.dmaloginccebl.business.dao.LoginParamLowDao;
import it.csi.dma.dmaloginccebl.business.dao.ServiziLowDao;
import it.csi.dma.dmaloginccebl.business.dao.ServiziRichiamatiXmlLowDao;
import it.csi.dma.dmaloginccebl.business.dao.dto.*;
import it.csi.dma.dmaloginccebl.business.dao.util.CatalogoLog;
import it.csi.dma.dmaloginccebl.business.dao.util.CatalogoLogAudit;
import it.csi.dma.dmaloginccebl.business.dao.util.Constants;
import it.csi.dma.dmaloginccebl.business.dao.util.Servizi;
import it.csi.dma.dmaloginccebl.client.verifyLoginConditions.VerifyLoginConditionsClient;
import it.csi.dma.dmaloginccebl.client.verifyLoginConditions.VerifyLoginConditionsRequest;
import it.csi.dma.dmaloginccebl.client.verifyLoginConditions.VerifyLoginConditionsResponse;
import it.csi.dma.dmaloginccebl.integration.LogGeneralDaoBean;
import it.csi.dma.dmaloginccebl.integration.dao.LogGeneralDao;
import it.csi.dma.dmaloginccebl.interfacews.msg.Errore;
import it.csi.dma.dmaloginccebl.interfacews.msg.ParametriLogin;
import it.csi.dma.dmaloginccebl.interfacews.msg.RisultatoCodice;
import it.csi.dma.dmaloginccebl.interfacews.token.GetTokenInformationResponse;
import it.csi.dma.dmaloginccebl.interfacews.validation.AuthenticationServiceValidator;
import it.csi.dma.dmaloginccebl.interfacews.validation.TokenInformationServiceValidator;
import it.csi.dma.dmaloginccebl.iride.data.Identita;
import it.csi.dma.dmaloginccebl.iride.service.AuthService;
import it.csi.dma.dmaloginccebl.util.Utils;
import org.apache.cxf.security.SecurityContext;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 */

@WebService(serviceName = "AuthenticationConShibbolethService", portName = "AuthenticationConShibbolethService", targetNamespace = "http://dmacc.csi.it/", endpointInterface = "it.csi.dma.dmaloginccebl.interfacews.authenticationconshibboleth.AuthenticationConShibbolethService")
@Service(value = "authenticationConShibbolethService")
public class AuthenticationConShibbolethServiceImpl implements AuthenticationConShibbolethService {

    private final static Logger log = Logger .getLogger(Constants.APPLICATION_CODE);

	private static final Object GETAUTHENTICATIONCONSHIBB = "getAuthenticationConShibboleth";
    
    @Autowired
    private TokenInformationServiceValidator tokenInformationServiceValidator;

    @Resource
    private WebServiceContext wsContext;
    
    @Autowired
    private LogGeneralDao logGeneralDao;

    @Autowired
    private AuthenticationServiceValidator authenticationServiceValidator;

    @Autowired
    private LoginDataLowDao loginDataLowDao;

    @Autowired
    private LoginParamLowDao loginParamLowDao;

    @Autowired
    private VerifyLoginConditionsClient verifyLoginConditionsClient;

    @Autowired
    private ServiziRichiamatiXmlLowDao serviziRichiamatiXmlLowDao;
    
    @Autowired
    private ServiziLowDao serviziLowDao;
    
    public String className = "AuthenticationServiceImpl";



    private List<Errore> cleanRiferimentoExternalError(List<Errore> erroriServizio){
    	List<Errore> errori = new ArrayList<Errore>();
    	
    	if(erroriServizio!=null) {
    		for(it.csi.dma.dmaloginccebl.interfacews.msg.Errore err : erroriServizio){
                Errore errore = new Errore();     
                errore.setCodice(err.getCodice());
                errore.setDescrizione(err.getDescrizione());
                errore.setRiferimento(null);
                errori.add(errore);
            }
    	}else {
    		return null;
    	}
    	
    	
    	
    	return errori;
    	
    }

    private String getParametriLogStart(GetAuthenticationConShibbolethRequest parameters) {
        String parametriLogin = null;
        if(parameters.getParametriLogin() != null && !parameters.getParametriLogin().isEmpty()){
            for(ParametriLogin parametro : parameters.getParametriLogin()){
                parametriLogin = authenticationServiceValidator.appendParametriErrore(parametriLogin, parametro);
            }
        }
        return parametriLogin;
    }

    private void getErrori(List<Errore> errori, VerifyLoginConditionsResponse verifyLoginConditionsResponse) {
        for(it.csi.dma.dmaloginccebl.client.verifyLoginConditions.common.Errore erroreVerify : verifyLoginConditionsResponse.getErrori()){
            Errore errore = new Errore();
            BeanUtils.copyProperties(erroreVerify, errore);
            errore.setRiferimento(Constants.ERRORE_SERIVIZIO_ESTERNO);
            errori.add(errore);
        }
    }

    private VerifyLoginConditionsRequest getVerifyLoginConditionsRequest(GetAuthenticationConShibbolethRequest parameters, Richiedente richiedente, UtenteDto utenteDto) {
        VerifyLoginConditionsRequest verifyLoginConditionsRequest = new VerifyLoginConditionsRequest();
        verifyLoginConditionsRequest.setRuoloUtente(richiedente.getRuolo());
        verifyLoginConditionsRequest.setApplicazioneRichiesta(richiedente.getApplicazione());
        verifyLoginConditionsRequest.setCodiceFiscaleAssistito(parameters.getCodiceFiscaleAssistito());
        verifyLoginConditionsRequest.setCodiceFiscaleUtente(utenteDto.getCodiceFiscale());
        List<it.csi.dma.dmaloginccebl.client.verifyLoginConditions.common.ParametriLogin> listParametriVerify = new ArrayList<>();
        if(parameters.getParametriLogin() != null && !parameters.getParametriLogin().isEmpty()){
            setListaParametriLogin(parameters, verifyLoginConditionsRequest, listParametriVerify);
        }
        return verifyLoginConditionsRequest;
    }

    private void setListaParametriLogin(GetAuthenticationConShibbolethRequest parameters, VerifyLoginConditionsRequest verifyLoginConditionsRequest, List<it.csi.dma.dmaloginccebl.client.verifyLoginConditions.common.ParametriLogin> listParametriVerify) {
        for(ParametriLogin parametro : parameters.getParametriLogin()){
            it.csi.dma.dmaloginccebl.client.verifyLoginConditions.common.ParametriLogin parametroVerify = new it.csi.dma.dmaloginccebl.client.verifyLoginConditions.common.ParametriLogin();
            BeanUtils.copyProperties(parametro, parametroVerify);
            listParametriVerify.add(parametroVerify);
        }
        verifyLoginConditionsRequest.setParametriLogin(listParametriVerify);
    }

    private void registraParametriLogin(ParametriLogin parametriLogin, LoginDataDto loginDataDto){
        LoginParamDto loginParamDto = getLoginParamDto(parametriLogin, loginDataDto);

        loginParamLowDao.insert(loginParamDto);
    }

    private LoginParamDto getLoginParamDto(ParametriLogin parametriLogin, LoginDataDto loginDataDto) {
        LoginParamDto loginParamDto = new LoginParamDto();
        loginParamDto.setCodice(parametriLogin.getCodice());
        loginParamDto.setValore(parametriLogin.getValore());
        loginParamDto.setLoginDataDto(loginDataDto);
        return loginParamDto;
    }

    private LoginDataDto registraLoginData(GetAuthenticationConShibbolethRequest parameters, AbilitazioneDto abilitazioneDto, String tokenAutenticazione) {
        LoginDataDto loginDataDto = getLoginDataDto(parameters,  abilitazioneDto, tokenAutenticazione);

        return loginDataLowDao.insert(loginDataDto);
    }

    private LoginDataDto getLoginDataDto(GetAuthenticationConShibbolethRequest parameters, AbilitazioneDto abilitazioneDto, String tokenAutenticazione) {
        LoginDataDto loginDataDto = new LoginDataDto();
        loginDataDto.setAbilitazioneDto(abilitazioneDto);
        loginDataDto.setCfRichiedente(parameters.getRichiedente().getCodiceFiscaleMedico());
        loginDataDto.setCfAssistito(parameters.getCodiceFiscaleAssistito());
        loginDataDto.setClientIp(parameters.getRichiedente().getIpClient());
        loginDataDto.setRemoteIp(parameters.getRichiedente().getIpClient()); //TODO Controllare se corretto
        loginDataDto.setToken(tokenAutenticazione);
        loginDataDto.setDataRichiestaToken(Utils.sysdate());
        ServiziDto authenticationService = Utils.getFirstRecord(serviziLowDao.findByCodice(new ServiziDto(), Servizi.AUTHENTICATION.getValue()));
        loginDataDto.setServiziDto(authenticationService);
        return loginDataDto;
    }

    private boolean checkErrori(List<Errore> errori) {
        if (errori != null && !errori.isEmpty()) {
            return true;
        }
        return false;
    }
    
	private LogGeneralDaoBean prepareLogBeanAuthentication(GetAuthenticationConShibbolethRequest getAuthenticationRequest) {
		ServiziDto authenticationService = Utils.getFirstRecord(serviziLowDao.findByCodice(new ServiziDto(), Servizi.AUTHENTICATION.getValue()));
		Richiedente richiedente = getAuthenticationRequest.getRichiedente();
		//Creo LogDto
		LogDto logDto = createLogDto(getAuthenticationRequest, authenticationService);
		
		//Creo LogAuditDto
		LogAuditDto logAuditDto = createLogAuditDto(getAuthenticationRequest, authenticationService, richiedente);
		
		//Creo MessaggiDto
		MessaggiDto messaggiDto = createMessaggiDto(getAuthenticationRequest, authenticationService, richiedente);
		
		//Creo MessaggiXmlDto
		MessaggiXmlDto messaggiXmlDto = createMessaggiXmlDto(wsContext); 
		
		return new LogGeneralDaoBean(logDto, logAuditDto, messaggiDto, messaggiXmlDto, null);
	}
	
	private LogAuditDto createLogAuditDto(GetAuthenticationConShibbolethRequest getAuthenticationRequest,
			ServiziDto authenticationService, Richiedente richiedente) {
		LogAuditDto logAuditDto = new LogAuditDto();
		logAuditDto.setCfAssistito(getAuthenticationRequest.getCodiceFiscaleAssistito());
		if(richiedente != null) {
			logAuditDto.setIpRichiedente(richiedente.getIpClient());
		}
		logAuditDto.setServiziDto(authenticationService);
		return logAuditDto;
	}

	private LogDto createLogDto(GetAuthenticationConShibbolethRequest getAuthenticationRequest, ServiziDto authenticationService) {
		LogDto logDto = new LogDto();
		logDto.setCfAssistito(getAuthenticationRequest.getCodiceFiscaleAssistito());
		logDto.setServiziDto(authenticationService);
		return logDto;
	}
	
	private MessaggiDto createMessaggiDto(GetAuthenticationConShibbolethRequest getAuthenticationRequest,
			ServiziDto authenticationService, Richiedente richiedente) {
		MessaggiDto messaggiDto = new MessaggiDto();
		messaggiDto.setServiziDto(authenticationService);
		if(richiedente != null) {
			messaggiDto.setClientIp(richiedente.getIpClient());
			messaggiDto.setRuoloRichiedente(richiedente.getRuolo());
			messaggiDto.setApplicazione(richiedente.getApplicazione());
		}
		messaggiDto.setCfAssistito(getAuthenticationRequest.getCodiceFiscaleAssistito());
		messaggiDto.setDataRicezione(Utils.sysdate());
		messaggiDto.setCertificato("n/a");
		return messaggiDto;
	}
	
	private MessaggiXmlDto createMessaggiXmlDto(WebServiceContext wsContext) {
		MessaggiXmlDto messaggiXmlDto = new MessaggiXmlDto();
		messaggiXmlDto.setId(Utils.getLXmlMessaggiIdFromInterceptor(wsContext));
		return messaggiXmlDto;
	}

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public GetAuthenticationConShibbolethResponse getAuthenticationConShibboleth(
			GetAuthenticationConShibbolethRequest parameters) {
        final Richiedente richiedente = parameters.getRichiedente();

        List<Errore> errori = new ArrayList<>();
        GetAuthenticationConShibbolethResponse response = null;
        LogGeneralDaoBean logGeneralDaoBean = null;
        AbilitazioneDto abilitazioneDto = null;
        String tokenAutenticazione = null;

        try {


			// VERIFICA CREDENZIALI
        	errori = tokenInformationServiceValidator.validateCredenziali(Servizi.AUTHENTICATION, parameters.getRichiedente().getApplicazione(), wsContext, errori, logGeneralDaoBean);
        	
        	if (checkErrori(errori)) {
        		return response=new GetAuthenticationConShibbolethResponse(errori, RisultatoCodice.FALLIMENTO);
        	}
        	/* LOG START */



            logGeneralDaoBean = this.prepareLogBeanAuthentication(parameters);
            String parametriLogin = getParametriLogStart(parameters);
            logGeneralDao.logStart(logGeneralDaoBean, parametriLogin);

        	/* Verifica base parametri input */

            errori = authenticationServiceValidator.validate(parameters, logGeneralDaoBean, errori);
            if (checkErrori(errori)) return response = new GetAuthenticationConShibbolethResponse(errori, RisultatoCodice.FALLIMENTO);

            /* Verifica ruolo */

            RuoloDto ruoloDto = authenticationServiceValidator.verificaRuolo(logGeneralDaoBean, errori, richiedente.getRuolo());
            if (checkErrori(errori)) return response = new GetAuthenticationConShibbolethResponse(errori, RisultatoCodice.FALLIMENTO);

            /* Verifica applicazione richiesta */

                //il valore del parametro applicazione passato in input coincida con il
                //codice_applicazione di una delle applicazioni censite in lcce.auth_d_applicazione
                //Errore AUTH_ER_506
            ApplicazioneDto applicazioneDto = authenticationServiceValidator.verificaApplicazione(logGeneralDaoBean, errori, richiedente);
            if (checkErrori(errori)) return response = new GetAuthenticationConShibbolethResponse(errori, RisultatoCodice.FALLIMENTO);

            /* Verifica parametri aggiuntivi */

                //verifica parametri presenti in lista con quelli associati
                //all’applicazione passata in input in lcce.auth_d_catalogo_parametri
                //Errore AUTH_ER_517
            authenticationServiceValidator.verificaParametriLogin(logGeneralDaoBean,errori,
                    parameters.getParametriLogin(), applicazioneDto);
            if (checkErrori(errori)) return response = new GetAuthenticationConShibbolethResponse(errori, RisultatoCodice.FALLIMENTO);


            //sb jira LCCE-7
            logGeneralDaoBean.getMessaggiDto().setCfRichiedente(richiedente.getCodiceFiscaleMedico());

            /* Verifica credenziali IRIDE in archivio */

                //verifica codice fiscale restituito da IRIDE sia
                //presente nell’anagrafica utente di Login CCE (lcce.auth_t_utente).
                //Errore se non e' presente AUTH_ER_518
            UtenteDto utenteDto = authenticationServiceValidator.verificaCodiceFiscaleMedico(logGeneralDaoBean, errori, richiedente);
            if (checkErrori(errori)) return response = new GetAuthenticationConShibbolethResponse(errori, RisultatoCodice.FALLIMENTO);

            /* Verifica esistenza di abilitazione valida */

                //La verifica viene effettuata ricercando la coppia ruolo-utente passati in input
                //in lcce.auth_r_ruolo_utente, la coppia ruoloUtente-applicazione in lcce.auth_r_abilitazione
                //e verificando che la data corrente ricada nel
                //range idemtificato da Data_inizio_validità e Data_fine_validità
                //Errore AUTH_ER_506 se non trovata
            abilitazioneDto = authenticationServiceValidator.verificaAbilitazione(logGeneralDaoBean, errori, ruoloDto, utenteDto, applicazioneDto);
            if (checkErrori(errori)) return response = new GetAuthenticationConShibbolethResponse(errori,
                    RisultatoCodice.FALLIMENTO);

            /* Verifica necessita' VerifyLoginConditions */

                //ricerca in lcce.auth_d_applicazione l’applicazione
                //richiesta e verifica se l’attributo urlverifyloginconditions è popolato

                //Se non popolato si passa diretti alla generazione del token
                //Se popolato si fa la chiamata
            if(applicazioneDto.getUrlVerifyLoginConditions() != null){

                /* Chiamata VerifyLoginConditions */

                //Il servizio viene chiamato utilizzando le credenziali presenti sul DB (tabella auth_t_credenziali_servizi)
                CredenzialiServiziDto credenzialiServiziDto = authenticationServiceValidator.verificaCredenzialiServizio(logGeneralDaoBean, errori, Servizi.VERIFYLOGIN.getValue());
                // INPUT:
                //	ruoloUtente = ruolo dell’urtente ricevuto in input da CCE
                //	applicazioneRichiesta = applicazione ricevuto in input da CCE
                //	codiceFiscaleAssistito = codice Fiscale dell’Assitito ricevuto in input da CCE
                //	Lista dei parametri aggiuntivi ricevuti in input da CCE (se ricevuti)
                VerifyLoginConditionsRequest verifyLoginConditionsRequest = getVerifyLoginConditionsRequest(parameters, richiedente, utenteDto);
                VerifyLoginConditionsResponse verifyLoginConditionsResponse = null;
                
                ServiziRichiamatiXmlDto serviziRichiamatiXmlDto = logGeneralDao.getServiziRichiamatiXmlDto(logGeneralDaoBean,null,null,credenzialiServiziDto.getServiziDto(),null);
                try{
                    verifyLoginConditionsResponse =
                            verifyLoginConditionsClient.verifyLoginConditions(verifyLoginConditionsRequest, applicazioneDto, serviziRichiamatiXmlDto);
                }catch (Exception e){
                    e.printStackTrace();
                    log.error("Errore chiamata verifyLoginConditions", e);
                    errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(),
                            CatalogoLog.ERRORE_INTERNO.getValue()));
                    return new GetAuthenticationConShibbolethResponse(errori, RisultatoCodice.FALLIMENTO);
                }finally {
                    //Bisogna salvare XML_IN e XML_OUT della chiamata nella tabella lcce.auth_l_xml_servizi_richiamati
                	serviziRichiamatiXmlDto.setEsito((verifyLoginConditionsResponse != null && verifyLoginConditionsResponse.getEsito() != null) ? verifyLoginConditionsResponse.getEsito().getValue() : null);
                	logGeneralDao.registraXmlServiziRichiamati(serviziRichiamatiXmlDto);
                	}

                //Se esito negativo si prendono direttamente gli errori tornati dal servizio si scrivono nella messaggi errore
                //e si ritornano in response
                //Se esito positivo si passa allo step successivo
                if(verifyLoginConditionsResponse.getEsito() == RisultatoCodice.FALLIMENTO){
                    getErrori(errori, verifyLoginConditionsResponse);
                    
                    List<Errore> erroriOrigResponse = cleanRiferimentoExternalError(errori);
                    
                    return response = new GetAuthenticationConShibbolethResponse(erroriOrigResponse,
                            RisultatoCodice.FALLIMENTO);
                }

            }

            /* Generazione Token */

                //genera un token di autenticazione e  traccia tutte le informazioni relative alla
                //richiesta di autenticazione inserendo un record in lcce.auth_d_login_data

            tokenAutenticazione = UUID.randomUUID().toString();

                //	id da sequence
                //	id_abilitazione = identificativo dell’ abilitazione in auth_r_abilitazione
                //	cf_richiedente = codice fiscale del richiedente (restituito da IRIDE)
                //	cf_assistito = codice Fiscale dell’assistito ricevuta in input da CCE
                //	client_ip = ip address dell’applicazione chiamante ricevuta in input da CCE
                //	remote_ip = ip address dell’applicazione remota ricevuta in input da CCE
                //	token = token di autenticazione generato
                //	data_richiesta = data di richiesta del token
                //	data_utilizzo NULL
                //	data_inserimento = data di inserimento del record
                //	data_aggiornamento NULL

            LoginDataDto loginDataDto = registraLoginData(parameters, abilitazioneDto, tokenAutenticazione);

            //Se ci sono parametri aggiuntivi vanno inseriti nella d_login_param
            if(parameters.getParametriLogin() != null &&
                   !parameters.getParametriLogin().isEmpty()){
                //	id da sequence
                //	id_login = identificativo del record inserito in lcce.auth_d_login_data
                //	codice = codice del parametro
                //	valore = valore del parametro
                //	data_inserimento = data di inserimento del record
                for(ParametriLogin p : parameters.getParametriLogin()){
                    if(p.getCodice() != null && !p.getCodice().isEmpty()){
                        registraParametriLogin(p, loginDataDto);
                    }
                }
            }

            //Per l'azione e' previsto un log nella l_log_audit
            logGeneralDao.logAudit(logGeneralDaoBean.getLogAuditDto(), applicazioneDto, abilitazioneDto,
                    utenteDto, ruoloDto, tokenAutenticazione, CatalogoLogAudit.LOG_SUCCESSO_AUTHENTICATION.getValue());

            response = new GetAuthenticationConShibbolethResponse(null, RisultatoCodice.SUCCESSO);
            response.setAuthenticationToken(tokenAutenticazione);

        } catch (Exception e) {
            //ERRORE GENERICO SERVIZIO
            //AUTH_ER_000: Errore interno del servizio non riconducibile ad altri errori codificati
            //(l’errore è codificato in lcce.auth_d_catalogo_log).
        	log.warn("[authenticationConShibbolethService::getAuthenticationConShibboleth]", e);
            errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(),
                    CatalogoLog.ERRORE_INTERNO.getValue()));
            response = new GetAuthenticationConShibbolethResponse(errori,
                    RisultatoCodice.FALLIMENTO);
        } finally {
            /* LOG END */
            if(response!=null) {
            	  response.setErrore(errori);
            }
          
            
            logGeneralDao.logEnd(logGeneralDaoBean, abilitazioneDto,
                    response, tokenAutenticazione, null, GETAUTHENTICATIONCONSHIBB, (response!=null && response.getEsito()!=null)? response.getEsito().getValue():null);

        }

        return response;
	}
}
