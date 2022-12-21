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
@Table(name = "auth_r_ruolo_utente")
@SequenceGenerator(name = "seq_auth_r_ruolo_utente", sequenceName = "seq_auth_r_ruolo_utente", allocationSize = 1)
public class RuoloUtenteDto extends BaseDto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_auth_r_ruolo_utente")
	@Column(name = "id")
	private Long id;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_utente", referencedColumnName = "id")
	private UtenteDto utenteDto;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_ruolo", referencedColumnName = "id")
	private RuoloDto ruoloDto;

	@Column(name = "data_inizio_validita")
	private Timestamp dataInizioValidita;

	@Column(name = "data_fine_validita")
	private Timestamp dataFineValidita;

	@Column(name = "data_aggiornamento")
	private Timestamp dataAggiornamento;

	@Column(name = "cf_operatore")
	private String cfOperatore;

	@Column(name = "flag_configuratore", columnDefinition = "bpchar")
	private String flagConfiguratore;
	
	@Column(name = "col_fonte_id", columnDefinition = "int8")
	private Integer colFonteId;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_ruolo_utente")
	private List<AbilitazioneDto> abilitazioneList;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UtenteDto getUtenteDto() {
		return utenteDto;
	}

	public void setUtenteDto(UtenteDto utenteDto) {
		this.utenteDto = utenteDto;
	}

	public RuoloDto getRuoloDto() {
		return ruoloDto;
	}

	public void setRuoloDto(RuoloDto ruoloDto) {
		this.ruoloDto = ruoloDto;
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

	public List<AbilitazioneDto> getAbilitazioneList() {
		return abilitazioneList;
	}

	public void setAbilitazioneList(List<AbilitazioneDto> abilitazioneList) {
		this.abilitazioneList = abilitazioneList;
	}

	public Timestamp getDataAggiornamento() {
		return dataAggiornamento;
	}

	public void setDataAggiornamento(Timestamp dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}

	public String getCfOperatore() {
		return cfOperatore;
	}

	public void setCfOperatore(String cfOperatore) {
		this.cfOperatore = cfOperatore;
	}

	public String getFlagConfiguratore() {
		return flagConfiguratore;
	}

	public void setFlagConfiguratore(String flagConfiguratore) {
		this.flagConfiguratore = flagConfiguratore;
	}

	public Integer getColFonteId() {
		return colFonteId;
	}

	public void setColFonteId(Integer colFonteId) {
		this.colFonteId = colFonteId;
	}
	
	
	
}
