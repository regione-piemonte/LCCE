/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.dto.configuratorews;

import java.io.Serializable;
import java.util.List;

public class Preferences implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<ItemPreference> itemPreference;
	public List<ItemPreference> getItemPreference() {
		return itemPreference;
	}
	public void setItemPreference(List<ItemPreference> itemPreference) {
		this.itemPreference = itemPreference;
	}
	@Override
	public String toString() {
		StringBuilder sg = new StringBuilder();
		itemPreference.forEach((item)->{
			sg.append(item.toString());
		});
		return sg.toString();
	}
	
	
}
