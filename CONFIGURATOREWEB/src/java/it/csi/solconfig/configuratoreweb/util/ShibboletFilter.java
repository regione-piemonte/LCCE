/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.util;

import java.io.IOException;

import it.csi.csi.wrapper.CSIException;
import org.apache.log4j.Category;
import javax.servlet.FilterConfig;
import java.io.IOException;
import javax.servlet.ServletException;
import it.csi.iride2.policy.exceptions.MalformedIdTokenException;
import it.csi.iride2.policy.entity.Identita;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.FilterChain;
import javax.servlet.ServletResponse;
import javax.servlet.ServletRequest;
import org.apache.log4j.Logger;
import javax.servlet.Filter;

public class ShibboletFilter implements Filter
{
    public String irideIdSessionAttr;
    public String noCheckPage;
    public static final String AUTH_ID_MARKER = "Shib-Iride-IdentitaDigitale";
    protected static final Logger log= Logger.getLogger(ShibboletFilter.class);
	private static final String DEVMODE_INIT_PARAM = "devmode";
    private static boolean devmode=false;
    
    public static boolean isDevMode() {
		return devmode;
	}

	public void doFilter(final ServletRequest req, final ServletResponse resp, final FilterChain fchn) throws IOException, ServletException {
        final HttpServletRequest hreq = (HttpServletRequest)req;
        if (hreq.getSession().getAttribute(this.irideIdSessionAttr) == null) {
            final String marker = this.getToken(hreq);
            if (marker != null) {
                try {
                    final Identita identita = new Identita(this.normalizeToken(marker));
                    log.debug((Object)("[IrideIdAdapterFilter::doFilter] Inserito in sessione marcatore IRIDE:" + identita));
                    hreq.getSession().setAttribute(this.irideIdSessionAttr, (Object)identita);
                }
                catch (MalformedIdTokenException e) {
                	log.error((Object)("[IrideIdAdapterFilter::doFilter] " + ((CSIException)e).toString()), (Throwable)e);
                }
            }
            else if (this.mustCheckPage(hreq.getRequestURI())) {
            	log.error((Object)"[IrideIdAdapterFilter::doFilter] Tentativo di accesso a pagina non home e non di servizio senza token di sicurezza");
                throw new ServletException("Tentativo di accesso a pagina non home e non di servizio senza token di sicurezza");
            }
        }
        fchn.doFilter(req, resp);
    }
    
    private boolean mustCheckPage(final String requestURI) {
        return this.noCheckPage.indexOf(requestURI) <= -1;
    }
    
    public void destroy() {
    }
    
    public void init(final FilterConfig config) throws ServletException {
        this.setIrideIdSessionAttr(config.getInitParameter("IRIDE_ID_SESSIONATTR"));
        this.setNoCheckPage(config.getInitParameter("NO_CHECK_PAGE"));
        String sDevmode = config.getInitParameter(DEVMODE_INIT_PARAM);
        if ("true".equals(sDevmode)) {
            devmode = true;
        } else {
            devmode = false;
        }
    }
    
    public String getToken(final HttpServletRequest httpreq) {
        final String marker = httpreq.getHeader("Shib-Iride-IdentitaDigitale");
        if (marker == null && devmode) {
            return getTokenDevMode(httpreq);
        } else {
            try {
                // gestione dell'encoding
                String decodedMarker = new String(marker.getBytes("ISO-8859-1"), "UTF-8");
                return decodedMarker;
            } catch (java.io.UnsupportedEncodingException e) {
                // se la decodifica non funziona comunque sempre meglio restituire
                // il marker originale non decodificato
                return marker;
            }
        }
    }
    
    public static String getTokenDevMode(HttpServletRequest httpreq) {
        String marker = (String) httpreq.getParameter(AUTH_ID_MARKER);
        return marker;
    }
    
    private String normalizeToken(final String token) {
        return token;
    }
    
    public String getIrideIdSessionAttr() {
        return this.irideIdSessionAttr;
    }
    
    public void setIrideIdSessionAttr(final String irideIdSessionAttr) {
        this.irideIdSessionAttr = irideIdSessionAttr;
    }
    
    public String getNoCheckPage() {
        return this.noCheckPage;
    }
    
    public void setNoCheckPage(final String noCheckPage) {
        this.noCheckPage = noCheckPage;
    }
    
    public static void main(String[] args) throws MalformedIdTokenException {
		Identita i = new Identita("AAAAAA00B77B000F/Csi Piemonte/Demo 20/IPA/20220302123157/4/iA9Pz9Gwpkj4gy1JkAm2uQ==");
		System.out.println(i);
	}
}