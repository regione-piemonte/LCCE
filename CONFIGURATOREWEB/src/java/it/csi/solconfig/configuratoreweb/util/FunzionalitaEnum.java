/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.util;

public enum FunzionalitaEnum {

    OPRicercaUtente("OP-RicercaUtente"),
    OPInserisciNuovoUtente("OP-InserisciNuovoUtente"),
    OPRichiestaCredenzialiRUPAR("OP-RichiestaCredenzialiRUPAR"),
    OPInvioMailUtente("OP-InvioMailUtente"),
    OPModificaConfigurazioneUtente("OP-ModificaConfigurazioneUtente"),
    OPExportUtentiAbilitazioni("OP-ExportUtentiAbilitazioni"),
    OPRicercaRuolo("OP-RicercaRuolo"),
    OPInserisciNuovoRuolo("OP-InserisciNuovoRuolo"),
    OPModificaRuolo("OP-ModificaRuolo"),
    OPRicercaProfilo("OP-RicercaProfilo"),
    OPInserisciNuovoProfilo("OP-InserisciNuovoProfilo"),
    OPModificaProfilo("OP-ModificaProfilo"),
    OPRicercaCredenzialiRUPAR("OP-RicercaCredenzialiRUPAR"),
    OPListaSOLconConfiguratore("OP-ListaSOLconConfiguratore"),
    OPListaProfiliCompleta("OP-ListaProfiliCompleta"),
    OPRicercaAbilitazioneMassiva("OP-RicercaAbilitazioneMassiva"),
    OPAbilitazioneMassiva("OP-AbilitazioneMassiva"),
    OPRicercaDisabilitazioneMassiva("OP-RicercaDisabilitazioneMassiva"),
    OPDisabilitazioneMassiva("OP-DisabilitazioneMassiva"),
    OPVisualizzaStoricoMassiva("OP-VisualizzaStoricoMassiva"),
    SUPERUSERCONF_PROF("SUPERUSERCONF"),
    CONF_TITOLARE_PROF("CONF_TITOLARE"),
    CONF_DELEGATO_PROF("CONF_DELEGATO"),
    OPERATORE_PROF("OP_CONF");

    private String value;

    private FunzionalitaEnum(String value){

        this.value = value;

    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
