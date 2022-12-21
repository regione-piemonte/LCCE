package it.csi.configuratorews.business.configuratorews.temp;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import java.util.Set;
import java.util.HashSet;

import it.csi.configuratorews.business.configuratorews.temp.impl.Name1ApiServiceImpl;
import it.csi.configuratorews.business.configuratorews.temp.impl.Nome1ApiServiceImpl;
import it.csi.configuratorews.business.configuratorews.temp.impl.ServizioAttivoApiServiceImpl;

@ApplicationPath("/")
public class RestApplication extends Application {


    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet<Class<?>>();
        resources.add(Name1ApiServiceImpl.class);
        resources.add(Nome1ApiServiceImpl.class);
        resources.add(ServizioAttivoApiServiceImpl.class);

        return resources;
    }




}