/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.util;

import it.csi.solconfig.configuratoreweb.presentation.constants.ConstantsWebApp;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionManagerInterceptor implements HandlerInterceptor {

    protected static Logger log = Logger.getLogger(ConstantsWebApp.CONFIGURATORE);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest,
                             HttpServletResponse httpServletResponse, Object o) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest,
                           HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if(modelAndView != null){
            if (((Boolean) httpServletRequest
                    .getAttribute("it.csi.dma.isRequestedSessionIdValid")) == false &&
                    !modelAndView.getViewName().equals(ConstantsWebApp.REDIRECT_UTENTI)) {
                log.error("[SessionInterceptor::intercept] SESSIONE NON VALIDA !");
                modelAndView.setViewName(ConstantsWebApp.SESSION_TIMEOUT);
            }
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
                                HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
