/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InfoAnagrafiche complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InfoAnagrafiche">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="datiPrimari" type="{http://opessan.opessanws.def.csi.it/}DatiPrimari"/>
 *         &lt;element name="domicilio" type="{http://opessan.opessanws.def.csi.it/}DatiSecondari" minOccurs="0"/>
 *         &lt;element name="residenza" type="{http://opessan.opessanws.def.csi.it/}DatiSecondari"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InfoAnagrafiche", propOrder = {
    "datiPrimari",
    "domicilio",
    "residenza"
})
public class InfoAnagrafiche {

    @XmlElement(required = true)
    protected DatiPrimari datiPrimari;
    protected DatiSecondari domicilio;
    @XmlElement(required = true)
    protected DatiSecondari residenza;

    /**
     * Gets the value of the datiPrimari property.
     * 
     * @return
     *     possible object is
     *     {@link DatiPrimari }
     *     
     */
    public DatiPrimari getDatiPrimari() {
        return datiPrimari;
    }

    /**
     * Sets the value of the datiPrimari property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiPrimari }
     *     
     */
    public void setDatiPrimari(DatiPrimari value) {
        this.datiPrimari = value;
    }

    /**
     * Gets the value of the domicilio property.
     * 
     * @return
     *     possible object is
     *     {@link DatiSecondari }
     *     
     */
    public DatiSecondari getDomicilio() {
        return domicilio;
    }

    /**
     * Sets the value of the domicilio property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiSecondari }
     *     
     */
    public void setDomicilio(DatiSecondari value) {
        this.domicilio = value;
    }

    /**
     * Gets the value of the residenza property.
     * 
     * @return
     *     possible object is
     *     {@link DatiSecondari }
     *     
     */
    public DatiSecondari getResidenza() {
        return residenza;
    }

    /**
     * Sets the value of the residenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link DatiSecondari }
     *     
     */
    public void setResidenza(DatiSecondari value) {
        this.residenza = value;
    }

}
