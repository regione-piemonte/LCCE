/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.interfacews.msg;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "serviceResponse")
public class ServiceResponse extends EnsResponse {

	@XmlElement(namespace = "http://dma.csi.it/")
	protected Errori errori;
	protected RisultatoCodice esito;
	@XmlElement(namespace = "http://dma.csi.it/")
	protected List<Codifica> codifiche = new ArrayList<Codifica>();

	public ServiceResponse() {
	}

	public ServiceResponse(List<Errore> errori, RisultatoCodice esito) {
		super();
		this.errori = new Errori();
		this.errori.setErrore(errori);
		this.esito = esito;
	}

	public ServiceResponse(List<Errore> errori, RisultatoCodice esito, List<Codifica> codifiche) {
		this(errori, esito);
		this.codifiche = codifiche;
	}

	public ServiceResponse(RisultatoCodice esito) {
		super();
		this.esito = esito;
	}

	public ServiceResponse(Errore errore) {
		this(null, RisultatoCodice.FALLIMENTO, null);
		this.errori = new Errori();
		this.errori.setErrore(new ArrayList<Errore>());
		this.errori.getErrore().add(errore);
	}

	public List<Errore> getErrori() {
		return errori != null ? errori.getErrore() : null;
	}

	public void setErrore(List<Errore> errori) {
		if (this.errori == null) {
			this.errori = new Errori();
		}
		this.errori.setErrore(errori);
	}

	public RisultatoCodice getEsito() {
		return esito;
	}

	public void setEsito(RisultatoCodice esito) {
		this.esito = esito;
	}

	public List<Codifica> getCodifiche() {
		return codifiche;
	}

	public void setCodifiche(List<Codifica> codifiche) {
		this.codifiche = codifiche;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}