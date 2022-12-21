/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for ArrayIncarichi complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayIncarichi">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dataInizio" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="brancaSpec" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="unitaOperativa" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="totOreSett" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="dataFine" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="flagAnnullato" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayIncarichi", propOrder = {
    "dataInizio",
    "brancaSpec",
    "unitaOperativa",
    "totOreSett",
    "dataFine",
    "flagAnnullato"
})
public class ArrayIncarichi {

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataInizio;
    protected String brancaSpec;
    @XmlElement(required = true)
    protected String unitaOperativa;
    protected int totOreSett;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataFine;
    protected boolean flagAnnullato;

    /**
     * Gets the value of the dataInizio property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInizio() {
        return dataInizio;
    }

    /**
     * Sets the value of the dataInizio property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInizio(XMLGregorianCalendar value) {
        this.dataInizio = value;
    }

    /**
     * Gets the value of the brancaSpec property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrancaSpec() {
        return brancaSpec;
    }

    /**
     * Sets the value of the brancaSpec property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrancaSpec(String value) {
        this.brancaSpec = value;
    }

    /**
     * Gets the value of the unitaOperativa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitaOperativa() {
        return unitaOperativa;
    }

    /**
     * Sets the value of the unitaOperativa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUnitaOperativa(String value) {
        this.unitaOperativa = value;
    }

    /**
     * Gets the value of the totOreSett property.
     * 
     */
    public int getTotOreSett() {
        return totOreSett;
    }

    /**
     * Sets the value of the totOreSett property.
     * 
     */
    public void setTotOreSett(int value) {
        this.totOreSett = value;
    }

    /**
     * Gets the value of the dataFine property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFine() {
        return dataFine;
    }

    /**
     * Sets the value of the dataFine property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFine(XMLGregorianCalendar value) {
        this.dataFine = value;
    }

    /**
     * Gets the value of the flagAnnullato property.
     * 
     */
    public boolean isFlagAnnullato() {
        return flagAnnullato;
    }

    /**
     * Sets the value of the flagAnnullato property.
     * 
     */
    public void setFlagAnnullato(boolean value) {
        this.flagAnnullato = value;
    }

}
