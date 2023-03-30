package com.etzgh.xportal.utility;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAConnection {

    EntityManagerFactory factory = null;
    EntityManager manager = null;

    public EntityManager getEntityManger(String dbType) {
        if (dbType.equalsIgnoreCase("reprocessDB")) {
            if (factory != null) {
                factory.close();
            }
            factory = Persistence.createEntityManagerFactory("ReprocessPU");
        } else if (dbType.equalsIgnoreCase("ussdDb40.9")) {
            if (factory != null) {
                factory.close();
            }
            factory = Persistence.createEntityManagerFactory("UssdDB40.9PU");

        } else if (dbType.equalsIgnoreCase("sms")) {
            if (factory != null) {
                factory.close();
            }
            factory = Persistence.createEntityManagerFactory("centTelcodbPU");
        }
        manager = factory.createEntityManager();
        return this.manager;
    }

}
