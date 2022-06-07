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
import it.csi.dma.dmaloginccebl.interfacews.farmacia.GetFarmacieAderentiRequest;
import it.csi.dma.dmaloginccebl.interfacews.msg.Errore;

@Component
public class FarmacieAderentiServiceValidator extends BaseServiceValidator {

	public List<Errore> validateCampi(GetFarmacieAderentiRequest parameters, List<Errore> errori, LogGeneralDaoBean logGeneralDaoBean) {
		
		if(parameters.getRichiedente() != null) {
			
			validaRichiedente(parameters.getRichiedente(), errori, logGeneralDaoBean);
			
			verificaCorrettezzaLunghezzaCF(parameters.getRichiedente() != null ? parameters.getRichiedente().getCodiceFiscaleRichiedente() : null, Constants.CODICE_FISCALE_RICHIEDENTE, errori, logGeneralDaoBean);
			
		} else {
			
			errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.CAMPO_OBBLIGATORIO.getValue(), Constants.RICHIEDENTE));
			
			return errori;
		}
		
		return errori;
	}
}
