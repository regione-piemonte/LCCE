/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.dma.dmaloginccebl.iride.service;


import it.csi.dma.dmaloginccebl.iride.client.PolicyEnforcerBaseService;

public interface PolicyEnforcerBaseServiceImpl extends javax.xml.rpc.Service {
	public String getPolicyEnforcerBaseAddress();

	public PolicyEnforcerBaseService getPolicyEnforcerBase() throws javax.xml.rpc.ServiceException;

	public PolicyEnforcerBaseService
			getPolicyEnforcerBase(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
