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
 * <p>Java class for DatiAnagraficiBody complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiAnagraficiBody">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="elencoProfili" type="{http://AnagrafeFind.central.services.auraws.aura.csi.it}ArrayOfdatianagraficiDatiAnagrafici" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiAnagraficiBody", propOrder = {
    "elencoProfili"
})
public class DatiAnagraficiBody {

    protected ArrayOfdatianagraficiDatiAnagrafici elencoProfili;

    /**
     * Gets the value of the elencoProfili property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfdatianagraficiDatiAnagrafici }
     *     
     */
    public ArrayOfdatianagraficiDatiAnagrafici getElencoProfili() {
        return elencoProfili;
    }

    /**
     * Sets the value of the elencoProfili property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfdatianagraficiDatiAnagrafici }
     *     
     */
    public void setElencoProfili(ArrayOfdatianagraficiDatiAnagrafici value) {
        this.elencoProfili = value;
    }

}