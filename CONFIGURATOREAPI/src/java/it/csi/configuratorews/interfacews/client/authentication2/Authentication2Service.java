/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.interfacews.client.authentication2;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.BindingType;

/**
 * 
 */
@WebService(targetNamespace = "http://dmacc.csi.it/", name = "Authentication2Service")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@BindingType(value = javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_MTOM_BINDING)
public interface Authentication2Service {

	@WebMethod(action = "http://dmaccbl.csi.it/getAuthentication2")
	@WebResult(name = "getAuthentication2Response", targetNamespace = "http://dmacc.csi.it/", partName = "getAuthentication2Response")
	GetAuthentication2Response getAuthentication2(
            @WebParam(partName = "getAuthentication2Request", name = "getAuthentication2Request", targetNamespace = "http://dmaccbl.csi.it/") GetAuthentication2Request parameters);
}
