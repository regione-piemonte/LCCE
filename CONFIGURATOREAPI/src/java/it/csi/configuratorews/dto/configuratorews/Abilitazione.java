/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.dto.configuratorews;

import io.swagger.annotations.ApiModelProperty;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Objects;


public class Abilitazione   {
  // verra' utilizzata la seguente strategia serializzazione degli attributi: [explicit-as-modeled] 
  
  private ModelRuolo ruolo = null;
  private ModelCollocazione collocazione = null;
  private Profilo profilo = null;

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("ruolo") 
 
  public ModelRuolo getRuolo() {
    return ruolo;
  }
  public void setRuolo(ModelRuolo ruolo) {
    this.ruolo = ruolo;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("collocazione") 
 
  public ModelCollocazione getCollocazione() {
    return collocazione;
  }
  public void setCollocazione(ModelCollocazione collocazione) {
    this.collocazione = collocazione;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("profilo") 
 
  public Profilo getProfilo() {
    return profilo;
  }
  public void setProfilo(Profilo profilo) {
    this.profilo = profilo;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Abilitazione abilitazione = (Abilitazione) o;
    return Objects.equals(ruolo, abilitazione.ruolo) &&
        Objects.equals(collocazione, abilitazione.collocazione) &&
        Objects.equals(profilo, abilitazione.profilo);
  }

  @Override
  public int hashCode() {
    return Objects.hash(ruolo, collocazione, profilo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Abilitazione {\n");
    
    sb.append("    ruolo: ").append(toIndentedString(ruolo)).append("\n");
    sb.append("    collocazione: ").append(toIndentedString(collocazione)).append("\n");
    sb.append("    profilo: ").append(toIndentedString(profilo)).append("\n");
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

