/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.farmacia;

import java.util.List;

public class DatiFarmacia {
	
	List<String> codiceFarmacia;
	
	String nome;
	
	String indirizzo;
	
	String comune;

	public List<String> getCodiceFarmacia() {
		return codiceFarmacia;
	}

	public void setCodiceFarmacia(List<String> codiciFarmacia) {
		this.codiceFarmacia = codiciFarmacia;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}
}
