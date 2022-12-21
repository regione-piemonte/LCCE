/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.impl;

import java.util.List;

import javax.persistence.NoResultException;
import javax.persistence.Query;

import org.springframework.stereotype.Component;

import it.csi.solconfig.configuratoreweb.business.dao.ApplicazioneLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.ApplicazioneDto;
import it.csi.solconfig.configuratoreweb.presentation.constants.ConstantsWebApp;
import it.csi.solconfig.configuratoreweb.presentation.model.ServizioOnLineDTO;

@Component
public class ApplicazioneLowDaoImpl extends CatalogoBaseLowDaoImpl<ApplicazioneDto, Long>
		implements ApplicazioneLowDao {
	private static final String FLAG_CONFIGURATORE_S = "S";

	@Override
	public List<ApplicazioneDto> findApplicazione(String codice, String descrizione) {
		StringBuilder queryBuilder = new StringBuilder("from " + ApplicazioneDto.class.getName() + " t WHERE 1=1 ");

		if (codice != null) {
			queryBuilder.append(" and lower(t.codice) LIKE CONCAT('%', :codice, '%') ");
		}
		if (descrizione != null) {
			queryBuilder.append(" and lower(t.descrizioneWebapp) LIKE CONCAT('%', :descrizione, '%') ");
		}

		Query query = getEntityManager().createQuery(queryBuilder.toString());
		if (codice != null) {
			query.setParameter("codice", codice.toLowerCase());
		}

		if (descrizione != null) {
			query.setParameter("descrizione", descrizione.toLowerCase());
		}

		return query.getResultList();
	}

	@Override
	public List<ServizioOnLineDTO> findServiziByIdCollocazioni(List<Long> idCollocazioni,
			boolean isOPListaSOLconConfiguratore) {
		return entityManager.createQuery(
				"SELECT DISTINCT new it.csi.solconfig.configuratoreweb.presentation.model.ServizioOnLineDTO("
						+ "a.id, a.descrizione, a.codice ) "
						+ "FROM ApplicazioneCollocazioneDto ac JOIN ac.applicazioneDto a JOIN ac.collocazioneDto c "
						+ "WHERE c.colId IN :idCollocazioni AND a.flagConfiguratore = :flagConfiguratore "
						+ "AND (:isOPListaSOLconConfiguratore = TRUE OR a.codice <> :codiceConfiguratore) "
						+ "AND ((CURRENT_TIMESTAMP BETWEEN c.dataInizioValidita AND c.dataFineValidita) "
						+ "OR (CURRENT_TIMESTAMP > c.dataInizioValidita AND c.dataFineValidita IS NULL) "
						+ "OR (CURRENT_TIMESTAMP < c.dataFineValidita AND c.dataInizioValidita IS NULL) "
						+ "OR (c.dataInizioValidita IS NULL AND c.dataFineValidita IS NULL)) ",
				ServizioOnLineDTO.class).setParameter("idCollocazioni", idCollocazioni)
				.setParameter("flagConfiguratore", FLAG_CONFIGURATORE_S)
				.setParameter("codiceConfiguratore", ConstantsWebApp.APPL_CONF)
				.setParameter("isOPListaSOLconConfiguratore", isOPListaSOLconConfiguratore).getResultList();
	}

	@Override
	public List<String> findServiziByIdUtente(Long idUtente, boolean isOPListaSOLconConfiguratore) {
		return entityManager.createQuery("SELECT DISTINCT " + "CASE WHEN (a.dataFineValidita IS NOT NULL) "
				+ "THEN CONCAT(ap.id, '|', uc.collocazioneDto.colId, '|', f.idFunzione, '|', r.id, '|', a.dataFineValidita) "
				+ "ELSE CONCAT(ap.id, '|', uc.collocazioneDto.colId, '|', f.idFunzione, '|', r.id) " + "END "
				+ "FROM UtenteDto u JOIN u.utenteCollocazioneList uc "
				+ "LEFT JOIN uc.abilitazioneList a LEFT JOIN a.applicazioneDto ap "
				+ "LEFT JOIN a.treeFunzionalitaDto tf LEFT JOIN tf.funzionalitaDto f "
				+ "LEFT JOIN a.ruoloUtenteDto rp " +
//                        "LEFT JOIN f.ruoloProfiloList rp " +
				"LEFT JOIN uc.collocazioneDto JOIN rp.ruoloDto r "
				+ "WHERE u.id=:idUtente AND ap.flagConfiguratore = :flagConfiguratore "
				+ "AND (:isOPListaSOLconConfiguratore = TRUE OR ap.codice <> :codiceConfiguratore) "
				+ "AND ((CURRENT_TIMESTAMP BETWEEN rp.dataInizioValidita AND rp.dataFineValidita) "
				+ "OR (CURRENT_TIMESTAMP > rp.dataInizioValidita AND rp.dataFineValidita IS NULL) "
				+ "OR (CURRENT_TIMESTAMP < rp.dataFineValidita AND rp.dataInizioValidita IS NULL) "
				+ "OR (rp.dataInizioValidita IS NULL AND rp.dataFineValidita IS NULL)) "
				+ "AND ((CURRENT_TIMESTAMP BETWEEN r.dataInizioValidita AND r.dataFineValidita) "
				+ "OR (CURRENT_TIMESTAMP > r.dataInizioValidita AND r.dataFineValidita IS NULL) "
				+ "OR (CURRENT_TIMESTAMP < r.dataFineValidita AND r.dataInizioValidita IS NULL) "
				+ "OR (r.dataInizioValidita IS NULL AND r.dataFineValidita IS NULL)) "
				+ "AND ((CURRENT_TIMESTAMP BETWEEN uc.dataInizioValidita AND uc.dataFineValidita) "
				+ "OR (CURRENT_TIMESTAMP > uc.dataInizioValidita AND uc.dataFineValidita IS NULL) "
				+ "OR (CURRENT_TIMESTAMP < uc.dataFineValidita AND uc.dataInizioValidita IS NULL) "
				+ "OR (uc.dataInizioValidita IS NULL AND uc.dataFineValidita IS NULL)) "
				+ "AND ((CURRENT_TIMESTAMP BETWEEN uc.collocazioneDto.dataInizioValidita AND uc.collocazioneDto.dataFineValidita) "
				+ "OR (CURRENT_TIMESTAMP > uc.collocazioneDto.dataInizioValidita AND uc.collocazioneDto.dataFineValidita IS NULL) "
				+ "OR (CURRENT_TIMESTAMP < uc.collocazioneDto.dataFineValidita AND uc.collocazioneDto.dataInizioValidita IS NULL) "
				+ "OR (uc.collocazioneDto.dataInizioValidita IS NULL AND uc.collocazioneDto.dataFineValidita IS NULL)) "
				+ "AND ((CURRENT_TIMESTAMP BETWEEN a.dataInizioValidita AND a.dataFineValidita) "
				+ "OR (CURRENT_TIMESTAMP > a.dataInizioValidita AND a.dataFineValidita IS NULL) "
				+ "OR (CURRENT_TIMESTAMP < a.dataFineValidita AND a.dataInizioValidita IS NULL) "
				+ "OR (a.dataInizioValidita IS NULL AND a.dataFineValidita IS NULL)) "
				+ "AND ((CURRENT_TIMESTAMP BETWEEN tf.dataInizioValidita AND tf.dataFineValidita) "
				+ "OR (CURRENT_TIMESTAMP > tf.dataInizioValidita AND tf.dataFineValidita IS NULL) "
				+ "OR (CURRENT_TIMESTAMP < tf.dataFineValidita AND tf.dataInizioValidita IS NULL) "
				+ "OR (tf.dataInizioValidita IS NULL AND tf.dataFineValidita IS NULL)) "
				+ "AND ((CURRENT_TIMESTAMP BETWEEN f.dataInizioValidita AND f.dataFineValidita) "
				+ "OR (CURRENT_TIMESTAMP > f.dataInizioValidita AND f.dataFineValidita IS NULL) "
				+ "OR (CURRENT_TIMESTAMP < f.dataFineValidita AND f.dataInizioValidita IS NULL) "
				+ "OR (f.dataInizioValidita IS NULL AND f.dataFineValidita IS NULL)) ", String.class)
				.setParameter("idUtente", idUtente).setParameter("flagConfiguratore", FLAG_CONFIGURATORE_S)
				.setParameter("codiceConfiguratore", ConstantsWebApp.APPL_CONF)
				.setParameter("isOPListaSOLconConfiguratore", isOPListaSOLconConfiguratore).getResultList();
	}

	@Override
	public List<ServizioOnLineDTO> findSolSelezionabili(Long idCollocazione) {

		return entityManager.createQuery(
				"SELECT DISTINCT new it.csi.solconfig.configuratoreweb.presentation.model.ServizioOnLineDTO("
						+ "a.id, a.descrizione, a.codice ) "
						+ "FROM ApplicazioneCollocazioneDto ac JOIN ac.applicazioneDto a JOIN ac.collocazioneDto c "
						+ "WHERE c.colCodAzienda IN (" + "SELECT colCodAzienda " + " From CollocazioneDto "
						+ "Where colId=:colId " + ") " + "AND c.flagAzienda='S' "
						+ "AND ((CURRENT_TIMESTAMP BETWEEN c.dataInizioValidita AND c.dataFineValidita) "
						+ "OR (CURRENT_TIMESTAMP > c.dataInizioValidita AND c.dataFineValidita IS NULL) "
						+ "OR (CURRENT_TIMESTAMP < c.dataFineValidita AND c.dataInizioValidita IS NULL) "
						+ "OR (c.dataInizioValidita IS NULL AND c.dataFineValidita IS NULL)) ",
				ServizioOnLineDTO.class).setParameter("colId", idCollocazione).getResultList();
	}

	@Override
	public List<ServizioOnLineDTO> findSolFiltrati(List<Long> idSol, Long ruolo) {

		return entityManager
				.createQuery(
						"SELECT DISTINCT new it.csi.solconfig.configuratoreweb.presentation.model.ServizioOnLineDTO("
								+ "a.id, a.descrizione, a.codice ) "
								+ " From RuoloProfilo r JOIN r.funzionalita f JOIN f.applicazioneDto a JOIN r.ruolo ru"
								+ " WHERE ru.id=:idRuolo " + " AND a.id IN (:idSol) "
								+ "AND ((CURRENT_TIMESTAMP BETWEEN r.dataInizioValidita AND r.dataFineValidita) "
								+ "OR (CURRENT_TIMESTAMP > r.dataInizioValidita AND r.dataFineValidita IS NULL) "
								+ "OR (CURRENT_TIMESTAMP < r.dataFineValidita AND r.dataInizioValidita IS NULL) "
								+ "OR (r.dataInizioValidita IS NULL AND r.dataFineValidita IS NULL)) "
								+ "AND ((CURRENT_TIMESTAMP BETWEEN ru.dataInizioValidita AND ru.dataFineValidita) "
								+ "OR (CURRENT_TIMESTAMP > ru.dataInizioValidita AND ru.dataFineValidita IS NULL) "
								+ "OR (CURRENT_TIMESTAMP < ru.dataFineValidita AND ru.dataInizioValidita IS NULL) "
								+ "OR (ru.dataInizioValidita IS NULL AND ru.dataFineValidita IS NULL)) "
								+ "AND ((CURRENT_TIMESTAMP BETWEEN f.dataInizioValidita AND f.dataFineValidita) "
								+ "OR (CURRENT_TIMESTAMP > f.dataInizioValidita AND f.dataFineValidita IS NULL) "
								+ "OR (CURRENT_TIMESTAMP < f.dataFineValidita AND f.dataInizioValidita IS NULL) "
								+ "OR (f.dataInizioValidita IS NULL AND f.dataFineValidita IS NULL)) ",
						ServizioOnLineDTO.class)
				.setParameter("idRuolo", ruolo).setParameter("idSol", idSol).getResultList();

	}

	@Override
	public String getSolId(Long id) {

		try {
			return entityManager
					.createQuery("SELECT a.codice " + "FROM " + "ApplicazioneDto a " + "WHERE a.id=:id  ", String.class)
					.setParameter("id", id).getSingleResult();

		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public List<Long> getProfileTitolareoDelegatoSolConfig() {

		return entityManager.createQuery(
				"SELECT DISTINCT f.idFunzione " + "FROM FunzionalitaDto f " + "JOIN f.applicazioneDto a "
						+ "WHERE a.codice='SOLCONFIG' " + "AND  f.codiceFunzione IN ('CONF_DELEGATO','CONF_TITOLARE') ",
				Long.class).getResultList();

	}

	@Override
	public Long getProfiloTitolareSolConfig() {

		try {
			return entityManager.createQuery("SELECT DISTINCT f.idFunzione " + "FROM FunzionalitaDto f "
					+ "JOIN f.applicazioneDto a " + "WHERE a.codice='SOLCONFIG' "
					+ "AND  f.codiceFunzione = 'CONF_TITOLARE'" + " AND f.tipoFunzionalitaDto.idTipoFunzione = 1 ",
					Long.class).getSingleResult();

		} catch (NoResultException e) {
			return null;
		}

	}

}
