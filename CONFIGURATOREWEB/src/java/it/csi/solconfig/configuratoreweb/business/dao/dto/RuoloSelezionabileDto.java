/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.dto;

import java.math.BigInteger;
import java.sql.Timestamp;

import javax.persistence.*;

import it.csi.solconfig.configuratoreweb.presentation.model.RuoloDTO;

@Entity
@Table(name = "auth_r_ruolo_ruoli")
@SequenceGenerator(name = "auth_r_ruolo_ruoli_id_seq", sequenceName = "auth_r_ruolo_ruoli_id_seq", allocationSize = 1)
public class RuoloSelezionabileDto extends BaseDto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_r_ruolo_ruoli_id_seq")
	@Column(name = "id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_ruolo_operatore", referencedColumnName = "id")
	private RuoloDto ruoloOperatore;

//	@Column(name = "id_ruolo_operatore")
//	private Long idRuoloOperatore;

	@ManyToOne
	@JoinColumn(name = "id_ruolo_selezionabile", referencedColumnName = "id")
	private RuoloDto ruoloSelezionabile;
	
//	@Column(name = "id_ruolo_selezionabile")
//	private Long idRuoloSelezionabile;

	@ManyToOne
	@JoinColumn(name = "col_tipo_id", referencedColumnName = "col_tipo_id")
	private CollocazioneTipoDto collocazioneTipo;
	
//	@Column(name = "col_tipo_id")
//	private Long idTipoCollocazioneDto;

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

//	public Long getIdRuoloOperatore() {
//		return idRuoloOperatore;
//	}
//
//	public void setIdRuoloOperatore(Long idRuoloOperatore) {
//		this.idRuoloOperatore = idRuoloOperatore;
//	}
//
//	public Long getIdRuoloSelezionabile() {
//		return idRuoloSelezionabile;
//	}
//
//	public void setIdRuoloSelezionabile(Long idRuoloSelezionabile) {
//		this.idRuoloSelezionabile = idRuoloSelezionabile;
//	}


	public RuoloDto getRuoloOperatore() {
		return ruoloOperatore;
	}

	public void setRuoloOperatore(RuoloDto ruoloOperatore) {
		this.ruoloOperatore = ruoloOperatore;
	}

	public RuoloDto getRuoloSelezionabile() {
		return ruoloSelezionabile;
	}

	public void setRuoloSelezionabile(RuoloDto ruoloSelezionabile) {
		this.ruoloSelezionabile = ruoloSelezionabile;
	}
	
	public CollocazioneTipoDto getCollocazioneTipo() {
		return collocazioneTipo;
	}

	public void setCollocazioneTipo(CollocazioneTipoDto collocazioneTipo) {
		this.collocazioneTipo = collocazioneTipo;
	}

//	public Long getIdTipoCollocazioneDto() {
//		return idTipoCollocazioneDto;
//	}
//	
//	public void setIdTipoCollocazioneDto(Long idTipoCollocazioneDto) {
//		this.idTipoCollocazioneDto = idTipoCollocazioneDto;
//	}

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

	public RuoloSelezionabileDto() {
		
	}

	public RuoloSelezionabileDto(BigInteger id, BigInteger ruoloOperatore, BigInteger ruoloSelezionabile,
			BigInteger collocazioneTipo, Timestamp dataInizioValidita) {
		super();
		this.id = id.longValue();
		RuoloDto ruoloOp = new RuoloDto();
		ruoloOp.setId(ruoloOperatore.longValue());
		this.setRuoloOperatore(ruoloOp);
		
		RuoloDto ruoloSelez = new RuoloDto();
		ruoloSelez.setId(ruoloSelezionabile.longValue());
		this.setRuoloOperatore(ruoloSelez);
		
		CollocazioneTipoDto coll = new CollocazioneTipoDto();
		coll.setIdColTipo(collocazioneTipo.longValue());
		this.setCollocazioneTipo(coll);
		
		this.dataInizioValidita = dataInizioValidita;
	}

	public RuoloSelezionabileDto(BigInteger id, BigInteger ruoloOperatore, BigInteger ruoloSelezionabile,
			BigInteger collocazioneTipo, Timestamp dataInizioValidita, Timestamp dataFineValidita) {
		super();
		this.id = id.longValue();
		RuoloDto ruoloOp = new RuoloDto();
		ruoloOp.setId(ruoloOperatore.longValue());
		this.setRuoloOperatore(ruoloOp);
		
		RuoloDto ruoloSelez = new RuoloDto();
		ruoloSelez.setId(ruoloSelezionabile.longValue());
		this.setRuoloOperatore(ruoloSelez);
		
		CollocazioneTipoDto coll = new CollocazioneTipoDto();
		coll.setIdColTipo(collocazioneTipo.longValue());
		this.setCollocazioneTipo(coll);
		
		this.dataInizioValidita = dataInizioValidita;
		this.dataFineValidita = dataFineValidita;
	}
}
