/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.validator.impl;

import it.csi.configuratorews.business.dao.CatalogoLogLowDao;
import it.csi.configuratorews.business.dao.MessaggiUtenteLowDao;
import it.csi.configuratorews.business.dto.CatalogoLogDto;
import it.csi.configuratorews.business.dto.MessaggiUtenteDto;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.util.LogUtil;
import it.csi.configuratorews.util.Utils;

import java.text.MessageFormat;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaseValidatorImpl {

	protected LogUtil log = new LogUtil(this.getClass());

	@Autowired
	MessaggiUtenteLowDao messaggiUtenteLowDao;
	
	@Autowired
	protected CatalogoLogLowDao catalogoLogDao;
	
	public BaseValidatorImpl() {
		super();
	}

	protected ErroreBuilder validateHeader(String shibIdentitaCodiceFiscale, String xCodiceServizio, String xRequestId, String xForwaredFor) throws Exception{
		if(shibIdentitaCodiceFiscale == null || shibIdentitaCodiceFiscale.isEmpty()){
			return generateErrore(Constants.PARAMETRO_NON_VALORIZZATO, Constants.SHIB_IDENTITA_DIGITALE);
		}
		if(xCodiceServizio == null || xCodiceServizio.isEmpty()){
			 return generateErrore(Constants.PARAMETRO_NON_VALORIZZATO, Constants.X_CODICE_SERVIZIO);
		}
		if(xRequestId == null || xRequestId.isEmpty()){
			 return generateErrore(Constants.PARAMETRO_NON_VALORIZZATO, Constants.X_REQUEST_ID);
		}
		if(xForwaredFor == null || xForwaredFor.isEmpty()){
			 return generateErrore(Constants.PARAMETRO_NON_VALORIZZATO, Constants.X_FORWARED_FOR);
		}
		return null;
	}

	protected ErroreBuilder generateErroreFromMessaggiUtente(String codiceErrore, Object... parameters) throws Exception {
		MessaggiUtenteDto messaggiUtenteDto = new MessaggiUtenteDto();
		MessaggiUtenteDto messaggioDaVisualizzare = new MessaggiUtenteDto();
		if (codiceErrore != null && !codiceErrore.isEmpty()) {
			//ricerca del messaggioErrore da visualizzare	
			messaggiUtenteDto.setCodice(codiceErrore);
			messaggiUtenteDto = ricercaMessaggiErrore(messaggiUtenteDto);
			
			if(messaggiUtenteDto != null){
				//creo una copia del messaggioErrore
				messaggioDaVisualizzare = creaCopia(messaggiUtenteDto);

				String pattern= messaggioDaVisualizzare.getDescrizione();

				if(parameters!=null) {
					messaggioDaVisualizzare.setDescrizione(MessageFormat.format(pattern.replace("",""), parameters));
				}
			}
			
		}	
		
		return ErroreBuilder.from(Response.Status.BAD_REQUEST)
				.codice(messaggioDaVisualizzare.getCodice()).descrizione(messaggioDaVisualizzare.getDescrizione());
	}
	
	
	
	public MessaggiUtenteDto creaCopia(MessaggiUtenteDto dto) throws Exception {
		MessaggiUtenteDto clone = new MessaggiUtenteDto();
		
		clone.setCodice(dto.getCodice());
		clone.setDescrizione(dto.getDescrizione());
		clone.setId(dto.getId());
		clone.setDataInserimento(dto.getDataInserimento());
		clone.setTipoMessaggio(dto.getTipoMessaggio());
		
		return clone;	
	}

	
	public MessaggiUtenteDto ricercaMessaggiErrore(MessaggiUtenteDto dto) throws Exception {
		dto = Utils.getFirstRecord(messaggiUtenteLowDao.findByFilter(dto));
		return dto ;
	}

	protected ErroreBuilder generateErrore(String codiceErrore, Object... parameters) throws Exception {		
		CatalogoLogDto catalogoLogDto = getCatalogoLogDto(codiceErrore);		
		if(catalogoLogDto != null){
			String pattern= catalogoLogDto.getDescrizione();
			if(parameters!=null) {
				catalogoLogDto.setDescrizione(MessageFormat.format(pattern.replace("",""), parameters));
			}
		}			
		return ErroreBuilder.from(Response.Status.BAD_REQUEST)
				.codice(catalogoLogDto.getCodice()).descrizione(catalogoLogDto.getDescrizione());
	}

	private CatalogoLogDto getCatalogoLogDto(String codice) {
		CatalogoLogDto cat = new CatalogoLogDto();
		cat.setCodice((codice == null ? Constants.AUTH_ER_000 : codice));
		CatalogoLogDto d = Utils.getFirstRecord(catalogoLogDao.findByCodice(cat));
		if (d == null) {
			cat.setCodice(Constants.AUTH_ER_000);
			d = Utils.getFirstRecord(catalogoLogDao.findByCodice(cat));
		}

		return d;
	}
}
