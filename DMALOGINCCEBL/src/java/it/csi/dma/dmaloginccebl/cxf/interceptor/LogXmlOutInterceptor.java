package it.csi.dma.dmaloginccebl.cxf.interceptor;

import java.io.OutputStream;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.io.CacheAndWriteOutputStream;
import org.apache.cxf.io.CachedOutputStream;
import org.apache.cxf.io.CachedOutputStreamCallback;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import it.csi.dma.dmaloginccebl.business.dao.dto.MessaggiXmlDto;
import it.csi.dma.dmaloginccebl.util.ApplicationContextProvider;
import it.csi.dma.dmaloginccebl.util.PersistXmlFacade;
import it.csi.dma.dmaloginccebl.util.Utils;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class LogXmlOutInterceptor extends AbstractPhaseInterceptor<Message> {

	public static final String AUTH_L_XML_MESSAGGI_ID = "AUTH_L_XML_MESSAGGI_ID43342";
	private static final String UTF_8 = "UTF-8";

	ApplicationContext appContext;

	PersistXmlFacade persistXmlFacade;

	public LogXmlOutInterceptor() {
		super(Phase.PRE_STREAM);
	}

	@Override
	public void handleMessage(Message message) throws Fault {
		appContext = ApplicationContextProvider.getApplicationContext();
		persistXmlFacade = appContext.getBean(PersistXmlFacade.class);
		OutputStream out = message.getContent(OutputStream.class);
		final CacheAndWriteOutputStream newOut = new CacheAndWriteOutputStream(out);

		String id = (String) message.getExchange().get(AUTH_L_XML_MESSAGGI_ID);
		message.setContent(OutputStream.class, newOut);
		newOut.registerCallback(new LoggingCallback(new Long(id)));

	}

	public class LoggingCallback implements CachedOutputStreamCallback {
		Long id;

		public LoggingCallback(Long id) {
			this.id = id;
		}

		public void onFlush(CachedOutputStream cos) {
		}

		public void onClose(CachedOutputStream cos) {
			try {
				StringBuilder builder = new StringBuilder();
				cos.writeCacheTo(builder);
				// here comes my xml:
				String soapXml = builder.toString();

				MessaggiXmlDto messaggiXmlDto = new MessaggiXmlDto();

				messaggiXmlDto.setId(id);
				messaggiXmlDto.setXmlOut(soapXml.getBytes());
				persistXmlFacade.updateLogXml(messaggiXmlDto);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}