/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.presentation.controller;

import java.util.Base64;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import it.csi.dma.puawa.business.dao.ApplicazioneLowDao;
import it.csi.dma.puawa.business.dao.ReportTipoLowDao;
import it.csi.dma.puawa.business.dao.dto.ApplicazioneDto;
import it.csi.dma.puawa.business.dao.dto.LogAuditDto;
import it.csi.dma.puawa.business.dao.dto.ReportTipoDto;
import it.csi.dma.puawa.business.dao.util.Constants;
import it.csi.dma.puawa.integration.collocazioni.client.ViewCollocazione;
import it.csi.dma.puawa.integration.log.LogAuditRichiedente;
import it.csi.dma.puawa.integration.log.LogGeneralDaoBean;
import it.csi.dma.puawa.integration.reportOperazioniConsensi.client.ReportOperazioniConsensiRequest;
import it.csi.dma.puawa.integration.reportOperazioniConsensi.client.ReportOperazioniConsensiResponse;
import it.csi.dma.puawa.integration.reportOperazioniConsensi.client.ReportOperazioniConsensiServiceClient;
import it.csi.dma.puawa.integration.reportRefertiScaricati.client.ReportRefertiScaricatiRequest;
import it.csi.dma.puawa.integration.reportRefertiScaricati.client.ReportRefertiScaricatiResponse;
import it.csi.dma.puawa.integration.reportRefertiScaricati.client.ReportRefertiScaricatiServiceClient;
import it.csi.dma.puawa.integration.reports.common.ApplicazioneRichiedente;
import it.csi.dma.puawa.integration.reports.common.CollocazioneReport;
import it.csi.dma.puawa.integration.reports.common.FiltriReport;
import it.csi.dma.puawa.integration.reports.common.RegimeDMA;
import it.csi.dma.puawa.integration.reports.common.Richiedente;
import it.csi.dma.puawa.integration.reports.common.RuoloDMA;
import it.csi.dma.puawa.presentation.constants.ConstantsWebApp;
import it.csi.dma.puawa.presentation.model.Data;
import it.csi.dma.puawa.presentation.model.Report;
import it.csi.dma.puawa.presentation.model.ReportisticaData;
import it.csi.dma.puawa.util.Utils;

@Controller
@Scope("prototype")
public class ReportisticaController extends BaseController {

	protected static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	@Autowired
	private ApplicazioneLowDao applicazioneLowDao;

	@Autowired
	private ReportOperazioniConsensiServiceClient reportOperazioniConsensiServiceClient;

	@Autowired
	private ReportRefertiScaricatiServiceClient reportRefertiScaricatiServiceClient;

	@Autowired
	private ReportTipoLowDao reportTipoLowDao;

	@RequestMapping(value = "/reportistica", method = RequestMethod.GET)
	public ModelAndView reportistica(ModelAndView mav) {

		try {
			mav.addObject(new ReportisticaData());
			Collection<ReportTipoDto> reportTipoDtoList = reportTipoLowDao.findAllByDataValidazione();
			Data data = getData();
			if(reportTipoDtoList != null && !reportTipoDtoList.isEmpty()){
				data.setReportTipoDtoList((List<ReportTipoDto>) reportTipoDtoList);
			}
			updateData(data);
			mav.setViewName(ConstantsWebApp.REPORTISTICA);
		} catch (Exception e) {
			log.error("ERROR: reportistica- ", e);
			mav.setViewName(ConstantsWebApp.ERROR);
		}

		return mav;
	}

	@RequestMapping(value = "/genera", method = RequestMethod.POST)
	public ModelAndView genera(ModelAndView mav, @ModelAttribute ReportisticaData reportisticaData) {

		try {
			mav.setViewName(ConstantsWebApp.REPORTISTICA);
			Data data = getData();
			CollocazioneReport collocazioneRichiedente = new CollocazioneReport();

			Richiedente richiedenteReport = new Richiedente();
			for (ViewCollocazione viewCollocazione : data.getUtente().getViewListaCollocazioni()) {
				if (viewCollocazione.getColCodice().equals(reportisticaData.getCodiceCollocazione())) {
					if (viewCollocazione.getColCodAzienda().startsWith("010")) {
						collocazioneRichiedente.setCodiceAzienda(viewCollocazione.getColCodAzienda().substring(3,
								viewCollocazione.getColCodAzienda().length()));
					} else {
						collocazioneRichiedente.setCodiceAzienda(viewCollocazione.getColCodAzienda());
					}
					collocazioneRichiedente.setCodiceCollocazione(reportisticaData.getCodiceCollocazione());
					collocazioneRichiedente.setDescrizioneCollocazione(viewCollocazione.getColDescrizione());
				}
			}

			Calendar calendar = Calendar.getInstance();
			calendar.setTimeInMillis(new Date().getTime());
			richiedenteReport.setTokenOperazione(String.valueOf(calendar.getTimeInMillis()));
			richiedenteReport.setRegime(new RegimeDMA());
			ApplicazioneDto applicazioneDto = Utils
					.getFirstRecord(applicazioneLowDao.findByCodice(new ApplicazioneDto(), ConstantsWebApp.PUAWA));
			richiedenteReport.setApplicazione(
					new ApplicazioneRichiedente(applicazioneDto.getCodice(), applicazioneDto.getDescrizione()));

			richiedenteReport.setCodiceFiscale(data.getUtente().getCodiceFiscale());
			richiedenteReport.setRuolo(new RuoloDMA(data.getUtente().getRuolo().getCodice(),
					data.getUtente().getRuolo().getDescrizione()));
			richiedenteReport.setNumeroTransazione(UUID.randomUUID().toString());
			richiedenteReport.setRuolo(new RuoloDMA(data.getUtente().getRuolo().getCodice(), null));

			FiltriReport filtriReport = new FiltriReport();
			filtriReport.setCollocazione(collocazioneRichiedente);
			filtriReport.setDataRicercaA(reportisticaData.getDataA());
			filtriReport.setDataRicercaDA(reportisticaData.getDataDa());

			LogAuditRichiedente logAuditRichiedente = getLogAuditRichiedente(data, collocazioneRichiedente);

			LogAuditDto logAuditDto = new LogAuditDto();
			LogGeneralDaoBean logGeneralDaoBean = new LogGeneralDaoBean();
			String[] dataRicerca;

			switch (reportisticaData.getCodiceOptionReport()) {
			case "ROL":
				ReportRefertiScaricatiRequest reportRefertiScaricatiRequest = new ReportRefertiScaricatiRequest();
				reportRefertiScaricatiRequest.setCollocazioneRichiedente(collocazioneRichiedente);
				reportRefertiScaricatiRequest.setRichiedente(richiedenteReport);

				dataRicerca = filtriReport.getDataRicercaA().split("-");
				filtriReport.setDataRicercaA(
						dataRicerca[0] + "-" + dataRicerca[1] + "-" + dataRicerca[2] + " " + Utils.sysdate().getHours()
								+ ":" + Utils.sysdate().getMinutes() + ":" + Utils.sysdate().getSeconds());

				dataRicerca = filtriReport.getDataRicercaDA().split("-");
				filtriReport.setDataRicercaDA(
						dataRicerca[0] + "-" + dataRicerca[1] + "-" + dataRicerca[2] + " " + Utils.sysdate().getHours()
								+ ":" + Utils.sysdate().getMinutes() + ":" + Utils.sysdate().getSeconds());

				reportRefertiScaricatiRequest.setFiltriReport(filtriReport);


				logAuditRichiedente.setTipoReport("Report Referti Scaricati");

				ReportRefertiScaricatiResponse reportRefertiScaricatiResponse = reportRefertiScaricatiServiceClient
						.call(reportRefertiScaricatiRequest, logAuditRichiedente, logGeneralDaoBean);

				logAuditDto = setLogAudit(logAuditRichiedente, ConstantsWebApp.GENERA_REPORT_LOG,
						data.getUtente().getViewCollocazione().getColCodAzienda(),
						data.getUtente().getViewCollocazione().getColCodice(), null, null,
						logGeneralDaoBean.getMessaggiDto(), reportisticaData.getCodiceOptionReport(), reportisticaData.getDataDa(),
						reportisticaData.getDataA(), collocazioneRichiedente.getDescrizioneCollocazione());

				if (reportRefertiScaricatiResponse.getErrori() != null) {
					mav.addObject("errori", reportRefertiScaricatiResponse.getErrori());
				}
				if (reportRefertiScaricatiResponse.getReportFile() != null) {

					// TODO: Parametrizzare fileName
					byte[] decoded = Base64.getDecoder().decode(reportRefertiScaricatiResponse.getReportFile());
					data.setReport(new Report(decoded, "ReportRefertiScaricati-" + Utils.sysdate().toString()));
					updateData(data);
					return new ModelAndView("redirect:/download");
				}
				break;
			case "FSE-CONS":
				ReportOperazioniConsensiRequest reportOperazioniConsensiRequest = new ReportOperazioniConsensiRequest();

				reportOperazioniConsensiRequest.setCollocazioneRichiedente(collocazioneRichiedente);
				reportOperazioniConsensiRequest.setRichiedente(richiedenteReport);

				dataRicerca = filtriReport.getDataRicercaA().split("-");
				filtriReport.setDataRicercaA(dataRicerca[0] + "/" + dataRicerca[1] + "/" + dataRicerca[2]);

				dataRicerca = filtriReport.getDataRicercaDA().split("-");
				filtriReport.setDataRicercaDA(dataRicerca[0] + "/" + dataRicerca[1] + "/" + dataRicerca[2]);

				reportOperazioniConsensiRequest.setFiltriReport(filtriReport);

				logAuditRichiedente.setTipoReport("Report Operazioni Consensi");


				ReportOperazioniConsensiResponse reportOperazioniConsensiResponse = reportOperazioniConsensiServiceClient
						.call(reportOperazioniConsensiRequest, logAuditRichiedente, logGeneralDaoBean);

				logAuditDto = setLogAudit(logAuditRichiedente, ConstantsWebApp.GENERA_REPORT_LOG,
						data.getUtente().getViewCollocazione().getColCodAzienda(),
						data.getUtente().getViewCollocazione().getColCodice(), null, null,
						logGeneralDaoBean.getMessaggiDto(), reportisticaData.getCodiceOptionReport(), reportisticaData.getDataDa(),
						reportisticaData.getDataA(), collocazioneRichiedente.getDescrizioneCollocazione());


				if (reportOperazioniConsensiResponse.getErrori() != null) {
					mav.addObject("errori", reportOperazioniConsensiResponse.getErrori());
				}
				if (reportOperazioniConsensiResponse.getReportFile() != null) {

					// TODO: Parametrizzare fileName
					byte[] decoded = Base64.getDecoder().decode(reportOperazioniConsensiResponse.getReportFile());
					data.setReport(new Report(decoded, "ReportOperazioniConsensi-" + Utils.sysdate().toString()));
					updateData(data);
					return new ModelAndView("redirect:/download");
				}
				break;
			}
		} catch (Exception e) {
			log.error("ERROR: genera report - ", e);
			mav.setViewName(ConstantsWebApp.ERROR);
		}

		return mav;
	}

	private LogAuditRichiedente getLogAuditRichiedente(Data data, CollocazioneReport collocazioneRichiedente) {
		LogAuditRichiedente logAuditRichiedente = new LogAuditRichiedente();
		logAuditRichiedente.setCodiceFiscaleRichiedente(data.getUtente().getCodiceFiscale());
		logAuditRichiedente.setIpChiamante(data.getUtente().getIpAddress());
		logAuditRichiedente.setCodiceRuoloRichiedente(data.getCodiceRuoloSelezionato());
		logAuditRichiedente.setApplicazioneChiamante(ConstantsWebApp.PUAWA);
		logAuditRichiedente.setDataIniziale(Utils.sysdate().toString());
		logAuditRichiedente.setDataFinale(Utils.sysdate().toString());
		logAuditRichiedente.setDescrizioneCollocazione(collocazioneRichiedente.getDescrizioneCollocazione());
		return logAuditRichiedente;
	}

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public HttpEntity<byte[]> download() {
		HttpHeaders header = new HttpHeaders();
		Data data = getData();
		try {

			header.setContentType(new MediaType("application", "force-download"));
			header.set("Content-Disposition", "attachment; filename=\"" + data.getReport().getFilename() + ".xls\"");

		} catch (Exception e) {
			log.error("ERROR: download report - ", e);
		}
		return new HttpEntity<byte[]>(data.getReport() != null ? data.getReport().getReportFile() : null, header);
	}
}
