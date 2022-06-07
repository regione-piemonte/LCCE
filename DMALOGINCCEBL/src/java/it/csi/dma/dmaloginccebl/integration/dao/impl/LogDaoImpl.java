/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.integration.dao.impl;

import it.csi.dma.dmaloginccebl.business.dao.CatalogoLogLowDao;
import it.csi.dma.dmaloginccebl.business.dao.LogLowDao;
import it.csi.dma.dmaloginccebl.business.dao.dto.CatalogoLogDto;
import it.csi.dma.dmaloginccebl.business.dao.dto.LogDto;
import it.csi.dma.dmaloginccebl.business.dao.util.Constants;
import it.csi.dma.dmaloginccebl.integration.dao.LogDao;
import it.csi.dma.dmaloginccebl.interfacews.msg.Errore;
import it.csi.dma.dmaloginccebl.util.Utils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LogDaoImpl implements LogDao {
	
	Logger logger = Logger.getLogger(Constants.APPLICATION_CODE);

	@Autowired
	CatalogoLogLowDao catalogoLogLowDao;

	@Autowired
	LogLowDao logLowDao;

	/*
	 * Inserisce le informazioni contenute nell'oggetto in entrata tabella
	 * auth_l_log Recupera dal codice inserito la descrizione del log
	 * 
	 */
	@Override
	public CatalogoLogDto log(LogDto logDto, String codiceLog, Object... parametri) {
		LogDto log = new LogDto();
		if (logDto != null) {
			copyPropertiesLog(logDto, log);
			log.setCodiceLog(codiceLog);
			// Recupero descrizione log
			log.setCatalogoLogDto(
					Utils.getFirstRecord(catalogoLogLowDao.findByCodice(new CatalogoLogDto(), codiceLog)));

			//Se ho trovato il codice nel catalogo prendo la descrizione
			//Se non dovesse esserci metto una descrizione di default e vado avanti
			String descrizioneLog = null;
			if(log.getCatalogoLogDto() != null &&
					log.getCatalogoLogDto().getDescrizioneLog() != null) {
				descrizioneLog = log.getCatalogoLogDto().getDescrizioneLog();
			}else {
				descrizioneLog = "...";
			}
			//Insersco la descrizione trovata e sostituisco eventuali parametri
			log.setInformazioniTracciate(String.format(descrizioneLog, parametri));
			// Inserisco Log completo di descrizione
			logLowDao.insert(log);

			return log.getCatalogoLogDto();
		}
		return null;
	}

	private void copyPropertiesLog(LogDto logDto, LogDto log) {
		log.setMessaggiDto(logDto.getMessaggiDto());
		log.setServiziDto(logDto.getServiziDto());
		log.setCfAssistito(logDto.getCfAssistito());
	}

	/*
	 * (non-Javadoc)
	 * @see it.csi.dma.dmaloginccebl.integration.dao.LogDao#logErrore(it.csi.dma.dmaloginccebl.business.dao.dto.LogDto, java.lang.Object[])
	 * Inserisce un log per il codice d'errore passato in input e ritorna l'oggetto errore per la response dei servizi
	 * Se viene passato un codice d'errore viene direttamente utilizzato quello altrimenti cerca di utilizzare quello gia' presente nel LogDto
	 */
	@Override
	public Errore logErrore(LogDto logDto, String codiceErrore, Object... parametri) {
		Errore errore = new Errore();
		CatalogoLogDto catalogoLogDto = log(logDto, codiceErrore, parametri);
		errore.setCodice(codiceErrore);
		if(catalogoLogDto != null) {
			//Formatto la stringa per eventuali parametri presenti
			errore.setDescrizione(String.format(catalogoLogDto.getDescrizione(), parametri));
		}else{
			errore.setDescrizione("...");
		}
		return errore;
	}
	
	public CatalogoLogDto getCatalogoLogDto(String codiceErrore) {
		
		CatalogoLogDto catalogoLogDto = new CatalogoLogDto();
		catalogoLogDto.setCodice(codiceErrore);
		catalogoLogDto = Utils.getFirstRecord(catalogoLogLowDao.findByCodice(catalogoLogDto));
		return catalogoLogDto;
	}

}
