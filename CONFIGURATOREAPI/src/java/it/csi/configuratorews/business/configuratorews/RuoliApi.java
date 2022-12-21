/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.configuratorews;

import io.swagger.annotations.ApiParam;
import it.csi.configuratorews.dto.configuratorews.Errore;
import it.csi.configuratorews.dto.configuratorews.ModelRuolo;
import it.csi.configuratorews.dto.configuratorews.ModelUtenteBase;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/ruoli")

@io.swagger.annotations.Api(description = "the ruoli API")

public interface RuoliApi {

	@GET
	@Path("/{codice_ruolo}/utenti")
	@Produces({ "application/json" })
	@io.swagger.annotations.ApiOperation(value = "getUtentiRuolo", notes = "restituisce la lista degli utenti attivi presenti nel sistema Configuratore, eventualmente filtrati per ruolo ", response = ModelUtenteBase.class, responseContainer = "List", authorizations = {
			@io.swagger.annotations.Authorization(value = "basicAuth") }, tags = { "Configuratore API", })
	@io.swagger.annotations.ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo", response = ModelUtenteBase.class, responseContainer = "List"),

			@io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 404, message = "not found", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 502, message = "Service invocation error", response = Errore.class) })
	public Response ruoliCodiceRuoloUtentiGet(
			@ApiParam(value = "Contiene il codice fiscale dell'utente loggato ottenuto tramite shibboleth", required = true) @HeaderParam("Shib-Identita-CodiceFiscale") String shibIdentitaCodiceFiscale,
			@ApiParam(value = "Contiene un id univoco (`uuid v4`) della chiamata HTTP che viene generato dal front-end, viene propagato agli strati successivi e viene restituito al front-end assieme alla response, permettendo cos� di tracciare l'intero ciclo di vita della richiesta", required = true) @HeaderParam("X-Request-Id") String xRequestId,
			@ApiParam(value = "Contiene l'elenco degli ip che hanno gestito la richiesta. Utile per i log e l'audit. Il formato della stringa �  ``` <ip-front-end> | <ip-primo-server> | ... | <ip-ultimo-server> ``` Es. `10.10.10.10 | 10.38.45.68 | 10.128.69.244` ", required = true) @HeaderParam("X-Forwarded-For") String xForwardedFor,
			@ApiParam(value = "Contiene il codice del servizio chiamante ", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio,
			@PathParam("codice_ruolo") String codiceRuolo, @Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest request);

	@GET
	@Produces({ "application/json" })
	@io.swagger.annotations.ApiOperation(value = "getRuoli", notes = "restituisce la lista dei ruoli attivi presenti nel sistema Configuratore ", response = ModelRuolo.class, responseContainer = "List", authorizations = {
			@io.swagger.annotations.Authorization(value = "basicAuth") }, tags = { "Configuratore API", })
	@io.swagger.annotations.ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo", response = ModelRuolo.class, responseContainer = "List"),

			@io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 404, message = "not found", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 502, message = "Service invocation error", response = Errore.class) })
	public Response ruoliGet(
			@ApiParam(value = "Contiene il codice fiscale dell'utente loggato ottenuto tramite shibboleth", required = true) @HeaderParam("Shib-Identita-CodiceFiscale") String shibIdentitaCodiceFiscale,
			@ApiParam(value = "Contiene un id univoco (`uuid v4`) della chiamata HTTP che viene generato dal front-end, viene propagato agli strati successivi e viene restituito al front-end assieme alla response, permettendo cos� di tracciare l'intero ciclo di vita della richiesta", required = true) @HeaderParam("X-Request-Id") String xRequestId,
			@ApiParam(value = "Contiene l'elenco degli ip che hanno gestito la richiesta. Utile per i log e l'audit. Il formato della stringa �  ``` <ip-front-end> | <ip-primo-server> | ... | <ip-ultimo-server> ``` Es. `10.10.10.10 | 10.38.45.68 | 10.128.69.244` ", required = true) @HeaderParam("X-Forwarded-For") String xForwardedFor,
			@ApiParam(value = "Contiene il codice del servizio chiamante ", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest request, @QueryParam("limit") Integer limit,
			@QueryParam("offset") Integer offset);
}
