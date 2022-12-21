/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.solconfig.configuratoreweb.wsdl.aura.ScreeningEpatiteC;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Response complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Response">
 *   &lt;complexContent>
 *     &lt;extension base="{http://ScreeningEpatiteC.central.services.auraws.aura.csi.it}Ens_Response">
 *       &lt;sequence>
 *         &lt;element name="capDomicilio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="capResidenza" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cittadinanza" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiceAslAssistenza" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiceAslDomicilio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiceAslResidenzaAsl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiceAssistito" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiceComuneDomicilio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiceFiscale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiceFiscaleMedico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiceIstatNascita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiceIstatResidenza" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiceMunicipio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiceRegioneAssistenza" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiceRegioneDomicilio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiceRegioneResidenzaAsl" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="codiceTipoAssistito" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cognome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="cognomeMedico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comuneDomicilio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comuneNascita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="comuneResidenza" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataDecesso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataFineValidita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataFineValiditaSSN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataInizioValidita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="dataNascita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="descrizioneTipologiaAssistenza" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="idAura" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="indirizzoDomicilio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="indirizzoEmail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="indirizzoResidenza" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nome" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nomeMedico" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numeroTessera" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="provinciaNascita" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="provinciaResidenza" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="provinciaDomicilio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sesso" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="siglaProvNasc" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="siglaProvRes" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoAssistito" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipoMovimento" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="telefonoDomicilio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="telefonoResidenza" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="tipologiaAssistenza" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="telefonoCup" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="fonteDati" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="checkEsenzione" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="listaDiagnosi" type="{http://ScreeningEpatiteC.central.services.auraws.aura.csi.it}ArrayOfdiagnosiResponseDiagnosi" minOccurs="0"/>
 *         &lt;element name="listaMessaggi" type="{http://ScreeningEpatiteC.central.services.auraws.aura.csi.it}ArrayOfmsgResponseBodyMsgs" minOccurs="0"/>
 *         &lt;element name="esito" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *               &lt;maxLength value="100"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element name="DistrettoDomicilio" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Response", propOrder = {
    "capDomicilio",
    "capResidenza",
    "cittadinanza",
    "codiceAslAssistenza",
    "codiceAslDomicilio",
    "codiceAslResidenzaAsl",
    "codiceAssistito",
    "codiceComuneDomicilio",
    "codiceFiscale",
    "codiceFiscaleMedico",
    "codiceIstatNascita",
    "codiceIstatResidenza",
    "codiceMunicipio",
    "codiceRegioneAssistenza",
    "codiceRegioneDomicilio",
    "codiceRegioneResidenzaAsl",
    "codiceTipoAssistito",
    "cognome",
    "cognomeMedico",
    "comuneDomicilio",
    "comuneNascita",
    "comuneResidenza",
    "dataDecesso",
    "dataFineValidita",
    "dataFineValiditaSSN",
    "dataInizioValidita",
    "dataNascita",
    "descrizioneTipologiaAssistenza",
    "idAura",
    "indirizzoDomicilio",
    "indirizzoEmail",
    "indirizzoResidenza",
    "nome",
    "nomeMedico",
    "numeroTessera",
    "provinciaNascita",
    "provinciaResidenza",
    "provinciaDomicilio",
    "sesso",
    "siglaProvNasc",
    "siglaProvRes",
    "tipoAssistito",
    "tipoMovimento",
    "telefonoDomicilio",
    "telefonoResidenza",
    "tipologiaAssistenza",
    "telefonoCup",
    "fonteDati",
    "checkEsenzione",
    "listaDiagnosi",
    "listaMessaggi",
    "esito",
    "distrettoDomicilio"
})
@XmlRootElement
public class Response
    extends EnsResponse
{

    protected String capDomicilio;
    protected String capResidenza;
    protected String cittadinanza;
    protected String codiceAslAssistenza;
    protected String codiceAslDomicilio;
    protected String codiceAslResidenzaAsl;
    protected String codiceAssistito;
    protected String codiceComuneDomicilio;
    protected String codiceFiscale;
    protected String codiceFiscaleMedico;
    protected String codiceIstatNascita;
    protected String codiceIstatResidenza;
    protected String codiceMunicipio;
    protected String codiceRegioneAssistenza;
    protected String codiceRegioneDomicilio;
    protected String codiceRegioneResidenzaAsl;
    protected String codiceTipoAssistito;
    protected String cognome;
    protected String cognomeMedico;
    protected String comuneDomicilio;
    protected String comuneNascita;
    protected String comuneResidenza;
    protected String dataDecesso;
    protected String dataFineValidita;
    protected String dataFineValiditaSSN;
    protected String dataInizioValidita;
    protected String dataNascita;
    protected String descrizioneTipologiaAssistenza;
    protected Long idAura;
    protected String indirizzoDomicilio;
    protected String indirizzoEmail;
    protected String indirizzoResidenza;
    protected String nome;
    protected String nomeMedico;
    protected String numeroTessera;
    protected String provinciaNascita;
    protected String provinciaResidenza;
    protected String provinciaDomicilio;
    protected String sesso;
    protected String siglaProvNasc;
    protected String siglaProvRes;
    protected String tipoAssistito;
    protected String tipoMovimento;
    protected String telefonoDomicilio;
    protected String telefonoResidenza;
    protected String tipologiaAssistenza;
    protected String telefonoCup;
    protected String fonteDati;
    protected Long checkEsenzione;
    protected ArrayOfdiagnosiResponseDiagnosi listaDiagnosi;
    protected ArrayOfmsgResponseBodyMsgs listaMessaggi;
    protected String esito;
    @XmlElement(name = "DistrettoDomicilio")
    protected String distrettoDomicilio;

    /**
     * Gets the value of the capDomicilio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCapDomicilio() {
        return capDomicilio;
    }

    /**
     * Sets the value of the capDomicilio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCapDomicilio(String value) {
        this.capDomicilio = value;
    }

    /**
     * Gets the value of the capResidenza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCapResidenza() {
        return capResidenza;
    }

    /**
     * Sets the value of the capResidenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCapResidenza(String value) {
        this.capResidenza = value;
    }

    /**
     * Gets the value of the cittadinanza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCittadinanza() {
        return cittadinanza;
    }

    /**
     * Sets the value of the cittadinanza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCittadinanza(String value) {
        this.cittadinanza = value;
    }

    /**
     * Gets the value of the codiceAslAssistenza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceAslAssistenza() {
        return codiceAslAssistenza;
    }

    /**
     * Sets the value of the codiceAslAssistenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceAslAssistenza(String value) {
        this.codiceAslAssistenza = value;
    }

    /**
     * Gets the value of the codiceAslDomicilio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceAslDomicilio() {
        return codiceAslDomicilio;
    }

    /**
     * Sets the value of the codiceAslDomicilio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceAslDomicilio(String value) {
        this.codiceAslDomicilio = value;
    }

    /**
     * Gets the value of the codiceAslResidenzaAsl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceAslResidenzaAsl() {
        return codiceAslResidenzaAsl;
    }

    /**
     * Sets the value of the codiceAslResidenzaAsl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceAslResidenzaAsl(String value) {
        this.codiceAslResidenzaAsl = value;
    }

    /**
     * Gets the value of the codiceAssistito property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceAssistito() {
        return codiceAssistito;
    }

    /**
     * Sets the value of the codiceAssistito property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceAssistito(String value) {
        this.codiceAssistito = value;
    }

    /**
     * Gets the value of the codiceComuneDomicilio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceComuneDomicilio() {
        return codiceComuneDomicilio;
    }

    /**
     * Sets the value of the codiceComuneDomicilio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceComuneDomicilio(String value) {
        this.codiceComuneDomicilio = value;
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
     * Gets the value of the codiceFiscaleMedico property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceFiscaleMedico() {
        return codiceFiscaleMedico;
    }

    /**
     * Sets the value of the codiceFiscaleMedico property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceFiscaleMedico(String value) {
        this.codiceFiscaleMedico = value;
    }

    /**
     * Gets the value of the codiceIstatNascita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceIstatNascita() {
        return codiceIstatNascita;
    }

    /**
     * Sets the value of the codiceIstatNascita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceIstatNascita(String value) {
        this.codiceIstatNascita = value;
    }

    /**
     * Gets the value of the codiceIstatResidenza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceIstatResidenza() {
        return codiceIstatResidenza;
    }

    /**
     * Sets the value of the codiceIstatResidenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceIstatResidenza(String value) {
        this.codiceIstatResidenza = value;
    }

    /**
     * Gets the value of the codiceMunicipio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceMunicipio() {
        return codiceMunicipio;
    }

    /**
     * Sets the value of the codiceMunicipio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceMunicipio(String value) {
        this.codiceMunicipio = value;
    }

    /**
     * Gets the value of the codiceRegioneAssistenza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceRegioneAssistenza() {
        return codiceRegioneAssistenza;
    }

    /**
     * Sets the value of the codiceRegioneAssistenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceRegioneAssistenza(String value) {
        this.codiceRegioneAssistenza = value;
    }

    /**
     * Gets the value of the codiceRegioneDomicilio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceRegioneDomicilio() {
        return codiceRegioneDomicilio;
    }

    /**
     * Sets the value of the codiceRegioneDomicilio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceRegioneDomicilio(String value) {
        this.codiceRegioneDomicilio = value;
    }

    /**
     * Gets the value of the codiceRegioneResidenzaAsl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceRegioneResidenzaAsl() {
        return codiceRegioneResidenzaAsl;
    }

    /**
     * Sets the value of the codiceRegioneResidenzaAsl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceRegioneResidenzaAsl(String value) {
        this.codiceRegioneResidenzaAsl = value;
    }

    /**
     * Gets the value of the codiceTipoAssistito property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceTipoAssistito() {
        return codiceTipoAssistito;
    }

    /**
     * Sets the value of the codiceTipoAssistito property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceTipoAssistito(String value) {
        this.codiceTipoAssistito = value;
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
     * Gets the value of the cognomeMedico property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCognomeMedico() {
        return cognomeMedico;
    }

    /**
     * Sets the value of the cognomeMedico property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCognomeMedico(String value) {
        this.cognomeMedico = value;
    }

    /**
     * Gets the value of the comuneDomicilio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComuneDomicilio() {
        return comuneDomicilio;
    }

    /**
     * Sets the value of the comuneDomicilio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComuneDomicilio(String value) {
        this.comuneDomicilio = value;
    }

    /**
     * Gets the value of the comuneNascita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComuneNascita() {
        return comuneNascita;
    }

    /**
     * Sets the value of the comuneNascita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComuneNascita(String value) {
        this.comuneNascita = value;
    }

    /**
     * Gets the value of the comuneResidenza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComuneResidenza() {
        return comuneResidenza;
    }

    /**
     * Sets the value of the comuneResidenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComuneResidenza(String value) {
        this.comuneResidenza = value;
    }

    /**
     * Gets the value of the dataDecesso property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataDecesso() {
        return dataDecesso;
    }

    /**
     * Sets the value of the dataDecesso property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataDecesso(String value) {
        this.dataDecesso = value;
    }

    /**
     * Gets the value of the dataFineValidita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataFineValidita() {
        return dataFineValidita;
    }

    /**
     * Sets the value of the dataFineValidita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataFineValidita(String value) {
        this.dataFineValidita = value;
    }

    /**
     * Gets the value of the dataFineValiditaSSN property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataFineValiditaSSN() {
        return dataFineValiditaSSN;
    }

    /**
     * Sets the value of the dataFineValiditaSSN property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataFineValiditaSSN(String value) {
        this.dataFineValiditaSSN = value;
    }

    /**
     * Gets the value of the dataInizioValidita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataInizioValidita() {
        return dataInizioValidita;
    }

    /**
     * Sets the value of the dataInizioValidita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataInizioValidita(String value) {
        this.dataInizioValidita = value;
    }

    /**
     * Gets the value of the dataNascita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataNascita() {
        return dataNascita;
    }

    /**
     * Sets the value of the dataNascita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataNascita(String value) {
        this.dataNascita = value;
    }

    /**
     * Gets the value of the descrizioneTipologiaAssistenza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescrizioneTipologiaAssistenza() {
        return descrizioneTipologiaAssistenza;
    }

    /**
     * Sets the value of the descrizioneTipologiaAssistenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescrizioneTipologiaAssistenza(String value) {
        this.descrizioneTipologiaAssistenza = value;
    }

    /**
     * Gets the value of the idAura property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getIdAura() {
        return idAura;
    }

    /**
     * Sets the value of the idAura property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setIdAura(Long value) {
        this.idAura = value;
    }

    /**
     * Gets the value of the indirizzoDomicilio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndirizzoDomicilio() {
        return indirizzoDomicilio;
    }

    /**
     * Sets the value of the indirizzoDomicilio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndirizzoDomicilio(String value) {
        this.indirizzoDomicilio = value;
    }

    /**
     * Gets the value of the indirizzoEmail property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndirizzoEmail() {
        return indirizzoEmail;
    }

    /**
     * Sets the value of the indirizzoEmail property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndirizzoEmail(String value) {
        this.indirizzoEmail = value;
    }

    /**
     * Gets the value of the indirizzoResidenza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIndirizzoResidenza() {
        return indirizzoResidenza;
    }

    /**
     * Sets the value of the indirizzoResidenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIndirizzoResidenza(String value) {
        this.indirizzoResidenza = value;
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
     * Gets the value of the nomeMedico property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNomeMedico() {
        return nomeMedico;
    }

    /**
     * Sets the value of the nomeMedico property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNomeMedico(String value) {
        this.nomeMedico = value;
    }

    /**
     * Gets the value of the numeroTessera property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroTessera() {
        return numeroTessera;
    }

    /**
     * Sets the value of the numeroTessera property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroTessera(String value) {
        this.numeroTessera = value;
    }

    /**
     * Gets the value of the provinciaNascita property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvinciaNascita() {
        return provinciaNascita;
    }

    /**
     * Sets the value of the provinciaNascita property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvinciaNascita(String value) {
        this.provinciaNascita = value;
    }

    /**
     * Gets the value of the provinciaResidenza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvinciaResidenza() {
        return provinciaResidenza;
    }

    /**
     * Sets the value of the provinciaResidenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvinciaResidenza(String value) {
        this.provinciaResidenza = value;
    }

    /**
     * Gets the value of the provinciaDomicilio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvinciaDomicilio() {
        return provinciaDomicilio;
    }

    /**
     * Sets the value of the provinciaDomicilio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvinciaDomicilio(String value) {
        this.provinciaDomicilio = value;
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
     * Gets the value of the siglaProvNasc property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiglaProvNasc() {
        return siglaProvNasc;
    }

    /**
     * Sets the value of the siglaProvNasc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiglaProvNasc(String value) {
        this.siglaProvNasc = value;
    }

    /**
     * Gets the value of the siglaProvRes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSiglaProvRes() {
        return siglaProvRes;
    }

    /**
     * Sets the value of the siglaProvRes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSiglaProvRes(String value) {
        this.siglaProvRes = value;
    }

    /**
     * Gets the value of the tipoAssistito property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoAssistito() {
        return tipoAssistito;
    }

    /**
     * Sets the value of the tipoAssistito property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoAssistito(String value) {
        this.tipoAssistito = value;
    }

    /**
     * Gets the value of the tipoMovimento property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipoMovimento() {
        return tipoMovimento;
    }

    /**
     * Sets the value of the tipoMovimento property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipoMovimento(String value) {
        this.tipoMovimento = value;
    }

    /**
     * Gets the value of the telefonoDomicilio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelefonoDomicilio() {
        return telefonoDomicilio;
    }

    /**
     * Sets the value of the telefonoDomicilio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelefonoDomicilio(String value) {
        this.telefonoDomicilio = value;
    }

    /**
     * Gets the value of the telefonoResidenza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelefonoResidenza() {
        return telefonoResidenza;
    }

    /**
     * Sets the value of the telefonoResidenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelefonoResidenza(String value) {
        this.telefonoResidenza = value;
    }

    /**
     * Gets the value of the tipologiaAssistenza property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTipologiaAssistenza() {
        return tipologiaAssistenza;
    }

    /**
     * Sets the value of the tipologiaAssistenza property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTipologiaAssistenza(String value) {
        this.tipologiaAssistenza = value;
    }

    /**
     * Gets the value of the telefonoCup property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTelefonoCup() {
        return telefonoCup;
    }

    /**
     * Sets the value of the telefonoCup property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTelefonoCup(String value) {
        this.telefonoCup = value;
    }

    /**
     * Gets the value of the fonteDati property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFonteDati() {
        return fonteDati;
    }

    /**
     * Sets the value of the fonteDati property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFonteDati(String value) {
        this.fonteDati = value;
    }

    /**
     * Gets the value of the checkEsenzione property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCheckEsenzione() {
        return checkEsenzione;
    }

    /**
     * Sets the value of the checkEsenzione property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCheckEsenzione(Long value) {
        this.checkEsenzione = value;
    }

    /**
     * Gets the value of the listaDiagnosi property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfdiagnosiResponseDiagnosi }
     *     
     */
    public ArrayOfdiagnosiResponseDiagnosi getListaDiagnosi() {
        return listaDiagnosi;
    }

    /**
     * Sets the value of the listaDiagnosi property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfdiagnosiResponseDiagnosi }
     *     
     */
    public void setListaDiagnosi(ArrayOfdiagnosiResponseDiagnosi value) {
        this.listaDiagnosi = value;
    }

    /**
     * Gets the value of the listaMessaggi property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfmsgResponseBodyMsgs }
     *     
     */
    public ArrayOfmsgResponseBodyMsgs getListaMessaggi() {
        return listaMessaggi;
    }

    /**
     * Sets the value of the listaMessaggi property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfmsgResponseBodyMsgs }
     *     
     */
    public void setListaMessaggi(ArrayOfmsgResponseBodyMsgs value) {
        this.listaMessaggi = value;
    }

    /**
     * Gets the value of the esito property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEsito() {
        return esito;
    }

    /**
     * Sets the value of the esito property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEsito(String value) {
        this.esito = value;
    }

    /**
     * Gets the value of the distrettoDomicilio property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistrettoDomicilio() {
        return distrettoDomicilio;
    }

    /**
     * Sets the value of the distrettoDomicilio property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistrettoDomicilio(String value) {
        this.distrettoDomicilio = value;
    }

}
