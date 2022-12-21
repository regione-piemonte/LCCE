/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.dto.configuratorews;

import java.util.Objects;
import java.util.ArrayList;
import java.util.List;
import org.codehaus.jackson.annotate.JsonProperty;

import javax.validation.constraints.*;
import io.swagger.annotations.*;



public class ParametriAutenticazione   {
  // verra' utilizzata la seguente strategia serializzazione degli attributi: [explicit-as-modeled] 
  
  private String ruolo = null;
  private String collocazione = null;
  private String azienda = null;
  private String applicazione = null;
  private List<ParametroLogin> parametriLogin = new ArrayList<ParametroLogin>();

  /**
   * codice ruolo dell&#39;utente
   **/
  
  @ApiModelProperty(required = true, value = "codice ruolo dell'utente")
  @JsonProperty("ruolo") 
 
  @NotNull
  public String getRuolo() {
    return ruolo;
  }
  public void setRuolo(String ruolo) {
    this.ruolo = ruolo;
  }

  /**
   * codice collocazione dell&#39;utente
   **/
  
  @ApiModelProperty(required = true, value = "codice collocazione dell'utente")
  @JsonProperty("collocazione") 
 
  @NotNull
  public String getCollocazione() {
    return collocazione;
  }
  public void setCollocazione(String collocazione) {
    this.collocazione = collocazione;
  }

  /**
   * codice azienda dell&#39;utente
   **/
  
  @ApiModelProperty(required = true, value = "codice azienda dell'utente")
  @JsonProperty("azienda") 
 
  @NotNull
  public String getAzienda() {
    return azienda;
  }
  public void setAzienda(String azienda) {
    this.azienda = azienda;
  }

  /**
   * codice applicazione richiesta
   **/
  
  @ApiModelProperty(required = true, value = "codice applicazione richiesta")
  @JsonProperty("applicazione") 
 
  @NotNull
  public String getApplicazione() {
    return applicazione;
  }
  public void setApplicazione(String applicazione) {
    this.applicazione = applicazione;
  }

  /**
   * parametri opzionali utili al login
   **/
  
  @ApiModelProperty(value = "parametri opzionali utili al login")
  @JsonProperty("parametri_login") 
 
  public List<ParametroLogin> getParametriLogin() {
    return parametriLogin;
  }
  public void setParametriLogin(List<ParametroLogin> parametriLogin) {
    this.parametriLogin = parametriLogin;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ParametriAutenticazione parametriAutenticazione = (ParametriAutenticazione) o;
    return Objects.equals(ruolo, parametriAutenticazione.ruolo) &&
        Objects.equals(collocazione, parametriAutenticazione.collocazione) &&
        Objects.equals(azienda, parametriAutenticazione.azienda) &&
        Objects.equals(applicazione, parametriAutenticazione.applicazione) &&
        Objects.equals(parametriLogin, parametriAutenticazione.parametriLogin);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ruolo, collocazione, azienda, applicazione, parametriLogin);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ParametriAutenticazione {\n");
    
    sb.append("    ruolo: ").append(toIndentedString(ruolo)).append("\n");
    sb.append("    collocazione: ").append(toIndentedString(collocazione)).append("\n");
    sb.append("    azienda: ").append(toIndentedString(azienda)).append("\n");
    sb.append("    applicazione: ").append(toIndentedString(applicazione)).append("\n");
    sb.append("    parametriLogin: ").append(toIndentedString(parametriLogin)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

