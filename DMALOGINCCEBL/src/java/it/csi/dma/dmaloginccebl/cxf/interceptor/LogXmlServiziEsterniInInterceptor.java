package it.csi.dma.dmaloginccebl.cxf.interceptor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.interceptor.AbstractSoapInterceptor;
import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.phase.Phase;

import it.csi.dma.dmaloginccebl.business.dao.dto.ServiziRichiamatiXmlDto;

public class LogXmlServiziEsterniInInterceptor extends AbstractSoapInterceptor {

	public LogXmlServiziEsterniInInterceptor() {
		super(Phase.RECEIVE);
	}

	@Override
	public void handleMessage(SoapMessage message) {
		try {
			ServiziRichiamatiXmlDto serviziRichiamatiXmlDto = (ServiziRichiamatiXmlDto) message.getExchange().get(ServiziRichiamatiXmlDto.class.getSimpleName());


			InputStream is = message.getContent(InputStream.class);
			if (is != null) {
				try {
					byte[] payloadBytes = getPayload(is);
					serviziRichiamatiXmlDto.setXmlOut(payloadBytes);
					message.setContent(InputStream.class, new ByteArrayInputStream(payloadBytes));

				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}

			Date date = new Date();
			Timestamp ts = new Timestamp(date.getTime());
			serviziRichiamatiXmlDto.setDataRisposta(ts);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @param is
	 * @return
	 * @throws IOException
	 */
	private byte[] getPayload(InputStream is) throws IOException {
		String result = IOUtils.toString(is);
		is.close();
		return result.getBytes();
	}

}
