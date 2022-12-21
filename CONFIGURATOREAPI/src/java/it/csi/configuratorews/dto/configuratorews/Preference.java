/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.dto.configuratorews;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class Preference implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean email;
	private boolean sms;
	private boolean push;
	private boolean emailSelectable;
	private boolean smsSelectable;
	private boolean pushSelectable;
	@ApiModelProperty(value = "")
	@JsonProperty("email_selectable") 
	public boolean isEmailSelectable() {
		return emailSelectable;
	}
	public void setEmailSelectable(boolean emailSelectable) {
		this.emailSelectable = emailSelectable;
	}
	@ApiModelProperty(value = "")
	@JsonProperty("sms_selectable") 
	public boolean isSmsSelectable() {
		return smsSelectable;
	}
	public void setSmsSelectable(boolean smsSelectable) {
		this.smsSelectable = smsSelectable;
	}
	@ApiModelProperty(value = "")
	@JsonProperty("push_selectable") 
	public boolean isPushSelectable() {
		return pushSelectable;
	}
	public void setPushSelectable(boolean pushSelectable) {
		this.pushSelectable = pushSelectable;
	}
	public boolean isEmail() {
		return email;
	}
	public void setEmail(boolean email) {
		this.email = email;
	}
	public boolean isSms() {
		return sms;
	}
	public void setSms(boolean sms) {
		this.sms = sms;
	}
	public boolean isPush() {
		return push;
	}
	public void setPush(boolean push) {
		this.push = push;
	}

}
