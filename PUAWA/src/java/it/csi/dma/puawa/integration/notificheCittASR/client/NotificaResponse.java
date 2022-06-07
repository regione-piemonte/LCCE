/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.integration.notificheCittASR.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for NotificaResponse complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="NotificaResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="esito" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="elencoErrori" type="{http://notificheasr.dma.csi.it/NotificheCittASR/}erroriArrayType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement(name = "NotificaMessaggiResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NotificaResponse", namespace = "http://notificheasr.dma.csi.it/NotificheCittASR/", propOrder = {
		"esito", "elencoErrori" })
public class NotificaResponse {

	@XmlElement(required = true)
	protected String esito;
	protected ErroriArrayType elencoErrori;

	/**
	 * Gets the value of the esito property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getEsito() {
		return esito;
	}

	/**
	 * Sets the value of the esito property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setEsito(String value) {
		this.esito = value;
	}

	/**
	 * Gets the value of the elencoErrori property.
	 * 
	 * @return possible object is {@link ErroriArrayType }
	 * 
	 */
	public ErroriArrayType getElencoErrori() {
		return elencoErrori;
	}

	/**
	 * Sets the value of the elencoErrori property.
	 * 
	 * @param value allowed object is {@link ErroriArrayType }
	 * 
	 */
	public void setElencoErrori(ErroriArrayType value) {
		this.elencoErrori = value;
	}

	public NotificaResponse() {
	}

	public NotificaResponse(String esito, ErroriArrayType elencoErrori) {
		this.esito = esito;
		this.elencoErrori = elencoErrori;
	}
}
