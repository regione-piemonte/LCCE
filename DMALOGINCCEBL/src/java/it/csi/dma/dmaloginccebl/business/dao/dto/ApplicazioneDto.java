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
@Table(name = "auth_d_applicazione")
@SequenceGenerator(name="seq_auth_d_applicazione", sequenceName="seq_auth_d_applicazione",allocationSize=1)
public class ApplicazioneDto extends CatalogoBaseDto {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seq_auth_d_applicazione")
    @Column(name = "id")
	private Long id;
	
	@Column(name = "pin_richiesto")
	private String pinRichiesto;
	
	@Column(name = "urlverifyloginconditions")
	private String urlVerifyLoginConditions;
	
	@Column(name = "oscurato")
	private String oscurato;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPinRichiesto() {
		return pinRichiesto;
	}

	public void setPinRichiesto(String pinRichiesto) {
		this.pinRichiesto = pinRichiesto;
	}

	public String getUrlVerifyLoginConditions() {
		return urlVerifyLoginConditions;
	}

	public void setUrlVerifyLoginConditions(String urlVerifyLoginConditions) {
		this.urlVerifyLoginConditions = urlVerifyLoginConditions;
	}

	public String getOscurato() {
		return oscurato;
	}

	public void setOscurato(String oscurato) {
		this.oscurato = oscurato;
	}	
}
