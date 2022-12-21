/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.solconfig.configuratoreweb.wsdl.aura.findprofilianagrafici;

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
 *         &lt;element name="FindProfiliAnagraficiResult" type="{http://AnagrafeFind.central.services.auraws.aura.csi.it}datiAnagraficiMsg"/>
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
    "findProfiliAnagraficiResult"
})
@XmlRootElement(name = "FindProfiliAnagraficiResponse")
public class FindProfiliAnagraficiResponse {

    @XmlElement(name = "FindProfiliAnagraficiResult", required = true)
    protected DatiAnagraficiMsg findProfiliAnagraficiResult;

    /**
     * Gets the value of the findProfiliAnagraficiResult property.
     * 
     * @return
     *     possible object is
     *     {@link DatiAnagraficiMsg }
     *     
     */
    public DatiAnagraficiMsg getFindProfiliAnagraficiResult() {
        return findProfiliAnagraficiResult;
    }

    /**
     * Sets the value of the findProfiliAnagraficiResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiAnagraficiMsg }
     *     
     */
    public void setFindProfiliAnagraficiResult(DatiAnagraficiMsg value) {
        this.findProfiliAnagraficiResult = value;
    }

}
