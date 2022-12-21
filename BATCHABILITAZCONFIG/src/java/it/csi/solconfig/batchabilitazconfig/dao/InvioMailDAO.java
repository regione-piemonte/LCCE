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
import java.util.ArrayList;
import java.util.List;

import it.csi.solconfig.batchabilitazconfig.configuration.Configuration;
import it.csi.solconfig.batchabilitazconfig.dto.Applicazione;
import it.csi.solconfig.batchabilitazconfig.dto.Collocazione;
import it.csi.solconfig.batchabilitazconfig.dto.Funzionalita;
import it.csi.solconfig.batchabilitazconfig.dto.Utente;
import it.csi.solconfig.batchabilitazconfig.logger.BatchLoggerFactory;



public class InvioMailDAO {

	private Connection conn;
	private PreparedStatement ps;
	private String encryptionKey = Configuration.get("encryptionKey");

	public InvioMailDAO() throws ClassNotFoundException, SQLException {
		this.conn = DBConnectionManager.getConnection();
		this.conn.setAutoCommit(false);
	}
	
	public InvioMailDAO(Connection conn) {
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
	
	public String findEmailUtente(Long idUtente) throws SQLException {
		try {
			this.ps = this.conn.prepareStatement(SQLStatements.FIND_EMAIL_UTENTE);
			this.ps.setString(1, encryptionKey);
			this.ps.setLong(2, idUtente);
				
			ResultSet rs = this.ps.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}		
		} catch (SQLException sqEx) {
			BatchLoggerFactory.getLogger(this).info(
					"Si e' verificato un errore SQL. findEmailUtente. Elaborazione Batch terminata con errori ="
							+ sqEx.getLocalizedMessage());
			throw sqEx;
		} finally {
			this.ps.close();
		}
		return null;
	}
	
	public String findConfigurazione(String key) throws SQLException {
		try {
			this.ps = this.conn.prepareStatement(SQLStatements.FIND_CONFIGURAZIONE);
			this.ps.setString(1, key);
				
			ResultSet rs = this.ps.executeQuery();
			if (rs.next()) {
				return rs.getString(1);
			}		
		} catch (SQLException sqEx) {
			BatchLoggerFactory.getLogger(this).info(
					"Si e' verificato un errore SQL. findConfigurazione. Elaborazione Batch terminata con errori ="
							+ sqEx.getLocalizedMessage());
			throw sqEx;
		} finally {
			this.ps.close();
		}
		return null;
	}
	
	public List<Collocazione> findCollocazioniUtente(Long idUtente) throws SQLException {
		List<Collocazione> collocazioni = new ArrayList<Collocazione>();
		try {
			this.ps = this.conn.prepareStatement(SQLStatements.FIND_COLLOCAZIONI);
			this.ps.setLong(1, idUtente);
			
			ResultSet rs = this.ps.executeQuery();
			while (rs.next()) {
				collocazioni.add(new Collocazione(rs.getLong(1),
						rs.getString(2), rs.getString(3), rs.getString(4), 
						rs.getString(5), rs.getString(6)));
			}		
		} catch (SQLException sqEx) {
			BatchLoggerFactory.getLogger(this).info(
					"Si e' verificato un errore SQL. findCollocazioniUtente. Elaborazione Batch terminata con errori ="
							+ sqEx.getLocalizedMessage());
			throw sqEx;
		} finally {
			this.ps.close();
		}
		return collocazioni;
	}
	
	public List<Long> findRuoliUtente(Long idUtente) throws SQLException {
		List<Long> ruoli = new ArrayList<Long>();
		try {
			this.ps = this.conn.prepareStatement(SQLStatements.FIND_RUOLI);
			this.ps.setLong(1, idUtente);
			
			ResultSet rs = this.ps.executeQuery();
			while (rs.next()) {
				ruoli.add(rs.getLong(1));
			}		
		} catch (SQLException sqEx) {
			BatchLoggerFactory.getLogger(this).info(
					"Si e' verificato un errore SQL. findRuoliUtente. Elaborazione Batch terminata con errori ="
							+ sqEx.getLocalizedMessage());
			throw sqEx;
		} finally {
			this.ps.close();
		}
		return ruoli;
	}
	
	public List<String> findAbilitazioniString(Long idUtente) throws SQLException {
		List<String> abilitazioniString = new ArrayList<String>();
		try {
			this.ps = this.conn.prepareStatement(SQLStatements.FIND_ABILITAZIONI_STRING);
			this.ps.setLong(1, idUtente);
			
			ResultSet rs = this.ps.executeQuery();
			while (rs.next()) {
				abilitazioniString.add(rs.getString(1));
			}		
		} catch (SQLException sqEx) {
			BatchLoggerFactory.getLogger(this).info(
					"Si e' verificato un errore SQL. findAbilitazioniString. Elaborazione Batch terminata con errori ="
							+ sqEx.getLocalizedMessage());
			throw sqEx;
		} finally {
			this.ps.close();
		}
		return abilitazioniString;
	}
	
	public Applicazione findApplicazione(Long idApplicazione) {
		
		try {
			this.ps = this.conn.prepareStatement(SQLStatements.FIND_APPLICAZIONE);
			this.ps.setLong(1, idApplicazione);
			
			ResultSet rs = this.ps.executeQuery();
			if (rs.next()) {
				return new Applicazione(rs.getLong(1), rs.getString(2), rs.getString(3)); 
			}		
		} catch (SQLException sqEx) {
			BatchLoggerFactory.getLogger(this).info(
					"Si e' verificato un errore SQL. findApplicazione. Elaborazione Batch terminata con errori ="
							+ sqEx.getLocalizedMessage());

		} finally {
			try {
				this.ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public Funzionalita findFunzionalita(Long idFunzionalita) {
		
		try {
			this.ps = this.conn.prepareStatement(SQLStatements.FIND_FUNZIONALITA);
			this.ps.setLong(1, idFunzionalita);
			
			ResultSet rs = this.ps.executeQuery();
			if (rs.next()) {
				return new Funzionalita(rs.getLong(1), rs.getString(2), rs.getString(3)); 
			}		
		} catch (SQLException sqEx) {
			BatchLoggerFactory.getLogger(this).info(
					"Si e' verificato un errore SQL. findFunzionalita. Elaborazione Batch terminata con errori ="
							+ sqEx.getLocalizedMessage());

		} finally {
			try {
				this.ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public Utente findUtente(Long idUtente) throws SQLException {
		
		try {
			this.ps = this.conn.prepareStatement(SQLStatements.FIND_UTENTE);
			this.ps.setString(1, encryptionKey);
			this.ps.setString(2, encryptionKey);
			this.ps.setLong(3, idUtente);
			
			ResultSet rs = this.ps.executeQuery();
			if (rs.next()) {
				return new Utente(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6)); 
			}		
		} catch (SQLException sqEx) {
			BatchLoggerFactory.getLogger(this).info(
					"Si e' verificato un errore SQL. findUtente. Elaborazione Batch terminata con errori ="
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
	
	public String findDescRuolo(Long idRuolo) throws SQLException {
		
		try {
			this.ps = this.conn.prepareStatement(SQLStatements.FIND_DESC_RUOLO);
			this.ps.setLong(1, idRuolo);
			
			ResultSet rs = this.ps.executeQuery();
			if (rs.next()) {
				return rs.getString(1); 
			}		
		} catch (SQLException sqEx) {
			BatchLoggerFactory.getLogger(this).info(
					"Si e' verificato un errore SQL. findDescRuolo. Elaborazione Batch terminata con errori ="
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

	public String findDescCollocazione(Long idCollocazione) throws SQLException {
		
		try {
			this.ps = this.conn.prepareStatement(SQLStatements.FIND_DESC_COLLOCAZIONE);
			this.ps.setLong(1, idCollocazione);
			
			ResultSet rs = this.ps.executeQuery();
			if (rs.next()) {
				return rs.getString(1); 
			}		
		} catch (SQLException sqEx) {
			BatchLoggerFactory.getLogger(this).info(
					"Si e' verificato un errore SQL. findDescCollocazione. Elaborazione Batch terminata con errori ="
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
	
	public String findDescAzienda(Long idCollocazione) throws SQLException {
		
		try {
			this.ps = this.conn.prepareStatement(SQLStatements.FIND_DESC_AZIENDA);
			this.ps.setLong(1, idCollocazione);
			
			ResultSet rs = this.ps.executeQuery();
			if (rs.next()) {
				return rs.getString(1); 
			}		
		} catch (SQLException sqEx) {
			BatchLoggerFactory.getLogger(this).info(
					"Si e' verificato un errore SQL. findDescAzienda. Elaborazione Batch terminata con errori ="
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
	
}
