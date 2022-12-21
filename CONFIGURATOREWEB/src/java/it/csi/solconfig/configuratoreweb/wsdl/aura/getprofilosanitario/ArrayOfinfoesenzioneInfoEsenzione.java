/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.solconfig.configuratoreweb.wsdl.aura.getprofilosanitario;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ArrayOfinfoesenzioneInfoEsenzione complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ArrayOfinfoesenzioneInfoEsenzione">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="infoesenzione" type="{http://AnagrafeSanitaria.central.services.auraws.aura.csi.it}InfoEsenzione" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ArrayOfinfoesenzioneInfoEsenzione", propOrder = {
    "infoesenzione"
})
public class ArrayOfinfoesenzioneInfoEsenzione {

    @XmlElement(nillable = true)
    protected List<InfoEsenzione> infoesenzione;

    /**
     * Gets the value of the infoesenzione property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the infoesenzione property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInfoesenzione().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InfoEsenzione }
     * 
     * 
     */
    public List<InfoEsenzione> getInfoesenzione() {
        if (infoesenzione == null) {
            infoesenzione = new ArrayList<InfoEsenzione>();
        }
        return this.infoesenzione;
    }

}
