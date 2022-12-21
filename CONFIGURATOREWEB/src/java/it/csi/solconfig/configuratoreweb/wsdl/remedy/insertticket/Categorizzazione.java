/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.wsdl.remedy.insertticket;

public class Categorizzazione {
    private CategoriaOperativa categoriaOperativa;
    private CategoriaApplicativa categoriaApplicativa;

    public CategoriaOperativa getCategoriaOperativa() {
        return categoriaOperativa;
    }

    public void setCategoriaOperativa(CategoriaOperativa categoriaOperativa) {
        this.categoriaOperativa = categoriaOperativa;
    }

    public CategoriaApplicativa getCategoriaApplicativa() {
        return categoriaApplicativa;
    }

    public void setCategoriaApplicativa(CategoriaApplicativa categoriaApplicativa) {
        this.categoriaApplicativa = categoriaApplicativa;
    }
}
