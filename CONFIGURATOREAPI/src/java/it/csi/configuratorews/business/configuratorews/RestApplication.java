/*
* SPDX-FileCopyrightText: (C) Copyright 2022Regione Piemonte
* 
* SPDX-License-Identifier: EUPL-1.2
*/
package it.csi.configuratorews.business.configuratorews;

import it.csi.configuratorews.util.LogUtil;
import it.csi.configuratorews.util.SpringApplicationContextProvider;
import org.springframework.context.ApplicationContext;

import io.swagger.annotations.SwaggerDefinition;
import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import io.swagger.annotations.BasicAuthDefinition;
import io.swagger.annotations.SecurityDefinition;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


@ApplicationPath("api/v1")
@SwaggerDefinition(securityDefinition = @SecurityDefinition(basicAuthDefinitions = {@BasicAuthDefinition(key = "basicAuth")}))
public class RestApplication extends Application {
	private LogUtil log = new LogUtil(RestApplication.class);
	
	private Set<Object> singletons = new HashSet<Object>();
	private Set<Class<?>> empty = new HashSet<Class<?>>();

	public RestApplication() {
		final String METHOD_NAME = "RestApplication";
		log.debug(METHOD_NAME, "init RestApplication.");
		
		// autogenera lo schema swagger in GET sotto l'url /api/v1/swagger.json
//		configureSwagger();
		
		ApplicationContext appContext = SpringApplicationContextProvider.getApplicationContext();
		Map<String, Object> beans = appContext.getBeansWithAnnotation(Path.class);
		
		log.debug(METHOD_NAME, "Discovering @Path annotated beans... ");
		Collection<Object> values = beans.values();
		values.stream().forEach(this::register);

	}

	private void register(Object serviceImpl) {
		final String METHOD_NAME = "RestApplication";
		if(serviceImpl == null) {
			return;
		}
		log.info(METHOD_NAME, "Registering Rest bean: %s", serviceImpl.getClass().getName());
		singletons.add(serviceImpl);
	}
	

	@Override
	public Set<Class<?>> getClasses() {
		return empty;
	}

	@Override
	public Set<Object> getSingletons() {
		return singletons;
	}
	

//	/**
//	 * Genera lo YAML swagger
//	 */
	private void configureSwagger() {

		empty.add(ApiListingResource.class);
		empty.add(SwaggerSerializers.class);

        BeanConfig config = new BeanConfig();
        config.setConfigId("configuratoreapi");
        config.setTitle("Configuratore REST API");
        config.setVersion("v1");
        config.setContact("CSI Piemonte");
        config.setSchemes(new String[]{"https"});
        config.setBasePath("/api/v1");
        config.setResourcePackage("it.csi.configuratorews.business.configuratorews");
        config.setDescription("Api che realizzano la profilazione applicativa dei servizi online rivolti agli operatori sanitari della Regione Piemonte.");
        config.setPrettyPrint(true); // add swagger-codegen-cli.jar
        config.setScan(true);
    }

}
