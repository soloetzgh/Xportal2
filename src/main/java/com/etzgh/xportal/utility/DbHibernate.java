package com.etzgh.xportal.utility;

import com.etzgh.xportal.model.AuditTrail;
import com.etzgh.xportal.model.BucketBalanceType;
import com.etzgh.xportal.model.BulkUpload;
import com.etzgh.xportal.model.BulkUploadLog;
import com.etzgh.xportal.model.CocoaProfile;
import com.etzgh.xportal.model.ECARDHOLDER;
import com.etzgh.xportal.model.E_CMS;
import com.etzgh.xportal.model.E_RequestLog;
import com.etzgh.xportal.model.E_Reversal;
import com.etzgh.xportal.model.ExternalRequests;
import com.etzgh.xportal.model.MenuOptions;
import com.etzgh.xportal.model.MobileMoney;
import com.etzgh.xportal.model.NODES;
import com.etzgh.xportal.model.PeriodData;
import com.etzgh.xportal.model.PortalSettingsData;
import com.etzgh.xportal.model.RoleOption;
import com.etzgh.xportal.model.UserManagement;
import com.etzgh.xportal.model.UssdCardTransactions;
import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

/**
 * @author Eugene
 */
public class DbHibernate {

    private static int started = 0;
    private static final Config config = new Config();
    private static final Map<String, SessionFactory> sessionFactoryManager = new HashMap<>();
    private static final Logger log = Logger.getLogger(DbHibernate.class.getName());
    private static final String ERROR = "ERROR";

    static {
        PropertyConfigurator.configure("cfg" + File.separator + "log4j.config");
        try {

            log.info("Setting AppDb");

            config.getProperty("SHOW_EXTERNAL_SQL");
            config.getProperty("SHOW_APP_SQL");

            SessionFactory sessionFactory_ = null;
            StandardServiceRegistry registry_ = null;
            try {

                StandardServiceRegistryBuilder registryBuilder_ = new StandardServiceRegistryBuilder();

                Map<String, String> settings_ = new HashMap<>();
                settings_.put(Environment.DRIVER,
                        config.getProperty("APP_DB_TYPE").equalsIgnoreCase("mysql") ? "com.mysql.jdbc.Driver"
                        : "com.sybase.jdbc4.jdbc.SybDriver");

                settings_.put(Environment.URL,
                        (config.getProperty("APP_DB_TYPE").equalsIgnoreCase("mysql")
                        ? "jdbc:mysql://"
                        : "jdbc:sybase:Tds:")
                        + config.getProperty("APP_DB_CONNECTION_URL")
                        + (config.getProperty("APP_DB_TYPE").equalsIgnoreCase("mysql")
                        ? "?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&useSSL=false&allowPublicKeyRetrieval=true"
                        : ""));
                settings_.put(Environment.USER, config.getProperty("APP_DB_CONNECTION_USERNAME"));
                settings_.put(Environment.PASS, config.getProperty("APP_DB_CONNECTION_PASSWORD"));
                settings_.put(Environment.DIALECT,
                        (config.getProperty("APP_DB_TYPE").equalsIgnoreCase("mysql")
                        ? "org.hibernate.dialect.MySQLDialect"
                        : "org.hibernate.dialect.SybaseDialect"));
                settings_.put(Environment.POOL_SIZE, "1");
                settings_.put(Environment.CACHE_PROVIDER_CONFIG, "org.hibernate.cache.internal.NoCacheProvider");
                settings_.put(Environment.CONNECTION_PROVIDER, "org.hibernate.connection.C3P0ConnectionProvider");
                settings_.put(Environment.C3P0_MIN_SIZE, "5");
                settings_.put(Environment.C3P0_MAX_SIZE, "5");
                settings_.put(Environment.C3P0_TIMEOUT, "1");
                settings_.put(Environment.C3P0_ACQUIRE_INCREMENT, "1");
                settings_.put(Environment.C3P0_MAX_STATEMENTS, "200");
                settings_.put(Environment.C3P0_IDLE_TEST_PERIOD, "3000");
                settings_.put(Environment.SHOW_SQL,
                        config.getProperty("SHOW_APP_SQL").equals("true") ? "true" : "false");

                settings_.put(Environment.LOG_JDBC_WARNINGS, "false");
                registryBuilder_.applySettings(settings_);

                registry_ = registryBuilder_.build();

                MetadataSources sources_ = new MetadataSources(registry_);

                sources_.addAnnotatedClass(PortalSettingsData.class);
                sources_.addAnnotatedClass(UserManagement.class);
                sources_.addAnnotatedClass(NODES.class);
                sources_.addAnnotatedClass(MenuOptions.class);
                sources_.addAnnotatedClass(RoleOption.class);
                sources_.addAnnotatedClass(MobileMoney.class);
                sources_.addAnnotatedClass(AuditTrail.class);
                sources_.addAnnotatedClass(BucketBalanceType.class);
                sources_.addAnnotatedClass(E_CMS.class);

                sources_.addAnnotatedClass(CocoaProfile.class);
                sources_.addAnnotatedClass(BulkUpload.class);
                sources_.addAnnotatedClass(BulkUploadLog.class);
                sources_.addAnnotatedClass(PeriodData.class);
                sources_.addAnnotatedClass(UssdCardTransactions.class);
                sources_.addAnnotatedClass(ExternalRequests.class);

                Metadata metadata_ = sources_.getMetadataBuilder().build();

                sessionFactory_ = metadata_.getSessionFactoryBuilder().build();

            } catch (Exception c) {

                log.error(ERROR, c);

                if (registry_ != null) {
                    StandardServiceRegistryBuilder.destroy(registry_);
                    log.info("APP DB DONE_");

                }
            } finally {

                if (sessionFactory_ != null) {
                    sessionFactoryManager.put("APP_DB", sessionFactory_);
                    log.info("APP_DB DONE");
                }

                String extDbSetup = config.getProperty("EXT_DB");

                if (!extDbSetup.trim().isEmpty()) {
                    List<String> banks = Arrays.asList(extDbSetup.trim().split("\\s*,\\s*"));
                    log.info("EXT DB ::: " + banks);

                    banks.stream().forEach(f -> {
                        SessionFactory sessionFactory = createSessionFactory(f);
                        if (sessionFactory != null) {
                            sessionFactoryManager.put(f, sessionFactory);

                        }
                    });
                }
            }

        } catch (Exception e) {

            log.error(ERROR, e);

        }
    }

    public static SessionFactory createSessionFactory(String f) {

        log.info("Setting " + f + " Db - " + config.getProperty(f + "_DB_TYPE"));

        SessionFactory sessionFactory = null;
        StandardServiceRegistry registry = null;
        try {

            StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();

            Map<String, String> settings = new HashMap<>();
            settings.put(Environment.DRIVER,
                    config.getProperty(f + "_DB_TYPE").equalsIgnoreCase("mysql") ? "com.mysql.jdbc.Driver"
                    : "com.sybase.jdbc4.jdbc.SybDriver");

            settings.put(Environment.URL,
                    (config.getProperty(f + "_DB_TYPE").equalsIgnoreCase("mysql")
                    ? "jdbc:mysql://"
                    : "jdbc:sybase:Tds:")
                    + config.getProperty(f + "_DB_CONNECTION_URL")
                    + (config.getProperty(f + "_DB_TYPE").equalsIgnoreCase("mysql")
                    ? "?autoReconnect=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true"
                    : ""));
            settings.put(Environment.USER, config.getProperty(f + "_DB_CONNECTION_USERNAME"));
            settings.put(Environment.PASS, config.getProperty(f + "_DB_CONNECTION_PASSWORD"));
            settings.put(Environment.DIALECT,
                    (config.getProperty(f + "_DB_TYPE").equalsIgnoreCase("mysql") ? "org.hibernate.dialect.MySQLDialect"
                    : "org.hibernate.dialect.SybaseDialect"));
            settings.put(Environment.POOL_SIZE, "1");
            settings.put(Environment.CACHE_PROVIDER_CONFIG, "org.hibernate.cache.internal.NoCacheProvider");
            settings.put(Environment.CONNECTION_PROVIDER, "org.hibernate.connection.C3P0ConnectionProvider");
            settings.put(Environment.C3P0_MIN_SIZE, "5");
            settings.put(Environment.C3P0_MAX_SIZE, "5");
            settings.put(Environment.C3P0_TIMEOUT, "1");
            settings.put(Environment.C3P0_ACQUIRE_INCREMENT, "1");
            settings.put(Environment.C3P0_MAX_STATEMENTS, "200");
            settings.put(Environment.C3P0_IDLE_TEST_PERIOD, "3000");
            settings.put(Environment.SHOW_SQL,
                    config.getProperty("SHOW_EXTERNAL_SQL").equals("true") ? "true" : "false");
            settings.put(Environment.LOG_JDBC_WARNINGS, "false");

            registryBuilder.applySettings(settings);

            registry = registryBuilder.build();

            MetadataSources sources = new MetadataSources(registry);

            sources.addAnnotatedClass(RoleOption.class);
            sources.addAnnotatedClass(NODES.class);
            sources.addAnnotatedClass(E_Reversal.class);
            sources.addAnnotatedClass(ECARDHOLDER.class);
            sources.addAnnotatedClass(E_CMS.class);
            sources.addAnnotatedClass(E_RequestLog.class);
            sources.addAnnotatedClass(E_Reversal.class);
            sources.addAnnotatedClass(CocoaProfile.class);
            sources.addAnnotatedClass(BulkUpload.class);
            sources.addAnnotatedClass(BulkUploadLog.class);
            sources.addAnnotatedClass(PeriodData.class);
            sources.addAnnotatedClass(UssdCardTransactions.class);
            sources.addAnnotatedClass(ExternalRequests.class);

            Metadata metadata = sources.getMetadataBuilder().build();

            sessionFactory = metadata.getSessionFactoryBuilder().build();

        } catch (Exception er) {
            log.error(ERROR, er);

            if (registry != null) {
                StandardServiceRegistryBuilder.destroy(registry);
                log.info(f + " DB DONE_");
            }
        } finally {

        }
        return sessionFactory;

    }

    public static Session getSession(String type) {

        Session session = null;
        try {
            SessionFactory s = sessionFactoryManager.get(type);
            session = s.openSession();
        } catch (Exception h) {
            log.info("ERROR OCCURED OPENING SESSION :: " + type + " :: " + h.getCause());
        }
        return session;
    }

}
