/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.dto;

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
@Table(name = "auth_l_csi_log_audit")
@SequenceGenerator(name = "seq_auth_l_csi_log_audit", sequenceName = "seq_auth_l_csi_log_audit", allocationSize = 1)
public class CsiLogAuditDto extends BaseDto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_auth_l_csi_log_audit")
	@Column(name = "id")
	private Long id;
	
	//identificativo applicazione che ha effettuato l'operazione
	@Column(name = "id_app")
	private String idApplicazione;
	
	//id della macchina utilizzata
	@Column(name = "ip_address")
	private String ipAdress;
	
	//utente che ha effettuato l'operazione
	@Column(name = "cf_utente")
	private String codiceFiscaleUtente;

	//operazione effettuata: login/logout/read/insert/update/delete/merge
	@Column(name = "operazione")
	private String operazione;
	
	@ManyToOne
	@JoinColumn(name = "id_cat_log_audit_conf", referencedColumnName = "id")
	private CatalogoLogAuditConfDto catalogoLogAuditConf;

	@Column(name = "uu_id")
	private String uuId;
	
	//id univoco della chiamata http 
	@Column(name = "id_request")
	private String idRequest;
	
	//codice del servizio invocato
	@Column(name = "xcod_servizio")
	private String xcodServizio;
	
	@ManyToOne
	@JoinColumn(name = "ruolo", referencedColumnName = "id")
	private RuoloDto ruoloDto;
	
	@Column(name = "cf_oggetto_operazione")
	private String cfOggettoOperazione;
	
	@Column(name = "ogg_oper")
	private String oggOper;
	
	@Column(name = "key_oper")
	private String keyOper;
	

	//getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getIdApplicazione() {
		return idApplicazione;
	}

	public void setId_app(String idApplicazione) {
		this.idApplicazione = idApplicazione;
	}

	public String getIpAdress() {
		return ipAdress;
	}

	public void setIdAdress(String ipAdress) {
		this.ipAdress = ipAdress;
	}

	public void setIdApplicazione(String idApplicazione) {
		this.idApplicazione = idApplicazione;
	}

	public void setIpAdress(String ipAdress) {
		this.ipAdress = ipAdress;
	}

	public String getCodiceFiscaleUtente() {
		return codiceFiscaleUtente;
	}

	public void setCodiceFiscaleUtente(String codiceFiscaleUtente) {
		this.codiceFiscaleUtente = codiceFiscaleUtente;
	}

	public String getUuId() {
		return uuId;
	}

	public void setUuId(String uuId) {
		this.uuId = uuId;
	}

	public String getIdRequest() {
		return idRequest;
	}

	public void setIdRequest(String idRequest) {
		this.idRequest = idRequest;
	}

	public String getXcodServizio() {
		return xcodServizio;
	}

	public void setXcodServizio(String xcodServizio) {
		this.xcodServizio = xcodServizio;
	}

	public CatalogoLogAuditConfDto getCatalogoLogAuditConf() {
		return catalogoLogAuditConf;
	}

	public void setCatalogoLogAuditConf(CatalogoLogAuditConfDto catalogoLogAuditConf) {
		this.catalogoLogAuditConf = catalogoLogAuditConf;
	}

	public String getOperazione() {
		return operazione;
	}

	public void setOperazione(String operazione) {
		this.operazione = operazione;
	}

	public String getCfOggettoOperazione() {
		return cfOggettoOperazione;
	}

	public void setCfOggettoOperazione(String cfOggettoOperazione) {
		this.cfOggettoOperazione = cfOggettoOperazione;
	}

	public String getOggOper() {
		return oggOper;
	}

	public void setOggOper(String oggOper) {
		this.oggOper = oggOper;
	}

	public String getKeyOper() {
		return keyOper;
	}

	public void setKeyOper(String keyOper) {
		this.keyOper = keyOper;
	}

	public RuoloDto getRuoloDto() {
		return ruoloDto;
	}

	public void setRuoloDto(RuoloDto ruoloDto) {
		this.ruoloDto = ruoloDto;
	}
	
	
	
}
