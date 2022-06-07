/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
*
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.dma.dmaloginccebl.integration;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.log4j.Logger;

import it.csi.dma.dmaloginccebl.interfacews.msg.Codifica;
import it.csi.dma.dmaloginccebl.interfacews.msg.RisultatoCodice;
import it.csi.dma.dmaloginccebl.interfacews.msg.SiNo;

/**
 * Base per tutti i marshaller che sotruiscono gli oggetti di business dai DTO.
 * Provvede anche a comprimere gli oggetti codifica che vanno compressi.
 * 
 * La compressione è fatta impostando a true l'attributo "riferito" in una clase
 * che estende Codifica.
 * 
 * @author Petru Todoran
 * @version $Id: $
 */
public class GeneralMarshaller {

	private Logger logger;

	private BeanConverter beanConverter;

	public BeanConverter getBeanConverter() {
		return beanConverter;
	}

	public void setBeanConverter(BeanConverter beanConverter) {
		this.beanConverter = beanConverter;
	}

	private Codifica getRiferimento(final List<Codifica> lista,
			final Codifica ricercato) {
		Codifica retVal = null;

		if (lista.contains(ricercato)) {
			Iterator<Codifica> it = lista.iterator();

			while (it.hasNext()) {
				Codifica temp = it.next();

				if (temp.equals(ricercato)) {
					retVal = temp;
					break;
				}
			}
		}

		return retVal;
	}

	@SuppressWarnings("unchecked")
	private <T extends Codifica> void gestisciRiferito(List<Codifica> lista,
			T codificaIn, String prefissoRiferimento)
			throws InstantiationException, IllegalAccessException {
		if ((codificaIn.getCodice() == null || codificaIn.getCodice()
				.equals("")) && codificaIn.getDescrizione() != null)
			codificaIn.setCodice(codificaIn.getDescrizione().hashCode() + "");
		if (codificaIn.isRiferito()) {

			Codifica tempCode = getRiferimento(lista, codificaIn);

			if (tempCode == null) {

				tempCode = (T) (codificaIn.getClass().newInstance());

				tempCode.setCodice(codificaIn.getCodice());
				tempCode.setDescrizione(codificaIn.getDescrizione());

				tempCode.setRiferimento(prefissoRiferimento + "."
						+ (lista.size() + 1));

				lista.add(tempCode);
			}

			codificaIn.setCodice(null);
			codificaIn.setDescrizione(null);
			codificaIn.setRiferimento(tempCode.getRiferimento());
		}
	}

	private void ricomponiCodifica(Codifica codificaIn,
			final List<Codifica> codifiche) {
		for (Codifica temp : codifiche) {
			if (temp.getRiferimento().equals(codificaIn.getRiferimento())) {

				codificaIn.setCodice(temp.getCodice());
				codificaIn.setDescrizione(temp.getDescrizione());

				// Tolgo l'info circa il riferimento
				codificaIn.setRiferimento(null);

				break;
			}
		}
	}

	/**
	 * Ricompone una lista di oggetti, a partire dalle codifiche
	 * 
	 * @param lista
	 * @param codifiche
	 */
	public void uncompressList(List<?> lista, List<Codifica> codifiche) {
		if (lista != null && !lista.isEmpty() && codifiche != null
				&& !codifiche.isEmpty()) {
			for (Object o : lista) {
				uncompress(o, codifiche);
			}
		}
	}

	/**
	 * Ricompone un oggetto dalle codifiche
	 * 
	 * @param o
	 * @param codifiche
	 */
	@SuppressWarnings("rawtypes")
	public void uncompress(Object o, List<Codifica> codifiche) {
		if (o == null) {
			return;
		}

		/**
		 * prima verifico se l'0ggetto stesso è una codifica (es. Prestazione)
		 */
		if (o instanceof Codifica) {
			ricomponiCodifica((Codifica) o, codifiche);
		}

		Class<? extends Object> c = o.getClass();
		for (Member mbr : c.getMethods()) {
			Method method = (Method) mbr;

			String name = method.getName();

			if (name.startsWith("get")) {
				try {
					Object field = method.invoke(o);

					if (field != null) {

						// Se il valore del campo è di tipo Codifica la
						// rimpiazzo con la codifica riferita(se il flag
						// riferimento è true)
						if (field instanceof Codifica) {

							ricomponiCodifica((Codifica) field, codifiche);

						} else {
							// Se il campo non è di tipo codifica navigo in
							// basso nella gerarchia
							String fieldClassName = field.getClass().getName();
							if (!fieldClassName.startsWith("java.lang.")
									&& !fieldClassName.startsWith("java.util.")) {
								uncompress(field, codifiche);
							} else if (fieldClassName.startsWith("java.util.")
									&& (fieldClassName.endsWith("List") || fieldClassName
											.endsWith("Set"))) {
								Collection listaTemp = (Collection) field;

								Iterator it = listaTemp.iterator();

								while (it.hasNext()) {
									uncompress(it.next(), codifiche);
								}
							}
						}
					}

				} catch (IllegalArgumentException e1) {
				} catch (IllegalAccessException e1) {
				} catch (InvocationTargetException e1) {
				}
			}
		}
	}

	/**
	 * Comprime una lista di oggetti popolando le codifiche
	 * 
	 * @param lista
	 * @param codifiche
	 * @param prefisso
	 */
	public void compressList(List<?> lista, List<Codifica> codifiche,
			String prefisso) {
		for (Object o : lista) {
			compress(o, codifiche, prefisso);
		}
	}

	/**
	 * Comprime un oggetto popolando le codifiche
	 * 
	 * @param o
	 * @param codifiche
	 * @param prefisso
	 */
	public void compress(Object o, List<Codifica> codifiche, String prefisso) {
		if (o == null) {
			return;
		}

		/**
		 * prima verifico se l'0ggetto stesso è una codifica (es. Prestazione)
		 */
		if (o instanceof Codifica) {
			try {
				gestisciRiferito(codifiche, (Codifica) o, prefisso);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Class<? extends Object> c = o.getClass();
		for (Member mbr : c.getMethods()) {
			Method method = (Method) mbr;

			String methodName = method.getName();

			if (methodName.startsWith("get")) {
				try {
					Object field = method.invoke(o);

					if (field != null) {

						/*
						 * Se il valore del campo è di tipo Codifica la
						 * rimpiazzo con la codifica riferita(se il flag
						 * riferimento è true)
						 */
						if (field instanceof Codifica) {
							// cambio get con get
							StringBuilder sb = new StringBuilder(methodName);
							sb.setCharAt(0, 's');

							Codifica codificaIn = (Codifica) field;

							try {

								gestisciRiferito(codifiche, codificaIn,
										prefisso);

							} catch (InstantiationException e) {
								if (logger != null) {
									logger.warn("creazione oggetto type ["
											+ codificaIn.getClass().getName()
											+ "] non riuscito!", e);
								} else {
									e.printStackTrace();
								}
							}

						} else {
							/*
							 * Se il campo non è di tipo codifica navigo in
							 * basso nella gerarchia. Nel caso di una lista
							 * ottengo prima l'iterator
							 */
							String fieldClassName = field.getClass().getName();
							if (fieldClassName.startsWith("java.util.")
									&& (fieldClassName.endsWith("List") || fieldClassName
											.endsWith("Set"))) {
								Method methodIterator;
								try {
									methodIterator = field.getClass()
											.getMethod("iterator",
													new Class[] {});

									Iterator<?> iterator = (Iterator<?>) methodIterator
											.invoke(field);

									while (iterator.hasNext()) {
										compress(iterator.next(), codifiche,
												prefisso);
									}

								} catch (SecurityException e) {
								} catch (NoSuchMethodException e) {
									if (logger != null) {
										logger.warn("metodo iterator"
												+ "della classe ["
												+ fieldClassName
												+ "] non esiste!", e);
									} else {
										e.printStackTrace();
									}
								}

							} else if (!fieldClassName.startsWith("java.lang.")
									&& !fieldClassName.equals("java.util.Date")
									&& !fieldClassName.equals("java.sql.Date")
									&& !fieldClassName
											.equals("java.sql.Timestamp")) {
								compress(field, codifiche, prefisso);
							}
						}
					}

				} catch (IllegalArgumentException e1) {
				} catch (IllegalAccessException e1) {
				} catch (InvocationTargetException e1) {
				}
			}
		}
	}

	@SuppressWarnings({ "rawtypes" })
	private void fillOne(String chiaveGet, String chiaveSet, Object oIn,
			Object oOut) {

		PropertyUtilsBean propUtilBean = BeanUtilsBean.getInstance()
				.getPropertyUtils();
		/*
		 * parsifico la chiave get 1) se non contiene alcun punto chiamo
		 * direttamente il set
		 */
		int primoPunto = chiaveSet.indexOf('.');
		if (primoPunto == -1) {
			Object field;
			try {
				field = propUtilBean.getProperty(oIn, chiaveGet);

				Class setType = propUtilBean.getPropertyType(oOut, chiaveSet);

				// Se oOut.getProperty != null
				// && esiste un metodo di tipo set sull'oggetto oOut per quella
				// chiave
				if (field != null && setType != null) {

					if (setType.getName().equals("java.lang.String")) {

						Class getType = field.getClass();

						if (getType.isEnum()) {
							String valTemp = null;
							if (getType == SiNo.class) {
								valTemp = ((SiNo) field).getValue();
							} else if (getType == RisultatoCodice.class) {
								valTemp = ((RisultatoCodice) field).getValue();
							}

							propUtilBean.setProperty(oOut, chiaveSet, valTemp);
						} else {
							propUtilBean.setProperty(oOut, chiaveSet,
									field.toString());
						}

					} else if (setType.getName().equals("java.sql.Date")) {

						propUtilBean.setProperty(
								oOut,
								chiaveSet,
								new java.sql.Date(((java.util.Date) field)
										.getTime()));
					} else if (setType.getName().equals("java.sql.Timestamp")) {

						propUtilBean.setProperty(
								oOut,
								chiaveSet,
								new java.sql.Timestamp(((java.util.Date) field)
										.getTime()));
					} else if (setType.isEnum()) {
						Object valTemp = null;
						if (setType == SiNo.class) {
							valTemp = SiNo.fromValue(field.toString());
						} else if (setType == RisultatoCodice.class) {
							valTemp = RisultatoCodice.fromValue(field
									.toString());
						}
						propUtilBean.setProperty(oOut, chiaveSet, valTemp);
					} else {
						propUtilBean.setProperty(oOut, chiaveSet, field);
					}

				}

			} catch (IllegalAccessException e) {
				// e.printStackTrace();
			} catch (InvocationTargetException e) {
				// e.printStackTrace();
			} catch (NoSuchMethodException e) {
				if (logger != null) {
					logger.warn("metodo get per [" + chiaveGet
							+ "] della classe [" + oIn.getClass().getName()
							+ "] non esiste!", e);
				} else {
					e.printStackTrace();
				}

			}

		} else {
			// deve fare la ricorsione
			String primaParteSet = chiaveSet.substring(0, primoPunto);
			String secondaParteSet = chiaveSet.substring(primoPunto + 1);

			/*
			 * System.out.println("prima:" + primaParteSet);
			 * System.out.println("dopo:" + secondaParteSet);
			 */

			/*
			 * Method methodGet = oOut.getClass().getMethod(sb.toString(),
			 * null);
			 */

			Object oOutPrimaParte = null;

			try {
				oOutPrimaParte = propUtilBean.getProperty(oOut, primaParteSet);
			} catch (Exception e) {
				if (logger != null) {
					logger.warn("Invocazione metodo get per proprietà ["
							+ primaParteSet + "] della classe ["
							+ oOut.getClass().getName() + "] non riuscita!", e);
				} else {
					e.printStackTrace();
				}
			}

			if (oOutPrimaParte == null) {

				Class classType = null;
				try {
					classType = propUtilBean.getPropertyType(oOut,
							primaParteSet);
				} catch (Exception e) {
					if (logger != null) {
						logger.warn(
								"Ottenimento class type per proprietà ["
										+ primaParteSet + "] della classe ["
										+ oOut.getClass().getName()
										+ "] non riuscito!", e);
					} else {
						e.printStackTrace();
					}
				}

				if (classType != null) {

					try {
						oOutPrimaParte = classType.newInstance();
						BeanUtils.setProperty(oOut, primaParteSet,
								oOutPrimaParte);
					} catch (InstantiationException e) {
						if (logger != null) {
							logger.warn(
									"creazione oggetto type ["
											+ classType.getName()
											+ "] non riuscito!", e);
						} else {
							e.printStackTrace();
						}
					} catch (IllegalAccessException e) {
					} catch (InvocationTargetException e) {
						if (logger != null) {
							logger.warn(
									"Invocazione metodo set per proprietà ["
											+ primaParteSet
											+ "] della classe ["
											+ oOutPrimaParte
											+ "] non riuscita!", e);
						} else {
							e.printStackTrace();
						}
					}
				}

			}

			fillOne(chiaveGet, secondaParteSet, oIn, oOutPrimaParte);

		}
	}

	/**
	 * Riempie un oggetto target (oOut), partendo da un oggetto source (oIn) e
	 * data la mappa della corrispondenza dei getter/setter
	 * 
	 * @param map
	 * @param oIn
	 * @param oOut
	 */
	public void fill(Object oIn, Object oOut, Map<String, String> map) {
		for (String chiaveSet : map.keySet()) {
			String chiaveGet = map.get(chiaveSet);

			fillOne(chiaveGet, chiaveSet, oIn, oOut);
		}
	}

	/**
	 * fa la stessa cosa di fill ma gestisce le property null ed ha i converter
	 * configurabili
	 * 
	 * @param oIn
	 * @param oOut
	 * @param map
	 */
	public void fill2(Object oIn, Object oOut, Map<String, String> map) {
		if (beanConverter.getLogger() == null)
			beanConverter.setLogger(logger);
		beanConverter.convert(map, oOut, oIn);
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}