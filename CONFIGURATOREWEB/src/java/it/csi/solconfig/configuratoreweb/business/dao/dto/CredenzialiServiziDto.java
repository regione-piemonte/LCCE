/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.dto;

import javax.persistence.*;
import java.math.BigInteger;
import java.sql.Timestamp;

@Entity
@Table(name = "auth_t_credenziali_servizi")
@SequenceGenerator(name = "seq_auth_t_credenziali_servizi", sequenceName = "seq_auth_t_credenziali_servizi", allocationSize = 1)
public class CredenzialiServiziDto extends BaseDto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_auth_t_credenziali_servizi")
	@Column(name = "id")
	private Long id;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "data_inizio_validita")
	private Timestamp dataInizioValidita;

	@Column(name = "data_fine_validita")
	private Timestamp dataFineValidita;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_servizio", referencedColumnName = "id")
	private ServiziDto serviziDto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_applicazione", referencedColumnName = "id")
	private ApplicazioneDto applicazioneDto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public ServiziDto getServiziDto() {
		return serviziDto;
	}

	public void setServiziDto(ServiziDto serviziDto) {
		this.serviziDto = serviziDto;
	}

	public ApplicazioneDto getApplicazioneDto() {
		return applicazioneDto;
	}

	public void setApplicazioneDto(ApplicazioneDto applicazioneDto) {
		this.applicazioneDto = applicazioneDto;
	}

	public CredenzialiServiziDto(BigInteger id, BigInteger idServizio, BigInteger idApplicazione, String username,
			String password, Timestamp dataInizioValidita, Timestamp dataFineValidita) {
		this.id = Long.parseLong(id.toString());
		this.serviziDto = new ServiziDto();
		this.serviziDto.setId(Long.parseLong(idServizio.toString()));
		this.applicazioneDto = new ApplicazioneDto();
		this.applicazioneDto.setId(Long.parseLong(idApplicazione.toString()));
		this.username = username;
		this.password = password;
		this.dataInizioValidita = dataInizioValidita;
		this.dataFineValidita = dataFineValidita;
	}

	public CredenzialiServiziDto(BigInteger id, BigInteger idServizio, String username,
								 String password, Timestamp dataInizioValidita, Timestamp dataFineValidita) {
		this.id = id.longValue();
		this.serviziDto = new ServiziDto();
		this.serviziDto.setId(idServizio.longValue());
		this.username = username;
		this.password = password;
		this.dataInizioValidita = dataInizioValidita;
		this.dataFineValidita = dataFineValidita;
	}

	public CredenzialiServiziDto(BigInteger id, BigInteger idServizio, String username,
								 String password, Timestamp dataInizioValidita, Timestamp dataFineValidita, Timestamp dataInserimento) {
		this.id = id.longValue();
		this.serviziDto = new ServiziDto();
		this.serviziDto.setId(idServizio.longValue());
		this.username = username;
		this.password = password;
		this.dataInizioValidita = dataInizioValidita;
		this.dataFineValidita = dataFineValidita;
		setDataInserimento(dataInserimento);
	}

	public CredenzialiServiziDto() {
	}
}
