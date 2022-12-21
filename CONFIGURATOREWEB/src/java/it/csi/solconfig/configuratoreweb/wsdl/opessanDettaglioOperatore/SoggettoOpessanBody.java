/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatore;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SoggettoOpessanBody complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SoggettoOpessanBody">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idAura" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="codiceFiscaleAura" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="infoRappLavoro" type="{http://opessan.opessanws.def.csi.it/}InfoRapportiLavoro" minOccurs="0"/>
 *         &lt;element name="infoAnag" type="{http://opessan.opessanws.def.csi.it/}InfoAnagrafiche" minOccurs="0"/>
 *         &lt;element name="altreInfo" type="{http://opessan.opessanws.def.csi.it/}AltreInfos" minOccurs="0"/>
 *         &lt;element name="infoProf" type="{http://opessan.opessanws.def.csi.it/}InfoProfessionali" minOccurs="0"/>
 *         &lt;element name="associazioniAggregazioni" type="{http://opessan.opessanws.def.csi.it/}Associazioni" minOccurs="0"/>
 *         &lt;element name="elencoStudiMedici" type="{http://opessan.opessanws.def.csi.it/}StudiMedici" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SoggettoOpessanBody", propOrder = {
    "idAura",
    "codiceFiscaleAura",
    "infoRappLavoro",
    "infoAnag",
    "altreInfo",
    "infoProf",
    "associazioniAggregazioni",
    "elencoStudiMedici"
})
public class SoggettoOpessanBody {

    protected BigDecimal idAura;
    protected String codiceFiscaleAura;
    protected InfoRapportiLavoro infoRappLavoro;
    protected InfoAnagrafiche infoAnag;
    protected AltreInfos altreInfo;
    protected InfoProfessionali infoProf;
    protected Associazioni associazioniAggregazioni;
    protected StudiMedici elencoStudiMedici;

    /**
     * Gets the value of the idAura property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getIdAura() {
        return idAura;
    }

    /**
     * Sets the value of the idAura property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setIdAura(BigDecimal value) {
        this.idAura = value;
    }

    /**
     * Gets the value of the codiceFiscaleAura property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodiceFiscaleAura() {
        return codiceFiscaleAura;
    }

    /**
     * Sets the value of the codiceFiscaleAura property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodiceFiscaleAura(String value) {
        this.codiceFiscaleAura = value;
    }

    /**
     * Gets the value of the infoRappLavoro property.
     * 
     * @return
     *     possible object is
     *     {@link InfoRapportiLavoro }
     *     
     */
    public InfoRapportiLavoro getInfoRappLavoro() {
        return infoRappLavoro;
    }

    /**
     * Sets the value of the infoRappLavoro property.
     * 
     * @param value
     *     allowed object is
     *     {@link InfoRapportiLavoro }
     *     
     */
    public void setInfoRappLavoro(InfoRapportiLavoro value) {
        this.infoRappLavoro = value;
    }

    /**
     * Gets the value of the infoAnag property.
     * 
     * @return
     *     possible object is
     *     {@link InfoAnagrafiche }
     *     
     */
    public InfoAnagrafiche getInfoAnag() {
        return infoAnag;
    }

    /**
     * Sets the value of the infoAnag property.
     * 
     * @param value
     *     allowed object is
     *     {@link InfoAnagrafiche }
     *     
     */
    public void setInfoAnag(InfoAnagrafiche value) {
        this.infoAnag = value;
    }

    /**
     * Gets the value of the altreInfo property.
     * 
     * @return
     *     possible object is
     *     {@link AltreInfos }
     *     
     */
    public AltreInfos getAltreInfo() {
        return altreInfo;
    }

    /**
     * Sets the value of the altreInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link AltreInfos }
     *     
     */
    public void setAltreInfo(AltreInfos value) {
        this.altreInfo = value;
    }

    /**
     * Gets the value of the infoProf property.
     * 
     * @return
     *     possible object is
     *     {@link InfoProfessionali }
     *     
     */
    public InfoProfessionali getInfoProf() {
        return infoProf;
    }

    /**
     * Sets the value of the infoProf property.
     * 
     * @param value
     *     allowed object is
     *     {@link InfoProfessionali }
     *     
     */
    public void setInfoProf(InfoProfessionali value) {
        this.infoProf = value;
    }

    /**
     * Gets the value of the associazioniAggregazioni property.
     * 
     * @return
     *     possible object is
     *     {@link Associazioni }
     *     
     */
    public Associazioni getAssociazioniAggregazioni() {
        return associazioniAggregazioni;
    }

    /**
     * Sets the value of the associazioniAggregazioni property.
     * 
     * @param value
     *     allowed object is
     *     {@link Associazioni }
     *     
     */
    public void setAssociazioniAggregazioni(Associazioni value) {
        this.associazioniAggregazioni = value;
    }

    /**
     * Gets the value of the elencoStudiMedici property.
     * 
     * @return
     *     possible object is
     *     {@link StudiMedici }
     *     
     */
    public StudiMedici getElencoStudiMedici() {
        return elencoStudiMedici;
    }

    /**
     * Sets the value of the elencoStudiMedici property.
     * 
     * @param value
     *     allowed object is
     *     {@link StudiMedici }
     *     
     */
    public void setElencoStudiMedici(StudiMedici value) {
        this.elencoStudiMedici = value;
    }

}
