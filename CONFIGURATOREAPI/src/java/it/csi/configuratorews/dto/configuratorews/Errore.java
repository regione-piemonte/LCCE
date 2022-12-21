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
import io.swagger.annotations.*;



public class Errore   {
  // verra' utilizzata la seguente strategia serializzazione degli attributi: [explicit-as-modeled] 
  
  private Integer status = null;
  private String codice = null;
  private String descrizione = null;
  private List<Dettaglio> dettaglio = new ArrayList<Dettaglio>();

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("status") 
 
  public Integer getStatus() {
    return status;
  }
  public void setStatus(Integer status) {
    this.status = status;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("codice") 
 
  public String getCodice() {
    return codice;
  }
  public void setCodice(String codice) {
    this.codice = codice;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("descrizione") 
 
  public String getDescrizione() {
    return descrizione;
  }
  public void setDescrizione(String descrizione) {
    this.descrizione = descrizione;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("dettaglio") 
 
  public List<Dettaglio> getDettaglio() {
    return dettaglio;
  }
  public void setDettaglio(List<Dettaglio> dettaglio) {
    this.dettaglio = dettaglio;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Errore errore = (Errore) o;
    return Objects.equals(status, errore.status) &&
        Objects.equals(codice, errore.codice) &&
        Objects.equals(descrizione, errore.descrizione) &&
        Objects.equals(dettaglio, errore.dettaglio);
  }

  @Override
  public int hashCode() {
    return Objects.hash(status, codice, descrizione, dettaglio);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Errore {\n");
    
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    codice: ").append(toIndentedString(codice)).append("\n");
    sb.append("    descrizione: ").append(toIndentedString(descrizione)).append("\n");
    sb.append("    dettaglio: ").append(toIndentedString(dettaglio)).append("\n");
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

