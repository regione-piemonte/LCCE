/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.validator;

import it.csi.configuratorews.dto.configuratorews.ListaPermessi;
import it.csi.configuratorews.exception.ErroreBuilder;

public interface PermessiValidator {
	ErroreBuilder validate(ListaPermessi listaPermessi) throws Exception;
}
