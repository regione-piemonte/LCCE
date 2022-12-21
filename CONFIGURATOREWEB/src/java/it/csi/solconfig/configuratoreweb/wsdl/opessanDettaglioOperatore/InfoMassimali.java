/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for InfoMassimali complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InfoMassimali">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="massimale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="massInDeroga" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="massScelteTemp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="massScelta03anni" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataVariazione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InfoMassimali", propOrder = {
    "massimale",
    "massInDeroga",
    "massScelteTemp",
    "massScelta03Anni",
    "dataVariazione"
})
public class InfoMassimali {

    @XmlElement(required = true)
    protected String massimale;
    @XmlElement(required = true)
    protected String massInDeroga;
    protected String massScelteTemp;
    @XmlElement(name = "massScelta03anni")
    protected String massScelta03Anni;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataVariazione;

    /**
     * Gets the value of the massimale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMassimale() {
        return massimale;
    }

    /**
     * Sets the value of the massimale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMassimale(String value) {
        this.massimale = value;
    }

    /**
     * Gets the value of the massInDeroga property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMassInDeroga() {
        return massInDeroga;
    }

    /**
     * Sets the value of the massInDeroga property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMassInDeroga(String value) {
        this.massInDeroga = value;
    }

    /**
     * Gets the value of the massScelteTemp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMassScelteTemp() {
        return massScelteTemp;
    }

    /**
     * Sets the value of the massScelteTemp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMassScelteTemp(String value) {
        this.massScelteTemp = value;
    }

    /**
     * Gets the value of the massScelta03Anni property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMassScelta03Anni() {
        return massScelta03Anni;
    }

    /**
     * Sets the value of the massScelta03Anni property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMassScelta03Anni(String value) {
        this.massScelta03Anni = value;
    }

    /**
     * Gets the value of the dataVariazione property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataVariazione() {
        return dataVariazione;
    }

    /**
     * Sets the value of the dataVariazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataVariazione(XMLGregorianCalendar value) {
        this.dataVariazione = value;
    }

}
