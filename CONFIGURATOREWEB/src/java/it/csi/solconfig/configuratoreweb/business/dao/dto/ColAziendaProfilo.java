/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.dto;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "auth_r_colazienda_profilo")
@SequenceGenerator(name = "auth_r_colazienda_profilo_id_colaz_prof_seq", sequenceName = "auth_r_colazienda_profilo_id_colaz_prof_seq", allocationSize = 1)
public class ColAziendaProfilo extends BaseDto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_r_colazienda_profilo_id_colaz_prof_seq")
	@Column(name = "id_colaz_prof")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_collocazione", referencedColumnName = "col_id")
	private CollocazioneDto collocazione;
	
	@ManyToOne
	@JoinColumn(name = "fnz_id", referencedColumnName = "fnz_id")
	private FunzionalitaDto funzionalita;
	
	@Column(name = "data_inizio_val")
	private Timestamp dataInizioValidita;
	
	@Column(name = "data_fine_val")
	private Timestamp dataFineValidita;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CollocazioneDto getCollocazione() {
		return collocazione;
	}

	public void setCollocazione(CollocazioneDto collocazione) {
		this.collocazione = collocazione;
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
	





	
}
