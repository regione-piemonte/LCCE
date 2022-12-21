/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.configuratorews.rest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import it.csi.configuratorews.business.configuratorews.log.service.LogService;
import it.csi.configuratorews.business.configuratorews.log.util.Operazione;
import it.csi.configuratorews.business.configuratorews.rest.SOLRESTBaseService;
import it.csi.configuratorews.business.dao.AbilitazioneLowDao;
import it.csi.configuratorews.business.dao.ApplicazioneLowDao;
import it.csi.configuratorews.business.dao.FunzionalitaLowDao;
import it.csi.configuratorews.business.dao.TreeFunzionalitaLowDao;
import it.csi.configuratorews.business.dto.AbilitazioneDto;
import it.csi.configuratorews.business.dto.ApplicazioneDto;
import it.csi.configuratorews.business.dto.CsiLogAuditDto;
import it.csi.configuratorews.business.dto.FunzionalitaDto;
import it.csi.configuratorews.business.dto.TreeFunzionalitaDto;
import it.csi.configuratorews.dto.configuratorews.ModelAbilitazione;
import it.csi.configuratorews.dto.configuratorews.ModelApplicazione;
import it.csi.configuratorews.exception.ErroreBuilder;
import it.csi.configuratorews.exception.RESTException;
import it.csi.configuratorews.interfacews.client.abilitazione.AbilitazioniServiceClient;
import it.csi.configuratorews.interfacews.mapper.AbilitazioniMapper;
import it.csi.configuratorews.util.Constants;
import it.csi.configuratorews.util.LogUtil;
import it.csi.configuratorews.validator.AbilitazioniValidator;


@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Transactional
public class AbilitazioniService extends SOLRESTBaseService {

	protected LogUtil log = new LogUtil(this.getClass());

	private String codiceRuolo;
	private String codiceCollocazione;
	private String codiceAzienda;

	public AbilitazioniService(String shibIdentitaCodiceFiscale, String xRequestID,
							   String xForwardedFor, String xCodiceServizio, String codiceRuolo, String codiceCollocazione, String codiceAzienda,
							   SecurityContext securityContext, HttpHeaders httpHeaders, HttpServletRequest request) {
		super(shibIdentitaCodiceFiscale, xRequestID, xForwardedFor, xCodiceServizio, securityContext, httpHeaders, request);
		this.codiceRuolo = codiceRuolo;
		this.codiceCollocazione = codiceCollocazione;
		this.codiceAzienda = codiceAzienda;
	}

	@Autowired
	LogService logService;

	@Autowired
	AbilitazioniValidator abilitazioniGetValidator;

	@Autowired
	AbilitazioniMapper abilitazioniMapper;

	@Autowired
	AbilitazioniServiceClient abilitazioniServiceClient;

	@Autowired
	AbilitazioneLowDao abilitazioneLowDao;
	
	@Autowired
	ApplicazioneLowDao applicazioneLowDao;
	
	@Autowired
	private FunzionalitaLowDao funzionalitaLowDao;
	
	@Autowired
	private TreeFunzionalitaLowDao treeFunzionalitaLowDao;

	public static final String url = "/login/abilitazioni";

	public static final String NOME_SERVIZIO = "AbilitazioniService";

	@Override
	protected Response execute() {
		String METHOD_NAME = "execute";
		log.info(METHOD_NAME, "Applicazioni abilitate");
		String request = generateRequest(url);
		String response = null;
		String uuidString = UUID.randomUUID().toString();
		List<ModelApplicazione> applicazioniList = new ArrayList<ModelApplicazione>();
		CsiLogAuditDto csiLogAuditDto = new CsiLogAuditDto();
        try{
        	
        	String xForwadedForInHeader = extractXForwadedFor(xForwardedFor);
        	
			/*
				Scrittura logAudit attivazione
			 */
			csiLogAuditDto = logService.logAttivazione(Operazione.READ, Constants.GET_ABI, uuidString, xForwadedForInHeader,
					shibIdentitaCodiceFiscale, xRequestID, xCodiceServizio, request);

			/*
			 * validazione input
			 */
			ErroreBuilder erroreBuilder = abilitazioniGetValidator.checkCampiObbligatori(shibIdentitaCodiceFiscale, xRequestID, xForwadedForInHeader, xCodiceServizio,
					codiceRuolo, codiceCollocazione, codiceAzienda);


			// Invocazione DAO per restituire l'applicazione richiedente
			ApplicazioneDto applicazione = abilitazioneLowDao.findApplicazioneByCodice(xCodiceServizio);

			// System.out.println("codiceServizio: " + xCodiceServizio);
			// System.out.println("applicazione: " + applicazione);

			if(applicazione == null){
			// gestione errore
				Response returnResponse = ErroreBuilder.from(Response.Status.BAD_REQUEST)
						.codice(Constants.PARAMETRO_NON_VALIDO).descrizione("Il parametro Applicazione non risulta presente o non e' valido").response();

				response = returnResponse.getEntity().toString();
				return returnResponse;
			}


			// Invocazione DAO per restituire le applicazioni abilitate per il ruolo, collocazione, azienda e codice fiscale passati come parametro
			//List<AbilitazioneDto> abilitazioni = abilitazioneLowDao.findAbilitazioniByRuoloByCollByAzByCF(codiceRuolo, codiceCollocazione, codiceAzienda, shibIdentitaCodiceFiscale);
			List<ApplicazioneDto> abilitazioni = applicazioneLowDao.findApplicazioniAbilitate(codiceRuolo, codiceCollocazione, codiceAzienda, shibIdentitaCodiceFiscale);
			
			if(abilitazioni==null || abilitazioni.isEmpty()) {
				
				// gestione errore
				Response returnResponse = ErroreBuilder.from(Response.Status.BAD_REQUEST)
						.codice(Constants.PARAMETRO_NON_VALIDO).descrizione("Nessuna abilitazione trovata").response();

				response = returnResponse.getEntity().toString();
				return returnResponse;				
			}

			
			for (ApplicazioneDto applicazioneDto : abilitazioni) {
				ModelApplicazione modelApplicazione = new ModelApplicazione();
				modelApplicazione.setCodice(applicazioneDto.getCodice());
				modelApplicazione.setDescrizione(applicazioneDto.getDescrizione());
				
				String redirectUrldb = applicazioneDto.getRedirectUrl().trim();
				String redirectUrl = "";
				
				
				if(redirectUrldb.startsWith("redirect:")) {
					
						int indexstart= redirectUrldb.indexOf("http");
						redirectUrl = redirectUrldb.substring(redirectUrldb.indexOf("http"));
				}
				
				
				modelApplicazione.setRedirectUrl(redirectUrl);
				applicazioniList.add(modelApplicazione);
			}

			
        }catch(RESTException e){
        	log.error("AbilitazioniGet", "Errore rest: ", e);
			throw e;
		}catch(Exception e){
			log.error("AbilitazioniGet", "Errore rest: ", e);
			response = Response.Status.INTERNAL_SERVER_ERROR.getStatusCode() +" "+ Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase();
			throw ErroreBuilder.from(Response.Status.INTERNAL_SERVER_ERROR).exception();
        }finally{
        	/*
        		Update logAudit
         	 */
        	try {
				logService.updateLog(csiLogAuditDto, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
        }

		return Response.ok(applicazioniList).build();
	}
	
	/**
	 * @param arrayModelAbilitazione
	 * @return
	 */
	public String generateOKResponseLog(ModelAbilitazione[] arrayModelAbilitazione) {
		String response;
		StringBuilder responseAbilitazioni = new StringBuilder("Status: " + Response.Status.OK.getStatusCode() + "\n");
		for(ModelAbilitazione modelCollocazioni : arrayModelAbilitazione) {
			
			responseAbilitazioni.append(modelCollocazioni.toString() + "\n");
			
		}
		response = responseAbilitazioni.toString();
		return response;
	}
	
//	private void getCodiciApplicazione(List<String> listCodiciApplicazione, AbilitazioneDto abilitazioneDto) {
//		
//		if(abilitazioneDto.getTreeFunzionalitaDto() != null &&
//				abilitazioneDto.getTreeFunzionalitaDto().getFunzionalitaDto() != null &&
//				abilitazioneDto.getTreeFunzionalitaDto().getFunzionalitaDto().getApplicazioneDto() != null){
//			
//			listCodiciApplicazione.add(abilitazioneDto.getTreeFunzionalitaDto().getFunzionalitaDto().getApplicazioneDto().getCodice());
//			getCodiciApplicazioneSons(listCodiciApplicazione, abilitazioneDto);
//		
//		}else{
//			
//			if(abilitazioneDto.getTreeFunzionalitaDto() != null){
//				getCodiciApplicazioneSons(listCodiciApplicazione, abilitazioneDto);
//			}
//		}
//	}

//	private void getCodiciApplicazioneSons(List<String> listCodiciApplicazione, AbilitazioneDto abilitazioneDto) {
//		
//		TreeFunzionalitaDto treeFunzionalitaDto = new TreeFunzionalitaDto();
//		treeFunzionalitaDto.setIdTreeFunzione(abilitazioneDto.getTreeFunzionalitaDto().getIdTreeFunzione());
//		
//		List<TreeFunzionalitaDto> funzionalitaSonsList
//				= treeFunzionalitaLowDao.findFunzionalitaSons(treeFunzionalitaDto);
//		if(funzionalitaSonsList != null && !funzionalitaSonsList.isEmpty()){
//			for(TreeFunzionalitaDto treeFunzionalita : funzionalitaSonsList){
//				FunzionalitaDto funzionalita =
//						funzionalitaLowDao.findByPrimaryId(treeFunzionalita.getFunzionalitaDto().getIdFunzione());
//				if(funzionalita.getApplicazioneDto() != null){
//					listCodiciApplicazione.add(funzionalita.getApplicazioneDto().getCodice());
//				}
//			}
//		}
//	}
}
