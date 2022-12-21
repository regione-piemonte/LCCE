/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatoreDipendente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatoreDipendente.common.response.Footer;
import it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatoreDipendente.common.response.Header;


/**
 * <p>Java class for getDettaglioConfResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getDettaglioConfResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="header" type="{http://opessan.opessanws.dto.csi.it/common/response/}Header"/>
 *                   &lt;element name="body" type="{http://opessan.opessanws.def.csi.it/}SoggettoOpessanDipConfBody" minOccurs="0"/>
 *                   &lt;element name="footer" type="{http://opessan.opessanws.dto.csi.it/common/response/}Footer" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getDettaglioConfResponse", propOrder = {
    "_return"
})
public class GetDettaglioConfResponse2 {

    @XmlElement(name = "return")
    protected GetDettaglioConfResponse2 .Return _return;

    /**
     * Gets the value of the return property.
     * 
     * @return
     *     possible object is
     *     {@link GetDettaglioConfResponse2 .Return }
     *     
     */
    public GetDettaglioConfResponse2 .Return getReturn() {
        return _return;
    }

    /**
     * Sets the value of the return property.
     * 
     * @param value
     *     allowed object is
     *     {@link GetDettaglioConfResponse2 .Return }
     *     
     */
    public void setReturn(GetDettaglioConfResponse2 .Return value) {
        this._return = value;
    }


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
     *         &lt;element name="header" type="{http://opessan.opessanws.dto.csi.it/common/response/}Header"/>
     *         &lt;element name="body" type="{http://opessan.opessanws.def.csi.it/}SoggettoOpessanDipConfBody" minOccurs="0"/>
     *         &lt;element name="footer" type="{http://opessan.opessanws.dto.csi.it/common/response/}Footer" minOccurs="0"/>
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
        "header",
        "body",
        "footer"
    })
    @XmlRootElement
    public static class Return {

        @XmlElement(required = true)
        protected Header header;
        protected SoggettoOpessanDipConfBody body;
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
         *     {@link SoggettoOpessanDipConfBody }
         *     
         */
        public SoggettoOpessanDipConfBody getBody() {
            return body;
        }

        /**
         * Sets the value of the body property.
         * 
         * @param value
         *     allowed object is
         *     {@link SoggettoOpessanDipConfBody }
         *     
         */
        public void setBody(SoggettoOpessanDipConfBody value) {
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

}
