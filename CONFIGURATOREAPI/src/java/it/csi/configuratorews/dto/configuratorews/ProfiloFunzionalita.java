/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.dto.configuratorews;

import java.util.List;
import java.util.Objects;

import it.csi.configuratorews.interfacews.client.ruoliUtente.Ruolo;

public class ProfiloFunzionalita extends DatiTecnici implements Comparable<ProfiloFunzionalita> {
	
	private static final long serialVersionUID = -3162429342013637982L;
	private String codiceProfilo;
	private String descrizioneProfilo;
	private List<FunzionalitaTecnici> funzionalita;
	private List<Ruolo> listaRuoli;

	public String getCodiceProfilo() {
		return codiceProfilo;
	}

	public void setCodiceProfilo(String codiceProfilo) {
		this.codiceProfilo = codiceProfilo;
	}

	public String getDescrizioneProfilo() {
		return descrizioneProfilo;
	}

	public void setDescrizioneProfilo(String descrizioneProfilo) {
		this.descrizioneProfilo = descrizioneProfilo;
	}

	public List<FunzionalitaTecnici> getFunzionalita() {
		return funzionalita;
	}

	public void setFunzionalita(List<FunzionalitaTecnici> funzionalita) {
		this.funzionalita = funzionalita;
	}

	public List<Ruolo> getListaRuoli() {
		return listaRuoli;
	}

	public void setListaRuoli(List<Ruolo> listaRuoli) {
		this.listaRuoli = listaRuoli;
	}

	@Override
	public String toString() {
		return "ProfiloFunzionalita [codiceProfilo=" + codiceProfilo + ", descrizioneProfilo=" + descrizioneProfilo + ", funzionalita=" + funzionalita
				+ ", getId()=" + getId() + ", getInizioValidita()=" + getInizioValidita() + ", getFineValidita()=" + getFineValidita() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(codiceProfilo, descrizioneProfilo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProfiloFunzionalita other = (ProfiloFunzionalita) obj;
		return Objects.equals(codiceProfilo, other.codiceProfilo) && Objects.equals(descrizioneProfilo, other.descrizioneProfilo);
	}

	@Override
	public int compareTo(ProfiloFunzionalita o) {
		return this.getCodiceProfilo().compareTo(o.getCodiceProfilo());
	}

}
