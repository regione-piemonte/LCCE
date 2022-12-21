/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.interfacews.tokeninformation.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.BindingType;

/**
 * 
 */
@WebService(targetNamespace = "http://dmacc.csi.it/", name = "TokenInformationService")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@BindingType(value = javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_MTOM_BINDING)
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
