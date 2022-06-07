/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.business.dao.dto;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "auth_l_xml_servizi_richiamati")
@SequenceGenerator(name="seq_auth_l_xml_servizi_richiamati", sequenceName="seq_auth_l_xml_servizi_richiamati",allocationSize=1)
public class ServiziRichiamatiXmlDto extends BaseDto {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seq_auth_l_xml_servizi_richiamati")
    @Column(name = "id")
	private Long id;
	
	@Column(name = "xml_in")
	private byte[] xmlIn;
	
	@Column(name = "xml_out")
	private byte[] xmlOut;
	
	@Column(name = "data_chiamata")
	private Timestamp dataChiamata;
	
	@Column(name = "data_risposta")
	private Timestamp dataRisposta;
	
	@Column(name = "esito")
	private String esito;
	
	@Column(name = "data_aggiornamento")
	private Timestamp dataAggiornamento;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_messaggio", referencedColumnName = "id")
	private MessaggiDto messaggiDto;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_servizio", referencedColumnName = "id")
	private ServiziDto serviziDto;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getXmlIn() {
		return xmlIn;
	}

	public void setXmlIn(byte[] xmlIn) {
		this.xmlIn = xmlIn;
	}

	public byte[] getXmlOut() {
		return xmlOut;
	}

	public void setXmlOut(byte[] xmlOut) {
		this.xmlOut = xmlOut;
	}

	public Timestamp getDataChiamata() {
		return dataChiamata;
	}

	public void setDataChiamata(Timestamp dataChiamata) {
		this.dataChiamata = dataChiamata;
	}

	public Timestamp getDataRisposta() {
		return dataRisposta;
	}

	public void setDataRisposta(Timestamp dataRisposta) {
		this.dataRisposta = dataRisposta;
	}

	public String getEsito() {
		return esito;
	}

	public void setEsito(String esito) {
		this.esito = esito;
	}

	public Timestamp getDataAggiornamento() {
		return dataAggiornamento;
	}

	public void setDataAggiornamento(Timestamp dataAggiornamento) {
		this.dataAggiornamento = dataAggiornamento;
	}

	public MessaggiDto getMessaggiDto() {
		return messaggiDto;
	}

	public void setMessaggiDto(MessaggiDto messaggiDto) {
		this.messaggiDto = messaggiDto;
	}

	public ServiziDto getServiziDto() {
		return serviziDto;
	}

	public void setServiziDto(ServiziDto serviziDto) {
		this.serviziDto = serviziDto;
	}
	
}
