/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.token2;

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
import it.csi.dma.dmaloginccebl.interfacews.validation.TokenInformation2ServiceValidator;
import it.csi.dma.dmaloginccebl.util.Credenziali;
import it.csi.dma.dmaloginccebl.util.Utils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.xml.ws.WebServiceContext;
import java.util.*;

/**
 */
@Component
public class TokenInformation2DaoImpl {

    private final static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);
    
    private static final String ERRORE_ELABORAZIONE = "CC_ER_103";

	private static final Object GETTOKENINFORMATION = "GetTokenInformation2";
    
    @Autowired
    LogGeneralDao logGeneralDao;

    @Autowired
	TokenInformation2ServiceValidator tokenInformation2ServiceValidator;
    
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
    RuoloLowDao ruoloLowDao;
    
    @Autowired
    LoginParamLowDao loginParamLowDao;

    @Autowired
	FunzionalitaLowDao funzionalitaLowDao;

    @Autowired
	TreeFunzionalitaLowDao treeFunzionalitaLowDao;

    @Autowired
	ServiziLowDao serviziLowDao;

    @Autowired
	CollocazioneLowDao collocazioneLowDao;
    
    public String className = "TokenInformation2DaoImpl";


    /**
     * Implementazione del metodo getTokenInformation del servizio
     */
    public GetTokenInformation2Response getTokenInformation2(GetTokenInformation2Request getTokenInformation2Request, WebServiceContext wsContext) {

    	final String methodName = "getTokenInformation2";
        List<Errore> errori = new ArrayList<>();
        GetTokenInformation2Response response = null;
        LogGeneralDaoBean logGeneralDaoBean = null;
        ApplicazioneDto applicazioneDto = null;
		
        try {
			// GENERAZIONE LOG
			logGeneralDaoBean = this.prepareLogBeanTokenInformation(getTokenInformation2Request, wsContext);
			logGeneralDao.logStart(logGeneralDaoBean, Servizi.TOKEN2.getValue());
			
			// VERIFICA CREDENZIALI
        	errori = tokenInformation2ServiceValidator.validateCredenziali(Servizi.TOKEN2, null, wsContext, errori, logGeneralDaoBean);
        	if (checkErrori(errori)) {
        		return response=new GetTokenInformation2Response(errori, RisultatoCodice.FALLIMENTO);
        	}
    	    
        	//3. VERIFICA PARAMETRI IN INPUT
        	errori = tokenInformation2ServiceValidator.verifyCampiObbligatori(getTokenInformation2Request, logGeneralDaoBean, errori);
        	if (checkErrori(errori)) {
        		return response=new GetTokenInformation2Response(errori, RisultatoCodice.FALLIMENTO);
        	}
        	
        	//4. VERIFICA ESISTENZA TOKEN IN ARCHIVIO
        	LoginDataDto loginDataDto = getLoginData(getTokenInformation2Request);
        	
        	errori = tokenInformation2ServiceValidator.verifyEsistenzaToken(logGeneralDaoBean, errori, loginDataDto);
        	if (checkErrori(errori)) {
        		return response=new GetTokenInformation2Response(errori, RisultatoCodice.FALLIMENTO);
        	}
        		
    		//5. VERIFICA TOKEN NON UTILIZZATO
        	errori = tokenInformation2ServiceValidator.verifyUtilizzoToken(getTokenInformation2Request, logGeneralDaoBean, errori, loginDataDto);
        	if (checkErrori(errori)) {
        		return response=new GetTokenInformation2Response(errori, RisultatoCodice.FALLIMENTO);
        	}
    		
			// 6. VERIFICA CONGRUENZA IP IN INPUT CON IP IN ARCHIVIO (salvato da
			// getAuthentication) - RIMOSSO dopo incontro di sicurezza de 05/22 per problemi
			// legati all'instradamento sui vari cloud/reti
			// errori =
			// tokenInformation2ServiceValidator.verifyIp(getTokenInformation2Request,
			// logGeneralDaoBean, errori, loginDataDto);
			// if (checkErrori(errori)) {
			// return response=new GetTokenInformation2Response(errori,
			// RisultatoCodice.FALLIMENTO);
			// }

        	// VERIFICA APPLICAZIONE IN INPUT CON QUELLA PRESENTE IN LOGIN DATA
			errori = tokenInformation2ServiceValidator.verificaApplicazione(getTokenInformation2Request, logGeneralDaoBean, errori, loginDataDto);
			if (checkErrori(errori)) {
				return response=new GetTokenInformation2Response(errori, RisultatoCodice.FALLIMENTO);
			}
    		
    		//8. VERIFICA VALIDIT� TEMPORALE DEL TOKEN
    		errori = tokenInformation2ServiceValidator.verifyValiditaTemporaleToken(logGeneralDaoBean, loginDataDto, errori);
        	if (checkErrori(errori)) {
        		return response=new GetTokenInformation2Response(errori, RisultatoCodice.FALLIMENTO);
        	}

        	// Reperimento funzionalita' abilitate all'utente
			List<Funzionalita> funzionalitaResponse = null;
			List<TreeFunzionalitaDto> listaFunzionalitaAbilitate =
					treeFunzionalitaLowDao.findFunzionalitaForTokenInfo2(loginDataDto.getCfRichiedente(), loginDataDto.getRuoloRichiedente(),
							loginDataDto.getApplicazioneRichiesta(), loginDataDto.getCodiceCollocazione(), loginDataDto.getCollCodiceAzienda());


			if(listaFunzionalitaAbilitate != null && !listaFunzionalitaAbilitate.isEmpty()){
				//Per ogni funzionalita' ricerco tutti i padri e tutti i figli aggiungendoli al Set
				//delle funzionalita' abilitate per evitare duplicati
				//(Ho creato i metodi equals e hashCode per il corretto funzionamento di questa logica)
				Set<TreeFunzionalitaDto> treeFunzionalitaAbilitateSet = new HashSet<TreeFunzionalitaDto>();
				treeFunzionalitaAbilitateSet.addAll(listaFunzionalitaAbilitate);
				for(TreeFunzionalitaDto treeFunzionalitaDto : listaFunzionalitaAbilitate){

					List<TreeFunzionalitaDto> funzionalitaParentsList = treeFunzionalitaLowDao.findFunzionalitaParents(treeFunzionalitaDto);
					List<TreeFunzionalitaDto> funzionalitaSonsList = treeFunzionalitaLowDao.findFunzionalitaSons(treeFunzionalitaDto);

					if(funzionalitaParentsList != null && !funzionalitaParentsList.isEmpty()){
						treeFunzionalitaAbilitateSet.addAll(funzionalitaParentsList);
					}

					if(funzionalitaSonsList != null && !funzionalitaSonsList.isEmpty()){
						treeFunzionalitaAbilitateSet.addAll(funzionalitaSonsList);
					}
				}
				//Con tutte le funzionalita' presenti nel set mi creo la lista finale per la response
				funzionalitaResponse = getFunzionalitaResponse(treeFunzionalitaAbilitateSet, loginDataDto.getApplicazioneRichiesta());
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
    		RuoloDto ruoloDto = getRuolo(loginDataDto);
			CollocazioneDto collocazioneDto = getCollocazione(loginDataDto);
    		Richiedente richiedente = setRichiedente(utenteDto, ruoloDto, collocazioneDto, loginDataDto);
    		
    		
    		
    		logGeneralDaoBean.getMessaggiDto().setCfRichiedente(richiedente.getCodiceFiscale());
    		logGeneralDaoBean.getMessaggiDto().setRuoloRichiedente(richiedente.getRuolo());
    		/*
    		 * informazioni_tracciate = auth_d_catalogo_log_audit.**Descrizione** dell�azione relativa alla verifica e utilizzo del token -> descrizione o descrizioneCodice?
    		 * id_catalogo_log_audit = identificativo dell�azione relativa alla alla verifica e utilizzo del token censita in auth_d_catalogo_log_audit.
    		 */
    		LogAuditDto logAuditDto = setLogAudit(logGeneralDaoBean, loginDataDto);
    		
    		logGeneralDao.logAudit(logAuditDto, null, null, utenteDto, ruoloDto,
    				getTokenInformation2Request.getToken(), CatalogoLogAudit.LOG_SUCCESSO_TOKEN.getValue());
    		
    		//11. Componi Response    		
    		response = new GetTokenInformation2Response(null, RisultatoCodice.SUCCESSO);

    		setResponse(response, loginDataDto, richiedente, funzionalitaResponse);
    		
    		//9. CONFIGURAZIONE VALENZA TEMPORALE NEL DB
    		updateLoginData(loginDataDto);

        } catch (Exception e) {
        	e.printStackTrace();
        	errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.ERRORE_INTERNO.getValue()));
            response = new GetTokenInformation2Response(errori, RisultatoCodice.FALLIMENTO);
        } finally {
            String xmlOut = Utils.xmlMessageFromObject(response);
            logGeneralDao.logEnd(logGeneralDaoBean, null, response, getTokenInformation2Request.getToken(), xmlOut, GETTOKENINFORMATION, response.getEsito().getValue());
        }
        return response;
    }

	private List<Funzionalita> getFunzionalitaResponse(Set<TreeFunzionalitaDto> treeFunzionalitaAbilitateSet, String applicazioneRichiesta) throws Exception {
		List<Funzionalita> funzionalitaResponse = new ArrayList<>();
    	for(TreeFunzionalitaDto treeFunzionalitaDto : treeFunzionalitaAbilitateSet){
    		/*
    			Aggiunto controllo sull'applicazione
    			Praticamente la funzionalita' viene registrata in response solo
    			se l'applicazione non e' valorizzata (quindi si tratta di un profilo) oppure e' valorizzata con l'applicazione richiesta
    		 */
			FunzionalitaDto funzionalitaDto = getFunzionalitaDto(treeFunzionalitaDto.getFunzionalitaDto().getIdFunzione());
    		if(funzionalitaDto.getApplicazioneDto() == null ||
					funzionalitaDto.getApplicazioneDto().getCodice().equalsIgnoreCase(applicazioneRichiesta)){
				Funzionalita funzionalita = new Funzionalita();
				// Ricerco e Setto informazioni funzionalita

				funzionalita.setCodiceFunzionalita(funzionalitaDto.getCodiceFunzione());
				funzionalita.setDescrizioneFunzionalita(funzionalitaDto.getDescrizioneFunzione());
				//Recupero codice funzionalita padre ed setto le informazioni se presente
				if(treeFunzionalitaDto.getFunzionalitaTreePadreDto() != null){
					TreeFunzionalitaDto funzionalitaTreePadreDto = getTreeFunzionalitaDto(treeFunzionalitaDto.getFunzionalitaTreePadreDto().getIdTreeFunzione());
					FunzionalitaDto funzionalitaPadreDto = getFunzionalitaDto(funzionalitaTreePadreDto.getFunzionalitaDto().getIdFunzione());
					funzionalita.setCodiceFunzionalitaPadre(funzionalitaPadreDto.getCodiceFunzione());
					funzionalita.setDescrizioneFunzionalitaPadre(funzionalitaPadreDto.getDescrizioneFunzione());
				}

				funzionalitaResponse.add(funzionalita);
			}

		}
		return funzionalitaResponse;
	}

	private TreeFunzionalitaDto getTreeFunzionalitaDto(Long idTreeFunzionalita) {
		return treeFunzionalitaLowDao.findByPrimaryId(idTreeFunzionalita);
	}

	private FunzionalitaDto getFunzionalitaDto(Long idFunzionalita) {
		return funzionalitaLowDao.findByPrimaryId(idFunzionalita);
	}

	private boolean checkErrori(List<Errore> errori) {
        if (errori != null && !errori.isEmpty()) {
            return true;
        }
        return false;
    }

	private ApplicazioneDto getApplicazione(LoginDataDto loginDataDto) throws Exception {
		return Utils.getFirstRecord(applicazioneLowDao.findByCodice(new ApplicazioneDto(), loginDataDto.getApplicazioneRichiesta()));
	}
    
	private LoginDataDto getLoginData(GetTokenInformation2Request getTokenInformation2Request) throws Exception {
		
		LoginDataDto loginDataDto = new LoginDataDto();
		loginDataDto.setToken(getTokenInformation2Request.getToken());
		//devo inserire come filtro che il servizio che ha generato il token sia getAuthentication2
		ServiziDto getAuth2 = new ServiziDto();
		getAuth2 = Utils.getFirstRecord(serviziLowDao.findByCodice(new ServiziDto(), Servizi.AUTHENTICATION2.toString()));
		loginDataDto.setServiziDto(getAuth2);
    	LoginDataDto loginDataDtos = Utils.getFirstRecord(loginDataLowDao.findByFilter(loginDataDto));

		return loginDataDtos;
	}

	private void updateLoginData(LoginDataDto loginDataDto) {
		
		loginDataDto.setDataUtilizzoToken(Utils.sysdate());
		loginDataLowDao.update(loginDataDto);
	}

	private LogAuditDto setLogAudit(LogGeneralDaoBean logGeneralDaoBean, LoginDataDto loginDataDto) {
		
		LogAuditDto logAuditDto = logGeneralDaoBean.getLogAuditDto();
		logAuditDto.setCfAssistito(loginDataDto.getCfAssistito());
		logAuditDto.setMessaggiDto(logGeneralDaoBean.getMessaggiDto());
		logAuditDto.setCfRichiedente(loginDataDto.getCfRichiedente());
				
		return logAuditDto;
	}
	
	private void setResponse(GetTokenInformation2Response response, LoginDataDto loginDataDto, 
			Richiedente richiedente, List<Funzionalita> funzionalitaResponse) throws Exception {
		
		response.setRichiedente(richiedente);
		FunzionalitaAbilitate funzionalitaAbilitate = new FunzionalitaAbilitate();
		funzionalitaAbilitate.setFunzionalita(funzionalitaResponse);
		response.setFunzionalitaAbilitate(funzionalitaAbilitate);
        
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
	
	private Richiedente setRichiedente(UtenteDto utenteDto, RuoloDto ruoloDto, CollocazioneDto collocazioneDto, LoginDataDto loginDataDto) {
		
		Richiedente richiedente = new Richiedente();
		if(utenteDto != null) {
			richiedente.setNome(utenteDto.getNome());
	        richiedente.setCognome(utenteDto.getCognome());
		}
		richiedente.setCodiceFiscale(loginDataDto.getCfRichiedente());
		if(ruoloDto != null) {
			richiedente.setRuolo(ruoloDto.getCodice());
		}

		richiedente.setCodiceCollocazione(collocazioneDto.getColCodice());
		richiedente.setDescrizioneCollocazione(collocazioneDto.getColDescrizione());
		richiedente.setCodiceAzienda(collocazioneDto.getColCodAzienda());
		richiedente.setDescrizioneAzienda(collocazioneDto.getColDescAzienda());

		return richiedente;
	}

	private UtenteDto getUtente(LoginDataDto loginDataDto) throws Exception {
		UtenteDto utenteDto = new UtenteDto();
	    utenteDto.setCodiceFiscale(loginDataDto.getCfRichiedente());
	    utenteDto = Utils.getFirstRecord(utenteLowDao.findByFilter(utenteDto));
	    return utenteDto;
	}

	private CollocazioneDto getCollocazione(LoginDataDto loginDataDto) throws Exception {
		CollocazioneDto collocazioneDto = new CollocazioneDto();
		collocazioneDto.setColCodice(loginDataDto.getCodiceCollocazione());
		collocazioneDto.setColCodAzienda(loginDataDto.getCollCodiceAzienda());
		collocazioneDto = Utils.getFirstRecord(collocazioneLowDao.findByFilter(collocazioneDto));
		return collocazioneDto;
	}

	private RuoloDto getRuolo(LoginDataDto loginDataDto) {

		RuoloDto ruoloDto = Utils.getFirstRecord(ruoloLowDao.findByCodice(new RuoloDto(), loginDataDto.getRuoloRichiedente()));
		
		return ruoloDto;
	}
	
	private LogGeneralDaoBean prepareLogBeanTokenInformation(GetTokenInformation2Request getTokenInformation2Request, WebServiceContext wsContext) {
		
		ServiziDto tokenInformationService = Utils.getFirstRecord(serviziLowDao.findByCodice(new ServiziDto(), Servizi.TOKEN2.getValue()));

		//Creo MessaggiDto
		MessaggiDto messaggiDto = createMessaggiDtoForTokenInformation(getTokenInformation2Request, tokenInformationService, wsContext);
		
		//Creo LogDto
		LogDto logDto = createLogDtoForTokenInformation(getTokenInformation2Request, tokenInformationService);

		//Creo MessaggiXmlDto
		MessaggiXmlDto messaggiXmlDto = createMessaggiXmlDtoForTokenInformation(getTokenInformation2Request); 
		
		//Creo LogAuditDto
		LogAuditDto logAuditDto = createLogAuditDtoForTokenInformation(getTokenInformation2Request, tokenInformationService);
		
		return new LogGeneralDaoBean(logDto, logAuditDto, messaggiDto, messaggiXmlDto, null);
		
	}

	private MessaggiDto createMessaggiDtoForTokenInformation(GetTokenInformation2Request getTokenInformation2Request,
			ServiziDto tokenInformationService, WebServiceContext wsContext) {
		
		MessaggiDto messaggiDto = new MessaggiDto();
		Credenziali credenzialiFromHeader=tokenInformation2ServiceValidator.getCredentialsFromHeader(wsContext);
		if(credenzialiFromHeader != null) {
			messaggiDto.setCertificato(credenzialiFromHeader.getUsername());
		}
		messaggiDto.setServiziDto(tokenInformationService);
		messaggiDto.setClientIp(getTokenInformation2Request.getIpBrowser());
		messaggiDto.setApplicazione(getTokenInformation2Request.getApplicazione());
		messaggiDto.setToken(getTokenInformation2Request.getToken());
		messaggiDto.setDataRicezione(Utils.sysdate());
		
		return messaggiDto;
	}
	
	private LogDto createLogDtoForTokenInformation(GetTokenInformation2Request getTokenInformation2Request, 
			ServiziDto tokenInformationService) {
		
		LogDto logDto = new LogDto();
		logDto.setServiziDto(tokenInformationService);
		
		return logDto;
	}

	private MessaggiXmlDto createMessaggiXmlDtoForTokenInformation(GetTokenInformation2Request getTokenInformation2Request) {
		
		MessaggiXmlDto messaggiXmlDto = new MessaggiXmlDto();
		String xmlIn = Utils.xmlMessageFromObject(getTokenInformation2Request);
		messaggiXmlDto.setXmlIn(xmlIn != null ? xmlIn.getBytes() : null);
		
		return messaggiXmlDto;
	}
	
	private LogAuditDto createLogAuditDtoForTokenInformation(GetTokenInformation2Request getTokenInformation2Request,
			ServiziDto tokenInformationService) {
		
		LogAuditDto logAuditDto = new LogAuditDto();
		logAuditDto.setToken(getTokenInformation2Request.getToken());
		logAuditDto.setIpRichiedente(getTokenInformation2Request.getIpBrowser());
		logAuditDto.setServiziDto(tokenInformationService);
		
		return logAuditDto;
	}
}
