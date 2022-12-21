/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao;

import it.csi.solconfig.configuratoreweb.business.dao.dto.UtenteDto;
import it.csi.solconfig.configuratoreweb.presentation.model.Data;
import it.csi.solconfig.configuratoreweb.presentation.model.PaginaDTO;
import it.csi.solconfig.configuratoreweb.presentation.model.RisultatiRicercaUtenteDTO;

import java.util.Collection;
import java.util.List;

public interface UtenteLowDao extends EntityBaseLowDao<UtenteDto, Long> {

    UtenteDto salva(UtenteDto utenteDto);

    void modifica(UtenteDto utenteDto);

    Collection<UtenteDto> findByCodiceFiscale(String codiceFiscale);

    PaginaDTO<RisultatiRicercaUtenteDTO> ricercaUtenteDaSuperUserByCodiceFiscale(String codiceFiscale, Integer numeroPagina, Integer numeroElementi,String codiceFiscaleOperatore);

    PaginaDTO<RisultatiRicercaUtenteDTO> ricercaUtenteDaOperatoreByCodiceFiscale(String codiceFiscale, List<Long> listCollocazione, Integer numeroPagina, Integer numeroElementi,String codiceFiscaleOperatore,boolean isDelegato,boolean isTitolare);

    List<RisultatiRicercaUtenteDTO> esportaUtentiSuperUser(String codiceFiscale,String codiceFiscaleOperatore);

    List<RisultatiRicercaUtenteDTO> esportaUtentiOperatore(String codiceFiscale, List<Long> listCollocazione,String codiceFiscaleOperatore);
    
    boolean checkUtente(String codiceFiscale);
    
    PaginaDTO<RisultatiRicercaUtenteDTO> ricercaUtenteDaSuperUserByCodiceFiscaleFiltrato(String codiceFiscale, Integer numeroPagina, Integer numeroElementi,String codiceFiscaleOperatore);

    List<UtenteDto> findByIdRuoloAndIdCollocazione(Long idRuolo, Long idCollocazione);
    
    Long countByIdRuoloAndIdCollocazione(long idRuolo, long idCollocazione);
    
    List<UtenteDto> findByIds(List<Long> ids);
	
    List<UtenteDto> findByIdRuoloAndIdAzienda(Long idRuolo, Long idAzienda);
}
