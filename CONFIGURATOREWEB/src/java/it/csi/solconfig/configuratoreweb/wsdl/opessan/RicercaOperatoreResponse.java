/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.solconfig.configuratoreweb.wsdl.opessan;

import it.csi.solconfig.configuratoreweb.wsdl.opessan.common.response.Footer;
import it.csi.solconfig.configuratoreweb.wsdl.opessan.common.response.Header;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * &lt;p&gt;Classe Java per anonymous complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="header" type="{http://opessan.opessanws.dto.csi.it/common/response/}Header"/&amp;gt;
 *         &amp;lt;element name="body" type="{http://opessan.opessanws.def.csi.it/}RicercaOperatoreBody" minOccurs="0"/&amp;gt;
 *         &amp;lt;element name="footer" type="{http://opessan.opessanws.dto.csi.it/common/response/}Footer" minOccurs="0"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "header",
    "body",
    "footer"
})
@XmlRootElement(name = "RicercaOperatoreResponse")
public class RicercaOperatoreResponse {

    @XmlElement(required = true)
    protected Header header;
    protected RicercaOperatoreBody body;
    protected Footer footer;

    /**
     * Recupera il valore della propriet� header.
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
     * Imposta il valore della propriet� header.
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
     * Recupera il valore della propriet� body.
     * 
     * @return
     *     possible object is
     *     {@link RicercaOperatoreBody }
     *     
     */
    public RicercaOperatoreBody getBody() {
        return body;
    }

    /**
     * Imposta il valore della propriet� body.
     * 
     * @param value
     *     allowed object is
     *     {@link RicercaOperatoreBody }
     *     
     */
    public void setBody(RicercaOperatoreBody value) {
        this.body = value;
    }

    /**
     * Recupera il valore della propriet� footer.
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
     * Imposta il valore della propriet� footer.
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
