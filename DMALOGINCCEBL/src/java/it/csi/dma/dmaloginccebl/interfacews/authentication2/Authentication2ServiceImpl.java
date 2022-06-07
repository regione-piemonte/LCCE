/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.authentication2;

import it.csi.dma.dmaloginccebl.business.dao.LoginDataLowDao;
import it.csi.dma.dmaloginccebl.business.dao.LoginParamLowDao;
import it.csi.dma.dmaloginccebl.business.dao.ServiziLowDao;
import it.csi.dma.dmaloginccebl.business.dao.ServiziRichiamatiXmlLowDao;
import it.csi.dma.dmaloginccebl.business.dao.dto.*;
import it.csi.dma.dmaloginccebl.business.dao.util.CatalogoLog;
import it.csi.dma.dmaloginccebl.business.dao.util.CatalogoLogAudit;
import it.csi.dma.dmaloginccebl.business.dao.util.Constants;
import it.csi.dma.dmaloginccebl.business.dao.util.Servizi;
import it.csi.dma.dmaloginccebl.client.verifyLoginConditions2.VerifyLoginConditions2Client;
import it.csi.dma.dmaloginccebl.client.verifyLoginConditions2.VerifyLoginConditions2Request;
import it.csi.dma.dmaloginccebl.client.verifyLoginConditions2.VerifyLoginConditions2Response;
import it.csi.dma.dmaloginccebl.integration.LogGeneralDaoBean;
import it.csi.dma.dmaloginccebl.integration.dao.LogGeneralDao;
import it.csi.dma.dmaloginccebl.interfacews.msg.Errore;
import it.csi.dma.dmaloginccebl.interfacews.msg.ParametriLogin;
import it.csi.dma.dmaloginccebl.interfacews.msg.RisultatoCodice;
import it.csi.dma.dmaloginccebl.interfacews.validation.Authentication2ServiceValidator;
import it.csi.dma.dmaloginccebl.iride.service.AuthService;
import it.csi.dma.dmaloginccebl.util.Credenziali;
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
@WebService(serviceName = "Authentication2Service", portName = "Authentication2Service", targetNamespace = "http://dmacc.csi.it/", endpointInterface = "it.csi.dma.dmaloginccebl.interfacews.authentication2.Authentication2Service")
@Service(value = "authentication2Service")
public class Authentication2ServiceImpl implements Authentication2Service {

    private final static Logger log = Logger .getLogger(Constants.APPLICATION_CODE);

	private static final Object GETAUTHENTICATION = "GetAuthentication2";
    
    @Resource
    WebServiceContext wsContext;
    
    @Autowired
    LogGeneralDao logGeneralDao;

    @Autowired
    Authentication2ServiceValidator authentication2ServiceValidator;

    @Autowired
    AuthService authService;

    @Autowired
    LoginDataLowDao loginDataLowDao;

    @Autowired
    LoginParamLowDao loginParamLowDao;

    @Autowired
    VerifyLoginConditions2Client verifyLoginConditions2Client;

    @Autowired
    ServiziRichiamatiXmlLowDao serviziRichiamatiXmlLowDao;
    
    @Autowired
    private ServiziLowDao serviziLowDao;
    
    public String className = "Authentication2ServiceImpl";

    /**
     * Implementazioone del metodo getAuthentication del servizio
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public GetAuthentication2Response getAuthentication2(GetAuthentication2Request parameters) {
        final Richiedente richiedente = parameters.getRichiedente();

        ValidateGetAuthentication2Response validateGetAuthentication2Response = new ValidateGetAuthentication2Response();
        GetAuthentication2Response response = null;
        LogGeneralDaoBean logGeneralDaoBean = null;
        AbilitazioneDto abilitazioneDto = null;
        String tokenAutenticazione = null;

        try {

        	/* LOG START */

            logGeneralDaoBean = this.prepareLogBeanAuthentication(parameters);
            String parametriLogin = getParametriLogStart(parameters);
            logGeneralDao.logStart(logGeneralDaoBean, parametriLogin);

            /* Verifica Credenziali */
            validateGetAuthentication2Response.setListaErrori(authentication2ServiceValidator.validateCredenziali(Servizi.AUTHENTICATION2,
                    null, wsContext, validateGetAuthentication2Response.getListaErrori(), logGeneralDaoBean));
            if (checkErrori(validateGetAuthentication2Response.getListaErrori()))
                return response = new GetAuthentication2Response(validateGetAuthentication2Response.getListaErrori(), RisultatoCodice.FALLIMENTO);

        	/* Verifica base parametri input */

            validateGetAuthentication2Response = authentication2ServiceValidator.validate(parameters, logGeneralDaoBean, validateGetAuthentication2Response);
            if (checkErrori(validateGetAuthentication2Response.getListaErrori()))
                return response = new GetAuthentication2Response(validateGetAuthentication2Response.getListaErrori(), RisultatoCodice.FALLIMENTO);

            /* Verifica presenza abilitazione */
            validateGetAuthentication2Response = authentication2ServiceValidator
                    .verificaAbilitazione(logGeneralDaoBean,validateGetAuthentication2Response,
                            parameters.getRichiedente().getCodiceCollocazione(), parameters.getRichiedente().getCollCodiceAzienda());
            if (checkErrori(validateGetAuthentication2Response.getListaErrori()))
                return response = new GetAuthentication2Response(validateGetAuthentication2Response.getListaErrori(), RisultatoCodice.FALLIMENTO);

            /* Verifica necessita' VerifyLoginConditions */

                //ricerca in lcce.auth_d_applicazione l’applicazione
                //richiesta e verifica se l’attributo urlverifyloginconditions è popolato

                //Se non popolato si passa diretti alla generazione del token
                //Se popolato si fa la chiamata
            if(validateGetAuthentication2Response.getApplicazioneRichiestaDto().getUrlVerifyLoginConditions() != null){

                /* Chiamata VerifyLoginConditions */

                //Il servizio viene chiamato utilizzando le credenziali presenti sul DB (tabella auth_t_credenziali_servizi)
                CredenzialiServiziDto credenzialiServiziDto = authentication2ServiceValidator.verificaCredenzialiServizio(Servizi.VERIFYLOGIN2.getValue());

                VerifyLoginConditions2Request verifyLoginConditions2Request = getVerifyLoginConditionsRequest(parameters);
                VerifyLoginConditions2Response verifyLoginConditions2Response = null;
                try{
                    verifyLoginConditions2Response =
                            verifyLoginConditions2Client.verifyLoginConditions2(verifyLoginConditions2Request, validateGetAuthentication2Response.getApplicazioneRichiestaDto(), credenzialiServiziDto);
                }catch (Exception e){
                    e.printStackTrace();
                    log.error("Errore chiamata verifyLoginConditions2", e);
                    validateGetAuthentication2Response.getListaErrori().add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(),
                            CatalogoLog.ERRORE_INTERNO.getValue()));
                    return new GetAuthentication2Response(validateGetAuthentication2Response.getListaErrori(), RisultatoCodice.FALLIMENTO);
                }finally {
                    //Bisogna salvare XML_IN e XML_OUT della chiamata nella tabella lcce.auth_l_xml_servizi_richiamati
                    logGeneralDao.registraXmlServiziRichiamati(logGeneralDaoBean, Utils.xmlMessageFromObject(verifyLoginConditions2Request),
                            Utils.xmlMessageFromObject(verifyLoginConditions2Response), credenzialiServiziDto.getServiziDto(),
                            verifyLoginConditions2Response != null ? verifyLoginConditions2Response.getEsito().getValue() : null);
                }

                //Se esito negativo si prendono direttamente gli errori tornati dal servizio si scrivono nella messaggi errore
                //e si ritornano in response
                //Se esito positivo si passa allo step successivo
                if(verifyLoginConditions2Response.getEsito() == RisultatoCodice.FALLIMENTO){
                    getErrori(validateGetAuthentication2Response.getListaErrori(), verifyLoginConditions2Response);

                    List<Errore> erroriOrigResponse = cleanRiferimentoExternalError(validateGetAuthentication2Response.getListaErrori());

                    return response = new GetAuthentication2Response(erroriOrigResponse,
                            RisultatoCodice.FALLIMENTO);
                }

            }

            /* Generazione Token */

                //genera un token di autenticazione e  traccia tutte le informazioni relative alla
                //richiesta di autenticazione inserendo un record in lcce.auth_d_login_data

            tokenAutenticazione = UUID.randomUUID().toString();

            LoginDataDto loginDataDto = registraLoginData(parameters, tokenAutenticazione);

            //Se ci sono parametri aggiuntivi vanno inseriti nella d_login_param
            if(parameters.getParametriLogin() != null &&
                   !parameters.getParametriLogin().isEmpty()){
                for(ParametriLogin p : parameters.getParametriLogin()){
                    if(p.getCodice() != null && !p.getCodice().isEmpty()){
                        registraParametriLogin(p, loginDataDto);
                    }
                }
            }

            //Per l'azione e' previsto un log nella l_log_audit
            logGeneralDao.logAudit(logGeneralDaoBean.getLogAuditDto(), validateGetAuthentication2Response.getApplicazioneRichiestaDto(), abilitazioneDto,
                    validateGetAuthentication2Response.getUtenteDto(), validateGetAuthentication2Response.getRuoloDto(), tokenAutenticazione, CatalogoLogAudit.LOG_SUCCESSO_AUTHENTICATION.getValue());

            //Setto a null la lista errori per evitare che il tag esca in response anche se non sono presenti.
            validateGetAuthentication2Response.setListaErrori(null);
            response = new GetAuthentication2Response(null, RisultatoCodice.SUCCESSO);
            response.setAuthenticationToken(tokenAutenticazione);

        } catch (Exception e) {
            //ERRORE GENERICO SERVIZIO
            //AUTH_ER_000: Errore interno del servizio non riconducibile ad altri errori codificati
            //(l’errore è codificato in lcce.auth_d_catalogo_log).
            e.printStackTrace();
            validateGetAuthentication2Response.getListaErrori().add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(),
                    CatalogoLog.ERRORE_INTERNO.getValue()));
            response = new GetAuthentication2Response(validateGetAuthentication2Response.getListaErrori(),
                    RisultatoCodice.FALLIMENTO);
        } finally {
            /* LOG END */
            String xmlOut = Utils.xmlMessageFromObject(response);

            if(response!=null) {
            	  response.setErrore(validateGetAuthentication2Response.getListaErrori());
            }


            logGeneralDao.logEnd(logGeneralDaoBean, abilitazioneDto,
                    response, tokenAutenticazione, xmlOut, GETAUTHENTICATION, response.getEsito().getValue());

        }

        return response;
    }

    private String getCertificatoFromSecurity() {
        MessageContext mctx = wsContext.getMessageContext();
        SecurityContext sec = (SecurityContext) mctx.get("org.apache.cxf.security.SecurityContext");
        String certificato = null;
        if(sec != null && sec.getUserPrincipal() != null){
            certificato = sec.getUserPrincipal().getName().substring(3);
        }
        return certificato;
    }

    private List<Errore> cleanRiferimentoExternalError(List<Errore> erroriServizio){
    	List<Errore> errori = new ArrayList<Errore>();

    	if(erroriServizio!=null) {
    		for(Errore err : erroriServizio){
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

    private String getParametriLogStart(GetAuthentication2Request parameters) {
        String parametriLogin = null;
        if(parameters.getParametriLogin() != null && !parameters.getParametriLogin().isEmpty()){
            for(ParametriLogin parametro : parameters.getParametriLogin()){
                parametriLogin = authentication2ServiceValidator.appendParametriErrore(parametriLogin, parametro);
            }
        }
        return parametriLogin;
    }

    private void getErrori(List<Errore> errori, VerifyLoginConditions2Response verifyLoginConditions2Response) {
        for(it.csi.dma.dmaloginccebl.client.verifyLoginConditions.common.Errore erroreVerify : verifyLoginConditions2Response.getErrori()){
            Errore errore = new Errore();
            BeanUtils.copyProperties(erroreVerify, errore);
            errore.setRiferimento(Constants.ERRORE_SERIVIZIO_ESTERNO);
            errori.add(errore);
        }
    }

    private VerifyLoginConditions2Request getVerifyLoginConditionsRequest(GetAuthentication2Request parameters) {
        VerifyLoginConditions2Request verifyLoginConditions2Request = new VerifyLoginConditions2Request();
        verifyLoginConditions2Request.setRuoloUtente(parameters.getRichiedente().getRuolo());
        verifyLoginConditions2Request.setCodiceFiscaleUtente(parameters.getRichiedente().getCodiceFiscaleRichiedente());
        verifyLoginConditions2Request.setCodiceAziendaRichiedente(getCodiceAzienda(parameters));
        verifyLoginConditions2Request.setApplicazioneRichiesta(parameters.getRichiedente().getApplicazioneRichiesta());
        verifyLoginConditions2Request.setCodiceCollocazioneRichiedente(parameters.getRichiedente().getCodiceCollocazione());
        List<it.csi.dma.dmaloginccebl.client.verifyLoginConditions.common.ParametriLogin> listParametriVerify = new ArrayList<>();
        if(parameters.getParametriLogin() != null && !parameters.getParametriLogin().isEmpty()){
            setListaParametriLogin(parameters, verifyLoginConditions2Request, listParametriVerify);
        }
        return verifyLoginConditions2Request;
    }

    private String getCodiceAzienda(GetAuthentication2Request parameters) {
        String codiceAzienda = parameters.getRichiedente().getCollCodiceAzienda();
        if(codiceAzienda.startsWith("010")){
            codiceAzienda = codiceAzienda.substring(3);
        }
        return codiceAzienda;
    }

    private void setListaParametriLogin(GetAuthentication2Request parameters, VerifyLoginConditions2Request verifyLoginConditionsRequest, List<it.csi.dma.dmaloginccebl.client.verifyLoginConditions.common.ParametriLogin> listParametriVerify) {
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

    private LoginDataDto registraLoginData(GetAuthentication2Request parameters,String tokenAutenticazione) {
        LoginDataDto loginDataDto = getLoginDataDto(parameters, tokenAutenticazione);

        return loginDataLowDao.insert(loginDataDto);
    }

    private LoginDataDto getLoginDataDto(GetAuthentication2Request parameters, String tokenAutenticazione) {
        LoginDataDto loginDataDto = new LoginDataDto();
        loginDataDto.setCfRichiedente(parameters.getRichiedente().getCodiceFiscaleRichiedente());
        loginDataDto.setRuoloRichiedente(parameters.getRichiedente().getRuolo());
        loginDataDto.setCollCodiceAzienda(parameters.getRichiedente().getCollCodiceAzienda());
        loginDataDto.setCodiceCollocazione(parameters.getRichiedente().getCodiceCollocazione());
        loginDataDto.setApplicazioneChiamante(parameters.getRichiedente().getApplicazioneChiamante());
        loginDataDto.setApplicazioneRichiesta(parameters.getRichiedente().getApplicazioneRichiesta());
        loginDataDto.setServiziDto(Utils.getFirstRecord(serviziLowDao.findByCodice(new ServiziDto(), Servizi.AUTHENTICATION2.getValue())));
        loginDataDto.setClientIp(parameters.getRichiedente().getIpClient());
        loginDataDto.setRemoteIp(parameters.getRichiedente().getIpClient());
        loginDataDto.setToken(tokenAutenticazione);
        loginDataDto.setDataRichiestaToken(Utils.sysdate());
        return loginDataDto;
    }

    private boolean checkErrori(List<Errore> errori) {
        if (errori != null && !errori.isEmpty()) {
            return true;
        }
        return false;
    }

	private LogGeneralDaoBean prepareLogBeanAuthentication(GetAuthentication2Request getAuthenticationRequest) {
		ServiziDto authenticationService = Utils.getFirstRecord(serviziLowDao.findByCodice(new ServiziDto(), Servizi.AUTHENTICATION2.getValue()));
		Richiedente richiedente = getAuthenticationRequest.getRichiedente();
		//Creo LogDto
		LogDto logDto = createLogDto(authenticationService);

		//Creo LogAuditDto
		LogAuditDto logAuditDto = createLogAuditDto(authenticationService, richiedente);

		//Creo MessaggiDto
		MessaggiDto messaggiDto = createMessaggiDto(authenticationService, richiedente);

		//Creo MessaggiXmlDto
		MessaggiXmlDto messaggiXmlDto = createMessaggiXmlDto(getAuthenticationRequest);

		return new LogGeneralDaoBean(logDto, logAuditDto, messaggiDto, messaggiXmlDto, null);
	}

	private LogAuditDto createLogAuditDto(ServiziDto authenticationService, Richiedente richiedente) {
		LogAuditDto logAuditDto = new LogAuditDto();
		if(richiedente != null) {
			logAuditDto.setIpRichiedente(richiedente.getIpClient());
			logAuditDto.setCfRichiedente(richiedente.getCodiceFiscaleRichiedente());
		}
		logAuditDto.setServiziDto(authenticationService);
		return logAuditDto;
	}

	private LogDto createLogDto(ServiziDto authenticationService) {
		LogDto logDto = new LogDto();
		logDto.setServiziDto(authenticationService);
		return logDto;
	}

	private MessaggiDto createMessaggiDto(ServiziDto authentication2Service, Richiedente richiedente) {
		MessaggiDto messaggiDto = new MessaggiDto();
		messaggiDto.setServiziDto(authentication2Service);
        Credenziali credenzialiFromHeader=authentication2ServiceValidator.getCredentialsFromHeader(wsContext);
        if(credenzialiFromHeader != null) {
            messaggiDto.setCertificato(credenzialiFromHeader.getUsername());
        }
		if(richiedente != null) {
			messaggiDto.setClientIp(richiedente.getIpClient());
			messaggiDto.setCfRichiedente(richiedente.getCodiceFiscaleRichiedente());
			messaggiDto.setRuoloRichiedente(richiedente.getRuolo());
			messaggiDto.setApplicazione(richiedente.getApplicazioneChiamante());
		}
		messaggiDto.setDataRicezione(Utils.sysdate());
		return messaggiDto;
	}
	
	private MessaggiXmlDto createMessaggiXmlDto(GetAuthentication2Request getAuthenticationRequest) {
		MessaggiXmlDto messaggiXmlDto = new MessaggiXmlDto();
		String xmlIn = Utils.xmlMessageFromObject(getAuthenticationRequest);
		messaggiXmlDto.setXmlIn(xmlIn != null ? xmlIn.getBytes() : null);
		return messaggiXmlDto;
	}
}
