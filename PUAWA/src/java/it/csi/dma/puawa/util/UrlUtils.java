/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.puawa.util;

public class UrlUtils {

	private String logoutUrl;
	private String fseUrl;
	private String rolUrl;

	public String getFseUrl() {
		return fseUrl;
	}

	public void setFseUrl(String fseUrl) {
		this.fseUrl = fseUrl;
	}

	public String getRolUrl() {
		return rolUrl;
	}

	public void setRolUrl(String rolUrl) {
		this.rolUrl = rolUrl;
	}

	public String getLogoutUrl() {
		return logoutUrl;
	}

	public void setLogoutUrl(String logoutUrl) {
		this.logoutUrl = logoutUrl;
	}
}
