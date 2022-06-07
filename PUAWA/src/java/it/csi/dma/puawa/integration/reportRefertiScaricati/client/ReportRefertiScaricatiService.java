/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.integration.reportRefertiScaricati.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(targetNamespace = "http://dmacc.csi.it/", name = "ReportRefertiScaricatiService")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface ReportRefertiScaricatiService {

	@WebMethod
	public @WebResult(targetNamespace = "http://dmacc.csi.it/") ReportRefertiScaricatiResponse getReportRefertiScaricati(
			@WebParam(targetNamespace = "http://dmacc.csi.it/") ReportRefertiScaricatiRequest parameters);

}
