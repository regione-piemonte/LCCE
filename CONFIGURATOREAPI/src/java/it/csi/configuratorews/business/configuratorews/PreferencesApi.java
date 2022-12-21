/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.configuratorews;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.jboss.resteasy.annotations.GZIP;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.annotations.ApiParam;
import it.csi.configuratorews.dto.configuratorews.Errore;
import it.csi.configuratorews.dto.configuratorews.FilterPreferences;
import it.csi.configuratorews.dto.configuratorews.ModelUtenteBase;
import it.csi.configuratorews.dto.configuratorews.Preferences;
import it.csi.configuratorews.dto.configuratorews.TokenConfiguration;

@Path("/preferences")
@io.swagger.annotations.Api(description = "the preference API")
public interface PreferencesApi {

	
	@GET
	@Path("/{utente}/search")
	@Produces({ "application/json" })
	@io.swagger.annotations.ApiOperation(value = "preferences", notes = "restituisce la lista delle preferenze di spedizione usato dal pua presenti nel sistema Configuratore per l'utente in input", response = ModelUtenteBase.class, responseContainer = "List", authorizations = {
			@io.swagger.annotations.Authorization(value = "basicAuth") }, tags = { "Configuratore API", })
	@io.swagger.annotations.ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo", response = ModelUtenteBase.class, responseContainer = "List"),

			@io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 404, message = "not found", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 502, message = "Service invocation error", response = Errore.class) })
	public Response getPreferenzeOperatore(
			@ApiParam(value = "Contiene il codice fiscale dell'utente loggato ottenuto tramite shibboleth", required = true) @HeaderParam("Shib-Identita-CodiceFiscale") String shibIdentitaCodiceFiscale,
			@ApiParam(value = "Contiene un id univoco (`uuid v4`) della chiamata HTTP che viene generato dal front-end, viene propagato agli strati successivi e viene restituito al front-end assieme alla response, permettendo cos� di tracciare l'intero ciclo di vita della richiesta", required = true) @HeaderParam("X-Request-Id") String xRequestId,
			@ApiParam(value = "Contiene l'elenco degli ip che hanno gestito la richiesta. Utile per i log e l'audit. Il formato della stringa �  ``` <ip-front-end> | <ip-primo-server> | ... | <ip-ultimo-server> ``` Es. `10.10.10.10 | 10.38.45.68 | 10.128.69.244` ", required = true) @HeaderParam("X-Forwarded-For") String xForwardedFor,
			@ApiParam(value = "Contiene il codice del servizio chiamante ", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio,
			@PathParam("utente") String utente, @Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest request);
	
	@PUT
	@Path("/{utente}/save")
	@Produces({ "application/json" })
	@io.swagger.annotations.ApiOperation(value = "savePreferences", notes = "salva la lista delle preferenze di spedizione usato dal pua presenti nel sistema Configuratore per l'utente in input", response = ModelUtenteBase.class, responseContainer = "List", authorizations = {
			@io.swagger.annotations.Authorization(value = "basicAuth") }, tags = { "Configuratore API", })
	@io.swagger.annotations.ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo", response = ModelUtenteBase.class, responseContainer = "List"),

			@io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 404, message = "not found", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 502, message = "Service invocation error", response = Errore.class) })
	public Response salvaPreferenzeOperatore(
			@ApiParam(value = "Contiene il codice fiscale dell'utente loggato ottenuto tramite shibboleth", required = true) @HeaderParam("Shib-Identita-CodiceFiscale") String shibIdentitaCodiceFiscale,
			@ApiParam(value = "Contiene un id univoco (`uuid v4`) della chiamata HTTP che viene generato dal front-end, viene propagato agli strati successivi e viene restituito al front-end assieme alla response, permettendo cos� di tracciare l'intero ciclo di vita della richiesta", required = true) @HeaderParam("X-Request-Id") String xRequestId,
			@ApiParam(value = "Contiene l'elenco degli ip che hanno gestito la richiesta. Utile per i log e l'audit. Il formato della stringa �  ``` <ip-front-end> | <ip-primo-server> | ... | <ip-ultimo-server> ``` Es. `10.10.10.10 | 10.38.45.68 | 10.128.69.244` ", required = true) @HeaderParam("X-Forwarded-For") String xForwardedFor,
			@ApiParam(value = "Contiene il codice del servizio chiamante ", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio,
			@PathParam("utente") String utente, @RequestBody Preferences input,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest request);

	
	@POST
	@Path("/utenti")
	@Produces({ "application/json" })
	@GZIP
	@io.swagger.annotations.ApiOperation(value = "preferences", notes = "restituisce la lista degli utenti che il notificatore usera' per spedire email in base a applicativo o ruolo o ruolo e collocazione", response = ModelUtenteBase.class, responseContainer = "List", authorizations = {
			@io.swagger.annotations.Authorization(value = "basicAuth") }, tags = { "Configuratore API", })
	@io.swagger.annotations.ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo", response = ModelUtenteBase.class, responseContainer = "List"),

			@io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 404, message = "not found", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 502, message = "Service invocation error", response = Errore.class) })
	public Response getUtentiNotifiche(
			@ApiParam(value = "Contiene il codice fiscale dell'utente loggato ottenuto tramite shibboleth", required = true) @HeaderParam("Shib-Identita-CodiceFiscale") String shibIdentitaCodiceFiscale,
			@ApiParam(value = "Contiene un id univoco (`uuid v4`) della chiamata HTTP che viene generato dal front-end, viene propagato agli strati successivi e viene restituito al front-end assieme alla response, permettendo cosi' di tracciare l'intero ciclo di vita della richiesta", required = true) @HeaderParam("X-Request-Id") String xRequestId,
			@ApiParam(value = "Contiene l'elenco degli ip che hanno gestito la richiesta. Utile per i log e l'audit. Il formato della stringa  ``` <ip-front-end> | <ip-primo-server> | ... | <ip-ultimo-server> ``` Es. `10.10.10.10 | 10.38.45.68 | 10.128.69.244` ", required = true) @HeaderParam("X-Forwarded-For") String xForwardedFor,
			@ApiParam(value = "Contiene il codice del servizio chiamante ", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio,
			@RequestBody FilterPreferences input, @Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest request);
	
	@PUT
	@Path("/token/configuration")
	@Produces({ "application/json" })
	@GZIP
	@io.swagger.annotations.ApiOperation(value = "preferences", notes = "modifica le configurazioni del fruitore andando a cancellare le preferenze qualora necessario", response = ModelUtenteBase.class, responseContainer = "List", authorizations = {
			@io.swagger.annotations.Authorization(value = "basicAuth") }, tags = { "Configuratore API", })
	@io.swagger.annotations.ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo", response = ModelUtenteBase.class, responseContainer = "List"),

			@io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 404, message = "not found", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 502, message = "Service invocation error", response = Errore.class) })
	public Response saveTokenConfiguration(
			@ApiParam(value = "Contiene il codice fiscale dell'utente loggato ottenuto tramite shibboleth", required = true) @HeaderParam("Shib-Identita-CodiceFiscale") String shibIdentitaCodiceFiscale,
			@ApiParam(value = "Contiene un id univoco (`uuid v4`) della chiamata HTTP che viene generato dal front-end, viene propagato agli strati successivi e viene restituito al front-end assieme alla response, permettendo cosi' di tracciare l'intero ciclo di vita della richiesta", required = true) @HeaderParam("X-Request-Id") String xRequestId,
			@ApiParam(value = "Contiene l'elenco degli ip che hanno gestito la richiesta. Utile per i log e l'audit. Il formato della stringa  ``` <ip-front-end> | <ip-primo-server> | ... | <ip-ultimo-server> ``` Es. `10.10.10.10 | 10.38.45.68 | 10.128.69.244` ", required = true) @HeaderParam("X-Forwarded-For") String xForwardedFor,
			@ApiParam(value = "Contiene il codice del servizio chiamante ", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio,
			@RequestBody TokenConfiguration input, @Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest request);
}
