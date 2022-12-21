/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.interfacews.msg;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import javax.xml.bind.annotation.*;

/**
 * Codifica base per molte entita'
 * 
 * @author Alberto Lagna
 * @version $Id: $
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "codifica", 
	namespace="http://dma.csi.it/",
	propOrder = {
	    "codice",
	    "descrizione",
	    "riferimento"
	})
@XmlSeeAlso({
    Errore.class,
})
public class Codifica implements java.io.Serializable {

	private static final long serialVersionUID = -5236681327845542096L;
	
	/** codice interno DMA */
    protected String codice;
    protected String riferimento;
    
	@XmlTransient
	protected boolean riferito = false;
    
    protected String descrizione;

	public boolean isRiferito() {
		return riferito;
	}

	public Codifica(){}
    
	public Codifica(String codice, String descrizione) {
		super();
		this.codice = codice;
		this.descrizione = descrizione;
	}
	public String getCodice() {
		return codice;
	}
	public void setCodice(String codice) {
		this.codice = codice;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codice == null) ? 0 : codice.hashCode());
		result = prime * result
				+ ((descrizione == null) ? 0 : descrizione.hashCode());
		return result;
	}

	/**
	 * Modificato per far funzionare la equals di oggetti simili nella lista di codifiche
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Codifica other = (Codifica) obj;
		if (codice == null) {
			if (other.codice != null)
				return false;
		} else if (!codice.equals(other.codice))
			return false;
		if (descrizione == null) {
			if (other.descrizione != null)
				return false;
		} else if (!descrizione.equals(other.descrizione))
			return false;
		return true;
	}

	public String getRiferimento() {
		return riferimento;
	}

	public void setRiferimento(String riferimento) {
		this.riferimento = riferimento;
	}
	
	public String toString(){
		return ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}