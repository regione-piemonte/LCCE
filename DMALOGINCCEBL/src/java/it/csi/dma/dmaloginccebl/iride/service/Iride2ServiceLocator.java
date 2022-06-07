/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.iride.service;


import it.csi.dma.dmaloginccebl.iride.client.PolicyEnforcerBaseService;
import org.apache.axis.client.Service;

public class Iride2ServiceLocator extends Service implements PolicyEnforcerBaseServiceImpl {
	private static final long serialVersionUID = 297491815883348487L;

	private int timeout = 1500;

	public Iride2ServiceLocator() {
	}

	public Iride2ServiceLocator(String address) {
		this.address = address;
	}

	// Use to get a proxy class for PolicyEnforcerBase
	private String address;

	public String getPolicyEnforcerBaseAddress() {
		return address;
	}

	// The WSDD service name defaults to the port name.
	private String PolicyEnforcerBaseWSDDServiceName = "PolicyEnforcerBase";

	public String getPolicyEnforcerBaseWSDDServiceName() {
		return PolicyEnforcerBaseWSDDServiceName;
	}

	public void setPolicyEnforcerBaseWSDDServiceName(String name) {
		PolicyEnforcerBaseWSDDServiceName = name;
	}

	public PolicyEnforcerBaseService getPolicyEnforcerBase() throws javax.xml.rpc.ServiceException {
		java.net.URL endpoint;

		try {
			endpoint = new java.net.URL(address);
		}
		catch (java.net.MalformedURLException e) {
			throw new javax.xml.rpc.ServiceException(e);
		}
		return getPolicyEnforcerBase(endpoint);
	}

	public PolicyEnforcerBaseService
			getPolicyEnforcerBase(java.net.URL portAddress) throws javax.xml.rpc.ServiceException
	{
		try {
			PolicyEnforcerBaseStub _stub = new PolicyEnforcerBaseStub(portAddress, this);

			_stub.setTimeout(timeout);
			_stub.setPortName(getPolicyEnforcerBaseWSDDServiceName());

			return _stub;
		}
		catch (org.apache.axis.AxisFault e) {
			return null;
		}
	}

	/**
	 * For the given interface, get the stub implementation. If this service has no
	 * port for the given interface, then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
		try {
			if (PolicyEnforcerBaseService.class.isAssignableFrom(serviceEndpointInterface)) {
				PolicyEnforcerBaseStub _stub = new PolicyEnforcerBaseStub(
						new java.net.URL(address), this);
				_stub.setPortName(getPolicyEnforcerBaseWSDDServiceName());
				return _stub;
			}
		}
		catch (Throwable t) {
			throw new javax.xml.rpc.ServiceException(t);
		}
		throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null"
				: serviceEndpointInterface.getName()));
	}

	/**
	 * For the given interface, get the stub implementation. If this service has no
	 * port for the given interface, then ServiceException is thrown.
	 */
	public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
		if (portName == null) {
			return getPort(serviceEndpointInterface);
		}
		String inputPortName = portName.getLocalPart();
		if ("PolicyEnforcerBase".equals(inputPortName)) {
			return getPolicyEnforcerBase();
		}

		java.rmi.Remote _stub = getPort(serviceEndpointInterface);
		((org.apache.axis.client.Stub) _stub).setPortName(portName);
		return _stub;
	}

	public javax.xml.namespace.QName getServiceName() {
		return new javax.xml.namespace.QName("http://tst-wfexp-vip01.csi.it/pep_wsfad_policy/services/PolicyEnforcerBase",
				"PolicyEnforcerBaseServiceService");
	}

	private java.util.HashSet<javax.xml.namespace.QName> ports = null;

	public java.util.Iterator<javax.xml.namespace.QName> getPorts() {
		if (ports == null) {
			ports = new java.util.HashSet<javax.xml.namespace.QName>();
			ports.add(new javax.xml.namespace.QName("http://tst-wfexp-vip01.csi.it/pep_wsfad_policy/services/PolicyEnforcerBase", "PolicyEnforcerBase"));
		}
		return ports.iterator();
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(String portName, String address) throws javax.xml.rpc.ServiceException {

		if ("PolicyEnforcerBase".equals(portName)) {
			this.address = address;
		}
		else { // Unknown Port Name
			throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
		}
	}

	/**
	 * Set the endpoint address for the specified port name.
	 */
	public void setEndpointAddress(javax.xml.namespace.QName portName, String address) throws javax.xml.rpc.ServiceException {
		setEndpointAddress(portName.getLocalPart(), address);
	}
}