/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.farmacia;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.BindingType;

@WebService(targetNamespace = "http://dmacc.csi.it/", name = "FarmaciaService")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@BindingType(value = javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_MTOM_BINDING)
public interface FarmaciaService {

	@WebMethod(action = "http://dmaccbl.csi.it/getFarmacieAderenti")
    @WebResult(name = "getFarmacieAderentiResponse", targetNamespace = "http://dmacc.csi.it/", partName = "getFarmacieAderentiResponse")
	GetFarmacieAderentiResponse getFarmacieAderenti(@WebParam(partName = "getFarmacieAderentiRequest", name = "getFarmacieAderentiRequest", targetNamespace = "http://dmaccbl.csi.it/")
	GetFarmacieAderentiRequest parameters);
	
	@WebMethod(action = "http://dmaccbl.csi.it/verificaFarmacista")
    @WebResult(name = "verificaFarmacistaResponse", targetNamespace = "http://dmacc.csi.it/", partName = "verificaFarmacistaResponse")
	VerificaFarmacistaResponse verificaFarmacista(@WebParam(partName = "verificaFarmacistaRequest", name = "verificaFarmacistaRequest", targetNamespace = "http://dmaccbl.csi.it/")
	VerificaFarmacistaRequest parameters);
}
