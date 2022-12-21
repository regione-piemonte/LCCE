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
 * <p>Java class for infoQualifica complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="infoQualifica">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="dataDecorrenza" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="esclusivitaRapporto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tipoContratto" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ruolo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="categoria" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="profiloProfessionale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="posizioneFunzionale" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="mansione" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "infoQualifica", propOrder = {
    "dataDecorrenza",
    "esclusivitaRapporto",
    "tipoContratto",
    "ruolo",
    "categoria",
    "profiloProfessionale",
    "posizioneFunzionale",
    "mansione"
})
public class InfoQualifica {

    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataDecorrenza;
    @XmlElement(required = true)
    protected String esclusivitaRapporto;
    @XmlElement(required = true)
    protected String tipoContratto;
    @XmlElement(required = true)
    protected String ruolo;
    @XmlElement(required = true)
    protected String categoria;
    @XmlElement(required = true)
    protected String profiloProfessionale;
    @XmlElement(required = true)
    protected String posizioneFunzionale;
    @XmlElement(required = true)
    protected String mansione;

    /**
     * Gets the value of the dataDecorrenza property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataDecorrenza() {
        return dataDecorrenza;
    }

    /**
     * Sets the value of the dataDecorrenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataDecorrenza(XMLGregorianCalendar value) {
        this.dataDecorrenza = value;
    }

    /**
     * Gets the value of the esclusivitaRapporto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsclusivitaRapporto() {
        return esclusivitaRapporto;
    }

    /**
     * Sets the value of the esclusivitaRapporto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsclusivitaRapporto(String value) {
        this.esclusivitaRapporto = value;
    }

    /**
     * Gets the value of the tipoContratto property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoContratto() {
        return tipoContratto;
    }

    /**
     * Sets the value of the tipoContratto property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoContratto(String value) {
        this.tipoContratto = value;
    }

    /**
     * Gets the value of the ruolo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRuolo() {
        return ruolo;
    }

    /**
     * Sets the value of the ruolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRuolo(String value) {
        this.ruolo = value;
    }

    /**
     * Gets the value of the categoria property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Sets the value of the categoria property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCategoria(String value) {
        this.categoria = value;
    }

    /**
     * Gets the value of the profiloProfessionale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProfiloProfessionale() {
        return profiloProfessionale;
    }

    /**
     * Sets the value of the profiloProfessionale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProfiloProfessionale(String value) {
        this.profiloProfessionale = value;
    }

    /**
     * Gets the value of the posizioneFunzionale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPosizioneFunzionale() {
        return posizioneFunzionale;
    }

    /**
     * Sets the value of the posizioneFunzionale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPosizioneFunzionale(String value) {
        this.posizioneFunzionale = value;
    }

    /**
     * Gets the value of the mansione property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMansione() {
        return mansione;
    }

    /**
     * Sets the value of the mansione property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMansione(String value) {
        this.mansione = value;
    }

}
