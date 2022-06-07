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

/**
 * Risposta di base di tutti i servizi. La risposta contiene anche le codifiche
 * che si trovano nel messaggio: - nel messaggio ci sarà solo il codice - nella
 * lista "codifiche" ci saranno le codifiche con le descrizioni usate
 * 
 * @author Alberto Lagna
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "errori")
public class Errori {
	@XmlElement(namespace = "http://dma.csi.it/")
	protected List<Errore> errore = new ArrayList<Errore>();

	public List<Errore> getErrore() {
		return errore;
	}

	public void setErrore(List<Errore> errore) {
		this.errore = errore;
	}

	public String toString() {
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
