/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.interfacews.client.base;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

/**
 * Paziente
 * 
 * @author Alberto Lagna
 * @version $Id: $
 */
@XmlRootElement()
@XmlType(namespace = "http://dma.csi.it/")
public class ParametriLogin implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String codice;
	protected String valore;

	public String getCodice() {
		return codice;
	}

	public void setCodice(String codice) {
		this.codice = codice;
	}

	public String getValore() {
		return valore;
	}

	public void setValore(String valore) {
		this.valore = valore;
	}

}
