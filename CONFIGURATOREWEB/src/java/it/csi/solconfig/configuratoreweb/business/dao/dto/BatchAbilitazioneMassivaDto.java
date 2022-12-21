/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.dto;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name = "auth_t_batch_abilitazione_massiva")
@SequenceGenerator(name = "auth_t_batch_abilitazione_massiva_id_seq", sequenceName = "auth_t_batch_abilitazione_massiva_id_seq", allocationSize = 1)
public class BatchAbilitazioneMassivaDto extends BaseDto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_t_batch_abilitazione_massiva_id_seq")
	@Column(name = "id")
	private Long id;

	@Column(name = "codice_fiscale_operatore")
	private String codiceFiscaleOperatore;

	@Column(name = "data_inizio")
	private Timestamp dataInizio;

	@Column(name = "data_fine")
	private Timestamp dataFine;

	@Column(name = "disabilitazione")
	private boolean disabilitazione;

	@ManyToOne
	@JoinColumn(name = "batch_stato_id", referencedColumnName = "batch_stato_id")
	private BatchStatoDto statoBatch;
	
	@Column(name = "csv_utenti_inseriti")
	private byte[] csvUtentiInseriti;
	
	@Column(name = "csv_utenti_non_inseriti")
	private byte[] csvUtentiNonInseriti;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodiceFiscaleOperatore() {
		return codiceFiscaleOperatore;
	}

	public void setCodiceFiscaleOperatore(String codiceFiscaleOperatore) {
		this.codiceFiscaleOperatore = codiceFiscaleOperatore;
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

	public boolean isDisabilitazione() {
		return disabilitazione;
	}

	public void setDisabilitazione(boolean disabilitazione) {
		this.disabilitazione = disabilitazione;
	}

	public BatchStatoDto getStatoBatch() {
		return statoBatch;
	}

	public void setStatoBatch(BatchStatoDto statoBatch) {
		this.statoBatch = statoBatch;
	}

	public byte[] getCsvUtentiInseriti() {
		return csvUtentiInseriti;
	}

	public void setCsvUtentiInseriti(byte[] csvUtentiInseriti) {
		this.csvUtentiInseriti = csvUtentiInseriti;
	}

	public byte[] getCsvUtentiNonInseriti() {
		return csvUtentiNonInseriti;
	}

	public void setCsvUtentiNonInseriti(byte[] csvUtentiNonInseriti) {
		this.csvUtentiNonInseriti = csvUtentiNonInseriti;
	}
	
	

	
}
