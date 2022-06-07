/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.integration.log;

import javax.xml.bind.annotation.XmlType;

@XmlType(namespace = "http://dmac.csi.it/")
public class LogAuditRichiedente {

	private String codiceFiscaleRichiedente;
	private String codiceRuoloRichiedente;
	private String collCodiceAziendaRichiedente;
	private String codiceCollocazioneRichiedente;
	private String applicazioneRichiesta;
	private String applicazioneChiamante;
	private String ipChiamante;

	// Informazioni per il logAudit della pagina di Reportistica
	private String tipoReport;
	private String dataIniziale;
	private String dataFinale;
	private String descrizioneCollocazione;

	public String getTipoReport() {
		return tipoReport;
	}

	public void setTipoReport(String tipoReport) {
		this.tipoReport = tipoReport;
	}

	public String getDataIniziale() {
		return dataIniziale;
	}

	public void setDataIniziale(String dataIniziale) {
		this.dataIniziale = dataIniziale;
	}

	public String getDataFinale() {
		return dataFinale;
	}

	public void setDataFinale(String dataFinale) {
		this.dataFinale = dataFinale;
	}

	public String getDescrizioneCollocazione() {
		return descrizioneCollocazione;
	}

	public void setDescrizioneCollocazione(String descrizioneCollocazione) {
		this.descrizioneCollocazione = descrizioneCollocazione;
	}

	public String getCodiceRuoloRichiedente() {
		return codiceRuoloRichiedente;
	}

	public void setCodiceRuoloRichiedente(String codiceRuoloRichiedente) {
		this.codiceRuoloRichiedente = codiceRuoloRichiedente;
	}

	public String getCodiceFiscaleRichiedente() {
		return codiceFiscaleRichiedente;
	}

	public void setCodiceFiscaleRichiedente(String codiceFiscaleRichiedente) {
		this.codiceFiscaleRichiedente = codiceFiscaleRichiedente;
	}

	public String getApplicazioneChiamante() {
		return applicazioneChiamante;
	}

	public void setApplicazioneChiamante(String applicazioneChiamante) {
		this.applicazioneChiamante = applicazioneChiamante;
	}

	public String getIpChiamante() {
		return ipChiamante;
	}

	public void setIpChiamante(String ipChiamante) {
		this.ipChiamante = ipChiamante;
	}

	public String getCodiceCollocazioneRichiedente() {
		return codiceCollocazioneRichiedente;
	}

	public void setCodiceCollocazioneRichiedente(String codiceCollocazioneRichiedente) {
		this.codiceCollocazioneRichiedente = codiceCollocazioneRichiedente;
	}

	public String getCollCodiceAziendaRichiedente() {
		return collCodiceAziendaRichiedente;
	}

	public void setCollCodiceAziendaRichiedente(String collCodiceAziendaRichiedente) {
		this.collCodiceAziendaRichiedente = collCodiceAziendaRichiedente;
	}

	public String getApplicazioneRichiesta() {
		return applicazioneRichiesta;
	}

	public void setApplicazioneRichiesta(String applicazioneRichiesta) {
		this.applicazioneRichiesta = applicazioneRichiesta;
	}
}