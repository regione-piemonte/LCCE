/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.model;

import java.util.List;

public class SelectPaginati{
		
		private String numeroPagina;
		private List<String> selezionati;
		
		public String getNumeroPagina() {
			return numeroPagina;
		}
		public void setNumeroPagina(String numeroPagina) {
			this.numeroPagina = numeroPagina;
		}
		public List<String> getSelezionati() {
			return selezionati;
		}
		public void setSelezionati(List<String> selezionati) {
			this.selezionati = selezionati;
		}
		
		
	}