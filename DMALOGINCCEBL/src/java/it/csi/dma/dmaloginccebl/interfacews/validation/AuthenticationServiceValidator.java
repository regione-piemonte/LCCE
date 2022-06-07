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
import it.csi.dma.dmaloginccebl.interfacews.authentication.Credenziali;
import it.csi.dma.dmaloginccebl.interfacews.authentication.GetAuthenticationRequest;
import it.csi.dma.dmaloginccebl.interfacews.authentication.Richiedente;
import it.csi.dma.dmaloginccebl.interfacews.msg.Errore;
import it.csi.dma.dmaloginccebl.interfacews.msg.ParametriLogin;
import it.csi.dma.dmaloginccebl.iride.data.Identita;
import it.csi.dma.dmaloginccebl.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AuthenticationServiceValidator extends BaseServiceValidator{

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

    /**
     * Verificare i parametri di input obbligatori e necessarie codifiche da database
     * @param getAuthenticationRequest
     * @param logGeneralDaoBean
     * @return
     */
    public List<Errore> validate(GetAuthenticationRequest getAuthenticationRequest, LogGeneralDaoBean logGeneralDaoBean, List<Errore> errori){

        Richiedente richiedente = getAuthenticationRequest.getRichiedente();

        if(richiedente != null){
            //Verifica credenziali
            Credenziali credenziali = richiedente.getCredenziali();
            if(credenziali != null) {
                verificaCredenziali(logGeneralDaoBean, errori, credenziali);
            } else{
                //Errore credenziali non presenti
                errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.CREDENZIALI_OBBLIGATORIO.getValue()));
            }
            //Verifica ipClient
            verificaCampoObbligatorio(logGeneralDaoBean, errori, richiedente.getIpClient(), CatalogoLog.IPCLIENT_OBBLIGATORIO);
            //Verifica applicazione (SOLO OBBLIGATORIETA')
            verificaCampoObbligatorio(logGeneralDaoBean, errori, richiedente.getApplicazione(), CatalogoLog.APPLICAZIONE_OBBLIGATORIO);
        }else{
            //Errore richiedente non presente
            errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.RICHIEDENTE_OBBLIGATORIO.getValue()));
        }
        //Verifica codiceFiscalePaziente
        verificaCampoObbligatorio(logGeneralDaoBean, errori, getAuthenticationRequest.getCodiceFiscaleAssistito(), CatalogoLog.COD_FISC_PAZIENTE);

        return errori;
    }

    public RuoloDto verificaRuolo(LogGeneralDaoBean logGeneralDaoBean, List<Errore> errori, String codiceRuolo) {
        RuoloDto ruoloDto = new RuoloDto();
        if(codiceRuolo == null ||
                codiceRuolo.isEmpty()){
            //Errore ruolo non presente
            errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.RUOLO_OBBLIGATORIO.getValue()));
        }else{
            //Controllo codifica ruolo
            ruoloDto = Utils.getFirstRecord(ruoloLowDao.findByCodice(ruoloDto, codiceRuolo));
            if(ruoloDto == null){
                //Se non trovato vuol dire che non e' una codifica presente nel DB
                errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.RUOLO_ERRATO.getValue()));
            }
        }
        return ruoloDto;
    }

    public void verificaParametriLogin(LogGeneralDaoBean logGeneralDaoBean, List<Errore> errori,
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

    public ApplicazioneDto verificaApplicazione(LogGeneralDaoBean logGeneralDaoBean, List<Errore> errori, Richiedente richiedente) {
        ApplicazioneDto applicazioneDto = new ApplicazioneDto();

        applicazioneDto = Utils.getFirstRecord(
                applicazioneLowDao.findByCodice(applicazioneDto, richiedente.getApplicazione()));

        if(applicazioneDto == null){
            errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.ABILITAZIONE_NON_VALIDA.getValue()));
        }else{
            //Se il PIN e' richiesto, ma non presente in request si ritorna l'errore
            if(applicazioneDto.getPinRichiesto() != null &&
                    applicazioneDto.getPinRichiesto().equals("S") &&
                    (richiedente.getCredenziali().getPIN() == null || richiedente.getCredenziali().getPIN().isEmpty())){
                errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.PIN_RICHIESTO.getValue()));
            }
        }
        return applicazioneDto;
    }

    public UtenteDto verificaIrideResponse(LogGeneralDaoBean logGeneralDaoBean, List<Errore> errori, Identita identita) throws Exception {

        UtenteDto utenteDto = new UtenteDto();
        utenteDto.setCodiceFiscale(identita.getCodFiscale());
        utenteDto = Utils.getFirstRecord(utenteLowDao.findByFilter(utenteDto));
        if(utenteDto == null){
            errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.UTENTE_ERRATO.getValue()));
        }
        return utenteDto;
    }

    public CredenzialiServiziDto verificaCredenzialiServizio(LogGeneralDaoBean logGeneralDaoBean, List<Errore> errori, String codiceServizio) {
        ServiziDto serviziDto = Utils.getFirstRecord(serviziLowDao.findByCodice(new ServiziDto(), codiceServizio));
        CredenzialiServiziDto credenzialiServiziDto = new CredenzialiServiziDto();
        credenzialiServiziDto.setServiziDto(serviziDto);
        credenzialiServiziDto = Utils.getFirstRecord(credenzialiServiziLowDao.findByFilterAndServizioAndData(credenzialiServiziDto));
        if(credenzialiServiziDto == null){
            errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.APPLICAZIONE_ERRATO.getValue()));
        }
        return credenzialiServiziDto;
    }

    public AbilitazioneDto verificaAbilitazione(LogGeneralDaoBean logGeneralDaoBean, List<Errore> errori,
                                     RuoloDto ruoloDto, UtenteDto utenteDto, ApplicazioneDto applicazioneDto){

        AbilitazioneDto abilitazioneDto = null;
        RuoloUtenteDto ruoloUtenteDto = new RuoloUtenteDto();
        ruoloUtenteDto.setRuoloDto(ruoloDto);
        ruoloUtenteDto.setUtenteDto(utenteDto);
        ruoloUtenteDto = Utils.getFirstRecord(ruoloUtenteLowDao.findByUtenteRuoloAndData(ruoloUtenteDto));
        if(ruoloUtenteDto == null){
            errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.ABILITAZIONE_NON_VALIDA.getValue()));
        }else{
            abilitazioneDto = new AbilitazioneDto();
            abilitazioneDto.setRuoloUtenteDto(ruoloUtenteDto);
            abilitazioneDto.setApplicazioneDto(applicazioneDto);
            abilitazioneDto = Utils.getFirstRecord(abilitazioneLowDao.findByUtenteRuoloAppAndData(abilitazioneDto));
            if(abilitazioneDto == null){
                errori.add(logGeneralDao.logErrore(logGeneralDaoBean.getLogDto(), CatalogoLog.ABILITAZIONE_NON_VALIDA.getValue()));
            }
        }
        return abilitazioneDto;
    }

    private void verificaCredenziali(LogGeneralDaoBean logGeneralDaoBean, List<Errore> errori, Credenziali credenziali) {
        //Verifica username
        verificaCampoObbligatorio(logGeneralDaoBean, errori, credenziali.getUsername(), CatalogoLog.USERNAME_OBBLIGATORIO);
        //Verifica password
        verificaCampoObbligatorio(logGeneralDaoBean, errori, credenziali.getPassword(), CatalogoLog.PASSWORD_OBBLIGATORIO);
    }
}
