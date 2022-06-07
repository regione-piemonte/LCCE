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
import it.csi.dma.dmaloginccebl.interfacews.abilitazione.GetAbilitazioniRequest;
import it.csi.dma.dmaloginccebl.interfacews.abilitazione.Richiedente;
import it.csi.dma.dmaloginccebl.interfacews.abilitazione.ValidateAbilitazioneResponse;
import it.csi.dma.dmaloginccebl.interfacews.msg.Errore;
import it.csi.dma.dmaloginccebl.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class AbilitazioneServiceValidator extends BaseServiceValidator{

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

    /**
     * Verificare i parametri di input obbligatori e necessarie codifiche da database
     * @param getAbilitazioniRequest
     * @param logGeneralDaoBean
     * @return
     */
    public ValidateAbilitazioneResponse validate(GetAbilitazioniRequest getAbilitazioniRequest,
                                                 LogGeneralDaoBean logGeneralDaoBean,
                                                 ValidateAbilitazioneResponse validateAbilitazioniResponse) throws Exception {

        List<Errore> errori = new ArrayList<Errore>();
        Richiedente richiedente = getAbilitazioniRequest.getRichiedente();

        if(richiedente != null){
            //Verifica CodFiscaleRichiedente
            validateAbilitazioniResponse.setUtenteDto(
                    verificaCfRichiedente(logGeneralDaoBean, errori, richiedente.getCodiceFiscaleRichiedente()));
            //Verifica Ruolo
            validateAbilitazioniResponse.setRuoloDto(verificaRuolo(logGeneralDaoBean,errori,richiedente.getCodiceRuoloRichiedente()));
            //Verifica CollCodAzienda
            verificaCampoObbligatorio(logGeneralDaoBean, errori, richiedente.getCollCodiceAziendaRichiedente(), CatalogoLog.COLL_COD_AZIENDA_OBBLIGATORIO);
            //Verifica codiceCollocazione
            validateAbilitazioniResponse.setCollocazioneDto(verificaCollocazione(logGeneralDaoBean, errori,
                    richiedente.getCodiceCollocazioneRichiedente(), richiedente.getCollCodiceAziendaRichiedente()));
            //Verifica ipClient
            verificaCampoObbligatorio(logGeneralDaoBean, errori, richiedente.getIpChiamante(), CatalogoLog.IPCLIENT_OBBLIGATORIO);
            //Verifica applicazione CHIAMANTE
            validateAbilitazioniResponse.setApplicazioneDto(
                    verificaApplicazione(logGeneralDaoBean,
                            errori, richiedente.getApplicazioneChiamante(),
                            CatalogoLog.APPLICAZIONE_CHIAMANTE_OBBLIGATORIA, CatalogoLog.APPLICAZIONE_CHIAMANTE_ERRATA));
        }else{
            //Errore richiedente non presente
            errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.RICHIEDENTE_OBBLIGATORIO.getValue()));
        }

        validateAbilitazioniResponse.setListaErrori(errori);

        return validateAbilitazioniResponse;
    }

    public ValidateAbilitazioneResponse verificaCampiInterdipentendi(GetAbilitazioniRequest getAbilitazioniRequest, LogGeneralDaoBean logGeneralDaoBean, ValidateAbilitazioneResponse validateAbilitazioniResponse) {
        List<Errore> errori = validateAbilitazioniResponse.getListaErrori();

        //Verifica ruoloUtente
        validateAbilitazioniResponse.setRuoloUtenteDto(verificaRuoloUtente(logGeneralDaoBean, errori, validateAbilitazioniResponse));
        //Verifica utente collocazione
        validateAbilitazioniResponse.setUtenteCollocazioneDto(verificaUtenteCollocazione(logGeneralDaoBean, validateAbilitazioniResponse));
        //Verifica congruienza azienda con collocazione
        if(!validateAbilitazioniResponse.getCollocazioneDto().getColCodAzienda().equals(getAbilitazioniRequest.getRichiedente().getCollCodiceAziendaRichiedente())){
            errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.COLL_AZIENDA_ERRATA.getValue()));
        }

        validateAbilitazioniResponse.setListaErrori(errori);
        return validateAbilitazioniResponse;
    }

    private UtenteCollocazioneDto verificaUtenteCollocazione(LogGeneralDaoBean logGeneralDaoBean, ValidateAbilitazioneResponse validateAbilitazioniResponse) {
        List<Errore> errori = validateAbilitazioniResponse.getListaErrori();

        UtenteCollocazioneDto utenteCollocazioneDto = new UtenteCollocazioneDto();
        utenteCollocazioneDto.setUtenteDto(validateAbilitazioniResponse.getUtenteDto());
        utenteCollocazioneDto.setCollocazioneDto(validateAbilitazioniResponse.getCollocazioneDto());
        utenteCollocazioneDto = Utils.getFirstRecord(utenteCollocazioneLowDao.findByUtenteAndCollocazioneAndValidita(utenteCollocazioneDto));
        if(utenteCollocazioneDto == null){
            errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.INCONGRUENZA_CODICE_FISCALE_UTENTE.getValue()));
        }
        return utenteCollocazioneDto;
    }

    private RuoloUtenteDto verificaRuoloUtente(LogGeneralDaoBean logGeneralDaoBean, List<Errore> errori, ValidateAbilitazioneResponse validateAbilitazioniResponse) {
        RuoloUtenteDto ruoloUtenteDto = new RuoloUtenteDto();
        ruoloUtenteDto.setRuoloDto(validateAbilitazioniResponse.getRuoloDto());
        ruoloUtenteDto.setUtenteDto(validateAbilitazioniResponse.getUtenteDto());
        ruoloUtenteDto = Utils.getFirstRecord(ruoloUtenteLowDao.findByUtenteRuoloAndData(ruoloUtenteDto));
        if(ruoloUtenteDto == null){
            errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.INCONGRUENZA_CODICE_FISCALE_UTENTE.getValue()));
        }
        return ruoloUtenteDto;
    }

    public ValidateAbilitazioneResponse verificaAbilitazione(LogGeneralDaoBean logGeneralDaoBean, ValidateAbilitazioneResponse validateAbilitazioniResponse) throws Exception {

        List<Errore> errori = validateAbilitazioniResponse.getListaErrori();

        Collection<AbilitazioneDto> abilitazioneDtoList = null;
        AbilitazioneDto abilitazioneDto = new AbilitazioneDto();

        abilitazioneDto.setRuoloUtenteDto(validateAbilitazioniResponse.getRuoloUtenteDto());
        abilitazioneDto.setUtenteCollocazioneDto(validateAbilitazioniResponse.getUtenteCollocazioneDto());

        abilitazioneDtoList = abilitazioneLowDao.findAbilitazioneGetAbilitazioni(abilitazioneDto);
        if(abilitazioneDtoList == null || abilitazioneDtoList.isEmpty()){
            errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.ABILITAZIONI_NON_PRESENTI.getValue()));
        }
        validateAbilitazioniResponse.setAbilitazioneDtoList(abilitazioneDtoList);
        validateAbilitazioniResponse.setListaErrori(errori);
        return validateAbilitazioniResponse;
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
            ruoloDto = new RuoloDto();
            ruoloDto = Utils.getFirstRecord(ruoloLowDao.findByCodice(ruoloDto, codiceRuolo));
            if(ruoloDto == null){
                //Se non trovato vuol dire che non e' una codifica presente nel DB
                errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.RUOLO_ERRATO.getValue()));
            }
        }
        return ruoloDto;
    }

    private CollocazioneDto verificaCollocazione(LogGeneralDaoBean logGeneralDaoBean, List<Errore> errori,
                                                 String codiceCollocazione, String codiceAzienda) throws Exception {
        CollocazioneDto collocazioneDto = null;
        if(codiceCollocazione == null ||
                codiceCollocazione.isEmpty()){
            //Errore collocazione non presente
            errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.CODICE_COLLOCAZIONE_OBBLIGATORIO.getValue()));
        }else{
            //Controllo codifica ruolo
            collocazioneDto = new CollocazioneDto();
            collocazioneDto.setColCodice(codiceCollocazione);
            collocazioneDto.setColCodAzienda(codiceAzienda);
            collocazioneDto = Utils.getFirstRecord(collocazioneLowDao.findByFilter(collocazioneDto));
            if(collocazioneDto == null){
                //Se non trovato vuol dire che non e' una codifica presente nel DB
                errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.COLLOCAZIONE_ERRATA.getValue()));
            }
        }
        return collocazioneDto;
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
        ApplicazioneDto applicazioneDto = new ApplicazioneDto();

        applicazioneDto = Utils.getFirstRecord(
                applicazioneLowDao.findByCodice(applicazioneDto, codApplicazione));

        if(applicazioneDto == null){
            errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), codiceErrore.getValue()));
        }
        return applicazioneDto;
    }
}
