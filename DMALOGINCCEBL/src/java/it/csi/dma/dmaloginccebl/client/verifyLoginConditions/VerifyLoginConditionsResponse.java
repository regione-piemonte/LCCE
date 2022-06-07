/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.dma.dmaloginccebl.client.verifyLoginConditions;


import it.csi.dma.dmaloginccebl.client.verifyLoginConditions.common.Errore;
import it.csi.dma.dmaloginccebl.client.verifyLoginConditions.common.ServiceResponse;
import it.csi.dma.dmaloginccebl.interfacews.msg.RisultatoCodice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement(name="verifyLoginConditionsResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class VerifyLoginConditionsResponse
    extends ServiceResponse {
	
	public VerifyLoginConditionsResponse(List<Errore> errori, RisultatoCodice esito) {
		super(errori, esito);
	}
	public VerifyLoginConditionsResponse() {
		super();
	}
	
}
