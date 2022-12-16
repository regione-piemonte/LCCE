/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.collocazioni;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.BindingType;

import org.apache.cxf.interceptor.InInterceptors;
import org.apache.cxf.interceptor.OutInterceptors;

@WebService(targetNamespace = "http://dmacc.csi.it/", name="CollocazioniService")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@BindingType(value = javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_MTOM_BINDING)
@InInterceptors (interceptors = {"it.csi.dma.dmaloginccebl.cxf.interceptor.LogXmlInInterceptor" })
@OutInterceptors (interceptors = {"it.csi.dma.dmaloginccebl.cxf.interceptor.LogXmlOutInterceptor" })
public interface CollocazioniService {

	@WebMethod(action = "http://dmaccbl.csi.it/getCollocazioni")
    @WebResult(name = "GetCollocazioniResponse", targetNamespace = "http://dmacc.csi.it/", partName = "GetCollocazioni")
	GetCollocazioniResponse GetCollocazioni(
			@WebParam(partName = "getCollocazioniRequest", name = "getCollocazioniRequest", targetNamespace = "http://dmaccbl.csi.it/")
			GetCollocazioniRequest parameters);
}
