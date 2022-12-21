/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.validator;

import it.csi.configuratorews.exception.ErroreBuilder;

public interface FunzionalitaPerApplicazioneValidator {
	
	public ErroreBuilder validate(String codiceFunzionalita, String codiceApplicazione) throws Exception;
	
	public ErroreBuilder validateCodiceProfilo(String codiceFunzionalita, String codiceApplicazione) throws Exception;
	
}
