/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dto;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "auth_d_funzionalita_tipo")
@SequenceGenerator(name = "auth_d_funzionalita_tipo_fnz_tipo_id_seq", sequenceName = "auth_d_funzionalita_tipo_fnz_tipo_id_seq", allocationSize = 1)
public class TipoFunzionalitaDto extends BaseDto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_d_funzionalita_tipo_fnz_tipo_id_seq")
	@Column(name = "fnz_tipo_Id")
	private Long idTipoFunzione;

	@Column(name = "fnz_tipo_codice")
	private String codiceTipoFunzione;

	@Column(name = "fnz_tipo_descrizione")
	private String descrizioneTipoFunzione;

	@Column(name = "data_inizio_validita")
	private Timestamp dataInizioValidita;

	@Column(name = "data_fine_validita")
	private Timestamp dataFineValidita;

	public Long getIdTipoFunzione() {
		return idTipoFunzione;
	}

	public void setIdTipoFunzione(Long idTipoFunzione) {
		this.idTipoFunzione = idTipoFunzione;
	}

	public String getCodiceTipoFunzione() {
		return codiceTipoFunzione;
	}

	public void setCodiceTipoFunzione(String codiceTipoFunzione) {
		this.codiceTipoFunzione = codiceTipoFunzione;
	}

	public String getDescrizioneTipoFunzione() {
		return descrizioneTipoFunzione;
	}

	public void setDescrizioneTipoFunzione(String descrizioneTipoFunzione) {
		this.descrizioneTipoFunzione = descrizioneTipoFunzione;
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
}
