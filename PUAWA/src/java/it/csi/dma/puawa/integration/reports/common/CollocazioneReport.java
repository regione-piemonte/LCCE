/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.integration.reports.common;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlRootElement()
public class CollocazioneReport implements Serializable {

	private static final long serialVersionUID = 6123012386525924653L;

	protected String codiceAzienda;
	protected String codiceCollocazione;
	protected String descrizioneCollocazione;

	@XmlElement(namespace = "http://dma.csi.it/")
	public String getCodiceAzienda() {
		return codiceAzienda;
	}

	public void setCodiceAzienda(String codiceAzienda) {
		this.codiceAzienda = codiceAzienda;
	}

	@XmlElement(namespace = "http://dma.csi.it/")
	public String getCodiceCollocazione() {
		return codiceCollocazione;
	}

	public void setCodiceCollocazione(String codiceCollocazione) {
		this.codiceCollocazione = codiceCollocazione;
	}

	@XmlElement(namespace = "http://dma.csi.it/")
	public String getDescrizioneCollocazione() {
		return descrizioneCollocazione;
	}

	public void setDescrizioneCollocazione(String descrizioneCollocazione) {
		this.descrizioneCollocazione = descrizioneCollocazione;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
