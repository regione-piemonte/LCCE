/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.farmacia;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.dma.dmaloginccebl.integration.dao.FarmacieAderentiDao;
import it.csi.dma.dmaloginccebl.business.dao.ServiziLowDao;
import it.csi.dma.dmaloginccebl.integration.dao.VerificaFarmacistaDao;
import it.csi.dma.dmaloginccebl.business.dao.dto.AbilitazioneDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.FarmacieDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.LogAuditDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.LogDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.MessaggiDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.MessaggiXmlDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.ServiziDto;
import it.csi.dma.dmaloginccebl.business.dao.util.Constants;
import it.csi.dma.dmaloginccebl.business.dao.util.Servizi;
import it.csi.dma.dmaloginccebl.integration.LogGeneralDaoBean;
import it.csi.dma.dmaloginccebl.integration.dao.LogGeneralDao;
import it.csi.dma.dmaloginccebl.interfacews.msg.Errore;
import it.csi.dma.dmaloginccebl.interfacews.msg.Indirizzo;
import it.csi.dma.dmaloginccebl.interfacews.msg.RisultatoCodice;
import it.csi.dma.dmaloginccebl.interfacews.validation.FarmacieAderentiServiceValidator;
import it.csi.dma.dmaloginccebl.interfacews.validation.VerificaFarmacistaServiceValidator;
import it.csi.dma.dmaloginccebl.util.Credenziali;
import it.csi.dma.dmaloginccebl.util.Utils;

@WebService(serviceName="FarmaciaService", portName="FarmaciaService", targetNamespace = "http://dmacc.csi.it/", endpointInterface = "it.csi.dma.dmaloginccebl.interfacews.farmacia.FarmaciaService")
@Service(value = "farmaciaService")
public class FarmaciaServiceImpl implements FarmaciaService{
	
	private org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(Constants.APPLICATION_CODE);
	
	@Resource
    private WebServiceContext wsContext;
	
	@Autowired
	private LogGeneralDao logGeneralDao;
	
	@Autowired
	private ServiziLowDao serviziLowDao;
	
	@Autowired
	private FarmacieAderentiServiceValidator farmacieAderentiServiceValidator;
	
	@Autowired
	private VerificaFarmacistaServiceValidator verificaFarmacistaServiceValidator;
	
	@Autowired
	public FarmacieAderentiDao farmacieAderentiDao;

	@Autowired
	public VerificaFarmacistaDao verificaFarmacistaDao;
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public GetFarmacieAderentiResponse getFarmacieAderenti(GetFarmacieAderentiRequest parameters) {
		
		LogGeneralDaoBean logGeneralDaoBean = null;
		AbilitazioneDto abilitazioneDto = null;
		List<Errore> errori = new ArrayList<Errore>();
		GetFarmacieAderentiResponse getFarmacieAderentiResponse = new GetFarmacieAderentiResponse(RisultatoCodice.SUCCESSO);
		try {
			//1:Log
			logGeneralDaoBean=this.prepareLogBeanFarmacieAderenti(parameters, wsContext);
			logGeneralDao.logStart(logGeneralDaoBean, Servizi.FARMACIE_ADERENTI.getValue());
			
			//2: Verifica Credenziali
			errori=farmacieAderentiServiceValidator.validateCredenzialiDigest(Servizi.FARMACIE_ADERENTI, null, wsContext, errori, logGeneralDaoBean);
			
			if(checkErrori(errori)) {
				getFarmacieAderentiResponse=new GetFarmacieAderentiResponse(errori,RisultatoCodice.FALLIMENTO);
				return getFarmacieAderentiResponse;
			}
			
			//3-4: Verifica Obbligatorieta e Correttezza Parametri
			errori=farmacieAderentiServiceValidator.validateCampi(parameters, errori, logGeneralDaoBean);
			
			if(checkErrori(errori)) {
				getFarmacieAderentiResponse=new GetFarmacieAderentiResponse(errori,RisultatoCodice.FALLIMENTO);
				return getFarmacieAderentiResponse;
			}
			
			//5: Reperimento Dati Farmacie Aderenti
			List<FarmacieDto> farmacieAderenti = farmacieAderentiDao.findFarmacieAderenti(parameters, errori);
			mapFarmacieAderentiResponse(getFarmacieAderentiResponse, farmacieAderenti);
		} catch(Exception e) {
			log.error("getFarmacieAderenti", e);
			 getFarmacieAderentiResponse.setEsito(RisultatoCodice.FALLIMENTO);
		} finally {
			String xmlOut = Utils.xmlMessageFromObject(getFarmacieAderentiResponse);
			logGeneralDao.logEnd(logGeneralDaoBean, abilitazioneDto, getFarmacieAderentiResponse, null, xmlOut, "GetFarmacieAderenti", getFarmacieAderentiResponse.getEsito().getValue());
		}
		return getFarmacieAderentiResponse;
		
	}

	/**
	 * @param getFarmacieAderentiResponse
	 * @param farmacieAderenti
	 */
	public void mapFarmacieAderentiResponse(GetFarmacieAderentiResponse getFarmacieAderentiResponse,
			List<FarmacieDto> farmacieAderenti) {
		if(farmacieAderenti != null && farmacieAderenti.size() > 0) {

			List<FarmaciaAderente> farmaciaAderenteList = new ArrayList<FarmaciaAderente>();
			
			for(FarmacieDto farmacia : farmacieAderenti) {
				FarmaciaAderente farmaciaAderenteResponse = new FarmaciaAderente();
				farmaciaAderenteResponse.setCodice(farmacia.getCodiceFarmacia());
				farmaciaAderenteResponse.setNome(farmacia.getDenominazioneFarmacia());

				Indirizzo indirizzo = new Indirizzo();
				indirizzo.setCap(farmacia.getCap());
				indirizzo.setComune(farmacia.getComune());
				indirizzo.setDescrizioneIndirizzo(farmacia.getIndirizzo()+" "+farmacia.getNumeroCivico());
				indirizzo.setProvincia(farmacia.getDenominazioneProvincia());
				farmaciaAderenteResponse.setIndirizzo(indirizzo);
				farmaciaAderenteList.add(farmaciaAderenteResponse);
			}
			
			Farmacie farmacie = new Farmacie();
			farmacie.setFarmaciaAderente(farmaciaAderenteList);
			getFarmacieAderentiResponse.setFarmacie(farmacie);
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@Override
	public VerificaFarmacistaResponse verificaFarmacista(VerificaFarmacistaRequest parameters) {
		LogGeneralDaoBean logGeneralDaoBean = null;
		AbilitazioneDto abilitazioneDto = null;
		List<Errore> errori = new ArrayList<>();
		VerificaFarmacistaResponse verificaFarmacistaResponse = new VerificaFarmacistaResponse(RisultatoCodice.SUCCESSO);
		try {
			
			//1:Log
			logGeneralDaoBean=this.prepareLogBeanVerificaFarmacista(parameters, wsContext);
			logGeneralDao.logStart(logGeneralDaoBean, Servizi.VERIFICA_FARMACISTA.getValue());
			
			//2: Verifica Credenziali
			errori=verificaFarmacistaServiceValidator.validateCredenzialiDigest(Servizi.VERIFICA_FARMACISTA, null, wsContext, errori, logGeneralDaoBean);
			
			if(checkErrori(errori)) {
				return verificaFarmacistaResponse = new VerificaFarmacistaResponse(errori,RisultatoCodice.FALLIMENTO);
			}
			
			//3-4: Verifica Obbligatorieta e Correttezza Parametri
			errori=verificaFarmacistaServiceValidator.validateCampi(parameters, errori, logGeneralDaoBean);
			
			if(checkErrori(errori)) {
				return verificaFarmacistaResponse = new VerificaFarmacistaResponse(errori,RisultatoCodice.FALLIMENTO);
			}
			
			//5: Verifica Farmacia Aderente
			verificaFarmacistaDao.ricercaFarmaciaAderente(parameters, errori, logGeneralDaoBean);
			
			if(checkErrori(errori)) {
				return verificaFarmacistaResponse = new VerificaFarmacistaResponse(errori,RisultatoCodice.FALLIMENTO);
			}
			
			//6: Verifica Farmacia Abituale
			verificaFarmacistaDao.verificaFunzionalitaFarmaciaAbituale(parameters, errori, logGeneralDaoBean);
			
			if(checkErrori(errori)) {
				return verificaFarmacistaResponse = new VerificaFarmacistaResponse(errori,RisultatoCodice.FALLIMENTO);
			}			
		}
		catch(Exception e) {
			log.error("verificaFarmacista", e);
			verificaFarmacistaResponse.setEsito(RisultatoCodice.FALLIMENTO);
		} finally {
			String xmlOut = Utils.xmlMessageFromObject(verificaFarmacistaResponse);
			logGeneralDao.logEnd(logGeneralDaoBean, abilitazioneDto, verificaFarmacistaResponse, null, xmlOut, "verificaFarmacista", verificaFarmacistaResponse.getEsito().getValue());
		}
		return verificaFarmacistaResponse;
		
	}

	
	private LogGeneralDaoBean prepareLogBeanVerificaFarmacista(VerificaFarmacistaRequest parameters,
			WebServiceContext wsContext2) {
		ServiziDto verificaFarmacistaService = Utils.getFirstRecord(serviziLowDao.findByCodice(new ServiziDto(), Servizi.VERIFICA_FARMACISTA.getValue()));

		//Creo MessaggiDto
		MessaggiDto messaggiDto=new MessaggiDto();
		
		//Set Certificato con username= ???
		Credenziali credenzialiFromHeader=verificaFarmacistaServiceValidator.getCredentialsFromHeader(wsContext);
		if(credenzialiFromHeader != null) {
			messaggiDto.setCertificato(verificaFarmacistaServiceValidator.getCredentialsFromHeader(wsContext).getUsername());
		}
		messaggiDto.setServiziDto(verificaFarmacistaService);
		messaggiDto.setCfRichiedente(parameters.getCodiceFiscaleFarmacista());
		messaggiDto.setDataRicezione(Utils.sysdate());
		
		//Creo LogDto
		LogDto logDto = new LogDto();
		logDto.setServiziDto(verificaFarmacistaService);
		logDto.setMessaggiDto(messaggiDto);
		
		//Creo MessaggiXmlDto
		MessaggiXmlDto messaggiXmlDto = new MessaggiXmlDto();
		String xmlIn = Utils.xmlMessageFromObject(parameters);
		messaggiXmlDto.setXmlIn(xmlIn != null ? xmlIn.toString().getBytes() : null);
		logDto.setMessaggiDto(messaggiDto);

		return new LogGeneralDaoBean(logDto, new LogAuditDto(), messaggiDto, messaggiXmlDto, null);
	}


	private LogGeneralDaoBean prepareLogBeanFarmacieAderenti(GetFarmacieAderentiRequest parameters,
			WebServiceContext wsContext2) {
		ServiziDto farmacieAderentiService = Utils.getFirstRecord(serviziLowDao.findByCodice(new ServiziDto(), Servizi.FARMACIE_ADERENTI.getValue()));

		//Creo MessaggiDto
		MessaggiDto messaggiDto=new MessaggiDto();
		
		//Set Certificato con username= ???
		Credenziali credenzialiFromHeader=farmacieAderentiServiceValidator.getCredentialsFromHeader(wsContext);
		if(credenzialiFromHeader != null) {
			messaggiDto.setCertificato(farmacieAderentiServiceValidator.getCredentialsFromHeader(wsContext).getUsername());
		}
		messaggiDto.setServiziDto(farmacieAderentiService);
		messaggiDto.setClientIp(parameters.getRichiedente().getIp());
		messaggiDto.setApplicazione(parameters.getRichiedente().getApplicazioneChiamante());
		messaggiDto.setCfRichiedente(parameters.getRichiedente().getCodiceFiscaleRichiedente());
		messaggiDto.setDataRicezione(Utils.sysdate());
		
		//Creo LogDto
		LogDto logDto = new LogDto();
		logDto.setServiziDto(farmacieAderentiService);
		logDto.setMessaggiDto(messaggiDto);
		
		//Creo MessaggiXmlDto
		MessaggiXmlDto messaggiXmlDto = new MessaggiXmlDto();
		String xmlIn = Utils.xmlMessageFromObject(parameters);
		messaggiXmlDto.setXmlIn(xmlIn != null ? xmlIn.toString().getBytes() : null);
		logDto.setMessaggiDto(messaggiDto);

		return new LogGeneralDaoBean(logDto, new LogAuditDto(), messaggiDto, messaggiXmlDto, null);
	}


	private boolean checkErrori(List<Errore> errori) {
        if (errori != null && !errori.isEmpty()) {
            return true;
        }
        return false;
    }
}
