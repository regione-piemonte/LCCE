/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.validation;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.apache.cxf.headers.Header;
import org.apache.cxf.helpers.CastUtils;
import org.apache.cxf.jaxws.context.WrappedMessageContext;
import org.apache.cxf.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;

import it.csi.dma.dmaloginccebl.business.dao.ApplicazioneLowDao;
import it.csi.dma.dmaloginccebl.business.dao.CredenzialiServiziLowDao;
import it.csi.dma.dmaloginccebl.business.dao.ServiziLowDao;
import it.csi.dma.dmaloginccebl.business.dao.dto.ApplicazioneDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.CredenzialiServiziDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.ServiziDto;
import it.csi.dma.dmaloginccebl.business.dao.util.CatalogoLog;
import it.csi.dma.dmaloginccebl.business.dao.util.Constants;
import it.csi.dma.dmaloginccebl.business.dao.util.Servizi;
import it.csi.dma.dmaloginccebl.integration.LogGeneralDaoBean;
import it.csi.dma.dmaloginccebl.integration.dao.LogGeneralDao;
import it.csi.dma.dmaloginccebl.interfacews.farmacia.Richiedente;
import it.csi.dma.dmaloginccebl.interfacews.farmacia.VerificaFarmacistaRequest;
import it.csi.dma.dmaloginccebl.interfacews.msg.Errore;
import it.csi.dma.dmaloginccebl.util.Credenziali;
import it.csi.dma.dmaloginccebl.util.Utils;
import org.w3c.dom.NodeList;

@Component
public class BaseServiceValidator {

	protected org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(Constants.APPLICATION_CODE);

	@Autowired
    protected LogGeneralDao logGeneralDao;
    
	@Autowired
	private ServiziLowDao serviziLowDao;
	
	@Autowired
    private ApplicazioneLowDao applicazioneLowDao;
	
	@Autowired
	private CredenzialiServiziLowDao credenzialiServiziLowDao;

	public boolean checkErrori(List<Errore> errori) {
        if (errori != null && !errori.isEmpty()) {
            return true;
        }
        return false;
    }
	
    protected void verificaCampoObbligatorio(LogGeneralDaoBean logGeneralDaoBean, List<Errore> errori, String campo, CatalogoLog codiceErrore) {
        if (campo == null ||
                campo.isEmpty()) {
            errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), codiceErrore.getValue()));
        }
    }
    
	public List<Errore> validateCredenziali(Servizi service, String applicazione, WebServiceContext wsContext, List<Errore> errori, LogGeneralDaoBean logGeneralDaoBean) {
				
		String username = null;
		String password = null;
	    CredenzialiServiziDto credenzialiServiziDto = new CredenzialiServiziDto();
	    
		Credenziali credenzialiFromHeader=getCredentialsFromHeader(wsContext);
	    
	    if(credenzialiFromHeader != null) {
		    username = credenzialiFromHeader.getUsername();
		    password = credenzialiFromHeader.getPassword();
	    }
	    
	    ServiziDto serviceDto = Utils.getFirstRecord(serviziLowDao.findByCodice(new ServiziDto(), service.getValue()));
	    
	    credenzialiServiziDto.setServiziDto(serviceDto);
	    
		if(applicazione != null && !applicazione.isEmpty()) {
			ApplicazioneDto applicazioneDto = new ApplicazioneDto();
		    applicazioneDto = Utils.getFirstRecord(applicazioneLowDao.findByCodice(applicazioneDto, applicazione));
			credenzialiServiziDto.setApplicazioneDto(applicazioneDto);
		}
	    
	    Collection<CredenzialiServiziDto> credenzialiServiziDtos = credenzialiServiziLowDao.findByFilterAndServizioAndData(credenzialiServiziDto);
	    boolean isValidAccess = false;
	    
	    for(CredenzialiServiziDto dto : credenzialiServiziDtos) {
	    	
	    	if(dto.getUsername().equals(username) && dto.getPassword().equals(password)) {
	    		isValidAccess = true;
	    		return errori;
	    	}
	    }
	    if(!isValidAccess) {
	    	errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.CREDENZIALI_ERRATE.getValue()));
	    }
	    
	    return errori;
	}

	public List<Errore> validateCredenzialiDigest(Servizi service, String applicazione, WebServiceContext wsContext, List<Errore> errori, LogGeneralDaoBean logGeneralDaoBean) throws Exception {

		CredenzialiServiziDto credenzialiServiziDto = new CredenzialiServiziDto();

		Credenziali credenzialiFromHeader = getCredentialsFromHeader(wsContext);

		if(credenzialiFromHeader == null){
			errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.CREDENZIALI_ERRATE.getValue()));
			return errori;
		}

		ServiziDto serviceDto = Utils.getFirstRecord(serviziLowDao.findByCodice(new ServiziDto(), service.getValue()));

		credenzialiServiziDto.setServiziDto(serviceDto);

		if(applicazione != null && !applicazione.isEmpty()) {
			ApplicazioneDto applicazioneDto = new ApplicazioneDto();
			applicazioneDto = Utils.getFirstRecord(applicazioneLowDao.findByCodice(applicazioneDto, applicazione));
			credenzialiServiziDto.setApplicazioneDto(applicazioneDto);
		}

		Collection<CredenzialiServiziDto> credenzialiServiziDtos = credenzialiServiziLowDao.findByFilterAndServizioAndData(credenzialiServiziDto);
		boolean isValidAccess = false;

		for(CredenzialiServiziDto dto : credenzialiServiziDtos) {

			if(verify(credenzialiFromHeader.getNonce(), credenzialiFromHeader.getCreated(),
					dto.getPassword(), credenzialiFromHeader.getPassword(), credenzialiFromHeader.getUsername(), dto.getUsername())) {
				isValidAccess = true;
				return errori;
			}
		}
		if(!isValidAccess) {
			errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.CREDENZIALI_ERRATE.getValue()));
		}

		return errori;
	}

	private boolean verify(String nonce, String created,
						   String pwd, String passwordDigest, String usernameHeader, String username) throws Exception {
		ByteArrayOutputStream outputStream = null;
		try {
			byte[] nonceBytes = Base64.getDecoder().decode(nonce);
			byte[] createdBytes = created.getBytes("UTF-8");
			byte[] passwordBytes = pwd.getBytes("UTF-8");

			outputStream =	new ByteArrayOutputStream( );
			outputStream.write(nonceBytes);
			outputStream.write(createdBytes);
			outputStream.write(passwordBytes);
			byte[] concatenatedBytes = outputStream.toByteArray();
			MessageDigest digest = MessageDigest.getInstance( "SHA-1" );
			digest.update(concatenatedBytes, 0, concatenatedBytes.length);
			byte[] digestBytes = digest.digest();
			String digestString = Base64.getEncoder().encodeToString(digestBytes);

			if (digestString.equals(passwordDigest) && usernameHeader.equalsIgnoreCase(username)) {
				return true;
			}
		} catch (Exception e) {
			log.error("Errore durante la verifica della passwordDigest: ", e);
		}finally {
			if(outputStream != null){
				outputStream.close();
			}
		}
		return false;
	}
	
	public Credenziali getCredentialsFromHeader(WebServiceContext wsContext) {
		Credenziali credenziali=null;
		MessageContext mctx = wsContext.getMessageContext();
		
		Message message = ((WrappedMessageContext) mctx).getWrappedMessage();
		
	    List<Header> headers = CastUtils.cast((List<?>) message.get(Header.HEADER_LIST));
	    Header header = Utils.getFirstRecord(headers);	    
	    if(header != null) {
			Element e = (Element) header.getObject();
			NodeList usernameToken = Utils.getUsernameTokenFromHeader(e.getChildNodes());
			credenziali=new Credenziali();
			credenziali.setUsername(Utils.getValueFromHeader(usernameToken, "Username"));
			credenziali.setPassword(Utils.getValueFromHeader(usernameToken, "Password"));
			credenziali.setCreated(Utils.getValueFromHeader(usernameToken, "Created"));
			credenziali.setNonce(Utils.getValueFromHeader(usernameToken, "Nonce"));
	    }
		return credenziali;
	}
	
	public void validaRichiedente(Richiedente richiedente,
			List<Errore> errori, LogGeneralDaoBean logGeneralDaoBean) {
		if(richiedente.getApplicazioneChiamante() == null || richiedente.getApplicazioneChiamante().isEmpty()) {
			
			errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.CAMPO_OBBLIGATORIO.getValue(), Constants.APPLICAZIONE_CHIAMANTE));
			
			return;
		}
		
		if(richiedente.getUuid() == null || richiedente.getUuid().isEmpty()) {
			
			errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.CAMPO_OBBLIGATORIO.getValue(), Constants.UUID));
			
			return;
		}
		
		if(!Constants.SANSOL.equals(richiedente.getApplicazioneChiamante()) && !Constants.WEBAPP_CM.equals(richiedente.getApplicazioneChiamante())) {
			errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.CAMPO_NON_CORRETTO.getValue(), Constants.APPLICAZIONE_CHIAMANTE));

			return;
		}
	}
	
	/**
	 * @param errori
	 * @param logGeneralDaoBean
	 */
	public List<Errore> verificaCorrettezzaLunghezzaCF(String codiceFiscale, String etichettaCodiceFiscale, List<Errore> errori,
			LogGeneralDaoBean logGeneralDaoBean) {
		if(codiceFiscale != null && !codiceFiscale.isEmpty() && codiceFiscale.length() != Constants.CORRETTA_LONGHEZZA_CF) {
			
			errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.CAMPO_NON_CORRETTO.getValue(), etichettaCodiceFiscale));
			
			return errori;
		}
		return errori;
	}
}
