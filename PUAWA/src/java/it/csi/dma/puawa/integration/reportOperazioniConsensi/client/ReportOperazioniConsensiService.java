/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.integration.reportOperazioniConsensi.client;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(targetNamespace = "http://dmacc.csi.it/", name = "ReportOperazioniConsensiService")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface ReportOperazioniConsensiService {

	/**
	 *
	 * 
	 * @param parameters
	 * @return
	 */
	@WebMethod
	public @WebResult(targetNamespace = "http://dmacc.csi.it/") ReportOperazioniConsensiResponse getReportOperazioniConsensi(
			@WebParam(targetNamespace = "http://dmacc.csi.it/") ReportOperazioniConsensiRequest parameters);

}
