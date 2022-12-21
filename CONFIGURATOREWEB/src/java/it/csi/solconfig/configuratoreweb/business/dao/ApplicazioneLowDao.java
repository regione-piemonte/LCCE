/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao;

import it.csi.solconfig.configuratoreweb.business.dao.dto.ApplicazioneDto;
import it.csi.solconfig.configuratoreweb.presentation.model.ServizioOnLineDTO;

import java.util.List;

public interface ApplicazioneLowDao extends CatalogoBaseLowDao<ApplicazioneDto, Long> {

	List<ServizioOnLineDTO> findServiziByIdCollocazioni(List<Long> idCollocazioni,
			boolean isOPListaSOLconConfiguratore);

	List<String> findServiziByIdUtente(Long idUtente, boolean isOPListaSOLconConfiguratore);

	List<ServizioOnLineDTO> findSolSelezionabili(Long idCollocazione);

	List<ServizioOnLineDTO> findSolFiltrati(List<Long> idSol, Long ruolo);

	String getSolId(Long id);

	List<Long> getProfileTitolareoDelegatoSolConfig();

	Long getProfiloTitolareSolConfig();

	List<ApplicazioneDto> findApplicazione(String codice, String descrizione);
}
