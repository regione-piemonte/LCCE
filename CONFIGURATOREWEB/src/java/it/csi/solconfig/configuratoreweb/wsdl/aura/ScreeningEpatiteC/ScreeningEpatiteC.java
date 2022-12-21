/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.solconfig.configuratoreweb.wsdl.aura.ScreeningEpatiteC;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="assistito" type="{http://ScreeningEpatiteC.central.services.auraws.aura.csi.it}Request"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "assistito"
})
@XmlRootElement(name = "ScreeningEpatiteC")
public class ScreeningEpatiteC {

    @XmlElement(required = true)
    protected Request assistito;

    /**
     * Gets the value of the assistito property.
     * 
     * @return
     *     possible object is
     *     {@link Request }
     *     
     */
    public Request getAssistito() {
        return assistito;
    }

    /**
     * Sets the value of the assistito property.
     * 
     * @param value
     *     allowed object is
     *     {@link Request }
     *     
     */
    public void setAssistito(Request value) {
        this.assistito = value;
    }

}
