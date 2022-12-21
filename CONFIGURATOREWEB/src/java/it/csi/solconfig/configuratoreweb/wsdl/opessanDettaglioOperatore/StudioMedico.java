/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StudioMedico complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StudioMedico">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="denominazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="qualifica" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tipo" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="indicatoreAmbCondiviso" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="indicatoreAmbPubblico" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="indirizzo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="numCivico" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="comune" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cap" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codASL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ASL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="email" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="telPrimario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="telSecondario" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="giorniApertura" type="{http://opessan.opessanws.def.csi.it/}GiorniApertura" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StudioMedico", propOrder = {
    "denominazione",
    "qualifica",
    "tipo",
    "indicatoreAmbCondiviso",
    "indicatoreAmbPubblico",
    "indirizzo",
    "numCivico",
    "comune",
    "cap",
    "codASL",
    "asl",
    "email",
    "telPrimario",
    "telSecondario",
    "giorniApertura"
})
public class StudioMedico {

    @XmlElement(required = true)
    protected String denominazione;
    @XmlElement(required = true)
    protected String qualifica;
    protected int tipo;
    @XmlElement(required = true)
    protected String indicatoreAmbCondiviso;
    @XmlElement(required = true)
    protected String indicatoreAmbPubblico;
    @XmlElement(required = true)
    protected String indirizzo;
    @XmlElement(required = true)
    protected String numCivico;
    @XmlElement(required = true)
    protected String comune;
    protected String cap;
    protected String codASL;
    @XmlElement(name = "ASL")
    protected String asl;
    @XmlElement(required = true)
    protected String email;
    protected String telPrimario;
    protected String telSecondario;
    protected GiorniApertura giorniApertura;

    /**
     * Gets the value of the denominazione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDenominazione() {
        return denominazione;
    }

    /**
     * Sets the value of the denominazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDenominazione(String value) {
        this.denominazione = value;
    }

    /**
     * Gets the value of the qualifica property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getQualifica() {
        return qualifica;
    }

    /**
     * Sets the value of the qualifica property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setQualifica(String value) {
        this.qualifica = value;
    }

    /**
     * Gets the value of the tipo property.
     * 
     */
    public int getTipo() {
        return tipo;
    }

    /**
     * Sets the value of the tipo property.
     * 
     */
    public void setTipo(int value) {
        this.tipo = value;
    }

    /**
     * Gets the value of the indicatoreAmbCondiviso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndicatoreAmbCondiviso() {
        return indicatoreAmbCondiviso;
    }

    /**
     * Sets the value of the indicatoreAmbCondiviso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndicatoreAmbCondiviso(String value) {
        this.indicatoreAmbCondiviso = value;
    }

    /**
     * Gets the value of the indicatoreAmbPubblico property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndicatoreAmbPubblico() {
        return indicatoreAmbPubblico;
    }

    /**
     * Sets the value of the indicatoreAmbPubblico property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndicatoreAmbPubblico(String value) {
        this.indicatoreAmbPubblico = value;
    }

    /**
     * Gets the value of the indirizzo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndirizzo() {
        return indirizzo;
    }

    /**
     * Sets the value of the indirizzo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndirizzo(String value) {
        this.indirizzo = value;
    }

    /**
     * Gets the value of the numCivico property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumCivico() {
        return numCivico;
    }

    /**
     * Sets the value of the numCivico property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumCivico(String value) {
        this.numCivico = value;
    }

    /**
     * Gets the value of the comune property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComune() {
        return comune;
    }

    /**
     * Sets the value of the comune property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComune(String value) {
        this.comune = value;
    }

    /**
     * Gets the value of the cap property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCap() {
        return cap;
    }

    /**
     * Sets the value of the cap property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCap(String value) {
        this.cap = value;
    }

    /**
     * Gets the value of the codASL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodASL() {
        return codASL;
    }

    /**
     * Sets the value of the codASL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodASL(String value) {
        this.codASL = value;
    }

    /**
     * Gets the value of the asl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getASL() {
        return asl;
    }

    /**
     * Sets the value of the asl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setASL(String value) {
        this.asl = value;
    }

    /**
     * Gets the value of the email property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the value of the email property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEmail(String value) {
        this.email = value;
    }

    /**
     * Gets the value of the telPrimario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelPrimario() {
        return telPrimario;
    }

    /**
     * Sets the value of the telPrimario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelPrimario(String value) {
        this.telPrimario = value;
    }

    /**
     * Gets the value of the telSecondario property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelSecondario() {
        return telSecondario;
    }

    /**
     * Sets the value of the telSecondario property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelSecondario(String value) {
        this.telSecondario = value;
    }

    /**
     * Gets the value of the giorniApertura property.
     * 
     * @return
     *     possible object is
     *     {@link GiorniApertura }
     *     
     */
    public GiorniApertura getGiorniApertura() {
        return giorniApertura;
    }

    /**
     * Sets the value of the giorniApertura property.
     * 
     * @param value
     *     allowed object is
     *     {@link GiorniApertura }
     *     
     */
    public void setGiorniApertura(GiorniApertura value) {
        this.giorniApertura = value;
    }

}
