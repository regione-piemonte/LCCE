/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.util;


import org.apache.commons.io.IOUtils;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.io.ByteArrayOutputStream;
import java.util.Set;
import java.util.logging.Logger;

public class MyMessageHandlerCustom implements SOAPHandler<SOAPMessageContext> {
	
	
	public String requestXml;
	public String responseXml;

    private static Logger logger = Logger.getLogger(MyMessageHandlerCustom.class
            .getCanonicalName());

    public static final String REQUEST_XML="REQUEST_XML";
    
    public static final String RESPONSE_XML="RESPONSE_XML";


    @Override
    public boolean handleFault(SOAPMessageContext context) {

        writeMessageLogging(context);
        return true;
    }

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        String xml = writeMessageLogging(context);
        
        
        Boolean outboundProperty = (Boolean) context
                .get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        
        
        if (!outboundProperty.booleanValue()) {
        	this.setResponseXml(xml);
            
        }else  if (outboundProperty.booleanValue()) {
        	
        	
        	this.setRequestXml(xml);
        }
        
        
        
        return true;

    }

    private String writeMessageLogging(SOAPMessageContext smc) {
        Boolean outboundProperty = (Boolean) smc
                .get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        
        String xml= null;

        SOAPMessage message = smc.getMessage();
        ByteArrayOutputStream out=null;
        try {
             out = new ByteArrayOutputStream();
             message.writeTo(out);
             String strMsg = new String(out.toByteArray());

            if (!outboundProperty.booleanValue()) {
            	 String responseXML=(String)smc.get(REQUEST_XML);
                 xml = "Request of Response:"+responseXML;
                
            }else  if (outboundProperty.booleanValue()) {
               
                
                
                String requestXML=(String)smc.get(REQUEST_XML);
                xml = "Request of Response:"+requestXML;
            }else{
                smc.put(REQUEST_XML,strMsg);
            }
            xml ="strMsg:" + strMsg;
            out.close();
        } catch (Exception e) {
        	xml ="Exception in handler:" +e.getMessage();
        }finally{
            IOUtils.closeQuietly(out);
        }
        
        return xml;

    }

	@Override
	public void close(MessageContext arg0) {
		//  Auto-generated method stub
		
	}

	@Override
	public Set<QName> getHeaders() {
		//  Auto-generated method stub
		return null;
	}

	public String getRequestXml() {
		return requestXml;
	}

	public void setRequestXml(String requestXml) {
		this.requestXml = requestXml;
	}

	public String getResponseXml() {
		return responseXml;
	}

	public void setResponseXml(String responseXml) {
		this.responseXml = responseXml;
	}
	
	
}