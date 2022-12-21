/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.dto.configuratorews;

import java.util.Objects;

import org.codehaus.jackson.annotate.JsonProperty;
import io.swagger.annotations.*;



public class Funzionalita   {
  // verra' utilizzata la seguente strategia serializzazione degli attributi: [explicit-as-modeled] 
  
  private String codice = null;
  private String descrizione = null;
  private String codiceFunzionalitaPadre = null;
  private String descrizioneFunzionalitaPadre = null;

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
  @JsonProperty("codice_funzionalita_padre") 
 
  public String getCodiceFunzionalitaPadre() {
    return codiceFunzionalitaPadre;
  }
  public void setCodiceFunzionalitaPadre(String codiceFunzionalitaPadre) {
    this.codiceFunzionalitaPadre = codiceFunzionalitaPadre;
  }

  /**
   **/
  
  @ApiModelProperty(value = "")
  @JsonProperty("descrizione_funzionalita_padre") 
 
  public String getDescrizioneFunzionalitaPadre() {
    return descrizioneFunzionalitaPadre;
  }
  public void setDescrizioneFunzionalitaPadre(String descrizioneFunzionalitaPadre) {
    this.descrizioneFunzionalitaPadre = descrizioneFunzionalitaPadre;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Funzionalita funzionalita = (Funzionalita) o;
    return Objects.equals(codice, funzionalita.codice) &&
        Objects.equals(descrizione, funzionalita.descrizione) &&
        Objects.equals(codiceFunzionalitaPadre, funzionalita.codiceFunzionalitaPadre) &&
        Objects.equals(descrizioneFunzionalitaPadre, funzionalita.descrizioneFunzionalitaPadre);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codice, descrizione, codiceFunzionalitaPadre, descrizioneFunzionalitaPadre);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Funzionalita {\n");
    
    sb.append("    codice: ").append(toIndentedString(codice)).append("\n");
    sb.append("    descrizione: ").append(toIndentedString(descrizione)).append("\n");
    sb.append("    codiceFunzionalitaPadre: ").append(toIndentedString(codiceFunzionalitaPadre)).append("\n");
    sb.append("    descrizioneFunzionalitaPadre: ").append(toIndentedString(descrizioneFunzionalitaPadre)).append("\n");
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

