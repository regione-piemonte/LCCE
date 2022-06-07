/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.dma.dmaloginccebl.iride.client;


import it.csi.dma.dmaloginccebl.business.dao.dto.IdentitaCallDto;
import it.csi.dma.dmaloginccebl.iride.data.*;

public interface PolicyEnforcerBaseService extends java.rmi.Remote {
	public Identita identificaUserPassword(String in0, String in1)
			throws java.rmi.RemoteException, SystemException, AuthException, MalformedUsernameException,
			IdProviderNotFoundException, InternalException, UnrecoverableException;
	
	public IdentitaCallDto
	identificaUserPasswordCallDto(String in0, String in1)
			throws java.rmi.RemoteException, SystemException, AuthException, MalformedUsernameException,
			IdProviderNotFoundException, InternalException, UnrecoverableException;


	public Identita identificaCertificato(byte[] in0)
			throws java.rmi.RemoteException, SystemException, CertRevokedException, IdProviderNotFoundException,
			InternalException, CertOutsideValidityException, UnrecoverableException;

	public Identita identificaUserPasswordPIN(String in0, String in1, String in2)
			throws java.rmi.RemoteException, SystemException, AuthException, MalformedUsernameException,
			IdProviderNotFoundException, InternalException, UnrecoverableException;

	public IdentitaCallDto identificaUserPasswordPINCall(String in0, String in1, String in2)
			throws java.rmi.RemoteException, SystemException, AuthException, MalformedUsernameException,
			IdProviderNotFoundException, InternalException, UnrecoverableException;
	
	
	public boolean isPersonaAutorizzataInUseCase(Identita in0, UseCase in1)
			throws java.rmi.RemoteException, SystemException, NoSuchUseCaseException, NoSuchApplicationException,
			IdentitaNonAutenticaException, InternalException, UnrecoverableException;

	public UseCase[] findUseCasesForPersonaInApplication(Identita in0, Application in1)
			throws java.rmi.RemoteException, SystemException, NoSuchApplicationException, IdentitaNonAutenticaException,
			InternalException, UnrecoverableException;

	public boolean isIdentitaAutentica(Identita in0)
			throws java.rmi.RemoteException, SystemException, InternalException, UnrecoverableException;

	public String getInfoPersonaInUseCase(Identita in0, UseCase in1)
			throws java.rmi.RemoteException, SystemException, NoSuchUseCaseException, NoSuchApplicationException,
			IdentitaNonAutenticaException, InternalException, UnrecoverableException;

	public Ruolo[] findRuoliForPersonaInUseCase(Identita in0, UseCase in1)
			throws java.rmi.RemoteException, SystemException, NoSuchUseCaseException, NoSuchApplicationException,
			IdentitaNonAutenticaException, InternalException, UnrecoverableException;

	public Ruolo[] findRuoliForPersonaInApplication(Identita in0, Application in1)
			throws java.rmi.RemoteException, SystemException, NoSuchApplicationException, IdentitaNonAutenticaException,
			InternalException, UnrecoverableException;

	public String getInfoPersonaSchema(Ruolo in0) throws java.rmi.RemoteException, SystemException,
			BadRuoloException, InternalException, UnrecoverableException;

	public Actor[] findActorsForPersonaInApplication(Identita in0, Application in1)
			throws java.rmi.RemoteException, SystemException, NoSuchApplicationException, IdentitaNonAutenticaException,
			InternalException, UnrecoverableException;

	public Actor[] findActorsForPersonaInUseCase(Identita in0, UseCase in1)
			throws java.rmi.RemoteException, SystemException, NoSuchUseCaseException, NoSuchApplicationException,
			IdentitaNonAutenticaException, InternalException, UnrecoverableException;

	public boolean isPersonaInRuolo(Identita in0, Ruolo in1) throws java.rmi.RemoteException, SystemException,
			BadRuoloException, IdentitaNonAutenticaException, InternalException, UnrecoverableException;
}
