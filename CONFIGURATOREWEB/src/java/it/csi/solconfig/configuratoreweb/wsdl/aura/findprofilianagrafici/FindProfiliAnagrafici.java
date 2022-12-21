/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.solconfig.configuratoreweb.wsdl.aura.findprofilianagrafici;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
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
 *         &lt;element name="profiliRequest" type="{http://AnagrafeFind.central.services.auraws.aura.csi.it}findProfiliAnagraficiRequest" minOccurs="0"/>
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
    "profiliRequest"
})
@XmlRootElement(name = "FindProfiliAnagrafici")
public class FindProfiliAnagrafici {

    protected FindProfiliAnagraficiRequest profiliRequest;

    /**
     * Gets the value of the profiliRequest property.
     * 
     * @return
     *     possible object is
     *     {@link FindProfiliAnagraficiRequest }
     *     
     */
    public FindProfiliAnagraficiRequest getProfiliRequest() {
        return profiliRequest;
    }

    /**
     * Sets the value of the profiliRequest property.
     * 
     * @param value
     *     allowed object is
     *     {@link FindProfiliAnagraficiRequest }
     *     
     */
    public void setProfiliRequest(FindProfiliAnagraficiRequest value) {
        this.profiliRequest = value;
    }

}
