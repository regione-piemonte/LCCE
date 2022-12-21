/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.jboss.resteasy.annotations.interception.ServerInterceptor;
import org.jboss.resteasy.core.ResourceMethod;
import org.jboss.resteasy.core.ServerResponse;
import org.jboss.resteasy.spi.Failure;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.interception.PostProcessInterceptor;
import org.jboss.resteasy.spi.interception.PreProcessInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

import it.csi.configuratorews.business.dao.MessaggiLowDao;
import it.csi.configuratorews.business.dao.MessaggiXmlLowDao;
import it.csi.configuratorews.business.dao.ServiziLowDao;
import it.csi.configuratorews.business.dto.MessaggiDto;
import it.csi.configuratorews.business.dto.MessaggiXmlDto;
import it.csi.configuratorews.business.dto.ServiziDto;

// it.csi.configuratorews.util.TraceRequestInterceptor
@Provider
@ServerInterceptor
public class TraceRequestInterceptor implements PreProcessInterceptor, PostProcessInterceptor {

	private static final Logger logger = Logger.getLogger(LogUtil.APPLICATION_CODE);

	private @Context HttpServletRequest httpRequest;

	private MessaggiLowDao messaggiDao;
	private MessaggiXmlLowDao messaggiXmlLowDao;
	private ServiziDto serviziDto = null;
	public ServiziLowDao serviziLowDao;

	public TraceRequestInterceptor() {
		ApplicationContext appContext = SpringApplicationContextProvider.getApplicationContext();
		messaggiDao = appContext.getBean(MessaggiLowDao.class);
		messaggiXmlLowDao = appContext.getBean(MessaggiXmlLowDao.class);
		serviziLowDao = appContext.getBean(ServiziLowDao.class);
	}

	@Override
	public ServerResponse preProcess(HttpRequest request, ResourceMethod arg1) throws Failure, WebApplicationException {
		try {
			if (serviziDto == null) {
				serviziDto = Utils
						.getFirstRecord(serviziLowDao.findByCodice(new ServiziDto(), Servizi.CONF_API.getValue()));
			}

			httpRequest.setAttribute("tempoPartenza", System.currentTimeMillis());

			MessaggiDto msg = new MessaggiDto();
			String ip = null;
			msg.setServiziDto(serviziDto);
			msg.setCfRichiedente(httpRequest.getHeader("Shib-Identita-CodiceFiscale"));

			if (httpRequest.getHeader("X-Forwarded-For") != null) {
				String[] sff = httpRequest.getHeader("X-Forwarded-For").split(",");
				if (sff != null && sff.length > 0) {
					ip = sff[0];
				}
			}
			msg.setClientIp((ip != null ? ip : "unknow"));
			msg.setDataRicezione(new Timestamp(System.currentTimeMillis()));
			msg.setRequestUri(httpRequest.getRequestURI());

			messaggiDao.insert(msg);
			httpRequest.setAttribute(Constants.IDMSG, msg.getId());

			BufferedInputStream bis = new BufferedInputStream(request.getInputStream());
			ByteArrayOutputStream bufReq = new ByteArrayOutputStream();
			ByteArrayOutputStream bufDb = new ByteArrayOutputStream();
			String content = "";
			int result;

			// parte iniziale del log - con la request, query e headere
			String baseStr = httpRequest.getMethod() + " " + httpRequest.getRequestURI()
					+ (StringUtils.isEmpty(httpRequest.getQueryString()) ? "" : "?" + httpRequest.getQueryString())
					+ "\n";

			Enumeration<String> headerNames = httpRequest.getHeaderNames();
			while (headerNames != null && headerNames.hasMoreElements()) {
				String headerName = headerNames.nextElement();
				if (httpRequest.getHeader(headerName) != null) {
					baseStr += headerName + ": " + httpRequest.getHeader(headerName) + "\n";
				}
			}
			baseStr += "\n";
			// init per la scrittura
			bufDb.write(baseStr.getBytes());
			// fine parte iniziale

			try {
				result = bis.read();
				while (result != -1) {
					byte b = (byte) result;
					bufReq.write(b); // leggo per propagare il body a resteasy
					bufDb.write(b);// leggo per scrivere sul DB
					result = bis.read();
				}
				// logger.info("[TraceRequestInterceptor::preProcess] " + bufDb.toString());

				MessaggiXmlDto xml = new MessaggiXmlDto();
				xml.setMessaggiDto(msg);
				xml.setXmlIn(bufDb.toByteArray());
				messaggiXmlLowDao.insert(xml);

				httpRequest.setAttribute("idxml", xml.getId());
			} catch (IOException ex) {
				logger.warn("[TraceRequestInterceptor::preProcess]", ex);

			}
			try {
				content = bufReq.toString("UTF-8");

				ByteArrayInputStream bi = new ByteArrayInputStream(bufReq.toByteArray());
				request.setInputStream(bi);
			} catch (UnsupportedEncodingException ex) {
				logger.warn("[TraceRequestInterceptor::preProcess]", ex);
			}

		} catch (Exception e) {
			logger.warn("[TraceRequestInterceptor::preProcess]", e);
		}

		return null;

	}

	@Override
	public void postProcess(ServerResponse response) {
		try {
			Long idmsg = (Long) httpRequest.getAttribute(Constants.IDMSG);
			Long idxml = (Long) httpRequest.getAttribute("idxml");
			Long tempoPartenza = (Long) httpRequest.getAttribute("tempoPartenza");
			long elapsed = System.currentTimeMillis() - tempoPartenza;

			logger.info("[TraceRequestInterceptor::postProcess] idmsg        :: " + idmsg);
			logger.info("[TraceRequestInterceptor::postProcess] idxml        :: " + idxml);
			logger.info("[TraceRequestInterceptor::postProcess] elapsed      :: " + elapsed);
			logger.info("[TraceRequestInterceptor::postProcess] Content-Type :: "
					+ response.getMetadata().get("Content-Type"));

			if (response.getMetadata().get("Content-Type") != null
					&& response.getMetadata().get("Content-Type").toString().equals("[application/json]")) {
				ObjectMapper mapper = new ObjectMapper();
				ByteArrayOutputStream os = new ByteArrayOutputStream();
				mapper.writeValue(os, response.getEntity());

				// logger.info("[TraceRequestInterceptor::postProcess] " + os.toString());

				// aggiornamento DB
				if (idmsg != null) {
					MessaggiDto msg = new MessaggiDto();
					msg.setId(idmsg);
					msg.setEsito(String.valueOf(response.getStatus()));
					messaggiDao.update(msg);

					if (idxml != null) {
						MessaggiXmlDto xml = new MessaggiXmlDto();
						xml.setId(idxml);
						xml.setXmlOut(os.toByteArray());
						messaggiXmlLowDao.update(xml);

					}
				}
				// fine aggiornamento DB
			}

		} catch (Exception e) {
			logger.warn("[TraceRequestInterceptor::postProcess] ", e);
		}

	}

}
