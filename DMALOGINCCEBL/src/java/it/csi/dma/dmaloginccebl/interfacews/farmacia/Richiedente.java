/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.farmacia;

public class Richiedente {

	String ip;

	String uuid;
	
	String applicazioneChiamante;
	
	String codiceFiscaleRichiedente;
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String sessione) {
		this.uuid = sessione;
	}

	public String getApplicazioneChiamante() {
		return applicazioneChiamante;
	}

	public void setApplicazioneChiamante(String applicazioneChiamante) {
		this.applicazioneChiamante = applicazioneChiamante;
	}

	public String getCodiceFiscaleRichiedente() {
		return codiceFiscaleRichiedente;
	}

	public void setCodiceFiscaleRichiedente(String codiceFiscaleRichiedente) {
		this.codiceFiscaleRichiedente = codiceFiscaleRichiedente;
	}
	
}
