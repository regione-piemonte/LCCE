/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.model;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import it.csi.solconfig.configuratoreweb.presentation.constants.ConstantsWebApp;
import it.csi.solconfig.configuratoreweb.util.Utils;

public class SolModel {
	private Long idSol;
	private Long idCollocazione;
	private Long idRuolo;
	private Long idProfilo;
	private Long idProfiloOld;
	private Timestamp validita;
	
	public SolModel(String sol) {
		SimpleDateFormat sdf = new SimpleDateFormat(ConstantsWebApp.DATE_PATTERN);
		String[] split = sol.split("\\|");
		Long idApplicazione;
        Long idCollocazione;
        Long idFunzioneNuova;
        Long idRuolo;
        Timestamp dataFineValidita;
        Long idFunzioneVecchia;

        try {
            idApplicazione = Long.parseLong(split[0]);
            idCollocazione = Long.parseLong(split[1]);
            idFunzioneNuova = Long.parseLong(split[2]);
            idRuolo  = Long.parseLong(split[3]);
            dataFineValidita = split.length > 4 && !Utils.isEmpty(split[4])
                    ? Utils.truncateTimestamp(Utils.toTimestamp(sdf.parse(split[4])), false)
                    : null;
            idFunzioneVecchia = split.length > 5 && !Utils.isEmpty(split[5]) ? Long.parseLong(split[5]) : null;
        } catch (IndexOutOfBoundsException | NumberFormatException | ParseException e) {
            return;
        }
        
        this.idSol = idApplicazione;
        this.idCollocazione = idCollocazione;
        this.idRuolo = idRuolo;
        this.idProfilo = idFunzioneNuova;
        this.idProfiloOld = idFunzioneVecchia;
        this.validita = dataFineValidita;
	}
	
	
	public Long getIdSol() {
		return idSol;
	}
	public void setIdSol(Long idSol) {
		this.idSol = idSol;
	}
	public Long getIdCollocazione() {
		return idCollocazione;
	}
	public void setIdCollocazione(Long idCollocazione) {
		this.idCollocazione = idCollocazione;
	}
	public Long getIdRuolo() {
		return idRuolo;
	}
	public void setIdRuolo(Long idRuolo) {
		this.idRuolo = idRuolo;
	}
	public Long getIdProfilo() {
		return idProfilo;
	}
	public void setIdProfilo(Long idProfilo) {
		this.idProfilo = idProfilo;
	}
	public Long getIdProfiloOld() {
		return idProfiloOld;
	}
	public void setIdProfiloOld(Long idProfiloOld) {
		this.idProfiloOld = idProfiloOld;
	}
	public Timestamp getValidita() {
		return validita;
	}
	public void setValidita(Timestamp validita) {
		this.validita = validita;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idCollocazione == null) ? 0 : idCollocazione.hashCode());
		result = prime * result + ((idProfilo == null) ? 0 : idProfilo.hashCode());
		result = prime * result + ((idRuolo == null) ? 0 : idRuolo.hashCode());
		result = prime * result + ((idSol == null) ? 0 : idSol.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SolModel other = (SolModel) obj;
		if (idCollocazione == null) {
			if (other.idCollocazione != null)
				return false;
		} else if (!idCollocazione.equals(other.idCollocazione))
			return false;
		if (idProfilo == null) {
			if (other.idProfilo != null)
				return false;
		} else if (!idProfilo.equals(other.idProfilo))
			return false;
		if (idRuolo == null) {
			if (other.idRuolo != null)
				return false;
		} else if (!idRuolo.equals(other.idRuolo))
			return false;
		if (idSol == null) {
			if (other.idSol != null)
				return false;
		} else if (!idSol.equals(other.idSol))
			return false;
		return true;
	}
	
	
	
}
