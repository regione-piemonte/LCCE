/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.farmacia;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.csi.dma.dmaloginccebl.interfacews.msg.Errore;
import it.csi.dma.dmaloginccebl.interfacews.msg.RisultatoCodice;
import it.csi.dma.dmaloginccebl.interfacews.msg.ServiceResponse;

@XmlRootElement(name="verificaFarmacistaResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "verificaFarmacistaResponse")
public class VerificaFarmacistaResponse extends ServiceResponse{
	
	public VerificaFarmacistaResponse(List<Errore> errori, RisultatoCodice esito) {
		super(errori, esito);
	}

	public VerificaFarmacistaResponse(RisultatoCodice esito) {
		super(esito);
	}

	public VerificaFarmacistaResponse() {
		super();
	}
}
