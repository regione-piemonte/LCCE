/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.filter;

import java.util.List;

public interface ModelFilter<T> {
	
	public List<T> doFilter(List<T> list, String filter);
	

}
