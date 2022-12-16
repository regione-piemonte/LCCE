/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.authenticationconshibboleth;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.BindingType;

import org.apache.cxf.interceptor.InInterceptors;
import org.apache.cxf.interceptor.OutInterceptors;

/**
 * 
 */
@WebService(targetNamespace = "http://dmacc.csi.it/", name = "AuthenticationConShibbolethService")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@BindingType(value = javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_MTOM_BINDING)
@InInterceptors (interceptors = {"it.csi.dma.dmaloginccebl.cxf.interceptor.LogXmlInInterceptor" })
@OutInterceptors (interceptors = {"it.csi.dma.dmaloginccebl.cxf.interceptor.LogXmlOutInterceptor" })
public interface AuthenticationConShibbolethService {

	@WebMethod(action = "http://dmaccbl.csi.it/getAuthenticationConShibboleth")
    @WebResult(name = "getAuthenticationConShibbolethResponse", targetNamespace = "http://dmacc.csi.it/", partName = "getAuthenticationConShibbolethResponse")
    GetAuthenticationConShibbolethResponse getAuthenticationConShibboleth(
    		@WebParam(partName = "getAuthenticationConShibbolethRequest", name = "getAuthenticationConShibbolethRequest", targetNamespace = "http://dmaccbl.csi.it/")
    		GetAuthenticationConShibbolethRequest parameters);

}
