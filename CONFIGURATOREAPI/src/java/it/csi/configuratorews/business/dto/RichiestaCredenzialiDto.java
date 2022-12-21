/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dto;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name = "auth_t_Richiesta_credenziali")
@SequenceGenerator(name = "seq_auth_t_Richiesta_credenziali", sequenceName = "seq_auth_t_Richiesta_credenziali", allocationSize = 1)
public class RichiestaCredenzialiDto extends BaseDto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_auth_t_Richiesta_credenziali")
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
