/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.dto;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Table(name = "auth_t_faq_credenziali")
public class FaqRuparDto extends BaseDto {

	@Id
	@Column(name = "id")
	private Long id;

	@Column(name = "valore")
	private byte[] valore;

	@Column(name = "descrizione")
	private String descrizione;

	@Column(name = "data_chiusura")
	private Timestamp dataChiusura;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getValore() {
		return valore;
	}

	public void setValore(byte[] valore) {
		this.valore = valore;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Timestamp getDataModifica() {
		return dataChiusura;
	}

	public void setDataModifica(Timestamp dataModifica) {
		this.dataChiusura = dataModifica;
	}

	

}
