package it.csi.dma.dmaloginccebl.cxf.interceptor;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.cxf.helpers.IOUtils;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import it.csi.dma.dmaloginccebl.business.dao.MessaggiXmlLowDao;
import it.csi.dma.dmaloginccebl.business.dao.dto.MessaggiXmlDto;
import it.csi.dma.dmaloginccebl.util.ApplicationContextProvider;
import it.csi.dma.dmaloginccebl.util.PersistXmlFacade;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LogXmlInInterceptor extends AbstractPhaseInterceptor<Message> {

	public static final String AUTH_L_XML_MESSAGGI_ID = "AUTH_L_XML_MESSAGGI_ID43342";
	private static final String UTF_8 = "UTF-8";

	ApplicationContext appContext;

	@Autowired
	PersistXmlFacade persistXmlFacade;

	public LogXmlInInterceptor() {
		super(Phase.RECEIVE);
	}

	@Override
	public void handleMessage(Message message) {
		appContext = ApplicationContextProvider.getApplicationContext();
		persistXmlFacade = appContext.getBean(PersistXmlFacade.class);
		message.put(Message.ENCODING, UTF_8);
		InputStream is = message.getContent(InputStream.class);
		if (is != null) {
			try {

				byte[] payloadBytes = getPayload(is);
				MessaggiXmlDto messaggiXmlDto = new MessaggiXmlDto();
				messaggiXmlDto.setXmlIn(payloadBytes);
				Long id = persistXmlFacade.inserisciLogXml(messaggiXmlDto);
				message.getExchange().put(AUTH_L_XML_MESSAGGI_ID, Long.toString(id));
				message.setContent(InputStream.class, new ByteArrayInputStream(payloadBytes));

			} catch (IOException ioe) {
				ioe.printStackTrace();
			}
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
