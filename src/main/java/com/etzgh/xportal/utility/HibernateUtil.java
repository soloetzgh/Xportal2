package com.etzgh.xportal.utility;

import com.etzgh.xportal.model.MenuOptions;
import com.etzgh.xportal.model.NODES;
import com.etzgh.xportal.model.PortalSettingsData;
import com.etzgh.xportal.model.RoleOption;
import com.etzgh.xportal.model.UserManagement;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
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
public class HibernateUtil {

    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;
    private static final Config config = new Config();
    private static final Logger log = Logger.getLogger(HibernateUtil.class.getName());

    static {
        try {

            StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();

            Map<String, String> settings = new HashMap<>();
//            log.info("Username: " + config.getProperty("DB_CONNECTION_USERNAME") + " password: " + config.getProperty("DB_CONNECTION_PASSWORD"));
            settings.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
            settings.put(Environment.URL, "jdbc:mysql://");
            settings.put(Environment.USER, config.getProperty("DB_CONNECTION_USERNAME"));
            settings.put(Environment.PASS, config.getProperty("DB_CONNECTION_PASSWORD"));
            settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");

            settings.put(Environment.CACHE_PROVIDER_CONFIG, "org.hibernate.cache.internal.NoCacheProvider");
            settings.put(Environment.CONNECTION_PROVIDER, "org.hibernate.connection.C3P0ConnectionProvider");
            settings.put(Environment.C3P0_MIN_SIZE, "5");
            settings.put(Environment.C3P0_MAX_SIZE, "20");
            settings.put(Environment.C3P0_TIMEOUT, "1");
            settings.put(Environment.C3P0_ACQUIRE_INCREMENT, "1");
            settings.put(Environment.C3P0_MAX_STATEMENTS, "200");
            settings.put(Environment.C3P0_IDLE_TEST_PERIOD, "3000");
            settings.put(Environment.SHOW_SQL, "true");
            registryBuilder.applySettings(settings);

            registry = registryBuilder.build();

            MetadataSources sources = new MetadataSources(registry);

            sources.addAnnotatedClass(PortalSettingsData.class);
            sources.addAnnotatedClass(UserManagement.class);
            sources.addAnnotatedClass(NODES.class);
            sources.addAnnotatedClass(MenuOptions.class);
            sources.addAnnotatedClass(RoleOption.class);

            Metadata metadata = sources.getMetadataBuilder().build();

            sessionFactory = metadata.getSessionFactoryBuilder().build();

        } catch (Exception e) {
            log.error(e.getMessage(), e);

            if (registry != null) {
                StandardServiceRegistryBuilder.destroy(registry);
            }
        }

    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }

    public static void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

}
