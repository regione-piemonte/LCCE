/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.iride.service;

import it.csi.dma.dmaloginccebl.business.dao.dto.ApplicazioneDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.IdentitaCallDto;
import it.csi.dma.dmaloginccebl.integration.LogGeneralDaoBean;
import it.csi.dma.dmaloginccebl.interfacews.authentication.Credenziali;
import it.csi.dma.dmaloginccebl.iride.data.*;

import java.rmi.RemoteException;

public interface AuthService {

	Identita autenticaWithPin(IrideRequest irideRequest)
			throws RemoteException, SystemException, AuthException, MalformedUsernameException,
			IdProviderNotFoundException, InternalException, UnrecoverableException;
	
	IdentitaCallDto autenticaWithPinCall(IrideRequest irideRequest)
			throws RemoteException, SystemException, AuthException, MalformedUsernameException,
			IdProviderNotFoundException, InternalException, UnrecoverableException;
	

	Identita autentica(IrideRequest irideRequest)
			throws RemoteException, SystemException, AuthException, MalformedUsernameException,
			IdProviderNotFoundException, InternalException, UnrecoverableException;
	
	
	IdentitaCallDto autenticaCall(IrideRequest irideRequest)
			throws RemoteException, SystemException, AuthException, MalformedUsernameException,
			IdProviderNotFoundException, InternalException, UnrecoverableException;

    public abstract Identita
	autenticaCil(String user, String pwd) throws java.rmi.RemoteException,
  																	  SystemException,
																	  AuthException,
																	  MalformedUsernameException,
																	  IdProviderNotFoundException,
																	  InternalException,
																	  UnrecoverableException;

	public abstract Object getSrvInterface();

	Identita autenticaMedico(Credenziali credenziali, ApplicazioneDto applicazione, LogGeneralDaoBean logGeneralDaoBean);
}