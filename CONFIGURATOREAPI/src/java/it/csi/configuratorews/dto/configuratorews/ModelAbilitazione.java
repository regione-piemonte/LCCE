/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.dto.configuratorews;

import java.util.Objects;

import org.codehaus.jackson.annotate.JsonProperty;

import javax.validation.constraints.*;
import io.swagger.annotations.*;



public class ModelAbilitazione   {
  // verra' utilizzata la seguente strategia serializzazione degli attributi: [explicit-as-modeled] 
  
  private String applicazione = null;
  private String descrizione = null;
 

/**
   **/
  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("applicazione")
  @NotNull
  public String getApplicazione() {
    return applicazione;
  }
  public void setApplicazione(String applicazione) {
    this.applicazione = applicazione;
  }

  @ApiModelProperty(required = true, value = "")
  @JsonProperty("descrizione")
  public String getDescrizione() {
    return descrizione;
  }
  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ModelAbilitazione modelAbilitazione = (ModelAbilitazione) o;
    return Objects.equals(applicazione, modelAbilitazione.applicazione) && Objects.equals(descrizione, modelAbilitazione.descrizione);
  }

  @Override
  public int hashCode() {
    return Objects.hash(applicazione);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ModelAbilitazione {\n");
    
    sb.append("    applicazione: ").append(toIndentedString(applicazione)).append("\n");
    sb.append("    descrizione: ").append(toIndentedString(descrizione)).append("\n");
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

