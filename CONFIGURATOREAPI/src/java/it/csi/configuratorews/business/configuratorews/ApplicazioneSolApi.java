/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.configuratorews;

import io.swagger.annotations.ApiParam;
import it.csi.configuratorews.dto.configuratorews.Errore;
import it.csi.configuratorews.dto.configuratorews.ModelCollocazione;
import it.csi.configuratorews.dto.configuratorews.ModelUtente;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/sol")


@io.swagger.annotations.Api(description = "the applicazione_sol API")

public interface ApplicazioneSolApi  {
	@Deprecated
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
    public Response applicazioneSolApplicazioneCollocazioniGet(@ApiParam(value = "Contiene il codice fiscale dell'utente loggato ottenuto tramite shibboleth", required = true) @HeaderParam("Shib-Identita-CodiceFiscale") String shibIdentitaCodiceFiscale, @ApiParam(value = "Contiene un id univoco (`uuid v4`) della chiamata HTTP che viene generato dal front-end, viene propagato agli strati successivi e viene restituito al front-end assieme alla response, permettendo cos� di tracciare l'intero ciclo di vita della richiesta", required = true) @HeaderParam("X-Request-Id") String xRequestId, @ApiParam(value = "Contiene l'elenco degli ip che hanno gestito la richiesta. Utile per i log e l'audit. Il formato della stringa �  ``` <ip-front-end> | <ip-primo-server> | ... | <ip-ultimo-server> ``` Es. `10.10.10.10 | 10.38.45.68 | 10.128.69.244` ", required = true) @HeaderParam("X-Forwarded-For") String xForwardedFor, @ApiParam(value = "Contiene il codice del servizio chiamante ", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio, @PathParam("applicazione") String applicazione, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,@Context HttpServletRequest request);
	@Deprecated
	@GET
    @Path("/{applicazione}/utenti")

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
    public Response applicazioneSolApplicazioneUtentiGet(@ApiParam(value = "Contiene il codice fiscale dell'utente loggato ottenuto tramite shibboleth", required = true) @HeaderParam("Shib-Identita-CodiceFiscale") String shibIdentitaCodiceFiscale, @ApiParam(value = "Contiene un id univoco (`uuid v4`) della chiamata HTTP che viene generato dal front-end, viene propagato agli strati successivi e viene restituito al front-end assieme alla response, permettendo cos� di tracciare l'intero ciclo di vita della richiesta", required = true) @HeaderParam("X-Request-Id") String xRequestId, @ApiParam(value = "Contiene l'elenco degli ip che hanno gestito la richiesta. Utile per i log e l'audit. Il formato della stringa �  ``` <ip-front-end> | <ip-primo-server> | ... | <ip-ultimo-server> ``` Es. `10.10.10.10 | 10.38.45.68 | 10.128.69.244` ", required = true) @HeaderParam("X-Forwarded-For") String xForwardedFor, @ApiParam(value = "Contiene il codice del servizio chiamante ", required = true) @HeaderParam("X-Codice-Servizio") String xCodiceServizio, @PathParam("applicazione") String applicazione, @QueryParam("azienda") String azienda, @Context SecurityContext securityContext, @Context HttpHeaders httpHeaders,@Context HttpServletRequest request);
}
