/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.dto;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "auth_t_batch_utenti_abilitazione_massiva")
@SequenceGenerator(name = "auth_t_batch_utenti_abilitazione_massiva_id_seq", sequenceName = "auth_t_batch_utenti_abilitazione_massiva_id_seq", allocationSize = 1)
public class BatchAbilitazioneMassivaUtentiDto extends BaseDto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_t_batch_utenti_abilitazione_massiva_id_seq")
	@Column(name = "id")
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_batch", referencedColumnName = "id")
	private BatchAbilitazioneMassivaDto idBatch;

	@ManyToOne
	@JoinColumn(name = "utente", referencedColumnName = "id")
	private UtenteDto utente;

	@ManyToOne
	@JoinColumn(name = "ruolo", referencedColumnName = "id")
	private RuoloDto ruolo;

	@ManyToOne
	@JoinColumn(name = "collocazione", referencedColumnName = "col_id")
	private CollocazioneDto collocazione;

	@ManyToOne
	@JoinColumn(name = "abilitazione", referencedColumnName = "id")
	private ApplicazioneDto abilitazione;

	@ManyToOne
	@JoinColumn(name = "profilo", referencedColumnName = "fnz_id")
	private FunzionalitaDto profilo;

	@ManyToOne
	@JoinColumn(name = "errore_utente", referencedColumnName = "id")
	private MessaggiErroreDto erroreUtente;

	@Column(name = "errore_interno")
	private String erroreInterno;

	@Column(name = "data_inizio")
	private Timestamp dataInizio; 

	@Column(name = "data_fine")
	private Timestamp dataFine; 

	@Column(name = "data_fine_abilitazione")
	private Timestamp dataFineValidita;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BatchAbilitazioneMassivaDto getIdBatch() {
		return idBatch;
	}

	public void setIdBatch(BatchAbilitazioneMassivaDto idBatch) {
		this.idBatch = idBatch;
	}

	public UtenteDto getUtente() {
		return utente;
	}

	public void setUtente(UtenteDto utente) {
		this.utente = utente;
	}

	public RuoloDto getRuolo() {
		return ruolo;
	}

	public void setRuolo(RuoloDto ruolo) {
		this.ruolo = ruolo;
	}

	public CollocazioneDto getCollocazione() {
		return collocazione;
	}

	public void setCollocazione(CollocazioneDto collocazione) {
		this.collocazione = collocazione;
	}

	public ApplicazioneDto getAbilitazione() {
		return abilitazione;
	}

	public void setAbilitazione(ApplicazioneDto abilitazione) {
		this.abilitazione = abilitazione;
	}

	public FunzionalitaDto getProfilo() {
		return profilo;
	}

	public void setProfilo(FunzionalitaDto profilo) {
		this.profilo = profilo;
	}

	public MessaggiErroreDto getErroreUtente() {
		return erroreUtente;
	}

	public void setErroreUtente(MessaggiErroreDto erroreUtente) {
		this.erroreUtente = erroreUtente;
	}

	public String getErroreInterno() {
		return erroreInterno;
	}

	public void setErroreInterno(String erroreInterno) {
		this.erroreInterno = erroreInterno;
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

	public Timestamp getDataFineValidita() {
		return dataFineValidita;
	}

	public void setDataFineValidita(Timestamp dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

	
}
