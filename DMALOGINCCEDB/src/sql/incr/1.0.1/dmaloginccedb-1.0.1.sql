--                                                              --
--  SPDX-FileCopyrightText: (C) Copyright 2022 Regione Piemonte --
--                                                              --
--  SPDX-License-Identifier: EUPL-1.2                           --
--                                                              --


--	SCRIPT INCREMENTALE CONFIGURAZIONE LOGINCCE
--	VERSIONE: 01C
--	SCHEMA DATI: LCCE
--	CREATE DATE: 10/01/2020
--	MODIFY DATE: 13/01/2020

DO
$$
BEGIN
	--	PERFORM setval('seq_auth_d_applicazione', COALESCE((SELECT MAX(id)+1 FROM auth_d_applicazione), 1), false);
	IF EXISTS (select * from auth_d_applicazione where codice = 'DMAWA') 
	THEN
		UPDATE auth_d_applicazione set
		urlverifyLoginConditions = 'https://tst-feapi-dma.isan.csi.it/dmaccmedrti/services/CCVerifyLoginConditionsService'
		where codice = 'DMAWA';
	ELSE
		INSERT INTO auth_d_applicazione (ID, codice, descrizione, pin_richiesto, urlverifyloginconditions, data_inserimento) 
		VALUES (nextval ('seq_auth_d_applicazione'),'DMAWA','WebApp DMA','N','https://tst-feapi-dma.isan.csi.it/dmaccmedrti/services/CCVerifyLoginConditionsService',NOW());
	END IF;
END
$$;

DO
$$
BEGIN
	IF NOT EXISTS (select * from auth_t_credenziali_servizi where 
		Id_Servizio = (select ID from auth_d_servizi where codice = 'GET_TOK_INFO')
		and Id_applicazione = (select ID from auth_d_applicazione where codice = 'DMAWA')) 
	THEN
		INSERT INTO auth_t_credenziali_servizi(ID, Id_Servizio,Id_applicazione,Username,Password,data_inizio_validita,data_fine_validita,data_inserimento)
		VALUES (nextval ('seq_auth_t_credenziali_servizi'),(select ID from auth_d_servizi where codice = 'GET_TOK_INFO'),
		(select ID from auth_d_applicazione where codice = 'DMAWA'),'xxx','xxx','2019-11-01 00:00:00.630379','2099-12-31 23:59:59.630379',NOW());
	END IF;
END
$$;

DO
$$
BEGIN
	IF NOT EXISTS (select * from auth_t_credenziali_servizi where 
		Id_Servizio = (select ID from auth_d_servizi where codice = 'VER_LOG_COND')
		and Id_applicazione = (select ID from auth_d_applicazione where codice = 'DMAWA')) 
	THEN
		INSERT INTO auth_t_credenziali_servizi(ID, Id_Servizio,Id_applicazione,Username,Password,data_inizio_validita,data_fine_validita,data_inserimento)
		VALUES (nextval ('seq_auth_t_credenziali_servizi'),(select ID from auth_d_servizi where codice = 'VER_LOG_COND'),
		(select ID from auth_d_applicazione where codice = 'DMAWA'),'xxx','xxx','2019-11-01 00:00:00.630379','2099-12-31 23:59:59.630379',NOW());
	END IF;
END
$$;
