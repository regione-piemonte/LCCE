/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.interfacews.client.ruoliUtente;

import javax.xml.bind.annotation.XmlType;

@XmlType(namespace = "http://dmac.csi.it/")
public class Richiedente {

	private String CodiceFiscaleRichiedente;
	private String ApplicazioneChiamante;
	private String IpChiamante;

	public String getCodiceFiscaleRichiedente() {
		return CodiceFiscaleRichiedente;
	}

	public void setCodiceFiscaleRichiedente(String codiceFiscaleRichiedente) {
		CodiceFiscaleRichiedente = codiceFiscaleRichiedente;
	}

	public String getApplicazioneChiamante() {
		return ApplicazioneChiamante;
	}

	public void setApplicazioneChiamante(String applicazioneChiamante) {
		ApplicazioneChiamante = applicazioneChiamante;
	}

	public String getIpChiamante() {
		return IpChiamante;
	}

	public void setIpChiamante(String ipChiamante) {
		IpChiamante = ipChiamante;
	}
}