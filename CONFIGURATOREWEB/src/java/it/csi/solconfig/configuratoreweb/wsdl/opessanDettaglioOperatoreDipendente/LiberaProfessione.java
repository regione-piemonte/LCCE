/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatoreDipendente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for liberaProfessione complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="liberaProfessione">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tipologiaPersonale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="attivitaLiberaProfessione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipologiaLiberaProfessione" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="titolareAzienda" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strutturaAttivitaOsp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="centroResponsabilita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="matricola" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "liberaProfessione", propOrder = {
    "tipologiaPersonale",
    "attivitaLiberaProfessione",
    "tipologiaLiberaProfessione",
    "titolareAzienda",
    "strutturaAttivitaOsp",
    "centroResponsabilita",
    "matricola"
})
public class LiberaProfessione {

    @XmlElement(required = true)
    protected String tipologiaPersonale;
    protected String attivitaLiberaProfessione;
    protected String tipologiaLiberaProfessione;
    protected String titolareAzienda;
    protected String strutturaAttivitaOsp;
    protected String centroResponsabilita;
    protected String matricola;

    /**
     * Gets the value of the tipologiaPersonale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipologiaPersonale() {
        return tipologiaPersonale;
    }

    /**
     * Sets the value of the tipologiaPersonale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipologiaPersonale(String value) {
        this.tipologiaPersonale = value;
    }

    /**
     * Gets the value of the attivitaLiberaProfessione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAttivitaLiberaProfessione() {
        return attivitaLiberaProfessione;
    }

    /**
     * Sets the value of the attivitaLiberaProfessione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAttivitaLiberaProfessione(String value) {
        this.attivitaLiberaProfessione = value;
    }

    /**
     * Gets the value of the tipologiaLiberaProfessione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipologiaLiberaProfessione() {
        return tipologiaLiberaProfessione;
    }

    /**
     * Sets the value of the tipologiaLiberaProfessione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipologiaLiberaProfessione(String value) {
        this.tipologiaLiberaProfessione = value;
    }

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

}
