/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.client.verifyLoginConditions;

import it.csi.dma.dmaloginccebl.client.verifyLoginConditions2.VerifyLoginConditions2Request;
import it.csi.dma.dmaloginccebl.client.verifyLoginConditions2.VerifyLoginConditions2Response;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(targetNamespace = "http://dmacc.csi.it/", name = "VerifyLoginConditionsService")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface VerifyLoginConditionsService {



    @WebMethod(action = "http://dmaccbl.csi.it/verifyLoginConditions")
    @WebResult(name = "verifyLoginConditionsResponse", targetNamespace = "http://dmacc.csi.it/", partName = "verifyLoginConditionsResponse")
    public VerifyLoginConditionsResponse verifyLoginConditions(
            @WebParam(partName = "verifyLoginConditionsRequest", name = "verifyLoginConditionsRequest", targetNamespace = "http://dmaccbl.csi.it/")
                    VerifyLoginConditionsRequest verifyLoginConditionsRequest
    );

    @WebMethod(action = "http://dmaccbl.csi.it/verifyLoginConditions2")
    @WebResult(name = "verifyLoginConditions2Response", targetNamespace = "http://dmacc.csi.it/", partName = "verifyLoginConditions2Response")
    public VerifyLoginConditions2Response verifyLoginConditions2(
            @WebParam(partName = "verifyLoginConditions2Request", name = "verifyLoginConditions2Request", targetNamespace = "http://dmaccbl.csi.it/")
                    VerifyLoginConditions2Request verifyLoginConditions2Request
    );
}
