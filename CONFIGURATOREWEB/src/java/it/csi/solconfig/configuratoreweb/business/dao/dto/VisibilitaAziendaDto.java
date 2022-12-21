/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.dto;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "auth_r_utente_visibilita_azienda")
@SequenceGenerator(name = "auth_r_utente_visibilita_azienda_id_seq", sequenceName = "auth_r_utente_visibilita_azienda_id_seq", allocationSize = 1)
public class VisibilitaAziendaDto extends BaseDto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_r_utente_visibilita_azienda_id_seq")
	@Column(name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_utente", referencedColumnName = "id")
	private UtenteDto utenteDto;

	@Column(name = "data_inizio_val")
	private Timestamp dataInizioValidita;

	@Column(name = "data_fine_val")
	private Timestamp dataFineValidita;

	@ManyToOne
	@JoinColumn(name = "id_azienda", referencedColumnName = "id_azienda")
	private AziendaDto aziendaDto;

	@Column(name = "data_operazione")
	private Timestamp dataOperazione;

	@Column(name = "cf_operatore")
	private String cfOperatore;

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

	public AziendaDto getAziendaDto() {
		return aziendaDto;
	}

	public void setAziendaDto(AziendaDto aziendaDto) {
		this.aziendaDto = aziendaDto;
	}

	public String getCfOperatore() {
		return cfOperatore;
	}

	public void setCfOperatore(String cfOperatore) {
		this.cfOperatore = cfOperatore;
	}

	public Timestamp getDataOperazione() {
		return dataOperazione;
	}

	public void setDataOperazione(Timestamp dataOperazione) {
		this.dataOperazione = dataOperazione;
	}
	
	

	
}
