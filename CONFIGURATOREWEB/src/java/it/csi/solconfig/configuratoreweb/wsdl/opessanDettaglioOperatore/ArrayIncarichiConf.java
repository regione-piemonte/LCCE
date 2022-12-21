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
 * <p>Java class for ArrayIncarichiConf complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayIncarichiConf">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codAzienda" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codiceMatricolaMultispec" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrMatricolaMultispec" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codiceUO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="descrUO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataInizio" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="brancaSpec" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codMultiSpec" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="denomMultiSpec" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codUoEsteso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="denomUo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "ArrayIncarichiConf", propOrder = {
    "codAzienda",
    "codiceMatricolaMultispec",
    "descrMatricolaMultispec",
    "codiceUO",
    "descrUO",
    "dataInizio",
    "brancaSpec",
    "codMultiSpec",
    "denomMultiSpec",
    "codUoEsteso",
    "denomUo",
    "unitaOperativa",
    "totOreSett",
    "dataFine",
    "flagAnnullato"
})
public class ArrayIncarichiConf {

    @XmlElement(required = true)
    protected String codAzienda;
    @XmlElement(required = true)
    protected String codiceMatricolaMultispec;
    @XmlElement(required = true)
    protected String descrMatricolaMultispec;
    @XmlElement(required = true)
    protected String codiceUO;
    @XmlElement(required = true)
    protected String descrUO;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataInizio;
    protected String brancaSpec;
    protected String codMultiSpec;
    protected String denomMultiSpec;
    protected String codUoEsteso;
    protected String denomUo;
    @XmlElement(required = true)
    protected String unitaOperativa;
    protected int totOreSett;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataFine;
    protected boolean flagAnnullato;

    /**
     * Gets the value of the codAzienda property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodAzienda() {
        return codAzienda;
    }

    /**
     * Sets the value of the codAzienda property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodAzienda(String value) {
        this.codAzienda = value;
    }

    /**
     * Gets the value of the codiceMatricolaMultispec property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceMatricolaMultispec() {
        return codiceMatricolaMultispec;
    }

    /**
     * Sets the value of the codiceMatricolaMultispec property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceMatricolaMultispec(String value) {
        this.codiceMatricolaMultispec = value;
    }

    /**
     * Gets the value of the descrMatricolaMultispec property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrMatricolaMultispec() {
        return descrMatricolaMultispec;
    }

    /**
     * Sets the value of the descrMatricolaMultispec property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrMatricolaMultispec(String value) {
        this.descrMatricolaMultispec = value;
    }

    /**
     * Gets the value of the codiceUO property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceUO() {
        return codiceUO;
    }

    /**
     * Sets the value of the codiceUO property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceUO(String value) {
        this.codiceUO = value;
    }

    /**
     * Gets the value of the descrUO property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrUO() {
        return descrUO;
    }

    /**
     * Sets the value of the descrUO property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrUO(String value) {
        this.descrUO = value;
    }

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
     * Gets the value of the codMultiSpec property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodMultiSpec() {
        return codMultiSpec;
    }

    /**
     * Sets the value of the codMultiSpec property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodMultiSpec(String value) {
        this.codMultiSpec = value;
    }

    /**
     * Gets the value of the denomMultiSpec property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenomMultiSpec() {
        return denomMultiSpec;
    }

    /**
     * Sets the value of the denomMultiSpec property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenomMultiSpec(String value) {
        this.denomMultiSpec = value;
    }

    /**
     * Gets the value of the codUoEsteso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodUoEsteso() {
        return codUoEsteso;
    }

    /**
     * Sets the value of the codUoEsteso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodUoEsteso(String value) {
        this.codUoEsteso = value;
    }

    /**
     * Gets the value of the denomUo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenomUo() {
        return denomUo;
    }

    /**
     * Sets the value of the denomUo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenomUo(String value) {
        this.denomUo = value;
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
