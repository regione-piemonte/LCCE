/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dto;

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
@Table(name = "auth_r_funzionalita_tipologia_dato_permesso")
@SequenceGenerator(name = "auth_r_funzionalita_tipologia_funzionalita_tipologia_dato_p_seq", sequenceName = "auth_r_funzionalita_tipologia_funzionalita_tipologia_dato_p_seq", allocationSize = 1)
public class PermessoFunzionalitaDto extends BaseDto{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_r_funzionalita_tipologia_funzionalita_tipologia_dato_p_seq")
	@Column(name = "funzionalita_tipologia_dato_permesso_id")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "fnz_id", referencedColumnName = "fnz_id")
	private FunzionalitaDto funzionalita;
	@ManyToOne
	@JoinColumn(name = "permesso_id", referencedColumnName = "permesso_id")
	private PermessoDto permesso;
	@ManyToOne
	@JoinColumn(name = "tipologia_dato_id", referencedColumnName = "tipologia_dato_id")
	private TipologiaDatoDto tipologiaDato;
	
	@Column(name = "data_inizio_validita")
	private Timestamp dataInizioValidita;

	@Column(name = "data_fine_validita")
	private Timestamp dataFineValidita;
	
	@Column(name = "cf_operatore")
	private String cfOperatore;

	@Column(name = "data_cancellazione")
	private Timestamp dataCancellazione;
	
	@Column(name = "data_aggiornamento")
	private Timestamp dataAggiornamento;
	
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
	public String getCfOperatore() {
		return cfOperatore;
	}
	public void setCfOperatore(String cfOperatore) {
		this.cfOperatore = cfOperatore;
	}
	public Timestamp getDataCancellazione() {
		return dataCancellazione;
	}
	public void setDataCancellazione(Timestamp dataCancellazione) {
		this.dataCancellazione = dataCancellazione;
	}
	public Timestamp getDataAggiornamento() {
		return dataAggiornamento;
	}
	public void setDataAggiornamento(Timestamp dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public FunzionalitaDto getFunzionalita() {
		return funzionalita;
	}
	public void setFunzionalita(FunzionalitaDto funzionalita) {
		this.funzionalita = funzionalita;
	}
	public PermessoDto getPermesso() {
		return permesso;
	}
	public void setPermesso(PermessoDto permesso) {
		this.permesso = permesso;
	}
	public TipologiaDatoDto getTipologiaDato() {
		return tipologiaDato;
	}
	public void setTipologiaDato(TipologiaDatoDto tipologiaDato) {
		this.tipologiaDato = tipologiaDato;
	}
	
}
