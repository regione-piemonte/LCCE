/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.util;

import javax.xml.namespace.QName;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.HashSet;
import java.util.Set;

public class WSSESecurityUnderstand implements SOAPHandler<SOAPMessageContext> {
    @Override
    public Set<QName> getHeaders() {
        final QName securityHeader = new QName(
                "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd",
                "Security",
                "wsse");

        final Set<QName> headers = new HashSet<>();
        headers.add(securityHeader);

        // notify the runtime that this is handled
        return headers;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        // we must return true, or else the runtime will return
        // wrong wrapper element name (like makeTransfer instead of
        // makeTransferResponse)
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        // we must return true, or else the runtime will return
        // wrong wrapper element name (like makeTransfer instead of
        // makeTransferResponse)
        return true;
    }

    @Override
    public void close(MessageContext context) {
    }
}
