/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.dto;

import java.math.BigInteger;
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
@Table(name = "auth_r_ruolo_compatibilita")
@SequenceGenerator(name = "auth_r_ruolo_compatibilita_id_ruocomp_seq", sequenceName = "auth_r_ruolo_compatibilita_id_ruocomp_seq", allocationSize = 1)
public class RuoloCompatibilita extends BaseDto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "auth_r_ruolo_compatibilita_id_ruocomp_seq")
	@Column(name = "id_ruocomp")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "id_ruolo", referencedColumnName = "id")
	private RuoloDto ruolo;
	
	@ManyToOne
	@JoinColumn(name = "id_ruolo_compatibile", referencedColumnName = "id")
	private RuoloDto ruoloCompatibile;
	
	@Column(name = "data_inizio_val")
	private Timestamp dataInizioValidita;
	
	@Column(name = "data_fine_val")
	private Timestamp dataFineValidita;
	
	//@Column(name = "data_inserimento")
	//private Timestamp dataInserimento;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RuoloDto getRuolo() {
		return ruolo;
	}

	public void setRuolo(RuoloDto ruolo) {
		this.ruolo = ruolo;
	}

	public RuoloDto getRuoloCompatibile() {
		return ruoloCompatibile;
	}

	public void setRuoloCompatibile(RuoloDto ruoloCompatibile) {
		this.ruoloCompatibile = ruoloCompatibile;
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

	//public Timestamp getDataInserimento() {
	//	return dataInserimento;
	//}

	//public void setDataFineInserimento(Timestamp dataInserimento) {
	//	this.dataInserimento = dataInserimento;
	//}

	public RuoloCompatibilita(BigInteger id, BigInteger id_ruolo, BigInteger id_ruolo_compatibile, Timestamp dataInizioValidita, Timestamp dataFineValidita) {
		this.id = Long.parseLong(id.toString());
		this.ruolo = new RuoloDto();
		this.ruolo.setId(Long.parseLong(id_ruolo.toString()));
		this.ruoloCompatibile = new RuoloDto();
		this.ruoloCompatibile.setId(Long.parseLong(id_ruolo_compatibile.toString()));
		this.dataInizioValidita = dataInizioValidita;
		this.dataFineValidita = dataFineValidita;
	}

	public RuoloCompatibilita(BigInteger id, BigInteger id_ruolo, BigInteger id_ruolo_compatibile, Timestamp dataInizioValidita) {
		this.id = Long.parseLong(id.toString());
		this.ruolo = new RuoloDto();
		this.ruolo.setId(Long.parseLong(id_ruolo.toString()));
		this.ruoloCompatibile = new RuoloDto();
		this.ruoloCompatibile.setId(Long.parseLong(id_ruolo_compatibile.toString()));
		this.dataInizioValidita = dataInizioValidita;
		//this.dataFineValidita = dataFineValidita;
	}

	public RuoloCompatibilita() {
		
	}
	
}
