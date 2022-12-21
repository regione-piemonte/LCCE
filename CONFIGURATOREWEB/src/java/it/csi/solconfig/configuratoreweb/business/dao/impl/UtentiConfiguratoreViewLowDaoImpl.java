/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Query;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

import it.csi.solconfig.configuratoreweb.business.dao.UtentiConfiguratoreViewLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.UtentiConfiguratoreViewDto;

@Component
public class UtentiConfiguratoreViewLowDaoImpl extends BaseLowDaoImpl<T> implements UtentiConfiguratoreViewLowDao {

	@Override
	public List<UtentiConfiguratoreViewDto> findForExcelExport(boolean isOperatore, List<String> collocazioneFilter,
			String codFiscOperatore) {

		String esporta = "select utv.* from v_utenti_configuratore_2 utv ";

		if (isOperatore) {

			esporta += (" where utv.codice_azienda_sanitaria in  (select az.cod_azienda  "
					+ "	from auth_r_utente_visibilita_azienda as aruva, " + "	auth_t_utente as ut, "
					+ "	auth_d_azienda as az " + "	where  " + "	aruva.id_azienda=az.id_azienda "
					+ "	and aruva.id_utente=ut.id " + "	and ut.codice_fiscale=:cod "
					+ " and CURRENT_TIMESTAMP between coalesce(aruva.data_inizio_val, to_timestamp('1970-01-01 12:00:00', 'YYYY-MM-DD HH:MI:SS')) and coalesce( aruva.data_fine_val, to_timestamp('2999-12-31 12:00:00', 'YYYY-MM-DD HH:MI:SS')))");


		}

		Query query = entityManager.createNativeQuery(esporta);

		if (isOperatore) {
			
			query.setParameter("cod", codFiscOperatore.toUpperCase());
		}


		ArrayList<Object> objectList = (ArrayList<Object>) query.getResultList();
		List<UtentiConfiguratoreViewDto> list = new ArrayList<UtentiConfiguratoreViewDto>();
		Iterator<Object> itr = objectList.iterator();
		while (itr.hasNext()) {
			Object[] obj = (Object[]) itr.next();
			UtentiConfiguratoreViewDto s = new UtentiConfiguratoreViewDto(obj);
			list.add(s);
		}

		return list;
	}
}
