/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for InfoProfessionali complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InfoProfessionali">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dataLaurea" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dataSpecializzazione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dataTitolarita" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="nomeAlboProf" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="siglaProvAlboProf" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numIscrizioneAlbo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataIscrizAlbo" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dataCancAlbo" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dataInizioInformat" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dataInizioSospAlbo" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dataFineSospAlbo" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InfoProfessionali", propOrder = {
    "dataLaurea",
    "dataSpecializzazione",
    "dataTitolarita",
    "nomeAlboProf",
    "siglaProvAlboProf",
    "numIscrizioneAlbo",
    "dataIscrizAlbo",
    "dataCancAlbo",
    "dataInizioInformat",
    "dataInizioSospAlbo",
    "dataFineSospAlbo"
})
public class InfoProfessionali {

    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataLaurea;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataSpecializzazione;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataTitolarita;
    protected String nomeAlboProf;
    protected String siglaProvAlboProf;
    protected String numIscrizioneAlbo;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataIscrizAlbo;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataCancAlbo;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataInizioInformat;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataInizioSospAlbo;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataFineSospAlbo;

    /**
     * Gets the value of the dataLaurea property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataLaurea() {
        return dataLaurea;
    }

    /**
     * Sets the value of the dataLaurea property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataLaurea(XMLGregorianCalendar value) {
        this.dataLaurea = value;
    }

    /**
     * Gets the value of the dataSpecializzazione property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataSpecializzazione() {
        return dataSpecializzazione;
    }

    /**
     * Sets the value of the dataSpecializzazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataSpecializzazione(XMLGregorianCalendar value) {
        this.dataSpecializzazione = value;
    }

    /**
     * Gets the value of the dataTitolarita property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataTitolarita() {
        return dataTitolarita;
    }

    /**
     * Sets the value of the dataTitolarita property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataTitolarita(XMLGregorianCalendar value) {
        this.dataTitolarita = value;
    }

    /**
     * Gets the value of the nomeAlboProf property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeAlboProf() {
        return nomeAlboProf;
    }

    /**
     * Sets the value of the nomeAlboProf property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeAlboProf(String value) {
        this.nomeAlboProf = value;
    }

    /**
     * Gets the value of the siglaProvAlboProf property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiglaProvAlboProf() {
        return siglaProvAlboProf;
    }

    /**
     * Sets the value of the siglaProvAlboProf property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiglaProvAlboProf(String value) {
        this.siglaProvAlboProf = value;
    }

    /**
     * Gets the value of the numIscrizioneAlbo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumIscrizioneAlbo() {
        return numIscrizioneAlbo;
    }

    /**
     * Sets the value of the numIscrizioneAlbo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumIscrizioneAlbo(String value) {
        this.numIscrizioneAlbo = value;
    }

    /**
     * Gets the value of the dataIscrizAlbo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataIscrizAlbo() {
        return dataIscrizAlbo;
    }

    /**
     * Sets the value of the dataIscrizAlbo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataIscrizAlbo(XMLGregorianCalendar value) {
        this.dataIscrizAlbo = value;
    }

    /**
     * Gets the value of the dataCancAlbo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataCancAlbo() {
        return dataCancAlbo;
    }

    /**
     * Sets the value of the dataCancAlbo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataCancAlbo(XMLGregorianCalendar value) {
        this.dataCancAlbo = value;
    }

    /**
     * Gets the value of the dataInizioInformat property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInizioInformat() {
        return dataInizioInformat;
    }

    /**
     * Sets the value of the dataInizioInformat property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInizioInformat(XMLGregorianCalendar value) {
        this.dataInizioInformat = value;
    }

    /**
     * Gets the value of the dataInizioSospAlbo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInizioSospAlbo() {
        return dataInizioSospAlbo;
    }

    /**
     * Sets the value of the dataInizioSospAlbo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInizioSospAlbo(XMLGregorianCalendar value) {
        this.dataInizioSospAlbo = value;
    }

    /**
     * Gets the value of the dataFineSospAlbo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFineSospAlbo() {
        return dataFineSospAlbo;
    }

    /**
     * Sets the value of the dataFineSospAlbo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFineSospAlbo(XMLGregorianCalendar value) {
        this.dataFineSospAlbo = value;
    }

}
