/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.validator;

import it.csi.configuratorews.dto.configuratorews.InserimentoProfiloFunzionalitaBody;
import it.csi.configuratorews.exception.ErroreBuilder;

public interface ProfiloGetValidator {

	ErroreBuilder validate(String shibIdentitaCodiceFiscale, String xRequestID, String xForwardedFor,
			String xCodiceServizio, String codiceProfilo) throws Exception;

	ErroreBuilder validateNuovoProfilo(String codiceProfilo, String codiceApplicazione,
			InserimentoProfiloFunzionalitaBody body) throws Exception;

}
