/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.dto;

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
@Table(name = "auth_t_richiesta_credenziali")
@SequenceGenerator(name = "seq_auth_t_richiesta_credenziali", sequenceName = "seq_auth_t_richiesta_credenziali", allocationSize = 1)
public class RichiestaCredenzialiDto extends BaseDto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_auth_t_richiesta_credenziali")
	@Column(name = "id")
	private Long id;

	@Column(name = "data_richiesta")
	
	private Timestamp dataRichiesta;

	@Column(name = "Ticket_Remedy")
	private String ticketRemedy;
	
	@ManyToOne
	@JoinColumn(name = "id_utente", referencedColumnName = "id")
	private UtenteDto utenteDto;
	
	@ManyToOne
	@JoinColumn(name = "id_operatore", referencedColumnName = "id")
	private UtenteDto operatoreDto;

	public UtenteDto getOperatoreDto() {
		return operatoreDto;
	}

	public void setOperatoreDto(UtenteDto operatoreDto) {
		this.operatoreDto = operatoreDto;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getDataRichiesta() {
		return dataRichiesta;
	}

	public void setDataRichiesta(Timestamp dataRichiesta) {
		this.dataRichiesta = dataRichiesta;
	}

	public String getTicketRemedy() {
		return ticketRemedy;
	}

	public void setTicketRemedy(String ticketRemedy) {
		this.ticketRemedy = ticketRemedy;
	}

	public UtenteDto getUtenteDto() {
		return utenteDto;
	}

	public void setUtenteDto(UtenteDto utenteDto) {
		this.utenteDto = utenteDto;
	}

}
