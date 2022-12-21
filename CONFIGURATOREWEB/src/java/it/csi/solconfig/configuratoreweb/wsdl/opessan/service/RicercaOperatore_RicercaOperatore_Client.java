/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.solconfig.configuratoreweb.wsdl.opessan.service;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import it.csi.solconfig.configuratoreweb.wsdl.opessan.GetOperatoreAttivoResponse;
import it.csi.solconfig.configuratoreweb.wsdl.opessan.RicercaOperatore_Type;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;

/**
 * This class was generated by Apache CXF 3.4.3
 * 2021-03-30T09:59:25.029+02:00
 * Generated source version: 3.4.3
 *
 */
public final class RicercaOperatore_RicercaOperatore_Client {

    private static final QName SERVICE_NAME = new QName("http://opessan.opessanws.services.csi.it/", "RicercaOperatore");

    private RicercaOperatore_RicercaOperatore_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = RicercaOperatore.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) {
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        RicercaOperatore ss = new RicercaOperatore(wsdlURL, SERVICE_NAME);
        it.csi.solconfig.configuratoreweb.wsdl.opessan.RicercaOperatore port = ss.getRicercaOperatore();

        {
        System.out.println("Invoking getOperatoreAttivo...");
        RicercaOperatore_Type _getOperatoreAttivo_arg0 = null;
        GetOperatoreAttivoResponse.Return _getOperatoreAttivo__return = port.getOperatoreAttivo(_getOperatoreAttivo_arg0);
        System.out.println("getOperatoreAttivo.result=" + _getOperatoreAttivo__return);


        }

        System.exit(0);
    }

}