/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/

package it.csi.dma.dmaloginccebl.iride.data;

public class MalformedIdTokenException extends UserException implements java.io.Serializable {
	public MalformedIdTokenException() {
	}

	public MalformedIdTokenException(String msg) {
		super(null, null, msg);
	}

	public MalformedIdTokenException(String nestedExcMsg, String nestedExcClassName, String stackTraceMessage) {
		super(nestedExcMsg, nestedExcClassName, stackTraceMessage);
	}

	private Object __equalsCalc = null;

	public synchronized boolean equals(Object obj) {
		if (!(obj instanceof MalformedIdTokenException))
			return false;
		MalformedIdTokenException other = (MalformedIdTokenException) obj;
		if (obj == null)
			return false;
		if (this == obj)
			return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = super.equals(obj);
		__equalsCalc = null;
		return _equals;
	}

	private boolean __hashCodeCalc = false;

	public synchronized int hashCode() {
		if (__hashCodeCalc) {
			return 0;
		}
		__hashCodeCalc = true;
		int _hashCode = super.hashCode();
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc = new org.apache.axis.description.TypeDesc(MalformedIdTokenException.class, true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("urn:PolicyEnforcerBase", "MalformedIdTokenException"));
	}

	/**
	 * Return type metadata object
	 */
	public static org.apache.axis.description.TypeDesc getTypeDesc() {
		return typeDesc;
	}

	/**
	 * Get Custom Serializer
	 */
	public static org.apache.axis.encoding.Serializer getSerializer(String mechType,
                                                                    Class _javaType,
                                                                    javax.xml.namespace.QName _xmlType)
	{
		return new org.apache.axis.encoding.ser.BeanSerializer(_javaType, _xmlType, typeDesc);
	}

	/**
	 * Get Custom Deserializer
	 */
	public static org.apache.axis.encoding.Deserializer getDeserializer(String mechType,
                                                                        Class _javaType,
                                                                        javax.xml.namespace.QName _xmlType)
	{
		return new org.apache.axis.encoding.ser.BeanDeserializer(_javaType, _xmlType, typeDesc);
	}

}
