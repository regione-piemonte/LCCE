/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.model;

import java.util.List;

public class PaginaDTO<T> {

    private int pagineTotali;

    private long elementiTotali;

    private List<T> elementi;

    public int getPagineTotali() {
        return pagineTotali;
    }

    public void setPagineTotali(int pagineTotali) {
        this.pagineTotali = pagineTotali;
    }

    public long getElementiTotali() {
        return elementiTotali;
    }

    public void setElementiTotali(long elementiTotali) {
        this.elementiTotali = elementiTotali;
    }

    public List<T> getElementi() {
        return elementi;
    }

    public void setElementi(List<T> elementi) {
        this.elementi = elementi;
    }
}

