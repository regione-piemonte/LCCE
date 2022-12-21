/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.solconfig.configuratoreweb.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamSource;

import org.apache.log4j.Logger;

import it.csi.solconfig.configuratoreweb.business.dao.util.Constants;

public class RequestUtils {
	private static Logger log = Logger.getLogger(Constants.APPLICATION_CODE);

	public static <T> T load(Class<T> clazz, String resName) {

		T customer = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(clazz);

			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			JAXBElement<T> root = jaxbUnmarshaller.unmarshal(new StreamSource(
					RequestUtils.class.getResourceAsStream(resName)), clazz);
			customer = root.getValue();

		} catch (JAXBException e) {
			log.error("[RequestUtils::load]", e);
		}

		return customer;
	}

	public static <T> String toString(T obj, String localPart) {
		return toString(obj, new QName(localPart));
	}

	public static <T> String toString(T obj) {
		return toString(obj, new QName("data"));
	}

	public static <T> byte[] toByteArray(T obj, String localPart) {
		return toByteArray(obj, new QName(localPart));
	}

	public static <T> byte[] toByteArray(T obj, QName qName) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			@SuppressWarnings("unchecked")
			Class<T> clazz = (Class<T>) obj.getClass();
			JAXBContext jaxbContext = JAXBContext.newInstance(clazz);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

			// output pretty printed
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			JAXBElement<T> root = new JAXBElement<T>(qName, clazz, obj);

			// jaxbMarshaller.marshal(customer, file);
			jaxbMarshaller.marshal(root, baos);
		} catch (Exception e) {
			log.error("[RequestUtils::dump]", e);
		}
		return baos.toByteArray();
	}

	public static <T> String toString(T obj, QName qName) {
		return new String(toByteArray(obj, qName));
	}

	public static void store(String fileName, byte[] bytes) {
		FileOutputStream fop = null;

		File file;
		try {
			file = new File(fileName);

			fop = new FileOutputStream(file);

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}

			// get the content in bytes

			fop.write(bytes);
			fop.flush();
			fop.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
