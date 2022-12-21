/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatoreDipendente;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for InfoSedeContrattualeDip complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InfoSedeContrattualeDip">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="azienda" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="titolareAzienda" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strutturaAttivitaOsp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="centroResponsabilita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="puntoFisico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InfoSedeContrattualeDip", propOrder = {
    "azienda",
    "titolareAzienda",
    "strutturaAttivitaOsp",
    "centroResponsabilita",
    "puntoFisico"
})
public class InfoSedeContrattualeDip {

    protected String azienda;
    protected String titolareAzienda;
    protected String strutturaAttivitaOsp;
    protected String centroResponsabilita;
    protected String puntoFisico;

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
     * Gets the value of the puntoFisico property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPuntoFisico() {
        return puntoFisico;
    }

    /**
     * Sets the value of the puntoFisico property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPuntoFisico(String value) {
        this.puntoFisico = value;
    }

}
