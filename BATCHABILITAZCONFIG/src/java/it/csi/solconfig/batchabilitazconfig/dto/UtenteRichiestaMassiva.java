/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.batchabilitazconfig.dto;

import java.sql.Timestamp;

public class UtenteRichiestaMassiva {
	
	private Long id;
	private Long idBatch;
	private Long idUtente;
	private Long idRuolo;
	private Long idCollocazione;
	private Long idAbilitazione;
	private Long idProfilo;
	private Timestamp dataCreazione;
	private Long idErroreUtente;
	private String erroreInterno;
	private Timestamp dataInizio;
	private Timestamp dataFine;
	private Timestamp dataFineAbilitazione;
	
	public UtenteRichiestaMassiva() {
	}

	public UtenteRichiestaMassiva(Long id, Long idBatch, Long idUtente, Long idRuolo, Long idCollocazione,
			Long idAbilitazione, Long idProfilo, Timestamp dataCreazione, Long idErroreUtente, String erroreInterno,
			Timestamp dataInizio, Timestamp dataFine, Timestamp dataFineAbilitazione) {
		super();
		this.id = id;
		this.idBatch = idBatch;
		this.idUtente = idUtente;
		this.idRuolo = idRuolo;
		this.idCollocazione = idCollocazione;
		this.idAbilitazione = idAbilitazione;
		this.idProfilo = idProfilo;
		this.dataCreazione = dataCreazione;
		this.idErroreUtente = idErroreUtente.equals(Long.valueOf(0)) ? null : idErroreUtente;
		this.erroreInterno = erroreInterno;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
		this.dataFineAbilitazione = dataFineAbilitazione;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdBatch() {
		return idBatch;
	}

	public void setIdBatch(Long idBatch) {
		this.idBatch = idBatch;
	}

	public Long getIdUtente() {
		return idUtente;
	}

	public void setIdUtente(Long idUtente) {
		this.idUtente = idUtente;
	}

	public Long getIdRuolo() {
		return idRuolo;
	}

	public void setIdRuolo(Long idRuolo) {
		this.idRuolo = idRuolo;
	}

	public Long getIdCollocazione() {
		return idCollocazione;
	}

	public void setIdCollocazione(Long idCollocazione) {
		this.idCollocazione = idCollocazione;
	}

	public Long getIdAbilitazione() {
		return idAbilitazione;
	}

	public void setIdAbilitazione(Long idAbilitazione) {
		this.idAbilitazione = idAbilitazione;
	}

	public Long getIdProfilo() {
		return idProfilo;
	}

	public void setIdProfilo(Long idProfilo) {
		this.idProfilo = idProfilo;
	}

	public Timestamp getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Timestamp dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public Long getIdErroreUtente() {
		return idErroreUtente;
	}

	public void setIdErroreUtente(Long idErroreUtente) {
		this.idErroreUtente = idErroreUtente;
	}

	public String getErroreInterno() {
		return erroreInterno;
	}

	public void setErroreInterno(String erroreInterno) {
		this.erroreInterno = erroreInterno;
	}

	public Timestamp getDataInizio() {
		return dataInizio;
	}

	public void setDataInizio(Timestamp dataInizio) {
		this.dataInizio = dataInizio;
	}

	public Timestamp getDataFine() {
		return dataFine;
	}

	public void setDataFine(Timestamp dataFine) {
		this.dataFine = dataFine;
	}

	public Timestamp getDataFineAbilitazione() {
		return dataFineAbilitazione;
	}

	public void setDataFineAbilitazione(Timestamp dataFineAbilitazione) {
		this.dataFineAbilitazione = dataFineAbilitazione;
	}
	
}
