/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.interfacews.client.ruoliUtente;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.BindingType;

@WebService(targetNamespace = "http://dmacc.csi.it/", name = "RuoliUtenteService")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@BindingType(value = javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_MTOM_BINDING)
public interface RuoliUtenteService {

	@WebMethod(action = "http://dmaccbl.csi.it/getRuoliUtente")
	@WebResult(name = "GetRuoliUtenteResponse", targetNamespace = "http://dmacc.csi.it/", partName = "GetRuoliUtenteResponse")
	GetRuoliUtenteResponse getRuoliUtente(
            @WebParam(partName = "getRuoliUtenteRequest", name = "getRuoliUtenteRequest", targetNamespace = "http://dmaccbl.csi.it/") GetRuoliUtenteRequest parameters);
}
