/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.authentication;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.BindingType;

/**
 * 
 */
@WebService(targetNamespace = "http://dmacc.csi.it/", name = "AuthenticationService")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@BindingType(value = javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_MTOM_BINDING)
public interface AuthenticationService {

	@WebMethod(action = "http://dmaccbl.csi.it/getAuthentication")
    @WebResult(name = "getAuthenticationResponse", targetNamespace = "http://dmacc.csi.it/", partName = "getAuthenticationResponse")
    GetAuthenticationResponse getAuthentication(
    		@WebParam(partName = "getAuthenticationRequest", name = "getAuthenticationRequest", targetNamespace = "http://dmaccbl.csi.it/")
    		GetAuthenticationRequest parameters);

}
