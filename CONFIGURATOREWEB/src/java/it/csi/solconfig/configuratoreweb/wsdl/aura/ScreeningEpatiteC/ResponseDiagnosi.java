/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.solconfig.configuratoreweb.wsdl.aura.ScreeningEpatiteC;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ResponseDiagnosi complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ResponseDiagnosi">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ScreeningEpatiteC.central.services.auraws.aura.csi.it}Ens_Response">
 *       &lt;sequence>
 *         &lt;element name="codDiagnosi" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="diagnosi" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ResponseDiagnosi", namespace = "http://RicercaAssistitoCUP.central.services.auraws.aura.csi.it", propOrder = {
    "codDiagnosi",
    "diagnosi"
})
public class ResponseDiagnosi
    extends EnsResponse
{

    @XmlElement(required = true)
    protected String codDiagnosi;
    @XmlElement(required = true)
    protected String diagnosi;

    /**
     * Gets the value of the codDiagnosi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodDiagnosi() {
        return codDiagnosi;
    }

    /**
     * Sets the value of the codDiagnosi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodDiagnosi(String value) {
        this.codDiagnosi = value;
    }

    /**
     * Gets the value of the diagnosi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiagnosi() {
        return diagnosi;
    }

    /**
     * Sets the value of the diagnosi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiagnosi(String value) {
        this.diagnosi = value;
    }

}
