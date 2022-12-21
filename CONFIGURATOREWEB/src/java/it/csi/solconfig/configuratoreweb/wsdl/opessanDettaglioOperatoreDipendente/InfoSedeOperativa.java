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
 * <p>Java class for infoSedeOperativa complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="infoSedeOperativa">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="titolareAzienda" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="strutturaAttivitaOsp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="centroResponsabilita" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="matricola" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataInizioSedeOp" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="dataFineSedeOp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "infoSedeOperativa", propOrder = {
    "titolareAzienda",
    "strutturaAttivitaOsp",
    "centroResponsabilita",
    "matricola",
    "dataInizioSedeOp",
    "dataFineSedeOp"
})
public class InfoSedeOperativa {

    @XmlElement(required = true)
    protected String titolareAzienda;
    protected String strutturaAttivitaOsp;
    @XmlElement(required = true)
    protected String centroResponsabilita;
    @XmlElement(required = true)
    protected String matricola;
    @XmlElement(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataInizioSedeOp;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataFineSedeOp;

    /**
     * Gets the value of the titolareAzienda property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitolareAzienda() {
        return titolareAzienda;
    }

    /**
     * Sets the value of the titolareAzienda property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitolareAzienda(String value) {
        this.titolareAzienda = value;
    }

    /**
     * Gets the value of the strutturaAttivitaOsp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrutturaAttivitaOsp() {
        return strutturaAttivitaOsp;
    }

    /**
     * Sets the value of the strutturaAttivitaOsp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStrutturaAttivitaOsp(String value) {
        this.strutturaAttivitaOsp = value;
    }

    /**
     * Gets the value of the centroResponsabilita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCentroResponsabilita() {
        return centroResponsabilita;
    }

    /**
     * Sets the value of the centroResponsabilita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCentroResponsabilita(String value) {
        this.centroResponsabilita = value;
    }

    /**
     * Gets the value of the matricola property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMatricola() {
        return matricola;
    }

    /**
     * Sets the value of the matricola property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMatricola(String value) {
        this.matricola = value;
    }

    /**
     * Gets the value of the dataInizioSedeOp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInizioSedeOp() {
        return dataInizioSedeOp;
    }

    /**
     * Sets the value of the dataInizioSedeOp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInizioSedeOp(XMLGregorianCalendar value) {
        this.dataInizioSedeOp = value;
    }

    /**
     * Gets the value of the dataFineSedeOp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFineSedeOp() {
        return dataFineSedeOp;
    }

    /**
     * Sets the value of the dataFineSedeOp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFineSedeOp(XMLGregorianCalendar value) {
        this.dataFineSedeOp = value;
    }

}
