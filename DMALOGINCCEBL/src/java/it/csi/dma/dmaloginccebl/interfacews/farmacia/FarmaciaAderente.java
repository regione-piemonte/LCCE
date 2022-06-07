/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.farmacia;

import javax.xml.bind.annotation.XmlElement;

import it.csi.dma.dmaloginccebl.interfacews.msg.Indirizzo;

public class FarmaciaAderente {

	
	String codice;
	
	String nome;
	
	Indirizzo indirizzo;
	
	public String getCodice() {
		return codice;
	}


	public void setCodice(String codiceRegionaleFarmacia) {
		this.codice = codiceRegionaleFarmacia;
	}

	public Indirizzo getIndirizzo() {
		return indirizzo;
	}


	public void setIndirizzo(Indirizzo indirizzo) {
		this.indirizzo = indirizzo;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
