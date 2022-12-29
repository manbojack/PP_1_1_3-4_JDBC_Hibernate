package jm.task.core.jdbc.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import jm.task.core.jdbc.model.User;

public class Util {
    private static final Properties property = getProperties();
    private static Connection connection = null;
    private static SessionFactory sessionFactory;

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection(
                    property.getProperty("db.url"),
                    property.getProperty("db.user"),
                    property.getProperty("db.password")
            );
            connection.setAutoCommit(false);
            System.out.println("[INFO]: Connection OK");
        } catch (SQLException e) {
            System.err.println("[ERROR]: Connection ERROR");
        }
        return connection;
    }

    private static Properties getProperties() {
        Properties properties = null;
        try (InputStream input = new FileInputStream("src/main/resources/application.properties")) {
            properties = new Properties();
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return properties;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();

                // Hibernate settings equivalent to hibernate.cfg.xml's properties
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, property.getProperty("db.hibernate.driver"));
                settings.put(Environment.URL, property.getProperty("db.hibernate.url"));
                settings.put(Environment.USER, property.getProperty("db.hibernate.user"));
                settings.put(Environment.PASS, property.getProperty("db.hibernate.password"));
                settings.put(Environment.DIALECT, property.getProperty("db.hibernate.dialect"));
                settings.put(Environment.SHOW_SQL, property.getProperty("db.hibernate.showSQL"));
                settings.put(Environment.HBM2DDL_AUTO, property.getProperty("db.hibernate.HBM2DDL_AUTO"));
                settings.put(
                        Environment.CURRENT_SESSION_CONTEXT_CLASS,
                        property.getProperty("db.hibernate.currentSessionContextClass")
                );

                configuration.setProperties(settings);

                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }

}
