/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.client.verifyLoginConditions.common;


import it.csi.dma.dmaloginccebl.interfacews.msg.EnsResponse;
import it.csi.dma.dmaloginccebl.interfacews.msg.RisultatoCodice;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;

/**
 * Risposta di base di tutti i servizi.
 * La risposta contiene anche le codifiche che si trovano nel messaggio:
 * - nel messaggio ci sar� solo il codice
 * - nella lista "codifiche" ci saranno le codifiche con le descrizioni usate
 * 
 * @author Alberto Lagna
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "serviceResponse")
public class ServiceResponse extends EnsResponse {

	@XmlElement(namespace="http://dma.csi.it/")
	protected List<Errore> errori = new ArrayList<Errore>();
	protected RisultatoCodice esito;
	@XmlElement(namespace="http://dma.csi.it/")
	protected List<Codifica> codifiche = new ArrayList<Codifica>();

    public ServiceResponse(){}

	public ServiceResponse(List<Errore> errori, RisultatoCodice esito) {
		super();
		this.errori = errori;
		this.esito = esito;
	}

	public ServiceResponse(List<Errore> errori, RisultatoCodice esito, List<Codifica> codifiche) {
		this(errori, esito);
		this.codifiche = codifiche;
	}

	public ServiceResponse(Errore errore) {
        this(null, RisultatoCodice.FALLIMENTO, null);
        errori = new ArrayList<Errore>();
        errori.add(errore);
    }

	public List<Errore> getErrori() {
		return errori;
	}
	public void setErrore(List<Errore> errori) {
		this.errori = errori;
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
	
	public String toString(){
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
    
}
