/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.integration.collocazioni.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.BindingType;

@WebService(targetNamespace = "http://dmacc.csi.it/", name = "CollocazioniService")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@BindingType(value = javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_MTOM_BINDING)
public interface CollocazioniService {

	@WebMethod(action = "http://dmaccbl.csi.it/getCollocazioni")
	@WebResult(name = "GetCollocazioniResponse", targetNamespace = "http://dmacc.csi.it/", partName = "GetCollocazioni")
	GetCollocazioniResponse GetCollocazioni(
			@WebParam(partName = "getCollocazioniRequest", name = "getCollocazioniRequest", targetNamespace = "http://dmaccbl.csi.it/") GetCollocazioniRequest parameters);
}
