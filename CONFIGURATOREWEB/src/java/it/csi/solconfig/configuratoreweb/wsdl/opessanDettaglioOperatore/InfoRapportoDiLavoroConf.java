/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatore;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for InfoRapportoDiLavoroConf complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="InfoRapportoDiLavoroConf">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codAzienda" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="azienda" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="dataInizioRappLavoro" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="posContrattuale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rapportoSSN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoRuolo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoRappLavoro" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="tipoContratto" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="qualifica" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="codRegionale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="indicatoreDisp" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="distrettoCompetenza" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ambitoCompetenza" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataScadenza" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="dataFine" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dataInizioSosp" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="dataFineSosp" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="motivo" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="causa" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="incarichi" type="{http://opessan.opessanws.def.csi.it/}IncarichiConf" minOccurs="0"/>
 *         &lt;element name="infoMassimali" type="{http://opessan.opessanws.def.csi.it/}InfoMassimali" minOccurs="0"/>
 *         &lt;element name="dataVariazione" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InfoRapportoDiLavoroConf", propOrder = {
    "codAzienda",
    "azienda",
    "dataInizioRappLavoro",
    "posContrattuale",
    "rapportoSSN",
    "tipoRuolo",
    "tipoRappLavoro",
    "tipoContratto",
    "qualifica",
    "codRegionale",
    "indicatoreDisp",
    "distrettoCompetenza",
    "ambitoCompetenza",
    "dataScadenza",
    "dataFine",
    "dataInizioSosp",
    "dataFineSosp",
    "motivo",
    "causa",
    "incarichi",
    "infoMassimali",
    "dataVariazione"
})
public class InfoRapportoDiLavoroConf {

    @XmlElement(required = true)
    protected String codAzienda;
    @XmlElement(required = true)
    protected String azienda;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataInizioRappLavoro;
    protected String posContrattuale;
    protected String rapportoSSN;
    protected String tipoRuolo;
    @XmlElement(required = true)
    protected String tipoRappLavoro;
    protected String tipoContratto;
    @XmlElement(required = true)
    protected String qualifica;
    protected String codRegionale;
    protected String indicatoreDisp;
    protected String distrettoCompetenza;
    protected String ambitoCompetenza;
    @XmlElement(required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataScadenza;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataFine;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataInizioSosp;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataFineSosp;
    protected String motivo;
    protected String causa;
    protected IncarichiConf incarichi;
    protected InfoMassimali infoMassimali;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dataVariazione;

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
     * Gets the value of the dataInizioRappLavoro property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInizioRappLavoro() {
        return dataInizioRappLavoro;
    }

    /**
     * Sets the value of the dataInizioRappLavoro property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInizioRappLavoro(XMLGregorianCalendar value) {
        this.dataInizioRappLavoro = value;
    }

    /**
     * Gets the value of the posContrattuale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPosContrattuale() {
        return posContrattuale;
    }

    /**
     * Sets the value of the posContrattuale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPosContrattuale(String value) {
        this.posContrattuale = value;
    }

    /**
     * Gets the value of the rapportoSSN property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRapportoSSN() {
        return rapportoSSN;
    }

    /**
     * Sets the value of the rapportoSSN property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRapportoSSN(String value) {
        this.rapportoSSN = value;
    }

    /**
     * Gets the value of the tipoRuolo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoRuolo() {
        return tipoRuolo;
    }

    /**
     * Sets the value of the tipoRuolo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoRuolo(String value) {
        this.tipoRuolo = value;
    }

    /**
     * Gets the value of the tipoRappLavoro property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoRappLavoro() {
        return tipoRappLavoro;
    }

    /**
     * Sets the value of the tipoRappLavoro property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoRappLavoro(String value) {
        this.tipoRappLavoro = value;
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
     * Gets the value of the codRegionale property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodRegionale() {
        return codRegionale;
    }

    /**
     * Sets the value of the codRegionale property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodRegionale(String value) {
        this.codRegionale = value;
    }

    /**
     * Gets the value of the indicatoreDisp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndicatoreDisp() {
        return indicatoreDisp;
    }

    /**
     * Sets the value of the indicatoreDisp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndicatoreDisp(String value) {
        this.indicatoreDisp = value;
    }

    /**
     * Gets the value of the distrettoCompetenza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistrettoCompetenza() {
        return distrettoCompetenza;
    }

    /**
     * Sets the value of the distrettoCompetenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistrettoCompetenza(String value) {
        this.distrettoCompetenza = value;
    }

    /**
     * Gets the value of the ambitoCompetenza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmbitoCompetenza() {
        return ambitoCompetenza;
    }

    /**
     * Sets the value of the ambitoCompetenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmbitoCompetenza(String value) {
        this.ambitoCompetenza = value;
    }

    /**
     * Gets the value of the dataScadenza property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataScadenza() {
        return dataScadenza;
    }

    /**
     * Sets the value of the dataScadenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataScadenza(XMLGregorianCalendar value) {
        this.dataScadenza = value;
    }

    /**
     * Gets the value of the dataFine property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFine() {
        return dataFine;
    }

    /**
     * Sets the value of the dataFine property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFine(XMLGregorianCalendar value) {
        this.dataFine = value;
    }

    /**
     * Gets the value of the dataInizioSosp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataInizioSosp() {
        return dataInizioSosp;
    }

    /**
     * Sets the value of the dataInizioSosp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataInizioSosp(XMLGregorianCalendar value) {
        this.dataInizioSosp = value;
    }

    /**
     * Gets the value of the dataFineSosp property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataFineSosp() {
        return dataFineSosp;
    }

    /**
     * Sets the value of the dataFineSosp property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataFineSosp(XMLGregorianCalendar value) {
        this.dataFineSosp = value;
    }

    /**
     * Gets the value of the motivo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMotivo() {
        return motivo;
    }

    /**
     * Sets the value of the motivo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMotivo(String value) {
        this.motivo = value;
    }

    /**
     * Gets the value of the causa property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCausa() {
        return causa;
    }

    /**
     * Sets the value of the causa property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCausa(String value) {
        this.causa = value;
    }

    /**
     * Gets the value of the incarichi property.
     * 
     * @return
     *     possible object is
     *     {@link IncarichiConf }
     *     
     */
    public IncarichiConf getIncarichi() {
        return incarichi;
    }

    /**
     * Sets the value of the incarichi property.
     * 
     * @param value
     *     allowed object is
     *     {@link IncarichiConf }
     *     
     */
    public void setIncarichi(IncarichiConf value) {
        this.incarichi = value;
    }

    /**
     * Gets the value of the infoMassimali property.
     * 
     * @return
     *     possible object is
     *     {@link InfoMassimali }
     *     
     */
    public InfoMassimali getInfoMassimali() {
        return infoMassimali;
    }

    /**
     * Sets the value of the infoMassimali property.
     * 
     * @param value
     *     allowed object is
     *     {@link InfoMassimali }
     *     
     */
    public void setInfoMassimali(InfoMassimali value) {
        this.infoMassimali = value;
    }

    /**
     * Gets the value of the dataVariazione property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDataVariazione() {
        return dataVariazione;
    }

    /**
     * Sets the value of the dataVariazione property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDataVariazione(XMLGregorianCalendar value) {
        this.dataVariazione = value;
    }

}
