/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.model;

import it.csi.solconfig.configuratoreweb.presentation.constants.ConstantsWebApp;

public class RicercaModel {

    private Integer numeroPagina = ConstantsWebApp.PAGINAZIONE_PRIMA_PAGINA;

    private Integer numeroElementi = ConstantsWebApp.PAGINAZIONE_20_ELEMENTI;

    public Integer getNumeroPagina() {
        return numeroPagina;
    }

    public void setNumeroPagina(Integer numeroPagina) {
        this.numeroPagina = numeroPagina;
    }

    public Integer getNumeroElementi() {
        return numeroElementi;
    }

    public void setNumeroElementi(Integer numeroElementi) {
        this.numeroElementi = numeroElementi;
    }

}
