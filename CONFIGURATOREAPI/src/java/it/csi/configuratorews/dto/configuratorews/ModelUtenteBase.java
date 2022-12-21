/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.dto.configuratorews;

import java.util.Objects;

import org.codehaus.jackson.annotate.JsonProperty;
import io.swagger.annotations.*;



public class ModelUtenteBase   {
  // verra' utilizzata la seguente strategia serializzazione degli attributi: [explicit-as-modeled] 
  
  private String nome = null;
  private String cognome = null;
  private String codiceFiscale = null;
  private String sesso = null;
  
  
  
  @ApiModelProperty(value = "sesso")
  @JsonProperty("sesso") 
  public String getSesso() {
	return sesso;
  }
  public void setSesso(String sesso) {
	this.sesso = sesso;
  }

  
  /**
   * nome dell&#39;utente
   **/
  
  @ApiModelProperty(value = "nome dell'utente")
  @JsonProperty("nome") 
 
  public String getNome() {
    return nome;
  }
  public void setNome(String nome) {
    this.nome = nome;
  }

  /**
   * cognome dell&#39;utente
   **/
  
  @ApiModelProperty(value = "cognome dell'utente")
  @JsonProperty("cognome") 
 
  public String getCognome() {
    return cognome;
  }
  public void setCognome(String cognome) {
    this.cognome = cognome;
  }

  /**
   * codice fiscale dell&#39;utente
   **/
  
  @ApiModelProperty(value = "codice fiscale dell'utente")
  @JsonProperty("codice_fiscale") 
 
  public String getCodiceFiscale() {
    return codiceFiscale;
  }
  public void setCodiceFiscale(String codiceFiscale) {
    this.codiceFiscale = codiceFiscale;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ModelUtenteBase modelUtenteBase = (ModelUtenteBase) o;
    return Objects.equals(nome, modelUtenteBase.nome) &&
        Objects.equals(cognome, modelUtenteBase.cognome) &&
        Objects.equals(codiceFiscale, modelUtenteBase.codiceFiscale);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nome, cognome, codiceFiscale);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ModelUtenteBase {\n");
    
    sb.append("    nome: ").append(toIndentedString(nome)).append("\n");
    sb.append("    cognome: ").append(toIndentedString(cognome)).append("\n");
    sb.append("    codiceFiscale: ").append(toIndentedString(codiceFiscale)).append("\n");
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

