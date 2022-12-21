/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.configuratorews;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.annotations.ApiParam;
import it.csi.configuratorews.dto.configuratorews.Applicazione;
import it.csi.configuratorews.dto.configuratorews.Errore;
import it.csi.configuratorews.dto.configuratorews.InserimentoProfiloFunzionalitaBody;
import it.csi.configuratorews.dto.configuratorews.ListaPermessi;
import it.csi.configuratorews.dto.configuratorews.ModelCollocazione;
import it.csi.configuratorews.dto.configuratorews.ModelUtente;
import it.csi.configuratorews.dto.configuratorews.ProfiloFunzionalita;
import it.csi.configuratorews.dto.configuratorews.ProfiloFunzionalitaBody;
import it.csi.configuratorews.dto.configuratorews.ResponseWrite;

@Path("/applicazioni")

@io.swagger.annotations.Api(description = "the applicazioni API")

public interface ApplicazioniApi {

	@GET
	@Produces({ "application/json" })
	@io.swagger.annotations.ApiOperation(value = "", notes = "Restituisce la lista delle applicazioni presenti nel sistema Configuratore ", response = Applicazione.class, responseContainer = "List", authorizations = {
			@io.swagger.annotations.Authorization(value = "basicAuth") }, tags = { "Configuratore API", })
	@io.swagger.annotations.ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo", response = Applicazione.class, responseContainer = "List"),

			@io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 404, message = "not found", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 502, message = "Service invocation error", response = Errore.class) })
	public Response applicazioniGet(
			@ApiParam(value = "Contiene il codice fiscale dell'utente loggato ottenuto tramite shibboleth", required = true) @HeaderParam("Shib-Identita-CodiceFiscale") String shibIdentitaCodiceFiscale,
			@ApiParam(value = "Contiene un id univoco (`uuid v4`) della chiamata HTTP che viene generato dal front-end, viene propagato agli strati successivi e viene restituito al front-end assieme alla response, permettendo di tracciare l'intero ciclo di vita della richiesta", required = true) @HeaderParam("X-Request-Id") String xRequestId,
			@ApiParam(value = "Contiene l'elenco degli ip che hanno gestito la richiesta, utile per i log e l'audit. Il formato della stringa è ``` <ip-front-end> | <ip-primo-server> | ... | <ip-ultimo-server> ", required = true) @HeaderParam("X-Forwarded-For") String xForwardedFor,
			@ApiParam(value = "Contiene il codice del servizio chiamante ", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest request,
			@NotNull @QueryParam("codice_azienda") String codiceAzienda,
			@QueryParam("limit") String limit, @QueryParam("offset") String offset);	// SER-10

	@GET
	@Path("/{codice_applicazione}/profili_funzionalita")
	@Produces({ "application/json" })
	@io.swagger.annotations.ApiOperation(value = "profili_funzionalita", notes = "restituisce la lista dei profili e delle funzionalita dato un applicativo", response = ProfiloFunzionalita.class, responseContainer = "List", authorizations = {
			@io.swagger.annotations.Authorization(value = "basicAuth") }, tags = { "Configuratore API", })
	@io.swagger.annotations.ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo", response = ProfiloFunzionalita.class, responseContainer = "List"),

			@io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 404, message = "not found", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 502, message = "Service invocation error", response = Errore.class) })
	public Response getProfiliFunzionalita(
			@ApiParam(value = "Contiene il codice fiscale dell'utente loggato ottenuto tramite shibboleth", required = true) @HeaderParam("Shib-Identita-CodiceFiscale") String shibIdentitaCodiceFiscale,
			@ApiParam(value = "Contiene un id univoco (`uuid v4`) della chiamata HTTP che viene generato dal front-end, viene propagato agli strati successivi e viene restituito al front-end assieme alla response, permettendo cos� di tracciare l'intero ciclo di vita della richiesta", required = true) @HeaderParam("X-Request-Id") String xRequestId,
			@ApiParam(value = "Contiene l'elenco degli ip che hanno gestito la richiesta. Utile per i log e l'audit. Il formato della stringa �  ``` <ip-front-end> | <ip-primo-server> | ... | <ip-ultimo-server> ``` Es. `10.10.10.10 | 10.38.45.68 | 10.128.69.244` ", required = true) @HeaderParam("X-Forwarded-For") String xForwardedFor,
			@ApiParam(value = "Contiene il codice del servizio chiamante ", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio,
			@PathParam("codice_applicazione") String codiceApplicazione, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,
			@Context HttpServletRequest request, @QueryParam("limit") String limit, @QueryParam("offset") String offset,
			@NotNull @QueryParam("codice_azienda") String codiceAzienda);	// SER-11

	@PUT
	@Path("/{codice_applicazione}/profili_funzionalita/{codice}")
	@Produces({ "application/json" })
	@io.swagger.annotations.ApiOperation(value = "profili_funzionalita", notes = "consente di aggiornare le informazioni sui profili e funzionalita", response = ResponseWrite.class, authorizations = {
			@io.swagger.annotations.Authorization(value = "basicAuth") }, tags = { "Configuratore API", })
	@io.swagger.annotations.ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo", response = ResponseWrite.class),

			@io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 404, message = "not found", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 502, message = "Service invocation error", response = Errore.class) })
	public Response modificaProfiliFunzionalita(
			@ApiParam(value = "Contiene il codice fiscale dell'utente loggato ottenuto tramite shibboleth", required = true) @HeaderParam("Shib-Identita-CodiceFiscale") String shibIdentitaCodiceFiscale,
			@ApiParam(value = "Contiene un id univoco (`uuid v4`) della chiamata HTTP che viene generato dal front-end, viene propagato agli strati successivi e viene restituito al front-end assieme alla response, permettendo cos� di tracciare l'intero ciclo di vita della richiesta", required = true) @HeaderParam("X-Request-Id") String xRequestId,
			@ApiParam(value = "Contiene l'elenco degli ip che hanno gestito la richiesta. Utile per i log e l'audit. Il formato della stringa   ``` <ip-front-end> | <ip-primo-server> | ... | <ip-ultimo-server> ``` Es. `10.10.10.10 | 10.38.45.68 | 10.128.69.244` ", required = true) @HeaderParam("X-Forwarded-For") String xForwardedFor,
			@ApiParam(value = "Contiene il codice del servizio chiamante ", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio,
			@PathParam("codice_applicazione") String codiceApplicazione, @PathParam("codice") String codiceFunzionalita,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest request,
			@RequestBody ProfiloFunzionalitaBody input,@ NotNull @QueryParam("codice_azienda") String codiceAzienda);	// SER-12
	
	@DELETE
	@Path("/{codice_applicazione}/funzionalita/{codice}")
	@Produces({ "application/json" })
	@io.swagger.annotations.ApiOperation(value = "profili_funzionalita", notes = "consente di cancellare una funzionalita", response = ResponseWrite.class, authorizations = {
			@io.swagger.annotations.Authorization(value = "basicAuth") }, tags = { "Configuratore API", })
	@io.swagger.annotations.ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo", response = ResponseWrite.class),
			
			@io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),
			
			@io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Errore.class),
			
			@io.swagger.annotations.ApiResponse(code = 404, message = "not found", response = Errore.class),
			
			@io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class),
			
			@io.swagger.annotations.ApiResponse(code = 502, message = "Service invocation error", response = Errore.class) })
	public Response cancellaFunzionalita(
			@ApiParam(value = "Contiene il codice fiscale dell'utente loggato ottenuto tramite shibboleth", required = true) @HeaderParam("Shib-Identita-CodiceFiscale") String shibIdentitaCodiceFiscale,
			@ApiParam(value = "Contiene un id univoco (`uuid v4`) della chiamata HTTP che viene generato dal front-end, viene propagato agli strati successivi e viene restituito al front-end assieme alla response, permettendo cos� di tracciare l'intero ciclo di vita della richiesta", required = true) @HeaderParam("X-Request-Id") String xRequestId,
			@ApiParam(value = "Contiene l'elenco degli ip che hanno gestito la richiesta. Utile per i log e l'audit. Il formato della stringa   ``` <ip-front-end> | <ip-primo-server> | ... | <ip-ultimo-server> ``` Es. `10.10.10.10 | 10.38.45.68 | 10.128.69.244` ", required = true) @HeaderParam("X-Forwarded-For") String xForwardedFor,
			@ApiParam(value = "Contiene il codice del servizio chiamante ", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio,
			@PathParam("codice_applicazione") String codiceApplicazione, @PathParam("codice") String codiceFunzionalita,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest request,
			@NotNull @QueryParam("codice_azienda") String codiceAzienda);		//SER-22
	
	@PUT
	@Path("/{codice_applicazione}/funzionalita/{codice}/tipologia_dato_permesso")
	@Produces({ "application/json" })
	@io.swagger.annotations.ApiOperation(value = "profili_funzionalita", notes = "consente di aggiornare le informazioni sui permessi delle funzionalita", response = ResponseWrite.class, authorizations = {
			@io.swagger.annotations.Authorization(value = "basicAuth") }, tags = { "Configuratore API", })
	@io.swagger.annotations.ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo", response = ResponseWrite.class),
			
			@io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),
			
			@io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Errore.class),
			
			@io.swagger.annotations.ApiResponse(code = 404, message = "not found", response = Errore.class),
			
			@io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class),
			
			@io.swagger.annotations.ApiResponse(code = 502, message = "Service invocation error", response = Errore.class) })
	public Response modificaPermesso(
			@ApiParam(value = "Contiene il codice fiscale dell'utente loggato ottenuto tramite shibboleth", required = true) @HeaderParam("Shib-Identita-CodiceFiscale") String shibIdentitaCodiceFiscale,
			@ApiParam(value = "Contiene un id univoco (`uuid v4`) della chiamata HTTP che viene generato dal front-end, viene propagato agli strati successivi e viene restituito al front-end assieme alla response, permettendo cos� di tracciare l'intero ciclo di vita della richiesta", required = true) @HeaderParam("X-Request-Id") String xRequestId,
			@ApiParam(value = "Contiene l'elenco degli ip che hanno gestito la richiesta. Utile per i log e l'audit. Il formato della stringa   ``` <ip-front-end> | <ip-primo-server> | ... | <ip-ultimo-server> ``` Es. `10.10.10.10 | 10.38.45.68 | 10.128.69.244` ", required = true) @HeaderParam("X-Forwarded-For") String xForwardedFor,
			@ApiParam(value = "Contiene il codice del servizio chiamante ", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio,
			@PathParam("codice_applicazione") String codiceApplicazione, @PathParam("codice") String codiceFunzionalita,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest request,
			@NotNull @QueryParam("codice_azienda") String codiceAzienda, @NotNull @RequestBody ListaPermessi input);	// SER-23
	
	
	@DELETE
	@Path("/{codice_applicazione}/profili/{codice}")
	@Produces({ "application/json" })
	@io.swagger.annotations.ApiOperation(value = "profili_funzionalita", notes = "consente di cancellare i profili", response = ResponseWrite.class, authorizations = {
			@io.swagger.annotations.Authorization(value = "basicAuth") }, tags = { "Configuratore API", })
	@io.swagger.annotations.ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo", response = ResponseWrite.class),
			
			@io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),
			
			@io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Errore.class),
			
			@io.swagger.annotations.ApiResponse(code = 404, message = "not found", response = Errore.class),
			
			@io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class),
			
			@io.swagger.annotations.ApiResponse(code = 502, message = "Service invocation error", response = Errore.class) })
	public Response cancellaProfilo(
			@ApiParam(value = "Contiene il codice fiscale dell'utente loggato ottenuto tramite shibboleth", required = true) @HeaderParam("Shib-Identita-CodiceFiscale") String shibIdentitaCodiceFiscale,
			@ApiParam(value = "Contiene un id univoco (`uuid v4`) della chiamata HTTP che viene generato dal front-end, viene propagato agli strati successivi e viene restituito al front-end assieme alla response, permettendo cos� di tracciare l'intero ciclo di vita della richiesta", required = true) @HeaderParam("X-Request-Id") String xRequestId,
			@ApiParam(value = "Contiene l'elenco degli ip che hanno gestito la richiesta. Utile per i log e l'audit. Il formato della stringa   ``` <ip-front-end> | <ip-primo-server> | ... | <ip-ultimo-server> ``` Es. `10.10.10.10 | 10.38.45.68 | 10.128.69.244` ", required = true) @HeaderParam("X-Forwarded-For") String xForwardedFor,
			@ApiParam(value = "Contiene il codice del servizio chiamante ", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio,
			@PathParam("codice_applicazione") String codiceApplicazione, @PathParam("codice") String codiceFunzionalita,
			@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest request,
			@NotNull @QueryParam("codice_azienda") String codiceAzienda);    //SER-21

	@POST
	@Path("/{codice_applicazione}/profili_funzionalita")
	@Produces({ "application/json" })
	@Consumes({ "application/json" })
	@io.swagger.annotations.ApiOperation(value = "profili_funzionalita", notes = "consente di aggiornare le informazioni sui profili e funzionalita", 
			response = ResponseWrite.class, authorizations = {
			@io.swagger.annotations.Authorization(value = "basicAuth") }, tags = { "Configuratore API", })
	@io.swagger.annotations.ApiResponses(value = {
			@io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo", response = ResponseWrite.class),

			@io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 404, message = "not found", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class),

			@io.swagger.annotations.ApiResponse(code = 502, message = "Service invocation error", response = Errore.class) })
	public Response inserisciProfiliFunzionalita(
			@ApiParam(value = "Contiene il codice fiscale dell'utente loggato ottenuto tramite shibboleth", required = true) @HeaderParam("Shib-Identita-CodiceFiscale") String shibIdentitaCodiceFiscale,
			@ApiParam(value = "Contiene un id univoco (`uuid v4`) della chiamata HTTP che viene generato dal front-end, viene propagato agli strati successivi e viene restituito al front-end assieme alla response, permettendo cos� di tracciare l'intero ciclo di vita della richiesta", required = true) @HeaderParam("X-Request-Id") String xRequestId,
			@ApiParam(value = "Contiene l'elenco degli ip che hanno gestito la richiesta. Utile per i log e l'audit. Il formato della stringa   ``` <ip-front-end> | <ip-primo-server> | ... | <ip-ultimo-server> ``` Es. `10.10.10.10 | 10.38.45.68 | 10.128.69.244` ", required = true) @HeaderParam("X-Forwarded-For") String xForwardedFor,
			@ApiParam(value = "Contiene il codice del servizio chiamante ", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio,
			@PathParam("codice_applicazione") String codiceApplicazione, @Context SecurityContext securityContext,
			@Context HttpHeaders httpHeaders, @Context HttpServletRequest request, 
			@RequestBody InserimentoProfiloFunzionalitaBody body, 
			@NotNull @QueryParam("codice_azienda") String codiceAzienda,
			@QueryParam("codice_profilo") String codiceProfilo,
			@QueryParam("descrizione_profilo") String descrizioneProfilo,
			@QueryParam("codice_funzionalita") String codiceFunzionalita,
			@QueryParam("descrizione_funzionalita") String descrizioneFunzionalita);	// SER-17

	@GET
	    @Path("/{applicazione}/collocazioni")
	    
	    @Produces({ "application/json" })
	    @io.swagger.annotations.ApiOperation(value = "getCollocazioniSol", notes = "restituisce la lista delle collocazioni (aziende) attive (in base alle date di validit�) in cui � attivo il SOL ricevuto in inpu ", response = ModelCollocazione.class, responseContainer = "List", authorizations = {
	        @io.swagger.annotations.Authorization(value = "basicAuth")
	    }, tags={ "Configuratore API", })
	    @io.swagger.annotations.ApiResponses(value = { 
	        @io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo", response = ModelCollocazione.class, responseContainer = "List"),
	        
	        @io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),
	        
	        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Errore.class),
	        
	        @io.swagger.annotations.ApiResponse(code = 404, message = "not found", response = Errore.class),
	        
	        @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class),
	        
	        @io.swagger.annotations.ApiResponse(code = 502, message = "Service invocation error", response = Errore.class) })
	    public Response applicazioneSolApplicazioneCollocazioniGet(
	    		@ApiParam(value = "Contiene il codice fiscale dell'utente loggato ottenuto tramite shibboleth", required = true) @HeaderParam("Shib-Identita-CodiceFiscale") String shibIdentitaCodiceFiscale, 
	    		@ApiParam(value = "Contiene un id univoco (`uuid v4`) della chiamata HTTP che viene generato dal front-end, viene propagato agli strati successivi e viene restituito al front-end assieme alla response, permettendo cos� di tracciare l'intero ciclo di vita della richiesta", required = true) @HeaderParam("X-Request-Id") String xRequestId, 
	    		@ApiParam(value = "Contiene l'elenco degli ip che hanno gestito la richiesta. Utile per i log e l'audit. Il formato della stringa �  ``` <ip-front-end> | <ip-primo-server> | ... | <ip-ultimo-server> ``` Es. `10.10.10.10 | 10.38.45.68 | 10.128.69.244` ", required = true) @HeaderParam("X-Forwarded-For") String xForwardedFor, 
	    		@ApiParam(value = "Contiene il codice del servizio chiamante ", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio, 
	    		@PathParam("applicazione") String applicazione, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,@Context HttpServletRequest request,
	    		@NotNull @QueryParam("limit") Integer limit, @NotNull @QueryParam("offset") Integer offset,
	    		@NotNull @QueryParam("codice_azienda") String codiceAzienda);
	
	
	@GET
    @Path("/{applicazione}/abilitazioni")
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "getUtentiSOL", notes = "restituisce la lista degli utenti attivi (in base alle date di validazione) di un SOL, eventualmente filtrata per Collocazione (Azienda). ", response = ModelUtente.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "basicAuth")
    }, tags={ "Configuratore API", })
    @io.swagger.annotations.ApiResponses(value = {
        @io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo", response = ModelUtente.class),

        @io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Errore.class),

        @io.swagger.annotations.ApiResponse(code = 404, message = "not found", response = Errore.class),

        @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class),

        @io.swagger.annotations.ApiResponse(code = 502, message = "Service invocation error", response = Errore.class) })
    public Response applicazioneSolApplicazioneUtentiGet(
    		@ApiParam(value = "Contiene il codice fiscale dell'utente loggato ottenuto tramite shibboleth", required = true) @HeaderParam("Shib-Identita-CodiceFiscale") String shibIdentitaCodiceFiscale, 
    		@ApiParam(value = "Contiene un id univoco (`uuid v4`) della chiamata HTTP che viene generato dal front-end, viene propagato agli strati successivi e viene restituito al front-end assieme alla response, permettendo cos� di tracciare l'intero ciclo di vita della richiesta", required = true) @HeaderParam("X-Request-Id") String xRequestId, 
    		@ApiParam(value = "Contiene l'elenco degli ip che hanno gestito la richiesta. Utile per i log e l'audit. Il formato della stringa �  ``` <ip-front-end> | <ip-primo-server> | ... | <ip-ultimo-server> ``` Es. `10.10.10.10 | 10.38.45.68 | 10.128.69.244` ", required = true) @HeaderParam("X-Forwarded-For") String xForwardedFor, 
    		@ApiParam(value = "Contiene il codice del servizio chiamante ", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio, 
    		@PathParam("applicazione") String applicazione, 
    		@QueryParam("codice_azienda") String azienda, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,@Context HttpServletRequest request,
    		@NotNull @QueryParam("limit") Integer limit, @NotNull @QueryParam("offset") Integer offset);
	
	@GET
    @Path("/{applicazione}/funzionalita")
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "getUtentiSOL", notes = "restituisce la lista degli utenti attivi (in base alle date di validazione) di un SOL, eventualmente filtrata per Collocazione (Azienda). ", response = ModelUtente.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "basicAuth")
    }, tags={ "Configuratore API", })
    @io.swagger.annotations.ApiResponses(value = {
        @io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo", response = ModelUtente.class),
        @io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Errore.class),
        @io.swagger.annotations.ApiResponse(code = 404, message = "not found", response = Errore.class),
        @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class),
        @io.swagger.annotations.ApiResponse(code = 502, message = "Service invocation error", response = Errore.class) })
    public Response getApplicazioneFunzionalita(
    		@ApiParam(value = "Contiene il codice fiscale dell'utente loggato ottenuto tramite shibboleth", required = true) @HeaderParam("Shib-Identita-CodiceFiscale") String shibIdentitaCodiceFiscale, 
    		@ApiParam(value = "Contiene un id univoco (`uuid v4`) della chiamata HTTP che viene generato dal front-end, viene propagato agli strati successivi e viene restituito al front-end assieme alla response, permettendo cos� di tracciare l'intero ciclo di vita della richiesta", required = true) @HeaderParam("X-Request-Id") String xRequestId, 
    		@ApiParam(value = "Contiene l'elenco degli ip che hanno gestito la richiesta. Utile per i log e l'audit. Il formato della stringa �  ``` <ip-front-end> | <ip-primo-server> | ... | <ip-ultimo-server> ``` Es. `10.10.10.10 | 10.38.45.68 | 10.128.69.244` ", required = true) @HeaderParam("X-Forwarded-For") String xForwardedFor, 
    		@ApiParam(value = "Contiene il codice del servizio chiamante ", required = true) 
    		@HeaderParam("X-Codice-Servizio") String xCodiceServizio, 
    		@PathParam("applicazione") String applicazione, 
    		@QueryParam("codice_azienda") String azienda, @Context SecurityContext securityContext, 
    		@Context HttpHeaders httpHeaders, @Context HttpServletRequest request,
    		@NotNull @QueryParam("limit") String limit, @NotNull @QueryParam("offset") String offset);		//SER-26

}
