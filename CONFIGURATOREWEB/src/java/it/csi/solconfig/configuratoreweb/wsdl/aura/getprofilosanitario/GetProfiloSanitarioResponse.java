/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.solconfig.configuratoreweb.wsdl.aura.getprofilosanitario;

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
 *         &lt;element name="GetProfiloSanitarioResult" type="{http://AnagrafeSanitaria.central.services.auraws.aura.csi.it}soggettoAuraMsg"/>
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
    "getProfiloSanitarioResult"
})
@XmlRootElement(name = "GetProfiloSanitarioResponse")
public class GetProfiloSanitarioResponse {

    @XmlElement(name = "GetProfiloSanitarioResult", required = true)
    protected SoggettoAuraMsg getProfiloSanitarioResult;

    /**
     * Gets the value of the getProfiloSanitarioResult property.
     * 
     * @return
     *     possible object is
     *     {@link SoggettoAuraMsg }
     *     
     */
    public SoggettoAuraMsg getGetProfiloSanitarioResult() {
        return getProfiloSanitarioResult;
    }

    /**
     * Sets the value of the getProfiloSanitarioResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link SoggettoAuraMsg }
     *     
     */
    public void setGetProfiloSanitarioResult(SoggettoAuraMsg value) {
        this.getProfiloSanitarioResult = value;
    }

}
