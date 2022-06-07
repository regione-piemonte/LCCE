/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.integration.notificheCittASR.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for emailMessageType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="emailMessageType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="oggetto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="testo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "emailMessageType", namespace = "http://notificheasr.dma.csi.it/NotificheCittASR/", propOrder = {
		"oggetto", "testo" })
public class EmailMessageType {

	@XmlElement(required = true)
	protected String oggetto;
	@XmlElement(required = true)
	protected String testo;

	/**
	 * Gets the value of the oggetto property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getOggetto() {
		return oggetto;
	}

	/**
	 * Sets the value of the oggetto property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setOggetto(String value) {
		this.oggetto = value;
	}

	/**
	 * Gets the value of the testo property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getTesto() {
		return testo;
	}

	/**
	 * Sets the value of the testo property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setTesto(String value) {
		this.testo = value;
	}

}
