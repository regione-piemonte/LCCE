/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.dto.configuratorews;

import java.sql.Timestamp;
import java.util.List;

public class ModelFunzionalitaProfilo {

	private Long idFunzione;
	private String codiceFunzione;
	private String descrizioneFunzione;
	private Timestamp dataInizioValidita;
	private Timestamp dataFineValidita;
	List<ProfiloConValidita> profili;

	public Long getIdFunzione() {
		return idFunzione;
	}

	public void setIdFunzione(Long idFunzione) {
		this.idFunzione = idFunzione;
	}

	public String getCodiceFunzione() {
		return codiceFunzione;
	}

	public void setCodiceFunzione(String codiceFunzione) {
		this.codiceFunzione = codiceFunzione;
	}

	public String getDescrizioneFunzione() {
		return descrizioneFunzione;
	}

	public void setDescrizioneFunzione(String descrizioneFunzione) {
		this.descrizioneFunzione = descrizioneFunzione;
	}

	public Timestamp getDataInizioValidita() {
		return dataInizioValidita;
	}

	public void setDataInizioValidita(Timestamp dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}

	public Timestamp getDataFineValidita() {
		return dataFineValidita;
	}

	public void setDataFineValidita(Timestamp dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

	public List<ProfiloConValidita> getProfili() {
		return profili;
	}

	public void setProfili(List<ProfiloConValidita> profili) {
		this.profili = profili;
	}

}
