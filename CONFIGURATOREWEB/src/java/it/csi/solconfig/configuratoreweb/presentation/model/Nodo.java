/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.presentation.model;

import java.lang.reflect.Field;
import java.util.List;

import it.csi.solconfig.configuratoreweb.business.dao.dto.TreeFunzionalitaDto;
import it.csi.solconfig.configuratoreweb.util.Utils;

public class Nodo {
	
	private Nodo parent;
	
	private TreeFunzionalitaDto treeFunzionalitaDto;
	
	private List<Nodo> figli;
	
	private boolean hasChildren;
	
	private boolean hasParent;
	
	private Boolean isPrimoFiglio;
	
	private boolean checkNull;
	
	private boolean found;
	
	public List<Nodo> getFigli() {
		return figli;
	}

	public void setFigli(List<Nodo> figli) {
		this.figli = figli;
	}

	public TreeFunzionalitaDto getTreeFunzionalitaDto() {
		return treeFunzionalitaDto;
	}

	public void setTreeFunzionalitaDto(TreeFunzionalitaDto treeFunzionalitaDto) {
		this.treeFunzionalitaDto = treeFunzionalitaDto;
	}

	public Nodo getParent() {
		return parent;
	}

	public void setParent(Nodo parent) {
		this.parent = parent;
	}

	public boolean gethasChildren() {
		if(figli == null ||figli.isEmpty()) {
			this.hasChildren = false;
			return this.hasChildren;
		}
		this.hasChildren = true;
		return this.hasChildren;
	}

	public void setAsChildren(boolean hasChildren) {
		this.hasChildren = hasChildren;
	}
	
	public boolean getHasParent() {
		if(parent == null) {
			hasParent = false;
			return this.hasParent;
		}
		this.hasChildren = true;
		return this.hasChildren;
	}
	
	public Boolean getIsPrimoFiglio() {
		if(this.isPrimoFiglio == null) {
			this.isPrimoFiglio = true;
			return this.isPrimoFiglio;
		}
		this.isPrimoFiglio = false;
		return this.isPrimoFiglio;
	}

	public void setIsPrimoFiglio(Boolean isPrimoFiglio) {
		this.isPrimoFiglio = isPrimoFiglio;
	}
	
	public Boolean primoFiglio(Nodo figlioCorrente) {
		if(figlioCorrente == Utils.getFirstRecord(figli)) {
			return true;
		}
		return false;
	}
	
	public boolean isCheckNull() throws IllegalAccessException {
	    for (Field f : getClass().getDeclaredFields()) {
	        if (f.get(this) != null) {
	        	this.checkNull = false;
	            return this.checkNull;
	        }
	    }
	    this.checkNull = true;
        return this.checkNull;            
	}

	public void setCheckNull(boolean checkNull) {
		this.checkNull = checkNull;
	}
	
	public boolean equals(Nodo nodoConfronto) {
		if(!this.getTreeFunzionalitaDto().getFunzionalitaDto().getIdFunzione().equals(nodoConfronto.treeFunzionalitaDto.getFunzionalitaDto().getIdFunzione())) {
			return false;
		}
		return true;
	}

	public boolean isFound() {
		return found;
	}

	public void setFound(boolean found) {
		this.found = found;
	}
	
}
