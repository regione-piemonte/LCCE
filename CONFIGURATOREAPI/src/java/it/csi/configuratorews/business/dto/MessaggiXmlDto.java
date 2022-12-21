/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dto;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "auth_l_xml_messaggi")
@SequenceGenerator(name="seq_auth_l_xml_messaggi", sequenceName="seq_auth_l_xml_messaggi",allocationSize=1)
public class MessaggiXmlDto extends BaseDto {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="seq_auth_l_xml_messaggi")
    @Column(name = "id")
	private Long id;
	
	@Column(name = "xml_in")
	private byte[] xmlIn;
	
	@Column(name = "xml_out")
	private byte[] xmlOut;
	
	@Column(name = "data_aggiornamento")
	private Timestamp dataAggiornamento;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_messaggio", referencedColumnName = "id")
	private MessaggiDto messaggiDto;

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
	
}
