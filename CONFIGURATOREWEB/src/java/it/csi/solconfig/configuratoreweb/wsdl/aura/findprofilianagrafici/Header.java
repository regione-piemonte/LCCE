/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.solconfig.configuratoreweb.wsdl.aura.findprofilianagrafici;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Header complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Header">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="actors" type="{http://AnagrafeFind.central.services.auraws.aura.csi.it}ArrayOfactorActor" minOccurs="0"/>
 *         &lt;element name="codiceRitorno" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Header", propOrder = {
    "actors",
    "codiceRitorno"
})
public class Header {

    protected ArrayOfactorActor actors;
    protected String codiceRitorno;

    /**
     * Gets the value of the actors property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfactorActor }
     *     
     */
    public ArrayOfactorActor getActors() {
        return actors;
    }

    /**
     * Sets the value of the actors property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfactorActor }
     *     
     */
    public void setActors(ArrayOfactorActor value) {
        this.actors = value;
    }

    /**
     * Gets the value of the codiceRitorno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceRitorno() {
        return codiceRitorno;
    }

    /**
     * Sets the value of the codiceRitorno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceRitorno(String value) {
        this.codiceRitorno = value;
    }

}
