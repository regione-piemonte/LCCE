/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.integration.notificheCittASR.client;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for erroriArrayType complex type.
 * 
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 * 
 * <pre>
 * &lt;complexType name="erroriArrayType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="errore" type="{http://notificheasr.dma.csi.it/NotificheCittASR/}erroreType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "erroriArrayType", namespace = "http://notificheasr.dma.csi.it/NotificheCittASR/", propOrder = {
		"errore" })
public class ErroriArrayType {

	@XmlElement(required = true)
	protected List<ErroreType> errore;

	/**
	 * Gets the value of the errore property.
	 * 
	 * <p>
	 * This accessor method returns a reference to the live list, not a snapshot.
	 * Therefore any modification you make to the returned list will be present
	 * inside the JAXB object. This is why there is not a <CODE>set</CODE> method
	 * for the errore property.
	 * 
	 * <p>
	 * For example, to add a new item, do as follows:
	 * 
	 * <pre>
	 * getErrore().add(newItem);
	 * </pre>
	 * 
	 * 
	 * <p>
	 * Objects of the following type(s) are allowed in the list {@link ErroreType }
	 * 
	 * 
	 */
	public List<ErroreType> getErrore() {
		if (errore == null) {
			errore = new ArrayList<ErroreType>();
		}
		return this.errore;
	}

	public void setErrore(List<ErroreType> errore) {
		this.errore = errore;
	}

	public ErroriArrayType() {
	}

	public ErroriArrayType(List<ErroreType> errore) {
		this.errore = errore;
	}
}
