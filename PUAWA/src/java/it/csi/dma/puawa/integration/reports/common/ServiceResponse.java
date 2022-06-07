/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.integration.reports.common;

import java.util.ArrayList;
import java.util.List;

import it.csi.dma.puawa.integration.reportOperazioniConsensi.client.Codifica;
import it.csi.dma.puawa.interfacews.msg.RisultatoCodice;

/**
 * Risposta di base di tutti i servizi. La risposta contiene anche le codifiche
 * che si trovano nel messaggio: - nel messaggio ci sarà solo il codice - nella
 * lista "codifiche" ci saranno le codifiche con le descrizioni usate
 * 
 * @author Alberto Lagna
 * @author Emanuele Scaglia
 * @version $Id: ServiceResponse.java 11218 2017-12-12 10:37:05Z 1118 $
 * 
 */
public class ServiceResponse {
	protected List<Errore> errori = null;
	protected RisultatoCodice esito = RisultatoCodice.SUCCESSO;
	protected List<Codifica> codifiche = null;

	public ServiceResponse() {
		super();
	}

	public ServiceResponse(List<Errore> errori, RisultatoCodice esito, List<Codifica> codifiche) {
		this.errori = errori;
		this.esito = esito;
		this.codifiche = codifiche;
	}

	public ServiceResponse(List<Errore> errori, RisultatoCodice esito) {
		this(errori, esito, null);
	}

	public ServiceResponse(Errore errore) {
		this(null, RisultatoCodice.FALLIMENTO, null);
		errori = new ArrayList<Errore>();
		errori.add(errore);
	}

	public List<Errore> getErrori() {
		return errori;
	}

	public void setErrori(List<Errore> errori) {
		this.errori = errori;
	}

	public RisultatoCodice getEsito() {
		return esito;
	}

	public void setEsito(RisultatoCodice esito) {
		this.esito = esito;
	}

	public List<Codifica> getCodifiche() {
		return codifiche;
	}

	public void setCodifiche(List<Codifica> codifiche) {
		this.codifiche = codifiche;
	}
}