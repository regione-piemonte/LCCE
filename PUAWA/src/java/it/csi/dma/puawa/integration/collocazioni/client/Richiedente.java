/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.integration.collocazioni.client;

import javax.xml.bind.annotation.XmlType;

@XmlType(namespace = "http://dmac.csi.it/")
public class Richiedente {

	private String codiceFiscaleRichiedente;
	private String codiceRuoloRichiedente;
	private String applicazioneChiamante;
	private String ipChiamante;

	public String getCodiceFiscaleRichiedente() {
		return codiceFiscaleRichiedente;
	}

	public void setCodiceFiscaleRichiedente(String codiceFiscaleRichiedente) {
		this.codiceFiscaleRichiedente = codiceFiscaleRichiedente;
	}

	public String getCodiceRuoloRichiedente() {
		return codiceRuoloRichiedente;
	}

	public void setCodiceRuoloRichiedente(String codiceRuoloRichiedente) {
		this.codiceRuoloRichiedente = codiceRuoloRichiedente;
	}

	public String getApplicazioneChiamante() {
		return applicazioneChiamante;
	}

	public void setApplicazioneChiamante(String applicazioneChiamante) {
		this.applicazioneChiamante = applicazioneChiamante;
	}

	public String getIpChiamante() {
		return ipChiamante;
	}

	public void setIpChiamante(String ipChiamante) {
		this.ipChiamante = ipChiamante;
	}
}
