/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.batchabilitazconfig.dto;

import java.sql.Timestamp;
import java.util.List;

public class RichiestaMassiva {
	
	private Long id;
	private String cfOperatore;
	private Boolean isDisabilitazione;
	private Long idStatoBatch;
	private Timestamp dataCreazione;
	private Timestamp dataInizio;
	private Timestamp dataFine;
	private List<UtenteRichiestaMassiva> listaUtenti;
	
	private Boolean isInvioMailAura;
	
	public RichiestaMassiva() {}

	public RichiestaMassiva(Long id, String cfOperatore, Boolean isDisabilitazione, Long idStatoBatch,
			Timestamp dataCreazione, Timestamp dataInizio, Timestamp dataFine) {
		super();
		this.id = id;
		this.cfOperatore = cfOperatore;
		this.isDisabilitazione = isDisabilitazione != null ? isDisabilitazione : false;
		this.idStatoBatch = idStatoBatch;
		this.dataCreazione = dataCreazione;
		this.dataInizio = dataInizio;
		this.dataFine = dataFine;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCfOperatore() {
		return cfOperatore;
	}

	public void setCfOperatore(String cfOperatore) {
		this.cfOperatore = cfOperatore;
	}

	public Boolean isDisabilitazione() {
		return isDisabilitazione;
	}

	public void setDisabilitazione(Boolean isDisabilitazione) {
		this.isDisabilitazione = isDisabilitazione;
	}

	public Long getIdStatoBatch() {
		return idStatoBatch;
	}

	public void setIdStatoBatch(Long idStatoBatch) {
		this.idStatoBatch = idStatoBatch;
	}

	public Timestamp getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(Timestamp dataCreazione) {
		this.dataCreazione = dataCreazione;
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

	public List<UtenteRichiestaMassiva> getListaUtenti() {
		return listaUtenti;
	}

	public void setListaUtenti(List<UtenteRichiestaMassiva> listaUtenti) {
		this.listaUtenti = listaUtenti;
	}

	public Boolean getIsInvioMailAura() {
		return isInvioMailAura;
	}

	public void setIsInvioMailAura(Boolean isInvioMailAura) {
		this.isInvioMailAura = isInvioMailAura;
	}

}
