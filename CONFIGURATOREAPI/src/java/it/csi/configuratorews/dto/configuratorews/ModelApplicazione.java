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



public class ModelApplicazione   {
  // verra' utilizzata la seguente strategia serializzazione degli attributi: [explicit-as-modeled] 
  
  private String codice = null;
  private String descrizione = null;
  private String redirectUrl = null;
  
  
  
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("redirect_url")
  public String getRedirectUrl() {
	return redirectUrl;
  }

  public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
  }


  /**
   **/
  @ApiModelProperty(required = true, value = "")
  @JsonProperty("codice")
  @NotNull
  public String getCodice() {
    return codice;
  }
  public void setCodice(String codice) {
    this.codice = codice;
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
    ModelApplicazione model = (ModelApplicazione) o;
    return Objects.equals(codice, model.codice) && Objects.equals(descrizione, model.descrizione)
    		&& Objects.equals(redirectUrl, model.redirectUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(codice);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ModelApplicazione {\n");
    
    sb.append("    codice: ").append(toIndentedString(codice)).append("\n");
    sb.append("    descrizione: ").append(toIndentedString(descrizione)).append("\n");
    sb.append("    redirectUrl: ").append(toIndentedString(redirectUrl)).append("\n");
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

