/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.integration.reports.common;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlRootElement()
@XmlType(namespace = "http://dma.csi.it/")
public class Richiedente implements Serializable {

	private static final long serialVersionUID = 6123012386525924653L;

	protected ApplicazioneRichiedente applicazione;
	protected String codiceFiscale;
	protected String numeroTransazione;
	protected RegimeDMA regime;
	protected RuoloDMA ruolo;
	protected String tokenOperazione;

	@XmlElement(namespace = "http://dma.csi.it/")
	public ApplicazioneRichiedente getApplicazione() {
		return applicazione;
	}

	public void setApplicazione(ApplicazioneRichiedente applicazione) {
		this.applicazione = applicazione;
	}

	@XmlElement(namespace = "http://dma.csi.it/")
	public String getCodiceFiscale() {
		return codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	@XmlElement(namespace = "http://dma.csi.it/")
	public String getNumeroTransazione() {
		return numeroTransazione;
	}

	public void setNumeroTransazione(String numeroTransazione) {
		this.numeroTransazione = numeroTransazione;
	}

	@XmlElement(namespace = "http://dma.csi.it/")
	public RegimeDMA getRegime() {
		return regime;
	}

	public void setRegime(RegimeDMA regime) {
		this.regime = regime;
	}

	@XmlElement(namespace = "http://dma.csi.it/")
	public RuoloDMA getRuolo() {
		return ruolo;
	}

	public void setRuolo(RuoloDMA ruolo) {
		this.ruolo = ruolo;
	}

	@XmlElement(namespace = "http://dma.csi.it/")
	public String getTokenOperazione() {
		return tokenOperazione;
	}

	public void setTokenOperazione(String tokenOperazione) {
		this.tokenOperazione = tokenOperazione;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
