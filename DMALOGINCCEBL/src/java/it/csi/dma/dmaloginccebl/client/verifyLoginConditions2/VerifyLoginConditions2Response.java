/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.dma.dmaloginccebl.client.verifyLoginConditions2;


import it.csi.dma.dmaloginccebl.client.verifyLoginConditions.common.Errore;
import it.csi.dma.dmaloginccebl.client.verifyLoginConditions.common.ServiceResponse;
import it.csi.dma.dmaloginccebl.interfacews.msg.RisultatoCodice;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;


@XmlRootElement(name="verifyLoginConditions2Response")
@XmlAccessorType(XmlAccessType.FIELD)
public class VerifyLoginConditions2Response
    extends ServiceResponse {
	
	public VerifyLoginConditions2Response(List<Errore> errori, RisultatoCodice esito) {
		super(errori, esito);
	}
	public VerifyLoginConditions2Response() {
		super();
	}
	
}
