/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.token;

import javax.xml.bind.annotation.XmlType;

/**
 * 
 * @author DXC
 * 
 */
@XmlType(namespace="http://dmac.csi.it/")
public class Richiedente {
	
	protected String nome;
    protected String cognome;
    protected String codiceFiscale;
    protected String ruolo;
    protected String codiceAbilitazione;
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getCodiceFiscale() {
		return codiceFiscale;
	}
	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}
	public String getRuolo() {
		return ruolo;
	}
	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}
	public String getCodiceAbilitazione() {
		return codiceAbilitazione;
	}
	public void setCodiceAbilitazione(String codiceAbilitazione) {
		this.codiceAbilitazione = codiceAbilitazione;
	}
    
    

	
    
    

}
