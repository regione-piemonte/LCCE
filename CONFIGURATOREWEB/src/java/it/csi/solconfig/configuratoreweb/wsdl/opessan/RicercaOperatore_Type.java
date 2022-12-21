/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.solconfig.configuratoreweb.wsdl.opessan;

import it.csi.solconfig.configuratoreweb.wsdl.opessan.common.request.Header;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * &lt;p&gt;Classe Java per ricercaOperatore complex type.
 * 
 * &lt;p&gt;Il seguente frammento di schema specifica il contenuto previsto contenuto in questa classe.
 * 
 * &lt;pre&gt;
 * &amp;lt;complexType name="ricercaOperatore"&amp;gt;
 *   &amp;lt;complexContent&amp;gt;
 *     &amp;lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&amp;gt;
 *       &amp;lt;sequence&amp;gt;
 *         &amp;lt;element name="header" type="{http://opessan.opessanws.dto.csi.it/common/request/}Header"/&amp;gt;
 *         &amp;lt;element name="body" type="{http://opessan.opessanws.def.csi.it/}IdentificativoProfilo"/&amp;gt;
 *       &amp;lt;/sequence&amp;gt;
 *     &amp;lt;/restriction&amp;gt;
 *   &amp;lt;/complexContent&amp;gt;
 * &amp;lt;/complexType&amp;gt;
 * &lt;/pre&gt;
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ricercaOperatore", propOrder = {
    "header",
    "body"
})
public class RicercaOperatore_Type {

    @XmlElement(required = true)
    protected Header header;
    @XmlElement(required = true)
    protected IdentificativoProfilo body;

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
     *     {@link IdentificativoProfilo }
     *     
     */
    public IdentificativoProfilo getBody() {
        return body;
    }

    /**
     * Imposta il valore della propriet� body.
     * 
     * @param value
     *     allowed object is
     *     {@link IdentificativoProfilo }
     *     
     */
    public void setBody(IdentificativoProfilo value) {
        this.body = value;
    }

}
