/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.validator;

import it.csi.configuratorews.exception.ErroreBuilder;

public interface AbilitazioniValidator {

	ErroreBuilder checkCampiObbligatori(String shibIdentitaCodiceFiscale, String xRequestID, String xForwardedFor,
			String xCodiceServizio, String codiceRuolo, String codiceCollocazione, String codiceAzienda)
			throws Exception;

	ErroreBuilder verificaAbilitazioniAttive(String codiceProfilo, String codiceApplicazione) throws Exception;
	
	ErroreBuilder verificaAbilitazioneUtente(String codiceFiscale, String codiceCollocazione, String codiceRuolo,
			String codiceApplicazione) throws Exception;

}
