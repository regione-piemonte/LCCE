/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.integration.abilitazioni.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.BindingType;

@WebService(targetNamespace = "http://dmacc.csi.it/", name = "AbilitazioneService")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@BindingType(value = javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_MTOM_BINDING)
public interface AbilitazioneService {

	@WebMethod(action = "http://dmaccbl.csi.it/getAbilitazioni")
	@WebResult(name = "getAbilitazioniResponse", targetNamespace = "http://dmacc.csi.it/", partName = "getAbilitazioniResponse")
	GetAbilitazioniResponse getAbilitazioni(
			@WebParam(partName = "getAbilitazioniRequest", name = "getAbilitazioniRequest", targetNamespace = "http://dmaccbl.csi.it/") GetAbilitazioniRequest parameters);
}
