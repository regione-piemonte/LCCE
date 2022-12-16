/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.ruoliUtente;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.BindingType;

import org.apache.cxf.interceptor.InInterceptors;
import org.apache.cxf.interceptor.OutInterceptors;

@WebService(targetNamespace = "http://dmacc.csi.it/", name = "RuoliUtenteService")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@BindingType(value = javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_MTOM_BINDING)
@InInterceptors (interceptors = {"it.csi.dma.dmaloginccebl.cxf.interceptor.LogXmlInInterceptor" })
@OutInterceptors (interceptors = {"it.csi.dma.dmaloginccebl.cxf.interceptor.LogXmlOutInterceptor" })
public interface RuoliUtenteService {

	@WebMethod(action = "http://dmaccbl.csi.it/getRuoliUtente")
    @WebResult(name = "GetRuoliUtenteResponse", targetNamespace = "http://dmacc.csi.it/", partName = "GetRuoliUtenteResponse")
	GetRuoliUtenteResponse getRuoliUtente(
			@WebParam(partName = "getRuoliUtenteRequest", name = "getRuoliUtenteRequest", targetNamespace = "http://dmaccbl.csi.it/")
			GetRuoliUtenteRequest parameters);
}
