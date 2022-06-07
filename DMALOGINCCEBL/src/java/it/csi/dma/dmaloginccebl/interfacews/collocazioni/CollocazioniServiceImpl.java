/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.collocazioni;

import it.csi.dma.dmaloginccebl.business.dao.AbilitazioneLowDao;
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
import it.csi.dma.dmaloginccebl.interfacews.validation.CollocazioniServiceValidator;
import it.csi.dma.dmaloginccebl.util.Credenziali;
import it.csi.dma.dmaloginccebl.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import java.util.*;

@WebService(serviceName = "CollocazioniService", portName = "CollocazioniService", targetNamespace = "http://dmacc.csi.it/", endpointInterface = "it.csi.dma.dmaloginccebl.interfacews.collocazioni.CollocazioniService")
@Service(value = "collocazioniService")
public class CollocazioniServiceImpl implements CollocazioniService {

	@Resource
	private WebServiceContext wsContext;

	@Autowired
	private LogGeneralDao logGeneralDao;

	@Autowired
	private CollocazioniServiceValidator collocazioniServiceValidator;

	@Autowired
	private ServiziLowDao serviziLowDao;

	@Autowired
	private AbilitazioneLowDao abilitazioneLowDao;

	@Autowired
	private TreeFunzionalitaLowDao treeFunzionalitaLowDao;

	@Autowired
	private FunzionalitaLowDao funzionalitaLowDao;

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public GetCollocazioniResponse GetCollocazioni(GetCollocazioniRequest parameters) {
		GetCollocazioniResponse response = null;
		LogGeneralDaoBean logGeneralDaoBean = null;
		AbilitazioneDto abilitazioneDto = null;
		List<Errore> errori = new ArrayList<>();

		try {
			logGeneralDaoBean = this.prepareLogBeanCollocazioni(parameters);
			logGeneralDao.logStart(logGeneralDaoBean, Servizi.RUOLI_UTENTE.getValue());

			errori = collocazioniServiceValidator.validateCredenziali(Servizi.COLLOCAZIONI, null, wsContext, errori,
					logGeneralDaoBean);

			if (collocazioniServiceValidator.checkErrori(errori)) {
				return response = new GetCollocazioniResponse(errori, RisultatoCodice.FALLIMENTO);
			}

			// Verifica obbligatorieta e correttezza campi
			ValidateCollocazioniResponse validateCollocazioniResponse = collocazioniServiceValidator
					.validateCampi(parameters, wsContext, errori, logGeneralDaoBean);

			if (collocazioniServiceValidator.checkErrori(validateCollocazioniResponse.getListaErrori())) {
				return response = new GetCollocazioniResponse(validateCollocazioniResponse.getListaErrori(),
						RisultatoCodice.FALLIMENTO);
			}

			// Verifica congruenza parametri interdipendenti
			validateCollocazioniResponse = collocazioniServiceValidator.validateParametriInterdipendenti(wsContext,
					errori, logGeneralDaoBean, validateCollocazioniResponse);

			if (collocazioniServiceValidator.checkErrori(validateCollocazioniResponse.getListaErrori())) {
				return response = new GetCollocazioniResponse(validateCollocazioniResponse.getListaErrori(),
						RisultatoCodice.FALLIMENTO);
			}

			// Reperimento Collocazioni
			abilitazioneDto = new AbilitazioneDto();
			abilitazioneDto.setRuoloUtenteDto(validateCollocazioniResponse.getRuoloUtenteDto());
			Collection<AbilitazioneDto> abilitazioneDtoList = abilitazioneLowDao
					.findByUtenteRuolo(abilitazioneDto);
			response = new GetCollocazioniResponse();
			Set<Collocazione> hashSetCollocazioniResponse = new HashSet<Collocazione>();
			if (abilitazioneDtoList != null && !abilitazioneDtoList.isEmpty()) {
				response.setCollocazioni(new ArrayList<Collocazione>());
				for (AbilitazioneDto abilitazione : abilitazioneDtoList) {
					/*
						Controlliamo che l'abilitazione abbia realmente una collocazione collegata
						ma soprattutto controlliamo (come in getAbilitzioni) se la funzionalita'
						collegata all'abilitazione ha un applicazione segnata altrimenti la collocazione
						non deve essere visualizzata in output in quanto in realta' l'utente non e' abilitato
						ad un applicazione (questo per evitare che risultino collocazioni abilitate ma non applicazioni)
						nel caso in cui non dovesse esserci un applicazione potrebbe essere una funzionalita'
						padre quindi cerchiamo anche i figli per vedere se troviamo almeno un'applicazione abilitata
					 */
					boolean isApplicazionePresente = false;

					isApplicazionePresente = checkApplicazioneFunzionalita(abilitazione, isApplicazionePresente);

					if(isApplicazionePresente){
						Collocazione collocazione = new Collocazione();
						collocazione.setColCodAzienda(
								abilitazione.getUtenteCollocazioneDto().getCollocazioneDto().getColCodAzienda());
						collocazione.setColCodice(
								abilitazione.getUtenteCollocazioneDto().getCollocazioneDto().getColCodice());
						collocazione.setColDescAzienda(
								abilitazione.getUtenteCollocazioneDto().getCollocazioneDto().getColDescAzienda());
						collocazione.setColDescrizione(
								abilitazione.getUtenteCollocazioneDto().getCollocazioneDto().getColDescrizione());
						hashSetCollocazioniResponse.add(collocazione);
					}
				}
			}
			if(!hashSetCollocazioniResponse.isEmpty()) {
				response.getCollocazioni().addAll(hashSetCollocazioniResponse);
			}
			if (response.getCollocazioni() == null || response.getCollocazioni().isEmpty()) {
				errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(),
						CatalogoLog.LISTA_COLLOCAZIONI_VUOTA.getValue()));
				return response = new GetCollocazioniResponse(errori, RisultatoCodice.FALLIMENTO);
			}
			response.setEsito(RisultatoCodice.SUCCESSO);
			logGeneralDao.logAudit(logGeneralDaoBean.getLogAuditDto(),
					validateCollocazioniResponse.getApplicazioneDto(), null,
					validateCollocazioniResponse.getUtenteDto(), validateCollocazioniResponse.getRuoloDto(), null,
					CatalogoLogAudit.LOG_SUCCESSO_COLLOCAZIONI.getValue());
		} catch (Exception e) {
			errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.ERRORE_INTERNO.getValue()));
			response = new GetCollocazioniResponse(errori, RisultatoCodice.FALLIMENTO);
		} finally {
			String xmlOut = Utils.xmlMessageFromObject(response);
			logGeneralDao.logEnd(logGeneralDaoBean, null, response, null, xmlOut, "GetCollocazioni",
					response.getEsito().getValue());
		}
		return response;
	}

	private boolean checkApplicazioneFunzionalita(AbilitazioneDto abilitazione, boolean isApplicazionePresente) {
		if (abilitazione.getUtenteCollocazioneDto() != null &&
				abilitazione.getTreeFunzionalitaDto() != null &&
				abilitazione.getTreeFunzionalitaDto().getFunzionalitaDto() != null &&
				abilitazione.getTreeFunzionalitaDto().getFunzionalitaDto().getApplicazioneDto() != null) {
			isApplicazionePresente = true;
		}else{
			if(abilitazione.getTreeFunzionalitaDto() != null){
				TreeFunzionalitaDto treeFunzionalitaDto = new TreeFunzionalitaDto();
				treeFunzionalitaDto.setIdTreeFunzione(abilitazione.getTreeFunzionalitaDto().getIdTreeFunzione());
				List<TreeFunzionalitaDto> funzionalitaSonsList
						= treeFunzionalitaLowDao.findFunzionalitaSons(treeFunzionalitaDto);
				if(funzionalitaSonsList != null && !funzionalitaSonsList.isEmpty()){
					for(TreeFunzionalitaDto treeFunzionalita : funzionalitaSonsList){
						FunzionalitaDto funzionalita =
								funzionalitaLowDao.findByPrimaryId(treeFunzionalita.getFunzionalitaDto().getIdFunzione());
						if(funzionalita.getApplicazioneDto() != null){
							isApplicazionePresente = true;
							break;
						}
					}
				}
			}
		}
		return isApplicazionePresente;
	}

	private LogGeneralDaoBean prepareLogBeanCollocazioni(GetCollocazioniRequest getCollocazioniRequest) {

		ServiziDto collocazioniService = Utils
				.getFirstRecord(serviziLowDao.findByCodice(new ServiziDto(), Servizi.COLLOCAZIONI.getValue()));

		// Creo MessaggiDto
		MessaggiDto messaggiDto = new MessaggiDto();
		messaggiDto.setServiziDto(collocazioniService);
		Credenziali credenzialiFromHeader = collocazioniServiceValidator.getCredentialsFromHeader(wsContext);
		if (credenzialiFromHeader != null) {
			messaggiDto.setCertificato(credenzialiFromHeader.getUsername());
		}
		messaggiDto.setApplicazione(getCollocazioniRequest.getRichiedente().getApplicazioneChiamante());
		messaggiDto.setDataRicezione(Utils.sysdate());
		messaggiDto.setClientIp(getCollocazioniRequest.getRichiedente().getIpChiamante());
		messaggiDto.setCfRichiedente(getCollocazioniRequest.getRichiedente().getCodiceFiscaleRichiedente());
		messaggiDto.setRuoloRichiedente(getCollocazioniRequest.getRichiedente().getCodiceRuoloRichiedente());

		// Creo LogDto
		LogDto logDto = new LogDto();
		logDto.setServiziDto(collocazioniService);
		logDto.setMessaggiDto(messaggiDto);

		// Creo MessaggiXmlDto
		MessaggiXmlDto messaggiXmlDto = new MessaggiXmlDto();
		String xmlIn = Utils.xmlMessageFromObject(getCollocazioniRequest);
		messaggiXmlDto.setXmlIn(xmlIn != null ? xmlIn.toString().getBytes() : null);
		messaggiXmlDto.setMessaggiDto(messaggiDto);

		// Creo LogAuditDto
		LogAuditDto logAuditDto = new LogAuditDto();
		logAuditDto.setServiziDto(collocazioniService);
		logAuditDto.setMessaggiDto(messaggiDto);
		logAuditDto.setCfRichiedente(getCollocazioniRequest.getRichiedente().getCodiceFiscaleRichiedente());
		logAuditDto.setIpRichiedente(getCollocazioniRequest.getRichiedente().getIpChiamante());

		return new LogGeneralDaoBean(logDto, logAuditDto, messaggiDto, messaggiXmlDto, null);
	}
}
