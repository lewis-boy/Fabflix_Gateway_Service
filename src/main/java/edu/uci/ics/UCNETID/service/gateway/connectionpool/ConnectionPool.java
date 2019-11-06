package edu.uci.ics.UCNETID.service.gateway.connectionpool;

import edu.uci.ics.UCNETID.service.gateway.logger.ServiceLogger;
import org.glassfish.jersey.internal.util.ExceptionUtils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

public class ConnectionPool {
    LinkedList<Connection> connections;
    String driver;
    String url;
    String username;
    String password;

    public ConnectionPool(int numCons, String driver, String url, String username, String password) {

    }

    public synchronized Connection requestCon() {
        return null;
    }

    public synchronized void releaseCon(Connection con) {

    }

    private Connection createConnection() {
        try {
            Class.forName(driver);
            Connection con = DriverManager.getConnection(url, username, password);
            ServiceLogger.LOGGER.config("Created connection to database: " + url);
            return con;
        } catch (ClassNotFoundException | SQLException | NullPointerException e) {
            ServiceLogger.LOGGER.severe("Unable to create connection to database.\n" + ExceptionUtils.exceptionStackTraceAsString(e));
            return null;
        }
    }
}
