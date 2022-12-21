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
 * <p>Java class for FasceOrarie complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FasceOrarie">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="orario" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="orarioInizio" type="{http://www.w3.org/2001/XMLSchema}time" minOccurs="0"/>
 *                   &lt;element name="orarioFine" type="{http://www.w3.org/2001/XMLSchema}time" minOccurs="0"/>
 *                   &lt;element name="note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
@XmlType(name = "FasceOrarie", propOrder = {
    "orario"
})
public class FasceOrarie {

    @XmlElement(nillable = true)
    protected List<FasceOrarie.Orario> orario;

    /**
     * Gets the value of the orario property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the orario property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrario().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FasceOrarie.Orario }
     * 
     * 
     */
    public List<FasceOrarie.Orario> getOrario() {
        if (orario == null) {
            orario = new ArrayList<FasceOrarie.Orario>();
        }
        return this.orario;
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
     *         &lt;element name="orarioInizio" type="{http://www.w3.org/2001/XMLSchema}time" minOccurs="0"/>
     *         &lt;element name="orarioFine" type="{http://www.w3.org/2001/XMLSchema}time" minOccurs="0"/>
     *         &lt;element name="note" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
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
        "orarioInizio",
        "orarioFine",
        "note"
    })
    public static class Orario {

        @XmlSchemaType(name = "time")
        protected XMLGregorianCalendar orarioInizio;
        @XmlSchemaType(name = "time")
        protected XMLGregorianCalendar orarioFine;
        protected String note;

        /**
         * Gets the value of the orarioInizio property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getOrarioInizio() {
            return orarioInizio;
        }

        /**
         * Sets the value of the orarioInizio property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setOrarioInizio(XMLGregorianCalendar value) {
            this.orarioInizio = value;
        }

        /**
         * Gets the value of the orarioFine property.
         * 
         * @return
         *     possible object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public XMLGregorianCalendar getOrarioFine() {
            return orarioFine;
        }

        /**
         * Sets the value of the orarioFine property.
         * 
         * @param value
         *     allowed object is
         *     {@link XMLGregorianCalendar }
         *     
         */
        public void setOrarioFine(XMLGregorianCalendar value) {
            this.orarioFine = value;
        }

        /**
         * Gets the value of the note property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNote() {
            return note;
        }

        /**
         * Sets the value of the note property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNote(String value) {
            this.note = value;
        }

    }

}
