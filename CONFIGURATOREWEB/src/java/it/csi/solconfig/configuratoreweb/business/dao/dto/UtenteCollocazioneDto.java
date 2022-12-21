/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.dto;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "auth_r_utente_collocazione")
@SequenceGenerator(name = "auth_r_utente_collocazione_utecol_id_seq", sequenceName = "auth_r_utente_collocazione_utecol_id_seq", allocationSize = 1)
public class UtenteCollocazioneDto extends BaseDto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_r_utente_collocazione_utecol_id_seq")
	@Column(name = "utecol_id")
	private Long id_utecol;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ute_id", referencedColumnName = "id")
	private UtenteDto utenteDto;

	@ManyToOne
	@JoinColumn(name = "col_id", referencedColumnName = "col_id")
	private CollocazioneDto collocazioneDto;

	@Column(name = "data_inizio_validita")
	private Timestamp dataInizioValidita;

	@Column(name = "data_fine_validita")
	private Timestamp dataFineValidita;

	@Column(name = "data_aggiornamento")
	private Timestamp dataAggiornamento;

	@Column(name = "data_cancellazione")
	private Timestamp dataCancellazione;

	@Column(name = "cf_operatore")
	private String cfOperatore;
	
	@Column(name = "Flag_configuratore")
	private String flagConfiguratore;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "utecol_id")
	private List<AbilitazioneDto> abilitazioneList;
	
	@Column(name = "col_fonte_id", columnDefinition = "int8")
	private Integer colFonteId;
	

	public Long getId_utecol() {
		return id_utecol;
	}

	public void setId_utecol(Long id_utecol) {
		this.id_utecol = id_utecol;
	}

	public UtenteDto getUtenteDto() {
		return utenteDto;
	}

	public void setUtenteDto(UtenteDto utenteDto) {
		this.utenteDto = utenteDto;
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

	public String getCfOperatore() {
		return cfOperatore;
	}

	public void setCfOperatore(String cfOperatore) {
		this.cfOperatore = cfOperatore;
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

	public String getFlagConfiguratore() {
		return flagConfiguratore;
	}

	public void setFlagConfiguratore(String flagConfiguratore) {
		this.flagConfiguratore = flagConfiguratore;
	}

	public List<AbilitazioneDto> getAbilitazioneList() {
		return abilitazioneList;
	}

	public void setAbilitazioneList(List<AbilitazioneDto> abilitazioneList) {
		this.abilitazioneList = abilitazioneList;
	}

	public Integer getColFonteId() {
		return colFonteId;
	}

	public void setColFonteId(Integer colFonteId) {
		this.colFonteId = colFonteId;
	}
	
	
}
