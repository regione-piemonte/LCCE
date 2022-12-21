/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.dto;

import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "auth_r_ruolo_profilo")
@SequenceGenerator(name = "auth_r_ruolo_profilo_rp_id_seq", sequenceName = "auth_r_ruolo_profilo_rp_id_seq", allocationSize = 1)
public class RuoloProfilo extends BaseDto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_r_ruolo_profilo_rp_id_seq")
	@Column(name = "rp_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_ruolo", referencedColumnName = "id")
	private RuoloDto ruolo;
	
	@ManyToOne
	@JoinColumn(name = "fnz_id", referencedColumnName = "fnz_id")
	private FunzionalitaDto funzionalita;
	
	@Column(name = "data_inizio_validita")
	private Timestamp dataInizioValidita;
	
	@Column(name = "data_fine_validita")
	private Timestamp dataFineValidita;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RuoloDto getRuolo() {
		return ruolo;
	}

	public void setRuolo(RuoloDto ruolo) {
		this.ruolo = ruolo;
	}

	public FunzionalitaDto getFunzionalita() {
		return funzionalita;
	}

	public void setFunzionalita(FunzionalitaDto funzionalita) {
		this.funzionalita = funzionalita;
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

	public RuoloProfilo(BigInteger id, BigInteger id_ruolo, BigInteger funz_id, Timestamp dataInizioValidita, Timestamp dataFineValidita) {
		this.id = Long.parseLong(id.toString());
		this.ruolo = new RuoloDto();
		this.ruolo.setId(Long.parseLong(id_ruolo.toString()));
		this.funzionalita = new FunzionalitaDto();
		this.funzionalita.setIdFunzione(Long.parseLong(funz_id.toString()));
		this.dataInizioValidita = dataInizioValidita;
		this.dataFineValidita = dataFineValidita;
	}

	public RuoloProfilo(BigInteger id, BigInteger id_ruolo, BigInteger funz_id, Timestamp dataInizioValidita) {
		this.id = Long.parseLong(id.toString());
		this.ruolo = new RuoloDto();
		this.ruolo.setId(Long.parseLong(id_ruolo.toString()));
		this.funzionalita = new FunzionalitaDto();
		this.funzionalita.setIdFunzione(Long.parseLong(funz_id.toString()));
		this.dataInizioValidita = dataInizioValidita;
		//this.dataFineValidita = dataFineValidita;
	}

	public RuoloProfilo() {
		
	}
	
}
