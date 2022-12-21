/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.dto.configuratorews;

import java.io.Serializable;
import java.util.List;

public class ListaPermessi implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5768567033166121661L;

	private List<Permesso> permessi;

	public List<Permesso> getPermessi() {
		return permessi;
	}

	public void setPermessi(List<Permesso> permessi) {
		this.permessi = permessi;
	}
}
