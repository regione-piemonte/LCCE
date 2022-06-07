/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.abilitazione;

import it.csi.dma.dmaloginccebl.business.dao.FunzionalitaLowDao;
import it.csi.dma.dmaloginccebl.business.dao.ServiziLowDao;
import it.csi.dma.dmaloginccebl.business.dao.TreeFunzionalitaLowDao;
import it.csi.dma.dmaloginccebl.business.dao.dto.*;
import it.csi.dma.dmaloginccebl.business.dao.util.CatalogoLog;
import it.csi.dma.dmaloginccebl.business.dao.util.CatalogoLogAudit;
import it.csi.dma.dmaloginccebl.business.dao.util.Servizi;
import it.csi.dma.dmaloginccebl.integration.LogGeneralDaoBean;
import it.csi.dma.dmaloginccebl.integration.dao.LogGeneralDao;
import it.csi.dma.dmaloginccebl.interfacews.msg.Errore;
import it.csi.dma.dmaloginccebl.interfacews.msg.RisultatoCodice;
import it.csi.dma.dmaloginccebl.interfacews.validation.AbilitazioneServiceValidator;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@WebService(serviceName="AbilitazioneService", portName="AbilitazioneService", targetNamespace = "http://dmacc.csi.it/", endpointInterface = "it.csi.dma.dmaloginccebl.interfacews.abilitazione.AbilitazioneService")
@Service(value = "abilitazioneService")
public class AbilitazioneServiceImpl implements AbilitazioneService {

	public static final String GET_ABILITAZIONI = "GetAbilitazioni";
	@Resource
    private WebServiceContext wsContext;
	
	@Autowired
	private LogGeneralDao logGeneralDao;
	
	@Autowired
	private AbilitazioneServiceValidator abilitazioneServiceValidator;
	
	@Autowired
	private ServiziLowDao serviziLowDao;

	@Autowired
	private TreeFunzionalitaLowDao treeFunzionalitaLowDao;

	@Autowired
	private FunzionalitaLowDao funzionalitaLowDao;
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public GetAbilitazioniResponse getAbilitazioni(GetAbilitazioniRequest request) {
		GetAbilitazioniResponse response=null;
        LogGeneralDaoBean logGeneralDaoBean = null;
		List<Errore> errori = new ArrayList<>();
		ValidateAbilitazioneResponse validateAbilitazioneResponse=new ValidateAbilitazioneResponse();

		try {
			//1:Log
			logGeneralDaoBean = this.prepareLogBeanAbilitazioni(request, wsContext);
			logGeneralDao.logStart(logGeneralDaoBean, Servizi.ABILITAZIONE.getValue());

			//Validazione credenziali
			errori = abilitazioneServiceValidator.validateCredenziali(Servizi.ABILITAZIONE, null, wsContext, errori, logGeneralDaoBean);
			if (checkErrori(errori)) {
				return response=new GetAbilitazioniResponse(errori, RisultatoCodice.FALLIMENTO);
			}

			//Validazione base campi di input
			validateAbilitazioneResponse = abilitazioneServiceValidator.validate(request, logGeneralDaoBean, validateAbilitazioneResponse);
			if (checkErrori(validateAbilitazioneResponse.getListaErrori())) {
				return response=new GetAbilitazioniResponse(errori, RisultatoCodice.FALLIMENTO);
			}

			//Validazione campi interdipendenti
			validateAbilitazioneResponse = abilitazioneServiceValidator.verificaCampiInterdipentendi(request, logGeneralDaoBean, validateAbilitazioneResponse);
			if (checkErrori(validateAbilitazioneResponse.getListaErrori())) {
				return response=new GetAbilitazioniResponse(validateAbilitazioneResponse.getListaErrori(), RisultatoCodice.FALLIMENTO);
			}

			//Verifica presenza abilitazioni (se lista vuota va direttamente in errore)
			validateAbilitazioneResponse = abilitazioneServiceValidator.verificaAbilitazione(logGeneralDaoBean, validateAbilitazioneResponse);
			if (checkErrori(validateAbilitazioneResponse.getListaErrori())) {
				return response=new GetAbilitazioniResponse(validateAbilitazioneResponse.getListaErrori(), RisultatoCodice.FALLIMENTO);
			}

			/*
				Per ogni abilitazione trovata ricerco il record corrispondente in tree funzionalita
				con il quale ricerco la singola funzionalita' e quindi l'applicazione a cui si e' abilitati
				e la inserisco nella lista della response
			 */
			List<String> listCodiciApplicazione = new ArrayList<String>();
			List<Abilitazione> listaAbilitazioniResponse = new ArrayList<Abilitazione>();
			Set<Abilitazione> hashSetAbilitazioni = new HashSet<Abilitazione>();
			for(AbilitazioneDto abilitazioneDto : validateAbilitazioneResponse.getAbilitazioneDtoList()){
				getCodiciApplicazione(listCodiciApplicazione, abilitazioneDto);


				if(listCodiciApplicazione != null && !listCodiciApplicazione.isEmpty()){
					for(String codiceApplicazione : listCodiciApplicazione){
						Abilitazione abilitazione = new Abilitazione();
						Applicazione applicazione = new Applicazione();
						applicazione.setCodiceApplicazione(codiceApplicazione);
						abilitazione.setApplicazione(applicazione);
						hashSetAbilitazioni.add(abilitazione);
					}
				}
			}
			if(!hashSetAbilitazioni.isEmpty()){
				listaAbilitazioniResponse.addAll(hashSetAbilitazioni);
			}
			if(listaAbilitazioniResponse.isEmpty()){
				validateAbilitazioneResponse.getListaErrori().add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.ABILITAZIONI_NON_PRESENTI.getValue()));
				return response=new GetAbilitazioniResponse(validateAbilitazioneResponse.getListaErrori(), RisultatoCodice.FALLIMENTO);
			}

			//Log Audit
			logGeneralDao.logAudit(logGeneralDaoBean.getLogAuditDto(), validateAbilitazioneResponse.getApplicazioneDto(), null,
					validateAbilitazioneResponse.getUtenteDto(), validateAbilitazioneResponse.getRuoloDto(), null, CatalogoLogAudit.LOG_SUCCESSO_ABILITAZIONE.getValue());

			response = new GetAbilitazioniResponse(RisultatoCodice.SUCCESSO);
			response.setListaAbilitazioni(listaAbilitazioniResponse);


		} catch (Exception e) {
			errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.ERRORE_INTERNO.getValue()));
            response = new GetAbilitazioniResponse(errori, RisultatoCodice.FALLIMENTO);
		} finally {
			String xmlOut = Utils.xmlMessageFromObject(response);
			logGeneralDao.logEnd(logGeneralDaoBean, null, response, null, xmlOut,
					GET_ABILITAZIONI, response.getEsito().getValue());
		}
		return response;
	}

	private void getCodiciApplicazione(List<String> listCodiciApplicazione, AbilitazioneDto abilitazioneDto) {
		if(abilitazioneDto.getTreeFunzionalitaDto() != null &&
				abilitazioneDto.getTreeFunzionalitaDto().getFunzionalitaDto() != null &&
				abilitazioneDto.getTreeFunzionalitaDto().getFunzionalitaDto().getApplicazioneDto() != null){
			listCodiciApplicazione.add(abilitazioneDto.getTreeFunzionalitaDto().getFunzionalitaDto().getApplicazioneDto().getCodice());
			getCodiciApplicazioneSons(listCodiciApplicazione, abilitazioneDto);
		}else{
			if(abilitazioneDto.getTreeFunzionalitaDto() != null){
				getCodiciApplicazioneSons(listCodiciApplicazione, abilitazioneDto);
			}
		}
	}

	private void getCodiciApplicazioneSons(List<String> listCodiciApplicazione, AbilitazioneDto abilitazioneDto) {
		TreeFunzionalitaDto treeFunzionalitaDto = new TreeFunzionalitaDto();
		treeFunzionalitaDto.setIdTreeFunzione(abilitazioneDto.getTreeFunzionalitaDto().getIdTreeFunzione());
		List<TreeFunzionalitaDto> funzionalitaSonsList
				= treeFunzionalitaLowDao.findFunzionalitaSons(treeFunzionalitaDto);
		if(funzionalitaSonsList != null && !funzionalitaSonsList.isEmpty()){
			for(TreeFunzionalitaDto treeFunzionalita : funzionalitaSonsList){
				FunzionalitaDto funzionalita =
						funzionalitaLowDao.findByPrimaryId(treeFunzionalita.getFunzionalitaDto().getIdFunzione());
				if(funzionalita.getApplicazioneDto() != null){
					listCodiciApplicazione.add(funzionalita.getApplicazioneDto().getCodice());
				}
			}
		}
	}

	private boolean checkErrori(List<Errore> errori) {
        if (errori != null && !errori.isEmpty()) {
            return true;
        }
        return false;
    }
	
	private LogGeneralDaoBean prepareLogBeanAbilitazioni(GetAbilitazioniRequest getAbilitazioniRequest, WebServiceContext wsContext) {
		
		ServiziDto abilitazioneService = Utils.getFirstRecord(serviziLowDao.findByCodice(new ServiziDto(), Servizi.ABILITAZIONE.getValue()));

		//Creo MessaggiDto
		MessaggiDto messaggiDto=new MessaggiDto();
		
		//Set Certificato con username= ???
		Credenziali credenzialiFromHeader=abilitazioneServiceValidator.getCredentialsFromHeader(wsContext);
		if(credenzialiFromHeader != null) {
			messaggiDto.setCertificato(abilitazioneServiceValidator.getCredentialsFromHeader(wsContext).getUsername());
		}
		messaggiDto.setServiziDto(abilitazioneService);
		messaggiDto.setClientIp(getAbilitazioniRequest.getRichiedente().getIpChiamante());
		messaggiDto.setCfRichiedente(getAbilitazioniRequest.getRichiedente().getCodiceFiscaleRichiedente());
		messaggiDto.setApplicazione(getAbilitazioniRequest.getRichiedente().getApplicazioneChiamante());
		messaggiDto.setRuoloRichiedente(getAbilitazioniRequest.getRichiedente().getCodiceRuoloRichiedente());
		messaggiDto.setDataRicezione(Utils.sysdate());
		
		//Creo LogDto
		LogDto logDto = new LogDto();
		logDto.setServiziDto(abilitazioneService);
		logDto.setMessaggiDto(messaggiDto);
		
		//Creo LogAuditDto
		LogAuditDto logAuditDto = new LogAuditDto();
		logAuditDto.setCfRichiedente(getAbilitazioniRequest.getRichiedente().getCodiceFiscaleRichiedente());
		logAuditDto.setServiziDto(abilitazioneService);
		logAuditDto.setIpRichiedente(getAbilitazioniRequest.getRichiedente().getIpChiamante());
		logDto.setMessaggiDto(messaggiDto);
		
		//Creo MessaggiXmlDto
		MessaggiXmlDto messaggiXmlDto = new MessaggiXmlDto();
		String xmlIn = Utils.xmlMessageFromObject(getAbilitazioniRequest);
		messaggiXmlDto.setXmlIn(xmlIn != null ? xmlIn.getBytes() : null);
		logDto.setMessaggiDto(messaggiDto);

		return new LogGeneralDaoBean(logDto, logAuditDto, messaggiDto, messaggiXmlDto, null);
	}
}
