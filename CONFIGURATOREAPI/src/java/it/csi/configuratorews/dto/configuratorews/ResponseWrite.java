/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.dto.configuratorews;

import java.io.Serializable;
import java.util.List;

public class ResponseWrite implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4118792039360266050L;
	private Integer status;
	private String code;
	private String title;
	private List<ResponseDetail> responseDetail;
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public List<ResponseDetail> getResponseDetail() {
		return responseDetail;
	}
	public void setResponseDetail(List<ResponseDetail> responseDetail) {
		this.responseDetail = responseDetail;
	}
}
