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
 * <p>Java class for GiornoApertura complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="GiornoApertura">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="giorno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="orari" type="{http://opessan.opessanws.def.csi.it/}FasceOrarie" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GiornoApertura", propOrder = {
    "giorno",
    "orari"
})
public class GiornoApertura {

    protected String giorno;
    protected FasceOrarie orari;

    /**
     * Gets the value of the giorno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGiorno() {
        return giorno;
    }

    /**
     * Sets the value of the giorno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGiorno(String value) {
        this.giorno = value;
    }

    /**
     * Gets the value of the orari property.
     * 
     * @return
     *     possible object is
     *     {@link FasceOrarie }
     *     
     */
    public FasceOrarie getOrari() {
        return orari;
    }

    /**
     * Sets the value of the orari property.
     * 
     * @param value
     *     allowed object is
     *     {@link FasceOrarie }
     *     
     */
    public void setOrari(FasceOrarie value) {
        this.orari = value;
    }

}
