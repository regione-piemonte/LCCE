/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.dto;

import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

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

	@Column(name = "oscurato")
	private String oscurato;

	@Column(name = "Flag_configuratore")
	private String flagConfiguratore;
	
	@Column(name = "invio_mail_aura")
	private Boolean invioMailAura;

	@OneToMany(mappedBy = "applicazioneDto", cascade = CascadeType.ALL)
	private List<ApplicazioneCollocazioneDto> applicazioneCollocazioneList;

	public List<ApplicazioneCollocazioneDto> getApplicazioneCollocazioneList() {
		return applicazioneCollocazioneList;
	}

	public void setApplicazioneCollocazioneList(List<ApplicazioneCollocazioneDto> applicazioneCollocazioneList) {
		this.applicazioneCollocazioneList = applicazioneCollocazioneList;
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
	
	public Boolean getInvioMailAura() {
		return invioMailAura;
	}

	public void setInvioMailAura(Boolean invioMailAura) {
		this.invioMailAura = invioMailAura;
	}

	public String getOscurato() {
		return oscurato;
	}

	public void setOscurato(String oscurato) {
		this.oscurato = oscurato;
	}

	@Override
	public int hashCode() {
		return Objects.hash(applicazioneCollocazioneList, bottone, descrizioneWebapp, flagConfiguratore, id, oscurato,
				pathImmagine, pinRichiesto, redirectUrl, urlVerifyLoginConditions);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ApplicazioneDto other = (ApplicazioneDto) obj;
		return Objects.equals(applicazioneCollocazioneList, other.applicazioneCollocazioneList)
				&& Objects.equals(bottone, other.bottone) && Objects.equals(descrizioneWebapp, other.descrizioneWebapp)
				&& Objects.equals(flagConfiguratore, other.flagConfiguratore) && Objects.equals(id, other.id)
				&& Objects.equals(oscurato, other.oscurato) && Objects.equals(pathImmagine, other.pathImmagine)
				&& Objects.equals(pinRichiesto, other.pinRichiesto) && Objects.equals(redirectUrl, other.redirectUrl)
				&& Objects.equals(urlVerifyLoginConditions, other.urlVerifyLoginConditions);
	}


}
