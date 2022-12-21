/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dto;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "auth_t_preferenza")
@SequenceGenerator(name = "auth_t_preferenza_preferenza_id_seq", sequenceName = "auth_t_preferenza_preferenza_id_seq", allocationSize = 1)
public class PreferenzaDto extends BaseDto {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_t_preferenza_preferenza_id_seq")
	@Column(name = "preferenza_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_utente", referencedColumnName = "id")
	private UtenteDto utente;

	@ManyToOne
	@JoinColumn(name = "id_applicazione", referencedColumnName = "id")
	private ApplicazioneDto applicazioneDto;
	
	@ManyToOne
	@JoinColumn(name = "id_ruolo", referencedColumnName = "id")
	private RuoloDto ruoloDto;
	
	@ManyToOne
	@JoinColumn(name = "col_id", referencedColumnName = "col_id")
	private CollocazioneDto collocazioneDto;
	
	@Column(name = "data_aggiornamento")
	private Timestamp dataAggiornamento;
	
	@Column(name = "data_cancellazione")
	private Timestamp dataCancellazione;
	
	@Column(name = "data_inizio_validita")
	private Timestamp dataInizioValidita;
	
	@Column(name = "data_fine_validita")
	private Timestamp dataFineValidita;
	
	@OneToMany
	@JoinColumn(name = "preferenza_id")
	private List<PreferenzaSalvataDto> preferenzaSalvata;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UtenteDto getUtente() {
		return utente;
	}

	public void setUtente(UtenteDto utente) {
		this.utente = utente;
	}

	public ApplicazioneDto getApplicazioneDto() {
		return applicazioneDto;
	}

	public void setApplicazioneDto(ApplicazioneDto applicazioneDto) {
		this.applicazioneDto = applicazioneDto;
	}

	public RuoloDto getRuoloDto() {
		return ruoloDto;
	}

	public void setRuoloDto(RuoloDto ruoloDto) {
		this.ruoloDto = ruoloDto;
	}

	public CollocazioneDto getCollocazioneDto() {
		return collocazioneDto;
	}

	public void setCollocazioneDto(CollocazioneDto collocazioneDto) {
		this.collocazioneDto = collocazioneDto;
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

	public List<PreferenzaSalvataDto> getPreferenzaSalvata() {
		return preferenzaSalvata;
	}

	public void setPreferenzaSalvata(List<PreferenzaSalvataDto> preferenzaSalvata) {
		this.preferenzaSalvata = preferenzaSalvata;
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
