/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao;

import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloSelezionabileDto;
import it.csi.solconfig.configuratoreweb.presentation.model.RuoloDTO;

import java.util.Collection;
import java.util.List;

public interface RuoloLowDao extends CatalogoBaseLowDao<RuoloDto, Long> {

    Collection<RuoloDto> findByUtenteCodiceFiscale(String codiceFiscale);

    List<RuoloDto> findRuoli(RuoloDto ruoloDto, String flagAttivo, String flagNonAttivo, Boolean flagConfiguratore);

    List<RuoloDTO> findAll(boolean superUser);

    List<RuoloDTO> findByIdUtente(Long idUtente);

    List<RuoloDTO> ricercaRuoliNonConfiguratore(boolean superUser);
    
    List<RuoloDTO> getRuoliSelezionabili(String cfOperatore,String codiceCollocazione, String codRuolo);

	List<String> getRuoliCompatibili(Long valueOf);

	List<RuoloDTO> findRuoliByIdProfilo(Long id);

	RuoloDto findByQualificaOpessan(String qualifica);

	RuoloDto findByPosizioneAndProfilo(String descrPosizioneFunzionale, String descrProfiloProfessionale);

	List<RuoloSelezionabileDto> getRuoliSelezionabiliByRuolo(Long idRuoloOperatore);

	List<RuoloDTO> findAllNoFilter();

	void updateRuoloSelezionabile(RuoloSelezionabileDto rsel);

	void insertRuoloCompatibile(long idruolo, String newRuoliComp);

	void updateDataFineRuoliCompatibili(long idruolo, List<String> delRuoliComp);

	void updateDataFineRuoliSelezionabile(long idRuoloOp, String idRuoloSel, int id);

	void updateRuoliSelezionabile(long idRuoloOp, String idRuoloSel, String idCollTipo, int id);

	Collection<RuoloDto> findByCodiceAndDataValidita(String codiceRuolo);
	
	public RuoloDto findByCodice(String codice);

	public  void cleanRuoli(long id);

	void insertRuoliSelezionabile(long idRuoloOp, String idRuoloSel, String idCollTipo, int id);
}
