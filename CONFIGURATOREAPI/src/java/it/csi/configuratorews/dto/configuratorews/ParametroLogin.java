/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.dto.configuratorews;

import java.util.Objects;

import org.codehaus.jackson.annotate.JsonProperty;
import io.swagger.annotations.*;



public class ParametroLogin   {
  // verra' utilizzata la seguente strategia serializzazione degli attributi: [explicit-as-modeled] 
  
  private String codice = null;
  private String valore = null;

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
  @JsonProperty("valore") 
 
  public String getValore() {
    return valore;
  }
  public void setValore(String valore) {
    this.valore = valore;
  }


  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ParametroLogin parametroLogin = (ParametroLogin) o;
    return Objects.equals(codice, parametroLogin.codice) &&
        Objects.equals(valore, parametroLogin.valore);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codice, valore);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ParametroLogin {\n");
    
    sb.append("    codice: ").append(toIndentedString(codice)).append("\n");
    sb.append("    valore: ").append(toIndentedString(valore)).append("\n");
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

