/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.business.dao.dto;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "auth_t_farmacie")
@SequenceGenerator(name="auth_t_farmacie_id_farmacia_seq", sequenceName="auth_t_farmacie_id_farmacia_seq",allocationSize=1)
public class FarmacieDto {
	
	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="auth_t_farmacie_id_farmacia_seq")
    @Column(name = "id_farmacia")
	private int id;

	 @Column(name = "cod_azienda")
	 private String codiceAzienda;
	 
	 @Column(name = "denominazione")
	 private String denominazione;
	 
	 @Column(name = "cod_farmacia")
	 private String codiceFarmacia; 
	 
	 @Column(name = "denom_farmacia")
	 private String denominazioneFarmacia;
	 
	 @Column(name = "desc_natura")
	 private String descrizioneNatura;
	 
	 @Column(name = "partita_iva")
	 private String partitaIva;
	 
	 @Column(name = "desc_tipo_farm")
	 private String descrizioneTipoFarmacia;
	 
	 @Column(name = "cognome_dir")
	 private String cognomeDirettore;
	 
	 @Column(name = "nome_dir")
	 private String nomeDirettore;
	 
	 @Column(name = "codice_fiscale_dir")
	 private String codiceFiscaleDirettore;
	 
	 @Column(name = "indirizzo")
	 private String indirizzo;
	 
	 @Column(name = "numero_civico")
	 private String numeroCivico;
	 
	 @Column(name = "cap")
	 private String cap;
	 
	 @Column(name = "comune")
	 private String comune;
	 
	 @Column(name = "denom_provincia")
	 private String denominazioneProvincia;
	 
	 @Column(name = "data_inizio_validita_farm")
	 private Timestamp dataInizioValiditaFarmacia;
	 
	 @Column(name = "data_fine_validita_farm")
	 private Timestamp dataFineValiditaFarmacia;
	 
	 @Column(name = "data_inizio_farab")
	 private Timestamp dataInizioFarmaciaAbituale;
	 
	 @Column(name = "data_fine_farab")
	 private Timestamp dataFineFarmaciaAbituale;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCodiceAzienda() {
		return codiceAzienda;
	}

	public void setCodiceAzienda(String codiceAzienda) {
		this.codiceAzienda = codiceAzienda;
	}

	public String getDenominazione() {
		return denominazione;
	}

	public void setDenominazione(String denominazione) {
		this.denominazione = denominazione;
	}

	public String getCodiceFarmacia() {
		return codiceFarmacia;
	}

	public void setCodiceFarmacia(String codiceFarmacia) {
		this.codiceFarmacia = codiceFarmacia;
	}

	public String getDenominazioneFarmacia() {
		return denominazioneFarmacia;
	}

	public void setDenominazioneFarmacia(String denominazioneFarmacia) {
		this.denominazioneFarmacia = denominazioneFarmacia;
	}

	public String getDescrizioneNatura() {
		return descrizioneNatura;
	}

	public void setDescrizioneNatura(String descrizioneNatura) {
		this.descrizioneNatura = descrizioneNatura;
	}

	public String getPartitaIva() {
		return partitaIva;
	}

	public void setPartitaIva(String partitaIva) {
		this.partitaIva = partitaIva;
	}

	public String getDescrizioneTipoFarmacia() {
		return descrizioneTipoFarmacia;
	}

	public void setDescrizioneTipoFarmacia(String descrizioneTipoFarmacia) {
		this.descrizioneTipoFarmacia = descrizioneTipoFarmacia;
	}

	public String getCognomeDir() {
		return cognomeDirettore;
	}

	public void setCognomeDir(String cognomeDir) {
		this.cognomeDirettore = cognomeDir;
	}

	public String getNomeDir() {
		return nomeDirettore;
	}

	public void setNomeDir(String nomeDir) {
		this.nomeDirettore = nomeDir;
	}

	public String getCodiceFiscaleDir() {
		return codiceFiscaleDirettore;
	}

	public void setCodiceFiscaleDir(String codiceFiscaleDir) {
		this.codiceFiscaleDirettore = codiceFiscaleDir;
	}

	public String getIndirizzo() {
		return indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getNumeroCivico() {
		return numeroCivico;
	}

	public void setNumeroCivico(String numeroCivico) {
		this.numeroCivico = numeroCivico;
	}

	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getComune() {
		return comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getDenominazioneProvincia() {
		return denominazioneProvincia;
	}

	public void setDenominazioneProvincia(String denominazioneProvincia) {
		this.denominazioneProvincia = denominazioneProvincia;
	}

	public Timestamp getDataInizioValiditaFarmacia() {
		return dataInizioValiditaFarmacia;
	}

	public void setDataInizioValiditaFarmacia(Timestamp dataInizioValiditaFarmacia) {
		this.dataInizioValiditaFarmacia = dataInizioValiditaFarmacia;
	}

	public Timestamp getDataFineValiditaFarmacia() {
		return dataFineValiditaFarmacia;
	}

	public void setDataFineValiditaFarmacia(Timestamp dataFineValiditaFarmacia) {
		this.dataFineValiditaFarmacia = dataFineValiditaFarmacia;
	}

	public Timestamp getDataInizioFarmaciaAbituale() {
		return dataInizioFarmaciaAbituale;
	}

	public void setDataInizioFarmaciaAbituale(Timestamp dataInizioFarmaciaAbituale) {
		this.dataInizioFarmaciaAbituale = dataInizioFarmaciaAbituale;
	}

	public Timestamp getDataFineFarmaciaAbituale() {
		return dataFineFarmaciaAbituale;
	}

	public void setDataFineFarmaciaAbituale(Timestamp dataFineFarmaciaAbituale) {
		this.dataFineFarmaciaAbituale = dataFineFarmaciaAbituale;
	}
	 
	 
}
