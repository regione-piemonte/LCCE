package it.csi.dma.dmaloginccebl.cxf.interceptor;

import java.io.ByteArrayOutputStream;
import java.sql.Timestamp;
import java.util.Date;

import javax.xml.soap.SOAPMessage;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.phase.Phase;

import it.csi.dma.dmaloginccebl.business.dao.dto.ServiziRichiamatiXmlDto;

public class LogXmlServiziEsterniOutInterceptor extends AbstractSoapInterceptor {
	
	public LogXmlServiziEsterniOutInterceptor() {
		super(Phase.USER_PROTOCOL);
	}

	@Override
	public void handleMessage(SoapMessage message) {
		try {
			ServiziRichiamatiXmlDto serviziRichiamatiXmlDto = (ServiziRichiamatiXmlDto) message.getExchange().get(ServiziRichiamatiXmlDto.class.getSimpleName());

			SOAPMessage sm = message.getContent(SOAPMessage.class);
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			sm.writeTo(stream);

			serviziRichiamatiXmlDto.setXmlIn(stream.toByteArray());
			Date date = new Date();
			Timestamp ts = new Timestamp(date.getTime());
			serviziRichiamatiXmlDto.setDataInserimento(ts);
			serviziRichiamatiXmlDto.setDataChiamata(ts);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

//		//gestione singoli campi dell'xml
//		SOAPBody body = sm.getSOAPBody();
//		System.out.println(body.getElementsByTagName("ns3:ruoloUtente").item(0).getTextContent());

	}

}
