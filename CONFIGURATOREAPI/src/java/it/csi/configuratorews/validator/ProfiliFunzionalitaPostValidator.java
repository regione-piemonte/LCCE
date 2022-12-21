/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.validator;

import it.csi.configuratorews.dto.configuratorews.InserimentoProfiloFunzionalitaBody;
import it.csi.configuratorews.exception.ErroreBuilder;

public interface ProfiliFunzionalitaPostValidator {

	ErroreBuilder validateNuovaFunzionalita(String codiceFunzionalita, String codiceApplicazione, String codiceAzienda,
			InserimentoProfiloFunzionalitaBody body) throws Exception;

	ErroreBuilder validateCampiObbligatori(String codiceFunzionalita, String codiceProfilo, String descrizioneProfilo,
			String descrizioneFunzionalita) throws Exception;

}
