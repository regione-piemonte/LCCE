/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.model;

import it.csi.solconfig.configuratoreweb.util.validators.CodiceFiscale;
import it.csi.solconfig.configuratoreweb.util.validators.Date;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class FormDatiUtente implements Serializable {

    private Long id;

    @NotBlank
    private String nome;

    @NotBlank
    private String cognome;

    //@NotBlank
    @Date
    private String dataDiNascita;

    @CodiceFiscale
    @NotBlank
    private String cf;

    //@NotBlank
    private String provinciaDiNascita;

    //@NotBlank
    private String comuneDiNascita;

    @NotBlank
    private String sesso;

    @Email
    @NotBlank
    private String email;

    //@NotBlank
    private String telefono;

    @NotNull
    private Boolean stato;

    @Date
    private String dataFineValidita;

    //@NotNull
    private Long contratto;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getDataDiNascita() {
        return dataDiNascita;
    }

    public void setDataDiNascita(String dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public String getProvinciaDiNascita() {
        return provinciaDiNascita;
    }

    public void setProvinciaDiNascita(String provinciaDiNascita) {
        this.provinciaDiNascita = provinciaDiNascita;
    }

    public String getComuneDiNascita() {
        return comuneDiNascita;
    }

    public void setComuneDiNascita(String comuneDiNascita) {
        this.comuneDiNascita = comuneDiNascita;
    }

    public String getSesso() {
        return sesso;
    }

    public void setSesso(String sesso) {
        this.sesso = sesso;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Boolean getStato() {
        return stato;
    }

    public void setStato(Boolean stato) {
        this.stato = stato;
    }

    public String getDataFineValidita() {
        return dataFineValidita;
    }

    public void setDataFineValidita(String dataFineValidita) {
        this.dataFineValidita = dataFineValidita;
    }

    public Long getContratto() {
        return contratto;
    }

    public void setContratto(Long contratto) {
        this.contratto = contratto;
    }
}
