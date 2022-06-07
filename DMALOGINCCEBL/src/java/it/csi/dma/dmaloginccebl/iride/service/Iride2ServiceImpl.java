/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.iride.service;

import java.rmi.RemoteException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import it.csi.dma.dmaloginccebl.business.dao.ServiziLowDao;
import it.csi.dma.dmaloginccebl.business.dao.dto.ApplicazioneDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.IdentitaCallDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.ServiziDto;
import it.csi.dma.dmaloginccebl.business.dao.util.CatalogoLog;
import it.csi.dma.dmaloginccebl.business.dao.util.Constants;
import it.csi.dma.dmaloginccebl.business.dao.util.Servizi;
import it.csi.dma.dmaloginccebl.integration.LogGeneralDaoBean;
import it.csi.dma.dmaloginccebl.integration.dao.LogGeneralDao;
import it.csi.dma.dmaloginccebl.interfacews.authentication.Credenziali;
import it.csi.dma.dmaloginccebl.interfacews.msg.RisultatoCodice;
import it.csi.dma.dmaloginccebl.iride.client.PolicyEnforcerBaseService;
import it.csi.dma.dmaloginccebl.iride.data.AuthException;
import it.csi.dma.dmaloginccebl.iride.data.IdProviderNotFoundException;
import it.csi.dma.dmaloginccebl.iride.data.Identita;
import it.csi.dma.dmaloginccebl.iride.data.InternalException;
import it.csi.dma.dmaloginccebl.iride.data.MalformedUsernameException;
import it.csi.dma.dmaloginccebl.iride.data.SystemException;
import it.csi.dma.dmaloginccebl.iride.data.UnrecoverableException;
import it.csi.dma.dmaloginccebl.util.Utils;

@Component
public class Iride2ServiceImpl implements AuthService {
	private static final String clsName = Iride2ServiceImpl.class.getName();
	protected final Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	private String address;

	public void setAddress(String address) {
		this.address = address;
	}

	@Autowired
	private PolicyEnforcerBaseService srvInterface;

	@Autowired
	private ServiziLowDao serviziLowDao;

	public PolicyEnforcerBaseService getSrvInterface() {
		return srvInterface;
	}

	@Autowired
	LogGeneralDao logGeneralDao;

	@PostConstruct
	public void createService() {
		try {

			Iride2ServiceLocator pLoc = new Iride2ServiceLocator(address);
			this.srvInterface = pLoc.getPolicyEnforcerBase();
		} catch (Exception e) {
			log.error("createService", e);
		}
	}

	@Override
	public Identita autenticaWithPin(IrideRequest irideRequest)
			throws RemoteException, SystemException, AuthException, MalformedUsernameException,
			IdProviderNotFoundException, InternalException, UnrecoverableException {

		log.debug("call autentica(user + pwd + pin)");

		try {
			Identita response = srvInterface.identificaUserPasswordPIN(
					irideRequest.getUsername(), irideRequest.getPassword(), irideRequest.getPin());
			return response;
		} finally {
			log.debug("end autentica(user + pwd + pin)");
		}
	}
	
	
	@Override
	public IdentitaCallDto autenticaWithPinCall(IrideRequest irideRequest)
			throws RemoteException, SystemException, AuthException, MalformedUsernameException,
			IdProviderNotFoundException, InternalException, UnrecoverableException {
		
		IdentitaCallDto identitaDto = new IdentitaCallDto();
		
		log.debug("call autentica(user + pwd + pin)");

		try {
			identitaDto  = srvInterface.identificaUserPasswordPINCall(
					irideRequest.getUsername(), irideRequest.getPassword(), irideRequest.getPin());
			
		} finally {
			log.debug("end autentica(user + pwd + pin)");
		}
	
		
		
		return identitaDto;
	}

	@Override
	public Identita autentica(IrideRequest irideRequest)
			throws RemoteException, SystemException, AuthException, MalformedUsernameException,
			IdProviderNotFoundException, InternalException, UnrecoverableException {

		Identita response;

		log.info("call autentica(user + pwd)");
		response = srvInterface.identificaUserPassword(irideRequest.getUsername(), irideRequest.getPassword());
		
		
		

		return response;
	}
	
	
	
	@Override
	public IdentitaCallDto autenticaCall(IrideRequest irideRequest)
			throws RemoteException, SystemException, AuthException, MalformedUsernameException,
			IdProviderNotFoundException, InternalException, UnrecoverableException {
		
		
		IdentitaCallDto identitaDto = new IdentitaCallDto();
		

		log.info("call autentica(user + pwd)");
		identitaDto = srvInterface.identificaUserPasswordCallDto(irideRequest.getUsername(), irideRequest.getPassword());
		
		
		return identitaDto;
	}

	@Override
	public Identita autenticaCil(String user, String pwd) throws RemoteException, SystemException, AuthException,
			MalformedUsernameException, IdProviderNotFoundException, InternalException, UnrecoverableException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Identita autenticaMedico(Credenziali credenziali, ApplicazioneDto applicazione, LogGeneralDaoBean logGeneralDaoBean){
		Identita identita = null;
		IrideRequest irideRequest = getIrideRequest(credenziali);
		ServiziDto serviziDtoIride = null;
		IdentitaCallDto identitaDto = new IdentitaCallDto();
		try{
			if("S".equals(applicazione.getPinRichiesto())){
				serviziDtoIride = Utils.getFirstRecord(serviziLowDao.findByCodice(new ServiziDto(), Servizi.IRIDE_USER_PASSWORD_PIN.getValue()));
//				identita = autenticaWithPin(irideRequest);
				
				identitaDto = autenticaWithPinCall(irideRequest);
				identita = identitaDto.getIdentita();
			}else{
				serviziDtoIride = Utils.getFirstRecord(serviziLowDao.findByCodice(new ServiziDto(), Servizi.IRIDE_USER_PASSWORD.getValue()));
				
				identitaDto = autenticaCall(irideRequest);
				identita = identitaDto.getIdentita();
			}
			
			String esitoIrideLog = RisultatoCodice.SUCCESSO.getValue();
			
			if(identitaDto.getAxisFaultException() != null || identitaDto.getGenericException() != null) {
				esitoIrideLog = RisultatoCodice.FALLIMENTO.getValue();
			}
			
			
			logGeneralDao.registraXmlServiziRichiamati(logGeneralDaoBean, identitaDto.getXmlIn(), identitaDto.getXmlOut(), serviziDtoIride,esitoIrideLog);
			
			if (identitaDto.getAxisFaultException() != null) {
				log.error(this.eccezioneIride(identitaDto));
				identita = new Identita();
				identita.setErrore(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.ERRORE_AUTENTICAZIONE.getValue()));
			}else if (identitaDto.getGenericException() != null) {
				log.error(identitaDto.getGenericException());
				identita = new Identita();
				identita.setErrore(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.ERRORE_AUTENTICAZIONE.getValue()));
			}
			
			
		}catch (Exception e){
			log.error(e);
			identita = new Identita();
			identita.setErrore(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.ERRORE_AUTENTICAZIONE.getValue()));
		}
		
		
		return identita;
	}
	
	private Exception eccezioneIride(IdentitaCallDto identitaDto) {
		
		Exception ecc = new Exception();
		
		if (identitaDto.getAxisFaultException() instanceof java.rmi.RemoteException) {
			ecc = (java.rmi.RemoteException) identitaDto.getAxisFaultException();
		}
		if (identitaDto.getAxisFaultException() instanceof SystemException) {
			ecc = (SystemException) identitaDto.getAxisFaultException();
		}
		if (identitaDto.getAxisFaultException() instanceof AuthException) {
			ecc = (AuthException) identitaDto.getAxisFaultException();
		}
		if (identitaDto.getAxisFaultException() instanceof MalformedUsernameException) {
			ecc = (MalformedUsernameException) identitaDto.getAxisFaultException();
		}
		if (identitaDto.getAxisFaultException() instanceof IdProviderNotFoundException) {
			ecc = (IdProviderNotFoundException) identitaDto.getAxisFaultException();
		}
		if (identitaDto.getAxisFaultException() instanceof InternalException) {
			ecc = (InternalException) identitaDto.getAxisFaultException();
		}
		if (identitaDto.getAxisFaultException() instanceof UnrecoverableException) {
			ecc = (UnrecoverableException) identitaDto.getAxisFaultException();
		}
		
		return ecc;
	}

	private IrideRequest getIrideRequest(Credenziali credenziali) {
		IrideRequest irideRequest = new IrideRequest();
		irideRequest.setUsername(credenziali.getUsername());
		irideRequest.setPassword(credenziali.getPassword());
		irideRequest.setPin(credenziali.getPIN());
		return irideRequest;
	}

	private String decrypt(String pin, PrivateKey privateKey) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] textDecrypted = cipher.doFinal(pin.getBytes());
        String pinDecrypted = new String(textDecrypted);
		return pinDecrypted;
	}
	
	private String getValueFromHeader(NodeList nodeList, String value) {
		if(nodeList != null && nodeList.getLength() > 0) {
			for(int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				
				if(node.getLocalName() != null) {
					if(node.getLocalName().equals(value)) {
		            	return node.getTextContent();
		            }
		            
		            String valueFound =  getValueFromHeader(node.getChildNodes(), value);
		            if(valueFound != null) {
		            	return valueFound;
		            }
				}
		    }
		}
		return null;
	}
	
	
}