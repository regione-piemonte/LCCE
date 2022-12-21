/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.interfacews.client.abilitazione;

import javax.xml.bind.annotation.XmlType;
import java.util.Objects;

@XmlType(namespace="http://dmac.csi.it/")
public class Applicazione {

    private String codiceApplicazione;

    public String getCodiceApplicazione() {
        return codiceApplicazione;
    }

    public void setCodiceApplicazione(String codiceApplicazione) {
        this.codiceApplicazione = codiceApplicazione;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Applicazione that = (Applicazione) o;
        return getCodiceApplicazione().equals(that.getCodiceApplicazione());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCodiceApplicazione());
    }
}
