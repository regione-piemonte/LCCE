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
 * <p>Java class for parametroType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="parametroType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codice" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="valore" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "parametroType", namespace = "http://notificheasr.dma.csi.it/NotificheCittASR/", propOrder = {
    "codice",
    "valore"
})
public class ParametroType {

    @XmlElement(required = true)
    protected String codice;
    @XmlElement(required = true)
    protected String valore;

    /**
     * Gets the value of the codice property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodice() {
        return codice;
    }

    /**
     * Sets the value of the codice property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodice(String value) {
        this.codice = value;
    }

    /**
     * Gets the value of the valore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValore() {
        return valore;
    }

    /**
     * Sets the value of the valore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValore(String value) {
        this.valore = value;
    }

}
