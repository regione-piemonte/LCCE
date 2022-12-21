/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatoreDipendente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for InfoPosizioneProfessionale complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InfoPosizioneProfessionale">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="azienda" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="titoloStudio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="anniAnzianitaServPrec" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mesiAnzianitaServPrec" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="giorniAnzianitaServPrec" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="anniAnzianitaServASR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mesiAnzianitaServASR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="giorniAnzianitaServASR" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataInizioAttCertific" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="dataFineAttCertific" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="dataInizioAttPresc" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="dataFineAttPresc" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InfoPosizioneProfessionale", propOrder = {
    "azienda",
    "titoloStudio",
    "anniAnzianitaServPrec",
    "mesiAnzianitaServPrec",
    "giorniAnzianitaServPrec",
    "anniAnzianitaServASR",
    "mesiAnzianitaServASR",
    "giorniAnzianitaServASR",
    "dataInizioAttCertific",
    "dataFineAttCertific",
    "dataInizioAttPresc",
    "dataFineAttPresc"
})
public class InfoPosizioneProfessionale {

    protected String azienda;
    protected String titoloStudio;
    protected String anniAnzianitaServPrec;
    protected String mesiAnzianitaServPrec;
    protected String giorniAnzianitaServPrec;
    protected String anniAnzianitaServASR;
    protected String mesiAnzianitaServASR;
    protected String giorniAnzianitaServASR;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataInizioAttCertific;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataFineAttCertific;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataInizioAttPresc;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataFineAttPresc;

    /**
     * Gets the value of the azienda property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAzienda() {
        return azienda;
    }

    /**
     * Sets the value of the azienda property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAzienda(String value) {
        this.azienda = value;
    }

    /**
     * Gets the value of the titoloStudio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitoloStudio() {
        return titoloStudio;
    }

    /**
     * Sets the value of the titoloStudio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitoloStudio(String value) {
        this.titoloStudio = value;
    }

    /**
     * Gets the value of the anniAnzianitaServPrec property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnniAnzianitaServPrec() {
        return anniAnzianitaServPrec;
    }

    /**
     * Sets the value of the anniAnzianitaServPrec property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnniAnzianitaServPrec(String value) {
        this.anniAnzianitaServPrec = value;
    }

    /**
     * Gets the value of the mesiAnzianitaServPrec property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMesiAnzianitaServPrec() {
        return mesiAnzianitaServPrec;
    }

    /**
     * Sets the value of the mesiAnzianitaServPrec property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMesiAnzianitaServPrec(String value) {
        this.mesiAnzianitaServPrec = value;
    }

    /**
     * Gets the value of the giorniAnzianitaServPrec property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGiorniAnzianitaServPrec() {
        return giorniAnzianitaServPrec;
    }

    /**
     * Sets the value of the giorniAnzianitaServPrec property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGiorniAnzianitaServPrec(String value) {
        this.giorniAnzianitaServPrec = value;
    }

    /**
     * Gets the value of the anniAnzianitaServASR property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnniAnzianitaServASR() {
        return anniAnzianitaServASR;
    }

    /**
     * Sets the value of the anniAnzianitaServASR property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAnniAnzianitaServASR(String value) {
        this.anniAnzianitaServASR = value;
    }

    /**
     * Gets the value of the mesiAnzianitaServASR property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMesiAnzianitaServASR() {
        return mesiAnzianitaServASR;
    }

    /**
     * Sets the value of the mesiAnzianitaServASR property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMesiAnzianitaServASR(String value) {
        this.mesiAnzianitaServASR = value;
    }

    /**
     * Gets the value of the giorniAnzianitaServASR property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGiorniAnzianitaServASR() {
        return giorniAnzianitaServASR;
    }

    /**
     * Sets the value of the giorniAnzianitaServASR property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGiorniAnzianitaServASR(String value) {
        this.giorniAnzianitaServASR = value;
    }

    /**
     * Gets the value of the dataInizioAttCertific property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInizioAttCertific() {
        return dataInizioAttCertific;
    }

    /**
     * Sets the value of the dataInizioAttCertific property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInizioAttCertific(XMLGregorianCalendar value) {
        this.dataInizioAttCertific = value;
    }

    /**
     * Gets the value of the dataFineAttCertific property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFineAttCertific() {
        return dataFineAttCertific;
    }

    /**
     * Sets the value of the dataFineAttCertific property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFineAttCertific(XMLGregorianCalendar value) {
        this.dataFineAttCertific = value;
    }

    /**
     * Gets the value of the dataInizioAttPresc property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInizioAttPresc() {
        return dataInizioAttPresc;
    }

    /**
     * Sets the value of the dataInizioAttPresc property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInizioAttPresc(XMLGregorianCalendar value) {
        this.dataInizioAttPresc = value;
    }

    /**
     * Gets the value of the dataFineAttPresc property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFineAttPresc() {
        return dataFineAttPresc;
    }

    /**
     * Sets the value of the dataFineAttPresc property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFineAttPresc(XMLGregorianCalendar value) {
        this.dataFineAttPresc = value;
    }

}
