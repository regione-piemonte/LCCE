/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.configuratorews.rest;

import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import it.csi.configuratorews.business.configuratorews.rest.service.ServizioAttivoGetService;
import it.csi.configuratorews.business.dao.CatalogoLogLowDao;
import it.csi.configuratorews.business.dao.MessaggiErroreLowDao;
import it.csi.configuratorews.business.dto.CatalogoLogDto;
import it.csi.configuratorews.business.dto.MessaggiDto;
import it.csi.configuratorews.business.dto.MessaggiErroreDto;
import it.csi.configuratorews.dto.configuratorews.Errore;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.exception.RESTException;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.util.LogUtil;
import it.csi.configuratorews.util.Utils;

/**
 * Classe base dell'implementazione della business logic di un generico servizio
 * REST.
 * 
 * Estendendo questa classe si possono aggiugere le seguenti annotazioni di
 * Spring:
 * 
 * \@Service \@Scope(BeanDefinition.SCOPE_PROTOTYPE)
 * 
 * 
 * @author Virli Luca
 */
public abstract class RESTBaseService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected LogUtil log = new LogUtil(this.getClass());

	protected Response res;

	protected SecurityContext securityContext;
	protected HttpHeaders httpHeaders;
	protected HttpServletRequest request;

//	@Autowired
//	protected ErroreService erroreService;

	@Autowired
	protected CatalogoLogLowDao catalogoLog;

	@Autowired
	protected MessaggiErroreLowDao messErrori;

	public RESTBaseService(SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request) {
		this.securityContext = securityContext;
		this.httpHeaders = httpHeaders;
		this.request = request;
	}

	public Response executeService() {
		final String METHOD_NAME = "executeService";

		log.info(METHOD_NAME, "Start. Service: %s", this.getClass().getSimpleName());
		long startTime = System.currentTimeMillis();
		try {
			if (this.securityContext.isUserInRole(Constants.RUOLO_MONITORAGGIO)
					&& !this.getClass().getSimpleName().equals(ServizioAttivoGetService.class.getSimpleName())) {
				throw ErroreBuilder.from(400, "UNAUTHORIZED").exception();
			}
			res = execute();
		} catch (Exception e) {
			handleException(e);
		} finally {
			long elapsedTimeMillis = System.currentTimeMillis() - startTime;
			log.info(METHOD_NAME, "End. Service: %s. Elapsed time: %s ms.", this.getClass().getSimpleName(),
					elapsedTimeMillis);
		}
		
		tracciaMessaggiErrore();
		return res;

	}

	/**
	 * Gestione di base degli errori. L'implementazione può fare evenualmete
	 * override.
	 * 
	 * @param e
	 */
	protected Response handleException(Exception e) {
		final String METHOD_NAME = "handleException";

		if (e instanceof RESTException) {
			String msg = "Errore nell'esecuzione del servizio " + ((RESTException) e).getStatus() + ": "
					+ e.getMessage();
			log.error(METHOD_NAME, msg.replaceAll("%", "%%"), e);
			res = ((RESTException) e).getResponse();
		} else {
			String msg = "Errore generico nell'esecuzione del servizio " + this.getClass().getSimpleName(); // + ":
																											// "+e.getMessage();
			log.error(METHOD_NAME, msg.replaceAll("%", "%%"), e);

			res = ErroreBuilder.from(Status.INTERNAL_SERVER_ERROR).descrizione(msg).exception().getResponse();
			// res = Response.status(500).type(MediaType.TEXT_HTML).entity(msg).build();
		}

		return res;
	}

	private CatalogoLogDto getCatalogoLogDto(String codice) {
		CatalogoLogDto cat = new CatalogoLogDto();
		cat.setCodice((codice == null ? Constants.AUTH_ER_000 : codice));
		CatalogoLogDto d = Utils.getFirstRecord(catalogoLog.findByCodice(cat));
		if (d == null) {
			cat.setCodice(Constants.AUTH_ER_000);
			d = Utils.getFirstRecord(catalogoLog.findByCodice(cat));
		}

		return d;
	}

	private MessaggiErroreDto newMessaggiErroreDto(String codErr, String descrizioneErr) {
		Long idmsg = (Long) request.getAttribute(Constants.IDMSG);
		MessaggiErroreDto msgerr = new MessaggiErroreDto();
		MessaggiDto m = new MessaggiDto();

		m.setId(idmsg);
		msgerr.setMessaggiDto(m);

		CatalogoLogDto data = getCatalogoLogDto(codErr);

		msgerr.setCatalogoLogDto(data);
		msgerr.setCodiceErrore((codErr == null ? Constants.AUTH_ER_000 : codErr));
		msgerr.setDescrizioneErrore(descrizioneErr);
		return msgerr;
	}

	private void tracciaMessaggiErrore() {
		final String METHOD_NAME = "tracciaRisposteNegative";
		try {
			if (res != null && res instanceof Response && res.getEntity() instanceof Errore) {
				// si sta restituendo un errore
				Errore err = (Errore) res.getEntity();
				messErrori.insert(newMessaggiErroreDto(err.getCodice(), err.getDescrizione()));
			}

		} catch (Exception e) {
			log.error(METHOD_NAME, e.getMessage(), e);
		}

	}

	/**
	 * Implementa l'esecuzione della bussiness logic del servizio. E' demandato
	 * all'implementazione di questo metodo l'impostazione della response del
	 * servizio.
	 * 
	 */
	protected abstract Response execute();

	/**
	 * Controlla che il parametro obj sia valorizzato; diversamente solleva una
	 * {@link RESTException} con Status.BAD_REQUEST e con il messaggio passato come
	 * parametro.
	 * 
	 * @param obj
	 * @param message
	 */
	protected void checkNotNull(Object obj, String message) {
		checkCondition(obj != null, Status.BAD_REQUEST, message);
//		checkNotNull(obj, ErroreBuilder.from(Status.BAD_REQUEST).exception());
	}

	/**
	 * Controlla che il parametro obj sia valorizzato; diversamente solleva
	 * l'eccezione passata come parametro.
	 * 
	 * @param obj
	 * @param re
	 */
	protected void checkNotNull(Object obj, RESTException re) {
		checkCondition(obj != null, re);
	}

	/**
	 * Controlla che il parametro str sia valorizzato; diversamente solleva una
	 * {@link RESTException} con Status.BAD_REQUEST e con il messaggio passato come
	 * parametro.
	 * 
	 * @param obj
	 * @param re
	 */
	protected void checkNotBlank(String str, String message) {
		checkCondition(StringUtils.isNotBlank(str), Status.BAD_REQUEST, message);
//		checkNotBlank(str, ErroreBuilder.from(Status.BAD_REQUEST).exception());
	}

	/**
	 * Controlla che il parametro str sia valorizzato; diversamente solleva
	 * l'eccezione passata come parametro.
	 * 
	 * @param obj
	 * @param re
	 */
	protected void checkNotBlank(String str, RESTException re) {
		checkCondition(StringUtils.isNotBlank(str), re);
	}

	/**
	 * Controlla che la condizione isOk sia uguale a <code>true</code>; diversamente
	 * solleva l'eccezione passata come parametro.
	 * 
	 * @param isOk
	 * @param re
	 */
	protected void checkCondition(boolean isOk, RESTException re) {
		if (!isOk) {
			throw re;
		}
	}

	/**
	 * Controlla che la condizione isOk sia uguale a <code>true</code>; diversamente
	 * solleva l'eccezione passata come parametro.
	 * 
	 * @param isOk
	 * @param re
	 */
	protected void checkCondition(boolean isOk, Response.Status status, String message) {
		if (!isOk) {
//			ErroreDto erroreDto = erroreService.findByCodice(message);
//			throw ErroreBuilder.from(Status.BAD_REQUEST)
//			.codice(erroreDto.getIdErrore()).descrizione(erroreDto.getDescrizioneErrore()).exception();
		}
	}

	/**
	 * Controlla che la condizione isOk sia uguale a <code>true</code>; diversamente
	 * solleva una {@link RESTException} con Status.BAD_REQUEST e con il messaggio
	 * passato come parametro.
	 * 
	 * @param isOk
	 * @param re
	 */
	protected void checkCondition(boolean isOk, String message) {
		checkCondition(isOk, ErroreBuilder.from(Status.BAD_REQUEST).exception());
	}

}
