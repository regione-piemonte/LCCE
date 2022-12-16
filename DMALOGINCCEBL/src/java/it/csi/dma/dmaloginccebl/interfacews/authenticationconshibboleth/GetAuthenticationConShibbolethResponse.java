package it.csi.dma.dmaloginccebl.interfacews.authenticationconshibboleth;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import it.csi.dma.dmaloginccebl.interfacews.msg.Errore;
import it.csi.dma.dmaloginccebl.interfacews.msg.RisultatoCodice;
import it.csi.dma.dmaloginccebl.interfacews.msg.ServiceResponse;

@XmlRootElement(name = "getAuthenticationConShibbolethResponse")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getAuthenticationConShibbolethResponse", propOrder = { "authenticationToken" })
public class GetAuthenticationConShibbolethResponse extends ServiceResponse {
	@XmlElement(namespace = "http://dma.csi.it/")
	private String authenticationToken;

	public GetAuthenticationConShibbolethResponse(List<Errore> errori, RisultatoCodice esito) {
		super(errori, esito);
	}

	public GetAuthenticationConShibbolethResponse() {
		super();
	}

	public String getAuthenticationToken() {
		return authenticationToken;
	}

	public void setAuthenticationToken(String authenticationToken) {
		this.authenticationToken = authenticationToken;
	}

}
