/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dto;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "auth_d_applicazione")
@SequenceGenerator(name = "seq_auth_d_applicazione", sequenceName = "seq_auth_d_applicazione", allocationSize = 1)
public class ApplicazioneDto extends CatalogoBaseDto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_auth_d_applicazione")
	@Column(name = "id")
	private Long id;

	@Column(name = "pin_richiesto")
	private String pinRichiesto;

	@Column(name = "urlverifyloginconditions")
	private String urlVerifyLoginConditions;

	@Column(name = "descrizione_webapp")
	private String descrizioneWebapp;

	@Column(name = "path_immagine")
	private String pathImmagine;

	@Column(name = "redirect_url")
	private String redirectUrl;

	@Column(name = "bottone")
	private String bottone;

	@Column(name = "appunti")
	private String appunti;

	@Column(name = "Flag_configuratore")
	private String flagConfiguratore;

	@Column(name = "oscurato")
	private String flagOscurato;

	@Column(name = "flag_api_blocco_modifica")
	private String flagApiBloccoModifica;

	@OneToMany
	@JoinColumn(name = "id_app", referencedColumnName = "id")
	private List<ApplicazioneCollocazioneDto> applicazioneCollocazioneDtoList;

	public String getFlagOscurato() {
		return flagOscurato;
	}

	public void setFlagOscurato(String flagOscurato) {
		this.flagOscurato = flagOscurato;
	}

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

	public String getDescrizioneWebapp() {
		return descrizioneWebapp;
	}

	public void setDescrizioneWebapp(String descrizioneWebapp) {
		this.descrizioneWebapp = descrizioneWebapp;
	}

	public String getPathImmagine() {
		return pathImmagine;
	}

	public void setPathImmagine(String pathImmagine) {
		this.pathImmagine = pathImmagine;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public String getBottone() {
		return bottone;
	}

	public void setBottone(String bottone) {
		this.bottone = bottone;
	}

	public String getFlagConfiguratore() {
		return flagConfiguratore;
	}

	public void setFlagConfiguratore(String flagConfiguratore) {
		this.flagConfiguratore = flagConfiguratore;
	}

	public String getAppunti() {
		return appunti;
	}

	public void setAppunti(String appunti) {
		this.appunti = appunti;
	}

	public List<ApplicazioneCollocazioneDto> getApplicazioneCollocazioneDtoList() {
		return applicazioneCollocazioneDtoList;
	}

	public void setApplicazioneCollocazioneDtoList(List<ApplicazioneCollocazioneDto> applicazioneCollocazioneDtoList) {
		this.applicazioneCollocazioneDtoList = applicazioneCollocazioneDtoList;
	}

	public String getFlagApiBloccoModifica() {
		return flagApiBloccoModifica;
	}

	public void setFlagApiBloccoModifica(String flagApiBloccoModifica) {
		this.flagApiBloccoModifica = flagApiBloccoModifica;
	}

}
