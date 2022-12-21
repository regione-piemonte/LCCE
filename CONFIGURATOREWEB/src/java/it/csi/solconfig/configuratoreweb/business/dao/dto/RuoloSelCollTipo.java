/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RuoloSelCollTipo {
	private static final String NULL = "[null]";
	private int id;
	private String idRuolo;
	private String idCollTipo;

	public RuoloSelCollTipo() {
		super();
	}

	public RuoloSelCollTipo(String idRuolo, String idCollTipo) {
		super();
		this.idRuolo = idRuolo;
		this.idCollTipo = idCollTipo;
	}

	public RuoloSelCollTipo(int id, String idRuolo, String idCollTipo) {
		super();
		this.setId(id);
		this.idRuolo = idRuolo;
		this.idCollTipo = idCollTipo;
	}

	public RuoloSelCollTipo(RuoloDto ruoloSelezionabile, CollocazioneTipoDto collocazioneTipo) {
		this.idRuolo = nvl(ruoloSelezionabile);
		this.idCollTipo = nvl(collocazioneTipo);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getIdRuolo() {
		return idRuolo;
	}

	public void setIdRuolo(String idRuolo) {
		this.idRuolo = idRuolo;
	}

	public String getIdCollTipo() {
		return nvl(idCollTipo);
	}

	public void setIdCollTipo(String idCollTipo) {
		this.idCollTipo = idCollTipo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(getIdCollTipo(), idRuolo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RuoloSelCollTipo other = (RuoloSelCollTipo) obj;
		// return // Objects.equals(getIdCollTipo(), other.getIdCollTipo()) &&
		return Objects.equals(getIdRuolo(), other.getIdRuolo());
	}

	@Override
	public String toString() {
		return idRuolo + "-" + nvl(idCollTipo);
	}

	public static RuoloSelCollTipo valueOf(String v) {
		RuoloSelCollTipo r = new RuoloSelCollTipo();
		if (v != null) {
			String[] vs = v.split("-");
			r.setIdRuolo(vs[0]);
			if (vs.length > 1) {
				r.setIdCollTipo(vs[1]);
			}
		}

		return r;
	}

	public static List<RuoloSelCollTipo> asListOf(List<RuoloSelezionabileDto> druoli) {
		List<RuoloSelCollTipo> r = new ArrayList<>();
		String coll;
		String ruolo;
		for (RuoloSelezionabileDto d : druoli) {
			if (d.getCollocazioneTipo() != null && d.getCollocazioneTipo().getIdColTipo() != null) {
				coll = d.getCollocazioneTipo().getIdColTipo().toString();
			} else {
				coll = null;
			}
			ruolo = d.getRuoloSelezionabile().getId().toString();
			r.add(new RuoloSelCollTipo(ruolo, coll));

		}
		return r;
	}

	public static List<RuoloSelCollTipo> asListOf(List<String> ruoliSel, List<String> collTipoSel) {
		List<RuoloSelCollTipo> r = new ArrayList<>();
		for (int i = 0; ruoliSel != null && i < ruoliSel.size(); i++) {
			r.add(new RuoloSelCollTipo(ruoliSel.get(i), getCollTipoSel(collTipoSel, i)));
		}

		return r;
	}

	private static String getCollTipoSel(List<String> collTipoSel, int i) {
		String data = null;
		if (i < collTipoSel.size())
			data = nvl(collTipoSel.get(i));
		return data;
	}

	private static String nvl(Long l) {
		return (l == null ? NULL : l.toString());
	}

	private static String nvl(String s) {
		return (isNull(s) ? NULL : s);
	}

	private String nvl(CollocazioneTipoDto collocazioneTipo) {
		return (collocazioneTipo == null ? NULL : nvl(collocazioneTipo.getIdColTipo()));
	}

	private String nvl(RuoloDto ruoloSelezionabile) {
		return (ruoloSelezionabile == null ? NULL : nvl(ruoloSelezionabile.getId()));
	}

	public static String toString(String s) {
		return (isNull(s) ? null : s);
	}

	public static Long parseLong(String s) {
		return (isNull(s) ? null : Long.parseLong(s));
	}

	public static boolean isNull(String s) {
		return s == null || NULL.equalsIgnoreCase(s) || "".equals(s.trim()) || s.trim().length() == 0;
	}

}
