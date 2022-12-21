/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatore;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for Associazioni complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Associazioni">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="associazioneAggregazione" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="codAzienda" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="codice" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="denominazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="tipo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *                   &lt;element name="cognomeRef" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="nomeRef" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="indirizzo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="numCivico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="cap" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="comune" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="tel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *                   &lt;element name="dataCessazione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *                   &lt;element name="dataInizioAdesione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *                   &lt;element name="dataFineAdesione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Associazioni", propOrder = {
    "associazioneAggregazione"
})
public class Associazioni {

    @XmlElement(nillable = true)
    protected List<Associazioni.AssociazioneAggregazione> associazioneAggregazione;

    /**
     * Gets the value of the associazioneAggregazione property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the associazioneAggregazione property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAssociazioneAggregazione().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Associazioni.AssociazioneAggregazione }
     * 
     * 
     */
    public List<Associazioni.AssociazioneAggregazione> getAssociazioneAggregazione() {
        if (associazioneAggregazione == null) {
            associazioneAggregazione = new ArrayList<Associazioni.AssociazioneAggregazione>();
        }
        return this.associazioneAggregazione;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="codAzienda" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="codice" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="denominazione" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="tipo" type="{http://www.w3.org/2001/XMLSchema}string"/>
     *         &lt;element name="cognomeRef" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="nomeRef" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="indirizzo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="numCivico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="cap" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="comune" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="tel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
     *         &lt;element name="dataCessazione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
     *         &lt;element name="dataInizioAdesione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
     *         &lt;element name="dataFineAdesione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
     *       &lt;/sequence>
     *     &lt;/restriction>
     *   &lt;/complexContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "codAzienda",
        "codice",
        "denominazione",
        "tipo",
        "cognomeRef",
        "nomeRef",
        "indirizzo",
        "numCivico",
        "cap",
        "comune",
        "tel",
        "dataCessazione",
        "dataInizioAdesione",
        "dataFineAdesione"
    })
    public static class AssociazioneAggregazione {

        @XmlElement(required = true)
        protected String codAzienda;
        @XmlElement(required = true)
        protected String codice;
        @XmlElement(required = true)
        protected String denominazione;
        @XmlElement(required = true)
        protected String tipo;
        protected String cognomeRef;
        protected String nomeRef;
        protected String indirizzo;
        protected String numCivico;
        protected String cap;
        protected String comune;
        protected String tel;
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar dataCessazione;
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar dataInizioAdesione;
        @XmlSchemaType(name = "dateTime")
        protected XMLGregorianCalendar dataFineAdesione;

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
         * Gets the value of the codice property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCodice() {
            return codice;
        }

        /**
         * Sets the value of the codice property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCodice(String value) {
            this.codice = value;
        }

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
         * Gets the value of the tipo property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTipo() {
            return tipo;
        }

        /**
         * Sets the value of the tipo property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTipo(String value) {
            this.tipo = value;
        }

        /**
         * Gets the value of the cognomeRef property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getCognomeRef() {
            return cognomeRef;
        }

        /**
         * Sets the value of the cognomeRef property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setCognomeRef(String value) {
            this.cognomeRef = value;
        }

        /**
         * Gets the value of the nomeRef property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNomeRef() {
            return nomeRef;
        }

        /**
         * Sets the value of the nomeRef property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNomeRef(String value) {
            this.nomeRef = value;
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
         * Gets the value of the tel property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getTel() {
            return tel;
        }

        /**
         * Sets the value of the tel property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setTel(String value) {
            this.tel = value;
        }

        /**
         * Gets the value of the dataCessazione property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getDataCessazione() {
            return dataCessazione;
        }

        /**
         * Sets the value of the dataCessazione property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setDataCessazione(XMLGregorianCalendar value) {
            this.dataCessazione = value;
        }

        /**
         * Gets the value of the dataInizioAdesione property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getDataInizioAdesione() {
            return dataInizioAdesione;
        }

        /**
         * Sets the value of the dataInizioAdesione property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setDataInizioAdesione(XMLGregorianCalendar value) {
            this.dataInizioAdesione = value;
        }

        /**
         * Gets the value of the dataFineAdesione property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getDataFineAdesione() {
            return dataFineAdesione;
        }

        /**
         * Sets the value of the dataFineAdesione property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setDataFineAdesione(XMLGregorianCalendar value) {
            this.dataFineAdesione = value;
        }

    }

}
