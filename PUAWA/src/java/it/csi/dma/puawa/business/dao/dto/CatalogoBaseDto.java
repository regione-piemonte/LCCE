/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.business.dao.dto;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class CatalogoBaseDto {
	
	@Column(name = "codice")
	private String codice;
	
	@Column(name = "descrizione")
	private String descrizione;

    @Column(name = "data_inserimento")
    private Timestamp dataInserimento;
	

	public Timestamp getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Timestamp dataInserimento) {
		this.dataInserimento = dataInserimento;
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
	
    
}
