/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.integration.notificheCittASR.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.BindingType;

/**
 * 
 */
@WebService(targetNamespace = "http://notificheasr.dma.csi.it/NotificheCittASR/", name = "NotificheCittASR")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@BindingType(value = javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_MTOM_BINDING)
public interface NotificheCittASRService {

	@WebMethod(action = "http://notificheasr.dma.csi.it/NotificheCittASR/notifica")
	@WebResult(name = "notificaMessaggiResponse", targetNamespace = "http://notificheasr.dma.csi.it/NotificheCittASR/", partName = "notificaMessaggiResponse")
	NotificaResponse notificaMessaggi(
			@WebParam(partName = "notificaMessaggiRequest", targetNamespace = "http://notificheasr.dma.csi.it/NotificheCittASR/", name = "notificaMessaggiRequest") NotificaRequest notificaMessaggiRequest);

}
