/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.authenticationconshibboleth;

import javax.xml.bind.annotation.XmlType;

/**
 * 
 * @author DXC
 * 
 */
@XmlType(namespace="http://dmac.csi.it/")
public class Richiedente {
	
    protected String codiceFiscaleMedico;
    public String getCodiceFiscaleMedico() {
		return codiceFiscaleMedico;
	}
	public void setCodiceFiscaleMedico(String codiceFiscaleMedico) {
		this.codiceFiscaleMedico = codiceFiscaleMedico;
	}
	protected String ruolo;
    protected String ipClient;
    protected String applicazione;
    
    
	
	public String getRuolo() {
		return ruolo;
	}
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	public String getIpClient() {
		return ipClient;
	}
	public void setIpClient(String ipClient) {
		this.ipClient = ipClient;
	}
	public String getApplicazione() {
		return applicazione;
	}
	public void setApplicazione(String applicazione) {
		this.applicazione = applicazione;
	}
    
    

	
    
    

}
