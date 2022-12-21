/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.solconfig.configuratoreweb.wsdl.aura.getprofilosanitario;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SoggettoAuraBody complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SoggettoAuraBody">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idAura" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="infoAnag" type="{http://AnagrafeSanitaria.central.services.auraws.aura.csi.it}InfoAnagrafiche" minOccurs="0"/>
 *         &lt;element name="infoSan" type="{http://AnagrafeSanitaria.central.services.auraws.aura.csi.it}InfoSanitarie" minOccurs="0"/>
 *         &lt;element name="infoEsenzioni" type="{http://AnagrafeSanitaria.central.services.auraws.aura.csi.it}ArrayOfinfoesenzioneInfoEsenzione" minOccurs="0"/>
 *         &lt;element name="altreInfo" type="{http://AnagrafeSanitaria.central.services.auraws.aura.csi.it}ArrayOfinformazioniAltreInfo" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SoggettoAuraBody", propOrder = {
    "idAura",
    "infoAnag",
    "infoSan",
    "infoEsenzioni",
    "altreInfo"
})
public class SoggettoAuraBody {

    protected BigDecimal idAura;
    protected InfoAnagrafiche infoAnag;
    protected InfoSanitarie infoSan;
    protected ArrayOfinfoesenzioneInfoEsenzione infoEsenzioni;
    protected ArrayOfinformazioniAltreInfo altreInfo;

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
     * Gets the value of the infoSan property.
     * 
     * @return
     *     possible object is
     *     {@link InfoSanitarie }
     *     
     */
    public InfoSanitarie getInfoSan() {
        return infoSan;
    }

    /**
     * Sets the value of the infoSan property.
     * 
     * @param value
     *     allowed object is
     *     {@link InfoSanitarie }
     *     
     */
    public void setInfoSan(InfoSanitarie value) {
        this.infoSan = value;
    }

    /**
     * Gets the value of the infoEsenzioni property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfinfoesenzioneInfoEsenzione }
     *     
     */
    public ArrayOfinfoesenzioneInfoEsenzione getInfoEsenzioni() {
        return infoEsenzioni;
    }

    /**
     * Sets the value of the infoEsenzioni property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfinfoesenzioneInfoEsenzione }
     *     
     */
    public void setInfoEsenzioni(ArrayOfinfoesenzioneInfoEsenzione value) {
        this.infoEsenzioni = value;
    }

    /**
     * Gets the value of the altreInfo property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfinformazioniAltreInfo }
     *     
     */
    public ArrayOfinformazioniAltreInfo getAltreInfo() {
        return altreInfo;
    }

    /**
     * Sets the value of the altreInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfinformazioniAltreInfo }
     *     
     */
    public void setAltreInfo(ArrayOfinformazioniAltreInfo value) {
        this.altreInfo = value;
    }

}
