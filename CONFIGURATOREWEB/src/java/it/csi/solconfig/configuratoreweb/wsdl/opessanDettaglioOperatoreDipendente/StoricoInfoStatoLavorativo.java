/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatoreDipendente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for StoricoInfoStatoLavorativo complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StoricoInfoStatoLavorativo">
 *   &lt;complexContent>
 *     &lt;extension base="{http://opessan.opessanws.def.csi.it/}InfoStatoLavorativoDip">
 *       &lt;sequence>
 *         &lt;element name="dataFineEvento" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StoricoInfoStatoLavorativo", propOrder = {
    "dataFineEvento"
})
public class StoricoInfoStatoLavorativo
    extends InfoStatoLavorativoDip
{

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataFineEvento;

    /**
     * Gets the value of the dataFineEvento property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFineEvento() {
        return dataFineEvento;
    }

    /**
     * Sets the value of the dataFineEvento property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFineEvento(XMLGregorianCalendar value) {
        this.dataFineEvento = value;
    }

}
