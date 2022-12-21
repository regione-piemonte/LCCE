/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.batchabilitazconfig.dto;

import java.sql.Timestamp;

public class Abilitazione {
	
	private Long id;
	private Long idRuoloUtente;
	private Long idApplicazione;
	private String codiceAbilitazione;
	private Timestamp dataInizioValidita;
	private Timestamp dataFineValidita;
	private Timestamp dataInserimento;
	private Long fnztreeId;
	private Long utecolId;
	private Timestamp dataAggiornamento;
	private Timestamp dataCancellazione;
	private String cfOperatore;
	
	public Abilitazione() {
	}

	public Abilitazione(Long id, Long idRuoloUtente, Long idApplicazione, String codiceAbilitazione,
			Timestamp dataInizioValidita, Timestamp dataFineValidita, Timestamp dataInserimento, Long fnztreeId,
			Long utecolId, Timestamp dataAggiornamento, Timestamp dataCancellazione, String cfOperatore) {
		super();
		this.id = id;
		this.idRuoloUtente = idRuoloUtente;
		this.idApplicazione = idApplicazione;
		this.codiceAbilitazione = codiceAbilitazione;
		this.dataInizioValidita = dataInizioValidita;
		this.dataFineValidita = dataFineValidita;
		this.dataInserimento = dataInserimento;
		this.fnztreeId = fnztreeId;
		this.utecolId = utecolId;
		this.dataAggiornamento = dataAggiornamento;
		this.dataCancellazione = dataCancellazione;
		this.cfOperatore = cfOperatore;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdRuoloUtente() {
		return idRuoloUtente;
	}

	public void setIdRuoloUtente(Long idRuoloUtente) {
		this.idRuoloUtente = idRuoloUtente;
	}

	public Long getIdApplicazione() {
		return idApplicazione;
	}

	public void setIdApplicazione(Long idApplicazione) {
		this.idApplicazione = idApplicazione;
	}

	public String getCodiceAbilitazione() {
		return codiceAbilitazione;
	}

	public void setCodiceAbilitazione(String codiceAbilitazione) {
		this.codiceAbilitazione = codiceAbilitazione;
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

	public Timestamp getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Timestamp dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public Long getFnztreeId() {
		return fnztreeId;
	}

	public void setFnztreeId(Long fnztreeId) {
		this.fnztreeId = fnztreeId;
	}

	public Long getUtecolId() {
		return utecolId;
	}

	public void setUtecolId(Long utecolId) {
		this.utecolId = utecolId;
	}

	public Timestamp getDataAggiornamento() {
		return dataAggiornamento;
	}

	public void setDataAggiornamento(Timestamp dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}

	public Timestamp getDataCancellazione() {
		return dataCancellazione;
	}

	public void setDataCancellazione(Timestamp dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}

	public String getCfOperatore() {
		return cfOperatore;
	}

	public void setCfOperatore(String cfOperatore) {
		this.cfOperatore = cfOperatore;
	}
	
}
