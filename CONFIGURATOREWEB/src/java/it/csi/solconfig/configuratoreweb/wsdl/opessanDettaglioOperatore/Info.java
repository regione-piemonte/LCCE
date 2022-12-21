/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Info complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Info">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codInformazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descInformazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="valInformazione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Info", propOrder = {
    "codInformazione",
    "descInformazione",
    "valInformazione"
})
public class Info {

    protected String codInformazione;
    protected String descInformazione;
    protected String valInformazione;

    /**
     * Gets the value of the codInformazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodInformazione() {
        return codInformazione;
    }

    /**
     * Sets the value of the codInformazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodInformazione(String value) {
        this.codInformazione = value;
    }

    /**
     * Gets the value of the descInformazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescInformazione() {
        return descInformazione;
    }

    /**
     * Sets the value of the descInformazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescInformazione(String value) {
        this.descInformazione = value;
    }

    /**
     * Gets the value of the valInformazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValInformazione() {
        return valInformazione;
    }

    /**
     * Sets the value of the valInformazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValInformazione(String value) {
        this.valInformazione = value;
    }

}
