/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.interfacews.client.authentication2;

import javax.xml.bind.annotation.XmlType;

@XmlType(namespace = "http://dmac.csi.it/")
public class Richiedente {

	protected String codiceFiscaleRichiedente;
	protected String ruolo;
	protected String collCodiceAzienda;
	protected String codiceCollocazione;
	protected String ipClient;
	protected String applicazioneRichiesta;
	protected String applicazioneChiamante;

	public String getCodiceFiscaleRichiedente() {
		return codiceFiscaleRichiedente;
	}

	public void setCodiceFiscaleRichiedente(String codiceFiscaleRichiedente) {
		this.codiceFiscaleRichiedente = codiceFiscaleRichiedente;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public String getCollCodiceAzienda() {
		return collCodiceAzienda;
	}

	public void setCollCodiceAzienda(String collCodiceAzienda) {
		this.collCodiceAzienda = collCodiceAzienda;
	}

	public String getCodiceCollocazione() {
		return codiceCollocazione;
	}

	public void setCodiceCollocazione(String codiceCollocazione) {
		this.codiceCollocazione = codiceCollocazione;
	}

	public String getIpClient() {
		return ipClient;
	}

	public void setIpClient(String ipClient) {
		this.ipClient = ipClient;
	}

	public String getApplicazioneRichiesta() {
		return applicazioneRichiesta;
	}

	public void setApplicazioneRichiesta(String applicazioneRichiesta) {
		this.applicazioneRichiesta = applicazioneRichiesta;
	}

	public String getApplicazioneChiamante() {
		return applicazioneChiamante;
	}

	public void setApplicazioneChiamante(String applicazioneChiamante) {
		this.applicazioneChiamante = applicazioneChiamante;
	}
}