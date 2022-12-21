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
 * <p>Java class for InfoSedeOperativaConf complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InfoSedeOperativaConf">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="azienda" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codStruttura" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="denominazioneStruttura" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="titolareAzienda" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="strutturaAttivitaOsp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="centroResponsabilita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="puntoFisico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="matricola" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataInizioSedeOp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="dataFineSedeOp" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="codUoEsteso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="denomUo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codMultiSpec" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="denomMultiSpec" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codElemOrganiz" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descElemento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InfoSedeOperativaConf", propOrder = {
    "azienda",
    "codStruttura",
    "denominazioneStruttura",
    "titolareAzienda",
    "strutturaAttivitaOsp",
    "centroResponsabilita",
    "puntoFisico",
    "matricola",
    "dataInizioSedeOp",
    "dataFineSedeOp",
    "codUoEsteso",
    "denomUo",
    "codMultiSpec",
    "denomMultiSpec",
    "codElemOrganiz",
    "descElemento"
})
public class InfoSedeOperativaConf {

    protected String azienda;
    protected String codStruttura;
    protected String denominazioneStruttura;
    protected String titolareAzienda;
    protected String strutturaAttivitaOsp;
    protected String centroResponsabilita;
    protected String puntoFisico;
    protected String matricola;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataInizioSedeOp;
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dataFineSedeOp;
    protected String codUoEsteso;
    protected String denomUo;
    protected String codMultiSpec;
    protected String denomMultiSpec;
    protected String codElemOrganiz;
    protected String descElemento;

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
     * Gets the value of the codStruttura property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodStruttura() {
        return codStruttura;
    }

    /**
     * Sets the value of the codStruttura property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodStruttura(String value) {
        this.codStruttura = value;
    }

    /**
     * Gets the value of the denominazioneStruttura property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenominazioneStruttura() {
        return denominazioneStruttura;
    }

    /**
     * Sets the value of the denominazioneStruttura property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenominazioneStruttura(String value) {
        this.denominazioneStruttura = value;
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
     * Gets the value of the codElemOrganiz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodElemOrganiz() {
        return codElemOrganiz;
    }

    /**
     * Sets the value of the codElemOrganiz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodElemOrganiz(String value) {
        this.codElemOrganiz = value;
    }

    /**
     * Gets the value of the descElemento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescElemento() {
        return descElemento;
    }

    /**
     * Sets the value of the descElemento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescElemento(String value) {
        this.descElemento = value;
    }

}
