/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.export;

import it.csi.solconfig.configuratoreweb.business.dao.dto.UtentiConfiguratoreViewDto;
import it.csi.solconfig.configuratoreweb.presentation.model.RisultatiRicercaUtenteDTO;
import it.csi.solconfig.configuratoreweb.util.Utils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

public class ExcelReportView extends AbstractXlsView {

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {

        response.setHeader("Content-Disposition", "attachment;filename=\"export.xls\"");
        List<UtentiConfiguratoreViewDto> list = (List<UtentiConfiguratoreViewDto>) model.get("exportList");

        Sheet sheet = workbook.createSheet("Dati Utenti");
        Row header = sheet.createRow(0);
        header.createCell(0).setCellValue("Codice Fiscale");
        header.createCell(1).setCellValue("Cognome");
        header.createCell(2).setCellValue("Nome");
        header.createCell(3).setCellValue("Utente inserito dal Configuratore");
        header.createCell(4).setCellValue("Codice Ruolo");
        header.createCell(5).setCellValue("Ruolo");
        header.createCell(6).setCellValue("Codice_Azienda_Sanitaria");
        header.createCell(7).setCellValue("Descrizione_Azienda_Sanitaria");
        header.createCell(8).setCellValue("Codice_Struttura_Sanitaria");
        header.createCell(9).setCellValue("Descrizione_Struttura_Sanitaria");
        header.createCell(10).setCellValue("SOL");
        header.createCell(11).setCellValue("Gestito da Configuratore");
        header.createCell(12).setCellValue("Profili");

        header.createCell(13).setCellValue("Data inizio abilitazione");
        header.createCell(14).setCellValue("Data fine abilitazione");
        header.createCell(15).setCellValue("Codice Fiscale Operatore");
        
        int rowNum = 1;
        for (UtentiConfiguratoreViewDto item : list) {

            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(item.getCodiceFiscale());
            row.createCell(1).setCellValue(item.getCognome());
            row.createCell(2).setCellValue(item.getNome());
            row.createCell(3).setCellValue(item.getUtenteInseritoConfiguratore());
            row.createCell(4).setCellValue(item.getCodiceRuolo());
            row.createCell(5).setCellValue(item.getRuolo());
            row.createCell(6).setCellValue(item.getCodiceAziendaSanitaria());
            row.createCell(7).setCellValue(item.getDescrizioneAziendaSanitaria());

            String codiceStruttura = item.getCodiceStrutturaSanitaria();
            if(codiceStruttura != null)
                codiceStruttura = codiceStruttura.replaceAll("@", " ");
            row.createCell(8).setCellValue(codiceStruttura);

            String descStruttura = item.getDescrizioneStrutturaSanitaria();
            if(descStruttura != null){
                descStruttura = descStruttura.replaceAll("@", " ");
            }
            row.createCell(9).setCellValue(descStruttura);

            row.createCell(10).setCellValue(item.getSol());
            row.createCell(11).setCellValue(item.getGestitoConfiguratore());
            row.createCell(12).setCellValue(item.getProfili());

            if(item.getDataInizioAbilitazione() != null){
            	String dataInizio = item.getDataInizioAbilitazione().toLocalDateTime().toString();
            	row.createCell(13).setCellValue(dataInizio);
            }
            if(item.getDataFineAbilitazione() != null){
            	String dataFine = item.getDataFineAbilitazione().toLocalDateTime().toString();
            	row.createCell(14).setCellValue(dataFine);
            } else {
            	row.createCell(14).setCellValue("Fino a fine rapporto");
            }
            if(item.getCfOperatore() != null){
            	row.createCell(15).setCellValue(item.getCfOperatore());
            }
        }
    }
}
