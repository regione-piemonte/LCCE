/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.configuratorews;

import io.swagger.annotations.ApiParam;
import it.csi.configuratorews.dto.configuratorews.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/login")


@io.swagger.annotations.Api(description = "the proxy API")

public interface LoginApi {
   
    @GET
    @Path("/abilitazioni")
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "getAbilitazioni", notes = "restituisce la lista degli applicativi verticali (SOL) sui quali l�utente collegato con un dato ruolo e una data collocazione ", response = ModelAbilitazione.class, responseContainer = "List", authorizations = {
        @io.swagger.annotations.Authorization(value = "basicAuth")
    }, tags={ "Configuratore Proxy API", })
    @io.swagger.annotations.ApiResponses(value = { 
        @io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo", response = ModelAbilitazione.class, responseContainer = "List"),
        
        @io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),
        
        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Errore.class),
        
        @io.swagger.annotations.ApiResponse(code = 404, message = "not found", response = Errore.class),
        
        @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class),
        
        @io.swagger.annotations.ApiResponse(code = 502, message = "Service invocation error", response = Errore.class) })
    public Response proxyAbilitazioniGet(@ApiParam(value = "Contiene il codice fiscale dell'utente loggato ottenuto tramite shibboleth", required = true) @HeaderParam("Shib-Identita-CodiceFiscale") String shibIdentitaCodiceFiscale, @ApiParam(value = "Contiene un id univoco (`uuid v4`) della chiamata HTTP che viene generato dal front-end, viene propagato agli strati successivi e viene restituito al front-end assieme alla response, permettendo cos� di tracciare l'intero ciclo di vita della richiesta", required = true) @HeaderParam("X-Request-Id") String xRequestId, @ApiParam(value = "Contiene l'elenco degli ip che hanno gestito la richiesta. Utile per i log e l'audit. Il formato della stringa �  ``` <ip-front-end> | <ip-primo-server> | ... | <ip-ultimo-server> ``` Es. `10.10.10.10 | 10.38.45.68 | 10.128.69.244` ", required = true) @HeaderParam("X-Forwarded-For") String xForwardedFor, @ApiParam(value = "Contiene il codice del servizio chiamante ", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio, @NotNull @QueryParam("codice_ruolo") String codiceRuolo, @NotNull @QueryParam("codice_collocazione") String codiceCollocazione, @NotNull @QueryParam("codice_azienda") String codiceAzienda, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest request );

    @GET
    @Path("/applicazioni-abilitate")
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "Applicazioni", notes = "restituisce la lista degli applicativi verticali (SOL) sui quali l'utente collegato con un dato ruolo e una data collocazione ", response = ModelApplicazione.class, responseContainer = "List", authorizations = {
            @io.swagger.annotations.Authorization(value = "basicAuth")
    }, tags={ "Configuratore API", })
    @io.swagger.annotations.ApiResponses(value = {
            @io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo", response = ModelApplicazione.class, responseContainer = "List"),

            @io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

            @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Errore.class),

            @io.swagger.annotations.ApiResponse(code = 404, message = "not found", response = Errore.class),

            @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class),

            @io.swagger.annotations.ApiResponse(code = 502, message = "Service invocation error", response = Errore.class) })
    public Response applicazioniGet(@ApiParam(value = "Contiene il codice fiscale dell'utente loggato ottenuto tramite shibboleth", required = true) @HeaderParam("Shib-Identita-CodiceFiscale") String shibIdentitaCodiceFiscale, @ApiParam(value = "Contiene un id univoco (`uuid v4`) della chiamata HTTP che viene generato dal front-end, viene propagato agli strati successivi e viene restituito al front-end assieme alla response, permettendo cos� di tracciare l'intero ciclo di vita della richiesta", required = true) @HeaderParam("X-Request-Id") String xRequestId, @ApiParam(value = "Contiene l'elenco degli ip che hanno gestito la richiesta. Utile per i log e l'audit. Il formato della stringa �  ``` <ip-front-end> | <ip-primo-server> | ... | <ip-ultimo-server> ``` Es. `10.10.10.10 | 10.38.45.68 | 10.128.69.244` ", required = true) @HeaderParam("X-Forwarded-For") String xForwardedFor, @ApiParam(value = "Contiene il codice del servizio chiamante ", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio, @NotNull @QueryParam("codice_ruolo") String codiceRuolo, @NotNull @QueryParam("codice_collocazione") String codiceCollocazione, @NotNull @QueryParam("codice_azienda") String codiceAzienda, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest request );

    
    
    
    @GET
    @Path("/collocazioni")
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "get collocazioni", notes = "Restituisce la lista delle collocazioni associate all�utente collegato e al ruolo specificato nei parametri di input ", response = ModelCollocazione.class, responseContainer = "List", authorizations = {
        @io.swagger.annotations.Authorization(value = "basicAuth")
    }, tags={ "Configuratore Proxy API", })
    @io.swagger.annotations.ApiResponses(value = {
        @io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo", response = ModelCollocazione.class, responseContainer = "List"),

        @io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Errore.class),

        @io.swagger.annotations.ApiResponse(code = 404, message = "not found", response = Errore.class),

        @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class),

        @io.swagger.annotations.ApiResponse(code = 502, message = "Service invocation error", response = Errore.class) })
    public Response proxyCollocazioniGet(@ApiParam(value = "Contiene il codice fiscale dell'utente loggato ottenuto tramite shibboleth", required = true) @HeaderParam("Shib-Identita-CodiceFiscale") String shibIdentitaCodiceFiscale, @ApiParam(value = "Contiene un id univoco (`uuid v4`) della chiamata HTTP che viene generato dal front-end, viene propagato agli strati successivi e viene restituito al front-end assieme alla response, permettendo cos� di tracciare l'intero ciclo di vita della richiesta", required = true) @HeaderParam("X-Request-Id") String xRequestId, @ApiParam(value = "Contiene l'elenco degli ip che hanno gestito la richiesta. Utile per i log e l'audit. Il formato della stringa �  ``` <ip-front-end> | <ip-primo-server> | ... | <ip-ultimo-server> ``` Es. `10.10.10.10 | 10.38.45.68 | 10.128.69.244` ", required = true) @HeaderParam("X-Forwarded-For") String xForwardedFor, @ApiParam(value = "Contiene il codice del servizio chiamante ", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio, @NotNull @QueryParam("codice_ruolo") String codiceRuolo, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest request);

    @GET
    @Path("/ruoli")
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "GetRuoli", notes = "restituisce la lista dei ruoli presenti nel sistema Configuratore ", response = ModelRuolo.class, responseContainer = "List", authorizations = {
        @io.swagger.annotations.Authorization(value = "basicAuth")
    }, tags={ "Configuratore Proxy API", })
    @io.swagger.annotations.ApiResponses(value = {
        @io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo", response = ModelRuolo.class, responseContainer = "List"),

        @io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Errore.class),

        @io.swagger.annotations.ApiResponse(code = 404, message = "not found", response = Errore.class),

        @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class),

        @io.swagger.annotations.ApiResponse(code = 502, message = "Service invocation error", response = Errore.class) })
    public Response proxyRuoliGet(@ApiParam(value = "Contiene il codice fiscale dell'utente loggato ottenuto tramite shibboleth", required = true) @HeaderParam("Shib-Identita-CodiceFiscale") String shibIdentitaCodiceFiscale, @ApiParam(value = "Contiene un id univoco (`uuid v4`) della chiamata HTTP che viene generato dal front-end, viene propagato agli strati successivi e viene restituito al front-end assieme alla response, permettendo cos� di tracciare l'intero ciclo di vita della richiesta", required = true) @HeaderParam("X-Request-Id") String xRequestId, @ApiParam(value = "Contiene l'elenco degli ip che hanno gestito la richiesta. Utile per i log e l'audit. Il formato della stringa �  ``` <ip-front-end> | <ip-primo-server> | ... | <ip-ultimo-server> ``` Es. `10.10.10.10 | 10.38.45.68 | 10.128.69.244` ", required = true) @HeaderParam("X-Forwarded-For") String xForwardedFor, @ApiParam(value = "Contiene il codice del servizio chiamante ", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest request);

    @POST
    @Path("/authentication2")
    @Consumes({ "application/json" })
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "getAuthentication", notes = "restituisce un token per l�accesso alle piattaforme del Sistema Sanitario Regionale On Line ", response = Token.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "basicAuth")
    }, tags={ "Configuratore Proxy API", })
    @io.swagger.annotations.ApiResponses(value = {
        @io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo", response = Token.class),

        @io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Errore.class),

        @io.swagger.annotations.ApiResponse(code = 404, message = "not found", response = Errore.class),

        @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class),

        @io.swagger.annotations.ApiResponse(code = 502, message = "Service invocation error", response = Errore.class) })
    public Response proxyTokenAuthenticationPost(@ApiParam(value = "Contiene il codice fiscale dell'utente loggato ottenuto tramite shibboleth", required = true) @HeaderParam("Shib-Identita-CodiceFiscale") String shibIdentitaCodiceFiscale, @ApiParam(value = "Contiene un id univoco (`uuid v4`) della chiamata HTTP che viene generato dal front-end, viene propagato agli strati successivi e viene restituito al front-end assieme alla response, permettendo cos� di tracciare l'intero ciclo di vita della richiesta", required = true) @HeaderParam("X-Request-Id") String xRequestId, @ApiParam(value = "Contiene l'elenco degli ip che hanno gestito la richiesta. Utile per i log e l'audit. Il formato della stringa �  ``` <ip-front-end> | <ip-primo-server> | ... | <ip-ultimo-server> ``` Es. `10.10.10.10 | 10.38.45.68 | 10.128.69.244` ", required = true) @HeaderParam("X-Forwarded-For") String xForwardedFor, @ApiParam(value = "Contiene il codice del servizio chiamante ", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio, @ApiParam(value = "parametri necessari al recupero del token di autenticazione", required = true) ParametriAutenticazione parametriAutenticazione, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest request);

    @GET
    @Path("/token-information2")
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "getTokenInformation", notes = "verifica che il token utilizzato per l�accesso sia quello generato in fase di autenticazione e  sia ancora valido e di reperire le caratteristiche della richiesta di accesso registrate in LCCE in fase di autenticazione ", response = ModelTokenInformazione.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "basicAuth")
    })
    @io.swagger.annotations.ApiResponses(value = {
        @io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo", response = ModelTokenInformazione.class),

        @io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Errore.class),

        @io.swagger.annotations.ApiResponse(code = 404, message = "not found", response = Errore.class),

        @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class),

        @io.swagger.annotations.ApiResponse(code = 502, message = "Service invocation error", response = Errore.class) })
    public Response proxyTokenInformationGet(
    		@ApiParam(value = "Contiene il codice fiscale dell'utente loggato ottenuto tramite shibboleth", required = true) @HeaderParam("Shib-Identita-CodiceFiscale") String shibIdentitaCodiceFiscale, 
    		@ApiParam(value = "Contiene un id univoco (`uuid v4`) della chiamata HTTP che viene generato dal front-end, viene propagato agli strati successivi e viene restituito al front-end assieme alla response, permettendo cos� di tracciare l'intero ciclo di vita della richiesta", required = true) @HeaderParam("X-Request-Id") String xRequestId, 
    		@ApiParam(value = "Contiene l'elenco degli ip che hanno gestito la richiesta. Utile per i log e l'audit. Il formato della stringa �  ``` <ip-front-end> | <ip-primo-server> | ... | <ip-ultimo-server> ``` Es. `10.10.10.10 | 10.38.45.68 | 10.128.69.244` ", required = true) @HeaderParam("X-Forwarded-For") String xForwardedFor, 
    		@ApiParam(value = "Contiene il codice del servizio chiamante ", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio, 
    		@ApiParam(value = "Contiene il token di autenticazione ", required = true) @HeaderParam("Token") String token, 
    		@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest request);
    
    
    
    @GET
    @Path("/utente")
    @Produces({ "application/json" })
    @io.swagger.annotations.ApiOperation(value = "utenteGet", notes = "Legge le informazioni relative all'utente", response = ModelUtenteBase.class, authorizations = {
        @io.swagger.annotations.Authorization(value = "basicAuth")
    })
    @io.swagger.annotations.ApiResponses(value = {
        @io.swagger.annotations.ApiResponse(code = 200, message = "Operazione eseguita con successo", response = ModelUtenteBase.class),

        @io.swagger.annotations.ApiResponse(code = 400, message = "bad request", response = Errore.class),

        @io.swagger.annotations.ApiResponse(code = 401, message = "Unauthorized", response = Errore.class),

        @io.swagger.annotations.ApiResponse(code = 404, message = "not found", response = Errore.class),

        @io.swagger.annotations.ApiResponse(code = 500, message = "Internal Server Error", response = Errore.class),

        @io.swagger.annotations.ApiResponse(code = 502, message = "Service invocation error", response = Errore.class) })
    public Response utenteGet(
    		@ApiParam(value = "Contiene il codice fiscale dell'utente loggato ottenuto tramite shibboleth", required = true) @HeaderParam("Shib-Identita-CodiceFiscale") String shibIdentitaCodiceFiscale, 
    		@ApiParam(value = "Contiene un id univoco (`uuid v4`) della chiamata HTTP che viene generato dal front-end, viene propagato agli strati successivi e viene restituito al front-end assieme alla response, permettendo cos� di tracciare l'intero ciclo di vita della richiesta", required = true) @HeaderParam("X-Request-Id") String xRequestId, 
    		@ApiParam(value = "Contiene l'elenco degli ip che hanno gestito la richiesta. Utile per i log e l'audit. Il formato della stringa �  ``` <ip-front-end> | <ip-primo-server> | ... | <ip-ultimo-server> ``` Es. `10.10.10.10 | 10.38.45.68 | 10.128.69.244` ", required = true) @HeaderParam("X-Forwarded-For") String xForwardedFor, 
    		@ApiParam(value = "Contiene il codice del servizio chiamante ", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio, 
    		@Context SecurityContext securityContext, @Context HttpHeaders httpHeaders, @Context HttpServletRequest request);

}
