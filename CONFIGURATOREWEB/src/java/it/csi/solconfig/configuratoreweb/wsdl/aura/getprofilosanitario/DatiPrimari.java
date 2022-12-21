/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.solconfig.configuratoreweb.wsdl.aura.getprofilosanitario;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for DatiPrimari complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DatiPrimari">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codCittadinanza" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codComuneNascita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codStatoNascita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiceFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cognome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataDecesso" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dataNascita" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="descCittadinanza" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descComuneNascita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descStatoNascita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sesso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="siglaProvNascita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="statoCodiceFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="statoProfiloAnagrafico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatiPrimari", propOrder = {
    "codCittadinanza",
    "codComuneNascita",
    "codStatoNascita",
    "codiceFiscale",
    "cognome",
    "dataDecesso",
    "dataNascita",
    "descCittadinanza",
    "descComuneNascita",
    "descStatoNascita",
    "nome",
    "sesso",
    "siglaProvNascita",
    "statoCodiceFiscale",
    "statoProfiloAnagrafico"
})
public class DatiPrimari {

    protected String codCittadinanza;
    protected String codComuneNascita;
    protected String codStatoNascita;
    protected String codiceFiscale;
    protected String cognome;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataDecesso;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataNascita;
    protected String descCittadinanza;
    protected String descComuneNascita;
    protected String descStatoNascita;
    protected String nome;
    protected String sesso;
    protected String siglaProvNascita;
    protected String statoCodiceFiscale;
    protected String statoProfiloAnagrafico;

    /**
     * Gets the value of the codCittadinanza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodCittadinanza() {
        return codCittadinanza;
    }

    /**
     * Sets the value of the codCittadinanza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodCittadinanza(String value) {
        this.codCittadinanza = value;
    }

    /**
     * Gets the value of the codComuneNascita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodComuneNascita() {
        return codComuneNascita;
    }

    /**
     * Sets the value of the codComuneNascita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodComuneNascita(String value) {
        this.codComuneNascita = value;
    }

    /**
     * Gets the value of the codStatoNascita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodStatoNascita() {
        return codStatoNascita;
    }

    /**
     * Sets the value of the codStatoNascita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodStatoNascita(String value) {
        this.codStatoNascita = value;
    }

    /**
     * Gets the value of the codiceFiscale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    /**
     * Sets the value of the codiceFiscale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceFiscale(String value) {
        this.codiceFiscale = value;
    }

    /**
     * Gets the value of the cognome property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Sets the value of the cognome property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCognome(String value) {
        this.cognome = value;
    }

    /**
     * Gets the value of the dataDecesso property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataDecesso() {
        return dataDecesso;
    }

    /**
     * Sets the value of the dataDecesso property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataDecesso(XMLGregorianCalendar value) {
        this.dataDecesso = value;
    }

    /**
     * Gets the value of the dataNascita property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataNascita() {
        return dataNascita;
    }

    /**
     * Sets the value of the dataNascita property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataNascita(XMLGregorianCalendar value) {
        this.dataNascita = value;
    }

    /**
     * Gets the value of the descCittadinanza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescCittadinanza() {
        return descCittadinanza;
    }

    /**
     * Sets the value of the descCittadinanza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescCittadinanza(String value) {
        this.descCittadinanza = value;
    }

    /**
     * Gets the value of the descComuneNascita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescComuneNascita() {
        return descComuneNascita;
    }

    /**
     * Sets the value of the descComuneNascita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescComuneNascita(String value) {
        this.descComuneNascita = value;
    }

    /**
     * Gets the value of the descStatoNascita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescStatoNascita() {
        return descStatoNascita;
    }

    /**
     * Sets the value of the descStatoNascita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescStatoNascita(String value) {
        this.descStatoNascita = value;
    }

    /**
     * Gets the value of the nome property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNome() {
        return nome;
    }

    /**
     * Sets the value of the nome property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNome(String value) {
        this.nome = value;
    }

    /**
     * Gets the value of the sesso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSesso() {
        return sesso;
    }

    /**
     * Sets the value of the sesso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSesso(String value) {
        this.sesso = value;
    }

    /**
     * Gets the value of the siglaProvNascita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiglaProvNascita() {
        return siglaProvNascita;
    }

    /**
     * Sets the value of the siglaProvNascita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiglaProvNascita(String value) {
        this.siglaProvNascita = value;
    }

    /**
     * Gets the value of the statoCodiceFiscale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatoCodiceFiscale() {
        return statoCodiceFiscale;
    }

    /**
     * Sets the value of the statoCodiceFiscale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatoCodiceFiscale(String value) {
        this.statoCodiceFiscale = value;
    }

    /**
     * Gets the value of the statoProfiloAnagrafico property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatoProfiloAnagrafico() {
        return statoProfiloAnagrafico;
    }

    /**
     * Sets the value of the statoProfiloAnagrafico property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatoProfiloAnagrafico(String value) {
        this.statoProfiloAnagrafico = value;
    }

}
