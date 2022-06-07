/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.util;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class SessionManagerFilter implements Filter {

	/**
	 * 
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) arg0;
        request.setAttribute("it.csi.dma.isRequestedSessionIdValid", request.isRequestedSessionIdValid());
        arg2.doFilter(arg0, arg1);
   }

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
}
