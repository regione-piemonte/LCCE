/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.dto.configuratorews;

import java.util.List;

public class Pagination<T> {
    private Long count;
    private List<T> listaRis;


    public Long getCount() {
        return count;
    }
    public void setCount(Long count) {
        this.count = count;
    }
    public List<T> getListaRis() {
        return listaRis;
    }
    public void setListaRis(List<T> listaRis) {
        this.listaRis = listaRis;
    }


}
