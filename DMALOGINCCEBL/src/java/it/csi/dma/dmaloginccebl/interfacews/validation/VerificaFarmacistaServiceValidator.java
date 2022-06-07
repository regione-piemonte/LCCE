/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.validation;

import java.util.List;

import javax.xml.ws.WebServiceContext;

import org.springframework.stereotype.Component;

import it.csi.dma.dmaloginccebl.business.dao.util.CatalogoLog;
import it.csi.dma.dmaloginccebl.business.dao.util.Constants;
import it.csi.dma.dmaloginccebl.integration.LogGeneralDaoBean;
import it.csi.dma.dmaloginccebl.interfacews.farmacia.VerificaFarmacistaRequest;
import it.csi.dma.dmaloginccebl.interfacews.msg.Errore;

@Component
public class VerificaFarmacistaServiceValidator extends BaseServiceValidator{

	public List<Errore> validateCampi(VerificaFarmacistaRequest parameters,
			List<Errore> errori, LogGeneralDaoBean logGeneralDaoBean) {
		
		if(parameters.getFarmacia() != null) {
			
			if(parameters.getFarmacia().getCodice() == null || parameters.getFarmacia().getCodice().isEmpty()) {
				
				errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.CAMPO_OBBLIGATORIO.getValue(), Constants.CODICE_FARMACIA));
				
				return errori;
			}
			
			if(parameters.getFarmacia().getPartitaIVA() == null || parameters.getFarmacia().getPartitaIVA().isEmpty()) {
				
				errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.CAMPO_OBBLIGATORIO.getValue(), Constants.PARTITA_IVA_FARMACIA));
		
				return errori;
			}
		} else {
			
			errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.CAMPO_OBBLIGATORIO.getValue(), Constants.FARMACIA));
			
			return errori;
		}
		if(parameters.getCodiceFiscaleFarmacista() == null || parameters.getCodiceFiscaleFarmacista().isEmpty()) {
			
			errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.CAMPO_OBBLIGATORIO.getValue(), Constants.CODICE_FISCALE_FARMACISTA));
			
			return errori;
		}
		verificaCorrettezzaLunghezzaCF(parameters.getCodiceFiscaleFarmacista(), Constants.CODICE_FISCALE_FARMACISTA, errori, logGeneralDaoBean);
			
		return errori;
	}
	

}


