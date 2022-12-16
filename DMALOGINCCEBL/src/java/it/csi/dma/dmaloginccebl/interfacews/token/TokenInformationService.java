/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.token;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.BindingType;

import org.apache.cxf.interceptor.InInterceptors;
import org.apache.cxf.interceptor.OutInterceptors;

import it.csi.dma.dmaloginccebl.interfacews.token2.GetTokenInformation2Request;
import it.csi.dma.dmaloginccebl.interfacews.token2.GetTokenInformation2Response;

/**
 * 
 */
@WebService(targetNamespace = "http://dmacc.csi.it/", name = "TokenInformationService")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@BindingType(value = javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_MTOM_BINDING)
@InInterceptors (interceptors = {"it.csi.dma.dmaloginccebl.cxf.interceptor.LogXmlInInterceptor" })
@OutInterceptors (interceptors = {"it.csi.dma.dmaloginccebl.cxf.interceptor.LogXmlOutInterceptor" })
public interface TokenInformationService {

	@WebMethod(action = "http://dmaccbl.csi.it/getTokenInformation")
    @WebResult(name = "getTokenInformationResponse", targetNamespace = "http://dmacc.csi.it/", partName = "getTokenInformationResponse")
	GetTokenInformationResponse getTokenInformation(
    		@WebParam(partName = "getTokenInformationRequest", name = "getTokenInformationRequest", targetNamespace = "http://dmaccbl.csi.it/")
    		GetTokenInformationRequest parameters);

	@WebMethod(action = "http://dmaccbl.csi.it/getTokenInformation2")
	@WebResult(name = "getTokenInformation2Response", targetNamespace = "http://dmacc.csi.it/", partName = "getTokenInformation2Response")
	GetTokenInformation2Response getTokenInformation2(
			@WebParam(partName = "getTokenInformation2Request", name = "getTokenInformation2Request", targetNamespace = "http://dmaccbl.csi.it/")
					GetTokenInformation2Request parameters);
}
