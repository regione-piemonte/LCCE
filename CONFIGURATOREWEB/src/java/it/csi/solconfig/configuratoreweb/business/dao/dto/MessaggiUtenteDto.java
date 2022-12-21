/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.business.dao.dto;
	
	import javax.persistence.Column;
	import javax.persistence.Entity;
	import javax.persistence.GeneratedValue;
	import javax.persistence.GenerationType;
	import javax.persistence.Id;
	import javax.persistence.SequenceGenerator;
	import javax.persistence.Table;

	@Entity
	@Table(name = "auth_d_messaggi_utente")
	@SequenceGenerator(name = "seq_auth_d_messaggi_utente", sequenceName = "seq_auth_d_messaggi_utente", allocationSize = 1)
	public class MessaggiUtenteDto extends BaseDto {

		@Id
		@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_auth_d_messaggi_utente")
		@Column(name = "id")
		private Long id;
		
		@Column(name = "codice")
		private String codice;

		
		@Column(name = "descrizione")
		private String descrizione;
		
		//tipo messaggio: E=ERRORE, W= WARNING, S=SUCCESSO
		@Column(name = "tipo_messaggio")
		private String tipoMessaggio;
		
		
		//getters and setters
		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getCodice() {
			return codice;
		}

		public void setCodice(String codice) {
			this.codice = codice;
		}
		
		public String getDescrizione() {
			return descrizione;
		}

		public void setDescrizione(String descrizione) {
			this.descrizione = descrizione;
		}
		
		public String getTipoMessaggio() {
			return tipoMessaggio;
		}

		public void setTipoMessaggio(String tipoMessaggio) {
			this.tipoMessaggio = tipoMessaggio;
		}



		@Override
		public String toString() {
			return "MessaggiUtenteDto [id=" + id + ", codice=" + codice + ", descrizione=" + descrizione
					+ ", tipoMessaggio=" + tipoMessaggio + "]";
		}

}
