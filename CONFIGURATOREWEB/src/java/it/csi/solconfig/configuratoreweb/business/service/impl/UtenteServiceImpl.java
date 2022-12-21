/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.service.impl;

import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import javax.mail.MessagingException;
import javax.persistence.PersistenceException;
import javax.xml.bind.JAXBContext;
import javax.xml.ws.soap.SOAPFaultException;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;

import it.csi.solconfig.configuratoreweb.business.dao.AbilitazioneLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.ApplicazioneLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.AziendaLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.BatchAbilitazioneMassivaLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.BatchAbilitazioneMassivaUtentiLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.BatchStatoLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.CollocazioneLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.ConfigurazioneLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.FonteLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.FunzionalitaLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.PreferenzaLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.RichiestaCredenzialiLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.RuoloLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.RuoloUtenteLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.TipoContrattoLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.TreeFunzionalitaLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.UtenteCollocazioneLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.UtenteLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.UtentiConfiguratoreViewLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.VisibilitaAziendaLowDao;
import it.csi.solconfig.configuratoreweb.business.dao.dto.AbilitazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.ApplicazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.AziendaDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.BatchAbilitazioneMassivaDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.BatchAbilitazioneMassivaUtentiDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.CatalogoLogAuditConfDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.CollocazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.ConfigurazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.CsiLogAuditDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.FonteDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.FunzionalitaDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.MessaggiUtenteDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.PreferenzaDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RichiestaCredenzialiDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.RuoloUtenteDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.UtenteCollocazioneDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.UtenteDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.UtentiConfiguratoreViewDto;
import it.csi.solconfig.configuratoreweb.business.dao.dto.VisibilitaAziendaDto;
import it.csi.solconfig.configuratoreweb.business.dao.util.Constants;
import it.csi.solconfig.configuratoreweb.business.dao.util.WebServiceResponse;
import it.csi.solconfig.configuratoreweb.business.exceptions.UserExistException;
import it.csi.solconfig.configuratoreweb.business.exceptions.WebServiceException;
import it.csi.solconfig.configuratoreweb.business.service.CollocazioneService;
import it.csi.solconfig.configuratoreweb.business.service.ServiziOnLineService;
import it.csi.solconfig.configuratoreweb.business.service.UtenteService;
import it.csi.solconfig.configuratoreweb.presentation.constants.ConstantsWebApp;
import it.csi.solconfig.configuratoreweb.presentation.model.AbilitazioneMassivaModel;
import it.csi.solconfig.configuratoreweb.presentation.model.BaseDto;
import it.csi.solconfig.configuratoreweb.presentation.model.Data;
import it.csi.solconfig.configuratoreweb.presentation.model.FormNuovoUtente;
import it.csi.solconfig.configuratoreweb.presentation.model.PaginaDTO;
import it.csi.solconfig.configuratoreweb.presentation.model.RicercaUtenteModel;
import it.csi.solconfig.configuratoreweb.presentation.model.RisultatiRicercaAbilitazioneMassivaDTO;
import it.csi.solconfig.configuratoreweb.presentation.model.RisultatiRicercaUtenteDTO;
import it.csi.solconfig.configuratoreweb.presentation.model.SalvataggioUtenteModel;
import it.csi.solconfig.configuratoreweb.presentation.model.SolModel;
import it.csi.solconfig.configuratoreweb.presentation.model.StoricoDTO;
import it.csi.solconfig.configuratoreweb.util.FunzionalitaEnum;
import it.csi.solconfig.configuratoreweb.util.OperazioneEnum;
import it.csi.solconfig.configuratoreweb.util.Utils;
import it.csi.solconfig.configuratoreweb.util.mailsender.JavaMailUtil;
import it.csi.solconfig.configuratoreweb.util.mailsender.MailAuraParams;
import it.csi.solconfig.configuratoreweb.util.mailsender.MailMessage;
import it.csi.solconfig.configuratoreweb.wsdl.aura.ScreeningEpatiteC.Request;
import it.csi.solconfig.configuratoreweb.wsdl.aura.ScreeningEpatiteC.Response;
import it.csi.solconfig.configuratoreweb.wsdl.aura.ScreeningEpatiteC.client.ScreeningEpatiteCServiceClient;
import it.csi.solconfig.configuratoreweb.wsdl.aura.findprofilianagrafici.ArrayOfdatianagraficiDatiAnagrafici;
import it.csi.solconfig.configuratoreweb.wsdl.aura.findprofilianagrafici.ArrayOfmessageMessage;
import it.csi.solconfig.configuratoreweb.wsdl.aura.findprofilianagrafici.DatiAnagrafici;
import it.csi.solconfig.configuratoreweb.wsdl.aura.findprofilianagrafici.DatiAnagraficiBody;
import it.csi.solconfig.configuratoreweb.wsdl.aura.findprofilianagrafici.DatiAnagraficiMsg;
import it.csi.solconfig.configuratoreweb.wsdl.aura.findprofilianagrafici.FindProfiliAnagraficiRequest;
import it.csi.solconfig.configuratoreweb.wsdl.aura.findprofilianagrafici.Footer;
import it.csi.solconfig.configuratoreweb.wsdl.aura.findprofilianagrafici.Message;
import it.csi.solconfig.configuratoreweb.wsdl.aura.findprofilianagrafici.client.AuraAnagrafeServiceClient;
import it.csi.solconfig.configuratoreweb.wsdl.aura.getprofilosanitario.DatiPrimari;
import it.csi.solconfig.configuratoreweb.wsdl.aura.getprofilosanitario.InfoAnagrafiche;
import it.csi.solconfig.configuratoreweb.wsdl.aura.getprofilosanitario.SoggettoAuraBody;
import it.csi.solconfig.configuratoreweb.wsdl.aura.getprofilosanitario.SoggettoAuraMsg;
import it.csi.solconfig.configuratoreweb.wsdl.aura.getprofilosanitario.client.AuraGetProfiloSanServiceClient;
import it.csi.solconfig.configuratoreweb.wsdl.opessan.GetOperatoreAttivoResponse;
import it.csi.solconfig.configuratoreweb.wsdl.opessan.IdentificativoProfilo;
import it.csi.solconfig.configuratoreweb.wsdl.opessan.RicercaOperatoreBody;
import it.csi.solconfig.configuratoreweb.wsdl.opessan.RicercaOperatore_Type;
import it.csi.solconfig.configuratoreweb.wsdl.opessan.client.RicercaOperatoreServiceClient;
import it.csi.solconfig.configuratoreweb.wsdl.opessan.common.response.Header;
import it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatore.ArrayIncarichiConf;
import it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatore.GetInfoOperatoreConf;
import it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatore.GetInfoOperatoreConfResponse;
import it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatore.GetInfoOperatoreConfResponse.Return;
import it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatore.IncarichiConf;
import it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatore.InfoRapportoDiLavoroConf;
import it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatore.InfoRapportoLavoroConf;
import it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatore.ReqDettaglioOperatoreConf;
import it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatore.StudiMediciConf;
import it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatore.StudioMedicoConf;
import it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatore.client.DettaglioOperatoreServiceClient;
import it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatoreDipendente.GetDettaglioConf;
import it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatoreDipendente.GetDettaglioConfResponse2;
import it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatoreDipendente.GetDettaglioRequestBody;
import it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatoreDipendente.InfoQualificaDipConf;
import it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatoreDipendente.InfoQualificheDipConf;
import it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatoreDipendente.InfoSedeOperativaConf;
import it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatoreDipendente.SoggettoOpessanDipConfBody.InfoSediOperative;
import it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatoreDipendente.client.OperatoreDipendenteServiceClient;
import it.csi.solconfig.configuratoreweb.wsdl.remedy.InfoNota;
import it.csi.solconfig.configuratoreweb.wsdl.remedy.infodettagli.client.RemedyInfoDettagliServiceClient;
import it.csi.solconfig.configuratoreweb.wsdl.remedy.insertticket.client.RemedyInsertTicketServiceClient;

@Service
@Transactional
public class UtenteServiceImpl extends BaseServiceImpl implements UtenteService {

    @Autowired
    private UtenteLowDao utenteLowDao;

    @Autowired
    private ApplicazioneLowDao applicazioneLowDao;

    @Autowired
    private UtenteCollocazioneLowDao utenteCollocazioneLowDao;

    @Autowired
    private RuoloUtenteLowDao ruoloUtenteLowDao;

    @Autowired
    private RuoloLowDao ruoloLowDao;

    @Autowired
    private CollocazioneLowDao collocazioneLowDao;

    @Autowired
    private AbilitazioneLowDao abilitazioneLowDao;

    @Autowired
    private TipoContrattoLowDao contrattoLowDao;

    @Autowired
    private FunzionalitaLowDao funzionalitaLowDao;

    @Autowired
    private TreeFunzionalitaLowDao treeFunzionalitaLowDao;

    @Autowired
    private AuraAnagrafeServiceClient auraAnagrafeServiceClient;

    @Autowired
    private AuraGetProfiloSanServiceClient auraGetProfiloSanServiceClient;

    @Autowired
    private RicercaOperatoreServiceClient ricercaOperatoreServiceClient;

    @Autowired
    private CollocazioneService collocazioneService;

    @Autowired
    private ServiziOnLineService serviziOnLineService;

    @Autowired
    private JavaMailUtil javaMailUtil;

    @Autowired
    private MailMessage mailMessage;

    @Autowired
    private RemedyInsertTicketServiceClient remedyInsertTicketServiceClient;

    @Autowired
    private RemedyInfoDettagliServiceClient remedyInfoDettagliServiceClient;

    @Autowired
    private RichiestaCredenzialiLowDao richiestaCredenzialiLowDao;

    @Autowired
    private ConfigurazioneLowDao configurazioneLowDao;

    @Autowired
    private UtentiConfiguratoreViewLowDao utentiConfiguratoreViewLowDao;
    
    @Autowired
    private ScreeningEpatiteCServiceClient screeningEpatiteCServiceClient;
    
    @Autowired
    private DettaglioOperatoreServiceClient dettaglioOperatoreServiceClient; 
    
    @Autowired
    private OperatoreDipendenteServiceClient operatoreDipendenteServiceClient; 
    
    @Autowired
    private FonteLowDao fonteLowDao;
    
    @Autowired
    private AziendaLowDao aziendaDaoLowDao;
    
    @Autowired
    private VisibilitaAziendaLowDao visiblitaAziendaLowDao;
    
    @Autowired
    private PreferenzaLowDao preferenzaLowDao;
    
    @Autowired
    private BatchStatoLowDao statoBatchLowDao;
    
    @Autowired
    BatchAbilitazioneMassivaLowDao batchAbilitazioneLowDao;
    
    @Autowired
    BatchAbilitazioneMassivaUtentiLowDao batchAbilitazioneUtentiLowDao;
    /*
    @Autowired
    CsiLogAuditLowDao csiLogAuditLowDao;

    @Autowired
    CatalogoLogAuditConfLowDao catalogoLogAuditConfLowDao;
    
    @Value("${codiceAmbiente:AmbienteDefault}")
    String codiceAmbiente;
    @Value("${codiceUnitaInstallazione:UnitaDefault}")
    String codiceUnitaInstallazione;
    */
    
    @Override
    public List<UtentiConfiguratoreViewDto> exportUtenti(Data data) {

        List<UtentiConfiguratoreViewDto> risultatiExportUtenti = new ArrayList<UtentiConfiguratoreViewDto>();

        log.info("Profilo export :" + data.getUtente().getProfilo());
        boolean superUser = FunzionalitaEnum.SUPERUSERCONF_PROF.getValue().equalsIgnoreCase(data.getUtente().getProfilo());
       

        if (superUser)
            risultatiExportUtenti = utentiConfiguratoreViewLowDao.findForExcelExport(false, null,data.getUtente().getCodiceFiscale());
        else {
            List<CollocazioneDto> collocazioneDtoList =
                    collocazioneLowDao.getCollocazioniAbilitatePerExport(data.getUtente().getCodiceFiscale(), Constants.APPLICATION_CODE);

            if(collocazioneDtoList != null && !collocazioneDtoList.isEmpty()){
                List<String> collocazioneFilter = new ArrayList<>();
                for(CollocazioneDto collocazioneDto : collocazioneDtoList){
                    collocazioneFilter.add(collocazioneDto.getColCodAzienda() + collocazioneDto.getColDescAzienda()
                            + collocazioneDto.getColCodice() + collocazioneDto.getColDescrizione());
                }

                risultatiExportUtenti = utentiConfiguratoreViewLowDao.findForExcelExport(true, collocazioneFilter,data.getUtente().getCodiceFiscale());
            }
        }

        return risultatiExportUtenti;
    }

    @Override
    public PaginaDTO<RisultatiRicercaUtenteDTO> ricercaUtente(RicercaUtenteModel ricercaUtenteModel, Data data) throws WebServiceException {
        if (ricercaUtenteModel.getNumeroPagina() < 1 || ricercaUtenteModel.getNumeroElementi() < 1) {
            log.error("ERROR: NumeroElementi e/o numeroPagina non corretti");
            return new PaginaDTO<>();
        }

        PaginaDTO<RisultatiRicercaUtenteDTO> paginaDTO = new PaginaDTO<>();

        if (data != null && data.getUtente() != null && data.getUtente().getFunzionalitaAbilitate() != null
                && !data.getUtente().getFunzionalitaAbilitate().isEmpty()) {

            if (data.getUtente().getUtenteCollocazioneList() == null) {
                UtenteDto utenteDto = Utils.getFirstRecord(utenteLowDao.findByCodiceFiscale(data.getUtente().getCodiceFiscale()));
                data.getUtente().setUtenteCollocazioneList(utenteCollocazioneLowDao.findByUtenteDto(utenteDto));
            }

            boolean superUser = FunzionalitaEnum.SUPERUSERCONF_PROF.getValue().equalsIgnoreCase(data.getUtente().getProfilo());
           
            

            if (superUser) {
                paginaDTO = utenteLowDao.ricercaUtenteDaSuperUserByCodiceFiscale(ricercaUtenteModel.getCodiceFiscale(),
                        ricercaUtenteModel.getNumeroPagina(), ricercaUtenteModel.getNumeroElementi(),data.getUtente().getCodiceFiscale());
                if(paginaDTO.getElementi().size()==0) {
                	UtenteDto utenteEsiste = Utils.getFirstRecord(utenteLowDao.findByCodiceFiscale(ricercaUtenteModel.getCodiceFiscale()));
                	if(utenteEsiste!=null) {
                		paginaDTO = utenteLowDao.ricercaUtenteDaSuperUserByCodiceFiscaleFiltrato(ricercaUtenteModel.getCodiceFiscale(),
                                ricercaUtenteModel.getNumeroPagina(), ricercaUtenteModel.getNumeroElementi(),data.getUtente().getCodiceFiscale());
                	}
                	
                	
                }
               
            } else {
                ApplicazioneDto applicazioneDto = new ApplicazioneDto();
                ApplicazioneDto record = Utils.getFirstRecord(applicazioneLowDao.findByCodice(applicazioneDto, ConstantsWebApp.APPL_CONF));

                List<Long> listCollocazioni = new ArrayList<>();
                if (data.getUtente().getUtenteCollocazioneList() != null)
                    listCollocazioni = getCollocazioniAbilitate(data, Constants.APPLICATION_CODE);
                
                boolean isDelegato = data.getUtente() != null &&
        				data.getUtente().getProfilo() != null &&
        				data.getUtente().getProfilo().equals(FunzionalitaEnum.CONF_DELEGATO_PROF.getValue());
                
                boolean isTitolare = data.getUtente() != null &&
        				data.getUtente().getProfilo() != null &&
        				data.getUtente().getProfilo().equals(FunzionalitaEnum.CONF_TITOLARE_PROF.getValue());

                paginaDTO = utenteLowDao.ricercaUtenteDaOperatoreByCodiceFiscale(ricercaUtenteModel.getCodiceFiscale(),
                        listCollocazioni.isEmpty() ? Collections.singletonList(-1L) : listCollocazioni,
                        ricercaUtenteModel.getNumeroPagina(), ricercaUtenteModel.getNumeroElementi(),data.getUtente().getCodiceFiscale(),isDelegato,isTitolare);
                                
                
                if(paginaDTO.getElementi().size()==0) {
                	UtenteDto utenteEsiste = Utils.getFirstRecord(utenteLowDao.findByCodiceFiscale(ricercaUtenteModel.getCodiceFiscale()));
                	if(utenteEsiste!=null) {
                		paginaDTO = utenteLowDao.ricercaUtenteDaSuperUserByCodiceFiscaleFiltrato(ricercaUtenteModel.getCodiceFiscale(),
                                ricercaUtenteModel.getNumeroPagina(), ricercaUtenteModel.getNumeroElementi(),data.getUtente().getCodiceFiscale());
                	}
                	
                	
                }
            }
            replaceStringCollocazione(paginaDTO.getElementi(), "@", "\n");
        }

        return paginaDTO;
    }

    private List<Long> getCollocazioniAbilitate(Data data, String codiceApplicazione) {
        return collocazioneLowDao.getCollocazioniAbilitate(data.getUtente().getCodiceFiscale(), codiceApplicazione);
    }

    @Override
    public UtenteDto recuperaUtenteByCodiceFiscale(String codiceFiscale) {
        return Utils.getFirstRecord(utenteLowDao.findByCodiceFiscale(codiceFiscale));
    }

    @Override
    public UtenteDto recuperaUtenteById(Long id) {
        return utenteLowDao.findByPrimaryId(id);
    }

    @Override
    public FormNuovoUtente getUtenteFromAura(String cf) throws WebServiceException {
        FindProfiliAnagraficiRequest findProfiliAnagraficiRequest = new FindProfiliAnagraficiRequest();
        findProfiliAnagraficiRequest.setCodiceFiscale(cf);
        findProfiliAnagraficiRequest.setFlagDecesso("0");
        Optional<DatiAnagraficiMsg> responseAura = Optional.ofNullable(auraAnagrafeServiceClient.call(findProfiliAnagraficiRequest));

        List<DatiAnagrafici> datiAnagraficiList = responseAura.map(DatiAnagraficiMsg::getBody)
                .map(DatiAnagraficiBody::getElencoProfili)
                .map(ArrayOfdatianagraficiDatiAnagrafici::getDatianagrafici)
                .orElseGet(Collections::emptyList);

        WebServiceResponse auraResponse = checkAuraResponse(responseAura, datiAnagraficiList);

        switch (auraResponse) {
            case PRESENTE:
                for (DatiAnagrafici datiAnagrafici : datiAnagraficiList) {
                    SoggettoAuraMsg soggettoAuraMsg = auraGetProfiloSanServiceClient
                            .call(datiAnagrafici.getIdProfiloAnagrafico().toString());

                    if (Optional.ofNullable(soggettoAuraMsg)
                            .map(SoggettoAuraMsg::getBody)
                            .map(SoggettoAuraBody::getInfoAnag)
                            .map(InfoAnagrafiche::getDatiPrimari)
                            .map(DatiPrimari::getStatoProfiloAnagrafico)
                            .map("1"::equals).orElse(false)) {

                        RicercaOperatore_Type req = new RicercaOperatore_Type();
                        IdentificativoProfilo identificativoProfilo = new IdentificativoProfilo();
                        identificativoProfilo.setIdAura(datiAnagrafici.getIdProfiloAnagrafico());
                        req.setBody(identificativoProfilo);

                        Optional<GetOperatoreAttivoResponse.Return> callOpessan = Optional.ofNullable(ricercaOperatoreServiceClient.call(req));
                        WebServiceResponse opessanResponse = checkOpessanResponse(callOpessan);

                        switch (opessanResponse) {
                            case ERRORE:
                                throw new WebServiceException(ConstantsWebApp.ERRORE_WS_OPESSAN);

                            case PRESENTE:
                                throw new WebServiceException(ConstantsWebApp.UTENTE_PRENSENTE_IN_AURA_E_OPESSAN);

                            case NON_PRESENTE:
                                DatiPrimari datiPrimari = soggettoAuraMsg.getBody().getInfoAnag().getDatiPrimari();
                                SimpleDateFormat sdf = new SimpleDateFormat(ConstantsWebApp.DATE_PATTERN);

                                FormNuovoUtente form = new FormNuovoUtente();
                                form.setCf(cf);
                                form.setIdAura(datiAnagrafici.getIdProfiloAnagrafico().longValue());
                                form.setNome(datiPrimari.getNome());
                                form.setCognome(datiPrimari.getCognome());
                                form.setDataDiNascita(sdf.format(datiPrimari.getDataNascita().toGregorianCalendar().getTime()));
                                form.setProvinciaDiNascita(datiPrimari.getSiglaProvNascita());
                                form.setComuneDiNascita(datiPrimari.getDescComuneNascita());
                                form.setSesso(datiPrimari.getSesso());

                                return form;
                        }
                    }
                }

                return null;

            case NON_PRESENTE:
                return null;

            default:
                throw new WebServiceException(ConstantsWebApp.ERRORE_WS_AURA);
        }
    }

    private static WebServiceResponse checkAuraResponse(Optional<DatiAnagraficiMsg> responseAura, List<DatiAnagrafici> datiAnagraficiList) {
        if (!responseAura.map(DatiAnagraficiMsg::getFooter).map(Footer::getMessages).isPresent()
                && !datiAnagraficiList.isEmpty()) {
            return WebServiceResponse.PRESENTE;
        }

        return responseAura.map(DatiAnagraficiMsg::getFooter)
                .map(Footer::getMessages)
                .map(ArrayOfmessageMessage::getMessage)
                .orElseGet(Collections::emptyList)
                .stream()
                .map(Message::getCodice)
                .filter("E0010"::equals)
                .map(codice -> WebServiceResponse.NON_PRESENTE)
                .findFirst()
                .orElse(WebServiceResponse.ERRORE);
    }

    private static WebServiceResponse checkOpessanResponse(Optional<GetOperatoreAttivoResponse.Return> call) {
        if (call.map(GetOperatoreAttivoResponse.Return::getHeader)
                .map(Header::getCodiceRitorno)
                .map(codice -> codice == 1)
                .orElse(false)) {
            return WebServiceResponse.ERRORE;
        }

        return call.map(GetOperatoreAttivoResponse.Return::getBody)
                .map(RicercaOperatoreBody::getEsito)
                .map("KO"::equals)
                .orElse(false) ? WebServiceResponse.NON_PRESENTE : WebServiceResponse.PRESENTE;
    }

    private static WebServiceResponse checkAuraOrMEFResponse(Optional<Response> responseAura) {
        return responseAura.isPresent() && 
        	   responseAura.map(Response::getCognome).get() != null && 
        	   !responseAura.map(Response::getCognome).get().isEmpty()  ?
        		 WebServiceResponse.PRESENTE :
        			 responseAura.map(Response::getEsito)
        			 	.filter("Codice fiscale non trovato"::equalsIgnoreCase)
        			 	.map(esito -> WebServiceResponse.NON_PRESENTE)
        			 	.orElse(WebServiceResponse.ERRORE);
    }
    
    private static WebServiceResponse checkOpessanDettaglioOperatoreConfResponse(Optional<GetInfoOperatoreConfResponse.Return> call) {
        if (call.map(GetInfoOperatoreConfResponse.Return::getHeader)
                .map(it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatore.common.response.Header::getCodiceRitorno)
                .map(codice -> codice == 1)
                .orElse(false)) {
            return WebServiceResponse.ERRORE;
        }

        return call.map(GetInfoOperatoreConfResponse.Return::getBody).isPresent()
        		? WebServiceResponse.PRESENTE : WebServiceResponse.NON_PRESENTE;
    }
    

	private WebServiceResponse checkOpessanOperatoreDipendenteConfResponse(Optional<GetDettaglioConfResponse2.Return> call) {
		if (call.map(GetDettaglioConfResponse2.Return::getHeader)
                .map(it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatoreDipendente.common.response.Header::getCodiceRitorno)
                .map(codice -> codice != 1)
                .orElse(false)) {
            return WebServiceResponse.ERRORE;
        }

        return call.map(GetDettaglioConfResponse2.Return::getBody).isPresent()
        		? WebServiceResponse.PRESENTE : WebServiceResponse.NON_PRESENTE;
	}
    
    
    public SalvataggioUtenteModel salvaUtente(FormNuovoUtente form, String cfOperatore, String uuid, Data data) throws ParseException, UserExistException {
    	SalvataggioUtenteModel utenteSalvato = new SalvataggioUtenteModel();
        SimpleDateFormat sdf = new SimpleDateFormat(ConstantsWebApp.DATE_PATTERN);
        FonteDto fonte = Utils.getFirstRecord(fonteLowDao.findByFonteCodice(ConstantsWebApp.CODICE_FONTE_OPESSAN));
        
        if (!utenteLowDao.findByCodiceFiscale(form.getCf()).isEmpty())
            throw new UserExistException();

        UtenteDto operatore = Utils.getFirstRecord(utenteLowDao.findByCodiceFiscale(cfOperatore));

        UtenteDto utenteDto = new UtenteDto();
        utenteDto.setNome(form.getNome());
        utenteDto.setIdAura(form.getIdAura() != null ? form.getIdAura() : null);
        utenteDto.setCognome(form.getCognome());
        utenteDto.setComuneNascita(form.getComuneDiNascita());
        utenteDto.setProvincia(form.getProvinciaDiNascita());
        utenteDto.setCodiceFiscale(form.getCf());

        utenteDto.setDataNascita(Utils.toTimestamp(sdf.parse(form.getDataDiNascita())));
        utenteDto.setIndirizzoMail(form.getEmail());
        utenteDto.setNumeroTelefono(form.getTelefono());
        utenteDto.setSesso(form.getSesso());
        if(form.getContratto() != null) {
        	utenteDto.setTipoContrattoDto(contrattoLowDao.findByPrimaryId(form.getContratto()));
        }
        utenteDto.setDataInizioValidita(Utils.truncateTimestamp(Utils.sysdate(), true));

        Timestamp dataFineValidita;
        if (form.getStato()) {
            dataFineValidita = Utils.isNotEmpty(form.getDataFineValidita())
                    ? Utils.truncateTimestamp(Utils.toTimestamp(sdf.parse(form.getDataFineValidita())), false)
                    : null;
        } else {
            dataFineValidita = Utils.sysdate();
        }
        utenteDto.setDataFineValidita(dataFineValidita);

        utenteDto.setUtenteDto(operatore);
        utenteDto.setFlagConfiguratore("S");
        utenteDto = utenteLowDao.salva(utenteDto);
        UtenteDto finalUtenteDto = utenteDto;
        
        // Scrittura log Audit
        try {
			setLogAuditSOLNew(OperazioneEnum.INSERT, ConstantsWebApp.KEY_OPER_INSERIMENTO_UTENTE, form.getCf(), 
					uuid, finalUtenteDto.getId().toString(), ConstantsWebApp.INSERIMENTO_UTENTE, data);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

        
        List<RuoloUtenteDto> ruoloUtenteDtoList = new ArrayList<>();
        if (form.getRuoli() != null || form.getRuoliFromOpessan() != null) {
        	List<String> ruoliId = form.getRuoli() != null ? form.getRuoli().stream().filter(Utils::isNotEmpty).collect(Collectors.toList()) : Collections.emptyList();
        	List<String> idsFromOpessan = form.getRuoliFromOpessan() != null ? 
        			form.getRuoliFromOpessan().stream().filter(Utils::isNotEmpty)
        			.map(e -> e.substring(0, e.length()-2)).collect(Collectors.toList()) : Collections.emptyList();
        	if(!ruoliId.isEmpty()) {        		
        		ruoloUtenteDtoList = ruoliId.stream().map(idRuolo -> {
        			String id = idRuolo;
        			RuoloUtenteDto ruoloUtenteDto = new RuoloUtenteDto();
        			if(idsFromOpessan.contains(id))  {
        				ruoloUtenteDto.setColFonteId(fonte != null ? fonte.getFonteId().intValue() : null);
        			}
        			RuoloDto ruolo = ruoloLowDao.findByPrimaryId(Long.parseLong(id));
        			ruoloUtenteDto.setRuoloDto(ruolo);
        			ruoloUtenteDto.setUtenteDto(finalUtenteDto);
        			ruoloUtenteDto.setCfOperatore(cfOperatore);
        			ruoloUtenteDto.setDataInizioValidita(Utils.truncateTimestamp(Utils.sysdate(), true));
        			ruoloUtenteDto.setDataAggiornamento(Utils.sysdate());
        			ruoloUtenteDto.setFlagConfiguratore("S");
        			return ruoloUtenteLowDao.insert(ruoloUtenteDto);
        		}).collect(Collectors.toList());
        	}
        }

        List<UtenteCollocazioneDto> utenteCollocazioneDtoList = new ArrayList<>();
        if (form.getCollocazioni() != null || form.getCollocazioniFromOpessan() != null) {
        	List<String> collId = form.getCollocazioni() != null ? form.getCollocazioni().stream().filter(Utils::isNotEmpty).collect(Collectors.toList()) : Collections.emptyList();
        	List<String> collIdsFromOpessan = form.getCollocazioniFromOpessan() != null ? 
        			form.getCollocazioniFromOpessan().stream().filter(Utils::isNotEmpty)
        			.map(e -> e.substring(0, e.length()-2)).collect(Collectors.toList()) : Collections.emptyList();
        	if(!collId.isEmpty()) {        		
        		collId.forEach(idCollocazione -> {
        			Long id = null;
        			try {
        				id = Long.parseLong(idCollocazione);
        			} catch (NumberFormatException e) {
        				log.error("ERROR: Salvataggio Utente, collocazioni - ", e);
        			}
        			if (id != null) {
        				UtenteCollocazioneDto utenteCollocazioneDto = new UtenteCollocazioneDto();
        				if(collIdsFromOpessan.contains(idCollocazione)) {
        					utenteCollocazioneDto.setColFonteId(fonte != null ? fonte.getFonteId().intValue() : null);
        				}
        				CollocazioneDto collocazione = collocazioneLowDao.findByPrimaryId(id);
        				utenteCollocazioneDto.setUtenteDto(finalUtenteDto);
        				utenteCollocazioneDto.setFlagConfiguratore("S");
        				utenteCollocazioneDto.setDataInizioValidita(Utils.truncateTimestamp(Utils.sysdate(), true));
        				utenteCollocazioneDto.setCollocazioneDto(collocazione);
        				utenteCollocazioneDto.setCfOperatore(cfOperatore);
        				utenteCollocazioneDto.setDataAggiornamento(Utils.sysdate());
        				utenteCollocazioneDtoList.add(utenteCollocazioneLowDao.insert(utenteCollocazioneDto));
        			}
        		});
        	}
        	if(!collIdsFromOpessan.isEmpty()) {
        		collIdsFromOpessan.forEach(idFromOpessan -> {
        			if(!collId.contains(idFromOpessan)) {
        				Long id = null;
            			try {
            				id = Long.parseLong(idFromOpessan);
            			} catch (NumberFormatException e) {
            				log.error("ERROR: Salvataggio Utente, collocazioni - ", e);
            			}
            			if (id != null) {
	        				UtenteCollocazioneDto utenteCollocazioneDto = new UtenteCollocazioneDto();
	        				CollocazioneDto collocazione = collocazioneLowDao.findByPrimaryId(id);
	        				utenteCollocazioneDto.setUtenteDto(finalUtenteDto);
	        				utenteCollocazioneDto.setFlagConfiguratore("S");
	        				utenteCollocazioneDto.setDataInizioValidita(Utils.truncateTimestamp(Utils.sysdate(), true));
	        				utenteCollocazioneDto.setCollocazioneDto(collocazione);
	        				utenteCollocazioneDto.setCfOperatore(cfOperatore);
	        				utenteCollocazioneDto.setDataAggiornamento(Utils.sysdate());
	        				utenteCollocazioneDto.setColFonteId(fonte != null ? fonte.getFonteId().intValue() : null);
	        				utenteCollocazioneDtoList.add(utenteCollocazioneLowDao.insert(utenteCollocazioneDto));
            			}
        			}
        		});
        	}
        }

        List<UtenteCollocazioneDto> finalUtenteCollocazioneDtoList = utenteCollocazioneDtoList;
        List<RuoloUtenteDto> finalRuoloUtenteDtoList = ruoloUtenteDtoList;
        
        if(finalRuoloUtenteDtoList != null && !finalRuoloUtenteDtoList.isEmpty()) {
        	String roleKeyOper = finalRuoloUtenteDtoList.stream().map(RuoloUtenteDto::getId).map(String::valueOf).collect(Collectors.joining(","));
        	// Scrittura log Audit
            try {
    			setLogAuditSOLNew(OperazioneEnum.INSERT, ConstantsWebApp.KEY_OPER_ASSEGNA_RUOLO, form.getCf(), 
    					uuid, roleKeyOper, ConstantsWebApp.ASSEGNA_RUOLO, data);
    		} catch (Exception e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
        }
        
        if(finalUtenteCollocazioneDtoList != null && !finalUtenteCollocazioneDtoList.isEmpty()) {
        	String collKeyOper = finalUtenteCollocazioneDtoList.stream().map(UtenteCollocazioneDto::getId_utecol).map(String::valueOf).collect(Collectors.joining(","));
        	// Scrittura log Audit
            try {
    			setLogAuditSOLNew(OperazioneEnum.INSERT, ConstantsWebApp.KEY_OPER_ASSEGNA_COLLOCAZIONE, form.getCf(), 
    					uuid, collKeyOper, ConstantsWebApp.ASSEGNA_COLLOCAZIONE, data);
    		} catch (Exception e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
        }
        
        if (form.getProfiliSol() != null && checkProfiliSol(form.getProfiliSol())) {
            //ruoloUtenteDtoList.forEach(ruoloUtenteDto ->
                    form.getProfiliSol().forEach(sol -> {
                        String[] split = sol.split("\\|");

                        long idApplicazione;
                        long idCollocazione;
                        long idFunz;
                        long idRuolo;

                        try {
                            idApplicazione = Long.parseLong(split[0]);
                            idCollocazione = Long.parseLong(split[1]);
                            idFunz = Long.parseLong(split[2]);
                            idRuolo = Long.parseLong(split[3]);
                        } catch (NumberFormatException e) {
                            log.error("ERROR: Salvataggio Utente - ", e);
                            return;
                        }

                        FunzionalitaDto byPrimaryId = funzionalitaLowDao.findByPrimaryId(idFunz);

                        AbilitazioneDto abilitazioneDto = new AbilitazioneDto();
                        abilitazioneDto.setCfOperatore(cfOperatore);
                        abilitazioneDto.setDataInizioValidita(Utils.truncateTimestamp(Utils.sysdate(), true));
                        if (split.length > 4) {
                            try {
                                abilitazioneDto.setDataFineValidita(Utils.truncateTimestamp(Utils.toTimestamp(sdf.parse(split[4])), false));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        Optional<RuoloUtenteDto> optionalRuoloUtenteDto = finalRuoloUtenteDtoList.stream()
                                .filter(ruoloUtenteDto -> ruoloUtenteDto.getRuoloDto().getId().equals(idRuolo))
                                .findFirst();
                        optionalRuoloUtenteDto.ifPresent(abilitazioneDto::setRuoloUtenteDto);
                        //abilitazioneDto.setRuoloUtenteDto(ruoloUtenteDto);

                        Long finalIdCollocazione = idCollocazione;
                        Optional<UtenteCollocazioneDto> optionalUtenteCollocazioneDto = finalUtenteCollocazioneDtoList.stream()
                                .filter(utenteCollocazioneDto -> utenteCollocazioneDto.getCollocazioneDto().getColId().equals(finalIdCollocazione))
                                .findFirst();

                        optionalUtenteCollocazioneDto.ifPresent(abilitazioneDto::setUtenteCollocazioneDto);

                        abilitazioneDto.setApplicazioneDto(applicazioneLowDao.findByPrimaryId(idApplicazione));
                        abilitazioneDto.setTreeFunzionalitaDto(Utils.getFirstRecord(treeFunzionalitaLowDao.findByFunzionalitaDto(byPrimaryId)));
                        abilitazioneDto.setCodiceAbilitazione(UUID.randomUUID().toString());
                        abilitazioneDto.setDataAggiornamento(Utils.sysdate());
                        AbilitazioneDto abilitazione = abilitazioneLowDao.insert(abilitazioneDto);
                        
                        ////////////////////////////////////////////////////
                        List<PreferenzaDto> preferenze = aggiornaPreferenza(abilitazioneDto);
                        
                        String keyOperAbil = abilitazione.getId().toString();
                        if(preferenze != null && !preferenze.isEmpty()) {
                        	keyOperAbil += "-" + preferenze.stream().map(PreferenzaDto::getId).map(String::valueOf).collect(Collectors.joining(",")); 
                        }
                        
                        // Scrittura log Audit
                        try {
                			setLogAuditSOLNew(OperazioneEnum.INSERT, ConstantsWebApp.KEY_OPER_ASSEGNA_SOL, form.getCf(), 
                					uuid, keyOperAbil, ConstantsWebApp.ASSEGNA_SOL, data);
                		} catch (Exception e1) {
                			// TODO Auto-generated catch block
                			e1.printStackTrace();
                		}
                        
                        if(abilitazioneDto.getApplicazioneDto().getCodice().equalsIgnoreCase(Constants.APPLICATION_CODE)) {
                        	aggiornaVisibilitaAzienda(finalUtenteDto.getId(), operatore.getCodiceFiscale(), abilitazioneDto.getUtenteCollocazioneDto().getCollocazioneDto().getColCodAzienda(),abilitazioneDto.getDataFineValidita());
                        }
                        
                        if(abilitazioneDto.getApplicazioneDto().getInvioMailAura() != null && abilitazioneDto.getApplicazioneDto().getInvioMailAura()) {
                        	utenteSalvato.addAbilitazioneAura(abilitazioneDto);
                       	}
                    });
           // );

        }
        utenteSalvato.setUtenteDto(finalUtenteDto);
        return utenteSalvato;
    }

    //@Transactional(propagation = Propagation.REQUIRES_NEW)
    public SalvataggioUtenteModel modificaUtente(Long idUtente, FormNuovoUtente form, List<String> listaProfiliSolACuiUtenteAbilitato, Data data, String uuid) throws ParseException {
    	SalvataggioUtenteModel utenteModificato = new SalvataggioUtenteModel();
    	UtenteDto utentePresenteSuDb = utenteLowDao.findByPrimaryId(idUtente);
        if (utentePresenteSuDb != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(ConstantsWebApp.DATE_PATTERN);
            UtenteDto operatore = Utils.getFirstRecord(utenteLowDao.findByCodiceFiscale(data.getUtente().getCodiceFiscale()));
            // AGGIORNAMENTO DATI ANAGRAFICI
            UtenteDto utenteDaAggiornare = utentePresenteSuDb;
            // nel caso in cui l'utente non sia stato inserito dal Configuratore Ã¨ possibile modificare solo i campi indirizzo_email e numero_telefono
            // altrimenti Ã¨ possibile modificarli tutti
            if ("S".equals(utentePresenteSuDb.getFlagConfiguratore())) {
                utenteDaAggiornare.setNome(form.getNome());
                utenteDaAggiornare.setCognome(form.getCognome());
                utenteDaAggiornare.setDataNascita(Utils.toTimestamp(form.getDataDiNascita() != null ? sdf.parse(form.getDataDiNascita()) : null));
                utenteDaAggiornare.setCodiceFiscale(form.getCf());
                utenteDaAggiornare.setComuneNascita(form.getComuneDiNascita());
                utenteDaAggiornare.setProvincia(form.getProvinciaDiNascita());
                utenteDaAggiornare.setSesso(form.getSesso());
            }
            Timestamp dataFineValidita;
            if (form.getStato()) {
                dataFineValidita = Utils.isNotEmpty(form.getDataFineValidita())
                        ? Utils.truncateTimestamp(Utils.toTimestamp(sdf.parse(form.getDataFineValidita())), false)
                        : null;
            } else {
                dataFineValidita = Utils.sysdate();
            }
            utenteDaAggiornare.setDataFineValidita(dataFineValidita);

            utenteDaAggiornare.setUtenteDto(operatore);
            utenteDaAggiornare.setTipoContrattoDto(form.getContratto() != null ? contrattoLowDao.findByPrimaryId(form.getContratto()) : null);
            utenteDaAggiornare.setIndirizzoMail(form.getEmail());
            utenteDaAggiornare.setNumeroTelefono(form.getTelefono());
            utenteDaAggiornare.setDataAggiornamento(Utils.sysdate());
            utenteLowDao.modifica(utenteDaAggiornare);
            // Scrittura log Audit
            try {
    			setLogAuditSOLNew(OperazioneEnum.UPDATE, ConstantsWebApp.KEY_OPER_MODIFICA_DATI_UTENTE_SALVA, form.getCf(), 
    					uuid, utenteDaAggiornare.getId().toString(), ConstantsWebApp.MODIFICA_DATI_UTENTE_SALVA, data);
    		} catch (Exception e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}

            List<Long> listaCollocazioniACuiOperatoreAbilitato = getCollocazioniAbilitate(data, Constants.APPLICATION_CODE);

            List<RuoloUtenteDto> listaRuoliUtentePresentiSuDb = utentePresenteSuDb.getRuoloUtenteList().stream()
                    .filter(ruoloUtenteDto -> Utils.isValidTime(ruoloUtenteDto.getDataInizioValidita(), ruoloUtenteDto.getDataFineValidita()))
                    .collect(Collectors.toList());

            List<String> listaProfiliSol = getDistinctSol(form.getProfiliSol());
            List<UtenteCollocazioneDto> listaCollocazioniUtentePresentiSuDb = utentePresenteSuDb.getUtenteCollocazioneList()
                    .stream().filter(utenteCollocazioneDto -> Utils.isValidTime(utenteCollocazioneDto.getDataInizioValidita(), utenteCollocazioneDto.getDataFineValidita()))
                    .collect(Collectors.toList());
            // AGGIORNAMENTO ABILITAZIONI
            // aggiorno le abilitazioni presenti sul db
            if (!Utils.listIsNullOrEmpty(listaProfiliSol)) {
                aggiornaAbilitazioni(idUtente, listaProfiliSol, operatore.getCodiceFiscale(), sdf, utentePresenteSuDb.getCodiceFiscale(), listaCollocazioniACuiOperatoreAbilitato, data, form, utenteModificato,uuid);
            } else {
                boolean isSuperUser = FunzionalitaEnum.SUPERUSERCONF_PROF.getValue().equalsIgnoreCase(data.getUtente().getProfilo());
                List<RuoloUtenteDto> listaRuoliUtente = listaRuoliUtentePresentiSuDb.stream()
                        .filter(ruoloUtenteDto -> ruoloUtenteDto.getRuoloDto().getFlagConfiguratore() != null && ruoloUtenteDto.getRuoloDto().getFlagConfiguratore().equals("S"))
                        .collect(Collectors.toList());

                listaRuoliUtente.forEach(ruoloUtenteDto -> {
                    List<AbilitazioneDto> listaAbilitazioniAssociateARuoloUtenteDaEliminareLogicamente = ruoloUtenteDto.getAbilitazioneList()
                            .stream().filter(abilitazioneDto -> Utils.isValidTime(abilitazioneDto.getDataInizioValidita(), abilitazioneDto.getDataFineValidita()))
                            .collect(Collectors.toList());
                    if (!listaAbilitazioniAssociateARuoloUtenteDaEliminareLogicamente.isEmpty()
                            && (isSuperUser || listaAbilitazioniAssociateARuoloUtenteDaEliminareLogicamente.stream()
                            .allMatch(abilitazioneDto -> listaCollocazioniACuiOperatoreAbilitato.contains(abilitazioneDto.getUtenteCollocazioneDto().getCollocazioneDto().getColId())))) {
                        listaAbilitazioniAssociateARuoloUtenteDaEliminareLogicamente.forEach(abilitazioneAssociataARuoloDaEliminareLogicamente -> {
                                modificaAbilitazione(abilitazioneAssociataARuoloDaEliminareLogicamente, operatore.getCodiceFiscale(), true);
                                form.setMaildaInviare(true);
                                ////////////////////////////////////////////////////////////////////////////////////////////
                                List<PreferenzaDto> preferenze = cancellaPreferenza(utentePresenteSuDb.getCodiceFiscale(), abilitazioneAssociataARuoloDaEliminareLogicamente);
                                
                                if(abilitazioneAssociataARuoloDaEliminareLogicamente.getApplicazioneDto().getCodice().equalsIgnoreCase(Constants.APPLICATION_CODE)) {
                                  	aggiornaVisibilitaAzienda(idUtente, operatore.getCodiceFiscale(), abilitazioneAssociataARuoloDaEliminareLogicamente.getUtenteCollocazioneDto().getCollocazioneDto().getColCodAzienda(),Utils.sysdate());
                                }
                                
                                if(abilitazioneAssociataARuoloDaEliminareLogicamente.getApplicazioneDto().getInvioMailAura() != null && abilitazioneAssociataARuoloDaEliminareLogicamente.getApplicazioneDto().getInvioMailAura()) {
                               		utenteModificato.addDisabilitazioneAura(abilitazioneAssociataARuoloDaEliminareLogicamente);  
                               	}
                                
                          	  String keyOperAbil=abilitazioneAssociataARuoloDaEliminareLogicamente.getId().toString();
                           	  if(preferenze!=null && !preferenze.isEmpty()) {
                           		  keyOperAbil+="-"+preferenze.stream().map(PreferenzaDto::getId).map(String::valueOf).collect(Collectors.joining(","));
                           	  }
                          	// Scrittura log Audit
                              try {
                      			setLogAuditSOLNew(OperazioneEnum.UPDATE, ConstantsWebApp.KEY_OPER_MODIFICA_SOL, form.getCf(), 
                      					uuid, keyOperAbil, ConstantsWebApp.MODIFICA_SOL, data);
                      		} catch (Exception e1) {
                      			// TODO Auto-generated catch block
                      			e1.printStackTrace();
                      		}

                        });
                    }
                });
            }

            List<Long> listaCollocazioniModificate = getIdFromAbilitazioni(form.getCollocazioni());
            // AGGIORNAMENTO COLLOCAZIONI (se l'utente non Ã¨ stato inserito dal Configuratore non sono modificabili)
            List<UtenteCollocazioneDto> finalListaCollocazioniUtente = new ArrayList<>();
            if (!Utils.listIsNullOrEmpty(listaCollocazioniModificate)) {
                finalListaCollocazioniUtente = aggiornaCollocazioniUtente(listaCollocazioniUtentePresentiSuDb, listaCollocazioniModificate, utenteDaAggiornare, operatore.getCodiceFiscale(), data,uuid);
          
            } else {
                List<UtenteCollocazioneDto> listaCollocazioniUtenteDaEliminareLogicamente = recuperaListaCollocazioniACuiUtenteAbilitato(listaCollocazioniUtentePresentiSuDb, idUtente, data);
                if (!Utils.listIsNullOrEmpty(listaCollocazioniUtenteDaEliminareLogicamente)) {
                    eliminaLogicamenteCollocazioniUtente(listaCollocazioniUtenteDaEliminareLogicamente, operatore.getCodiceFiscale());
                    String collEliminateKeyOper= listaCollocazioniUtenteDaEliminareLogicamente.stream().map(UtenteCollocazioneDto::getId_utecol).map(String::valueOf).collect(Collectors.joining(","));
                	// Scrittura log Audit
                    try {
            			setLogAuditSOLNew(OperazioneEnum.UPDATE, ConstantsWebApp.KEY_OPER_RIMUOVI_COLLOCAZIONE, form.getCf(), 
            					uuid, collEliminateKeyOper, ConstantsWebApp.RIMUOVI_COLLOCAZIONE, data);
            		} catch (Exception e1) {
            			// TODO Auto-generated catch block
            			e1.printStackTrace();
            		}
                }
            }

            // AGGIORNAMENTO RUOLI
            List<RuoloUtenteDto> finalListaRuoliUtente = new ArrayList<>();
            if (!Utils.listIsNullOrEmpty(form.getRuoli())) {
                List<String> ruoliId = form.getRuoli().stream().filter(Utils::isNotEmpty).collect(Collectors.toList());
                if (!ruoliId.isEmpty()) {
                    List<Long> listaRuoliModificati = getIdFromAbilitazioni(form.getRuoli());
                    finalListaRuoliUtente = aggiornaRuoliUtente(listaRuoliUtentePresentiSuDb, listaRuoliModificati, utenteDaAggiornare,
                            operatore.getCodiceFiscale(), listaCollocazioniACuiOperatoreAbilitato, data,uuid);
                } else {
                    finalListaRuoliUtente = listaRuoliUtentePresentiSuDb;
                }
            } else {
                List<RuoloUtenteDto> listaRuoliUtenteDaEliminareLogicamente = listaRuoliUtentePresentiSuDb.stream()
                        .filter(ruoloUtenteDto -> ruoloUtenteDto.getRuoloDto().getFlagConfiguratore() != null && ruoloUtenteDto.getRuoloDto().getFlagConfiguratore().equals("S"))
                        .collect(Collectors.toList());
                eliminaLogicamenteRuoliUtente(listaRuoliUtenteDaEliminareLogicamente, operatore.getCodiceFiscale(), listaCollocazioniACuiOperatoreAbilitato, data);
                if(listaRuoliUtenteDaEliminareLogicamente!=null && !listaRuoliUtenteDaEliminareLogicamente.isEmpty()) {
                    String ruoliEliminatiKeyOper= listaRuoliUtenteDaEliminareLogicamente.stream().map(RuoloUtenteDto::getId).map(String::valueOf).collect(Collectors.joining(","));
                	// Scrittura log Audit
                    try {
            			setLogAuditSOLNew(OperazioneEnum.UPDATE, ConstantsWebApp.KEY_OPER_RIMUOVI_RUOLO, form.getCf(), 
            					uuid, ruoliEliminatiKeyOper, ConstantsWebApp.RIMUOVI_RUOLO, data);
            		} catch (Exception e1) {
            			// TODO Auto-generated catch block
            			e1.printStackTrace();
            		}
                }
            }

            // inserisco le nuove abilitazioni
            if (!finalListaCollocazioniUtente.isEmpty() && !finalListaRuoliUtente.isEmpty()) {
                 List<UtenteCollocazioneDto> listaNuoveCollocazioni = finalListaCollocazioniUtente;
                List<RuoloUtenteDto> listaNuoviRuoli = finalListaRuoliUtente;
                //finalListaRuoliUtente.forEach(ruoloUtenteDto -> {
                    // recupero le nuove abilitazioni
                	List<SolModel> profiliSolModelAbilitati = listaProfiliSolACuiUtenteAbilitato.stream().map(p -> new SolModel(p)).collect(Collectors.toList());
                	
                	listaProfiliSol.removeIf(p -> {
                		SolModel pModel = new SolModel(p);
                		return profiliSolModelAbilitati.contains(pModel);
                	} );
                	
                    List<AbilitazioneDto> listaNuoveAbilitazioniUtente = convertListaStringaProfiliToListaAbilitazioniDto(listaProfiliSol, listaNuoveCollocazioni, listaNuoviRuoli, sdf, true);

                    // se ruoloUtenteDto Ã¨ un nuovo ruolo, quindi non presente sul db, recupero anche gli altri profili a cui l'utente Ã¨ abilitato
                    Set<Long> listaRuoliIdPresentiSuDb = listaRuoliUtentePresentiSuDb.stream().map(RuoloUtenteDto::getId).collect(Collectors.toSet());
                    Set<Long> listaNuoviRuoliId = listaNuoviRuoli.stream().map(RuoloUtenteDto::getId).collect(Collectors.toSet());
                    List<AbilitazioneDto> listaAbilitazioniPresentiSuDb = new ArrayList<>();
                    if (listaRuoliIdPresentiSuDb.stream().noneMatch(listaNuoviRuoliId::contains))
                        listaAbilitazioniPresentiSuDb = convertListaStringaProfiliToListaAbilitazioniDto(listaProfiliSol, listaNuoveCollocazioni, listaNuoviRuoli, sdf, false);

                    List<AbilitazioneDto> finalListaAbilitazioniDto = new ArrayList<>();
                    finalListaAbilitazioniDto.addAll(listaNuoveAbilitazioniUtente);
                    finalListaAbilitazioniDto.addAll(listaAbilitazioniPresentiSuDb);
                    if (!Utils.listIsNullOrEmpty(finalListaAbilitazioniDto)) {
                        finalListaAbilitazioniDto.forEach(abilitazioneDto -> {
                        	form.setMaildaInviare(true);
                            AbilitazioneDto nuovoRecordAbilitazione = new AbilitazioneDto();
                            AbilitazioneDto popolaTabellaAbilitazione = popolaTabellaAbilitazione(nuovoRecordAbilitazione, abilitazioneDto, operatore.getCodiceFiscale());
                            
                            ////////////////////////////////////////////////////
                            List<PreferenzaDto> aggiornaPreferenza = aggiornaPreferenza(nuovoRecordAbilitazione);

                            if(abilitazioneDto.getApplicazioneDto().getCodice().equalsIgnoreCase(Constants.APPLICATION_CODE)) {
                            	aggiornaVisibilitaAzienda(idUtente, operatore.getCodiceFiscale(), abilitazioneDto.getUtenteCollocazioneDto().getCollocazioneDto().getColCodAzienda(),abilitazioneDto.getDataFineValidita());
                            }
                            
                            if(abilitazioneDto.getApplicazioneDto().getInvioMailAura() != null && abilitazioneDto.getApplicazioneDto().getInvioMailAura()) {
                           		utenteModificato.addAbilitazioneAura(abilitazioneDto);
                           	}
                            String keyOperAbil=popolaTabellaAbilitazione.getId().toString();
                         	  if(aggiornaPreferenza!=null && !aggiornaPreferenza.isEmpty()) {
                         		  keyOperAbil+="-"+aggiornaPreferenza.stream().map(PreferenzaDto::getId).map(String::valueOf).collect(Collectors.joining(","));
                         	  }
                        	// Scrittura log Audit
                            try {
                    			setLogAuditSOLNew(OperazioneEnum.INSERT, ConstantsWebApp.KEY_OPER_ASSEGNA_SOL, form.getCf(), 
                    					uuid, keyOperAbil, ConstantsWebApp.ASSEGNA_SOL, data);
                    		} catch (Exception e1) {
                    			// TODO Auto-generated catch block
                    			e1.printStackTrace();
                    		}
                        });
                    }
                //});
            }
        }
        utenteModificato.setUtenteDto(utentePresenteSuDb);
        return utenteModificato;
    }

	private List<PreferenzaDto> aggiornaPreferenza(AbilitazioneDto abilitazioneDto) {
		List<PreferenzaDto> preferenze = new ArrayList<PreferenzaDto>();
		UtenteDto utenteDto = abilitazioneDto.getRuoloUtenteDto().getUtenteDto();
		RuoloDto ruoloDto = abilitazioneDto.getRuoloUtenteDto().getRuoloDto();
		UtenteCollocazioneDto uteColl = utenteCollocazioneLowDao.findByUtenteDto(utenteDto).stream()
				.filter(coll -> coll.getId_utecol().equals(abilitazioneDto.getUtenteCollocazioneDto().getId_utecol()))
				.findFirst().orElse(null);
		CollocazioneDto collocazioneDto = uteColl != null ? uteColl.getCollocazioneDto() : null;
//		if (uteColl != null)
//			collocazioneDto = uteColl.getCollocazioneDto();
		ApplicazioneDto applicazioneDto = abilitazioneDto.getApplicazioneDto();
		Timestamp fineValAbilitazione = abilitazioneDto.getDataFineValidita() != null
				? abilitazioneDto.getDataFineValidita()
				: null;
		Timestamp fineValAbilitazioneTemp = fineValAbilitazione;
		
		// record utente-ruolo
		Collection<AbilitazioneDto> abilitazioniByRuoloUtente = abilitazioneLowDao.findByRuoloUtenteDto(abilitazioneDto.getRuoloUtenteDto());
		if(fineValAbilitazioneTemp != null) {		
			if(abilitazioniByRuoloUtente.stream().anyMatch(a -> a.getDataFineValidita() == null)) {
				fineValAbilitazione = null;
			} else {
				Timestamp fineVal = abilitazioniByRuoloUtente.stream()
						.filter(a -> a.getDataFineValidita().after(fineValAbilitazioneTemp) )
						.map(AbilitazioneDto::getDataFineValidita).max(Timestamp::compareTo).orElse(fineValAbilitazioneTemp);
				if(fineVal != null) fineValAbilitazione = fineVal;
			}
		}
		PreferenzaDto preferenzaRuolo = preferenzaLowDao.getPreferenzaRuolo(utenteDto, ruoloDto);
		preferenzaRuolo = insertUpdatePreferenza(abilitazioneDto, utenteDto, ruoloDto, null, null, fineValAbilitazione, preferenzaRuolo);
		preferenze.add(preferenzaRuolo);
		
		// record utente-ruolo-collocazione
		if(fineValAbilitazioneTemp != null) {
			if(abilitazioniByRuoloUtente.stream()
					.filter(a -> a.getUtenteCollocazioneDto().getId_utecol() == abilitazioneDto.getUtenteCollocazioneDto().getId_utecol())
					.anyMatch(a -> a.getDataFineValidita() == null)) {
				fineValAbilitazione = null;
			} else {
				Timestamp fineVal = abilitazioniByRuoloUtente.stream()
						.filter(a -> a.getUtenteCollocazioneDto().getId_utecol() == abilitazioneDto.getUtenteCollocazioneDto().getId_utecol())
						.filter(a -> a.getDataFineValidita().after(fineValAbilitazioneTemp) )
						.map(AbilitazioneDto::getDataFineValidita).max(Timestamp::compareTo).orElse(fineValAbilitazioneTemp);
				if(fineVal != null) fineValAbilitazione = fineVal;
			}
		}
		PreferenzaDto preferenzaRuoloCollocazione = preferenzaLowDao.getPreferenzaRuoloCollocazione(utenteDto, ruoloDto, collocazioneDto);
		preferenzaRuoloCollocazione = insertUpdatePreferenza(abilitazioneDto, utenteDto, ruoloDto, collocazioneDto, null, fineValAbilitazione, preferenzaRuoloCollocazione);
		preferenze.add(preferenzaRuoloCollocazione);
		
		// record utente-applicazione
		List<AbilitazioneDto> abilitazioniByApplicazione = abilitazioneLowDao.findByCodiceFiscaleAndApplicazioneAndDateValidita(utenteDto.getCodiceFiscale(), applicazioneDto.getCodice());
		if(fineValAbilitazioneTemp != null) {	
			if(abilitazioniByApplicazione.stream().anyMatch(a -> a.getDataFineValidita() == null)) {
				fineValAbilitazione = null;
			} else {
				Timestamp fineVal = abilitazioniByApplicazione.stream()
						.filter(a -> a.getDataFineValidita().after(fineValAbilitazioneTemp) )
						.map(AbilitazioneDto::getDataFineValidita).max(Timestamp::compareTo).orElse(fineValAbilitazioneTemp);
				if(fineVal != null) fineValAbilitazione = fineVal;
			}
		}
		PreferenzaDto preferenzaApplicazione = preferenzaLowDao.getPreferenzaApplicazione(utenteDto, applicazioneDto);
		preferenzaApplicazione = insertUpdatePreferenza(abilitazioneDto, utenteDto, null, null, applicazioneDto, fineValAbilitazione, preferenzaApplicazione);
		preferenze.add(preferenzaApplicazione);
		
		return preferenze;
		/////////////////////////////////////////////////////////////////////
	}

	private PreferenzaDto insertUpdatePreferenza(AbilitazioneDto abilitazioneDto, UtenteDto utenteDto, RuoloDto ruoloDto, CollocazioneDto collocazioneDto,
			ApplicazioneDto applicazioneDto, Timestamp fineValAbilitazione, PreferenzaDto preferenza) {
		if (preferenza == null) {
			preferenza = new PreferenzaDto();
			preferenza.setUtente(utenteDto);
			preferenza.setRuoloDto(ruoloDto);
			preferenza.setCollocazioneDto(collocazioneDto);
			preferenza.setApplicazioneDto(applicazioneDto);
			preferenza.setDataInserimento(Utils.sysdate());
			preferenza.setDataInizioValidita(abilitazioneDto.getDataInizioValidita());
			preferenza.setDataFineValidita(
					abilitazioneDto.getDataFineValidita() != null ? abilitazioneDto.getDataFineValidita() : null);
			preferenza = preferenzaLowDao.insert(preferenza);
		} else {
			if(preferenza.getDataCancellazione() != null)
				preferenza.setDataInizioValidita(abilitazioneDto.getDataInizioValidita());
			preferenza.setDataAggiornamento(Utils.sysdate());
			preferenza.setDataFineValidita(fineValAbilitazione);
			preferenza.setDataCancellazione(null);
			preferenzaLowDao.update(preferenza);
		}
		return preferenza;
	}

    private List<String> getDistinctSol(List<String> listaProfiliSol) {
        List<String> distinctListaSol = new ArrayList<>();

        if (!Utils.listIsNullOrEmpty(listaProfiliSol)) {
            distinctListaSol = listaProfiliSol.stream().map(sol -> {
            	if(!Utils.isEmpty(sol)) {            		
            		String[] split = sol.split("\\|");
            		List<String> duplicates = listaProfiliSol.stream().filter(s -> {
            			String[] split2 = s.split("\\|");
            			return split[0].equals(split2[0]) && split[1].equals(split2[1])
            					&& split[2].equals(split2[2]) && split[3].equals(split2[3]) 
            					&& (split2.length > 4 && split.length < 4);
            		}).collect(Collectors.toList());
            		if (Utils.listIsNullOrEmpty(duplicates))
            			return sol;
            		else
            			return null;
            	} else  return null;
            }).filter(Objects::nonNull).collect(Collectors.toList());
        }

        return distinctListaSol;
    }

    private List<AbilitazioneDto> convertListaStringaProfiliToListaAbilitazioniDto(List<String> listaProfiliSol,
                                                                                   List<UtenteCollocazioneDto> listaCollocazioniUtente,
                                                                                   List<RuoloUtenteDto> listaRuoliUtente,
                                                                                   SimpleDateFormat sdf,
                                                                                   boolean flagExtractOnlyNewAbilitazioni) {
        AtomicReference<Long> idApplicazione = new AtomicReference<>();
        AtomicReference<Long> idCollocazione = new AtomicReference<>();
        AtomicReference<Long> idFunzioneNuova = new AtomicReference<>();
        AtomicReference<Long> idFunzioneVecchia = new AtomicReference<>();
        AtomicReference<Long> idRuolo = new AtomicReference<>();
        List<AbilitazioneDto> listaAbilitazioniDto = new ArrayList<>();
        
        if (!Optional.ofNullable(listaProfiliSol).map(List::isEmpty).orElse(true)) {
            listaAbilitazioniDto = listaProfiliSol.stream()
                    .map(profiloSol -> {
                        String[] split = profiloSol.split("\\|");
                        Timestamp dataFineValidita;

                        try {
                            idApplicazione.set(Long.parseLong(split[0]));
                            idCollocazione.set(Long.parseLong(split[1]));
                            idFunzioneNuova.set(Long.parseLong(split[2]));
                            idRuolo.set(Long.parseLong(split[3]));
                            dataFineValidita = split.length > 4 && !Utils.isEmpty(split[4])
                                    ? Utils.truncateTimestamp(Utils.toTimestamp(sdf.parse(split[4])), false)
                                    : null;
                            if (split.length > 5 && !Utils.isEmpty(split[5])) {
                                idFunzioneVecchia.set(Long.parseLong(split[5]));
                            } else {
                                idFunzioneVecchia.set(null);
                            }
                        } catch (IndexOutOfBoundsException | NumberFormatException | ParseException e) {
                            log.error("ERROR: Recupero nuove abilitazioni - ", e);
                            return null;
                        }

                        AbilitazioneDto abilitazioneDto = null;
                        if ((flagExtractOnlyNewAbilitazioni && idFunzioneVecchia.get() == null)
                                || (!flagExtractOnlyNewAbilitazioni && idFunzioneVecchia.get() != null)) {
                            abilitazioneDto = new AbilitazioneDto();
                            
                            Optional<RuoloUtenteDto> optionalRuoloUtenteDto = listaRuoliUtente.stream()
                                    .filter(ruoloUtenteDto -> ruoloUtenteDto.getRuoloDto().getId().equals(idRuolo.get()))
                                    .findFirst();
                            optionalRuoloUtenteDto.ifPresent(abilitazioneDto::setRuoloUtenteDto);
                            //abilitazioneDto.setRuoloUtenteDto(ruoloUtenteDto);

                            ApplicazioneDto applicazioneDto = applicazioneLowDao.findByPrimaryId(idApplicazione.get());
                            abilitazioneDto.setApplicazioneDto(applicazioneDto);

                            if (abilitazioneDto.getRuoloUtenteDto().getRuoloDto().getId().equals(idRuolo.get())
                                    && abilitazioneDto.getApplicazioneDto().getId().equals(idApplicazione.get())) {
                                abilitazioneDto.setDataFineValidita(dataFineValidita);
                                FunzionalitaDto funzionalitaDto = funzionalitaLowDao.findByPrimaryId(flagExtractOnlyNewAbilitazioni ? idFunzioneNuova.get() : idFunzioneVecchia.get());
                                abilitazioneDto.setTreeFunzionalitaDto(Utils.getFirstRecord(treeFunzionalitaLowDao.findByFunzionalitaDto(funzionalitaDto)));

                                Optional<UtenteCollocazioneDto> optionalUtenteCollocazioneDto = listaCollocazioniUtente.stream()
                                        .filter(utenteCollocazioneDto -> utenteCollocazioneDto.getCollocazioneDto().getColId().equals(idCollocazione.get()))
                                        .findFirst();
                                optionalUtenteCollocazioneDto.ifPresent(abilitazioneDto::setUtenteCollocazioneDto);
                            }
                        }
                        return abilitazioneDto;
                    }).filter(Objects::nonNull).collect(Collectors.toList());
        }
        return listaAbilitazioniDto;
    }

    public void aggiornaAbilitazioni(Long idUtente, List<String> listaProfiliSol, String codiceFiscaleOperatore,
                                     SimpleDateFormat sdf, String codiceFiscaleUtente, List<Long> listaCollocazioniACuiOperatoreAbilitato, 
                                     Data data,FormNuovoUtente form, SalvataggioUtenteModel utenteModificato,String uuid) {

        boolean isSuperUser = FunzionalitaEnum.SUPERUSERCONF_PROF.getValue().equalsIgnoreCase(data.getUtente().getProfilo());

        // recupero la lista dei SOL a cui l'utente Ã¨ abilitato presenti sul db ma non presenti nella nuova lista listaProfiliSol
        // se la lista che ottengo non Ã¨ vuota effettuo l'eliminazione logica delle abilitazioni associate all'utente
        List<AbilitazioneDto> listaAbilitazioniDtoDaEliminareLogicamente = recuperaListaSolDaEliminareLogicamente(idUtente, codiceFiscaleUtente, listaProfiliSol, data);
        if (!Utils.listIsNullOrEmpty(listaAbilitazioniDtoDaEliminareLogicamente)
                && (isSuperUser || listaAbilitazioniDtoDaEliminareLogicamente.stream().allMatch(abilitazioneDto -> listaCollocazioniACuiOperatoreAbilitato.contains(abilitazioneDto.getUtenteCollocazioneDto().getCollocazioneDto().getColId())))) {
            listaAbilitazioniDtoDaEliminareLogicamente.forEach(abilitazioneDaEliminareLogicamente -> {
            	
                    modificaAbilitazione(abilitazioneDaEliminareLogicamente, codiceFiscaleOperatore, true);
                    
                    form.setMaildaInviare(true);
                    
                    //////////////////////////////////////////////////////////////////////////////
                  List<PreferenzaDto> preferenze = cancellaPreferenza(codiceFiscaleUtente, abilitazioneDaEliminareLogicamente);

                  if(abilitazioneDaEliminareLogicamente.getApplicazioneDto().getCodice().equalsIgnoreCase(Constants.APPLICATION_CODE)) {
                  	aggiornaVisibilitaAzienda(idUtente, codiceFiscaleOperatore, abilitazioneDaEliminareLogicamente.getUtenteCollocazioneDto().getCollocazioneDto().getColCodAzienda(),Utils.sysdate());
                  }
               	  
               	  if(abilitazioneDaEliminareLogicamente.getApplicazioneDto().getInvioMailAura() != null && abilitazioneDaEliminareLogicamente.getApplicazioneDto().getInvioMailAura()) {
               		utenteModificato.addDisabilitazioneAura(abilitazioneDaEliminareLogicamente);  
               	  }
               	  
               	  String keyOperAbil=abilitazioneDaEliminareLogicamente.getId().toString();
               	  if(preferenze!=null && !preferenze.isEmpty()) {
               		  keyOperAbil+="-"+preferenze.stream().map(PreferenzaDto::getId).map(String::valueOf).collect(Collectors.joining(","));
               	  }
              	// Scrittura log Audit
                  try {
          			setLogAuditSOLNew(OperazioneEnum.UPDATE, ConstantsWebApp.KEY_OPER_MODIFICA_SOL, form.getCf(), 
          					uuid, keyOperAbil, ConstantsWebApp.MODIFICA_SOL, data);
          		} catch (Exception e1) {
          			// TODO Auto-generated catch block
          			e1.printStackTrace();
          		}
            });
            
         
          
        }
        
        

        // modifico le abilitazioni presenti sul db
        listaProfiliSol.forEach(profiloSol -> {
            String[] split = profiloSol.split("\\|");
            Long idApplicazione;
            Long idCollocazione;
            Long idFunzioneNuova;
            Long idRuolo;
            Timestamp dataFineValidita;
            Long idFunzioneVecchia;

            try {
                idApplicazione = Long.parseLong(split[0]);
                idCollocazione = Long.parseLong(split[1]);
                idFunzioneNuova = Long.parseLong(split[2]);
                idRuolo  = Long.parseLong(split[3]);
                dataFineValidita = split.length > 4 && !Utils.isEmpty(split[4])
                        ? Utils.truncateTimestamp(Utils.toTimestamp(sdf.parse(split[4])), false)
                        : null;
                idFunzioneVecchia = split.length > 5 && !Utils.isEmpty(split[5]) ? Long.parseLong(split[5]) : null;
            } catch (IndexOutOfBoundsException | NumberFormatException | ParseException e) {
                log.error("ERROR: Aggiornamento abilitazioni - ", e);
                return;
            }

            //if (idFunzioneVecchia != null) {
                List<AbilitazioneDto> listaAbilitazioniDaModificare = abilitazioneLowDao
                        .findAbilitazioniByIdApplicazioneAndIdCollocazioneAndIdFunzioneAndCodiceFiscaleUtenteAndIdRuolo(idApplicazione, idCollocazione, idFunzioneNuova, codiceFiscaleUtente, idRuolo);
                if (!Utils.listIsNullOrEmpty(listaAbilitazioniDaModificare)) {
                    listaAbilitazioniDaModificare.forEach(abilitazioneDaModificare -> {
                        AbilitazioneDto abilitazioneDto = abilitazioneLowDao.findByPrimaryId(abilitazioneDaModificare.getId());
                        Timestamp vecchiaFineValidita= abilitazioneDto.getDataFineValidita()!= null ? new java.sql.Timestamp(abilitazioneDto.getDataFineValidita().getTime()) : new java.sql.Timestamp(new Date(0).getTime());
                        Timestamp attualefineValidita= dataFineValidita != null ? new java.sql.Timestamp(dataFineValidita.getTime()) : new java.sql.Timestamp(new Date(0).getTime());;
                        abilitazioneDaModificare.setDataFineValidita(dataFineValidita);
                        if(!abilitazioneDto.getApplicazioneDto().equals(abilitazioneDaModificare.getApplicazioneDto()) 
                    		|| !abilitazioneDto.getDataInizioValidita().equals(abilitazioneDaModificare.getDataInizioValidita()) 
                    		|| !vecchiaFineValidita.equals(attualefineValidita)){
                        	
                        	form.setMaildaInviare(true);
                        	modificaAbilitazione(abilitazioneDaModificare, codiceFiscaleOperatore, false);
                        	List<PreferenzaDto> preferenze = aggiornaPreferenza(abilitazioneDaModificare);
                        	
                        	if(abilitazioneDaModificare.getApplicazioneDto().getCodice().equalsIgnoreCase(Constants.APPLICATION_CODE)) {
                        		aggiornaVisibilitaAzienda(idUtente, codiceFiscaleOperatore, abilitazioneDaModificare.getUtenteCollocazioneDto().getCollocazioneDto().getColCodAzienda(),abilitazioneDaModificare.getDataFineValidita());
                        	}
                        	
                        	if(abilitazioneDaModificare.getApplicazioneDto().getInvioMailAura() != null && abilitazioneDaModificare.getApplicazioneDto().getInvioMailAura()) {
                        		utenteModificato.addModificheDataFineValeAura(abilitazioneDaModificare);
                        	}
                        	
                         	  String keyOperAbil=abilitazioneDaModificare.getId().toString();
                           	  if(preferenze!=null && !preferenze.isEmpty()) {
                           		  keyOperAbil+="-"+preferenze.stream().map(PreferenzaDto::getId).map(String::valueOf).collect(Collectors.joining(","));
                           	  }
                          	// Scrittura log Audit
                              try {
                      			setLogAuditSOLNew(OperazioneEnum.UPDATE, ConstantsWebApp.KEY_OPER_MODIFICA_SOL, form.getCf(), 
                      					uuid, keyOperAbil, ConstantsWebApp.MODIFICA_SOL, data);
                      		} catch (Exception e1) {
                      			// TODO Auto-generated catch block
                      			e1.printStackTrace();
                      		}
                        }
                        //FunzionalitaDto funzionalitaDto = funzionalitaLowDao.findByPrimaryId(idFunzioneNuova);
                        //abilitazioneDaModificare.setTreeFunzionalitaDto(Utils.getFirstRecord(treeFunzionalitaLowDao.findByFunzionalitaDto(funzionalitaDto)));
                        
                        /////////////////////////////////////////////////////////////////////////
                        

                    });
                }
           // }
        });
    }

	private List<PreferenzaDto> cancellaPreferenza(String codiceFiscaleUtente, AbilitazioneDto abilitazioneDaEliminareLogicamente) {
		List<PreferenzaDto> preferenze = new ArrayList<PreferenzaDto>();
		Collection<AbilitazioneDto> findByRuoloUtenteDto = abilitazioneLowDao.findByRuoloUtenteDto(abilitazioneDaEliminareLogicamente.getRuoloUtenteDto());
		if(findByRuoloUtenteDto.isEmpty()) {
			PreferenzaDto preferenzaRuolo = 
					preferenzaLowDao.getPreferenzaRuolo(abilitazioneDaEliminareLogicamente.getRuoloUtenteDto().getUtenteDto(), abilitazioneDaEliminareLogicamente.getRuoloUtenteDto().getRuoloDto());
			if(preferenzaRuolo != null) {
				cancellaLogicamentePreferenza(preferenzaRuolo);
			}
			
			preferenze.add(preferenzaRuolo);
			
			PreferenzaDto preferenzaRuoloCollocazione = 
					preferenzaLowDao.getPreferenzaRuoloCollocazione(abilitazioneDaEliminareLogicamente.getRuoloUtenteDto().getUtenteDto(), 
							abilitazioneDaEliminareLogicamente.getRuoloUtenteDto().getRuoloDto(), abilitazioneDaEliminareLogicamente.getUtenteCollocazioneDto().getCollocazioneDto());
			if(preferenzaRuoloCollocazione != null) {
				cancellaLogicamentePreferenza(preferenzaRuoloCollocazione);
			}
			
			preferenze.add(preferenzaRuoloCollocazione);
			
		} else {
			List<AbilitazioneDto> filtered = findByRuoloUtenteDto.stream().filter(a -> a.getUtenteCollocazioneDto().getId_utecol()
					.equals(abilitazioneDaEliminareLogicamente.getUtenteCollocazioneDto().getId_utecol()))
					.collect(Collectors.toList());
			if(filtered.isEmpty()) {
				PreferenzaDto preferenzaRuoloCollocazione = 
						preferenzaLowDao.getPreferenzaRuoloCollocazione(abilitazioneDaEliminareLogicamente.getRuoloUtenteDto().getUtenteDto(), 
								abilitazioneDaEliminareLogicamente.getRuoloUtenteDto().getRuoloDto(), abilitazioneDaEliminareLogicamente.getUtenteCollocazioneDto().getCollocazioneDto());
				if(preferenzaRuoloCollocazione != null) {
					cancellaLogicamentePreferenza(preferenzaRuoloCollocazione);
				}
				
				preferenze.add(preferenzaRuoloCollocazione);
			}		
		}
		
		List<AbilitazioneDto> findByCodiceFiscaleAndApplicazioneAndDateValidita = abilitazioneLowDao.findByCodiceFiscaleAndApplicazioneAndDateValidita(codiceFiscaleUtente, 
				abilitazioneDaEliminareLogicamente.getApplicazioneDto().getCodice());
		if(findByCodiceFiscaleAndApplicazioneAndDateValidita.isEmpty()) {
			PreferenzaDto preferenzaApplicazione = 
					preferenzaLowDao.getPreferenzaApplicazione(abilitazioneDaEliminareLogicamente.getRuoloUtenteDto().getUtenteDto(), abilitazioneDaEliminareLogicamente.getApplicazioneDto());
			if(preferenzaApplicazione != null) {
				cancellaLogicamentePreferenza(preferenzaApplicazione);
			}
			
			preferenze.add(preferenzaApplicazione);
		}
		
		return preferenze;
	}

	private void cancellaLogicamentePreferenza(PreferenzaDto preferenzaRuolo) {
		preferenzaRuolo.setDataAggiornamento(Utils.sysdate());
		preferenzaRuolo.setDataCancellazione(Utils.sysdate());
		preferenzaRuolo.setDataFineValidita(Utils.sysdate());
		preferenzaLowDao.update(preferenzaRuolo);
	}

    private List<AbilitazioneDto> recuperaListaSolDaEliminareLogicamente(Long idUtente, String codiceFiscaleUtente,
                                                                         List<String> listaNuoviSol, Data data) {
        List<AbilitazioneDto> listaAbilitazioniDtoDaEliminareLogicamente = new ArrayList<>();

        List<String> collocazioni = collocazioneService.ricercaCollocazioniByIdUtente(idUtente, data).stream()
                .map(BaseDto::getId).map(Object::toString).collect(Collectors.toList());
        
        List<String> ruoli = ruoloLowDao.findByIdUtente(idUtente).stream().map(BaseDto::getId).map(Object::toString).collect(Collectors.toList());
        
        List<String> listaSolVecchi = serviziOnLineService.ricercaServiziOnLineByIdUtenteAndData(idUtente, data, collocazioni,ruoli);
        if (!Utils.listIsNullOrEmpty(listaSolVecchi)) {
        	List<SolModel> oldSolModel = listaSolVecchi.stream().map(sol -> new SolModel(sol)).collect(Collectors.toList());
        	List<SolModel> newSolModel = listaNuoviSol.stream().map(sol -> new SolModel(sol)).collect(Collectors.toList());
        	
        	listaSolVecchi = oldSolModel.stream().filter(solVecchio -> !newSolModel.contains(solVecchio)).map(sol -> {
        				String s = sol.getIdSol() + "|" + sol.getIdCollocazione() + "|" + sol.getIdProfilo() + "|" + sol.getIdRuolo();
        			 	if(sol.getValidita() != null) {
        					String dataFineValidita = Utils.timestampToString(sol.getValidita());
//                            String newDate = dataFineValidita.substring(8, dataFineValidita.indexOf(' ')) + "/"
//                                    + dataFineValidita.substring(5, 7) + "/" + dataFineValidita.substring(0, 4);
//                            s = s + '|' + newDate;
        					s = s + '|' + dataFineValidita;
        				}
        				return s;
        			}).collect(Collectors.toList());
//            Set<Long> setListaSolIdNuovi = listaNuoviSol.stream().map(sol -> Long.parseLong(sol.split("\\|")[0])).collect(Collectors.toSet());
//           
//            listaSolVecchi = listaSolVecchi.stream()
//                    .filter(solVecchio -> !setListaSolIdNuovi.contains(Long.parseLong(solVecchio.split("\\|")[0])))
//                    .collect(Collectors.toList());
            listaAbilitazioniDtoDaEliminareLogicamente = recuperaListaAbilitazioniDtoACuiUtenteAbilitato(codiceFiscaleUtente, listaSolVecchi);
        }
        return listaAbilitazioniDtoDaEliminareLogicamente;
    }

    public List<UtenteCollocazioneDto> aggiornaCollocazioniUtente(List<UtenteCollocazioneDto> listaCollocazioniUtenteVecchia,
                                                                  List<Long> listaCollocazioniModificata, UtenteDto utenteDto,
                                                                  String cfOperatore, Data data,String uuid) {
        List<UtenteCollocazioneDto> finalListaCollocazioniUtente = new ArrayList<>();

        // estraggo gli elementi presenti in listaCollocazioniUtenteVecchia e non presenti in listaCollocazioniModificata
        // se la lista che ottengo non Ã¨ vuota effettuo l'eliminazione logica delle collocazioni associate all'utente
        List<UtenteCollocazioneDto> listaCollocazioniUtenteDaEliminareLogicamente = listaCollocazioniUtenteVecchia.stream()
                .filter(collocazioneUtenteVecchia -> !listaCollocazioniModificata.contains(collocazioneUtenteVecchia.getCollocazioneDto().getColId()))
                .collect(Collectors.toList());
        // estraggo le collocazioni a cui l'utente Ã¨ abilitato
        List<UtenteCollocazioneDto> finalListaCollocazioniUtenteDaEliminareLogicamente = recuperaListaCollocazioniACuiUtenteAbilitato(
                listaCollocazioniUtenteDaEliminareLogicamente, utenteDto.getId(), data);
        if (!finalListaCollocazioniUtenteDaEliminareLogicamente.isEmpty()) {
            eliminaLogicamenteCollocazioniUtente(finalListaCollocazioniUtenteDaEliminareLogicamente, cfOperatore);
            String collKeyoper= finalListaCollocazioniUtenteDaEliminareLogicamente.stream().map(UtenteCollocazioneDto::getId_utecol).map(String::valueOf).collect(Collectors.joining(",")); 
         	// Scrittura log Audit
            try {
    			setLogAuditSOLNew(OperazioneEnum.UPDATE, ConstantsWebApp.KEY_OPER_RIMUOVI_COLLOCAZIONE, utenteDto.getCodiceFiscale(), 
    					uuid, collKeyoper, ConstantsWebApp.RIMUOVI_COLLOCAZIONE, data);
    		} catch (Exception e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
        }

        // estraggo gli elementi presenti in listaCollocazioniModificata e non presenti in listaCollocazioniUtenteVecchia
        // se la lista che ottengo non Ã¨ vuota effettuo l'inserimento delle nuove collocazioni associate all'utente
        Set<Long> setListaIdCollocazioniUtenteVecchia = listaCollocazioniUtenteVecchia.stream()
                .map(utenteCollocazioneDto -> utenteCollocazioneDto.getCollocazioneDto().getColId()).collect(Collectors.toSet());
        List<Long> listaNuoveCollocazioniUtenteId = listaCollocazioniModificata.stream()
                .filter(collocazioneId -> !setListaIdCollocazioniUtenteVecchia.contains(collocazioneId)).collect(Collectors.toList());
        List <UtenteCollocazioneDto> utentiCollocazione= new ArrayList<UtenteCollocazioneDto>();
        if (!listaNuoveCollocazioniUtenteId.isEmpty()) {
            finalListaCollocazioniUtente = listaNuoveCollocazioniUtenteId.stream().map(idCollocazione -> {
                UtenteCollocazioneDto nuovaCollocazioneUtenteDto = new UtenteCollocazioneDto();
                UtenteCollocazioneDto popolaTabellaCollocazioneUtente = popolaTabellaCollocazioneUtente(nuovaCollocazioneUtenteDto, utenteDto, idCollocazione, cfOperatore);
                utentiCollocazione.add(popolaTabellaCollocazioneUtente);
                return nuovaCollocazioneUtenteDto;
            }).collect(Collectors.toList());
        }
        if (!listaNuoveCollocazioniUtenteId.isEmpty()) {
        	String UtenteCollKeyOper = utentiCollocazione.stream().map(UtenteCollocazioneDto::getId_utecol).map(String::valueOf).collect(Collectors.joining(","));
        	// Scrittura log Audit
            try {
    			setLogAuditSOLNew(OperazioneEnum.INSERT, ConstantsWebApp.KEY_OPER_ASSEGNA_COLLOCAZIONE, utenteDto.getCodiceFiscale(), 
    					uuid, UtenteCollKeyOper, ConstantsWebApp.ASSEGNA_COLLOCAZIONE, data);
    		} catch (Exception e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
        	
        }
        listaCollocazioniUtenteVecchia.removeIf(collocazioneUtenteVecchia -> !finalListaCollocazioniUtenteDaEliminareLogicamente.isEmpty()
                && finalListaCollocazioniUtenteDaEliminareLogicamente.stream().map(collocazioneUtenteDto -> collocazioneUtenteDto.getCollocazioneDto().getColId())
                .collect(Collectors.toSet()).contains(collocazioneUtenteVecchia.getCollocazioneDto().getColId()));
        finalListaCollocazioniUtente.addAll(listaCollocazioniUtenteVecchia);

        return finalListaCollocazioniUtente;
    }

    public void eliminaLogicamenteCollocazioniUtente(List<UtenteCollocazioneDto> listaCollocazioniUtenteDaEliminareLogicamente,
                                                     String cfOperatore) {
        listaCollocazioniUtenteDaEliminareLogicamente.forEach(collocazioneDaEliminareLogicamente -> {
            collocazioneDaEliminareLogicamente.setDataAggiornamento(Utils.sysdate());
            collocazioneDaEliminareLogicamente.setDataFineValidita(Utils.sysdate());
            collocazioneDaEliminareLogicamente.setDataCancellazione(Utils.sysdate());
            collocazioneDaEliminareLogicamente.setCfOperatore(cfOperatore);
            utenteCollocazioneLowDao.update(collocazioneDaEliminareLogicamente);
            List<AbilitazioneDto> listaAbilitazioniDaEliminareLogicamente = collocazioneDaEliminareLogicamente.getAbilitazioneList();
            if (!Utils.listIsNullOrEmpty(listaAbilitazioniDaEliminareLogicamente)) {
                listaAbilitazioniDaEliminareLogicamente.forEach(abilitazioneAssociataACollocazioneDaEliminareLogicamente ->
                        modificaAbilitazione(abilitazioneAssociataACollocazioneDaEliminareLogicamente, cfOperatore, true)
                );
            }
        });
    }

    public List<RuoloUtenteDto> aggiornaRuoliUtente(List<RuoloUtenteDto> listaRuoliUtenteVecchia,
                                                    List<Long> listaRuoliModificata, UtenteDto utenteDto,
                                                    String cfOperatore, List<Long> listaCollocazioniACuiOperatoreAbilitato, Data data,String uuid) {
        List<RuoloUtenteDto> finalListaRuoliUtente = new ArrayList<>();

        // estraggo gli elementi presenti in listaRuoliUtenteVecchia e non presenti in listaRuoliModificata
        // se la lista che ottengo non Ã¨ vuota effettuo l'eliminazione logica dei ruoli associati all'utente
        List<RuoloUtenteDto> listaRuoliUtenteDaEliminareLogicamente = listaRuoliUtenteVecchia.stream()
                .filter(ruoloUtenteVecchio -> !listaRuoliModificata.contains(ruoloUtenteVecchio.getRuoloDto().getId())).collect(Collectors.toList());
        List<RuoloUtenteDto> finalListaRuoliUtenteDaEliminareLogicamente = listaRuoliUtenteDaEliminareLogicamente.stream()
                .filter(ruoloUtenteDto -> ruoloUtenteDto.getRuoloDto().getFlagConfiguratore() != null && ruoloUtenteDto.getRuoloDto().getFlagConfiguratore().equals("S"))
                .collect(Collectors.toList());
        if (!finalListaRuoliUtenteDaEliminareLogicamente.isEmpty()) {
            eliminaLogicamenteRuoliUtente(finalListaRuoliUtenteDaEliminareLogicamente, cfOperatore, listaCollocazioniACuiOperatoreAbilitato, data);
            String ruoliElimKeyOper=finalListaRuoliUtenteDaEliminareLogicamente.stream().map(RuoloUtenteDto::getId).map(String::valueOf).collect(Collectors.joining(","));
        	// Scrittura log Audit
            try {
    			setLogAuditSOLNew(OperazioneEnum.UPDATE, ConstantsWebApp.KEY_OPER_RIMUOVI_RUOLO, utenteDto.getCodiceFiscale(), 
    					uuid, ruoliElimKeyOper, ConstantsWebApp.RIMUOVI_RUOLO, data);
    		} catch (Exception e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
        }

        // estraggo gli elementi presenti in listaRuoliModificata e non presenti in listaRuoliUtenteVecchia
        // se la lista che ottengo non Ã¨ vuota effettuo l'inserimento dei nuovi ruoli associati all'utente
        Set<Long> setListaIdRuoliUtenteVecchia = listaRuoliUtenteVecchia.stream()
                .map(ruoloUtenteDto -> ruoloUtenteDto.getRuoloDto().getId()).collect(Collectors.toSet());
        List<Long> listaNuoviRuoliUtenteId = listaRuoliModificata.stream()
                .filter(ruoloId -> !setListaIdRuoliUtenteVecchia.contains(ruoloId)).collect(Collectors.toList());
        List<RuoloUtenteDto> ruoliInseriti= new ArrayList<RuoloUtenteDto>();
        if (!listaNuoviRuoliUtenteId.isEmpty()) {
            finalListaRuoliUtente = listaNuoviRuoliUtenteId.stream().map(ruoloId -> {
                RuoloUtenteDto nuovoRuoloUtenteDto = new RuoloUtenteDto();
                RuoloUtenteDto popolaTabellaRuoloUtente = popolaTabellaRuoloUtente(nuovoRuoloUtenteDto, utenteDto, ruoloId, cfOperatore);
                ruoliInseriti.add(popolaTabellaRuoloUtente);
                return nuovoRuoloUtenteDto;
            }).collect(Collectors.toList());
            String ruoliKeyOper= ruoliInseriti.stream().map(RuoloUtenteDto::getId).map(String::valueOf).collect(Collectors.joining(","));
         // Scrittura log Audit
            try {
    			setLogAuditSOLNew(OperazioneEnum.INSERT, ConstantsWebApp.KEY_OPER_ASSEGNA_RUOLO, utenteDto.getCodiceFiscale(), 
    					uuid, ruoliKeyOper, ConstantsWebApp.ASSEGNA_RUOLO, data);
    		} catch (Exception e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
        }

        listaRuoliUtenteVecchia.removeIf(ruoloUtenteVecchio -> !finalListaRuoliUtenteDaEliminareLogicamente.isEmpty()
                && finalListaRuoliUtenteDaEliminareLogicamente.stream().map(ruoloUtenteDto -> ruoloUtenteDto.getRuoloDto().getId())
                .collect(Collectors.toSet()).contains(ruoloUtenteVecchio.getRuoloDto().getId()));
        finalListaRuoliUtente.addAll(listaRuoliUtenteVecchia);

        return finalListaRuoliUtente;
    }

    public void eliminaLogicamenteRuoliUtente(List<RuoloUtenteDto> listaRuoliUtenteDaEliminareLogicamente,
                                              String cfOperatore, List<Long> listaCollocazioniACuiOperatoreAbilitato, Data data) {

        boolean isSuperUser = FunzionalitaEnum.SUPERUSERCONF_PROF.getValue().equalsIgnoreCase(data.getUtente().getProfilo());

        listaRuoliUtenteDaEliminareLogicamente.forEach(ruoloUtenteDaEliminareLogicamente -> {
            // Se ruoloUtenteDaEliminareLogicamente risulta associato a profili (abilitazioni) su collocazioni
            // di competenza dellâ€™operatore effettuo lâ€™eliminazione logica di tale ruolo
            List<AbilitazioneDto> listaAbilitazioniAssociateARuoloUtenteDaEliminareLogicamente = ruoloUtenteDaEliminareLogicamente.getAbilitazioneList()
                    .stream().filter(abilitazioneDto -> Utils.isValidTime(abilitazioneDto.getDataInizioValidita(), abilitazioneDto.getDataFineValidita()))
                    .collect(Collectors.toList());
            if (isSuperUser || listaAbilitazioniAssociateARuoloUtenteDaEliminareLogicamente.isEmpty()) {
                ruoloUtenteDaEliminareLogicamente.setCfOperatore(cfOperatore);
                ruoloUtenteDaEliminareLogicamente.setDataAggiornamento(Utils.sysdate());
                ruoloUtenteDaEliminareLogicamente.setDataFineValidita(Utils.sysdate());
                ruoloUtenteLowDao.update(ruoloUtenteDaEliminareLogicamente);
                listaAbilitazioniAssociateARuoloUtenteDaEliminareLogicamente.forEach(abilitazioneAssociataARuoloDaEliminareLogicamente ->
                        modificaAbilitazione(abilitazioneAssociataARuoloDaEliminareLogicamente, cfOperatore, true)
                );
            }
        });
    }

    public void modificaAbilitazione(AbilitazioneDto abilitazioneDtoDaModificare, String cfOperatore,
                                     boolean isEliminazione) {
        AbilitazioneDto abilitazioneDto = abilitazioneLowDao.findByPrimaryId(abilitazioneDtoDaModificare.getId());
        abilitazioneDto.setDataAggiornamento(Utils.sysdate());
        	abilitazioneDto.setCfOperatore(cfOperatore);
        	if (isEliminazione) {
        		abilitazioneDto.setDataFineValidita(Utils.sysdate());
        		abilitazioneDto.setDataCancellazione(Utils.sysdate());
        	} else {
        		abilitazioneDto.setTreeFunzionalitaDto(abilitazioneDtoDaModificare.getTreeFunzionalitaDto());
        		abilitazioneDto.setDataFineValidita(Utils.truncateTimestamp(abilitazioneDtoDaModificare.getDataFineValidita(), false));
        	}
        	abilitazioneLowDao.update(abilitazioneDto);
        	
        
      
    }

    public UtenteCollocazioneDto popolaTabellaCollocazioneUtente(UtenteCollocazioneDto utenteCollocazioneDto, UtenteDto utenteDto,
                                                Long idCollocazione, String cfOperatore) {
    	utenteCollocazioneDto.setUtenteDto(utenteDto);
        utenteCollocazioneDto.setCollocazioneDto(collocazioneLowDao.findByPrimaryId(idCollocazione));
        utenteCollocazioneDto.setDataInserimento(Utils.sysdate());
        utenteCollocazioneDto.setDataInizioValidita(Utils.truncateTimestamp(Utils.sysdate(), true));
        utenteCollocazioneDto.setDataAggiornamento(Utils.sysdate());
        utenteCollocazioneDto.setCfOperatore(cfOperatore);
        utenteCollocazioneDto.setFlagConfiguratore("S");
        UtenteCollocazioneDto collocazioneInserita= utenteCollocazioneLowDao.insert(utenteCollocazioneDto);
        return collocazioneInserita;
    }

    public RuoloUtenteDto popolaTabellaRuoloUtente(RuoloUtenteDto ruoloUtenteDto, UtenteDto utenteDto, Long idRuolo,
                                         String cfOperatore) {
        ruoloUtenteDto.setUtenteDto(utenteDto);
        ruoloUtenteDto.setRuoloDto(ruoloLowDao.findByPrimaryId(idRuolo));
        ruoloUtenteDto.setDataInserimento(Utils.sysdate());
        ruoloUtenteDto.setDataInizioValidita(Utils.truncateTimestamp(Utils.sysdate(), true));
        ruoloUtenteDto.setCfOperatore(cfOperatore);
        ruoloUtenteDto.setFlagConfiguratore("S");
        RuoloUtenteDto insert = ruoloUtenteLowDao.insert(ruoloUtenteDto);
        return insert;
    }

    public AbilitazioneDto popolaTabellaAbilitazione(AbilitazioneDto nuovoRecordAbilitazione, AbilitazioneDto abilitazioneDto, String cfOperatore) {
        nuovoRecordAbilitazione.setRuoloUtenteDto(abilitazioneDto.getRuoloUtenteDto());
        nuovoRecordAbilitazione.setApplicazioneDto(abilitazioneDto.getApplicazioneDto());
        nuovoRecordAbilitazione.setCodiceAbilitazione(UUID.randomUUID().toString());
        nuovoRecordAbilitazione.setDataInizioValidita(Utils.truncateTimestamp(Utils.sysdate(), true));
        nuovoRecordAbilitazione.setDataFineValidita(Utils.truncateTimestamp(abilitazioneDto.getDataFineValidita(), false));
        nuovoRecordAbilitazione.setDataInserimento(Utils.sysdate());
        nuovoRecordAbilitazione.setTreeFunzionalitaDto(abilitazioneDto.getTreeFunzionalitaDto());
        nuovoRecordAbilitazione.setUtenteCollocazioneDto(abilitazioneDto.getUtenteCollocazioneDto());
        nuovoRecordAbilitazione.setDataAggiornamento(Utils.sysdate());
        nuovoRecordAbilitazione.setCfOperatore(cfOperatore);

        AbilitazioneDto insert = abilitazioneLowDao.insert(nuovoRecordAbilitazione);
        return insert;
        
        
    }

    private void replaceStringCollocazione(List<RisultatiRicercaUtenteDTO> risultatiRicercaUtente, String oldChar, String newChar) {
        if (!Utils.listIsNullOrEmpty(risultatiRicercaUtente)) {
            risultatiRicercaUtente.forEach(risultatoRicercaUtente -> {
                if (risultatoRicercaUtente.getCollocazione() != null && !risultatoRicercaUtente.getCollocazione().isEmpty())
                    risultatoRicercaUtente.setCollocazione(risultatoRicercaUtente.getCollocazione().replaceAll(oldChar, newChar));
            });
        }
    }

    private void replaceStringCollocazioneExport(List<UtentiConfiguratoreViewDto> risultatiExportUtente, String oldChar, String newChar) {
        if (!Utils.listIsNullOrEmpty(risultatiExportUtente)) {
            risultatiExportUtente.forEach(risultatoExport -> {
                if(risultatoExport.getCodiceStrutturaSanitaria() != null)
                    risultatoExport.setCodiceStrutturaSanitaria(risultatoExport.getCodiceStrutturaSanitaria().replaceAll(oldChar, newChar));
                if(risultatoExport.getDescrizioneAziendaSanitaria() != null)
                    risultatoExport.setDescrizioneStrutturaSanitaria(risultatoExport.getDescrizioneStrutturaSanitaria().replaceAll(oldChar, newChar));
            });
        }
    }

    private boolean checkProfiliSol(List<String> profiliSol) {
        AtomicBoolean checkProfiliSol = new AtomicBoolean(true);
        SimpleDateFormat sdf = new SimpleDateFormat(ConstantsWebApp.DATE_PATTERN);

        profiliSol.forEach(sol -> {
            String[] split = sol.split("\\|");
            try {
                Long.parseLong(split[0]);
                Long.parseLong(split[1]);
                Long.parseLong(split[2]);
                Long.parseLong(split[3]);
                if (split.length > 4) Utils.toTimestamp(sdf.parse(split[4]));
            } catch (IndexOutOfBoundsException | NumberFormatException | ParseException e) {
                log.error("ERROR: Salvataggio Utente, profiliSol - ", e);
                checkProfiliSol.set(false);
            }
        });
        return checkProfiliSol.get();
    }

    public void controllaDataFineValiditaUtenteSol(UtenteDto utenteDto, List<String> profiliSol,
                                                   String dataFineValiditaUtente) throws WebServiceException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(ConstantsWebApp.DATE_PATTERN);

        Timestamp fineValiditaUtente = dataFineValiditaUtente != null ? Utils.toTimestamp(sdf.parse(dataFineValiditaUtente)) : null;
        if (!Utils.isDataFineValiditaValida(utenteDto.getDataInizioValidita(), Utils.truncateTimestamp(fineValiditaUtente, false)))
            throw new WebServiceException("dataFineValidita - DataFineValidita");

        if (!Utils.listIsNullOrEmpty(profiliSol)) {
            List<AbilitazioneDto> listaAbilitazioniConDataFineValiditaInvalida = new ArrayList<>();
            profiliSol.forEach(sol -> {
                String[] split = sol.split("\\|");
                Long idApplicazione;
                Long idCollocazione;
                Long idRuolo;
                Timestamp dataFineValidita;
                Long idFunzioneVecchia;

                try {
                    idApplicazione = Long.parseLong(split[0]);
                    idCollocazione = Long.parseLong(split[1]);
                    idRuolo = Long.parseLong(split[3]);
                    dataFineValidita = split.length > 4 && !Utils.isEmpty(split[4])
                            ? Utils.truncateTimestamp(Utils.toTimestamp(sdf.parse(split[4])), false)
                            : null;
                    idFunzioneVecchia = split.length > 5 && !Utils.isEmpty(split[5]) ? Long.parseLong(split[5]) : null;
                } catch (IndexOutOfBoundsException | NumberFormatException | ParseException e) {
                    return;
                }

                if (idFunzioneVecchia == null && !Utils.isDataFineValiditaValida(null, dataFineValidita)) {
                    listaAbilitazioniConDataFineValiditaInvalida.add(new AbilitazioneDto());
                } else {
                    List<AbilitazioneDto> listaAbilitazioniPresentiSuDb = abilitazioneLowDao
                            .findAbilitazioniByIdApplicazioneAndIdCollocazioneAndIdFunzioneAndCodiceFiscaleUtenteAndIdRuolo(idApplicazione, idCollocazione, idFunzioneVecchia, utenteDto.getCodiceFiscale(), idRuolo);
                    listaAbilitazioniPresentiSuDb = listaAbilitazioniPresentiSuDb.stream()
                            .filter(abilitazioneDto -> !Utils.isDataFineValiditaValida(abilitazioneDto.getDataInizioValidita(), dataFineValidita))
                            .collect(Collectors.toList());
                    listaAbilitazioniConDataFineValiditaInvalida.addAll(listaAbilitazioniPresentiSuDb);
                }
            });
            if (!Utils.listIsNullOrEmpty(listaAbilitazioniConDataFineValiditaInvalida))
                throw new WebServiceException("profiliSol - DataFineValidita");
        }
    }

    public List<MessaggiUtenteDto> invioMailAUtenteProfilato(String cf, Data data, SalvataggioUtenteModel utenteSalvato) throws Exception {
    	
        List<MessaggiUtenteDto> messaggiErrore = new ArrayList<>();
        UtenteDto utenteDto = Utils.getFirstRecord(utenteLowDao.findByCodiceFiscale(cf));
        if (utenteDto != null) {
            if (Utils.isEmpty(utenteDto.getIndirizzoMail())) {
                messaggiErrore.add(aggiungiErrori(ConstantsWebApp.EMAIL_NON_PRESENTE));
                return messaggiErrore;
            }

            /*
                Recuper oggetto e testo mail da db
             */
            getConfigurazionMail(utenteSalvato.checkMailAura());
            
            if (Utils.isEmpty(mailMessage.getOggettoMail()) || Utils.isEmpty(mailMessage.getTestoPrimaParteMail())) {
                messaggiErrore.add(aggiungiErrori(ConstantsWebApp.ERRORE_COMPILAZIONE_TESTO_MAIL));
                return messaggiErrore;
            }

            List<String> collocazioni = collocazioneService.ricercaCollocazioniByIdUtente(utenteDto.getId(), data)
                    .stream().map(BaseDto::getId).map(Object::toString).collect(Collectors.toList());
            List<String> ruoli = ruoloLowDao.findByIdUtente(utenteDto.getId()).stream().map(BaseDto::getId).map(Object::toString).collect(Collectors.toList());
            List<String> listaProfili = serviziOnLineService.ricercaServiziOnLineByIdUtenteAndData(utenteDto.getId(), data, collocazioni, ruoli);
            String solProfiliCollocazioniAbilitateEAttive = listaProfili.stream().map(profiloSol -> {
                String[] split = profiloSol.split("\\|");
                String testo;

                ApplicazioneDto applicazioneDto = applicazioneLowDao.findByPrimaryId(Long.valueOf(split[0]));
                CollocazioneDto collocazioneDto = collocazioneLowDao.findByPrimaryId(Long.valueOf(split[1]));
                FunzionalitaDto funzionalitaDto = funzionalitaLowDao.findByPrimaryId(Long.valueOf(split[2]));

                testo = applicazioneDto.getDescrizione() + ", " + funzionalitaDto.getDescrizioneFunzione() + " - "
                        + collocazioneDto.getColCodAzienda() + " " + collocazioneDto.getColDescAzienda();
                if (!Utils.isEmpty(collocazioneDto.getColCodice()) && !Utils.isEmpty(collocazioneDto.getColDescrizione())) {
                    String codiceStruttura = collocazioneDto.getColCodice();
                    String descrizioneStruttura = collocazioneDto.getColDescrizione();
                    if ((collocazioneDto.getFlagAzienda() == null || !collocazioneDto.getFlagAzienda().equalsIgnoreCase("S")) && !descrizioneStruttura.equals("-"))
                        testo += " - " + codiceStruttura + " " + descrizioneStruttura;
                    else if ((collocazioneDto.getFlagAzienda() == null || !collocazioneDto.getFlagAzienda().equalsIgnoreCase("S")))
                        testo += " - " + codiceStruttura;
                    else if (!descrizioneStruttura.equals("-"))
                        testo += " - " + descrizioneStruttura;
                }
                
                testo += " - data fine validita': ";
                if (split.length == 5 && !split[4].isEmpty()) {
                	testo += split[4];
				} else {
					testo += "fino a fine rapporto";
				}
                
                return testo;
            }).collect(Collectors.joining("\n -  "));
            String corpoMail = mailMessage.getTestoPrimaParteMail() + "\n -  " + solProfiliCollocazioniAbilitateEAttive;
            
            if(utenteSalvato.checkMailAura()) {
            	// aggiunta testo per abilitazioni AURA
            	corpoMail += "\n";
            	if(!Utils.isEmptyList(utenteSalvato.getAbilitazioniAura())) {
            		List<String> apps = utenteSalvato.getAbilitazioniAura().stream().map(a -> a.getApplicazioneDto().getCodice()).distinct().collect(Collectors.toList());
            		for(String app : apps)
            			corpoMail += "\n"+mailMessage.getAuraParams().getTestoMailUserAbil().replace("@APP@", app);
            	}
            	if(!Utils.isEmptyList(utenteSalvato.getDisabilitazioniAura())) {
            		List<String> apps = utenteSalvato.getDisabilitazioniAura().stream().map(a -> a.getApplicazioneDto().getCodice()).distinct().collect(Collectors.toList());
            		for(String app : apps)
            		corpoMail += "\n"+mailMessage.getAuraParams().getTestoMailUserDisabil().replace("@APP@", app);
            	}
            }
            corpoMail += "\n\n\n\n " + mailMessage.getFooter();
			
            try {
				javaMailUtil.sendSimpleMailMessage(mailMessage.getMittenteMail(), utenteDto.getIndirizzoMail(),
						mailMessage.getOggettoMail(), corpoMail);
				messaggiErrore.add(aggiungiErrori(ConstantsWebApp.OPERAZIONE_EFFETTUATA));
			} catch (MessagingException e) {
                messaggiErrore.add(aggiungiErrori(ConstantsWebApp.ERRORE_INVIO_EMAIL));
            }
        }
        return messaggiErrore;
    }
    
    public List<MessaggiUtenteDto> invioMailConfAdAura(String cf, SalvataggioUtenteModel utenteSalvato) throws Exception {
        List<MessaggiUtenteDto> messaggiErrore = new ArrayList<>();
        UtenteDto utenteDto = Utils.getFirstRecord(utenteLowDao.findByCodiceFiscale(cf));
        if (utenteDto != null) {
           
            /*
                Recuper oggetto e testo mail da db
             */
            getConfigurazionMail(utenteSalvato.checkMailAura());
            
            if(utenteSalvato.checkMailAura()) {
            	if (mailMessage.getAuraParams() == null || Utils.isEmpty(mailMessage.getAuraParams().getEmailAura()) 
            			|| Utils.isEmpty(mailMessage.getAuraParams().getOggettoMailAura()) 
            			|| Utils.isEmpty(mailMessage.getAuraParams().getTestoPrimaParteMailAura())
            			|| Utils.isEmpty(mailMessage.getAuraParams().getTestoElencoMailAura())) {
                    messaggiErrore.add(aggiungiErrori(ConstantsWebApp.ERRORE_COMPILAZIONE_TESTO_MAIL_AURA));
                } else {
                	// Invio mail ad AURA
                	if(!Utils.isEmptyList(utenteSalvato.getAbilitazioniAura())) {
                		//Invio mail Abilitazione ad AURA
                		invioMailAdAura(utenteSalvato.getAbilitazioniAura(), messaggiErrore, utenteDto, "abilitazione");
                	}
                	if(!messaggiErrore.stream().anyMatch(m -> m.getCodice().equalsIgnoreCase(ConstantsWebApp.ERRORE_COMPILAZIONE_TESTO_MAIL_AURA)) 
                			&& !Utils.isEmptyList(utenteSalvato.getDisabilitazioniAura())) {
                		//Invio mail Disabilitazione ad AURA
                		invioMailAdAura(utenteSalvato.getDisabilitazioniAura(), messaggiErrore, utenteDto, "disabilitazione");
                	}
                	if(!messaggiErrore.stream().anyMatch(m -> m.getCodice().equalsIgnoreCase(ConstantsWebApp.ERRORE_COMPILAZIONE_TESTO_MAIL_AURA)) 
                			&& !Utils.isEmptyList(utenteSalvato.getModificheDataFineValAura())) {
                		//Invio mail modifica data fine validita' ad AURA
                		invioMailAdAura(utenteSalvato.getModificheDataFineValAura(), messaggiErrore, utenteDto, "modifica data fine validita'");
                	}
                }
            }
        }
        return messaggiErrore;
    }

	private void invioMailAdAura(List<AbilitazioneDto> abilitazioni, List<MessaggiUtenteDto> messaggiErrore,
			UtenteDto utenteDto, String azione) throws Exception {
		String oggettoAura = mailMessage.getAuraParams().getOggettoMailAura();
		oggettoAura = oggettoAura.replace("@AZIONE@", azione);
		
		String testoAura = mailMessage.getAuraParams().getTestoPrimaParteMailAura();
		testoAura = testoAura.replace("@AZIONE@", azione)
							 .replace("@CODICE_FISCALE@", utenteDto.getCodiceFiscale())
							 .replace("@NOME@", utenteDto.getNome())
							 .replace("@COGNOME@", utenteDto.getCognome())
							 .replace("@EMAIL,TELEFONO@", utenteDto.getIndirizzoMail() + 
									 (Utils.isEmpty(utenteDto.getNumeroTelefono()) ? "" : ", "+utenteDto.getNumeroTelefono()) );
		
		SimpleDateFormat sdf = new SimpleDateFormat(ConstantsWebApp.DATE_PATTERN);
		for(AbilitazioneDto abi : abilitazioni) {
			testoAura += "\n -  "+mailMessage.getAuraParams().getTestoElencoMailAura()
			.replace("@COD_RUOLO@", abi.getRuoloUtenteDto().getRuoloDto().getCodice())
			.replace("@DESC_RUOLO@", abi.getRuoloUtenteDto().getRuoloDto().getDescrizione())
			.replace("@COL_CODICE@", abi.getUtenteCollocazioneDto().getCollocazioneDto().getColCodice())
			.replace("@COL_DESC@", abi.getUtenteCollocazioneDto().getCollocazioneDto().getColDescrizione())
			.replace("@COD_AZIENDA@", abi.getUtenteCollocazioneDto().getCollocazioneDto().getColCodAzienda())
			.replace("@DESC_AZIENDA@", abi.getUtenteCollocazioneDto().getCollocazioneDto().getColDescAzienda())
			.replace("@APP@", abi.getApplicazioneDto().getDescrizione())
			.replace("@COD_PROF@", abi.getTreeFunzionalitaDto().getFunzionalitaDto().getCodiceFunzione())
			.replace("@DESC_PROF@", abi.getTreeFunzionalitaDto().getFunzionalitaDto().getDescrizioneFunzione())
			.replace("@DATA_FINE_VAL@", abi.getDataFineValidita() == null ? "fino a fine rapporto" : sdf.format(abi.getDataFineValidita()));
		}
		testoAura += "\n\n\n\n " + mailMessage.getFooter();
		
		try {
			javaMailUtil.sendSimpleMailMessage(mailMessage.getMittenteMail(), mailMessage.getAuraParams().getEmailAura(),
					oggettoAura, testoAura);
//			messaggiErrore.add(aggiungiErrori(ConstantsWebApp.OPERAZIONE_EFFETTUATA));
		} catch (MessagingException e) {
		    messaggiErrore.add(aggiungiErrori(ConstantsWebApp.ERRORE_COMPILAZIONE_TESTO_MAIL_AURA));
		}
	}

    private void getConfigurazionMail(boolean mailAura) throws Exception {
        ConfigurazioneDto chiaveSubject = new ConfigurazioneDto();
        chiaveSubject.setChiave(Constants.CONF_MAIL_OGGETTO);
        ConfigurazioneDto chiaveTesto = new ConfigurazioneDto();
        chiaveTesto.setChiave(Constants.CONF_MAIL_CORPO);
        ConfigurazioneDto chiaveFooter = new ConfigurazioneDto();
        chiaveFooter.setChiave(Constants.CONF_MAIL_FOOTER);
        chiaveSubject = Utils.getFirstRecord(configurazioneLowDao.findByFilter(chiaveSubject));
        chiaveTesto = Utils.getFirstRecord(configurazioneLowDao.findByFilter(chiaveTesto));
        chiaveFooter = Utils.getFirstRecord(configurazioneLowDao.findByFilter(chiaveFooter));
        
        mailMessage.setOggettoMail(chiaveSubject.getValore());
        mailMessage.setTestoPrimaParteMail(chiaveTesto.getValore());
        mailMessage.setFooter(chiaveFooter.getValore());

        if(mailAura) {
        	MailAuraParams auraParams = getAuraMailParams();
        	mailMessage.setAuraParams(auraParams);
        }
    }

	private MailAuraParams getAuraMailParams() throws Exception {
		MailAuraParams auraParams = new MailAuraParams();
		ConfigurazioneDto chiaveEmailAura = new ConfigurazioneDto();
		chiaveEmailAura.setChiave(Constants.CONF_MAIL_EMAIL_AURA);
		ConfigurazioneDto chiaveSubjectAura = new ConfigurazioneDto();
		chiaveSubjectAura.setChiave(Constants.CONF_MAIL_OGGETTO_AURA);
		ConfigurazioneDto chiaveTestoAura = new ConfigurazioneDto();
		chiaveTestoAura.setChiave(Constants.CONF_MAIL_CORPO_AURA);
		ConfigurazioneDto chiaveTestoElencoAura = new ConfigurazioneDto();
		chiaveTestoElencoAura.setChiave(Constants.CONF_MAIL_ELENCO_AURA);
		ConfigurazioneDto chiaveTestoUserAbil = new ConfigurazioneDto();
		chiaveTestoUserAbil.setChiave(Constants.CONF_MAIL_USER_ABI);
		ConfigurazioneDto chiaveTestoUserDisabil = new ConfigurazioneDto();
		chiaveTestoUserDisabil.setChiave(Constants.CONF_MAIL_USER_DISABI);
		
		chiaveEmailAura = Utils.getFirstRecord(configurazioneLowDao.findByFilter(chiaveEmailAura));
		chiaveSubjectAura = Utils.getFirstRecord(configurazioneLowDao.findByFilter(chiaveSubjectAura));
		chiaveTestoAura = Utils.getFirstRecord(configurazioneLowDao.findByFilter(chiaveTestoAura));
		chiaveTestoElencoAura = Utils.getFirstRecord(configurazioneLowDao.findByFilter(chiaveTestoElencoAura));
		chiaveTestoUserAbil = Utils.getFirstRecord(configurazioneLowDao.findByFilter(chiaveTestoUserAbil));
		chiaveTestoUserDisabil = Utils.getFirstRecord(configurazioneLowDao.findByFilter(chiaveTestoUserDisabil));
		
		auraParams.setEmailAura(chiaveEmailAura.getValore());
		auraParams.setOggettoMailAura(chiaveSubjectAura.getValore());
		auraParams.setTestoPrimaParteMailAura(chiaveTestoAura.getValore());
		auraParams.setTestoElencoMailAura(chiaveTestoElencoAura.getValore());
		auraParams.setTestoMailUserAbil(chiaveTestoUserAbil.getValore());
		auraParams.setTestoMailUserDisabil(chiaveTestoUserDisabil.getValore());
		
		return auraParams;
	}

    private List<Long> getIdFromAbilitazioni(List<String> abilitazioni) {
        List<Long> listid = new ArrayList<>();
        if (abilitazioni != null) {
            abilitazioni.forEach(c -> {
                try {
                    long id = c.endsWith("ro") ? Long.parseLong(c.substring(0, c.length() - 2)) : Long.parseLong(c);
                    listid.add(id);
                } catch (NumberFormatException e) {
                    log.error("ERROR: modificaUtente " + e);
                }
            });
        }
        return listid;
    }

    public String disabilitaTutteConfigurazioni(Long id, List<String> listaProfiliSolACuiUtenteAbilitato, Data data, String uuid) {
        UtenteDto operatore = Utils.getFirstRecord(utenteLowDao.findByCodiceFiscale(data.getUtente().getCodiceFiscale()));

        UtenteDto utentePresenteSuDb = utenteLowDao.findByPrimaryId(id);
        List<AbilitazioneDto> listaAbilitazioniDaEliminareLogicamente = recuperaListaAbilitazioniDtoACuiUtenteAbilitato(utentePresenteSuDb.getCodiceFiscale(), listaProfiliSolACuiUtenteAbilitato);
        if (!Utils.listIsNullOrEmpty(listaAbilitazioniDaEliminareLogicamente)) {
        	String keyOperAbil = "";
        	String keyOperPref = "";
        	for(AbilitazioneDto abilitazioneDaEliminareLogicamente : listaAbilitazioniDaEliminareLogicamente) {
//	        listaAbilitazioniDaEliminareLogicamente.forEach(abilitazioneDaEliminareLogicamente ->{
	        	modificaAbilitazione(abilitazioneDaEliminareLogicamente, operatore.getCodiceFiscale(), true);
	        	List<PreferenzaDto> preferenze = cancellaPreferenza(utentePresenteSuDb.getCodiceFiscale(), abilitazioneDaEliminareLogicamente);
	        	
	        	if(abilitazioneDaEliminareLogicamente.getApplicazioneDto().getCodice().equalsIgnoreCase(Constants.APPLICATION_CODE)) {
	              	aggiornaVisibilitaAzienda(utentePresenteSuDb.getId(), operatore.getCodiceFiscale(), abilitazioneDaEliminareLogicamente.getUtenteCollocazioneDto().getCollocazioneDto().getColCodAzienda(),Utils.sysdate());
	            }
	        	
	      	  	if(preferenze!=null && !preferenze.isEmpty()) {
	      	  		keyOperPref+=","+preferenze.stream().map(PreferenzaDto::getId).map(String::valueOf).collect(Collectors.joining(","));
	      	  	}
	        }
        	//);
            keyOperAbil = listaAbilitazioniDaEliminareLogicamente.stream().map(AbilitazioneDto::getId).map(String::valueOf).collect(Collectors.joining(","));
            keyOperAbil += "-"+keyOperPref;
           
            // Scrittura log Audit
            try {
            	setLogAuditSOLNew(OperazioneEnum.UPDATE, ConstantsWebApp.KEY_OPER_DISABILITA_CONFIGURAZIONE, utentePresenteSuDb.getCodiceFiscale(), 
    					uuid, keyOperAbil, ConstantsWebApp.DISABILITA_CONFIGURAZIONE, data);
            } catch (Exception e1) {
            	// TODO Auto-generated catch block
            	e1.printStackTrace();
            }
            		
        }
        return utentePresenteSuDb.getCodiceFiscale();
    }

    private List<AbilitazioneDto> recuperaListaAbilitazioniDtoACuiUtenteAbilitato(String codiceFiscaleUtente, List<String> listaProfiliSol) {
        List<AbilitazioneDto> listaAbilitazioniACuiUtenteAbilitato = new ArrayList<>();
        if (!Utils.listIsNullOrEmpty(listaProfiliSol)) {
            listaProfiliSol.forEach(profiloSol -> {
                String[] split = profiloSol.split("\\|");
                Long idApplicazione;
                Long idCollocazione;
                Long idFunzione;
                Long idRuolo;

                try {
                    idApplicazione = Long.parseLong(split[0]);
                    idCollocazione = Long.parseLong(split[1]);
                    idFunzione = Long.parseLong(split[2]);
                    idRuolo = Long.parseLong(split[3]);
                } catch (IndexOutOfBoundsException | NumberFormatException e) {
                    log.error("ERROR: Recupero abilitazioni a cui utente abilitato - ", e);
                    return;
                }
                listaAbilitazioniACuiUtenteAbilitato.addAll(abilitazioneLowDao
                        .findAbilitazioniByIdApplicazioneAndIdCollocazioneAndIdFunzioneAndCodiceFiscaleUtenteAndIdRuolo(idApplicazione, idCollocazione, idFunzione, codiceFiscaleUtente, idRuolo));
            });
        }
        return listaAbilitazioniACuiUtenteAbilitato;
    }

    private List<UtenteCollocazioneDto> recuperaListaCollocazioniACuiUtenteAbilitato(List<UtenteCollocazioneDto> listaCollocazioniUtentePresentiSuDb,
                                                                                     Long idUtente, Data data) {
        List<Long> listaCollocazioniACuiUtenteAbilitato = collocazioneService.ricercaCollocazioniByIdUtente(idUtente, data)
                .stream().map(BaseDto::getId).collect(Collectors.toList());
        return listaCollocazioniUtentePresentiSuDb.stream().filter(utenteCollocazioneDto ->
                listaCollocazioniACuiUtenteAbilitato.contains(utenteCollocazioneDto.getCollocazioneDto().getColId())).collect(Collectors.toList());
    }

    public List<MessaggiUtenteDto> richiestaCredenzialiRupar(String cf, Data data) throws Exception {
        List<MessaggiUtenteDto> messaggiErrore = new ArrayList<>();
        UtenteDto utenteDto = Utils.getFirstRecord(utenteLowDao.findByCodiceFiscale(cf));
        if (utenteDto != null) {
            try {
                /*
                    Aggiungo controllo che non sia gia' stata effettuata una richiesta di credenziali
                 */
                if (checkRichiestaCredenziali(messaggiErrore, utenteDto)) return messaggiErrore;

                // Effettuo una chiamata al sistema di trouble ticketing Remedy, contattando il servizio esposto
                // â€œ/tickets (POST)â€� per inserire una richiesta di inserimento di un ticket
                ResponseEntity<String> insertTicketResponse = remedyInsertTicketServiceClient.insertTicket();
                if (insertTicketResponse.getStatusCode() == HttpStatus.OK) {
                    String ticketJsonResponse = insertTicketResponse.getBody();
                    ObjectNode node = new ObjectMapper().readValue(ticketJsonResponse, ObjectNode.class);
                    String ticketId = node.get("ticketId").getTextValue();

                    try {
                        // creazione file csv da allegare alla chiamata PUT /remwes/api/v1/tickets/{ticketId}/stato/info-dettagli
                        StringWriter stringWriter = creaFileCsvPerRichiestaCredenziali(utenteDto);
                        String uuidString = UUID.randomUUID().toString();
                        Timestamp dataRichiesta = Utils.sysdate();
                        // Effettuo una seconda chiamata al sistema Remedy, al servizio â€œ/tickets/{ticketId}/stato/info-dettagliâ€� (PUT),
                        // usando lâ€™identificativo del ticket ricevuto in risposta dal primo servizio chiamato e
                        // in allegato invio un file csv contenente le informazioni dell'utente per effettuare la richiesta delle credenziali RUPAR
                        ResponseEntity<String> infoDettagliResponse = remedyInfoDettagliServiceClient.infoDettagli(ticketId, stringWriter);
                        if (infoDettagliResponse.getStatusCode() == HttpStatus.OK) {
                            String infoDettagliJsonResponse = infoDettagliResponse.getBody();
                            ObjectMapper mapper = new ObjectMapper();
                            InfoNota infoNota = mapper.readValue(infoDettagliJsonResponse, InfoNota.class);
                            try {
                                UtenteDto operatore = Utils.getFirstRecord(utenteLowDao.findByCodiceFiscale(data.getUtente().getCodiceFiscale()));
                               Long id= popolaTabellaRichiestaCredenziali(utenteDto, operatore, ticketId, dataRichiesta);
                               setLogAuditSOLNew(OperazioneEnum.INSERT, ConstantsWebApp.KEY_OPER_RICHIESTA_CREDENZIALI_RUPAR, cf,uuidString,id.toString(),ConstantsWebApp.RICHIESTA_CREDENZIALI_RUPAR, data);
                                messaggiErrore.add(aggiungiErrori(ConstantsWebApp.SUCCESSO_SALVATAGGIO_RICHIESTA_CREDENZIALI_RUPAR));
                            } catch (PersistenceException pe) {
                                log.error(pe.getMessage());
                                pe.printStackTrace();
                                messaggiErrore.add(aggiungiErrori(ConstantsWebApp.ERRORE_SALVATAGGIO_RICHIESTA_CREDENZIALI_RUPAR));
                            }
                        } else {
                            log.error("ERRORE: info dettagli " + ticketId);
                            messaggiErrore.add(aggiungiErrori(ConstantsWebApp.ERRORE_INFO_DETTAGLI, ticketId, utenteDto.getCodiceFiscale()));
                        }
                    } catch (RestClientException | IOException e) {
                        log.error(e.getMessage());
                        e.printStackTrace();
                        messaggiErrore.add(aggiungiErrori(ConstantsWebApp.ERRORE_INFO_DETTAGLI, ticketId, utenteDto.getCodiceFiscale()));
                    }
                } else {
                    log.error("ERRORE: inserimento ticket");
                    messaggiErrore.add(aggiungiErrori(ConstantsWebApp.ERRORE_INSERIMENTO_TICKET, utenteDto.getCodiceFiscale()));
                }
            } catch (RestClientException | IOException e) {
                log.error(e.getMessage());
                e.printStackTrace();
                messaggiErrore.add(aggiungiErrori(ConstantsWebApp.ERRORE_INSERIMENTO_TICKET, utenteDto.getCodiceFiscale()));
            }
        }
        return messaggiErrore;
    }

    private boolean checkRichiestaCredenziali(List<MessaggiUtenteDto> messaggiErrore, UtenteDto utenteDto) throws Exception {
        RichiestaCredenzialiDto richiestaCredenzialiDto = new RichiestaCredenzialiDto();
        richiestaCredenzialiDto.setUtenteDto(utenteDto);
        richiestaCredenzialiDto = Utils.getFirstRecord(richiestaCredenzialiLowDao.findByIdUtente(utenteDto));
        if(richiestaCredenzialiDto != null){
            messaggiErrore.add(aggiungiErrori(ConstantsWebApp.RICHIESTA_CREDENZIALI_RUPAR_GIA_EFFETTUATA));
            return true;
        }
        return false;
    }

    private StringWriter creaFileCsvPerRichiestaCredenziali(UtenteDto utenteDto) {
        List<String> headerFields = Arrays.asList("CODICE FISCALE", "COGNOME", "NOME", "TIPO DI CREDENZIALE RICHIESTA", "INQUADRAMENTO CONTRATTUALE", "E_MAIL", "DENOMINAZIONE");
        // CAMPO DENOMINAZIONE (descrizione degli enti di appartenenza all'utente utenteDto)
        List<String> datiEnte = utenteDto.getUtenteCollocazioneList().stream().map(utenteCollocazioneDto -> {
            String descrizioneAzienda = utenteCollocazioneDto.getCollocazioneDto().getColDescAzienda();
            String descrizioneStruttura = utenteCollocazioneDto.getCollocazioneDto().getColDescrizione();
            if (!Utils.isEmpty(descrizioneStruttura))
                return descrizioneAzienda + "-" + descrizioneStruttura;
            else
                return descrizioneAzienda;
        }).filter(Objects::nonNull).distinct().collect(Collectors.toList());

        StringWriter writer = new StringWriter();
        headerFields.forEach(headerField -> {
            writer.write(headerField);
            if (headerFields.indexOf(headerField) != headerFields.size()-1)
                writer.append(Utils.SEPARATOR);
        });
        writer.append("\n");
        if (!Utils.listIsNullOrEmpty(datiEnte)) {
            datiEnte.forEach(ente -> {
                convertInfoUtenteToCsv(writer, utenteDto);
                writer.write(Utils.SEPARATOR);
                writer.write(Utils.escapeSpecialCharacters(ente));
                if (datiEnte.size() > 1)
                    writer.append("\n");
            });
        } else {
            convertInfoUtenteToCsv(writer, utenteDto);
        }
        writer.flush();

        return writer;
    }

    private void convertInfoUtenteToCsv(StringWriter writer, UtenteDto utenteDto) {
        writer.write(utenteDto.getCodiceFiscale().toUpperCase());
        writer.write(Utils.SEPARATOR);
        writer.write(Utils.escapeSpecialCharacters(utenteDto.getCognome()).toUpperCase());
        writer.write(Utils.SEPARATOR);
        writer.write(Utils.escapeSpecialCharacters(utenteDto.getNome()).toUpperCase());
        writer.write(Utils.SEPARATOR);
        writer.write("Login/Password/PIN");
        writer.write(Utils.SEPARATOR);
        String inquadramentoContrattuale = contrattoLowDao.findByPrimaryId(utenteDto.getTipoContrattoDto().getId()).getDescrizione();
        writer.write(inquadramentoContrattuale);
        writer.write(Utils.SEPARATOR);
        writer.write(utenteDto.getIndirizzoMail());
    }

    private Long popolaTabellaRichiestaCredenziali(UtenteDto utente, UtenteDto operatore, String numeroTicketRemedy,
                                                   Timestamp dataRichiesta) throws PersistenceException {
        RichiestaCredenzialiDto nuovaRichiestaCredenzialiDto = new RichiestaCredenzialiDto();
        nuovaRichiestaCredenzialiDto.setUtenteDto(utente);
        nuovaRichiestaCredenzialiDto.setDataRichiesta(dataRichiesta);
        nuovaRichiestaCredenzialiDto.setOperatoreDto(operatore);
        nuovaRichiestaCredenzialiDto.setTicketRemedy(numeroTicketRemedy);
        nuovaRichiestaCredenzialiDto.setDataInserimento(Utils.sysdate());
        richiestaCredenzialiLowDao.insert(nuovaRichiestaCredenzialiDto);
        
        return nuovaRichiestaCredenzialiDto.getId();
    }
    
    @Override
    public FormNuovoUtente getUtenteFromAuraOrMEF(String cf, Data data) throws WebServiceException {
    	Request screeningEpatiteCRequest = new Request();        
    	screeningEpatiteCRequest.setCodiceFiscale(cf);
    	
    	Timestamp chiamata = new Timestamp(System.currentTimeMillis());
    	String serviceCode = ConstantsWebApp.AURA_SEPAC_SERVICE_CODE;
    	String xmlRequest = "";
    	try {
    		xmlRequest = Utils.getXmlString(JAXBContext.newInstance(Request.class), screeningEpatiteCRequest);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	try {
	        Optional<Response> responseAura = Optional.ofNullable(screeningEpatiteCServiceClient.call(screeningEpatiteCRequest));
	
			Timestamp risposta = new Timestamp(System.currentTimeMillis());
			String xmlResponse = "";
	    	try {
	    		xmlResponse = Utils.getXmlString(JAXBContext.newInstance(Response.class), responseAura.get());
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	        WebServiceResponse auraResponse = checkAuraOrMEFResponse(responseAura);
	        switch (auraResponse) {
	            case PRESENTE:
	            	Response response = responseAura.get();
	            	
	            	saveLogServiziEsterni(data, serviceCode, xmlRequest, xmlResponse, chiamata, risposta, "0000", null);
	            	
	            	if(Utils.isEmpty(response.getDataDecesso())) {
		            	SimpleDateFormat from = new SimpleDateFormat("ddMMyyyy");
		            	SimpleDateFormat sdf = new SimpleDateFormat(ConstantsWebApp.DATE_PATTERN);
		            	FormNuovoUtente form = new FormNuovoUtente();
		                form.setCf(cf);
		                form.setIdAura(response.getIdAura());
		                form.setNome(response.getNome());
		                form.setCognome(response.getCognome());
		                try {
		                	form.setDataDiNascita(sdf.format(from.parse(response.getDataNascita())));
		                } catch (ParseException e) {
		                	form.setDataDiNascita(null);
		                }
		                form.setProvinciaDiNascita(response.getProvinciaNascita());
		                form.setComuneDiNascita(response.getComuneNascita());
		                form.setSesso(response.getSesso());
		                form.setEmail(response.getIndirizzoEmail());
		                form.setTelefono(response.getTelefonoResidenza());
	            	
		                return form;
	            	}
	            	return null;
	
	            case NON_PRESENTE:
	            	//Codice fiscale non trovato
	            	saveLogServiziEsterni(data, serviceCode, xmlRequest, xmlResponse, chiamata, risposta,
	            			"0001", ConstantsWebApp.ERRORE_AURA_SEPAC_CF_NON_TROVATO);
	                return null;
	
	            default:
	                throw new WebServiceException(ConstantsWebApp.ERRORE_WS_AURA);
	        }
    	} catch (SOAPFaultException e1) {
    		//AURA SOAP Fault
    		Timestamp risposta = new Timestamp(System.currentTimeMillis());
    		saveLogServiziEsterni(data, serviceCode, xmlRequest, null, chiamata, risposta,
    				"9999", ConstantsWebApp.ERRORE_AURA_SEPAC_SOAP_FAULT);
    		return null;
    		
    	} catch (WebServiceException e1) {
			//400 Could not send Message
    		Timestamp risposta = new Timestamp(System.currentTimeMillis());
    		saveLogServiziEsterni(data, serviceCode, xmlRequest, null, chiamata, risposta,
    				"9999", ConstantsWebApp.ERRORE_AURA_SEPAC_WS_EXCEPTION);
			return null;
			
		}
    }

    @Override
    public FormNuovoUtente getRuoliCollocazioniFromOpessan(FormNuovoUtente form, Data data) throws WebServiceException {
    	List<String> ruoli = new ArrayList<String>();
    	List<String> collocazioni = new ArrayList<String>();
    	
    	BigDecimal idAura = BigDecimal.valueOf(form.getIdAura());
    	
    	GetInfoOperatoreConf.Arg0 dettaglioOperatoreConfReq = new GetInfoOperatoreConf.Arg0();
    	ReqDettaglioOperatoreConf reqDettaglioOperatore = new ReqDettaglioOperatoreConf();
    	reqDettaglioOperatore.setIdAura(idAura);
    	reqDettaglioOperatore.setIndRappAttivi(BigDecimal.ONE);
    	dettaglioOperatoreConfReq.setBody(reqDettaglioOperatore);

    	Timestamp chiamata = new Timestamp(System.currentTimeMillis());
    	String serviceCode = ConstantsWebApp.OPESSAN_DETT_OP_SERVICE_CODE;
    	String xmlRequest = "";
    	try {
    		xmlRequest = Utils.getXmlString(JAXBContext.newInstance(GetInfoOperatoreConf.Arg0.class), dettaglioOperatoreConfReq);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
        try {
			Optional<it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatore.GetInfoOperatoreConfResponse.Return> dettaglioOperatoreResponse = 
					Optional.ofNullable(dettaglioOperatoreServiceClient.callInfoOperatoreConf(dettaglioOperatoreConfReq)); 
			
			Timestamp risposta = new Timestamp(System.currentTimeMillis());
			String xmlResponse = "";
	    	try {
	    		xmlResponse = Utils.getXmlString(JAXBContext.newInstance(it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatore.GetInfoOperatoreConfResponse.Return.class), dettaglioOperatoreResponse.get());
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
			WebServiceResponse opessanResponse = checkOpessanDettaglioOperatoreConfResponse(dettaglioOperatoreResponse);
			
			switch(opessanResponse) {
			case PRESENTE:
				saveLogServiziEsterni(data, serviceCode, xmlRequest, xmlResponse, chiamata, risposta, "0000", null);
				
				Return dettaglioOp = dettaglioOperatoreResponse.get();
				
				// Cerco ruoli e collocazioni nei rapporti di lavoro attivi
				InfoRapportoLavoroConf infoRappLavoro = dettaglioOp.getBody().getInfoRappLavoro();
				if(infoRappLavoro != null && infoRappLavoro.getInfo() != null && !infoRappLavoro.getInfo().isEmpty()) {
					for(InfoRapportoDiLavoroConf info : infoRappLavoro.getInfo()) {
						// Cerco ruolo per qualifica
						RuoloDto ruoloQualifica = ruoloLowDao.findByQualificaOpessan(info.getQualifica());
						if(ruoloQualifica != null && !ruoli.contains(ruoloQualifica.getId().toString()+"ro")) 
							ruoli.add(ruoloQualifica.getId().toString()+"ro");
						
						// Cerco collocazioni negli incarichi
						IncarichiConf incarichi = info.getIncarichi();
						if(incarichi != null && incarichi.getIncarico() != null && !incarichi.getIncarico().isEmpty()) {
							// Se ci sono incarichi per il rapporto di lavoro...
							for (ArrayIncarichiConf incarico : incarichi.getIncarico()) {
								// Cerco collocazione per codice azienda, codice UO e codice Multi Spec 
								CollocazioneDto collocazioneOp = collocazioneLowDao.findByCodUoAndCodMultiSpec(incarico.getCodAzienda(), incarico.getCodUoEsteso(), incarico.getCodMultiSpec());
								if(collocazioneOp != null && !collocazioni.contains(collocazioneOp.getColId().toString()+"ro")) 
									collocazioni.add(collocazioneOp.getColId().toString()+"ro");
							}
						} else {
							// Altrimenti cerco la collocazione azienda
							CollocazioneDto collocazioneOp = collocazioneLowDao.findByCodUoAndCodMultiSpec(info.getCodAzienda(), null, null);
							if(collocazioneOp != null && !collocazioni.contains(collocazioneOp.getColId().toString()+"ro")) 
								collocazioni.add(collocazioneOp.getColId().toString()+"ro");
						}
					}
				}
				
				// Cerco collocazioni degli studi medici
				StudiMediciConf elencoStudiMedici = dettaglioOp.getBody().getElencoStudiMedici();
				if(elencoStudiMedici != null && elencoStudiMedici.getStudioMedico() != null && !elencoStudiMedici.getStudioMedico().isEmpty()) {
					for(StudioMedicoConf studioMedico : elencoStudiMedici.getStudioMedico()) {
						// Cerco collocazione per id ambulatorio
						CollocazioneDto collocazioneOp = collocazioneLowDao.findByIdAmbulatorio(studioMedico.getIdAmbulatorio());
						if(collocazioneOp != null && !collocazioni.contains(collocazioneOp.getColId().toString()+"ro")) 
							collocazioni.add(collocazioneOp.getColId().toString()+"ro");
					}
				}

				ruoli = ruoli.stream().distinct().collect(Collectors.toList());
				collocazioni = collocazioni.stream().distinct().collect(Collectors.toList());
				
				// Chiamo il servizio Opessan dettaglioOperatoreDipendente 
				getRuoloCollocazioniFromDettaglioOpDip(ruoli, collocazioni, idAura, data);
				
				break;
				
			case NON_PRESENTE:
				saveLogServiziEsterni(data, serviceCode, xmlRequest, xmlResponse, chiamata, risposta,
            			"0001", ConstantsWebApp.ERRORE_OPESSAN_ID_AURA_NON_TROVATO);

				// Chiamo il servizio Opessan dettaglioOperatoreDipendente
				getRuoloCollocazioniFromDettaglioOpDip(ruoli, collocazioni, idAura, data);
				
				break;
				
			default:
			    throw new WebServiceException(ConstantsWebApp.ERRORE_WS_OPESSAN);
			}
        } catch (SOAPFaultException e1) {
    		//OPESSAN SOAP Fault
    		Timestamp risposta = new Timestamp(System.currentTimeMillis());
    		saveLogServiziEsterni(data, serviceCode, xmlRequest, null, chiamata, risposta,
    				"9999", ConstantsWebApp.ERRORE_OPESSAN_SOAP_FAULT);
    		return null;
    		
    	} catch (WebServiceException e1) {
			//400 Could not send Message
    		Timestamp risposta = new Timestamp(System.currentTimeMillis());
    		saveLogServiziEsterni(data, serviceCode, xmlRequest, null, chiamata, risposta,
    				"9999", ConstantsWebApp.ERRORE_OPESSAN_WS_EXCEPTION);
			return null;
			
		}
	
        form.setRuoli(ruoli);
        form.setRuoliFromOpessan(ruoli);
        form.setCollocazioni(collocazioni);
        form.setCollocazioniFromOpessan(collocazioni);
        
        return form;
    }

	private void getRuoloCollocazioniFromDettaglioOpDip(List<String> ruoli, List<String> collocazioni,
			BigDecimal idAura, Data data) throws WebServiceException {
		GetDettaglioConf.Arg0 getOperatoreDipendenteConfReq = new GetDettaglioConf.Arg0();
		GetDettaglioRequestBody reqOperatoreDipendente = new GetDettaglioRequestBody();
		reqOperatoreDipendente.setIdAura(idAura);
		getOperatoreDipendenteConfReq.setBody(reqOperatoreDipendente);
		
		Timestamp chiamata = new Timestamp(System.currentTimeMillis());
    	String serviceCode = ConstantsWebApp.OPESSAN_DETT_OP_DIP_SERVICE_CODE;
    	String xmlRequest = "";
    	try {
    		xmlRequest = Utils.getXmlString(JAXBContext.newInstance(GetDettaglioConf.Arg0.class), getOperatoreDipendenteConfReq);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
		try {
			Optional<it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatoreDipendente.GetDettaglioConfResponse2.Return> operatoreDipendenteResponse = 
					Optional.ofNullable(operatoreDipendenteServiceClient.call(getOperatoreDipendenteConfReq));
			
			Timestamp risposta = new Timestamp(System.currentTimeMillis());
			String xmlResponse = "";
	    	try {
	    		xmlResponse = Utils.getXmlString(JAXBContext.newInstance(it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatoreDipendente.GetDettaglioConfResponse2.Return.class), operatoreDipendenteResponse.get());
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			WebServiceResponse opessanDipResponse = checkOpessanOperatoreDipendenteConfResponse(operatoreDipendenteResponse);
			switch(opessanDipResponse) {
			 case PRESENTE:
				saveLogServiziEsterni(data, serviceCode, xmlRequest, xmlResponse, chiamata, risposta, "0000", null);
				it.csi.solconfig.configuratoreweb.wsdl.opessanDettaglioOperatoreDipendente.GetDettaglioConfResponse2.Return dettaglioOpDip = operatoreDipendenteResponse.get();
				 
				// Cerco ruoli nelle qualifiche
			 	InfoQualificheDipConf infoQualifiche = dettaglioOpDip.getBody().getInfoQualifiche();
			 	if(infoQualifiche != null && infoQualifiche.getInfo() != null && !infoQualifiche.getInfo().isEmpty()) {
			 		for(InfoQualificaDipConf info : infoQualifiche.getInfo()) {
			 			// Cerco ruolo per posizione funzionale e profilo professionale
			 			RuoloDto ruolo = ruoloLowDao.findByPosizioneAndProfilo(info.getDescrPosizioneFunzionale(), info.getDescrProfiloProfessionale());
			 			if(ruolo != null && !ruoli.contains(ruolo.getId().toString()+"ro")) 
			 				ruoli.add(ruolo.getId().toString()+"ro");
			 		}
			 	}
			 	
			 	// Cerco collocazioni nelle sedi operatorive
			 	InfoSediOperative infoSediOperative = dettaglioOpDip.getBody().getInfoSediOperative();
			 	if(infoSediOperative != null && infoSediOperative.getInfo() != null && !infoSediOperative.getInfo().isEmpty()) {
			 		for(InfoSedeOperativaConf info : infoSediOperative.getInfo()) {
			 			if(info.getDataFineSedeOp() == null) {
			 				// Cerco collocazione per codice Azienda, codice Struttura, codice UO, codice Multi Spec e codice Elemento Organizzativo
				 			CollocazioneDto collocazioneOp = collocazioneLowDao.findByCodStrutAndCodUoAndCodMultiSpecAndColElemOrg(info.getAzienda(), info.getCodStruttura(), info.getCodUoEsteso(), info.getCodMultiSpec(), info.getCodElemOrganiz());
				 			if(collocazioneOp != null && !collocazioni.contains(collocazioneOp.getColId().toString()+"ro"))
				 				collocazioni.add(collocazioneOp.getColId().toString()+"ro");
			 			}
			 		}
			 		
			 	}
			 	
			 	ruoli = ruoli.stream().distinct().collect(Collectors.toList());
				collocazioni = collocazioni.stream().distinct().collect(Collectors.toList());
			 	break;
			 	
			 case NON_PRESENTE:
				 saveLogServiziEsterni(data, serviceCode, xmlRequest, xmlResponse, chiamata, risposta,
	            			"0001", ConstantsWebApp.ERRORE_OPESSAN_ID_AURA_NON_TROVATO);
				 break;
				 
			 default:
			     throw new WebServiceException(ConstantsWebApp.ERRORE_WS_OPESSAN);
			 }
		} catch (SOAPFaultException e1) {
    		//OPESSAN SOAP Fault
    		Timestamp risposta = new Timestamp(System.currentTimeMillis());
    		saveLogServiziEsterni(data, serviceCode, xmlRequest, null, chiamata, risposta,
    				"9999", ConstantsWebApp.ERRORE_OPESSAN_SOAP_FAULT);
    	} catch (WebServiceException e1) {
			//400 Could not send Message
    		Timestamp risposta = new Timestamp(System.currentTimeMillis());
    		saveLogServiziEsterni(data, serviceCode, xmlRequest, null, chiamata, risposta,
    				"9999", ConstantsWebApp.ERRORE_OPESSAN_WS_EXCEPTION);
		}
	}

	@Override
	public boolean checkAbilitazione(Long id) throws Exception {
		UtenteDto utentePresenteSuDb = utenteLowDao.findByPrimaryId(id);
		return  utenteLowDao.checkUtente(utentePresenteSuDb.getCodiceFiscale());
				
	}
	
	public void aggiornaVisibilitaAzienda(Long idUtente,String codFiscOperatore,String codAzienda,Timestamp dataFineVal){
		UtenteDto utente= utenteLowDao.findByPrimaryId(idUtente);
		AziendaDto azienda=aziendaDaoLowDao.findAziendaByCodice(codAzienda);
		VisibilitaAziendaDto visibAz=visiblitaAziendaLowDao.findVisibilitaByIdUtenteAndIdAzienda(azienda.getIdAzienda(), idUtente);
		if(visibAz!=null) {
			visibAz.setCfOperatore(codFiscOperatore);
			visibAz.setDataOperazione(Utils.sysdate());
			visibAz.setDataFineValidita(dataFineVal);
			visiblitaAziendaLowDao.salva(visibAz);
			
			
		}else {
			VisibilitaAziendaDto visibilita= new VisibilitaAziendaDto();
			visibilita.setUtenteDto(utente);
			visibilita.setAziendaDto(azienda);
			visibilita.setCfOperatore(codFiscOperatore);
			visibilita.setDataInizioValidita(Utils.sysdate());
			visibilita.setDataOperazione(Utils.sysdate());
			visibilita.setDataInserimento(Utils.sysdate());
			visibilita.setDataFineValidita(dataFineVal);
			visiblitaAziendaLowDao.insert(visibilita);
		}
		
		
		
	}
	
	@Override
	public Boolean checkIsAbilitazioneConfiguratorePresente(Long idCollocazione,String codiceFiscale) {
		return !abilitazioneLowDao.findAbilitazioneConfiguratoreByIdCollocazioneAndCodiceFiscale(idCollocazione,codiceFiscale).isEmpty(); 
	}


	@Override
	public PaginaDTO<RisultatiRicercaAbilitazioneMassivaDTO> ricercaUtentiAbilitazioneMassiva(
			AbilitazioneMassivaModel abilitazioneMassivaModel, Data data) {
		if (abilitazioneMassivaModel.getNumeroPagina() < 1 || abilitazioneMassivaModel.getNumeroElementi() < 1) {
			log.error("ERROR: NumeroElementi e/o numeroPagina non corretti");
			return new PaginaDTO<>();
		}

		PaginaDTO<RisultatiRicercaAbilitazioneMassivaDTO> paginaDTO = new PaginaDTO<>();

		if (data != null && data.getUtente() != null && data.getUtente().getFunzionalitaAbilitate() != null
				&& !data.getUtente().getFunzionalitaAbilitate().isEmpty()) {

			Long idRuolo = 		  Long.valueOf(abilitazioneMassivaModel.getRuolo());
			Long idAzienda = 	  Long.valueOf(abilitazioneMassivaModel.getAzienda());
			Long idCollocazione = Utils.isEmpty(abilitazioneMassivaModel.getCollocazione()) ? null : Long.valueOf(abilitazioneMassivaModel.getCollocazione());
			Long idSol = 		  Utils.isEmpty(abilitazioneMassivaModel.getSol()) ? null : Long.valueOf(abilitazioneMassivaModel.getSol());

			Long count = utenteCollocazioneLowDao.countRicercaUtenteCollocazioneAbilitazioneMassiva(idRuolo, idAzienda, idCollocazione, idSol);
			List<UtenteCollocazioneDto> collocazioniUtente = utenteCollocazioneLowDao.ricercaUtenteCollocazioneAbilitazioneMassiva(idRuolo, idAzienda, idCollocazione, idSol, abilitazioneMassivaModel.getNumeroPagina(), abilitazioneMassivaModel.getNumeroElementi());
			
			List<RisultatiRicercaAbilitazioneMassivaDTO> ricercaAbilitazioneMassivaDTOList = mapListUtentiToListRisultatiRicercaAbilitazioneMassivaDTO(collocazioniUtente);

			paginaDTO.setElementi(ricercaAbilitazioneMassivaDTOList);
			paginaDTO.setElementiTotali(count);
			paginaDTO.setPagineTotali((int) Math.ceil((float) paginaDTO.getElementiTotali() / abilitazioneMassivaModel.getNumeroElementi()));
			
		}

		return paginaDTO;
	}
	
	@Override
	public PaginaDTO<RisultatiRicercaAbilitazioneMassivaDTO> ricercaUtentiDisabilitazioneMassiva(
			AbilitazioneMassivaModel abilitazioneMassivaModel, Data data) {
		if (abilitazioneMassivaModel.getNumeroPagina() < 1 || abilitazioneMassivaModel.getNumeroElementi() < 1) {
			log.error("ERROR: NumeroElementi e/o numeroPagina non corretti");
			return new PaginaDTO<>();
		}

		PaginaDTO<RisultatiRicercaAbilitazioneMassivaDTO> paginaDTO = new PaginaDTO<>();

		if (data != null && data.getUtente() != null && data.getUtente().getFunzionalitaAbilitate() != null
				&& !data.getUtente().getFunzionalitaAbilitate().isEmpty()) {

			Long idRuolo = 		  Utils.isEmpty(abilitazioneMassivaModel.getRuolo()) ? null : Long.valueOf(abilitazioneMassivaModel.getRuolo());
			Long idAzienda = 	  Long.valueOf(abilitazioneMassivaModel.getAzienda());
			Long idCollocazione = Utils.isEmpty(abilitazioneMassivaModel.getCollocazione()) ? null : Long.valueOf(abilitazioneMassivaModel.getCollocazione());
			Long idSol = 		  Utils.isEmpty(abilitazioneMassivaModel.getSol()) ? null : Long.valueOf(abilitazioneMassivaModel.getSol());

			Long count = utenteCollocazioneLowDao.countRicercaUtenteCollocazioneDisabilitazioneMassiva(idRuolo, idAzienda, idCollocazione, idSol);
			List<AbilitazioneDto> collocazioniUtente = utenteCollocazioneLowDao.ricercaUtenteCollocazioneDisabilitazioneMassiva(idRuolo, idAzienda, idCollocazione, idSol, abilitazioneMassivaModel.getNumeroPagina(), abilitazioneMassivaModel.getNumeroElementi());
			
			List<RisultatiRicercaAbilitazioneMassivaDTO> ricercaAbilitazioneMassivaDTOList = mapListUtentiToListRisultatiRicercaDisabilitazioneMassivaDTO(collocazioniUtente);

			paginaDTO.setElementi(ricercaAbilitazioneMassivaDTOList);
			paginaDTO.setElementiTotali(count);
			paginaDTO.setPagineTotali((int) Math.ceil((float) paginaDTO.getElementiTotali() / abilitazioneMassivaModel.getNumeroElementi()));
			
		}

		return paginaDTO;
	}

	 private List<RisultatiRicercaAbilitazioneMassivaDTO> mapListUtentiToListRisultatiRicercaAbilitazioneMassivaDTO(List<UtenteCollocazioneDto> collocazioniUtente) {
		 List<RisultatiRicercaAbilitazioneMassivaDTO> mappedList= new ArrayList<RisultatiRicercaAbilitazioneMassivaDTO>();
		 for(UtenteCollocazioneDto collocazioneUtente : collocazioniUtente) {
			 mappedList.add(new RisultatiRicercaAbilitazioneMassivaDTO(
					 collocazioneUtente.getId_utecol().toString(),
					 collocazioneUtente.getUtenteDto().getNome(),
					 collocazioneUtente.getUtenteDto().getCognome(),
					 collocazioneUtente.getUtenteDto().getCodiceFiscale(),
					 collocazioneUtente.getCollocazioneDto().getColCodice() + " - " + collocazioneUtente.getCollocazioneDto().getColDescrizione()
					 ));
		 }
		 return mappedList;
	 }
	 
	 private List<RisultatiRicercaAbilitazioneMassivaDTO> mapListUtentiToListRisultatiRicercaDisabilitazioneMassivaDTO(List<AbilitazioneDto> collocazioniUtente) {
		 List<RisultatiRicercaAbilitazioneMassivaDTO> mappedList= new ArrayList<RisultatiRicercaAbilitazioneMassivaDTO>();
		 for(AbilitazioneDto collocazioneUtente : collocazioniUtente) {
					 mappedList.add(new RisultatiRicercaAbilitazioneMassivaDTO(
					 collocazioneUtente.getId().toString(),
					 collocazioneUtente.getRuoloUtenteDto().getUtenteDto().getNome(),
					 collocazioneUtente.getRuoloUtenteDto().getUtenteDto().getCognome(),
					 collocazioneUtente.getRuoloUtenteDto().getUtenteDto().getCodiceFiscale(),
					 collocazioneUtente.getUtenteCollocazioneDto().getCollocazioneDto().getColCodice() + " - " + collocazioneUtente.getUtenteCollocazioneDto().getCollocazioneDto().getColDescrizione(),
					 collocazioneUtente.getRuoloUtenteDto().getRuoloDto().getDescrizione(),
					 collocazioneUtente.getTreeFunzionalitaDto().getFunzionalitaDto().getCodiceFunzione()
					 ));
		 }
		 return mappedList;
	 }

	@Override
	public void abilitazioneMassiva(AbilitazioneMassivaModel abilitazioneMassivaModel, Data data) {
		
		
		
		
		List<String> tempIdUtenti= new ArrayList<String>();
		abilitazioneMassivaModel.getSelected().forEach(f->{
			f=f.replace("|",",");
			f=f.split(",")[1];
			tempIdUtenti.add(f);
			
		});
		
		
		List<Long> idUtentiCollocazione= tempIdUtenti.stream().map(Long::valueOf).collect(Collectors.toList());
		
		Long idRuolo = 		  Utils.isEmpty(abilitazioneMassivaModel.getRuolo()) ? null : Long.valueOf(abilitazioneMassivaModel.getRuolo());
		Long idAzienda = 	  Long.valueOf(abilitazioneMassivaModel.getAzienda());
		Long idCollocazione = Utils.isEmpty(abilitazioneMassivaModel.getCollocazione()) ? null : Long.valueOf(abilitazioneMassivaModel.getCollocazione());
		//Id del sol selezionato per abilitazione
		Long idSol = 		  Utils.isEmpty(abilitazioneMassivaModel.getSolSelezionabili()) ? null : Long.valueOf(abilitazioneMassivaModel.getSolSelezionabili());
		Long idProfilo = 	  Utils.isEmpty(abilitazioneMassivaModel.getProfili()) ? null : Long.valueOf(abilitazioneMassivaModel.getProfili());
		//Id del sol selezionato in fase di ricerca
		Long sol = 		  	  Utils.isEmpty(abilitazioneMassivaModel.getSol()) ? null : Long.valueOf(abilitazioneMassivaModel.getSol());
		
		if(abilitazioneMassivaModel.isAllSelected()) {
			if(abilitazioneMassivaModel.isDisabilitazione()) {
				idUtentiCollocazione=utenteCollocazioneLowDao.ricercaIDUtenteCollocazioneDisabilitazioneMassiva(idRuolo, idAzienda, idCollocazione, sol);
			}else {
				
				idUtentiCollocazione=utenteCollocazioneLowDao.ricercaIDUtenteCollocazioneAbilitazioneMassiva(idRuolo, idAzienda, idCollocazione, sol);
			}
		}
		
		BatchAbilitazioneMassivaDto batch= new BatchAbilitazioneMassivaDto();
		batch.setStatoBatch(statoBatchLowDao.findByStato("DAELAB"));
		batch.setCodiceFiscaleOperatore(data.getUtente().getCodiceFiscale());
		batch.setDisabilitazione(abilitazioneMassivaModel.isDisabilitazione());
		batch.setDataInserimento(Utils.sysdate());
		
		batch=batchAbilitazioneLowDao.salva(batch);
		BatchAbilitazioneMassivaDto finalBatch = batch;
				
		idUtentiCollocazione.forEach(utente->{
			BatchAbilitazioneMassivaUtentiDto b = new BatchAbilitazioneMassivaUtentiDto();
			b.setIdBatch(finalBatch);
			UtenteCollocazioneDto u= new UtenteCollocazioneDto();
			AbilitazioneDto abilitazione= new AbilitazioneDto();
			if(abilitazioneMassivaModel.isDisabilitazione()) {
				abilitazione= abilitazioneLowDao.findByPrimaryId(utente);
				u = abilitazione.getUtenteCollocazioneDto();
			}else {
				
				u = utenteCollocazioneLowDao.findByPrimaryId(utente);
			}
			b.setUtente(u.getUtenteDto());
			b.setCollocazione(u.getCollocazioneDto());
			if(abilitazioneMassivaModel.isDisabilitazione()) {
				  b.setAbilitazione(applicazioneLowDao.findByPrimaryId(sol));
				}else {
				  b.setAbilitazione(applicazioneLowDao.findByPrimaryId(idSol));
				}
			if(!abilitazioneMassivaModel.isDisabilitazione()) {
				b.setProfilo(funzionalitaLowDao.findByPrimaryId(idProfilo));
				}else {
					b.setProfilo(abilitazione.getTreeFunzionalitaDto().getFunzionalitaDto());
				}
			if(abilitazioneMassivaModel.isDisabilitazione()) {
				b.setRuolo(abilitazione.getRuoloUtenteDto().getRuoloDto());
			}else {
				if(idRuolo!=null) {
					b.setRuolo(ruoloLowDao.findByPrimaryId(idRuolo));
					}
			}
			if(idRuolo!=null) {
				b.setRuolo(ruoloLowDao.findByPrimaryId(idRuolo));
				
			}
			b.setDataInserimento(Utils.sysdate());
			
			try {
			    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			    Date parsedDate = dateFormat.parse(abilitazioneMassivaModel.getDataFineValidita());
			    Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
			
			    b.setDataFineValidita(timestamp);
			} catch(Exception e) { //this generic but you can control another types of exception
				log.error("ERROR: abilitazioneMassiva dataFineValidita: ", e);
			}
			b=batchAbilitazioneUtentiLowDao.salva(b);
			
			
		});
		
		
		if(abilitazioneMassivaModel.isDisabilitazione()) {
			// Scrittura log Audit
	        try {
	    		setLogAuditSOLNew(OperazioneEnum.INSERT, ConstantsWebApp.KEY_OPER_DISABILITAZIONE_MASSIVA_UTENTI, null, 
	            		UUID.randomUUID().toString(), batch.getId().toString(), ConstantsWebApp.DISABILITAZIONE_MASSIVA_UTENTI, data);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else {
			// Scrittura log Audit
	        try {
	    		setLogAuditSOLNew(OperazioneEnum.INSERT, ConstantsWebApp.KEY_OPER_ABILITAZIONE_MASSIVA_UTENTI, null, 
	            		UUID.randomUUID().toString(), batch.getId().toString(), ConstantsWebApp.ABILITAZIONE_MASSIVA_UTENTI, data);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	
		
	}

	@Override
	public List<StoricoDTO> findStoricoByUtente(Data data,boolean disabilitazione) {
		
		List<StoricoDTO> storico=new ArrayList<StoricoDTO>();
		
		
		List<BatchAbilitazioneMassivaDto> batch= batchAbilitazioneLowDao.findByCfOperatore(data.getUtente().getCodiceFiscale(),disabilitazione);
		
		
		batch.forEach(b->{
			StoricoDTO st= new StoricoDTO();
			st.setId(b.getId().toString());
			st.setStato(b.getStatoBatch().getDescrizione());
			st.setDataInserimento(Utils.dateToString(new Date(b.getDataInserimento().getTime()), true));
			st.setProgresso(batchAbilitazioneUtentiLowDao.progressoBatch(b.getId()));
			//Prendo solo il primo di questa lista in quanto sol e profilo( per abilitazione) sono uguali 
			BatchAbilitazioneMassivaUtentiDto temp= batchAbilitazioneUtentiLowDao.findByIdBatch(b.getId()).get(0);
			st.setSol(temp.getAbilitazione().getDescrizione());
			if(!disabilitazione) {
			  st.setProfilo(temp.getProfilo().getCodiceFunzione());
			}
			if(b.getCsvUtentiInseriti() !=null && b.getCsvUtentiNonInseriti() !=null) {
				st.setDownload(false);
			}else {
				st.setDownload(true);
			}
			storico.add(st);
			
		});
		
		// Scrittura log Audit
        try {
    		setLogAuditSOLNew(OperazioneEnum.READ, ConstantsWebApp.KEY_OPER_STORICO, null, 
            		UUID.randomUUID().toString(), null, ConstantsWebApp.STORICO, data);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return storico;
	}

	@Override
	public boolean checkBatchInElaborazione(Data data, List<String> stati) {
		
		return !batchAbilitazioneLowDao.findBatchByStatoAndByCfOperatore(data.getUtente().getCodiceFiscale(), stati).isEmpty();
	}

	@Override
	public byte[] downloadCsv(Long idBatch, boolean inseriti) {
	
		BatchAbilitazioneMassivaDto batch= batchAbilitazioneLowDao.findByPrimaryId(idBatch);
		
		if(inseriti) {
			return Base64.getDecoder().decode(batch.getCsvUtentiInseriti());
		}else {
			return Base64.getDecoder().decode(batch.getCsvUtentiNonInseriti());
		}
	}

	@Override
	public String getMessaggioAura(String msg, String app, Boolean errore) {
		String messaggio = "";
		String azione = msg.equals("0") ? "disabilitazione" : msg.equals("1") ? "abilitazione" : "modifica data fine validita'"; 
		if(errore) {
			messaggio = ricercaMessaggiErroreByCod(ConstantsWebApp.ERRORE_COMPILAZIONE_TESTO_MAIL_AURA);
		} else {
			if(azione.equals("abilitazione"))
				messaggio = ricercaMessaggiErroreByCod(ConstantsWebApp.MSG_ABILITAZIONE_AURA);
			else if(azione.equals("disabilitazione"))
				messaggio = ricercaMessaggiErroreByCod(ConstantsWebApp.MSG_DISABILITAZIONE_AURA);
		}
		return messaggio.replace("@APP@", app).replace("@AZIONE@", azione);
	}
	
	  public boolean validateParametriLogAudit(OperazioneEnum operazione) {
	        if (operazione.getValue() == null || operazione.getValue().isEmpty()) {
	            log.error("Operazione deve essere valorizzato");
	            return false;
	        }
	        return true;
	    }
	  
	  public String generaIdApp(String codiceAmbiente, String codiceUnitaInstallazione) {
	        return ConstantsWebApp.CODICE_PRODOTTO + "_" + ConstantsWebApp.CODICE_LINEA_CLIENTE + "_" + codiceAmbiente + "_" + codiceUnitaInstallazione;
	    }
	


}

