/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class MapperUtils {

    private static LogUtil _log = new LogUtil(MapperUtils.class);

//    public static List<ModelAmbulatorio> createModelAmbulatorio(String jsonSource) throws JSONException {
//        List<ModelAmbulatorio> result = new ArrayList<>();
//        try {
//        	if(!"".equals(jsonSource)) {
//        		JSONArray jsonArray = new JSONArray(jsonSource);
//
//        		for (int i = 0; i < jsonArray.length(); i++) {
//        			JSONObject jsonObject = jsonArray.getJSONObject(i);
//        			ModelAmbulatorio modelAmbulatorio = new ModelAmbulatorio();
//        			modelAmbulatorio.setDescrizione(getStringFromJsonObject(jsonObject, "Descrizione"));
//        			modelAmbulatorio.setCodice(getStringFromJsonObject(jsonObject, "Codice"));
//        			JSONArray jsonArrayOrario = jsonObject.optJSONArray("Orari");
//        			result.add(modelAmbulatorio);
//        		}
//        	}
//        } catch (JSONException e) {
//            _log.error("createModelAmbulatorio", e.getMessage());
//            throw e;
//        }
//        return result;
//    }
//
//    public static Ticket createTicket(String jsonSource) throws JSONException {
//        Ticket ticket = null;
//        try {
//            ticket = new Ticket();
//            JSONObject json = new JSONObject(jsonSource);
//            ticket.setTicketId(getStringFromJsonObject(json, "ticketId"));
////            ticket.setInsertDate(getStringFromJsonObject(json, "insertDate"));
//            ticket.setRiepilogo(getStringFromJsonObject(json, "riepilogo"));
//            ticket.setImpatto(Ticket.ImpattoEnum.fromString(getStringFromJsonObject(json, "impatto")));
//            ticket.setUrgenza(Ticket.UrgenzaEnum.fromString(getStringFromJsonObject(json, "urgenza")));
//            ticket.setTipologia(Ticket.TipologiaEnum.fromString(getStringFromJsonObject(json, "tipologia")));
//             ticket.setRichiedente(createRichiedenteTicket(getStringFromJsonObject(json, "richiedente")));
//            ticket.setCategorizzazione(createCategorizzazioneTicket(getStringFromJsonObject(json, "categorizzazione")));
//        } catch (JSONException e) {
//            _log.error("createTicket", e.getMessage());
//            throw e;
//
//        }
//
//        return ticket;
//    }

//    public static List<TicketSnapshot> createTicketSnapshot(String jsonSource) throws JSONException {
//        List<TicketSnapshot> result = new ArrayList<TicketSnapshot>();
//        try {
//            if(!"".equals(jsonSource)) {
//                JSONArray jsonArray = new JSONArray(jsonSource);
//                for (int i = 0; i < jsonArray.length(); i++) {
//                    JSONObject jsonObject = jsonArray.getJSONObject(i);
//                    TicketSnapshot ticketSnapshot = new TicketSnapshot();
//                    ticketSnapshot.setTicketId(getStringFromJsonObject(jsonObject, "ticketId"));
//                    ticketSnapshot.setStatoAttuale(TicketSnapshot.StatoAttualeEnum.fromString(getStringFromJsonObject(jsonObject, "statoAttuale")));
//                    ticketSnapshot.setStatoPrecedente(TicketSnapshot.StatoPrecedenteEnum.fromString(getStringFromJsonObject(jsonObject, "statoPrecedente")));
//                    result.add(ticketSnapshot);
//                }
//            }
//        } catch (JSONException e) {
//            _log.error("createTicketSnapshot", e.getMessage());
//            throw e;
//        }
//        return result;
//    }
    
    private static ZonedDateTime getZonedDateTime(String dateToParse) {
        // Response non valida per le date. Se c'e' T deve esserci anche la Z alla fine  es "DataDiNascita": "0001-01-01T00:00:00"
        if (!dateToParse.toUpperCase().contains("Z")) {
            dateToParse += "Z";
        }

        Instant instant = Instant.parse(dateToParse);
        ZoneId zoneId = ZoneId.of("Europe/Rome");
        return ZonedDateTime.ofInstant(instant, zoneId);
    }

    public static byte[] base64toPDF(String base64) throws Exception {

        byte[] result = null;
        try {
            if (base64 != null && !("").equalsIgnoreCase(base64) && !("null").equalsIgnoreCase(base64)) {
            	base64 = (base64.startsWith("\"") && base64.endsWith("\"") ? base64.substring(1, base64.length()-1) : base64);
                if (Utils.isBase64(base64)) {
                    result = Utils.decodeBase64(base64);
                } else {
                    throw new Exception("Base 64 not valid");
                }
            }

        } catch (JSONException e) {
            _log.error("createModelCittadino", e.getMessage());
            throw new Exception(e);
        }

        return result;
    }

    private static String getStringFromJsonObject(JSONObject jsonObject, String tipoCampo) throws JSONException {
        return (jsonObject.has(tipoCampo) && !jsonObject.isNull(tipoCampo)) ? jsonObject.getString(tipoCampo) : null;
    }

//    private static Date getDateFromJsonObject(JSONObject jsonObject, String tipoCampo) throws JSONException {
//        String dateString = (jsonObject.has(tipoCampo) && !jsonObject.isNull(tipoCampo)) ? jsonObject.get(tipoCampo) : "";
//        return
//    }

}
