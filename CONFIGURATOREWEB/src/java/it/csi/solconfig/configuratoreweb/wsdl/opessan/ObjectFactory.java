/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.solconfig.configuratoreweb.wsdl.opessan;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the it.csi.solconfig.configuratoreweb.wsdl.opessan package.
 * &lt;p&gt;An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _RicercaOperatoreRequest_QNAME = new QName("http://opessan.opessanws.def.csi.it/", "RicercaOperatoreRequest");
    private final static QName _GetOperatoreAttivo_QNAME = new QName("http://opessan.opessanws.def.csi.it/", "getOperatoreAttivo");
    private final static QName _GetOperatoreAttivoResponse_QNAME = new QName("http://opessan.opessanws.def.csi.it/", "getOperatoreAttivoResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: it.csi.solconfig.configuratoreweb.wsdl.opessan
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetOperatoreAttivoResponse }
     * 
     */
    public GetOperatoreAttivoResponse createGetOperatoreAttivoResponse() {
        return new GetOperatoreAttivoResponse();
    }

    /**
     * Create an instance of {@link RicercaOperatore_Type }
     * 
     */
    public RicercaOperatore_Type createRicercaOperatore_Type() {
        return new RicercaOperatore_Type();
    }

    /**
     * Create an instance of {@link RicercaOperatoreResponse }
     * 
     */
    public RicercaOperatoreResponse createRicercaOperatoreResponse() {
        return new RicercaOperatoreResponse();
    }

    /**
     * Create an instance of {@link RicercaOperatoreBody }
     * 
     */
    public RicercaOperatoreBody createRicercaOperatoreBody() {
        return new RicercaOperatoreBody();
    }

    /**
     * Create an instance of {@link GetOperatoreAttivo }
     * 
     */
    public GetOperatoreAttivo createGetOperatoreAttivo() {
        return new GetOperatoreAttivo();
    }

    /**
     * Create an instance of {@link IdentificativoProfilo }
     * 
     */
    public IdentificativoProfilo createIdentificativoProfilo() {
        return new IdentificativoProfilo();
    }

    /**
     * Create an instance of {@link GetOperatoreAttivoResponse.Return }
     * 
     */
    public GetOperatoreAttivoResponse.Return createGetOperatoreAttivoResponseReturn() {
        return new GetOperatoreAttivoResponse.Return();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RicercaOperatore_Type }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link RicercaOperatore_Type }{@code >}
     */
    @XmlElementDecl(namespace = "http://opessan.opessanws.def.csi.it/", name = "RicercaOperatoreRequest")
    public JAXBElement<RicercaOperatore_Type> createRicercaOperatoreRequest(RicercaOperatore_Type value) {
        return new JAXBElement<RicercaOperatore_Type>(_RicercaOperatoreRequest_QNAME, RicercaOperatore_Type.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOperatoreAttivo }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetOperatoreAttivo }{@code >}
     */
    @XmlElementDecl(namespace = "http://opessan.opessanws.def.csi.it/", name = "getOperatoreAttivo")
    public JAXBElement<GetOperatoreAttivo> createGetOperatoreAttivo(GetOperatoreAttivo value) {
        return new JAXBElement<GetOperatoreAttivo>(_GetOperatoreAttivo_QNAME, GetOperatoreAttivo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOperatoreAttivoResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetOperatoreAttivoResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://opessan.opessanws.def.csi.it/", name = "getOperatoreAttivoResponse")
    public JAXBElement<GetOperatoreAttivoResponse> createGetOperatoreAttivoResponse(GetOperatoreAttivoResponse value) {
        return new JAXBElement<GetOperatoreAttivoResponse>(_GetOperatoreAttivoResponse_QNAME, GetOperatoreAttivoResponse.class, null, value);
    }

}
