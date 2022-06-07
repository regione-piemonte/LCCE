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
 * Java class for NotificaRequest complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="NotificaRequest">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codiceAzienda" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codiceFiscaleRichiedente" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="16"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="codiceFiscaleAssistito">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;length value="16"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="messaggioPush" type="{http://notificheasr.dma.csi.it/NotificheCittASR/}pushMessageType"/>
 *         &lt;element name="messaggioEmail" type="{http://notificheasr.dma.csi.it/NotificheCittASR/}emailMessageType"/>
 *         &lt;element name="messaggioSms" type="{http://notificheasr.dma.csi.it/NotificheCittASR/}smsMessageType"/>
 *         &lt;element name="messaggioSito" type="{http://notificheasr.dma.csi.it/NotificheCittASR/}mexMessageType"/>
 *         &lt;element name="parametriAggiuntivi" type="{http://notificheasr.dma.csi.it/NotificheCittASR/}parametriAggiuntiviType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlRootElement(name = "NotificaMessaggiRequest")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NotificaRequest", namespace = "http://notificheasr.dma.csi.it/NotificheCittASR/", propOrder = {
		"codiceAzienda", "codiceFiscaleRichiedente", "codiceFiscaleAssistito", "messaggioPush", "messaggioEmail",
		"messaggioSms", "messaggioSito", "parametriAggiuntivi" })
public class NotificaRequest {

	@XmlElement(required = true)
	protected String codiceAzienda;
	protected String codiceFiscaleRichiedente;
	@XmlElement(required = true)
	protected String codiceFiscaleAssistito;
	@XmlElement(required = true)
	protected PushMessageType messaggioPush;
	@XmlElement(required = true)
	protected EmailMessageType messaggioEmail;
	@XmlElement(required = true)
	protected SmsMessageType messaggioSms;
	@XmlElement(required = true)
	protected MexMessageType messaggioSito;
	protected ParametriAggiuntiviType parametriAggiuntivi;

	/**
	 * Gets the value of the codiceAzienda property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCodiceAzienda() {
		return codiceAzienda;
	}

	/**
	 * Sets the value of the codiceAzienda property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setCodiceAzienda(String value) {
		this.codiceAzienda = value;
	}

	/**
	 * Gets the value of the codiceFiscaleRichiedente property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCodiceFiscaleRichiedente() {
		return codiceFiscaleRichiedente;
	}

	/**
	 * Sets the value of the codiceFiscaleRichiedente property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setCodiceFiscaleRichiedente(String value) {
		this.codiceFiscaleRichiedente = value;
	}

	/**
	 * Gets the value of the codiceFiscaleAssistito property.
	 * 
	 * @return possible object is {@link String }
	 * 
	 */
	public String getCodiceFiscaleAssistito() {
		return codiceFiscaleAssistito;
	}

	/**
	 * Sets the value of the codiceFiscaleAssistito property.
	 * 
	 * @param value allowed object is {@link String }
	 * 
	 */
	public void setCodiceFiscaleAssistito(String value) {
		this.codiceFiscaleAssistito = value;
	}

	/**
	 * Gets the value of the messaggioPush property.
	 * 
	 * @return possible object is {@link PushMessageType }
	 * 
	 */
	public PushMessageType getMessaggioPush() {
		return messaggioPush;
	}

	/**
	 * Sets the value of the messaggioPush property.
	 * 
	 * @param value allowed object is {@link PushMessageType }
	 * 
	 */
	public void setMessaggioPush(PushMessageType value) {
		this.messaggioPush = value;
	}

	/**
	 * Gets the value of the messaggioEmail property.
	 * 
	 * @return possible object is {@link EmailMessageType }
	 * 
	 */
	public EmailMessageType getMessaggioEmail() {
		return messaggioEmail;
	}

	/**
	 * Sets the value of the messaggioEmail property.
	 * 
	 * @param value allowed object is {@link EmailMessageType }
	 * 
	 */
	public void setMessaggioEmail(EmailMessageType value) {
		this.messaggioEmail = value;
	}

	/**
	 * Gets the value of the messaggioSms property.
	 * 
	 * @return possible object is {@link SmsMessageType }
	 * 
	 */
	public SmsMessageType getMessaggioSms() {
		return messaggioSms;
	}

	/**
	 * Sets the value of the messaggioSms property.
	 * 
	 * @param value allowed object is {@link SmsMessageType }
	 * 
	 */
	public void setMessaggioSms(SmsMessageType value) {
		this.messaggioSms = value;
	}

	/**
	 * Gets the value of the messaggioSito property.
	 * 
	 * @return possible object is {@link MexMessageType }
	 * 
	 */
	public MexMessageType getMessaggioSito() {
		return messaggioSito;
	}

	/**
	 * Sets the value of the messaggioSito property.
	 * 
	 * @param value allowed object is {@link MexMessageType }
	 * 
	 */
	public void setMessaggioSito(MexMessageType value) {
		this.messaggioSito = value;
	}

	/**
	 * Gets the value of the parametriAggiuntivi property.
	 * 
	 * @return possible object is {@link ParametriAggiuntiviType }
	 * 
	 */
	public ParametriAggiuntiviType getParametriAggiuntivi() {
		return parametriAggiuntivi;
	}

	/**
	 * Sets the value of the parametriAggiuntivi property.
	 * 
	 * @param value allowed object is {@link ParametriAggiuntiviType }
	 * 
	 */
	public void setParametriAggiuntivi(ParametriAggiuntiviType value) {
		this.parametriAggiuntivi = value;
	}

}
