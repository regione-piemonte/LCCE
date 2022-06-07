/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.msg;

import javax.xml.bind.annotation.XmlElement;

public class Indirizzo {

	
	String descrizioneIndirizzo;
	
	String cap;
	
	String provincia;
	
	String comune;

	public String getDescrizioneIndirizzo() {
		return descrizioneIndirizzo;
	}

	public void setDescrizioneIndirizzo(String descrizioneIndirizzo) {
		this.descrizioneIndirizzo = descrizioneIndirizzo;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}
	
	
}
