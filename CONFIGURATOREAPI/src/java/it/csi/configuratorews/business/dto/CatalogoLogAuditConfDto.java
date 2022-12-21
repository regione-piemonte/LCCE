/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.dto;


import javax.persistence.*;

@Entity
@Table(name = "auth_d_catalogo_log_audit_conf")
@SequenceGenerator(name = "seq_auth_d_catalogo_log_audit_conf", sequenceName = "seq_auth_d_catalogo_log_audit_conf", allocationSize = 1)
public class CatalogoLogAuditConfDto extends BaseDto {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_auth_d_catalogo_log_audit_conf")
    @Column(name = "id")
    private Long id;

    @Column(name = "key_operazione")
    private String keyOperazione;

    @Column(name = "ogg_operazione")
    private String oggOperazione;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyOperazione() {
        return keyOperazione;
    }

    public void setKeyOperazione(String keyOperazione) {
        this.keyOperazione = keyOperazione;
    }

    public String getOggOperazione() {
        return oggOperazione;
    }

    public void setOggOperazione(String oggOperazione) {
        this.oggOperazione = oggOperazione;
    }



}


