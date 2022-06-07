/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.dma.dmaloginccebl.client.verifyLoginConditions;

import it.csi.dma.dmaloginccebl.client.verifyLoginConditions.common.ParametriLogin;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;


@XmlRootElement(name = "verifyLoginConditionsRequest")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "verifyLoginConditionsRequest", propOrder = {
	    "ruoloUtente",
	    "codiceFiscaleUtente",
	    "applicazioneRichiesta",
	    "codiceFiscaleAssistito",
	    "parametriLogin"
})
public class VerifyLoginConditionsRequest implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -2794491288972047554L;
    

	public static String[] requiredProperties = { "ruoloUtente",
			"codiceFiscaleUtente", "applicazioneRichiesta", 
			"codiceFiscaleAssistito"};
	

	@XmlElement(namespace="http://dma.csi.it/")
	protected String ruoloUtente;
	@XmlElement(namespace="http://dma.csi.it/")
	protected String codiceFiscaleUtente;
	@XmlElement(namespace="http://dma.csi.it/")
	protected String applicazioneRichiesta;
	@XmlElement(namespace="http://dma.csi.it/")
	protected String codiceFiscaleAssistito;
	@XmlElement(namespace="http://dma.csi.it/")
	protected List<ParametriLogin> parametriLogin;
   
       
	public String getRuoloUtente() {
		return ruoloUtente;
	}

	public void setRuoloUtente(String ruoloUtente) {
		this.ruoloUtente = ruoloUtente;
	}

	public String getCodiceFiscaleUtente() {
		return codiceFiscaleUtente;
	}

	public void setCodiceFiscaleUtente(String codiceFiscaleUtente) {
		this.codiceFiscaleUtente = codiceFiscaleUtente;
	}

	public String getApplicazioneRichiesta() {
		return applicazioneRichiesta;
	}

	public void setApplicazioneRichiesta(String applicazioneRichiesta) {
		this.applicazioneRichiesta = applicazioneRichiesta;
	}

	public String getCodiceFiscaleAssistito() {
		return codiceFiscaleAssistito;
	}

	public void setCodiceFiscaleAssistito(String codiceFiscaleAssistito) {
		this.codiceFiscaleAssistito = codiceFiscaleAssistito;
	}

	public List<ParametriLogin> getParametriLogin() {
		return parametriLogin;
	}

	public void setParametriLogin(List<ParametriLogin> parametriLogin) {
		this.parametriLogin = parametriLogin;
	}

	public String toString(){
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
