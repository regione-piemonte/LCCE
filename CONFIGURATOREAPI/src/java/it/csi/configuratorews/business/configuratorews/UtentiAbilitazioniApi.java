/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.configuratorews;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import io.swagger.annotations.ApiParam;
import it.csi.configuratorews.dto.configuratorews.Errore;
import it.csi.configuratorews.dto.configuratorews.UtenteProfiloFunzionalita;

@Path("/utenti_abilitazioni")
@io.swagger.annotations.Api(description = "the utenti e abilitazioni API")
public interface UtentiAbilitazioniApi {
	@GET
	@Produces({ "application/json" })
	@io.swagger.annotations.ApiOperation(value = "", notes = "restituisce la lista di tutti gli utenti attivi e dei relativi profili/funzionalita dato un ruolo e una collocazione", response = UtenteProfiloFunzionalita.class, responseContainer = "List", authorizations = {
			@io.swagger.annotations.Authorization(value = "basicAuth") }, tags = { "Configuratore API", })
	@io.swagger.annotations.ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo", response = UtenteProfiloFunzionalita.class, responseContainer = "List"),

			@io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 404, message = "not found", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 502, message = "Service invocation error", response = Errore.class) })
	public Response utentiGet(
			@ApiParam(value = "Contiene il codice fiscale dell'utente loggato ottenuto tramite shibboleth", required = true) @HeaderParam("Shib-Identita-CodiceFiscale") String shibIdentitaCodiceFiscale,
			@ApiParam(value = "Contiene un id univoco (`uuid v4`) della chiamata HTTP che viene generato dal front-end, viene propagato agli strati successivi e viene restituito al front-end assieme alla response, permettendo di tracciare l'intero ciclo di vita della richiesta", required = true) @HeaderParam("X-Request-Id") String xRequestId,
			@ApiParam(value = "Contiene l'elenco degli ip che hanno gestito la richiesta, utile per i log e l'audit. Il formato della stringa è ``` <ip-front-end> | <ip-primo-server> | ... | <ip-ultimo-server> ", required = true) @HeaderParam("X-Forwarded-For") String xForwardedFor,
			@ApiParam(value = "Contiene il codice del servizio chiamante ", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest request,
			@NotNull @QueryParam("ruolo_codice") String ruolo, @QueryParam("collocazione_codice") String collocazione,
			@NotNull @QueryParam("limit") String limit, @NotNull @QueryParam("offset") String offset,
			@NotNull @QueryParam("codice_azienda") String codiceAzienda); // SER-13
}
