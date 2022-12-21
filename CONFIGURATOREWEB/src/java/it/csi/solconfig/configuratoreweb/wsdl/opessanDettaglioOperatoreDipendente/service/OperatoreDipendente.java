
/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatoreDipendente.service;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.2.2
 * Thu May 19 11:02:53 CEST 2022
 * Generated source version: 2.2.2
 * 
 */


@WebServiceClient(name = "OperatoreDipendente", 
                  wsdlLocation = "https://tst-applogic.reteunitaria.piemonte.it:1449/opessanwsj/services/operatoreDipendenteUserTkn?wsdl",
                  targetNamespace = "http://opessan.opessanws.services.csi.it/") 
public class OperatoreDipendente extends Service {

    public final static URL WSDL_LOCATION;
    public final static QName SERVICE = new QName("http://opessan.opessanws.services.csi.it/", "OperatoreDipendente");
    public final static QName OperatoreDipendenteCXFImplPort = new QName("http://opessan.opessanws.services.csi.it/", "OperatoreDipendenteCXFImplPort");
    static {
        URL url = null;
        try {
            url = new URL("https://tst-applogic.reteunitaria.piemonte.it:1449/opessanwsj/services/operatoreDipendenteUserTkn?wsdl");
        } catch (MalformedURLException e) {
            System.err.println("Can not initialize the default wsdl from https://tst-applogic.reteunitaria.piemonte.it:1449/opessanwsj/services/operatoreDipendenteUserTkn?wsdl");
            // e.printStackTrace();
        }
        WSDL_LOCATION = url;
    }

    public OperatoreDipendente(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public OperatoreDipendente(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public OperatoreDipendente() {
        super(WSDL_LOCATION, SERVICE);
    }

    /**
     * 
     * @return
     *     returns OperatoreDipendente
     */
    @WebEndpoint(name = "OperatoreDipendenteCXFImplPort")
    public it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatoreDipendente.OperatoreDipendente getOperatoreDipendenteCXFImplPort() {
        return super.getPort(OperatoreDipendenteCXFImplPort, it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatoreDipendente.OperatoreDipendente.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns OperatoreDipendente
     */
    @WebEndpoint(name = "OperatoreDipendenteCXFImplPort")
    public it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatoreDipendente.OperatoreDipendente getOperatoreDipendenteCXFImplPort(WebServiceFeature... features) {
        return super.getPort(OperatoreDipendenteCXFImplPort, it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatoreDipendente.OperatoreDipendente.class, features);
    }

}
