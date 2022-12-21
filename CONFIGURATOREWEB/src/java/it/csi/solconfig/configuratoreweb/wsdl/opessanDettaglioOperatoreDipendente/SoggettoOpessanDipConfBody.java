/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatoreDipendente;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SoggettoOpessanDipConfBody complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="SoggettoOpessanDipConfBody">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="idAura" type="{http://www.w3.org/2001/XMLSchema}decimal" minOccurs="0"/>
 *         &lt;element name="codiceFiscaleAura" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="infoRappLavoro" type="{http://opessan.opessanws.def.csi.it/}InfoRapportiLavoroDip" minOccurs="0"/>
 *         &lt;element name="infoStatiLavorativi" type="{http://opessan.opessanws.def.csi.it/}InfoStatiLavorativiDip" minOccurs="0"/>
 *         &lt;element name="infoQualifiche" type="{http://opessan.opessanws.def.csi.it/}InfoQualificheDipConf" minOccurs="0"/>
 *         &lt;element name="infoSediContrattuali" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="info" type="{http://opessan.opessanws.def.csi.it/}InfoSedeContrattualeDip" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="infoSediOperative" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="info" type="{http://opessan.opessanws.def.csi.it/}InfoSedeOperativaConf" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="infoPosizioniProfessionali" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="info" type="{http://opessan.opessanws.def.csi.it/}InfoPosizioneProfessionale" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="infoLibereProfessioni" minOccurs="0">
 *           &lt;complexType>
 *             &lt;complexContent>
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *                 &lt;sequence>
 *                   &lt;element name="info" type="{http://opessan.opessanws.def.csi.it/}InfoLiberaProfessione" maxOccurs="unbounded" minOccurs="0"/>
 *                 &lt;/sequence>
 *               &lt;/restriction>
 *             &lt;/complexContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *         &lt;element name="infoAnag" type="{http://opessan.opessanws.def.csi.it/}InfoAnagrafiche" minOccurs="0"/>
 *         &lt;element name="altreInfo" type="{http://opessan.opessanws.def.csi.it/}AltreInfos" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SoggettoOpessanDipConfBody", propOrder = {
    "idAura",
    "codiceFiscaleAura",
    "infoRappLavoro",
    "infoStatiLavorativi",
    "infoQualifiche",
    "infoSediContrattuali",
    "infoSediOperative",
    "infoPosizioniProfessionali",
    "infoLibereProfessioni",
    "infoAnag",
    "altreInfo"
})
public class SoggettoOpessanDipConfBody {

    protected BigDecimal idAura;
    protected String codiceFiscaleAura;
    protected InfoRapportiLavoroDip infoRappLavoro;
    protected InfoStatiLavorativiDip infoStatiLavorativi;
    protected InfoQualificheDipConf infoQualifiche;
    protected SoggettoOpessanDipConfBody.InfoSediContrattuali infoSediContrattuali;
    protected SoggettoOpessanDipConfBody.InfoSediOperative infoSediOperative;
    protected SoggettoOpessanDipConfBody.InfoPosizioniProfessionali infoPosizioniProfessionali;
    protected SoggettoOpessanDipConfBody.InfoLibereProfessioni infoLibereProfessioni;
    protected InfoAnagrafiche infoAnag;
    protected AltreInfos altreInfo;

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
     *     {@link InfoRapportiLavoroDip }
     *     
     */
    public InfoRapportiLavoroDip getInfoRappLavoro() {
        return infoRappLavoro;
    }

    /**
     * Sets the value of the infoRappLavoro property.
     * 
     * @param value
     *     allowed object is
     *     {@link InfoRapportiLavoroDip }
     *     
     */
    public void setInfoRappLavoro(InfoRapportiLavoroDip value) {
        this.infoRappLavoro = value;
    }

    /**
     * Gets the value of the infoStatiLavorativi property.
     * 
     * @return
     *     possible object is
     *     {@link InfoStatiLavorativiDip }
     *     
     */
    public InfoStatiLavorativiDip getInfoStatiLavorativi() {
        return infoStatiLavorativi;
    }

    /**
     * Sets the value of the infoStatiLavorativi property.
     * 
     * @param value
     *     allowed object is
     *     {@link InfoStatiLavorativiDip }
     *     
     */
    public void setInfoStatiLavorativi(InfoStatiLavorativiDip value) {
        this.infoStatiLavorativi = value;
    }

    /**
     * Gets the value of the infoQualifiche property.
     * 
     * @return
     *     possible object is
     *     {@link InfoQualificheDipConf }
     *     
     */
    public InfoQualificheDipConf getInfoQualifiche() {
        return infoQualifiche;
    }

    /**
     * Sets the value of the infoQualifiche property.
     * 
     * @param value
     *     allowed object is
     *     {@link InfoQualificheDipConf }
     *     
     */
    public void setInfoQualifiche(InfoQualificheDipConf value) {
        this.infoQualifiche = value;
    }

    /**
     * Gets the value of the infoSediContrattuali property.
     * 
     * @return
     *     possible object is
     *     {@link SoggettoOpessanDipConfBody.InfoSediContrattuali }
     *     
     */
    public SoggettoOpessanDipConfBody.InfoSediContrattuali getInfoSediContrattuali() {
        return infoSediContrattuali;
    }

    /**
     * Sets the value of the infoSediContrattuali property.
     * 
     * @param value
     *     allowed object is
     *     {@link SoggettoOpessanDipConfBody.InfoSediContrattuali }
     *     
     */
    public void setInfoSediContrattuali(SoggettoOpessanDipConfBody.InfoSediContrattuali value) {
        this.infoSediContrattuali = value;
    }

    /**
     * Gets the value of the infoSediOperative property.
     * 
     * @return
     *     possible object is
     *     {@link SoggettoOpessanDipConfBody.InfoSediOperative }
     *     
     */
    public SoggettoOpessanDipConfBody.InfoSediOperative getInfoSediOperative() {
        return infoSediOperative;
    }

    /**
     * Sets the value of the infoSediOperative property.
     * 
     * @param value
     *     allowed object is
     *     {@link SoggettoOpessanDipConfBody.InfoSediOperative }
     *     
     */
    public void setInfoSediOperative(SoggettoOpessanDipConfBody.InfoSediOperative value) {
        this.infoSediOperative = value;
    }

    /**
     * Gets the value of the infoPosizioniProfessionali property.
     * 
     * @return
     *     possible object is
     *     {@link SoggettoOpessanDipConfBody.InfoPosizioniProfessionali }
     *     
     */
    public SoggettoOpessanDipConfBody.InfoPosizioniProfessionali getInfoPosizioniProfessionali() {
        return infoPosizioniProfessionali;
    }

    /**
     * Sets the value of the infoPosizioniProfessionali property.
     * 
     * @param value
     *     allowed object is
     *     {@link SoggettoOpessanDipConfBody.InfoPosizioniProfessionali }
     *     
     */
    public void setInfoPosizioniProfessionali(SoggettoOpessanDipConfBody.InfoPosizioniProfessionali value) {
        this.infoPosizioniProfessionali = value;
    }

    /**
     * Gets the value of the infoLibereProfessioni property.
     * 
     * @return
     *     possible object is
     *     {@link SoggettoOpessanDipConfBody.InfoLibereProfessioni }
     *     
     */
    public SoggettoOpessanDipConfBody.InfoLibereProfessioni getInfoLibereProfessioni() {
        return infoLibereProfessioni;
    }

    /**
     * Sets the value of the infoLibereProfessioni property.
     * 
     * @param value
     *     allowed object is
     *     {@link SoggettoOpessanDipConfBody.InfoLibereProfessioni }
     *     
     */
    public void setInfoLibereProfessioni(SoggettoOpessanDipConfBody.InfoLibereProfessioni value) {
        this.infoLibereProfessioni = value;
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
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;complexContent>
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
     *       &lt;sequence>
     *         &lt;element name="info" type="{http://opessan.opessanws.def.csi.it/}InfoLiberaProfessione" maxOccurs="unbounded" minOccurs="0"/>
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
        "info"
    })
    public static class InfoLibereProfessioni {

        protected List<InfoLiberaProfessione> info;

        /**
         * Gets the value of the info property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the info property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getInfo().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link InfoLiberaProfessione }
         * 
         * 
         */
        public List<InfoLiberaProfessione> getInfo() {
            if (info == null) {
                info = new ArrayList<InfoLiberaProfessione>();
            }
            return this.info;
        }

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
     *         &lt;element name="info" type="{http://opessan.opessanws.def.csi.it/}InfoPosizioneProfessionale" maxOccurs="unbounded" minOccurs="0"/>
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
        "info"
    })
    public static class InfoPosizioniProfessionali {

        protected List<InfoPosizioneProfessionale> info;

        /**
         * Gets the value of the info property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the info property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getInfo().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link InfoPosizioneProfessionale }
         * 
         * 
         */
        public List<InfoPosizioneProfessionale> getInfo() {
            if (info == null) {
                info = new ArrayList<InfoPosizioneProfessionale>();
            }
            return this.info;
        }

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
     *         &lt;element name="info" type="{http://opessan.opessanws.def.csi.it/}InfoSedeContrattualeDip" maxOccurs="unbounded" minOccurs="0"/>
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
        "info"
    })
    public static class InfoSediContrattuali {

        protected List<InfoSedeContrattualeDip> info;

        /**
         * Gets the value of the info property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the info property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getInfo().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link InfoSedeContrattualeDip }
         * 
         * 
         */
        public List<InfoSedeContrattualeDip> getInfo() {
            if (info == null) {
                info = new ArrayList<InfoSedeContrattualeDip>();
            }
            return this.info;
        }

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
     *         &lt;element name="info" type="{http://opessan.opessanws.def.csi.it/}InfoSedeOperativaConf" maxOccurs="unbounded" minOccurs="0"/>
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
        "info"
    })
    public static class InfoSediOperative {

        protected List<InfoSedeOperativaConf> info;

        /**
         * Gets the value of the info property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the info property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getInfo().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link InfoSedeOperativaConf }
         * 
         * 
         */
        public List<InfoSedeOperativaConf> getInfo() {
            if (info == null) {
                info = new ArrayList<InfoSedeOperativaConf>();
            }
            return this.info;
        }

    }

}
