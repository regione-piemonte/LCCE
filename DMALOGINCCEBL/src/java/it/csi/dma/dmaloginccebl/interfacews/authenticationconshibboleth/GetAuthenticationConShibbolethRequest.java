package it.csi.dma.dmaloginccebl.interfacews.authenticationconshibboleth;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


import it.csi.dma.dmaloginccebl.interfacews.msg.ParametriLogin;

@XmlRootElement(name = "getAuthenticationConShibbolethRequest")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getAuthenticationConShibbolethRequest", propOrder = {
	    "richiedente",
	    "codiceFiscaleAssistito",
	    "parametriLogin"
})
public class GetAuthenticationConShibbolethRequest {
	@XmlElement(namespace = "http://dmac.csi.it/")
    protected Richiedente richiedente;
	@XmlElement(namespace = "http://dma.csi.it/")
	protected String codiceFiscaleAssistito;
	@XmlElement(namespace = "http://dma.csi.it/")
	protected List<ParametriLogin> parametriLogin;

	public Richiedente getRichiedente() {
		return richiedente;
	}

	public void setRichiedente(Richiedente richiedente) {
		this.richiedente = richiedente;
	}

	public String getCodiceFiscaleAssistito() {
		return codiceFiscaleAssistito;
	}

	public void setCodiceFiscaleAssistito(String codiceFiscaleAssistito) {
		this.codiceFiscaleAssistito = codiceFiscaleAssistito;
	}

	public List<ParametriLogin> getParametriLogin() {
		return parametriLogin;
	}

	public void setParametriLogin(List<ParametriLogin> parametriLogin) {
		this.parametriLogin = parametriLogin;
	}


}
