/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.business.dao.dto;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;


@Entity
@Table(name = "auth_t_funzionalita")
@SequenceGenerator(name="auth_t_funzionalita_fnz_id_seq", sequenceName="auth_t_funzionalita_fnz_id_seq",allocationSize=1)
public class FunzionalitaDto extends BaseDto {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="auth_t_funzionalita_fnz_id_seq")
    @Column(name = "fnz_id")
	private Long idFunzione;
	
	@Column(name = "fnz_codice")
	private String codiceFunzione;

	@Column(name = "fnz_descrizione")
	private String descrizioneFunzione;

	@ManyToOne
	@JoinColumn(name = "fnz_tipo_id", referencedColumnName = "fnz_tipo_id")
	private TipoFunzionalitaDto tipoFunzionalitaDto;

	@ManyToOne
	@JoinColumn(name = "applicazione_id", referencedColumnName = "id")
	private ApplicazioneDto applicazioneDto;
	
	@Column(name = "data_inizio_validita")
	private Timestamp dataInizioValidita;
	
	@Column(name = "data_fine_validita")
	private Timestamp dataFineValidita;

	public Long getIdFunzione() {
		return idFunzione;
	}

	public void setIdFunzione(Long idFunzione) {
		this.idFunzione = idFunzione;
	}

	public String getCodiceFunzione() {
		return codiceFunzione;
	}

	public void setCodiceFunzione(String codiceFunzione) {
		this.codiceFunzione = codiceFunzione;
	}

	public String getDescrizioneFunzione() {
		return descrizioneFunzione;
	}

	public void setDescrizioneFunzione(String descrizioneFunzione) {
		this.descrizioneFunzione = descrizioneFunzione;
	}

	public TipoFunzionalitaDto getTipoFunzionalitaDto() {
		return tipoFunzionalitaDto;
	}

	public void setTipoFunzionalitaDto(TipoFunzionalitaDto tipoFunzionalitaDto) {
		this.tipoFunzionalitaDto = tipoFunzionalitaDto;
	}

	public ApplicazioneDto getApplicazioneDto() {
		return applicazioneDto;
	}

	public void setApplicazioneDto(ApplicazioneDto applicazioneDto) {
		this.applicazioneDto = applicazioneDto;
	}

	public Timestamp getDataInizioValidita() {
		return dataInizioValidita;
	}

	public void setDataInizioValidita(Timestamp dataInizioValidita) {
		this.dataInizioValidita = dataInizioValidita;
	}

	public Timestamp getDataFineValidita() {
		return dataFineValidita;
	}

	public void setDataFineValidita(Timestamp dataFineValidita) {
		this.dataFineValidita = dataFineValidita;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		FunzionalitaDto that = (FunzionalitaDto) o;
		return getIdFunzione().equals(that.getIdFunzione());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getIdFunzione());
	}
}
