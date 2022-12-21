/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao;

import it.csi.solconfig.configuratoreweb.business.dao.dto.AziendaDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.CollocazioneDto;
import it.csi.solconfig.configuratoreweb.presentation.model.CollocazioneDTO;
import it.csi.solconfig.configuratoreweb.presentation.model.Data;

import java.math.BigDecimal;
import java.util.List;

public interface CollocazioneLowDao extends EntityBaseLowDao<CollocazioneDto, Long> {

    List<CollocazioneDTO> findAllAziendeFromSuperUser();

    List<CollocazioneDTO> findByIdUtenteFromOperatore(Long idUtente, String codiceFiscale);

    List<CollocazioneDTO> findByIdUtenteFromSuperUser(Long idUtente);

    List<CollocazioneDTO> findAllAziendeFromOperatore(String codiceFiscale,String codiceFiscaleOperatore);

    List<CollocazioneDTO> findAziendaByCodiceOrDescrizioneFromSuperUser(String codiceAzienda, String descrizioneAzienda, String codice, String descrizione);

    List<CollocazioneDTO> findAziendaByCodiceOrDescrizioneFromOperatore(String codiceAzienda, String descrizioneAzienda, String codice, String descrizione, String codiceFiscale);

    List<CollocazioneDTO> findAziendaByIdFromSuperUser(List<Long> ids);

    List<CollocazioneDTO> findAziendaByIdFromOperatore(List<Long> ids, String codiceFiscale);

    List<Long> findAziendeSanitarie(List<Long> idCollocazioni);

    List<Long> findSediSanitarie(List<Long> idAziendeSanitarie);

    List<Long> getCollocazioniAbilitate(String codiceFiscale, String codiceApplicazione);

    List<CollocazioneDto> getCollocazioniAbilitatePerExport(String codiceFiscale, String codiceApplicazione);
    
    List<Long> findAziendeSanitarieConVisiblita(List<Long> idCollocazioni, String codiceFiscale);
    
    List<String> trovaVisibilita(String codFiscale);
    
    List<CollocazioneDTO> findCollocazioniFromOperatore(Long idUtente,String codiceFiscale);
    
    List<CollocazioneDTO> findCollocazioniFromID(List<Long> ids,String codiceFiscale);

	CollocazioneDto findByCodUoAndCodMultiSpec(String codAzienda, String codUoEsteso, String codMultiSpec);

	CollocazioneDto findByIdAmbulatorio(BigDecimal idAmbulatorio);

	CollocazioneDto findByCodStrutAndCodUoAndCodMultiSpecAndColElemOrg(String azienda, String codStruttura,
			String codUoEsteso, String codMultiSpec, String codElemOrganiz);
    
   
    
   
}
