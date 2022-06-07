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
 * Java class for smsMessageType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="smsMessageType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="contenuto">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;minLength value="10"/>
 *               &lt;maxLength value="159"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "smsMessageType", namespace = "http://notificheasr.dma.csi.it/NotificheCittASR/", propOrder = {
		"contenuto" })
public class SmsMessageType {

	@XmlElement(required = true)
	protected String contenuto;

	/**
	 * Gets the value of the contenuto property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getContenuto() {
		return contenuto;
	}

	/**
	 * Sets the value of the contenuto property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setContenuto(String value) {
		this.contenuto = value;
	}

}
