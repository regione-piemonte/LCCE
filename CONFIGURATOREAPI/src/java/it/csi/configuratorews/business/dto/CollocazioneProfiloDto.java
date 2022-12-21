/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dto;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "auth_r_colazienda_profilo")
@SequenceGenerator(name = "auth_r_colazienda_profilo_id_colaz_prof_seq", sequenceName = "auth_r_colazienda_profilo_id_colaz_prof_seq", allocationSize = 1)
public class CollocazioneProfiloDto extends BaseDto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_r_colazienda_profilo_id_colaz_prof_seq")
	@Column(name = "id_colaz_prof")
	private Long idColProf;

	@ManyToOne
	@JoinColumn(name = "id_collocazione", referencedColumnName = "col_id")
	private CollocazioneDto collocazioneDto;

	@ManyToOne
	@JoinColumn(name = "fnz_id", referencedColumnName = "fnz_id")
	private FunzionalitaDto funzionalitaDto;

	@Column(name = "data_inizio_val")
	private Timestamp dataInizioValidita;

	@Column(name = "data_fine_val")
	private Timestamp dataFineValidita;

	public Long getIdColProf() {
		return idColProf;
	}

	public void setIdColProf(Long idColProf) {
		this.idColProf = idColProf;
	}

	public CollocazioneDto getCollocazioneDto() {
		return collocazioneDto;
	}

	public void setCollocazioneDto(CollocazioneDto collocazioneDto) {
		this.collocazioneDto = collocazioneDto;
	}

	public FunzionalitaDto getFunzionalitaDto() {
		return funzionalitaDto;
	}

	public void setFunzionalitaDto(FunzionalitaDto funzionalitaDto) {
		this.funzionalitaDto = funzionalitaDto;
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
