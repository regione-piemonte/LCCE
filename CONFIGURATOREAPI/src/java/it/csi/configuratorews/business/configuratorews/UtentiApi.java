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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import io.swagger.annotations.ApiParam;
import it.csi.configuratorews.dto.configuratorews.Applicazione;
import it.csi.configuratorews.dto.configuratorews.AutorizzazioneUtente;
import it.csi.configuratorews.dto.configuratorews.CollocazioneUtente;
import it.csi.configuratorews.dto.configuratorews.Errore;
import it.csi.configuratorews.dto.configuratorews.UtenteApplicazioni;
import it.csi.configuratorews.interfacews.client.ruoliUtente.Ruolo;

@Path("/utenti")
@io.swagger.annotations.Api(description = "the utenti API")
public interface UtentiApi {

	@GET
	@Produces({ "application/json" })
	@io.swagger.annotations.ApiOperation(value = "", notes = "restituisce la lista di tutti gli utenti attivi e dei relativi profili/funzionalita dato un ruolo e una collocazione", response = UtenteApplicazioni.class, responseContainer = "List", authorizations = {
			@io.swagger.annotations.Authorization(value = "basicAuth") }, tags = { "Configuratore API", })
	@io.swagger.annotations.ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo", response = UtenteApplicazioni.class, responseContainer = "List"),

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
			@NotNull @QueryParam("codice_azienda") String codiceAzienda);	//SER-19
	
	@GET
	@Path("/{codice_fiscale}/abilitazioni")
	@Produces({ "application/json" })
	@io.swagger.annotations.ApiOperation(value = "", notes = "restituisce la lista di tutti gli utenti attivi e dei relativi profili/funzionalita dato un ruolo e una collocazione", response = Applicazione.class, responseContainer = "List", authorizations = {
			@io.swagger.annotations.Authorization(value = "basicAuth") }, tags = { "Configuratore API", })
	@io.swagger.annotations.ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo", response = Applicazione.class, responseContainer = "List"),

			@io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 404, message = "not found", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 502, message = "Service invocation error", response = Errore.class) })
	public Response abilitazioniGet(
			@ApiParam(value = "Contiene il codice fiscale dell'utente loggato ottenuto tramite shibboleth", required = true) @HeaderParam("Shib-Identita-CodiceFiscale") String shibIdentitaCodiceFiscale,
			@ApiParam(value = "Contiene un id univoco (`uuid v4`) della chiamata HTTP che viene generato dal front-end, viene propagato agli strati successivi e viene restituito al front-end assieme alla response, permettendo di tracciare l'intero ciclo di vita della richiesta", required = true) @HeaderParam("X-Request-Id") String xRequestId,
			@ApiParam(value = "Contiene l'elenco degli ip che hanno gestito la richiesta, utile per i log e l'audit. Il formato della stringa è ``` <ip-front-end> | <ip-primo-server> | ... | <ip-ultimo-server> ", required = true) @HeaderParam("X-Forwarded-For") String xForwardedFor,
			@ApiParam(value = "Contiene il codice del servizio chiamante ", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest request,
			@NotNull @QueryParam("ruolo_codice") String ruolo, @QueryParam("collocazione_codice") String collocazione,
			@NotNull @QueryParam("limit") String limit, @NotNull @QueryParam("offset") String offset,
			@NotNull @QueryParam("codice_azienda") String codiceAzienda,
			@PathParam("codice_fiscale") String codiceFiscale);		//SER-20
	
	@GET
	@Path("/{codice_fiscale}/collocazioni")
	@Produces({ "application/json" })
	@io.swagger.annotations.ApiOperation(value = "collocazioni", notes = "restituisce la lista di tutte le collocazioni associate ad abilitazioni attive per l'utente ricevuto in input. ", response = CollocazioneUtente.class, responseContainer = "List", authorizations = {
			@io.swagger.annotations.Authorization(value = "basicAuth") }, tags = { "Configuratore API", })
	@io.swagger.annotations.ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo", response = CollocazioneUtente.class, responseContainer = "List"),

			@io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 404, message = "not found", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 502, message = "Service invocation error", response = Errore.class) })
	public Response collocazioniGet(
			@ApiParam(value = "Contiene il codice fiscale dell'utente loggato ottenuto tramite shibboleth", required = true) @HeaderParam("Shib-Identita-CodiceFiscale") String shibIdentitaCodiceFiscale,
			@ApiParam(value = "Contiene un id univoco (`uuid v4`) della chiamata HTTP che viene generato dal front-end, viene propagato agli strati successivi e viene restituito al front-end assieme alla response, permettendo cos� di tracciare l'intero ciclo di vita della richiesta", required = true) @HeaderParam("X-Request-Id") String xRequestId,
			@ApiParam(value = "Contiene l'elenco degli ip che hanno gestito la richiesta. Utile per i log e l'audit. Il formato della stringa �  ``` <ip-front-end> | <ip-primo-server> | ... | <ip-ultimo-server> ``` Es. `10.10.10.10 | 10.38.45.68 | 10.128.69.244` ", required = true) @HeaderParam("X-Forwarded-For") String xForwardedFor,
			@ApiParam(value = "Contiene il codice del servizio chiamante ", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio,
			@PathParam("codice_fiscale") String codiceFiscale, @Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest request,@NotNull @QueryParam("ruolo_codice") String ruoloCodice,
			@NotNull @QueryParam("offset") String offset,
			@NotNull @QueryParam("limit") String limit, @NotNull @QueryParam("codice_azienda") String codiceAzienda);	//SER-15

	
	@GET
	@Path("/{codice_fiscale}/autorizzazioni")
	@Produces({ "application/json" })
	@io.swagger.annotations.ApiOperation(value = "autorizzazioni", notes = "restituisce l'informazione se l'utente e' abilitato all'applicativo in base ai parametri passati.  ", response = AutorizzazioneUtente.class, responseContainer = "List",
	authorizations = {
			@io.swagger.annotations.Authorization(value = "basicAuth") }, tags = { "Configuratore API", })
	@io.swagger.annotations.ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo", response = AutorizzazioneUtente.class, responseContainer = "List"),

			@io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 404, message = "not found", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 502, message = "Service invocation error", response = Errore.class) })
	public Response autorizzazioneUtenteGet(
			@ApiParam(value = "Contiene il codice fiscale dell'utente loggato ottenuto tramite shibboleth", required = true) @HeaderParam("Shib-Identita-CodiceFiscale") String shibIdentitaCodiceFiscale,
			@ApiParam(value = "Contiene un id univoco (`uuid v4`) della chiamata HTTP che viene generato dal front-end, viene propagato agli strati successivi e viene restituito al front-end assieme alla response, permettendo cos� di tracciare l'intero ciclo di vita della richiesta", required = true) @HeaderParam("X-Request-Id") String xRequestId,
			@ApiParam(value = "Contiene l'elenco degli ip che hanno gestito la richiesta. Utile per i log e l'audit. Il formato della stringa �  ``` <ip-front-end> | <ip-primo-server> | ... | <ip-ultimo-server> ``` Es. `10.10.10.10 | 10.38.45.68 | 10.128.69.244` ", required = true) @HeaderParam("X-Forwarded-For") String xForwardedFor,
			@ApiParam(value = "Contiene il codice del servizio chiamante ", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio,
			@PathParam("codice_fiscale") String codiceFiscale, @Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest request,
			@NotNull @QueryParam("ruolo_codice") String ruoloCodice,
			@NotNull @QueryParam("applicazione_codice") String applicazioneCodice,
			@NotNull @QueryParam("collocazione_codice") String collocazioneCodice,
			@QueryParam("profilo_codice") String profiloCodice,
			@QueryParam("funzionalita_codice") String funzionalitaCodice,@NotNull @QueryParam("codice_azienda") String codiceAzienda);  // SER-14
	
	
	@GET
	@Path("/{codice_fiscale}/ruoli")
	@Produces({ "application/json" })
	@io.swagger.annotations.ApiOperation(value = "autorizzazioni", notes = "restituisce i ruoli per l'utente  ", response = Ruolo.class, responseContainer = "List",
	authorizations = {
			@io.swagger.annotations.Authorization(value = "basicAuth") }, tags = { "Configuratore API", })
	@io.swagger.annotations.ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo", response = Ruolo.class, responseContainer = "List"),

			@io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 404, message = "not found", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 502, message = "Service invocation error", response = Errore.class) })
	public Response ruoliUtentiGet(
			@ApiParam(value = "Contiene il codice fiscale dell'utente loggato ottenuto tramite shibboleth", required = true) @HeaderParam("Shib-Identita-CodiceFiscale") String shibIdentitaCodiceFiscale,
			@ApiParam(value = "Contiene un id univoco (`uuid v4`) della chiamata HTTP che viene generato dal front-end, viene propagato agli strati successivi e viene restituito al front-end assieme alla response, permettendo cos� di tracciare l'intero ciclo di vita della richiesta", required = true) @HeaderParam("X-Request-Id") String xRequestId,
			@ApiParam(value = "Contiene l'elenco degli ip che hanno gestito la richiesta. Utile per i log e l'audit. Il formato della stringa �  ``` <ip-front-end> | <ip-primo-server> | ... | <ip-ultimo-server> ``` Es. `10.10.10.10 | 10.38.45.68 | 10.128.69.244` ", required = true) @HeaderParam("X-Forwarded-For") String xForwardedFor,
			@ApiParam(value = "Contiene il codice del servizio chiamante ", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio,
			@PathParam("codice_fiscale") String codiceFiscale, @Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest request,
			@NotNull @QueryParam("offset") Integer offset,
			@NotNull @QueryParam("limit") Integer limit,
			@NotNull @QueryParam("codice_azienda") String codiceAzienda);			// SER-24
	
	@GET
	@Path("/{codice_fiscale}/azienda")
	@Produces({ "application/json" })
	@io.swagger.annotations.ApiOperation(value = "codiceAzienda", notes = "restituisce il codice azienda della collocazione se esiste un'abilitazione attiva dato CF, ruolo, collocazione e applicazione ", response = String.class, 
	authorizations = {
			@io.swagger.annotations.Authorization(value = "basicAuth") }, tags = { "Configuratore API", })
	@io.swagger.annotations.ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo", response = String.class),

			@io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 404, message = "not found", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 502, message = "Service invocation error", response = Errore.class) })
	public Response aziendaUtenteGet(
			@ApiParam(value = "Contiene il codice fiscale dell'utente loggato ottenuto tramite shibboleth", required = true) @HeaderParam("Shib-Identita-CodiceFiscale") String shibIdentitaCodiceFiscale,
			@ApiParam(value = "Contiene un id univoco (`uuid v4`) della chiamata HTTP che viene generato dal front-end, viene propagato agli strati successivi e viene restituito al front-end assieme alla response, permettendo cos� di tracciare l'intero ciclo di vita della richiesta", required = true) @HeaderParam("X-Request-Id") String xRequestId,
			@ApiParam(value = "Contiene l'elenco degli ip che hanno gestito la richiesta. Utile per i log e l'audit. Il formato della stringa �  ``` <ip-front-end> | <ip-primo-server> | ... | <ip-ultimo-server> ``` Es. `10.10.10.10 | 10.38.45.68 | 10.128.69.244` ", required = true) @HeaderParam("X-Forwarded-For") String xForwardedFor,
			@ApiParam(value = "Contiene il codice del servizio chiamante ", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio,
			@PathParam("codice_fiscale") String codiceFiscale, @Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest request,
			@NotNull @QueryParam("collocazione_codice") String codiceCollocazione,
			@NotNull @QueryParam("ruolo_codice") String codiceRuolo,
			@NotNull @QueryParam("applicazione_codice") String codiceApplicazione);			// SER-27
	
//	se valorizzato funzionalita_codice deve essere valorizzato il profilo codice
}
