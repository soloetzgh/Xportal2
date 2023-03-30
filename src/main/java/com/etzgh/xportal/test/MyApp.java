package com.etzgh.xportal.test;

import java.util.LinkedHashSet;
import java.util.Set;
import javax.ws.rs.core.Application;

public class MyApp extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new LinkedHashSet<Class<?>>();
        resources.add(HelloResource.class);
        return resources;
    }

}
