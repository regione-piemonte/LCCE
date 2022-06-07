/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.business.dao.impl;

import it.csi.dma.dmaloginccebl.business.dao.AbilitazioneLowDao;
import it.csi.dma.dmaloginccebl.business.dao.dto.AbilitazioneDto;
import it.csi.dma.dmaloginccebl.interfacews.abilitazione.Abilitazione;
import it.csi.dma.dmaloginccebl.interfacews.msg.SiNo;
import it.csi.dma.dmaloginccebl.util.Utils;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.Collection;

@Component
public class AbilitazioneLowDaoImpl extends EntityBaseLowDaoImpl<AbilitazioneDto, Long> implements AbilitazioneLowDao {

    @Override
    public Collection<AbilitazioneDto> findByUtenteRuoloAppAndData(AbilitazioneDto abilitazioneDto) {
    	StringBuilder queryString= new StringBuilder("FROM " + abilitazioneDto.getClass().getName() + " t WHERE t.ruoloUtenteDto.id = :idRuoloUtente " +
                " AND ((:data > t.dataInizioValidita AND t.dataFineValidita is null) OR (:data BETWEEN t.dataInizioValidita AND t.dataFineValidita)) ");
        if(abilitazioneDto.getApplicazioneDto() != null) {
        	queryString.append("AND t.applicazioneDto.id = :idApplicazione");
        }
        Query query = entityManager.createQuery(queryString.toString());
        query.setParameter("idRuoloUtente", abilitazioneDto.getRuoloUtenteDto().getId());
        query.setParameter("data", Utils.sysdate());
        if(abilitazioneDto.getApplicazioneDto() != null) {
            query.setParameter("idApplicazione", abilitazioneDto.getApplicazioneDto().getId());
        }
        return query.getResultList();
    }
    
	@Override
    public Collection<AbilitazioneDto> findByUtenteRuolo(AbilitazioneDto abilitazioneDto) {
    	StringBuilder queryString= new StringBuilder("FROM " + abilitazioneDto.getClass().getName() + " t WHERE t.ruoloUtenteDto.id = :idRuoloUtente " +
                " AND ((:data > t.dataInizioValidita AND t.dataFineValidita is null) OR (:data BETWEEN t.dataInizioValidita AND t.dataFineValidita)) ");

        queryString.append("AND (t.treeFunzionalitaDto.funzionalitaDto.applicazioneDto.oscurato IS NULL OR t.treeFunzionalitaDto.funzionalitaDto.applicazioneDto.oscurato <> :attivo)");
        Query query = entityManager.createQuery(queryString.toString());
        query.setParameter("idRuoloUtente", abilitazioneDto.getRuoloUtenteDto().getId());
        query.setParameter("data", Utils.sysdate());
        query.setParameter("attivo", SiNo.SI.getValue());
        
        return query.getResultList();
    }

    @Override
    public Collection<AbilitazioneDto> findAbilitazioneGetAbilitazioni(AbilitazioneDto abilitazioneDto) {
        StringBuilder queryString= new StringBuilder("FROM AbilitazioneDto t  WHERE t.ruoloUtenteDto.id = :idRuoloUtente " +
                " AND t.utenteCollocazioneDto.id_utecol = :idUtenteCollocazione " +
                "AND ((:data > t.dataInizioValidita AND t.dataFineValidita is null) OR (:data BETWEEN t.dataInizioValidita AND t.dataFineValidita))");
        

        queryString.append("AND (t.treeFunzionalitaDto.funzionalitaDto.applicazioneDto.oscurato IS NULL OR t.treeFunzionalitaDto.funzionalitaDto.applicazioneDto.oscurato <> :attivo)");
        Query query = entityManager.createQuery(queryString.toString());
        query.setParameter("idRuoloUtente", abilitazioneDto.getRuoloUtenteDto().getId());
        query.setParameter("idUtenteCollocazione", abilitazioneDto.getUtenteCollocazioneDto().getId_utecol());
        query.setParameter("data", Utils.sysdate());
        query.setParameter("attivo", SiNo.SI.getValue());

        return query.getResultList();
    }

	@Override
	public Collection<AbilitazioneDto> findAbilitazioneFarmacista(String codiceCollocazione, String codiceFiscaleFarmacista, String codiceApplicazione) {
		StringBuilder queryString= new StringBuilder("FROM "+ AbilitazioneDto.class.getName() +" t  WHERE 1 = 1");
		
		queryString.append(" AND t.utenteCollocazioneDto.collocazioneDto.colCodice = :codiceCollocazione");

		queryString.append(" AND t.utenteCollocazioneDto.utenteDto.codiceFiscale = :codiceFiscaleFarmacista");

		queryString.append(" AND t.treeFunzionalitaDto.funzionalitaDto.applicazioneDto.codice = :codiceApplicazione");
		
		queryString.append(" AND (((t.utenteCollocazioneDto.dataInizioValidita is not null)"
				+ " AND (t.utenteCollocazioneDto.dataInizioValidita <= :data))");
		
		queryString.append(" AND ((t.utenteCollocazioneDto.dataFineValidita is null)"
				+ " OR (t.utenteCollocazioneDto.dataFineValidita > :data)))");
		
		queryString.append(" AND (((t.utenteCollocazioneDto.collocazioneDto.dataInizioValidita is not null)"
				+ " AND (t.utenteCollocazioneDto.collocazioneDto.dataInizioValidita <= :data))");
		
		queryString.append(" AND ((t.utenteCollocazioneDto.collocazioneDto.dataFineValidita is null)"
				+ " OR (t.utenteCollocazioneDto.collocazioneDto.dataFineValidita > :data)))");
		
		queryString.append(" AND (((t.treeFunzionalitaDto.funzionalitaDto.dataInizioValidita is not null)"
				+ " AND (t.treeFunzionalitaDto.funzionalitaDto.dataInizioValidita <= :data))");
		
		queryString.append(" AND ((t.treeFunzionalitaDto.funzionalitaDto.dataFineValidita is null)"
				+ " OR (t.treeFunzionalitaDto.funzionalitaDto.dataFineValidita > :data)))");
		
		queryString.append(" AND (((t.treeFunzionalitaDto.dataInizioValidita is not null)"
				+ " AND (t.treeFunzionalitaDto.dataInizioValidita <= :data))");
		
		queryString.append(" AND ((t.treeFunzionalitaDto.dataFineValidita is null)"
				+ " OR (t.treeFunzionalitaDto.dataFineValidita > :data)))");
		
		queryString.append("AND (((t.dataInizioValidita is not null) AND (t.dataInizioValidita <= :data))");
		
		queryString.append("AND ((t.dataFineValidita is null) OR (t.dataFineValidita > :data)))");
		
		queryString.append(" AND (((t.utenteCollocazioneDto.utenteDto.dataInizioValidita is not null)"
				+ " AND (t.utenteCollocazioneDto.utenteDto.dataInizioValidita <= :data))");
		
		queryString.append(" AND ((t.utenteCollocazioneDto.utenteDto.dataFineValidita is null)"
				+ " OR (t.utenteCollocazioneDto.utenteDto.dataFineValidita > :data)))");
		
		Query query = entityManager.createQuery(queryString.toString());
		query.setParameter("codiceCollocazione", codiceCollocazione);
		query.setParameter("codiceFiscaleFarmacista", codiceFiscaleFarmacista);
		query.setParameter("codiceApplicazione", codiceApplicazione);
		query.setParameter("data", Utils.sysdate());
		
		return query.getResultList();
	}
}
