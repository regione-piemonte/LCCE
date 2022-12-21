/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.impl;

import java.util.Collection;

import javax.persistence.Query;

import it.csi.solconfig.configuratoreweb.business.dao.dto.UtenteDto;
import it.csi.solconfig.configuratoreweb.util.Utils;
import org.springframework.stereotype.Component;

import it.csi.solconfig.configuratoreweb.business.dao.RichiestaCredenzialiLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.FaqRuparDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RichiestaCredenzialiDto;
import it.csi.solconfig.configuratoreweb.presentation.model.CredenzialiRuparModel;

@Component
public class RichiestaCredenzialiLowDaoImpl extends EntityBaseLowDaoImpl<RichiestaCredenzialiDto, Long> implements RichiestaCredenzialiLowDao{
	
	@Override
	public Collection<RichiestaCredenzialiDto> findRichiesteRupar(CredenzialiRuparModel ruparModel){

		String tabella= getTabName(new RichiestaCredenzialiDto());
		
		StringBuilder queryBuilder = new StringBuilder("from " + tabella + " t " + " WHERE 1=1");
		
		if(ruparModel.getCfUtente()!=null && !ruparModel.getCfUtente().isEmpty()) {
			queryBuilder.append("AND t.utenteDto.codiceFiscale = :codiceFiscaleUtente ");
		}
		 
		 if(ruparModel.getCfOperatore()!=null && !ruparModel.getCfOperatore().isEmpty()) {
		 	queryBuilder.append("AND t.operatoreDto.codiceFiscale = :codiceFiscaleOperatore ");
		} 
		 
		 if(ruparModel.getTicketRemedy()!=null && !ruparModel.getTicketRemedy().isEmpty()) {
			 	queryBuilder.append("AND (t.ticketRemedy = :ticketRemedy OR t.ticketRemedy = 'INC' || :ticketRemedy) ");
			} 
		 
		if(ruparModel.getDataRichiestaDa() !=null && !ruparModel.getDataRichiestaDa().isEmpty() &&
				ruparModel.getDataRichiestaA() !=null && !ruparModel.getDataRichiestaA().isEmpty()) {
			 queryBuilder.append("AND t.dataRichiesta BETWEEN :dataRichiestaDa AND :dataRichiestaA ");
		} else if(ruparModel.getDataRichiestaDa() !=null && !ruparModel.getDataRichiestaDa().isEmpty()
				&& (ruparModel.getDataRichiestaA() == null || ruparModel.getDataRichiestaA().isEmpty())) {
			 queryBuilder.append("AND t.dataRichiesta >= :dataRichiestaDa ");
		}else if((ruparModel.getDataRichiestaDa() == null || ruparModel.getDataRichiestaDa().isEmpty()) && ruparModel.getDataRichiestaA() !=null
					&& !ruparModel.getDataRichiestaA().isEmpty()) {
			 queryBuilder.append("AND t.dataRichiesta <= :dataRichiestaA ");
		}

		String queryResult = queryBuilder.toString();
		Query query = entityManager.createQuery(queryResult);
	      
		if(ruparModel.getCfUtente()!=null && !ruparModel.getCfUtente().isEmpty()) {
			query.setParameter("codiceFiscaleUtente", ruparModel.getCfUtente() );
		}
		if(ruparModel.getCfOperatore()!=null && !ruparModel.getCfOperatore().isEmpty()) {
			query.setParameter("codiceFiscaleOperatore", ruparModel.getCfOperatore() );
		}
		if(ruparModel.getTicketRemedy()!=null && !ruparModel.getTicketRemedy().isEmpty()) {
			query.setParameter("ticketRemedy", ruparModel.getTicketRemedy() );
		}
		if(ruparModel.getDataRichiestaDa() !=null && !ruparModel.getDataRichiestaDa().isEmpty() &&
				ruparModel.getDataRichiestaA() !=null && !ruparModel.getDataRichiestaA().isEmpty()) {

			query.setParameter("dataRichiestaDa", Utils.toTimestampFromStringDDMMYYYY(ruparModel.getDataRichiestaDa()));
			query.setParameter("dataRichiestaA", Utils.toTimestampFromStringDDMMYYYYEndOfDay(ruparModel.getDataRichiestaA()));

		} else if(ruparModel.getDataRichiestaDa() !=null && !ruparModel.getDataRichiestaDa().isEmpty()
				&& (ruparModel.getDataRichiestaA() == null || ruparModel.getDataRichiestaA().isEmpty())) {

			query.setParameter("dataRichiestaDa", Utils.toTimestampFromStringDDMMYYYY(ruparModel.getDataRichiestaDa()));

		}else if((ruparModel.getDataRichiestaDa() == null || ruparModel.getDataRichiestaDa().isEmpty()) && ruparModel.getDataRichiestaA() !=null
				&& !ruparModel.getDataRichiestaA().isEmpty()) {

			query.setParameter("dataRichiestaA", Utils.toTimestampFromStringDDMMYYYYEndOfDay(ruparModel.getDataRichiestaA()));
		}
	      
		return query.getResultList();
	}

	@Override
	public Collection<RichiestaCredenzialiDto> findByIdUtente(UtenteDto utenteDto){

		String tabella= getTabName(new RichiestaCredenzialiDto());

		StringBuilder queryBuilder = new StringBuilder("from " + tabella + " t " + " WHERE t.utenteDto.id = :idUtente");

		String queryResult = queryBuilder.toString();
		Query query = entityManager.createQuery(queryResult);

		query.setParameter("idUtente", utenteDto.getId());

		return query.getResultList();
	}
	 	
	 	 public String getTabName(Object className) {
		        return className.getClass().getSimpleName();
	 	 }

		@Override
		public FaqRuparDto findPfd(String descrizione) {
			
			String query="SELECT s FROM  FaqRuparDto s WHERE "
					+ "s.descrizione = :descrizione "
					+ " and (s.dataChiusura IS NULL OR CURRENT_TIMESTAMP < s.dataChiusura) ";
			
			return entityManager.createQuery(query,FaqRuparDto.class).setParameter("descrizione", descrizione).getSingleResult();

		
		}
}
