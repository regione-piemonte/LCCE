package it.csi.dma.dmaloginccebl.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import it.csi.dma.dmaloginccebl.business.dao.MessaggiXmlLowDao;
import it.csi.dma.dmaloginccebl.business.dao.dto.MessaggiXmlDto;

@Service
@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class PersistXmlFacade {
		
		@Autowired
		MessaggiXmlLowDao messaggiXmlLowDao;
	
		public Long inserisciLogXml(MessaggiXmlDto messaggiXmlDto) {
			messaggiXmlLowDao.insert(messaggiXmlDto);
			return messaggiXmlDto.getId();
		}
	
		public Long updateLogXml(MessaggiXmlDto messaggiXmlDto) {
			messaggiXmlLowDao.update(messaggiXmlDto);
			return messaggiXmlDto.getId();
		}
}
