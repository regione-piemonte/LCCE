/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.dto;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "auth_d_azienda")
@SequenceGenerator(name = "auth_d_azienda_id_azienda_seq", sequenceName = "auth_d_azienda_id_azienda_seq", allocationSize = 1)
public class AziendaDto{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_d_azienda_id_azienda_seq")
	@Column(name = "id_azienda")
	private Integer idAzienda;

	@Column(name = "cod_azienda")
	private String codAzienda;

	@Column(name = "desc_azienda")
	private String descAzienda;

	@Column(name = "data_inizio_val")
	private Timestamp dataInizioValidita;

	@Column(name = "data_fine_val")
	private Timestamp dataFineValidita;

	

	public Integer getIdAzienda() {
		return idAzienda;
	}

	public void setIdAzienda(Integer idAzienda) {
		this.idAzienda = idAzienda;
	}

	public String getCodAzienda() {
		return codAzienda;
	}

	public void setCodAzienda(String codAzienda) {
		this.codAzienda = codAzienda;
	}

	public String getDescAzienda() {
		return descAzienda;
	}

	public void setDescAzienda(String descAzienda) {
		this.descAzienda = descAzienda;
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
