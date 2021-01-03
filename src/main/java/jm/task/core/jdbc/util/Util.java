package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;



public class Util {
    private static final String url = "jdbc:mysql://localhost:3306/task";
    private static final String user = "root";
    private static final String password = "Aventador10";
    private static final String driver = "com.mysql.jdbc.Driver";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("connection error");
        }
        return connection;
    }

    public static SessionFactory getSessionFactory() {
        SessionFactory sessionFactory = null;
        try {

            Properties setting = new Properties();
            setting.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
            setting.put(Environment.URL,"jdbc:mysql://localhost:3306/task");
            setting.put(Environment.USER, "root");
            setting.put(Environment.PASS, "Aventador10");
            setting.put(Environment.SHOW_SQL, "true");
            setting.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");
            setting.setProperty(Environment.HBM2DDL_AUTO,"update");

            Configuration configuration = new Configuration();
            configuration.setProperties(setting);
            configuration.addAnnotatedClass(User.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        } catch (Exception e) {
            System.out.println("error building sessionFactory");
            e.printStackTrace();
        }
        return sessionFactory;
    }

}
