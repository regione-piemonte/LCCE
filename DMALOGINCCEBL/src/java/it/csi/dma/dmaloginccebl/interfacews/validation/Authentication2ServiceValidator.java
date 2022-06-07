/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.interfacews.validation;

import it.csi.dma.dmaloginccebl.business.dao.*;
import it.csi.dma.dmaloginccebl.business.dao.dto.*;
import it.csi.dma.dmaloginccebl.business.dao.util.CatalogoLog;
import it.csi.dma.dmaloginccebl.integration.LogGeneralDaoBean;
import it.csi.dma.dmaloginccebl.interfacews.authentication2.GetAuthentication2Request;
import it.csi.dma.dmaloginccebl.interfacews.authentication2.Richiedente;
import it.csi.dma.dmaloginccebl.interfacews.authentication2.ValidateGetAuthentication2Response;
import it.csi.dma.dmaloginccebl.interfacews.msg.Errore;
import it.csi.dma.dmaloginccebl.interfacews.msg.ParametriLogin;
import it.csi.dma.dmaloginccebl.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Component
public class Authentication2ServiceValidator extends BaseServiceValidator{

    @Autowired
    RuoloLowDao ruoloLowDao;

    @Autowired
    CatalogoParametriLowDao catalogoParametriLowDao;

    @Autowired
    UtenteLowDao utenteLowDao;

    @Autowired
    RuoloUtenteLowDao ruoloUtenteLowDao;

    @Autowired
    AbilitazioneLowDao abilitazioneLowDao;

    @Autowired
    ApplicazioneLowDao applicazioneLowDao;

    @Autowired
    ServiziLowDao serviziLowDao;

    @Autowired
    CredenzialiServiziLowDao credenzialiServiziLowDao;

    @Autowired
    CollocazioneLowDao collocazioneLowDao;

    @Autowired
    UtenteCollocazioneLowDao utenteCollocazioneLowDao;

    @Autowired
    TreeFunzionalitaLowDao treeFunzionalitaLowDao;

    @Autowired
    FunzionalitaLowDao funzionalitaLowDao;

    /**
     * Verificare i parametri di input obbligatori e necessarie codifiche da database
     * @param getAuthenticationRequest
     * @param logGeneralDaoBean
     * @return
     */
    public ValidateGetAuthentication2Response validate(GetAuthentication2Request getAuthenticationRequest,
                                                       LogGeneralDaoBean logGeneralDaoBean,
                                                       ValidateGetAuthentication2Response validateGetAuthentication2Response) throws Exception {

        List<Errore> errori = validateGetAuthentication2Response.getListaErrori();
        Richiedente richiedente = getAuthenticationRequest.getRichiedente();

        if(richiedente != null){
            //Verifica CodFiscaleRichiedente
            validateGetAuthentication2Response.setUtenteDto(
                    verificaCfRichiedente(logGeneralDaoBean, errori, richiedente.getCodiceFiscaleRichiedente()));
            //Verifica Ruolo
            validateGetAuthentication2Response.setRuoloDto(verificaRuolo(logGeneralDaoBean,errori,richiedente.getRuolo()));
            //Verifica CollCodAzienda
            verificaCampoObbligatorio(logGeneralDaoBean, errori, richiedente.getCollCodiceAzienda(), CatalogoLog.COLL_COD_AZIENDA_OBBLIGATORIO);
            //Verifica codiceCollocazione
            verificaCampoObbligatorio(logGeneralDaoBean, errori, richiedente.getCodiceCollocazione(), CatalogoLog.CODICE_COLLOCAZIONE_OBBLIGATORIO);
            //Verifica ipClient
            verificaCampoObbligatorio(logGeneralDaoBean, errori, richiedente.getIpClient(), CatalogoLog.IPCLIENT_OBBLIGATORIO);
            //Verifica applicazione richiesta
            validateGetAuthentication2Response.setApplicazioneRichiestaDto(
                    verificaApplicazione(logGeneralDaoBean,
                            errori, richiedente.getApplicazioneRichiesta(),
                            CatalogoLog.APPLICAZIONE_RICHIESTA_OBBLIGATORIO, CatalogoLog.APPLICAZIONE_RICHIESTA_ERRATA));
            //Verifica applicazione chiamante
            validateGetAuthentication2Response.setApplicazioneChiamanteDto(
                    verificaApplicazione(logGeneralDaoBean, errori,
                            richiedente.getApplicazioneChiamante(), CatalogoLog.APPLICAZIONE_CHIAMANTE_OBBLIGATORIA,
                            CatalogoLog.APPLICAZIONE_CHIAMANTE_ERRATA));
            //Verifica ParametriLogin
            if(validateGetAuthentication2Response.getApplicazioneRichiestaDto() != null){
                verificaParametriLogin(logGeneralDaoBean, errori,
                        getAuthenticationRequest.getParametriLogin(), validateGetAuthentication2Response.getApplicazioneRichiestaDto());
            }
        }else{
            //Errore richiedente non presente
            errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.RICHIEDENTE_OBBLIGATORIO.getValue()));
        }

        validateGetAuthentication2Response.setListaErrori(errori);

        return validateGetAuthentication2Response;
    }

    public ValidateGetAuthentication2Response verificaAbilitazione(LogGeneralDaoBean logGeneralDaoBean, ValidateGetAuthentication2Response validateGetAuthentication2Response,
                                                String codiceCollocazione, String codiceCollAzienda) throws Exception {

        List<Errore> errori = validateGetAuthentication2Response.getListaErrori();
        RuoloDto ruoloDto = validateGetAuthentication2Response.getRuoloDto();
        UtenteDto utenteDto = validateGetAuthentication2Response.getUtenteDto();
        ApplicazioneDto applicazioneDto = validateGetAuthentication2Response.getApplicazioneRichiestaDto();

        AbilitazioneDto abilitazioneDto = null;
        //Ricerca ruoloUtente
        RuoloUtenteDto ruoloUtenteDto = getRuoloUtenteDto(ruoloDto, utenteDto);
        //Ricerca della collocazione
        CollocazioneDto collocazioneDto = getCollocazioneDto(codiceCollocazione, codiceCollAzienda);
        //Ricerca utenteCollocazione
        UtenteCollocazioneDto utenteCollocazioneDto = getUtenteCollocazioneDto(utenteDto, collocazioneDto);
        if(ruoloUtenteDto == null || collocazioneDto == null || utenteCollocazioneDto == null){
            errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.ABILITAZIONE_NON_VALIDA_AUTH2.getValue()));
        }else{
            abilitazioneDto = new AbilitazioneDto();
            abilitazioneDto.setRuoloUtenteDto(ruoloUtenteDto);
            //aggiunger come filtro la collocazione
            abilitazioneDto.setUtenteCollocazioneDto(utenteCollocazioneDto);
            //creare query apposita
            Collection<AbilitazioneDto> abilitazioneDtoList = abilitazioneLowDao.findAbilitazioneGetAbilitazioni(abilitazioneDto);
            boolean isApplicazioneCorretta = false;
            if(abilitazioneDtoList != null && !abilitazioneDtoList.isEmpty()){
                for(AbilitazioneDto abilitazione : abilitazioneDtoList){
                    if(abilitazione.getTreeFunzionalitaDto() != null &&
                            abilitazione.getTreeFunzionalitaDto().getFunzionalitaDto() != null &&
                            abilitazione.getTreeFunzionalitaDto().getFunzionalitaDto().getApplicazioneDto() != null) {
                        if(abilitazione.getTreeFunzionalitaDto().getFunzionalitaDto().getApplicazioneDto().getCodice().equals(applicazioneDto.getCodice())){
                            isApplicazioneCorretta = true;
                            break;
                        }else{
                            isApplicazioneCorretta = getCodiciApplicazioneSons(abilitazione, applicazioneDto);
                            if(isApplicazioneCorretta) break;
                        }
                    }else{
                        if(abilitazione.getTreeFunzionalitaDto() != null){
                            isApplicazioneCorretta = getCodiciApplicazioneSons(abilitazione, applicazioneDto);
                            if(isApplicazioneCorretta) break;
                        }
                    }
                }
            }

            if(!isApplicazioneCorretta){
                errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.ABILITAZIONE_NON_VALIDA_AUTH2.getValue()));
            }

        }
//        validateGetAuthentication2Response.setAbilitazioneDto(abilitazioneDto);
        validateGetAuthentication2Response.setListaErrori(errori);
        return validateGetAuthentication2Response;
    }

    private UtenteCollocazioneDto getUtenteCollocazioneDto(UtenteDto utenteDto, CollocazioneDto collocazioneDto) {
        UtenteCollocazioneDto utenteCollocazioneDto = new UtenteCollocazioneDto();
        if(collocazioneDto != null){
            utenteCollocazioneDto.setUtenteDto(utenteDto);
            utenteCollocazioneDto.setCollocazioneDto(collocazioneDto);
            utenteCollocazioneDto = Utils.getFirstRecord(utenteCollocazioneLowDao.findByUtenteAndCollocazioneAndValidita(utenteCollocazioneDto));
        }
        return utenteCollocazioneDto;
    }

    private CollocazioneDto getCollocazioneDto(String codiceCollocazione, String codiceCollAzienda) throws Exception {
        CollocazioneDto collocazioneDto = new CollocazioneDto();
        collocazioneDto.setColCodice(codiceCollocazione);
        collocazioneDto.setColCodAzienda(codiceCollAzienda);
        collocazioneDto = Utils.getFirstRecord(collocazioneLowDao.findByFilter(collocazioneDto));
        return collocazioneDto;
    }

    private RuoloUtenteDto getRuoloUtenteDto(RuoloDto ruoloDto, UtenteDto utenteDto) {
        RuoloUtenteDto ruoloUtenteDto = new RuoloUtenteDto();
        ruoloUtenteDto.setRuoloDto(ruoloDto);
        ruoloUtenteDto.setUtenteDto(utenteDto);
        ruoloUtenteDto = Utils.getFirstRecord(ruoloUtenteLowDao.findByUtenteRuoloAndData(ruoloUtenteDto));
        return ruoloUtenteDto;
    }

    private boolean getCodiciApplicazioneSons(AbilitazioneDto abilitazioneDto, ApplicazioneDto applicazioneDto) {
        TreeFunzionalitaDto treeFunzionalitaDto = new TreeFunzionalitaDto();
        treeFunzionalitaDto.setIdTreeFunzione(abilitazioneDto.getTreeFunzionalitaDto().getIdTreeFunzione());
        List<TreeFunzionalitaDto> funzionalitaSonsList
                = treeFunzionalitaLowDao.findFunzionalitaSons(treeFunzionalitaDto);
        if(funzionalitaSonsList != null && !funzionalitaSonsList.isEmpty()){
            for(TreeFunzionalitaDto treeFunzionalita : funzionalitaSonsList){
                FunzionalitaDto funzionalita =
                        funzionalitaLowDao.findByPrimaryId(treeFunzionalita.getFunzionalitaDto().getIdFunzione());
                if(funzionalita.getApplicazioneDto() != null &&
                    funzionalita.getApplicazioneDto().getCodice().equals(applicazioneDto.getCodice())){
                    return true;
                }
            }
        }
        return false;
    }

    private UtenteDto verificaCfRichiedente(LogGeneralDaoBean logGeneralDaoBean, List<Errore> errori, String cfRichiedente) throws Exception {
        UtenteDto utenteDto = null;
        if(cfRichiedente == null ||
                cfRichiedente.isEmpty()){
            //Errore cfRichiedente non presente
            errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.CODICE_FISCALE_OBBLIGATORIO.getValue()));
        }else{
            //Controllo presenza cfRichiedente
            utenteDto = new UtenteDto();
            utenteDto.setCodiceFiscale(cfRichiedente);
            utenteDto = Utils.getFirstRecord(utenteLowDao.findByFilter(utenteDto));
            if(utenteDto == null){
                //Se non trovato vuol dire che non e' una codifica presente nel DB
                errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.CODICE_FISCALE_ERRATO.getValue()));
            }
        }
        return utenteDto;
    }


    private RuoloDto verificaRuolo(LogGeneralDaoBean logGeneralDaoBean, List<Errore> errori, String codiceRuolo) {
        RuoloDto ruoloDto = null;
        if(codiceRuolo == null ||
                codiceRuolo.isEmpty()){
            //Errore ruolo non presente
            errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.RUOLO_OBBLIGATORIO.getValue()));
        }else{
            //Controllo codifica ruolo
            ruoloDto = Utils.getFirstRecord(ruoloLowDao.findByCodice(new RuoloDto(), codiceRuolo));
            if(ruoloDto == null){
                //Se non trovato vuol dire che non e' una codifica presente nel DB
                errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.RUOLO_ERRATO.getValue()));
            }
        }
        return ruoloDto;
    }

    private void verificaParametriLogin(LogGeneralDaoBean logGeneralDaoBean, List<Errore> errori,
                                        List<ParametriLogin> parametriLoginList, ApplicazioneDto applicazioneDto) {

        CatalogoParametriDto catalogoParametriDto = new CatalogoParametriDto();
        catalogoParametriDto.setApplicazioneDto(applicazioneDto);
        String parametriErrore = null;
        if(parametriLoginList != null && !parametriLoginList.isEmpty()){
            for(ParametriLogin parametro : parametriLoginList){
                if(parametro.getCodice() != null && !parametro.getCodice().isEmpty()){
                    catalogoParametriDto.setCodice(parametro.getCodice());
                    if(Utils.getFirstRecord(catalogoParametriLowDao.findByCodiceAndApplicazione(catalogoParametriDto)) == null) {
                        parametriErrore = appendParametriErrore(parametriErrore, parametro);
                    }
                }
            }
            if(parametriErrore != null){
                errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(),
                        CatalogoLog.PARAMETRI_AGGIUNTIVI_ERRATI.getValue(), parametriErrore, applicazioneDto.getCodice()));
            }
        }
    }

    public String appendParametriErrore(String parametriErrore, ParametriLogin parametro) {
        if(parametriErrore == null){
            parametriErrore = parametro.getCodice();
        }else{
            parametriErrore = parametriErrore + ", "+parametro.getCodice();
        }
        return parametriErrore;
    }

    private ApplicazioneDto verificaApplicazione(LogGeneralDaoBean logGeneralDaoBean, List<Errore> errori,
                                                         String codApplicazione, CatalogoLog codiceErroreObbligatorieta,
                                                         CatalogoLog codiceErroreValidita) {

        ApplicazioneDto applicazioneDto = null;

        if(codApplicazione == null || codApplicazione.isEmpty()){
            errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), codiceErroreObbligatorieta.getValue()));
        }else{
            applicazioneDto = verificaApplicazione(logGeneralDaoBean, errori, codApplicazione,  codiceErroreValidita);
        }

        return applicazioneDto;
    }

    private ApplicazioneDto verificaApplicazione(LogGeneralDaoBean logGeneralDaoBean, List<Errore> errori, String codApplicazione, CatalogoLog codiceErrore) {
        ApplicazioneDto applicazioneDto = null;

        applicazioneDto = Utils.getFirstRecord(
                applicazioneLowDao.findByCodice(new ApplicazioneDto(), codApplicazione));

        if(applicazioneDto == null){
            errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), codiceErrore.getValue()));
        }
        return applicazioneDto;
    }

    public CredenzialiServiziDto verificaCredenzialiServizio(String codiceServizio) {
        ServiziDto serviziDto = Utils.getFirstRecord(serviziLowDao.findByCodice(new ServiziDto(), codiceServizio));
        CredenzialiServiziDto credenzialiServiziDto = new CredenzialiServiziDto();
        credenzialiServiziDto.setServiziDto(serviziDto);
        credenzialiServiziDto = Utils.getFirstRecord(credenzialiServiziLowDao.findByFilterAndServizioAndData(credenzialiServiziDto));
        return credenzialiServiziDto;
    }
}
