/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.solconfig.configuratoreweb.wsdl.aura.getprofilosanitario;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for soggettoAuraMsg complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="soggettoAuraMsg">
 *   &lt;complexContent>
 *     &lt;extension base="{http://AnagrafeSanitaria.central.services.auraws.aura.csi.it}Ens_Response">
 *       &lt;sequence>
 *         &lt;element name="header" type="{http://AnagrafeSanitaria.central.services.auraws.aura.csi.it}Header" minOccurs="0"/>
 *         &lt;element name="body" type="{http://AnagrafeSanitaria.central.services.auraws.aura.csi.it}SoggettoAuraBody" minOccurs="0"/>
 *         &lt;element name="footer" type="{http://AnagrafeSanitaria.central.services.auraws.aura.csi.it}Footer" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "soggettoAuraMsg", propOrder = {
    "header",
    "body",
    "footer"
})
public class SoggettoAuraMsg
    extends EnsResponse
{

    protected Header header;
    protected SoggettoAuraBody body;
    protected Footer footer;

    /**
     * Gets the value of the header property.
     * 
     * @return
     *     possible object is
     *     {@link Header }
     *     
     */
    public Header getHeader() {
        return header;
    }

    /**
     * Sets the value of the header property.
     * 
     * @param value
     *     allowed object is
     *     {@link Header }
     *     
     */
    public void setHeader(Header value) {
        this.header = value;
    }

    /**
     * Gets the value of the body property.
     * 
     * @return
     *     possible object is
     *     {@link SoggettoAuraBody }
     *     
     */
    public SoggettoAuraBody getBody() {
        return body;
    }

    /**
     * Sets the value of the body property.
     * 
     * @param value
     *     allowed object is
     *     {@link SoggettoAuraBody }
     *     
     */
    public void setBody(SoggettoAuraBody value) {
        this.body = value;
    }

    /**
     * Gets the value of the footer property.
     * 
     * @return
     *     possible object is
     *     {@link Footer }
     *     
     */
    public Footer getFooter() {
        return footer;
    }

    /**
     * Sets the value of the footer property.
     * 
     * @param value
     *     allowed object is
     *     {@link Footer }
     *     
     */
    public void setFooter(Footer value) {
        this.footer = value;
    }

}
