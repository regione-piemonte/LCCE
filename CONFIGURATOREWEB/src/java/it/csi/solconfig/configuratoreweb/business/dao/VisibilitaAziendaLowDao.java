/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao;

import it.csi.solconfig.configuratoreweb.business.dao.dto.AziendaDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.TipoFunzionalitaDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.UtenteDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.VisibilitaAziendaDto;

public interface VisibilitaAziendaLowDao extends EntityBaseLowDao<VisibilitaAziendaDto, Long> {
	
	VisibilitaAziendaDto findVisibilitaByIdUtenteAndIdAzienda(Integer idAzienda,Long idUtente);
	
	VisibilitaAziendaDto salva(VisibilitaAziendaDto visAz);

    void modifica(VisibilitaAziendaDto visAz);
}
