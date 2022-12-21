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
 *         &lt;element name="AURAid" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
    "aurAid"
})
@XmlRootElement(name = "GetProfiloSanitario")
public class GetProfiloSanitario {

    @XmlElement(name = "AURAid")
    protected String aurAid;

    /**
     * Gets the value of the aurAid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAURAid() {
        return aurAid;
    }

    /**
     * Sets the value of the aurAid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAURAid(String value) {
        this.aurAid = value;
    }

}
