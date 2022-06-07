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
 * <p>Java class for erroreType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="erroreType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codEsito" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="esito" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tipoErrore" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "erroreType", namespace = "http://notificheasr.dma.csi.it/NotificheCittASR/", propOrder = {
    "codEsito",
    "esito",
    "tipoErrore"
})
public class ErroreType {

    @XmlElement(required = true)
    protected String codEsito;
    @XmlElement(required = true)
    protected String esito;
    @XmlElement(required = true)
    protected String tipoErrore;

    /**
     * Gets the value of the codEsito property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodEsito() {
        return codEsito;
    }

    /**
     * Sets the value of the codEsito property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodEsito(String value) {
        this.codEsito = value;
    }

    /**
     * Gets the value of the esito property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsito() {
        return esito;
    }

    /**
     * Sets the value of the esito property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsito(String value) {
        this.esito = value;
    }

    /**
     * Gets the value of the tipoErrore property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoErrore() {
        return tipoErrore;
    }

    /**
     * Sets the value of the tipoErrore property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoErrore(String value) {
        this.tipoErrore = value;
    }

}
