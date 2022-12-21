/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.batchabilitazconfig.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import it.csi.solconfig.batchabilitazconfig.dto.Abilitazione;
import it.csi.solconfig.batchabilitazconfig.dto.RichiestaMassiva;
import it.csi.solconfig.batchabilitazconfig.dto.UtenteRichiestaMassiva;
import it.csi.solconfig.batchabilitazconfig.logger.BatchLoggerFactory;
import it.csi.solconfig.batchabilitazconfig.util.Constants;
import it.csi.solconfig.batchabilitazconfig.util.Utils;



public class RichiestaMassivaDAO {

	private Connection conn;
	private PreparedStatement ps;

	public RichiestaMassivaDAO() throws ClassNotFoundException, SQLException {
		this.conn = DBConnectionManager.getConnection();
		this.conn.setAutoCommit(false);
	}
	
	public RichiestaMassivaDAO(Connection conn) {
		this.conn = conn;
	}
		
	public void commit() {
		try {
			this.conn.commit();
		} catch (SQLException e) {
			BatchLoggerFactory.getLogger(this.getClass()).info("ERROR WHILE COMMITTING: " + e.getMessage());
		}
	}

	public void rollback() {
		try {
			this.conn.rollback();
		} catch (SQLException e1) {
			BatchLoggerFactory.getLogger(this.getClass()).info("ERROR WHILE ROLLBACKING: " + e1.getMessage());
		}
	}

	public void closeAll() {
		try {
			if (this.ps != null)
				ps.close();

			if (this.conn != null)
				this.conn.close();

		} catch (SQLException e) {
			BatchLoggerFactory.getLogger(this.getClass()).info("ERROR WHILE CLOSING CONNECTION: " + e.getMessage());
		}
	}
	
	public List<RichiestaMassiva> findRichiesteMassive() throws SQLException {
		List<RichiestaMassiva> richieste = new ArrayList<RichiestaMassiva>();
		try {
			this.ps = this.conn.prepareStatement(SQLStatements.FIND_RICHIESTE_MASSIVE);
			this.ps.setString(1, Constants.BATCH_STATO_DA_ELABORARE);
			this.ps.setString(2, Constants.BATCH_STATO_IN_ELABORAZIONE);
			ResultSet rs = this.ps.executeQuery();
			while (rs.next()) {
				richieste.add(new RichiestaMassiva(rs.getLong(1),
						rs.getString(2), rs.getBoolean(3), rs.getLong(4), 
						rs.getTimestamp(5), rs.getTimestamp(6), rs.getTimestamp(7)));
			}		
		} catch (SQLException sqEx) {
			BatchLoggerFactory.getLogger(this).info(
					"Si e' verificato un errore SQL. findRichiesteMassive. Elaborazione Batch terminata con errori ="
							+ sqEx.getLocalizedMessage());
			throw sqEx;
		} finally {
			this.ps.close();
		}
		return richieste;
	}
	
	public List<UtenteRichiestaMassiva> findUtentiRichiestaMassiva(Long idRichiesta) throws SQLException {
		List<UtenteRichiestaMassiva> utenti = new ArrayList<UtenteRichiestaMassiva>();
		try {
			this.ps = this.conn.prepareStatement(SQLStatements.FIND_UTENTI_RICHIESTA_MASSIVA);
			this.ps.setLong(1, idRichiesta);	
			ResultSet rs = this.ps.executeQuery();
			while (rs.next()) {
				utenti.add(new UtenteRichiestaMassiva(rs.getLong(1),
						rs.getLong(2), rs.getLong(3), rs.getLong(4), 
						rs.getLong(5), rs.getLong(6), rs.getLong(7),
						rs.getTimestamp(8), rs.getLong(9), rs.getString(10), 
						rs.getTimestamp(11), rs.getTimestamp(12), rs.getTimestamp(13)));
			}		
		} catch (SQLException sqEx) {
			BatchLoggerFactory.getLogger(this).info(
					"Si e' verificato un errore SQL. findUtentiRichiestaMassiva. Elaborazione Batch terminata con errori ="
							+ sqEx.getLocalizedMessage());
			throw sqEx;
		} finally {
			this.ps.close();
		}
		return utenti;
	}
	
	public void startRichiesta(Long idRichiesta) throws SQLException {
		try {
			this.ps = this.conn.prepareStatement(SQLStatements.START_RICHIESTA);
			this.ps.setString(1, Constants.BATCH_STATO_IN_ELABORAZIONE);
			this.ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			this.ps.setLong(3, idRichiesta);

			this.ps.executeUpdate();
			commit();

		} catch (SQLException e) {
			BatchLoggerFactory.getLogger(this).info(
					"Si e' verificato un errore SQL. startRichiesta. Elaborazione Batch terminata con errori ="
							+ e.getLocalizedMessage());
			rollback();
			throw e;
		} finally {
			try {
				this.ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void endRichiesta(Long idRichiesta, String endStato) throws SQLException {
		try {
			this.ps = this.conn.prepareStatement(SQLStatements.END_RICHIESTA);
			this.ps.setString(1, endStato);
			this.ps.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
			this.ps.setLong(3, idRichiesta);

			this.ps.executeUpdate();
			commit();

		} catch (SQLException e) {
			BatchLoggerFactory.getLogger(this).info(
					"Si e' verificato un errore SQL. startRichiesta. Elaborazione Batch terminata con errori ="
							+ e.getLocalizedMessage());
			rollback();
			throw e;
		} finally {
			try {
				this.ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void startUtenteRichiesta(Long idUtenteRichiesta) throws SQLException {
		try {
			this.ps = this.conn.prepareStatement(SQLStatements.START_UTENTE_RICHIESTA);
			this.ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			this.ps.setLong(2, idUtenteRichiesta);

			this.ps.executeUpdate();
			commit();

		} catch (SQLException e) {
			BatchLoggerFactory.getLogger(this).info(
					"Si e' verificato un errore SQL. startRichiesta. Elaborazione Batch terminata con errori ="
							+ e.getLocalizedMessage());
			rollback();
			throw e;
		} finally {
			try {
				this.ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void endUtenteRichiesta(UtenteRichiestaMassiva utente) throws SQLException {
		try {
			this.ps = this.conn.prepareStatement(SQLStatements.END_UTENTE_RICHIESTA);
			this.ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
			if(utente.getIdErroreUtente() != null)
				this.ps.setLong(2, utente.getIdErroreUtente());
			else
				this.ps.setNull(2, Types.BIGINT);
			if(utente.getErroreInterno() != null)
				this.ps.setString(3, utente.getErroreInterno());
			else
				this.ps.setNull(3, Types.VARCHAR);
			this.ps.setLong(4, utente.getId());

			this.ps.executeUpdate();
			commit();

		} catch (SQLException e) {
			BatchLoggerFactory.getLogger(this).info(
					"Si e' verificato un errore SQL. startRichiesta. Elaborazione Batch terminata con errori ="
							+ e.getLocalizedMessage());
			rollback();
			throw e;
		} finally {
			try {
				this.ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Abilitazione findAbilitazioneAttiva(Long idUtente, Long idRuolo, Long idCollocazione, Long idApplicazione, Long idProfilo) throws SQLException {
		Abilitazione abilitazione = null;
		try {
			this.ps = this.conn.prepareStatement(SQLStatements.FIND_ABILITAZIONE_ATTIVA);
			this.ps.setLong(1, idApplicazione);
			this.ps.setLong(2, idUtente);	
			this.ps.setLong(3, idRuolo);
			this.ps.setLong(4, idUtente);	
			this.ps.setLong(5, idCollocazione);	
			this.ps.setLong(6, idProfilo);	
			ResultSet rs = this.ps.executeQuery();
			if (rs.next()) {
				return new Abilitazione(rs.getLong(1), rs.getLong(2), rs.getLong(3), 
						rs.getString(4), rs.getTimestamp(5), rs.getTimestamp(6), 
						rs.getTimestamp(7),	rs.getLong(8), rs.getLong(9), 
						rs.getTimestamp(10), rs.getTimestamp(11), rs.getString(12));
			}		
		} catch (SQLException sqEx) {
			BatchLoggerFactory.getLogger(this).info(
					"Si e' verificato un errore SQL. findAbilitazioneAttiva. Elaborazione Batch terminata con errori ="
							+ sqEx.getLocalizedMessage());
			throw sqEx;
		} finally {
			this.ps.close();
		}
		return abilitazione;
	}
	
	public Abilitazione findAbilitazioneNonAttiva(Long idUtente, Long idRuolo, Long idCollocazione, Long idApplicazione, Long idProfilo) throws SQLException {
		Abilitazione abilitazione = null;
		try {
			this.ps = this.conn.prepareStatement(SQLStatements.FIND_ABILITAZIONE_NON_ATTIVA);
			this.ps.setLong(1, idApplicazione);
			this.ps.setLong(2, idUtente);	
			this.ps.setLong(3, idRuolo);
			this.ps.setLong(4, idUtente);	
			this.ps.setLong(5, idCollocazione);	
			this.ps.setLong(6, idProfilo);	
			ResultSet rs = this.ps.executeQuery();
			if (rs.next()) {
				return new Abilitazione(rs.getLong(1), rs.getLong(2), rs.getLong(3), 
						rs.getString(4), rs.getTimestamp(5), rs.getTimestamp(6), 
						rs.getTimestamp(7),	rs.getLong(8), rs.getLong(9), 
						rs.getTimestamp(10), rs.getTimestamp(11), rs.getString(12));
			}		
		} catch (SQLException sqEx) {
			BatchLoggerFactory.getLogger(this).info(
					"Si e' verificato un errore SQL. findAbilitazioneNonAttiva. Elaborazione Batch terminata con errori ="
							+ sqEx.getLocalizedMessage());
			throw sqEx;
		} finally {
			this.ps.close();
		}
		return abilitazione;
	}
	
	public Long findRuoloUtente(Long idUtente, Long idRuolo) throws SQLException {
		try {
			this.ps = this.conn.prepareStatement(SQLStatements.FIND_RUOLO_UTENTE);
			this.ps.setLong(1, idUtente);	
			this.ps.setLong(2, idRuolo);
				
			ResultSet rs = this.ps.executeQuery();
			if (rs.next()) {
				return rs.getLong(1);
			}		
		} catch (SQLException sqEx) {
			BatchLoggerFactory.getLogger(this).info(
					"Si e' verificato un errore SQL. findRuoloUtente. Elaborazione Batch terminata con errori ="
							+ sqEx.getLocalizedMessage());
			throw sqEx;
		} finally {
			this.ps.close();
		}
		return null;
	}
	
	public Long findCollocazioneUtente(Long idUtente, Long idCollocazione) throws SQLException {
		try {
			this.ps = this.conn.prepareStatement(SQLStatements.FIND_COLLOCAZIONE_UTENTE);
			this.ps.setLong(1, idUtente);	
			this.ps.setLong(2, idCollocazione);
				
			ResultSet rs = this.ps.executeQuery();
			if (rs.next()) {
				return rs.getLong(1);
			}		
		} catch (SQLException sqEx) {
			BatchLoggerFactory.getLogger(this).info(
					"Si e' verificato un errore SQL. findCollocazioneUtente. Elaborazione Batch terminata con errori ="
							+ sqEx.getLocalizedMessage());
			throw sqEx;
		} finally {
			this.ps.close();
		}
		return null;
	}
	
	public Long findFunzTreeId(Long idFunzione) throws SQLException {
		try {
			this.ps = this.conn.prepareStatement(SQLStatements.FIND_FUNZ_TREE_ID);
			this.ps.setLong(1, idFunzione);	
				
			ResultSet rs = this.ps.executeQuery();
			if (rs.next()) {
				return rs.getLong(1);
			}		
		} catch (SQLException sqEx) {
			BatchLoggerFactory.getLogger(this).info(
					"Si e' verificato un errore SQL. findFunzTreeId. Elaborazione Batch terminata con errori ="
							+ sqEx.getLocalizedMessage());
			throw sqEx;
		} finally {
			this.ps.close();
		}
		return null;
	}
	
	public void updateAbilitazione(Timestamp dataFine, Long idAbilitazione) throws SQLException {
		try {
			this.ps = this.conn.prepareStatement(SQLStatements.UPDATE_ABILITAZIONE);
			if(dataFine != null) {
				this.ps.setTimestamp(1, dataFine);
				this.ps.setTimestamp(2, dataFine);
			} else {
				this.ps.setNull(1, Types.TIMESTAMP);
				this.ps.setNull(2, Types.TIMESTAMP);
			}
			this.ps.setLong(3, idAbilitazione);

			this.ps.executeUpdate();
			commit();

		} catch (SQLException e) {
			BatchLoggerFactory.getLogger(this).info(
					"Si e' verificato un errore SQL. updateAbilitazione. Elaborazione Batch terminata con errori ="
							+ e.getLocalizedMessage());
			rollback();
			throw e;
		} finally {
			try {
				this.ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void insertAbilitazione(Long idRuoloUtente, Long idCollocazioneUtente, Long idApplicazione, Long idFunzTree, Timestamp dataFineValidazione) throws SQLException {
		try {
			//BatchLoggerFactory.getLogger(this).info("=== insertAbilitazione SQLStatements START ");
			//BatchLoggerFactory.getLogger(this).info(SQLStatements.INSERT_ABILITAZIONE);
			//BatchLoggerFactory.getLogger(this).info("=== insertAbilitazione SQLStatements START ");
			this.ps = this.conn.prepareStatement(SQLStatements.INSERT_ABILITAZIONE);
			this.ps.setLong(1, idRuoloUtente);
			this.ps.setLong(2, idApplicazione);
			this.ps.setString(3, UUID.randomUUID().toString());
			this.ps.setTimestamp(4, Utils.truncateTimestamp(Utils.sysdate(), true));
			
			//BatchLoggerFactory.getLogger(this).info("=== dataFineValidazione START ");
			//BatchLoggerFactory.getLogger(this).info(dataFineValidazione);
			//BatchLoggerFactory.getLogger(this).info("=== dataFineValidazione END ");
			
			Timestamp dataFineTemp = Utils.truncateTimestamp(dataFineValidazione, false);
			this.ps.setTimestamp(5, dataFineTemp);
			this.ps.setLong(6, idFunzTree);
			
			//BatchLoggerFactory.getLogger(this).info("=== dataFineTemp START ");
			//BatchLoggerFactory.getLogger(this).info(dataFineTemp);
			//BatchLoggerFactory.getLogger(this).info("=== dataFineTemp END ");
			
			this.ps.setLong(7, idCollocazioneUtente);
			
			//BatchLoggerFactory.getLogger(this).info("=== PS START ");
			//BatchLoggerFactory.getLogger(this).info(ps);
			//BatchLoggerFactory.getLogger(this).info("=== PS END ");

			this.ps.executeUpdate();
			commit();

		} catch (SQLException e) {
			BatchLoggerFactory.getLogger(this).info(
					"Si e' verificato un errore SQL. insertAbilitazione. Elaborazione Batch terminata con errori ="
							+ e.getLocalizedMessage());
			rollback();
			throw e;
		} finally {
			try {
				this.ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Long findPreferenzaRuolo(Long idUtente, Long idRuolo) throws SQLException {
		try {
			this.ps = this.conn.prepareStatement(SQLStatements.FIND_PREFERENZA_RUOLO);
			this.ps.setLong(1, idUtente);
			this.ps.setLong(2, idRuolo);
				
			ResultSet rs = this.ps.executeQuery();
			if (rs.next()) {
				return rs.getLong(1);
			}		
		} catch (SQLException sqEx) {
			BatchLoggerFactory.getLogger(this).info(
					"Si e' verificato un errore SQL. findPreferenzaRuolo. Elaborazione Batch terminata con errori ="
							+ sqEx.getLocalizedMessage());
			throw sqEx;
		} finally {
			this.ps.close();
		}
		return null;
	}
	
	public Long findPreferenzaRuoloCollocazione(Long idUtente, Long idRuolo, Long idCollocazione) throws SQLException {
		try {
			this.ps = this.conn.prepareStatement(SQLStatements.FIND_PREFERENZA_RUOLO_COLLOCAZIONE);
			this.ps.setLong(1, idUtente);
			this.ps.setLong(2, idRuolo);
			this.ps.setLong(3, idCollocazione);
				
			ResultSet rs = this.ps.executeQuery();
			if (rs.next()) {
				return rs.getLong(1);
			}		
		} catch (SQLException sqEx) {
			BatchLoggerFactory.getLogger(this).info(
					"Si e' verificato un errore SQL. findPreferenzaRuoloCollocazione. Elaborazione Batch terminata con errori ="
							+ sqEx.getLocalizedMessage());
			throw sqEx;
		} finally {
			this.ps.close();
		}
		return null;
	}
	
	public Long findPreferenzaApplicazione(Long idUtente, Long idApplicazione) throws SQLException {
		try {
			this.ps = this.conn.prepareStatement(SQLStatements.FIND_PREFERENZA_APPLICAZIONE);
			this.ps.setLong(1, idUtente);
			this.ps.setLong(2, idApplicazione);
				
			ResultSet rs = this.ps.executeQuery();
			if (rs.next()) {
				return rs.getLong(1);
			}		
		} catch (SQLException sqEx) {
			BatchLoggerFactory.getLogger(this).info(
					"Si e' verificato un errore SQL. findPreferenzaApplicazione. Elaborazione Batch terminata con errori ="
							+ sqEx.getLocalizedMessage());
			throw sqEx;
		} finally {
			this.ps.close();
		}
		return null;
	}
	
	public void updatePreferenza(Long preferenzaId, Timestamp dataCancellazione) throws SQLException {
		try {
			this.ps = this.conn.prepareStatement(SQLStatements.UPDATE_PREFERENZA);
			if(dataCancellazione != null) {
				this.ps.setTimestamp(1, dataCancellazione);
				this.ps.setTimestamp(2, dataCancellazione);
			} else {
				this.ps.setNull(1, Types.TIMESTAMP);
				this.ps.setNull(2, Types.TIMESTAMP);
			}
			this.ps.setLong(3, preferenzaId);

			this.ps.executeUpdate();
			commit();

		} catch (SQLException e) {
			BatchLoggerFactory.getLogger(this).info(
					"Si e' verificato un errore SQL. updatePreferenza. Elaborazione Batch terminata con errori ="
							+ e.getLocalizedMessage());
			rollback();
			throw e;
		} finally {
			try {
				this.ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void insertPreferenza(Long idUtente, Long idApplicazione, Long idRuolo, Long idCollocazione) throws SQLException {
		try {
			this.ps = this.conn.prepareStatement(SQLStatements.INSERT_PREFERENZA);
			this.ps.setLong(1, idUtente);
			if(idApplicazione != null)
				this.ps.setLong(2, idApplicazione);
			else 
				this.ps.setNull(2, Types.BIGINT);
			if(idRuolo != null)
				this.ps.setLong(3, idRuolo);
			else 
				this.ps.setNull(3, Types.BIGINT);
			if(idCollocazione != null)
				this.ps.setLong(4, idCollocazione);
			else 
				this.ps.setNull(4, Types.BIGINT);

			this.ps.executeUpdate();
			commit();

		} catch (SQLException e) {
			BatchLoggerFactory.getLogger(this).info(
					"Si e' verificato un errore SQL. updatePreferenza. Elaborazione Batch terminata con errori ="
							+ e.getLocalizedMessage());
			rollback();
			throw e;
		} finally {
			try {
				this.ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Long findMessaggioUtente(String codMessaggioUtente) throws SQLException {
		try {
			this.ps = this.conn.prepareStatement(SQLStatements.FIND_ID_MESSAGGIO_UTENTE);
			this.ps.setString(1, codMessaggioUtente);	
				
			ResultSet rs = this.ps.executeQuery();
			if (rs.next()) {
				return rs.getLong(1);
			}		
		} catch (SQLException sqEx) {
			BatchLoggerFactory.getLogger(this).info(
					"Si e' verificato un errore SQL. findMessaggioUtente. Elaborazione Batch terminata con errori ="
							+ sqEx.getLocalizedMessage());
			throw sqEx;
		} finally {
			this.ps.close();
		}
		return null;
	}
	
	public String findDescErrore(Long idMsg) throws SQLException {
		
		try {
			this.ps = this.conn.prepareStatement(SQLStatements.FIND_DESC_ERRORE);
			this.ps.setLong(1, idMsg);
			
			ResultSet rs = this.ps.executeQuery();
			if (rs.next()) {
				return rs.getString(1); 
			}		
		} catch (SQLException sqEx) {
			BatchLoggerFactory.getLogger(this).info(
					"Si e' verificato un errore SQL. findDescErrore. Elaborazione Batch terminata con errori ="
							+ sqEx.getLocalizedMessage());
			throw sqEx;
		} finally {
			try {
				this.ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public Boolean checkApplicazioneConInvioMailAura(Long idApp) throws SQLException {
		try {
			this.ps = this.conn.prepareStatement(SQLStatements.CHECK_APPLICAZIONE_CON_INVIO_MAIL_AURA);
			this.ps.setLong(1, idApp);
			
			ResultSet rs = this.ps.executeQuery();
			if (rs.next()) {
				return rs.getBoolean(1);
			}		
		} catch (SQLException sqEx) {
			BatchLoggerFactory.getLogger(this).info(
					"Si e' verificato un errore SQL. checkApplicazioneConInvioMailAura. Elaborazione Batch terminata con errori ="
							+ sqEx.getLocalizedMessage());
			throw sqEx;
		} finally {
			try {
				this.ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public List<Abilitazione> findAbilitazioniAttiveByIdRuoloUtente(Long idRuoloUtente) throws SQLException {
		List<Abilitazione> abilitazioni = new ArrayList<Abilitazione>();
		try {
			this.ps = this.conn.prepareStatement(SQLStatements.FIND_ABILITAZIONI_BY_RUOLO_UTENTE);
			this.ps.setLong(1, idRuoloUtente);	
			ResultSet rs = this.ps.executeQuery();
			while (rs.next()) {
				abilitazioni.add( new Abilitazione(rs.getLong(1), rs.getLong(2), rs.getLong(3), 
						rs.getString(4), rs.getTimestamp(5), rs.getTimestamp(6), 
						rs.getTimestamp(7),	rs.getLong(8), rs.getLong(9), 
						rs.getTimestamp(10), rs.getTimestamp(11), rs.getString(12)));
			}		
		} catch (SQLException sqEx) {
			BatchLoggerFactory.getLogger(this).info(
					"Si e' verificato un errore SQL. findAbilitazioniAttiveByIdRuoloUtente. Elaborazione Batch terminata con errori ="
							+ sqEx.getLocalizedMessage());
			throw sqEx;
		} finally {
			this.ps.close();
		}
		return abilitazioni;
	}

	public List<Abilitazione> findAbilitazioniAttiveByUserIdAndIdApp(Long idUtente, Long idAbilitazione) throws SQLException {
		List<Abilitazione> abilitazioni = new ArrayList<Abilitazione>();
		try {
			this.ps = this.conn.prepareStatement(SQLStatements.FIND_ABILITAZIONI_BY_USER_AND_APP);
			this.ps.setLong(1, idAbilitazione);
			this.ps.setLong(2, idUtente);
			ResultSet rs = this.ps.executeQuery();
			while (rs.next()) {
				abilitazioni.add( new Abilitazione(rs.getLong(1), rs.getLong(2), rs.getLong(3), 
						rs.getString(4), rs.getTimestamp(5), rs.getTimestamp(6), 
						rs.getTimestamp(7),	rs.getLong(8), rs.getLong(9), 
						rs.getTimestamp(10), rs.getTimestamp(11), rs.getString(12)));
			}		
		} catch (SQLException sqEx) {
			BatchLoggerFactory.getLogger(this).info(
					"Si e' verificato un errore SQL. findAbilitazioniAttiveByUserIdAndIdApp. Elaborazione Batch terminata con errori ="
							+ sqEx.getLocalizedMessage());
			throw sqEx;
		} finally {
			this.ps.close();
		}
		return abilitazioni;
	}
}
