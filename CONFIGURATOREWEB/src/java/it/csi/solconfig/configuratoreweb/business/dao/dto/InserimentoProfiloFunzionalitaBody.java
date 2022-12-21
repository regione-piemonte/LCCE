/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.dto;

import java.io.Serializable;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import it.csi.solconfig.configuratoreweb.presentation.model.Permesso;


public class InserimentoProfiloFunzionalitaBody implements Serializable {

	private static final long serialVersionUID = 2819588412536720846L;

	@JsonProperty("lista_profili") 
	private List<String> listaProfili;
	@JsonProperty("tipologia_dati_permessi") 
	private List<Permesso> tipologiaDatiPermessi;
	@JsonProperty("lista_ruoli") 
	private List<String> listaRuoli;

	
	public List<String> getListaProfili() {
		return listaProfili;
	}

	public void setListaProfili(List<String> listaProfili) {
		this.listaProfili = listaProfili;
	}

	public List<Permesso> getTipologiaDatiPermessi() {
		return tipologiaDatiPermessi;
	}

	public void setTipologiaDatiPermessi(List<Permesso> tipologiaDatiPermessi) {
		this.tipologiaDatiPermessi = tipologiaDatiPermessi;
	}

	public List<String> getListaRuoli() {
		return listaRuoli;
	}

	public void setListaRuoli(List<String> listaRuoli) {
		this.listaRuoli = listaRuoli;
	}

}
