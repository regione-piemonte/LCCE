/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.business.dao.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "auth_t_configurazione")
@SequenceGenerator(name="seq_auth_t_configurazione", sequenceName="seq_auth_t_configurazione",allocationSize=1)
public class ConfigurazioneDto extends BaseDto{

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seq_auth_t_configurazione")
    @Column(name = "id")
	private Long id;
	
    @Column(name = "chiave")
	private String chiave;
	
    @Column(name = "valore")
	private String valore;
	
    @Column(name = "descrizione")
	private String descrizione;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChiave() {
		return chiave;
	}

	public void setChiave(String chiave) {
		this.chiave = chiave;
	}

	public String getValore() {
		return valore;
	}

	public void setValore(String valore) {
		this.valore = valore;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	
    
}
