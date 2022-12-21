/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Query;

import org.springframework.stereotype.Component;

import it.csi.configuratorews.business.dao.PreferenzaLowDao;
import it.csi.configuratorews.business.dto.PreferenzaDto;
import it.csi.configuratorews.dto.configuratorews.FilterPreferences;
import it.csi.configuratorews.dto.configuratorews.RuoloCollocazione;

@Component
public class PreferenzaLowDaoImpl extends EntityBaseLowDaoImpl<PreferenzaDto, Long> implements PreferenzaLowDao {

	public List<PreferenzaDto> findByFilter(FilterPreferences filter) {
		StringBuffer hql = new StringBuffer("select preferenza from PreferenzaDto as preferenza inner join preferenza.utente as utente");
		hql.append(" inner join fetch preferenza.preferenzaSalvata preferenzaSalvata");
		hql.append(" inner join fetch preferenzaSalvata.preferenzaFruitoreDto preferenzaFruitore");
		hql.append(" where 1=1");
		hql.append(" and preferenza.dataInizioValidita < now() ");
		hql.append(" and (preferenza.dataFineValidita > now() or preferenza.dataFineValidita is null) ");
		
		hql.append(" and preferenza.utente.dataInizioValidita < now() ");
		hql.append(" and preferenzaSalvata.dataCancellazione is  null");
		hql.append(" and (preferenza.utente.dataFineValidita > now() or preferenza.utente.dataFineValidita is null) ");
		if (filter.getCodiceApplicazione() != null) {
			hql.append(" and preferenza.applicazioneDto.codice='");
			hql.append(filter.getCodiceApplicazione());
			hql.append("'");
		}
		if (filter.getCodiceCollocazione() != null) {
			hql.append(" and preferenza.collocazioneDto.colCodice ='");
			hql.append(filter.getCodiceCollocazione());
			hql.append("'");
			hql.append(" and preferenza.collocazioneDto.dataCancellazione is null");
		}else {
			hql.append(" and preferenza.collocazioneDto is null");
		}
		if (filter.getCodiceRuolo() != null) {
			hql.append(" and preferenza.ruoloDto.codice ='");
			hql.append(filter.getCodiceRuolo());
			hql.append("'");
			hql.append(" and preferenza.ruoloDto.dataInizioValidita < now() ");
			hql.append(" and (preferenza.ruoloDto.dataFineValidita > now() or preferenza.ruoloDto.dataFineValidita is null) ");
		}
		if (filter.getCodiceAzienda() != null) {
			hql.append(" and preferenza.collocazioneDto.colCodAzienda ='");
			hql.append(filter.getCodiceAzienda());
			hql.append("'");
			hql.append(" and preferenza.collocazioneDto.dataInizioValidita < now() ");
			hql.append(" and (preferenza.collocazioneDto.dataFineValidita > now() or preferenza.collocazioneDto.dataFineValidita is null) ");
		}
		if (filter.getCodiciFiscale() != null) {
			hql.append(" and utente.codiceFiscale in (:cf)");
		}
		hql.append(" and preferenzaFruitore.nomeFruitore = '"+filter.getNomeFruitore()+"'");
		hql.append(" and preferenzaFruitore.dataCancellazione is null");
		Query q = entityManager.createQuery(hql.toString());
		if (filter.getCodiciFiscale() != null) {
			q.setParameter("cf", filter.getCodiciFiscale());
		}
		return q.getResultList();
	}
	
	
	
	public List<PreferenzaDto> findLeftByFilter(FilterPreferences filter) {
		StringBuffer hql = new StringBuffer("select distinct preferenza from PreferenzaDto as preferenza inner join preferenza.utente as utente");
		hql.append(" left join fetch preferenza.preferenzaSalvata preferenzaSalvata");
		hql.append(" left join fetch preferenzaSalvata.preferenzaFruitoreDto preferenzaFruitore");
		hql.append(" where 1=1");
		hql.append(" and preferenza.dataInizioValidita < now() ");
		hql.append(" and (preferenza.dataFineValidita > now() or preferenza.dataFineValidita is null) ");
		
//		hql.append(" and preferenzaSalvata.dataCancellazione is  null");
		hql.append(" and preferenza.utente.dataInizioValidita < now() ");
		hql.append(" and (preferenza.utente.dataFineValidita > now() or preferenza.utente.dataFineValidita is null) ");
		if (filter.getCodiceApplicazione() != null) {
			hql.append(" and preferenza.applicazioneDto.codice='");
			hql.append(filter.getCodiceApplicazione());
			hql.append("'");
		}
		if (filter.getCodiceCollocazione() != null) {
			hql.append(" and preferenza.collocazioneDto.colCodice ='");
			hql.append(filter.getCodiceCollocazione());
			hql.append("'");
			hql.append(" and preferenza.collocazioneDto.dataCancellazione is null");
		}
		if (filter.getCodiceRuolo() != null) {
			hql.append(" and preferenza.ruoloDto.codice ='");
			hql.append(filter.getCodiceRuolo());
			hql.append("'");
			hql.append(" and preferenza.ruoloDto.dataInizioValidita < now() ");
			hql.append(" and (preferenza.ruoloDto.dataFineValidita > now() or preferenza.ruoloDto.dataFineValidita is null) ");
		}
		if (filter.getCodiceAzienda() != null) {
			hql.append(" and preferenza.collocazioneDto.colCodAzienda ='");
			hql.append(filter.getCodiceAzienda());
			hql.append("'");
			hql.append(" and preferenza.collocazioneDto.dataInizioValidita < now() ");
			hql.append(" and (preferenza.collocazioneDto.dataFineValidita > now() or preferenza.collocazioneDto.dataFineValidita is null) ");
		}
		if (filter.getCodiciFiscale() != null) {
			hql.append(" and utente.codiceFiscale in (:cf)");
		}
		Query q = entityManager.createQuery(hql.toString());
		if (filter.getCodiciFiscale() != null) {
			q.setParameter("cf", filter.getCodiciFiscale());
		}
		return q.getResultList();
	}

	@Override
	public List<PreferenzaDto> findApplicazionePreferenzeByCFAndApplicazioni(String cf, Set<String> applicazioni,boolean validRecord) {
		StringBuffer hql = new StringBuffer("select distinct preferenza from PreferenzaDto as preferenza inner join preferenza.utente as utente");
		hql.append(" inner join preferenza.applicazioneDto applicazione");
		hql.append(" left join fetch preferenza.preferenzaSalvata as prefSalvata");
		hql.append(" left join fetch prefSalvata.preferenzaFruitoreDto preferenzaFruitore");
		hql.append(" where 1=1");
		hql.append(" and preferenza.dataInizioValidita < now() ");
		hql.append(" and (preferenza.dataFineValidita > now() or preferenza.dataFineValidita is null) ");
		
		hql.append(" and preferenza.utente.dataInizioValidita < now() ");
		hql.append(" and (preferenza.utente.dataFineValidita > now() or preferenza.utente.dataFineValidita is null) ");
		hql.append(" and utente.codiceFiscale = '" + cf + "'");
		if(validRecord) {
			hql.append(" and prefSalvata.dataCancellazione is  null");
		}
		if(applicazioni!=null) {
			hql.append(" and applicazione.codice in (:applicazioni)");
		}
		hql.append(" and preferenzaFruitore.dataCancellazione is null");
		Query q = entityManager.createQuery(hql.toString());
		if(applicazioni!=null) {
			q.setParameter("applicazioni", applicazioni);
		}
		return q.getResultList();
	}

	@Override
	public List<PreferenzaDto> findRuoloPreferenzeByCFAndRuolo(String cf, Set<String> ruoli,boolean validRecord) {
		StringBuffer hql = new StringBuffer("select distinct preferenza from PreferenzaDto as preferenza inner join preferenza.utente as utente");
		hql.append(" inner join preferenza.ruoloDto ruolo");
		hql.append(" left join fetch preferenza.collocazioneDto collocazione");
		hql.append(" left join fetch preferenza.preferenzaSalvata as prefSalvata");
		hql.append(" left join fetch prefSalvata.preferenzaFruitoreDto preferenzaFruitore");
		hql.append(" where 1=1");
		hql.append(" and preferenza.dataInizioValidita < now() ");
		hql.append(" and (preferenza.dataFineValidita > now() or preferenza.dataFineValidita is null) ");
		
		hql.append(" and collocazione is null");
		hql.append(" and preferenza.utente.dataInizioValidita < now() ");
		hql.append(" and (preferenza.utente.dataFineValidita > now() or preferenza.utente.dataFineValidita is null) ");
		hql.append(" and utente.codiceFiscale = '" + cf + "'");

		hql.append(" and ruolo.dataInizioValidita < now() ");
		hql.append(" and (ruolo.dataFineValidita > now() or ruolo.dataFineValidita is null) ");
		hql.append(" and preferenzaFruitore.dataCancellazione is null");
		if(validRecord) {
			hql.append(" and prefSalvata.dataCancellazione is  null");
		}
		if(ruoli!=null) {
			hql.append(" and ruolo.codice in (:ruoli)");
		}
		Query q = entityManager.createQuery(hql.toString());
		if(ruoli!=null) {
			q.setParameter("ruoli", ruoli);
		}
		return q.getResultList();
	}

	@Override
	public List<PreferenzaDto> findRuoloCollocazioniPreferenzeByCFAndRuoloCollocazioni(String cf, Set<RuoloCollocazione> ruoloCollocazioni,boolean validRecord) {
		StringBuffer hql = new StringBuffer("select distinct preferenza from PreferenzaDto as preferenza inner join preferenza.utente as utente");
		hql.append(" inner join preferenza.collocazioneDto");
		hql.append(" inner join preferenza.ruoloDto");
		hql.append(" left join fetch preferenza.preferenzaSalvata as prefSalvata");
		hql.append(" left join prefSalvata.preferenzaFruitoreDto preferenzaFruitore");
		
		hql.append(" where 1=1");
		hql.append(" and preferenza.dataInizioValidita < now() ");
		hql.append(" and (preferenza.dataFineValidita > now() or preferenza.dataFineValidita is null) ");
		
		hql.append(" and preferenza.utente.dataInizioValidita < now() ");
		hql.append(" and (preferenza.utente.dataFineValidita > now() or preferenza.utente.dataFineValidita is null) ");
		hql.append(" and utente.codiceFiscale = '" + cf + "'");
		hql.append(" and preferenza.collocazioneDto.dataCancellazione is null");
		hql.append(" and preferenza.ruoloDto.dataInizioValidita < now() ");
		hql.append(" and (preferenza.ruoloDto.dataFineValidita > now() or preferenza.ruoloDto.dataFineValidita is null) ");
		hql.append(" and preferenzaFruitore.dataCancellazione is null");
		if(validRecord) {
			hql.append(" and prefSalvata.dataCancellazione is null");
		}
		if (ruoloCollocazioni != null && !ruoloCollocazioni.isEmpty()) {
			hql.append(" and (");
			int i = 0;
			for (RuoloCollocazione currCondition : ruoloCollocazioni) {
				
				hql.append(" (preferenza.ruoloDto.codice = '" + currCondition.getRuolo() + "' and preferenza.collocazioneDto.colCodice ='"
						+ currCondition.getCollocazione() + "') ");
				i++;
				if(i!=ruoloCollocazioni.size()) {
					hql.append(" or ");
				}
			}
			hql.append(" )");
		}
		Query q = entityManager.createQuery(hql.toString());
		return q.getResultList();
	}

	
	@Override
	public List<PreferenzaDto> findApplicazionePreferenzeByCFAndApplicazioni(String cf, Set<String> applicazioni) {
		StringBuffer hql = new StringBuffer("select distinct preferenza from PreferenzaDto as preferenza inner join preferenza.utente as utente");
		hql.append(" inner join preferenza.applicazioneDto applicazione");
		hql.append(" left join fetch preferenza.preferenzaSalvata as prefSalvata");
		hql.append(" left join fetch prefSalvata.preferenzaFruitoreDto preferenzaFruitore");
		hql.append(" where 1=1");
		hql.append(" and preferenza.dataInizioValidita < now() ");
		hql.append(" and (preferenza.dataFineValidita > now() or preferenza.dataFineValidita is null) ");
		
		hql.append(" and preferenza.utente.dataInizioValidita < now() ");
		hql.append(" and (preferenza.utente.dataFineValidita > now() or preferenza.utente.dataFineValidita is null) ");
		hql.append(" and utente.codiceFiscale = '" + cf + "'");
		hql.append("and (prefSalvata.id is null or prefSalvata.dataCancellazione is not null)");

		if(applicazioni!=null) {
			hql.append(" and applicazione.codice in (:applicazioni)");
		}
		hql.append(" and preferenzaFruitore.dataCancellazione is null");
		Query q = entityManager.createQuery(hql.toString());
		if(applicazioni!=null) {
			q.setParameter("applicazioni", applicazioni);
		}
		return q.getResultList();
	}

	@Override
	public List<PreferenzaDto> findRuoloPreferenzeByCFAndRuolo(String cf, Set<String> ruoli) {
		StringBuffer hql = new StringBuffer("select distinct preferenza from PreferenzaDto as preferenza inner join preferenza.utente as utente");
		hql.append(" inner join preferenza.ruoloDto ruolo");
		hql.append(" left join fetch preferenza.collocazioneDto collocazione");
		hql.append(" left join fetch preferenza.preferenzaSalvata as prefSalvata");
		hql.append(" left join fetch prefSalvata.preferenzaFruitoreDto preferenzaFruitore");
		hql.append(" where 1=1");
		hql.append(" and preferenza.dataInizioValidita < now() ");
		hql.append(" and (preferenza.dataFineValidita > now() or preferenza.dataFineValidita is null) ");
		
		hql.append(" and collocazione is null");
		hql.append(" and preferenza.utente.dataInizioValidita < now() ");
		hql.append(" and (preferenza.utente.dataFineValidita > now() or preferenza.utente.dataFineValidita is null) ");
		hql.append(" and utente.codiceFiscale = '" + cf + "'");
		hql.append("and (prefSalvata.id is null or prefSalvata.dataCancellazione is not null)");
		
		hql.append(" and ruolo.dataInizioValidita < now() ");
		hql.append(" and (ruolo.dataFineValidita > now() or ruolo.dataFineValidita is null) ");
		hql.append(" and preferenzaFruitore.dataCancellazione is null");
		if(ruoli!=null) {
			hql.append(" and ruolo.codice in (:ruoli)");
		}
		Query q = entityManager.createQuery(hql.toString());
		if(ruoli!=null) {
			q.setParameter("ruoli", ruoli);
		}
		return q.getResultList();
	}

	@Override
	public List<PreferenzaDto> findRuoloCollocazioniPreferenzeByCFAndRuoloCollocazioni(String cf, Set<RuoloCollocazione> ruoloCollocazioni) {
		StringBuffer hql = new StringBuffer("select distinct preferenza from PreferenzaDto as preferenza inner join preferenza.utente as utente");
		hql.append(" inner join preferenza.collocazioneDto");
		hql.append(" inner join preferenza.ruoloDto");
		hql.append(" left join fetch preferenza.preferenzaSalvata as prefSalvata");
		hql.append(" left join prefSalvata.preferenzaFruitoreDto preferenzaFruitore");
		hql.append(" where 1=1");
		hql.append(" and preferenza.dataInizioValidita < now() ");
		hql.append(" and (preferenza.dataFineValidita > now() or preferenza.dataFineValidita is null) ");
		
		hql.append(" and preferenza.utente.dataInizioValidita < now() ");
		hql.append(" and (preferenza.utente.dataFineValidita > now() or preferenza.utente.dataFineValidita is null) ");
		hql.append(" and utente.codiceFiscale = '" + cf + "'");
		hql.append(" and preferenza.collocazioneDto.dataCancellazione is null");
		hql.append(" and preferenza.ruoloDto.dataInizioValidita < now() ");
		hql.append(" and (preferenza.ruoloDto.dataFineValidita > now() or preferenza.ruoloDto.dataFineValidita is null) ");
		hql.append(" and preferenzaFruitore.dataCancellazione is null");
		hql.append(" and (prefSalvata.id is null or prefSalvata.dataCancellazione is not null)");
		
		if (ruoloCollocazioni != null && !ruoloCollocazioni.isEmpty()) {
			hql.append(" and (");
			int i = 0;
			for (RuoloCollocazione currCondition : ruoloCollocazioni) {
				
				hql.append(" (preferenza.ruoloDto.codice = '" + currCondition.getRuolo() + "' and preferenza.collocazioneDto.colCodice ='"
						+ currCondition.getCollocazione() + "') ");
				i++;
				if(i!=ruoloCollocazioni.size()) {
					hql.append(" or ");
				}
			}
			hql.append(" )");
		}
		Query q = entityManager.createQuery(hql.toString());
		return q.getResultList();
	}
}
