/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.util;

import java.util.Iterator;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.log4j.Logger;

public class CustomInterceptorOut extends AbstractPhaseInterceptor {

    private static Logger log = Logger.getLogger(CustomInterceptorOut.class
            .getCanonicalName());

    public CustomInterceptorOut() {
        super(Phase.SETUP);
    }

    @Override
    public void handleMessage(Message message) throws Fault {
        Iterator<Interceptor<? extends Message>> iterator =
                message.getInterceptorChain().iterator();
        Interceptor<?> removeInterceptor =  null;
        for (; iterator.hasNext(); ) {
           Interceptor<?> interceptor = iterator.next();
           if (interceptor.getClass().getName().equals("org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor")) {
                removeInterceptor = interceptor;
                break;
            }
        }

        if (removeInterceptor !=  null) {
            log.debug("Removing interceptor {}"+removeInterceptor.getClass().getName());
           message.getInterceptorChain().remove(removeInterceptor);
       }
        log.error("customInterceptorOut");
    }
}
