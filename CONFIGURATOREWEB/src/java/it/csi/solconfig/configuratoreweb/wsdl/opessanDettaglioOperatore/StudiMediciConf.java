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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for StudiMediciConf complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StudiMediciConf">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="studioMedico" type="{http://opessan.opessanws.def.csi.it/}StudioMedicoConf" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StudiMediciConf", propOrder = {
    "studioMedico"
})
public class StudiMediciConf {

    @XmlElement(nillable = true)
    protected List<StudioMedicoConf> studioMedico;

    /**
     * Gets the value of the studioMedico property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the studioMedico property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStudioMedico().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StudioMedicoConf }
     * 
     * 
     */
    public List<StudioMedicoConf> getStudioMedico() {
        if (studioMedico == null) {
            studioMedico = new ArrayList<StudioMedicoConf>();
        }
        return this.studioMedico;
    }

}
